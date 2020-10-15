package com.group9.prevue.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "showtimes")
public class Showtime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@ForeignKey
	private Long theaterId;
	//@ForeignKey
	private Long movieId;
	
	private Date showtime;
	
	public Showtime() { }
	
	public Showtime(Long theaterId, Long movieId, Date showtime) {
		this.theaterId = theaterId;
		this.movieId = movieId;
		this.showtime = showtime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
	
	
}
