package com.group9.prevue.model.request;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class AddShowtimeRequest {

	private Long theaterId;
	private Long movieId;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date showtime;
	
	private Double price;

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

	public Date getShowtime() {
		return showtime;
	}

	public void setShowtime(Date showtime) {
		this.showtime = showtime;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}
