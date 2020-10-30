package com.group9.prevue.model;

import javax.persistence.*;

import org.springframework.data.util.Pair;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "showtimes", uniqueConstraints = {@UniqueConstraint(columnNames= {"theater_id", "movie_id", "showtime"})})
public class Showtime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Theater theater;
	@ManyToOne
	private Movie movie;
	
	private Date showtime;
	private Double price;
	private Integer capacity;
	
	public Showtime() { }
	
	public Showtime(Theater theater, Movie movie, Date showtime, Double price, Integer capacity) {
		this.theater = theater;
		this.movie = movie;
		this.showtime = showtime;
		this.price = price;
		this.capacity = capacity;
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

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Date getShowtime() {
		return showtime;
	}

	public void setShowtime(Date showtime) {
		this.showtime = showtime;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}
