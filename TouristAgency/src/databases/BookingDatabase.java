package databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import datamodel.Booking;

public class BookingDatabase {

    public static void insertBooking(Booking booking) {
        try (Connection connection = DBConnector.connect()) {
            String query = "INSERT INTO booking (customerId, scheduleId, hotelId, paymentId, bookingDate, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, booking.getCustomerId());
                preparedStatement.setInt(3, booking.getScheduleId());
                preparedStatement.setInt(4, booking.getHotelId());
                preparedStatement.setInt(5, booking.getPaymentId());
                preparedStatement.setString(6, booking.getBookDate().toString());
                preparedStatement.setString(7, booking.getStatus());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateBookingDateTime(int bookingId, String newDateTime) {
        try (Connection connection = DBConnector.connect()) {
            String query = "UPDATE booking SET bookingDate = ? WHERE bookingId = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, newDateTime);
                preparedStatement.setInt(2, bookingId);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
