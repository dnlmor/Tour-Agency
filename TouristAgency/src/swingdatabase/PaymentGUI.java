package swingdatabase;

import service.BookingService;
import datamodel.Booking;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentGUI extends JFrame {

    private JTextField amountField;
    private BookingService bookingService;
    private BookingGUI bookingGUI;
    private Booking booking;

    public PaymentGUI(BookingService bookingService, BookingGUI bookingGUI, Booking booking) {
        this.bookingService = bookingService;
        this.bookingGUI = bookingGUI;
        this.booking = booking;

        setTitle("Payment System");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel amountLabel = new JLabel("Enter Amount:");
        amountField = new JTextField(10);

        JButton makePaymentButton = new JButton("Make Payment");
        makePaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makePayment();
            }
        });

        JPanel panel = new JPanel();
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(makePaymentButton);

        add(panel);
    }

    private void makePayment() {
        try {
            double amount = Double.parseDouble(amountField.getText());

            bookingService.updateBookingStatusToConfirmed(booking.getBookId(), amount);

            if (bookingGUI != null) {
                bookingGUI.paymentConfirmed(amount);
            }

            JOptionPane.showMessageDialog(null, "Payment successful");

            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a valid number.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PaymentGUI paymentGUI = new PaymentGUI(new BookingService(), null, null);
                paymentGUI.setVisible(true);
            }
        });
    }
}
