package com.group9.prevue.controller;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.group9.prevue.model.*;
import com.group9.prevue.model.request.SearchFilter;
import com.group9.prevue.model.response.MovieShowtime;
import com.group9.prevue.model.response.SnackResponse;
import com.group9.prevue.repository.*;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "http://localhost:5555", "https://prevuemovies.herokuapp.com"})
@RestController
@RequestMapping("/api/home")
public class HomeController {

	@Autowired
	private ShowtimeRepository showtimeRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private TheaterRepository theaterRepository;
	
	@Autowired
	private SnackRepository snackRepository;
	
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
				showtimeInfo.add(new ShowtimeInfo(showtime.getId(), showtime.getShowtime(), showtime.getPrice()));
				showtimeMap.put(showtime.getTheater(), showtimeInfo);
			} else {
				List<ShowtimeInfo> showtimeInfo = new ArrayList<>();
				showtimeInfo.add(new ShowtimeInfo(showtime.getId(), showtime.getShowtime(), showtime.getPrice()));
				showtimeMap.put(showtime.getTheater(), showtimeInfo);
			}
		});
		
		showtimeMap.keySet().forEach(theater -> {
			movieShowtimes.add(new MovieShowtime(movieId, theater.getId(), theater.getName(), showtimeMap.get(theater)));
		});
		
		return movieShowtimes;
	}
	
	@PostMapping("/movie_theater_showtimes")
	public MovieShowtime getShowtimesForMovieAtTheater(@RequestParam Long theaterId, @RequestParam Long movieId){
		Theater theater = theaterRepository.findById(theaterId).orElseThrow(() -> new RuntimeException("Error: Theater not found"));
		Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Error: Movie not found"));
		List<Showtime> showtimes = showtimeRepository.findByTheaterAndMovie(theater, movie);
		List<ShowtimeInfo> showtimeInfos = new ArrayList<>();
		
		showtimes.forEach(showtime -> {
			showtimeInfos.add(new ShowtimeInfo(showtime.getId(), showtime.getShowtime(), showtime.getPrice()));
		});
		
		return new MovieShowtime(movieId, theaterId, theater.getName(), showtimeInfos);
	}
	
	@PostMapping("/search")
	public ResponseEntity<?> search(@RequestBody SearchFilter filter) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		List<Showtime> showtimeResults = new ArrayList<>();
		Theater theater = null;
		if (filter.getTheaterId() != null)
			theater = theaterRepository.findById(filter.getTheaterId()).orElseThrow(() -> new RuntimeException("Error: Theater not found"));
		
		if (filter.getDate() == null && (filter.getLowPrice() == null || !filter.getLowPrice()) && (filter.getMidPrice() == null || !filter.getMidPrice()) && (filter.getHighPrice() == null || !filter.getHighPrice()))
			return new ResponseEntity<List<Movie>>(movieRepository.findAll(), HttpStatus.OK);
		
		try {
			if (filter.getDate() != null) {
				Date begin = sdf.parse(filter.getDate());
				calendar.setTime(begin);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				Date end = sdf.parse(sdf.format(calendar.getTime()));
				List<Showtime> showtimesOnDate = theater != null ? showtimeRepository.findByShowtimeBetweenAndShowtimeNotAndTheater(begin,  end,  end,  theater) : showtimeRepository.findByShowtimeBetweenAndShowtimeNot(begin, end, end);
				
				if (filter.getLowPrice() == null)
					filter.setLowPrice(false);
				if (filter.getMidPrice() == null)
					filter.setMidPrice(false);
				if (filter.getHighPrice() == null)
					filter.setHighPrice(false);
				
				if (!filter.getLowPrice() && !filter.getMidPrice() && !filter.getHighPrice())
					return new ResponseEntity<List<Showtime>>(showtimesOnDate, HttpStatus.OK);
				
				if (filter.getLowPrice()) {
					showtimesOnDate.forEach(showtime -> {
						if (showtime.getPrice() <= SearchFilter.LOW_PRICE)
							showtimeResults.add(showtime);
					});
				}
				
				if (filter.getMidPrice()) {
					showtimesOnDate.forEach(showtime -> {
						if (showtime.getPrice() > SearchFilter.LOW_PRICE && showtime.getPrice() <= SearchFilter.MID_PRICE)
							showtimeResults.add(showtime);
					});
				}
				
				if (filter.getHighPrice()) {
					showtimesOnDate.forEach(showtime -> {
						if (showtime.getPrice() > SearchFilter.MID_PRICE)
							showtimeResults.add(showtime);
					});
				}
				
				System.out.println(showtimeResults.size());
				
				Set<Movie> movieResults = new HashSet<>();
				
				showtimeResults.forEach(showtime -> {
					movieResults.add(showtime.getMovie());
				});
				
				return new ResponseEntity<Set<Movie>>(movieResults, HttpStatus.OK);
			}
		} catch (ParseException e) {
			return new ResponseEntity<String>("Invalid date format", HttpStatus.BAD_REQUEST);
		}
		
		List<Showtime> allShowtimes = theater != null ? showtimeRepository.findByTheater(theater) : showtimeRepository.findAll();
		
		if (!filter.getLowPrice() && !filter.getMidPrice() && !filter.getHighPrice())
			return new ResponseEntity<List<Showtime>>(allShowtimes, HttpStatus.OK);
		
		if (filter.getLowPrice()) {
			allShowtimes.forEach(showtime -> {
				if (showtime.getPrice() <= SearchFilter.LOW_PRICE)
					showtimeResults.add(showtime);
			});
		}
		
		if (filter.getMidPrice()) {
			allShowtimes.forEach(showtime -> {
				if (showtime.getPrice() > SearchFilter.LOW_PRICE && showtime.getPrice() <= SearchFilter.MID_PRICE)
					showtimeResults.add(showtime);
			});
		}
		
		if (filter.getHighPrice()) {
			allShowtimes.forEach(showtime -> {
				if (showtime.getPrice() > SearchFilter.MID_PRICE)
					showtimeResults.add(showtime);
			});
		}
		
		Set<Movie> movieResults = new HashSet<>();
		
		showtimeResults.forEach(showtime -> {
			movieResults.add(showtime.getMovie());
		});
		
		return new ResponseEntity<Set<Movie>>(movieResults, HttpStatus.OK);
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
	
	@GetMapping("/snacks/{theaterId}")
	public List<SnackResponse> getSnacks(@PathVariable Long theaterId) {
		Theater theater = theaterRepository.findById(theaterId).orElseThrow(() -> new RuntimeException("Error: Theater not found"));
		List<Snack> snacks = snackRepository.findByTheater(theater);
		List<SnackResponse> response = new ArrayList<>();
		snacks.forEach(snack -> {
			response.add(new SnackResponse(snack.getId(), snack.getTheater().getId(), snack.getName(), snack.getPrice()));
		});
		
		return response;
	}
}
