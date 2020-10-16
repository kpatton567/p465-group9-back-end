package com.group9.prevue.model;

import javax.persistence.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "showtimes")
public class Showtime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Theater theater;
	@ManyToOne
	private Movie movie;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date showtime;
	
	public Showtime() { }
	
	public Showtime(Theater theater, Movie movie, Date showtime) {
		this.theater = theater;
		this.movie = movie;
		this.showtime = showtime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Theater getTheater() {
		return theater;
	}

	public void setTheater(Theater theater) {
		this.theater = theater;
	}
		

	public Movie getMovie() {
		return movie;
	}

	public void setMovieId(Movie movie) {
		this.movie = movie;
	}

	public Date getShowtime() {
		return showtime;
	}

	public void setShowtime(Date showtime) {
		this.showtime = showtime;
	}
	
	
}
