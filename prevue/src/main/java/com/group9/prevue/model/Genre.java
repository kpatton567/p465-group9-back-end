package com.group9.prevue.model;

import javax.persistence.*;

@Entity
@Table(name = "genres")
public class Genre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private EGenre genre;
	
	public Genre () {}
	
	public Genre(EGenre genre) {
		this.genre = genre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EGenre getGenre() {
		return genre;
	}

	public void setGenre(EGenre genre) {
		this.genre = genre;
	}
	
	
}
