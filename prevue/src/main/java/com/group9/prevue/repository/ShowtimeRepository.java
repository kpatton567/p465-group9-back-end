package com.group9.prevue.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group9.prevue.model.Movie;
import com.group9.prevue.model.Theater;
import com.group9.prevue.model.Showtime;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long>{

	List<Showtime> findByTheater(Theater theaterId);
	List<Showtime> findByMovie(Movie movieId);
	List<Showtime> findByTheaterAndMovie(Theater theaterId, Movie movieId);
	List<Showtime> findByShowtimeBetweenAndShowtimeNot(Date date1, Date date2, Date notDate);
	List<Showtime> findByShowtimeBetweenAndShowtimeNotAndTheater(Date date1, Date date2, Date notDate, Theater theater);
}
