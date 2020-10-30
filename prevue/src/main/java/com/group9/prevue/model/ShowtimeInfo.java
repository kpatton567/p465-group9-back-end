package com.group9.prevue.model;

import java.util.Date;
import javax.persistence.Embeddable;

@Embeddable
public class ShowtimeInfo {

	private Date date;
	private Double price;
	
	public ShowtimeInfo() {}
	
	public ShowtimeInfo(Date date, Double price) {
		this.date = date;
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
