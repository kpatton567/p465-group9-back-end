package com.group9.prevue.controller;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.group9.prevue.model.*;
import com.group9.prevue.model.request.*;
import com.group9.prevue.model.response.*;
import com.group9.prevue.repository.*;
import com.group9.prevue.utility.JwtUtils;


@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "http://localhost:5555", "https://prevuemovies.herokuapp.com"})
@RestController
@RequestMapping("/api/manage")
public class ManagerController {

	@Autowired
	private ShowtimeRepository showtimeRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private TheaterRepository theaterRepository;
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Autowired
	private SnackRepository snackRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping("/add_showtime")
	public ResponseEntity<?> addShowtime(@RequestHeader(name = "Authorization") String token, @RequestBody AddShowtimeRequest request){
		
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User manager = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		if (manager.getRole() != ERole.ROLE_MANAGER)
			return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
		
		Movie movie = movieRepository.findById(request.getMovieId()).orElseThrow(() -> new RuntimeException("Error: Movie not found"));
		Theater theater = theaterRepository.findByManager(manager).orElseThrow(() -> new RuntimeException("Error: No theater with this manager"));
		
		try {
			showtimeRepository.save(new Showtime(theater, movie, request.getShowtime(), request.getPrice(), theater.getCapacity()));
		} catch(Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("This movie is already showing at this time"));
		}
		
		return ResponseEntity.ok(new MessageResponse("Showtime added successfully"));
	}
	
	@PostMapping("/add_movie")
	public ResponseEntity<?> addMovie(@RequestHeader(name = "Authorization") String token, @RequestBody AddMovieRequest request){
		
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User manager = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		if (manager.getRole() != ERole.ROLE_MANAGER)
			return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
		
		Movie movie = new Movie(request.getTitle(), request.getDescription(), request.getPosterLink());
		
		Set<String> strGenres = request.getGenre();
		Set<Genre> genres = new HashSet<>();
		
		if (strGenres != null) {
			strGenres.forEach(genre -> {
				switch (genre) {
				case "action":
					Genre actionGenre = genreRepository.findByGenre(EGenre.GENRE_ACTION).orElseThrow(() -> new RuntimeException("Error: Genre not found"));
					genres.add(actionGenre);
					break;
				case "adventure":
					Genre adventureGenre = genreRepository.findByGenre(EGenre.GENRE_ADVENTURE).orElseThrow(() -> new RuntimeException("Error: Genre not found"));
					genres.add(adventureGenre);
					break;
				case "thriller":
					Genre thrillerGenre = genreRepository.findByGenre(EGenre.GENRE_THRILLER).orElseThrow(() -> new RuntimeException("Error: Genre not found"));
					genres.add(thrillerGenre);
					break;
				case "horror":
					Genre horrorGenre = genreRepository.findByGenre(EGenre.GENRE_HORROR).orElseThrow(() -> new RuntimeException("Error: Genre not found"));
					genres.add(horrorGenre);
					break;
				case "psychological":
					Genre psychGenre = genreRepository.findByGenre(EGenre.GENRE_PSYCHOLOGICAL).orElseThrow(() -> new RuntimeException("Error: Genre not found"));
					genres.add(psychGenre);
					break;
				case "nonfiction":
					Genre nonfictionGenre = genreRepository.findByGenre(EGenre.GENRE_NONFICTION).orElseThrow(() -> new RuntimeException("Error: Genre not found"));
					genres.add(nonfictionGenre);
					break;
				case "biopic":
					Genre biopicGenre = genreRepository.findByGenre(EGenre.GENRE_BIOPIC).orElseThrow(() -> new RuntimeException("Error: Genre not found"));
					genres.add(biopicGenre);
					break;
				case "comedy":
					Genre comedyGenre = genreRepository.findByGenre(EGenre.GENRE_COMEDY).orElseThrow(() -> new RuntimeException("Error: Genre not found"));
					genres.add(comedyGenre);
					break;
				case "crime":
					Genre crimeGenre = genreRepository.findByGenre(EGenre.GENRE_CRIME).orElseThrow(() -> new RuntimeException("Error: Genre not found"));
					genres.add(crimeGenre);
					break;
				case "drama":
					Genre dramaGenre = genreRepository.findByGenre(EGenre.GENRE_DRAMA).orElseThrow(() -> new RuntimeException("Error: Genre not found"));
					genres.add(dramaGenre);
					break;
				case "romance":
					Genre romanceGenre = genreRepository.findByGenre(EGenre.GENRE_ROMANCE).orElseThrow(() -> new RuntimeException("Error: Genre not found"));
					genres.add(romanceGenre);
					break;
				case "scifi":
					Genre scifiGenre = genreRepository.findByGenre(EGenre.GENRE_SCIFI).orElseThrow(() -> new RuntimeException("Error: Genre not found"));
					genres.add(scifiGenre);
					break;
				}
			});
		}
		
		movie.setGenres(genres);
		movieRepository.save(movie);
		return ResponseEntity.ok(new MessageResponse("Movie added successfully"));
	}
	
	@PostMapping("/add_snack")
	public ResponseEntity<?> addSnack(@RequestHeader(name = "Authorization") String token, @RequestBody AddSnackRequest request) {
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User manager = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		if (manager.getRole() != ERole.ROLE_MANAGER)
			return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
		
		Theater theater = theaterRepository.findByManager(manager).orElseThrow(() -> new RuntimeException("Error: No theater with this manager"));
		Snack snack = new Snack(request.getName(), request.getPrice());
		snack.setTheater(theater);
		snackRepository.save(snack);
		
		return ResponseEntity.ok(new MessageResponse("Snack added successfully"));
	}
	
	@PostMapping("/delete_snack")
	public ResponseEntity<?> deleteSnack(@RequestHeader(name = "Authorization") String token, @RequestParam Long snackId) {
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User manager = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		if (manager.getRole() != ERole.ROLE_MANAGER)
			return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
		
		Snack snack = snackRepository.findById(snackId).orElseThrow(() -> new RuntimeException("Error: Snack not found"));
		if (!(manager.getUserId().equals(snack.getTheater().getManager().getUserId()))){
			return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
		}
		
		snackRepository.delete(snack);
		return ResponseEntity.ok(new MessageResponse("Snack added successfully"));
	}
	
	@GetMapping("transaction_history")
	public ResponseEntity<?> getTransactionHistory(@RequestHeader(name = "Authorization") String token){

		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User manager = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		if (manager.getRole() != ERole.ROLE_MANAGER)
			return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
		
		Theater theater = theaterRepository.findByManager(manager).orElseThrow(() -> new RuntimeException("Error: No theater with this manager"));
		List<Payment> payments = paymentRepository.findByTheater(theater);
		List<TheaterTransaction> transactions = new ArrayList<TheaterTransaction>();
		
		payments.forEach(payment -> {
			double[] total = {0.0};
			payment.getSnacks().forEach(snack -> {
				total[0] += snack.getSnack().getPrice() * snack.getQuantity();
			});
			
			total[0] += payment.getShowtime().getPrice() * payment.getTicketCount();
			TheaterTransaction transaction = new TheaterTransaction(payment.getId(), payment.getTheater().getId(), total[0], ShowtimeInfo.dateString(payment.getPaymentDate()));
			transactions.add(transaction);
		});
		
		return new ResponseEntity<List<TheaterTransaction>>(transactions, HttpStatus.OK);
	}
	
}
