package edu.tamu.istmhrs.data;

public class RecommendResult {

	String ApartmentName;
	String Address;
	String[][] recommendHouse = new String[10][2];

	RecommendResult(String apt, String add) {
		ApartmentName = apt;
		Address = add;
	}

	public String getApartmentName() {
		return ApartmentName;
	}

	public String getAddress() {
		return Address;
	}
}