package service;

import datamodel.Booking;
import databases.BookingDatabase;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingService {

    private final String bookingFilePath;
    private final BookingDatabase bookingDatabase;

    public BookingService() {
        this.bookingFilePath = "/Users/dnlmor/Downloads/school docs/semester 3/java development/TouristAgency/src/booking.csv";
        this.bookingDatabase = new BookingDatabase();
    }

    public List<Booking> readBookingsCSV() {
        List<Booking> bookingList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(bookingFilePath))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                if (count != 0) {
                    Booking booking = parseBooking(line);
                    if (booking != null) {
                        bookingList.add(booking);
                    } else {
                        System.err.println("Error parsing booking: " + line);
                    }
                }
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bookingList;
    }

    public void writeBookingCSV(Booking newBooking) {
        String formattedBooking = formatBooking(newBooking);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(bookingFilePath, true))) {
            writer.write(formattedBooking);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Booking createBooking(String customerName, int hotelId, String scheduleDate) {
        List<Booking> existingBookings = readBookingsCSV();
        int newBookingId = findHighestBookingId(existingBookings) + 1;

        int customerId = 1;
        int paymentId = 1;
        Date bookingDate = new Date();
        String status = "Paid";

        Booking newBooking = new Booking();
        newBooking.setBookId(newBookingId);
        newBooking.setCustomerId(customerId);
        newBooking.setScheduleId(getScheduleIdByDate(scheduleDate));
        newBooking.setHotelId(hotelId);
        newBooking.setPaymentId(paymentId);
        newBooking.setBookDate(bookingDate);
        newBooking.setStatus(status);

        writeBookingCSV(newBooking);

        bookingDatabase.insertBooking(newBooking);

        return newBooking;
    }

    public int getScheduleIdByDate(String scheduleDate) {
        List<Booking> bookings = readBookingsCSV();
        for (Booking booking : bookings) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String bookingDateStr = dateFormat.format(booking.getBookDate());

            if (bookingDateStr.equals(scheduleDate)) {
                return booking.getScheduleId();
            }
        }
        return -1; // Return -1 if no matching schedule is found
    }

    public List<String> getScheduleDates() {
        List<Booking> bookings = readBookingsCSV();
        List<String> scheduleDates = new ArrayList<>();

        for (Booking booking : bookings) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            scheduleDates.add(dateFormat.format(booking.getBookDate()));
        }

        return scheduleDates;
    }

    public void updateBookingStatusToConfirmed(int bookingId, double amount) {
        List<Booking> bookings = readBookingsCSV();
        for (Booking booking : bookings) {
            if (booking.getBookId() == bookingId) {
                booking.setStatus("Confirmed");
            }
        }

        updateBookingCSV(bookings);
    }

    private void updateBookingCSV(List<Booking> bookings) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(bookingFilePath, false))) {
            writer.write("bookId,customerId,scheduleId,hotelId,paymentId,bookDate,status");
            writer.newLine();

            for (Booking booking : bookings) {
                writer.write(formatBooking(booking));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Booking parseBooking(String line) {
        try {
            String[] data = line.split(",");
            Booking booking = new Booking();
            booking.setBookId(Integer.parseInt(data[0].trim()));
            booking.setCustomerId(Integer.parseInt(data[1].trim()));
            booking.setScheduleId(Integer.parseInt(data[3].trim()));
            booking.setHotelId(Integer.parseInt(data[4].trim()));
            booking.setPaymentId(Integer.parseInt(data[5].trim()));
            booking.setBookDate(parseDate(data[6].trim()));
            booking.setStatus(data[7].trim());
            return booking;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String formatBooking(Booking booking) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("%d,%d,%d,%d,%d,%d,%s,%s",
                booking.getBookId(),
                booking.getCustomerId(),
                booking.getScheduleId(),
                booking.getHotelId(),
                booking.getPaymentId(),
                dateFormat.format(booking.getBookDate()),
                booking.getStatus()
        );
    }

    private Date parseDate(String dateString) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
    }

    private int findHighestBookingId(List<Booking> bookings) {
        int highestId = 0;
        for (Booking booking : bookings) {
            if (booking.getBookId() > highestId) {
                highestId = booking.getBookId();
            }
        }
        return highestId;
    }
}
