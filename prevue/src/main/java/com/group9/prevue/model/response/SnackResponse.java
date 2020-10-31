package com.group9.prevue.model.response;

public class SnackResponse {

	private Long snackId;
	private Long theaterId;
	private String name;
	private Double price;
	
	public SnackResponse(Long snackId, Long theaterId, String name, Double price) {
		this.snackId = snackId;
		this.theaterId = theaterId;
		this.name = name;
		this.price = price;
	}

	public Long getSnackId() {
		return snackId;
	}

	public void setSnackId(Long snackId) {
		this.snackId = snackId;
	}

	public Long getTheaterId() {
		return theaterId;
	}

	public void setTheaterId(Long theaterId) {
		this.theaterId = theaterId;
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
