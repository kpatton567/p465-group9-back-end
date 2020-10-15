package com.group9.prevue.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import com.group9.prevue.model.Movie;
import com.group9.prevue.model.Showtime;
import com.group9.prevue.repository.MovieRepository;
import com.group9.prevue.repository.ShowtimeRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/home")
public class HomeController {

	@Autowired
	private ShowtimeRepository showtimeRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@PostMapping("/theater_showtimes")
	public List<Showtime> getShowtimesAtTheater(@RequestParam Long theaterId) {
		return showtimeRepository.findByTheaterId(theaterId);
	}
	
	@PostMapping
	public List<Showtime> getShowtimesForMovie(@RequestParam Long theaterId, @RequestParam Long movieId){
		return showtimeRepository.findByTheaterIdAndMovieId(theaterId, movieId);
	}
	
	@GetMapping("/movies")
	public List<Movie> getAllMovies(){
		return movieRepository.findAll();
	}
}
