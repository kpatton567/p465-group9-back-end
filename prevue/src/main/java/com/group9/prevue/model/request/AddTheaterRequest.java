package com.group9.prevue.model.request;

public class AddTheaterRequest {

	private String name;
	private String managerId;
	private Integer capacity;
	private String address;
	private Double latitude;
	private Double longitude;
	
	public AddTheaterRequest() {}
	
	public AddTheaterRequest(String name, String managerId, Integer capacity, String address, Double latitude, Double longitude) {
		this.name = name;
		this.managerId = managerId;
		this.capacity = capacity;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getManagerId() {
		return managerId;
	}
	
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
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
