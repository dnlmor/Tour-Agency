package launcher;

import databases.DBConnector;
import datamodel.Booking;
import service.BookingService;
import service.HotelService;
import swingdatabase.BookingGUI;
import swingdatabase.PaymentGUI;

import javax.swing.*;

public class Launcher {

    public static void main(String[] args) {
        try {
            // Initialize database connection
            DBConnector.connect();

            // Create instances of services
            BookingService bookingService = new BookingService();
            HotelService hotelService = new HotelService();

            // Create instances of GUIs
            BookingGUI bookingGUI = new BookingGUI(bookingService, hotelService);

            bookingGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            bookingGUI.pack();
            bookingGUI.setVisible(true);

            String customerName = JOptionPane.showInputDialog("Enter your name:");
            String destination = JOptionPane.showInputDialog("Enter your destination:");
            String schedule = JOptionPane.showInputDialog("Enter your schedule (date):");
            String hotel = JOptionPane.showInputDialog("Enter your hotel:");

            int hotelId = 1;

            // Create booking based on user input
            Booking booking = bookingService.createBooking(customerName, hotelId, schedule);

            // Display payment GUI
            PaymentGUI paymentGUI = new PaymentGUI(bookingService, bookingGUI, booking);
            paymentGUI.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            try {
                DBConnector.closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}