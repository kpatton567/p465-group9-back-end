package com.group9.prevue.model.request;

public class AddSnackRequest {

	private String name;
	private Double price;
	
	public AddSnackRequest() {}
	
	public AddSnackRequest(String name, Double price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
