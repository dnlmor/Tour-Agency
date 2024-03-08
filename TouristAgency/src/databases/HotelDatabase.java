package databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import datamodel.Hotel;
import service.HotelService;

public class HotelDatabase {

    public static void insertHotel(Hotel hotel) {
        try (Connection connection = DBConnector.connect()) {
            String query = "INSERT INTO hotel (hotelName, hotelAddress, hotelRate, hotelPrice, roomAvailable) " +
                    "VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, hotel.getHotelName());
                preparedStatement.setString(2, hotel.getHotelAddress());
                preparedStatement.setDouble(3, hotel.getHotelRate());
                preparedStatement.setDouble(4, hotel.getHotelPrice());
                preparedStatement.setInt(5, hotel.getRoomAvailable());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void displayDestinationsWithHighCost() {
        try (Connection connection = DBConnector.connect()) {
            String query = "SELECT * FROM hotel WHERE hotelPrice > 500";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int hotelId = resultSet.getInt("hotelId");
                    String hotelName = resultSet.getString("hotelName");
                    String hotelAddress = resultSet.getString("hotelAddress");
                    double hotelRate = resultSet.getDouble("hotelRate");
                    double hotelPrice = resultSet.getDouble("hotelPrice");
                    int roomAvailable = resultSet.getInt("roomAvailable");

                    System.out.println("Hotel ID: " + hotelId);
                    System.out.println("Hotel Name: " + hotelName);
                    System.out.println("Hotel Address: " + hotelAddress);
                    System.out.println("Hotel Rate: " + hotelRate);
                    System.out.println("Hotel Price: " + hotelPrice);
                    System.out.println("Room Available: " + roomAvailable);
                    System.out.println("----------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
