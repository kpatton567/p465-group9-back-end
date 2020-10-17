package com.group9.prevue.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "showtimes")
public class Showtimes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Theater theater;
	@ManyToOne
	private Movie movie;
	
	@ElementCollection
	private List<Date> showtimes;
	
	public Showtimes() { }
	
	public Showtimes(Theater theater, Movie movie) {
		this.theater = theater;
		this.movie = movie;
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

	public List<Date> getShowtimes() {
		return showtimes;
	}

	public void setShowtimes(List<Date> showtimes) {
		this.showtimes = showtimes;
	}
	
	
}
