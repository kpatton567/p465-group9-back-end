package com.group9.prevue.model.request;

public class AddTheaterRequest {

	private String name;
	private String managerId;
	private Integer capacity;
	
	public AddTheaterRequest() {}
	
	public AddTheaterRequest(String name, String managerId, Integer capacity) {
		this.name = name;
		this.managerId = managerId;
		this.capacity = capacity;
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
}
