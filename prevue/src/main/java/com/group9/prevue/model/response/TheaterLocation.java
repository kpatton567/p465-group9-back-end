package com.group9.prevue.model.response;

public class TheaterLocation {

	private String address;
	private Double latitude;
	private Double longitude;
	
	public TheaterLocation(String address, Double latitude, Double longitude) {
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	
}
