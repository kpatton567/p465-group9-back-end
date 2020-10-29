package com.group9.prevue.model.response;

import java.util.Date;

public class TheaterTransaction {

	private Long paymentId;
	private Long theaterId;
	private Double total;
	private Date date;
	
	public TheaterTransaction(Long paymentId, Long theaterId, Double total, Date date) {
		this.paymentId = paymentId;
		this.theaterId = theaterId;
		this.date = date;
		this.total = total;
	}
	
	public Long getPaymentId() {
		return paymentId;
	}
	
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	
	public Long getTheaterId() {
		return theaterId;
	}
	
	public void setTheaterId(Long theaterId) {
		this.theaterId = theaterId;
	}
	
	public Double getTotal() {
		return total;
	}
	
	public void setTotal(Double total) {
		this.total = total;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
}
