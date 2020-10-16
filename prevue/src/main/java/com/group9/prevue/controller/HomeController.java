package com.group9.prevue.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import com.group9.prevue.model.Movie;
import com.group9.prevue.model.Theater;
import com.group9.prevue.model.Showtime;
import com.group9.prevue.repository.MovieRepository;
import com.group9.prevue.repository.TheaterRepository;
import com.group9.prevue.repository.ShowtimeRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/home")
public class HomeController {

	@Autowired
	private ShowtimeRepository showtimeRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private TheaterRepository theaterRepository;
	
	@PostMapping("/theater_showtimes")
	public List<Showtime> getShowtimesAtTheater(@RequestParam Long theaterId) {
		return showtimeRepository.findByTheaterId(theaterId);
	}
	
	@PostMapping("/movie_showtimes")
	public List<Showtime> getShowtimesForMovieAtTheater(@RequestParam Long theaterId, @RequestParam Long movieId){
		return showtimeRepository.findByTheaterAndMovie(theaterId, movieId);
	}
	
	@GetMapping("/theaters")
	public List<Theater> getAllTheaters(){
		return theaterRepository.findAll();
	}
	
	@GetMapping("/theater/{theaterId}")
	public Theater getTheater(@PathVariable Long theaterId) {
		return theaterRepository.findById(theaterId).orElseThrow(() -> new RuntimeException("Error: Theater not found"));
	}
	
	@GetMapping("/movies")
	public List<Movie> getAllMovies(){
		return movieRepository.findAll();
	}
	
	@GetMapping("/movie/{movieId}")
	public Movie getMovie(@PathVariable Long movieId) {
		return movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Error: Movie not found"));
	}
}
