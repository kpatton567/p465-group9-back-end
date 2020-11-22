package com.group9.prevue.model;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer stars;
	
	@Column(length = 1000)
	private String review;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Movie movie;
	
	public Review() {}
	
	public Review(Integer stars, String review, User user, Movie movie) {
		this.stars = stars;
		this.review = review;
		this.user = user;
		this.movie = movie;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
}
