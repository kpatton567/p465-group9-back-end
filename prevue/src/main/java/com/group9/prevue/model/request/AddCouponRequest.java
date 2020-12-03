package com.group9.prevue.model.request;

public class AddCouponRequest {

	private String code;
	private String description;
	private String date;
	private Double percentOff;
	private String imageLink;
	
	public AddCouponRequest(String code, String description, String date, Double percentOff, String imageLink) {
		this.code = code;
		this.description = description;
		this.date = date;
		this.percentOff = percentOff;
		this.imageLink = imageLink;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getPercentOff() {
		return percentOff;
	}

	public void setPercentOff(Double percentOff) {
		this.percentOff = percentOff;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	
	
}
