package com.group9.prevue.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "coupons", uniqueConstraints = {@UniqueConstraint(columnNames = "code")})
public class Coupon {

	@Id
	private String code;
	
	private String description;
	private String imageLink;
	private Double percentOff;
	
	private Date expirationDate;
	
	public Coupon() {}
	
	public Coupon(String code, String description, String imageLink, Double percentOff, Date expirationDate) {
		this.code = code;
		this.description = description;
		this.imageLink = imageLink;
		this.percentOff = percentOff;
		this.expirationDate = expirationDate;
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
	public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	public Double getPercentOff() {
		return this.percentOff;
	}
	public void setPercentOff(Double percentOff) {
		this.percentOff = percentOff;
	}
	public Date getExpirationDate() {
		return this.expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	
}
