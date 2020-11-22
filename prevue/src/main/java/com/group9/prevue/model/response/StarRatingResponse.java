package com.group9.prevue.model.response;

public class StarRatingResponse {

	public Double averageRating;
	public Integer reviewCount;
	
	public StarRatingResponse(Double average, Integer count) {
		this.averageRating = average;
		this.reviewCount = count;
	}

	public Double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}

	public Integer getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(Integer reviewCount) {
		this.reviewCount = reviewCount;
	}
	
	
}
