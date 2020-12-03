package com.group9.prevue.utility;

import java.util.Comparator;

import com.group9.prevue.model.Movie;

public class MovieTitleComparator implements Comparator<Movie>{

	public int compare(Movie m1, Movie m2) {
		return m1.getTitle().compareToIgnoreCase(m2.getTitle());
	}
}
