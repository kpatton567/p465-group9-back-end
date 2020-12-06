package com.group9.prevue.model.request;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class AdminPaymentRequest {

	private String userId;
	private Long theaterId;
	private Long movieId;
	private Long showtimeId;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date date;
	private String creditCardNumber;
	private String cardExpiration;
	private String cvv;
	private String name;
	private String zip;
	private String couponCode;
	private Integer ticketQuantity;
	private List<Integer> snacks;
	
	public Long getTheaterId() {
		return theaterId;
	}
	public void setTheaterId(Long theaterId) {
		this.theaterId = theaterId;
	}
	public Long getMovieId() {
		return movieId;
	}
	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}
	public Long getShowtimeId() {
		return showtimeId;
	}
	public void setShowtimeId(Long showtimeId) {
		this.showtimeId = showtimeId;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public String getCardExpiration() {
		return cardExpiration;
	}
	public void setCardExpiration(String cardExpiration) {
		this.cardExpiration = cardExpiration;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public Integer getTicketQuantity() {
		return ticketQuantity;
	}
	public void setTicketQuantity(Integer ticketQuantity) {
		this.ticketQuantity = ticketQuantity;
	}
	public List<Integer> getSnacks() {
		return snacks;
	}
	public void setSnacks(List<Integer> snacks) {
		this.snacks = snacks;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
