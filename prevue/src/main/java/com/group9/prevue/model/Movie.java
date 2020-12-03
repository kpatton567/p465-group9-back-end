package com.group9.prevue.model;

import java.util.Set;
import java.util.HashSet;
import javax.persistence.*;

@Entity
@Table(name = "movies", uniqueConstraints = {@UniqueConstraint(columnNames = "title")})
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	private String description;
	private String posterLink;
	
	@ElementCollection
	private Set<EGenre> genres = new HashSet<>();
	
	public Movie() { }
	
	public Movie(String title, String description, String posterLink) {
		this.title = title;
		this.description = description;
		this.posterLink = posterLink;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Set<EGenre> getGenres() {
		return genres;
	}

	public void setGenres(Set<EGenre> genres) {
		this.genres = genres;
	}

	public String getPosterLink() {
		return posterLink;
	}

	public void setPosterLink(String posterLink) {
		this.posterLink = posterLink;
	}
	
	public int compareTo(Movie otherMovie) {
		return title.compareToIgnoreCase(otherMovie.title);
	}
}
