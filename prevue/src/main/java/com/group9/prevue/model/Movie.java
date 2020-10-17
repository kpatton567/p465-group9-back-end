package com.group9.prevue.model;

import java.util.Set;
import java.util.HashSet;
import javax.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	private String description;
	private String posterLink;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "movie_genres",
				joinColumns = @JoinColumn(name = "movie_id"),
				inverseJoinColumns = @JoinColumn(name = "genre_id"))
	private Set<Genre> genres = new HashSet<>();
	
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

	public Set<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}
}
