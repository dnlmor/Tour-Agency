package service;

import datamodel.Hotel;
import databases.HotelDatabase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HotelService {

    private final String hotelFilePath;

    public HotelService() {
        this.hotelFilePath = "/Users/dnlmor/Downloads/school docs/semester 3/java development/TouristAgency/src/hotel.csv";
    }

    public List<Hotel> readHotelsCSV() {
        List<Hotel> hotelList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(hotelFilePath))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                if (count != 0) {
                    Hotel hotel = parseHotel(line);
                    if (hotel != null) {
                        hotelList.add(hotel);
                    } else {
                        System.err.println("Error parsing hotel: " + line);
                    }
                }
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return hotelList;
    }

    public void insertHotelsFromCSV() {
        List<Hotel> hotels = readHotelsCSV();
        for (Hotel hotel : hotels) {
            HotelDatabase.insertHotel(hotel);
        }
    }

    public List<Hotel> getHotels() {
        return readHotelsCSV();
    }

    public int getHotelIdByName(String selectedHotel) {
        List<Hotel> hotels = readHotelsCSV();
        for (Hotel hotel : hotels) {
            if (hotel.getHotelName().equals(selectedHotel)) {
                return hotel.getHotelId();
            }
        }
        return -1; // Return -1 if the hotel is not found
    }

    private Hotel parseHotel(String line) {
        try {
            String[] data = line.split(",");
            if (data.length < 6) {
                System.err.println("Error parsing hotel: " + line);
                return null;
            }
            Hotel hotel = new Hotel();
            hotel.setHotelName(data[1].trim());
            hotel.setHotelAddress(data[2].trim());
            hotel.setHotelRate(Double.parseDouble(data[3].trim()));
            hotel.setHotelPrice(Double.parseDouble(data[4].trim()));
            hotel.setRoomAvailable(Integer.parseInt(data[5].trim()));
            return hotel;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }
}
