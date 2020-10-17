package com.group9.prevue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group9.prevue.model.Movie;
import com.group9.prevue.model.Theater;
import com.group9.prevue.model.Showtimes;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtimes, Long>{

	List<Showtimes> findByTheater(Theater theaterId);
	List<Showtimes> findByMovie(Movie movieId);
	Showtimes findByTheaterAndMovie(Theater theaterId, Movie movieId);
	boolean existsByTheaterAndMovie(Theater theaterId, Movie movieId);
}
