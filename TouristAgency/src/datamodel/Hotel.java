package datamodel;

public class Hotel {
	
	private int hotelId;
	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getHotelAddress() {
		return hotelAddress;
	}
	public void setHotelAddress(String hotelAddress) {
		this.hotelAddress = hotelAddress;
	}
	public double getHotelRate() {
		return hotelRate;
	}
	public void setHotelRate(double hotelRate) {
		this.hotelRate = hotelRate;
	}
	public double getHotelPrice() {
		return hotelPrice;
	}
	public void setHotelPrice(double hotelPrice) {
		this.hotelPrice = hotelPrice;
	}
	public int getRoomAvailable() {
		return roomAvailable;
	}
	public void setRoomAvailable(int roomAvailable) {
		this.roomAvailable = roomAvailable;
	}
	private String hotelName;
	private String hotelAddress;
	private double hotelRate;
	private double hotelPrice;
	private int roomAvailable;

}
