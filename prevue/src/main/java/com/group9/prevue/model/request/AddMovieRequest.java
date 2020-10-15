package com.group9.prevue.model.request;

import java.util.Set;

public class AddMovieRequest {

	private String title;
	private String description;
	
	private Set<String> genre;
	
	public AddMovieRequest () { }
	
	public AddMovieRequest(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<String> getGenre() {
		return genre;
	}

	public void setGenre(Set<String> genre) {
		this.genre = genre;
	}
}
