package com.group9.prevue.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

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
import com.group9.prevue.model.ShowtimeInfo;
import com.group9.prevue.model.response.MovieShowtime;
import com.group9.prevue.repository.MovieRepository;
import com.group9.prevue.repository.TheaterRepository;
import com.group9.prevue.repository.ShowtimeRepository;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "http://localhost:5555"})
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
		Theater theater = theaterRepository.findById(theaterId).orElseThrow(() -> new RuntimeException("Error: Theater not found"));
		return showtimeRepository.findByTheater(theater);
	}
	
	@PostMapping("/movie_showtimes")
	public List<MovieShowtime> getMovieShowtimes(@RequestParam Long movieId) {
		Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Error: Movie not found"));
		List<Showtime> showtimes = showtimeRepository.findByMovie(movie);
		List<MovieShowtime> movieShowtimes = new ArrayList<MovieShowtime>();
		
		Map<Theater, List<ShowtimeInfo>> showtimeMap = new HashMap<>();
		
		showtimes.forEach(showtime -> {
			if(showtimeMap.containsKey(showtime.getTheater())) {
				List<ShowtimeInfo> showtimeInfo = showtimeMap.get(showtime.getTheater());
				showtimeInfo.add(new ShowtimeInfo(showtime.getShowtime(), showtime.getPrice()));
				showtimeMap.put(showtime.getTheater(), showtimeInfo);
			} else {
				List<ShowtimeInfo> showtimeInfo = new ArrayList<>();
				showtimeInfo.add(new ShowtimeInfo(showtime.getShowtime(), showtime.getPrice()));
				showtimeMap.put(showtime.getTheater(), showtimeInfo);
			}
		});
		
		showtimeMap.keySet().forEach(theater -> {
			movieShowtimes.add(new MovieShowtime(theater.getId(), theater.getName(), showtimeMap.get(theater)));
		});
		
		return movieShowtimes;
	}
	
	@PostMapping("/movie_theater_showtimes")
	public List<Showtime> getShowtimesForMovieAtTheater(@RequestParam Long theaterId, @RequestParam Long movieId){
		Theater theater = theaterRepository.findById(theaterId).orElseThrow(() -> new RuntimeException("Error: Theater not found"));
		Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Error: Movie not found"));
		return showtimeRepository.findByTheaterAndMovie(theater, movie);
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
