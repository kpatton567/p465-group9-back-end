package com.group9.prevue.model;

public class SimpleReview {

	private Long movieId;
	private Integer stars;
	private String headline;
	private String review;
	
	public SimpleReview(Long movieId, Integer stars, String headline, String review) {
		this.movieId = movieId;
		this.stars = stars;
		this.headline = headline;
		this.review = review;
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
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

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}
	
}
