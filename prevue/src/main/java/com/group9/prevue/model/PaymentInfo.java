package com.group9.prevue.model;

import javax.persistence.*;

import com.group9.prevue.model.User;

@Entity
@Table(name = "payment_info")
public class PaymentInfo {

	@Id
	private String number;
	private String expiration;
	private String cvv;
	
	private String name;
	private String zip;
	
	@ManyToOne
	private User user;
	
	public PaymentInfo() {}
	
	public PaymentInfo(String number, String expiration, String cvv, String name, String zip) {
		this.number = number;
		this.expiration = expiration;
		this.cvv = cvv;
		this.name = name;
		this.zip = zip;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getExpiration() {
		return expiration;
	}

	public void setExpiration(String expiration) {
		this.expiration = expiration;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
