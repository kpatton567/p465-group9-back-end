package com.group9.prevue.model;

import java.util.Date;
import javax.persistence.Embeddable;

@Embeddable
public class ShowtimeInfo {

	private Long showtimeId;
	private String date;
	private Double price;
	
	public ShowtimeInfo() {}
	
	public ShowtimeInfo(Long showtimeId, Date date, Double price) {
		this.showtimeId = showtimeId;
		this.date = dateString(date);
		this.price = price;
	}

	public String getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = dateString(date);
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getShowtimeId() {
		return showtimeId;
	}

	public void setShowtimeId(Long showtimeId) {
		this.showtimeId = showtimeId;
	}
	
	public static String dateString(Date date) {
		String baseDate = date.toString();
		System.out.println(baseDate);
		String month = baseDate.substring(5, 7);
		String day = baseDate.substring(8, 10);
		String year = baseDate.substring(2, 4);
		int hourNum = Integer.parseInt(baseDate.substring(11, 13));
		String postfix = hourNum >= 12 ? "pm" : "am";
		String hour = hourNum == 0 || hourNum == 12 ? "12" : "" + (hourNum % 12);
		String minutes = baseDate.substring(14, 16);
		
		return month + "-" + day + "-" + year + " " + hour + ":" + minutes + postfix;
	}
}
