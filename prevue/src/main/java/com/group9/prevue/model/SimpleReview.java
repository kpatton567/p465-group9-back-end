package com.group9.prevue.model;

public class SimpleReview {

	private Long theaterId;
	private Integer stars;
	private String review;
	
	public SimpleReview(Long theaterId, Integer stars, String review) {
		this.theaterId = theaterId;
		this.stars = stars;
		this.review = review;
	}

	public Long getTheaterId() {
		return theaterId;
	}

	public void setTheaterId(Long theaterId) {
		this.theaterId = theaterId;
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
	
}
