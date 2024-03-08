package swingdatabase;

import service.BookingService;
import service.HotelService;

import javax.swing.*;

import datamodel.Booking;
import datamodel.Hotel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BookingGUI extends JFrame {

    private JTextField nameField;
    private JTextField destinationField;
    private JTextField scheduleField;
    private JComboBox<String> hotelComboBox;
    private BookingService bookingService;
    private HotelService hotelService;

    public BookingGUI(BookingService bookingService, HotelService hotelService) {
        this.bookingService = bookingService;
        this.hotelService = hotelService;
        setTitle("Booking System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel nameLabel = new JLabel("Customer Name:");
        JLabel destinationLabel = new JLabel("Destination:");
        JLabel scheduleLabel = new JLabel("Schedule:");
        JLabel hotelLabel = new JLabel("Select Hotel:");

        nameField = new JTextField(10);
        destinationField = new JTextField(10);
        scheduleField = new JTextField(10);

        List<Hotel> hotelNames = hotelService.getHotels();
        DefaultComboBoxModel<String> hotelComboBoxModel = new DefaultComboBoxModel<>();
        for (Hotel hotelName : hotelNames) {
            hotelComboBoxModel.addElement(hotelName.getHotelName());
        }
        hotelComboBox = new JComboBox<>(hotelComboBoxModel);

        JButton proceedToPaymentButton = new JButton("Proceed to Payment");
        proceedToPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proceedToPayment();
            }
        });

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(nameLabel)
                .addComponent(destinationLabel)
                .addComponent(scheduleLabel)
                .addComponent(hotelLabel)
                .addComponent(proceedToPaymentButton));
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(nameField)
                .addComponent(destinationField)
                .addComponent(scheduleField)
                .addComponent(hotelComboBox));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(nameLabel)
                .addComponent(nameField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(destinationLabel)
                .addComponent(destinationField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(scheduleLabel)
                .addComponent(scheduleField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(hotelLabel)
                .addComponent(hotelComboBox));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(proceedToPaymentButton));
        layout.setVerticalGroup(vGroup);

        add(panel);
    }

    private void proceedToPayment() {
        String customerName = nameField.getText();
        String destination = destinationField.getText();
        String schedule = scheduleField.getText();
        String selectedHotel = (String) hotelComboBox.getSelectedItem();

        int hotelId = hotelService.getHotelIdByName(selectedHotel);
        Booking booking = bookingService.createBooking(customerName, hotelId, schedule);

        if (booking != null) {
            PaymentGUI paymentGUI = new PaymentGUI(bookingService, this, booking);
            paymentGUI.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to create booking. Please check your input.");
        }
    }

    public void paymentConfirmed(double amount) {
        System.out.println("Payment confirmed for booking. Amount: " + amount);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BookingService bookingService = new BookingService();
                HotelService hotelService = new HotelService();

                hotelService.insertHotelsFromCSV();

                BookingGUI bookingGUI = new BookingGUI(bookingService, hotelService);
                bookingGUI.setVisible(true);
            }
        });
    }
}
