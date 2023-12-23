package monopoly.objects;

import monopoly.data.DataManager;

public enum Property {
	DEEP_BLUE("deep_blue"),
	GREEN("green"),
	ORANGE("orange"),
	LIGHT_BLUE("light_blue"),
	PINK("pink"),
	YELLOW("yellow"),
	BROWN("brown"),
	RED("red");
	
	private int basicRent;
	private int houseRent;
	private int hotelRent;
	private int buyingCost;
	private int houseCost;
	private int hotelCost;
	
	private Property(String name) {
		basicRent = getIntegerProperty(name + "_basicRent");
		houseRent = getIntegerProperty(name + "_houseRent");
		hotelRent = getIntegerProperty(name + "_hotelRent");
		buyingCost = getIntegerProperty(name + "_basicRent");
		houseCost = getIntegerProperty(name + "_basicRent");
		hotelCost = getIntegerProperty(name + "_basicRent");
	}
	
	public int getBasicRent() {
		return basicRent;
	}
	public void setBasicRent(int basicRent) {
		this.basicRent = basicRent;
	}
	public int getHouseRent() {
		return houseRent;
	}
	public void setHouseRent(int houseRent) {
		this.houseRent = houseRent;
	}
	public int getHotelRent() {
		return hotelRent;
	}
	public void setHotelRent(int hotelRent) {
		this.hotelRent = hotelRent;
	}
	public int getBuyingCost() {
		return buyingCost;
	}
	public void setBuyingCost(int buyingCost) {
		this.buyingCost = buyingCost;
	}
	public int getHouseCost() {
		return houseCost;
	}
	public void setHouseCost(int houseCost) {
		this.houseCost = houseCost;
	}
	public int getHotelCost() {
		return hotelCost;
	}
	public void setHotelCost(int hotelCost) {
		this.hotelCost = hotelCost;
	}
	
	protected static int getIntegerProperty(String integerProp) {
		return Integer.parseInt(DataManager.getInitializer().getProperty(integerProp));
	}
}
