package com.group9.prevue.model.response;

import java.util.List;
import java.util.Date;

import com.group9.prevue.model.ShowtimePrice;

public class MovieShowtime {

	private Long theaterId;
	private String theaterName;
	private List<ShowtimePrice> showtimes;
	
	public MovieShowtime(Long theaterId, String theaterName, List<ShowtimePrice> showtimes) {
		this.theaterId = theaterId;
		this.theaterName = theaterName;
		this.showtimes = showtimes;
	}

	public Long getTheaterId() {
		return theaterId;
	}

	public void setTheaterId(Long theaterId) {
		this.theaterId = theaterId;
	}

	public String getTheaterName() {
		return theaterName;
	}

	public void setTheaterName(String theaterName) {
		this.theaterName = theaterName;
	}

	public List<ShowtimePrice> getShowtimes() {
		return showtimes;
	}

	public void setShowtimes(List<ShowtimePrice> showtimes) {
		this.showtimes = showtimes;
	}
}
