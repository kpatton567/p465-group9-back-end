package com.group9.prevue.model.response;

import java.util.Date;
import java.util.List;

import com.group9.prevue.model.EPaymentStatus;

public class CustomerTransaction {

	private Long paymentId;
	private String date;
	private Long movieId;
	private String movieName;
	private Long theaterId;
	private String theaterName;
	private String last4Digits;
	private List<String> snacks;
	private Double total;
	private EPaymentStatus status;
	
	public CustomerTransaction(Long paymentId, String date, Long movieId, String movieName, Long theaterId, String theaterName, String last4Digits, List<String> snacks, Double total, EPaymentStatus status) {
		this.paymentId = paymentId;
		this.date = date;
		this.movieId = movieId;
		this.movieName = movieName;
		this.theaterId = theaterId;
		this.theaterName = theaterName;
		this.last4Digits = last4Digits;
		this.snacks = snacks;
		this.total = total;
		this.status = status;
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

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public Long getTheaterId() {
		return theaterId;
	}

	public void setTheaterId(Long theaterId) {
		this.theaterId = theaterId;
	}

	public EPaymentStatus getStatus() {
		return status;
	}

	public void setStatus(EPaymentStatus status) {
		this.status = status;
	}
	
}
