package com.group9.prevue.model.request;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class SearchFilter {

	public static final double LOW_PRICE = 4.99;
	public static final double MID_PRICE = 9.99;
	
	private Long theaterId;
	private String date;
	
	private Boolean lowPrice;
	private Boolean midPrice;
	private Boolean highPrice;
	
	public SearchFilter(Long theaterId, String date, Boolean lowPrice, Boolean midPrice, Boolean highPrice) {
		this.theaterId = theaterId;
		this.date = date;
		this.lowPrice = lowPrice;
		this.midPrice = midPrice;
		this.highPrice = highPrice;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Boolean getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(Boolean lowPrice) {
		this.lowPrice = lowPrice;
	}

	public Boolean getMidPrice() {
		return midPrice;
	}

	public void setMidPrice(Boolean midPrice) {
		this.midPrice = midPrice;
	}

	public Boolean getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(Boolean highPrice) {
		this.highPrice = highPrice;
	}

	public Long getTheaterId() {
		return theaterId;
	}

	public void setTheaterId(Long theaterId) {
		this.theaterId = theaterId;
	}
	
	
}
