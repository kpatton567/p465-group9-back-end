package com.group9.prevue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group9.prevue.model.Showtime;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long>{

	List<Showtime> findByTheaterId(Long theaterId);
	List<Showtime> findByTheaterIdAndMovieId(Long theaterId, Long movieId);
}
