package com.group9.prevue.model.response;

import java.util.List;
import java.util.Date;

import com.group9.prevue.model.ShowtimeInfo;

public class MovieShowtime {

	private Long movieId;
	private Long theaterId;
	private String theaterName;
	private List<ShowtimeInfo> showtimes;
	
	public MovieShowtime(Long movieId, Long theaterId, String theaterName, List<ShowtimePrice> showtimes) {
		this.movieId = movieId;
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

	public List<ShowtimeInfo> getShowtimes() {
		return showtimes;
	}

	public void setShowtimes(List<ShowtimeInfo> showtimes) {
		this.showtimes = showtimes;
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}
}
