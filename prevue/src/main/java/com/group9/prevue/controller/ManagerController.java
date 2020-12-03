package com.group9.prevue.controller;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.text.DecimalFormat;

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
import org.springframework.web.bind.annotation.PathVariable;

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
	private SnackRepository snackRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	private DecimalFormat moneyFormat = new DecimalFormat("0.00");
	
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
		
		movie.setGenres(request.getGenre());
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
	
	@PostMapping("/refund_response")
	public ResponseEntity<?> respondToRefundRequest(@RequestHeader(name = "Authorization") String token, @RequestParam Long paymentNum, @RequestParam Boolean accepted) {
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User manager = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		if (manager.getRole() != ERole.ROLE_MANAGER)
			return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
		
		Payment payment = paymentRepository.findById(paymentNum).orElseThrow(() -> new RuntimeException("Error: Payment not found"));
		Theater theater = theaterRepository.findByManager(manager).orElseThrow(() -> new RuntimeException("Error: No theater with this manager"));
		if (theater.getId() != payment.getTheater().getId())
			return new ResponseEntity<String>("Manager does not control this theater", HttpStatus.FORBIDDEN);
		
		if (accepted) {
			payment.setStatus(EPaymentStatus.REFUNDED);
		} else {
			payment.setStatus(EPaymentStatus.FINAL);
		}
		
		paymentRepository.save(payment);
		return new ResponseEntity<String>("Response sent", HttpStatus.OK);
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
			if (payment.getCoupon() != null)
				total[0] *= (100 - payment.getCoupon().getPercentOff()) / 100.0;
			TheaterTransaction transaction = new TheaterTransaction(payment.getId(), payment.getTheater().getId(), total[0], ShowtimeInfo.dateString(payment.getPaymentDate()));
			transactions.add(transaction);
		});
		
		return new ResponseEntity<List<TheaterTransaction>>(transactions, HttpStatus.OK);
	}
	
	@GetMapping("refund_requests")
	public ResponseEntity<?> getRefundRequests(@RequestHeader(name = "Authorization") String token) {
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User manager = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		if (manager.getRole() != ERole.ROLE_MANAGER)
			return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
		
		Theater theater = theaterRepository.findByManager(manager).orElseThrow(() -> new RuntimeException("Error: No theater with this manager"));
		
		List<Payment> payments = paymentRepository.findByTheaterAndStatus(theater, EPaymentStatus.REQUESTED);
		List<TheaterTransaction> transactions = new ArrayList<TheaterTransaction>();
		
		int i = 0;
		while (i < payments.size()) {
			Payment payment = payments.get(i);
			if (payment.getShowtime().getShowtime().compareTo(new Date()) <= 0) {
				payment.setStatus(EPaymentStatus.FINAL);
				paymentRepository.save(payment);
				payments.remove(i);
			} else {
				double[] total = {0.0};
				payment.getSnacks().forEach(snack -> {
					total[0] += snack.getSnack().getPrice() * snack.getQuantity();
				});
				total[0] += payment.getShowtime().getPrice() * payment.getTicketCount();
				if (payment.getCoupon() != null)
					total[0] *= (100 - payment.getCoupon().getPercentOff()) / 100.0;
				TheaterTransaction transaction = new TheaterTransaction(payment.getId(), payment.getTheater().getId(), new Double(Long.parseLong(moneyFormat.format(total[0]))), ShowtimeInfo.dateString(payment.getPaymentDate()));
				transactions.add(transaction);
				i++;
			}
		}
		
		return new ResponseEntity<List<TheaterTransaction>>(transactions, HttpStatus.OK);
	}
}
