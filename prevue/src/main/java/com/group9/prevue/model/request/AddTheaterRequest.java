package com.group9.prevue.model.request;

public class AddTheaterRequest {

	private String name;
	//private Long managerId;
	
	public AddTheaterRequest() {}
	
	public AddTheaterRequest(String name/*, Long managerId*/) {
		this.name = name;
		//this.managerId = managerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/*
	public Long getManagerId() {
		return managerId;
	}
	
	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
	 */
}
