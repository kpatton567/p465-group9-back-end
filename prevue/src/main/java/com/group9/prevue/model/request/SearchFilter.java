package com.group9.prevue.model.request;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.group9.prevue.model.EPriceRange;

public class SearchFilter {

	public static final double LOW_PRICE = 4.99;
	public static final double MID_PRICE = 9.99;
	
	private Long theaterId;
	private String date;
	
	private EPriceRange price;
	
	public SearchFilter(Long theaterId, String date, EPriceRange price) {
		this.theaterId = theaterId;
		this.date = date;
		this.price = price;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public EPriceRange getPrice() {
		return this.price;
	}
	
	public void setPrice(EPriceRange price) {
		this.price = price;
	}

	public Long getTheaterId() {
		return theaterId;
	}

	public void setTheaterId(Long theaterId) {
		this.theaterId = theaterId;
	}
	
	
}
