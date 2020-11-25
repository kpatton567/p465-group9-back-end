package com.group9.prevue.model.response;

import java.util.Date;
import java.util.List;

public class CustomerTransaction {

	private Long paymentId;
	private String date;
	private String movieName;
	private String theaterName;
	private String last4Digits;
	private List<String> snacks;
	private Double total;
	
	public CustomerTransaction(Long paymentId, String date, String movieName, String theaterName, String last4Digits, List<String> snacks, Double total) {
		this.paymentId = paymentId;
		this.date = date;
		this.movieName = movieName;
		this.theaterName = theaterName;
		this.last4Digits = last4Digits;
		this.snacks = snacks;
		this.total = total;
	}
	
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getTheaterName() {
		return theaterName;
	}
	public void setTheaterName(String theaterName) {
		this.theaterName = theaterName;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public List<String> getSnacks() {
		return snacks;
	}
	public void setSnacks(List<String> snacks) {
		this.snacks = snacks;
	}

	public String getLast4Digits() {
		return last4Digits;
	}

	public void setLast4Digits(String last4Digits) {
		this.last4Digits = last4Digits;
	}
	
}
