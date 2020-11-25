package com.group9.prevue.controller;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.group9.prevue.model.request.PaymentRequest;
import com.group9.prevue.model.response.MessageResponse;
import com.group9.prevue.model.response.CustomerTransaction;
import com.group9.prevue.model.*;
import com.group9.prevue.repository.*;
import com.group9.prevue.utility.JwtUtils;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "http://localhost:5555", "https://prevuemovies.herokuapp.com"})
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private TheaterRepository theaterRepository;
	
	@Autowired
	private ShowtimeRepository showtimeRepository;
	
	@Autowired
	private SnackRepository snackRepository;
	
	@Autowired
	private PaymentInfoRepository paymentInfoRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping("customer_payment")
	public ResponseEntity<?> submitCustomerPayment(@RequestHeader(name = "Authorization") String token, @RequestBody PaymentRequest request){
		
		if(!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		Showtime showtime = showtimeRepository.findById(request.getShowtimeId()).orElseThrow(() -> new RuntimeException("Error: Invalid showtime"));
		if (showtime.getCapacity() - request.getTicketQuantity() < 0)
			return ResponseEntity.badRequest().body(new MessageResponse("Showtime does not have enough capacity"));
		
		User user = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		
		Payment payment = new Payment();
		payment.setTheater(theaterRepository.findById(request.getTheaterId()).orElseThrow(() -> new RuntimeException("Error: Theater not found")));
		payment.setMovie(movieRepository.findById(request.getTheaterId()).orElseThrow(() -> new RuntimeException("Error: Movie not found")));
		payment.setUser(user);
		payment.setPaymentDate(new Date());
		payment.setShowtime(showtime);
		payment.setTicketCount(request.getTicketQuantity());
		
		try {
			Coupon coupon = couponRepository.findByCodeAndExpirationDateGreaterThan(request.getCouponCode(), new Date()).orElseThrow(() -> new RuntimeException("Error: Coupon not found"));
			List<Coupon> userUsedCoupons = user.getUsedCoupons();
			if (userUsedCoupons.contains(coupon))
				throw new RuntimeException("Error: Coupon already used");
			payment.setCoupon(coupon);
			userUsedCoupons.add(coupon);
			user.setUsedCoupons(userUsedCoupons);
		} catch (RuntimeException e) {
			payment.setCoupon(null);
		}
		
		List<SnackQuantity> snacks = new ArrayList<>();
		
		for (int i = 0; i < request.getSnacks().size(); i++) {
			if (request.getSnacks().get(i) == null || request.getSnacks().get(i) == 0) {
				continue;
			}
			try {
				Snack newSnack = snackRepository.findById((long) i).orElseThrow(() -> new RuntimeException("Error: Snack not found"));
				snacks.add(new SnackQuantity(newSnack, request.getSnacks().get(i)));
			} catch (RuntimeException e) {
				// Don't add snack, just continue
			}
		}
		
		payment.setSnacks(snacks);
		
		paymentRepository.save(payment);
		
		showtime.setCapacity(showtime.getCapacity() - request.getTicketQuantity());
		showtimeRepository.save(showtime);
		
		return ResponseEntity.ok(new MessageResponse("Payment successful"));
	}
	
	@PostMapping("post_review")
	public ResponseEntity<?> postReview(@RequestHeader(name = "Authorization") String token, @RequestBody SimpleReview request) {
		if(!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User user = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		Movie movie = movieRepository.findById(request.getMovieId()).orElseThrow(() -> new RuntimeException("Error: Theater not found"));
		
		Review review = new Review(request.getStars(), request.getReview(), user, movie);
		reviewRepository.save(review);
		
		return ResponseEntity.ok(new MessageResponse("Review posted"));
	}
	
	/*
	// For if we decide to save user credit cards
	@GetMapping("payment_options")
	public List<PaymentInfo> getPaymentOptions(@RequestHeader(name = "Authorization") String token){
		
		// Validate token
		
		String userId = jwtUtils.getUserFromToken(token.substring(7));
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Error: User not found"));
		
		return paymentInfoRepository.findByUser(user);
	}
	*/
	
	@GetMapping("payment_history")
	public ResponseEntity<?> getPaymentHistory(@RequestHeader(name = "Authorization") String token){
		
		if(!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		String userId = jwtUtils.getUserFromToken(token.substring(7));
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Error: User not found"));
		
		List<Payment> payments = paymentRepository.findByUser(user);
		List<CustomerTransaction> transactions = new ArrayList<CustomerTransaction>();
		
		payments.forEach(payment -> {
			double total[] = {0.0};
			List<String> snacks = new ArrayList<>();
			payment.getSnacks().forEach(snack -> {
				total[0] += snack.getSnack().getPrice() * snack.getQuantity();
				snacks.add(snack.getSnack().getName());
			});
			
			total[0] += payment.getShowtime().getPrice() * payment.getTicketCount();
			if (payment.getCoupon() != null)
				total[0] *= (100 - payment.getCoupon().getPercentOff()) / 100.0;
			CustomerTransaction transaction = new CustomerTransaction(payment.getId(), ShowtimeInfo.dateString(payment.getPaymentDate()), payment.getMovie().getTitle(), payment.getTheater().getName(), payment.getPaymentInfo().getNumber().substring(12), snacks, total[0]);
			transactions.add(transaction);
		});
		
		return new ResponseEntity<List<CustomerTransaction>>(transactions, HttpStatus.OK);
	}
	
	@GetMapping("theater_manager/{theaterId}")
	public ResponseEntity<?> getTheaterManager(@RequestHeader(name = "Authorization") String token, @PathVariable Long theaterId) {
		
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		Theater theater = theaterRepository.findById(theaterId).orElseThrow(() -> new RuntimeException("Error: Theater not found"));
		User manager = theater.getManager();
		
		return new ResponseEntity<User>(manager, HttpStatus.OK);
	}
	
	@GetMapping("rewards")
	public ResponseEntity<?> getRewards(@RequestHeader(name = "Authorization") String token) {
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		List<Coupon> allValidCoupons = couponRepository.findByExpirationDateGreaterThan(new Date());
		User user = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		
		List<Coupon> response = new ArrayList<>();
		allValidCoupons.forEach(coupon -> {
			if (!(user.getUsedCoupons().contains(coupon)))
				response.add(coupon);
		});
		
		return new ResponseEntity<List<Coupon>>(response, HttpStatus.OK);
	}
	
	@GetMapping("coupon/{couponCode}")
	public ResponseEntity<?> getCoupon(@RequestHeader(name = "Authorization") String token, @PathVariable String couponCode) {
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User user = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		
		try {
			Coupon coupon = couponRepository.findByCodeAndExpirationDateGreaterThan(couponCode, new Date()).orElseThrow(() -> new RuntimeException("Error: Coupon not found"));
			if (!(user.getUsedCoupons().contains(coupon)))
				return new ResponseEntity<Coupon>(coupon, HttpStatus.OK);
			else
				return ResponseEntity.badRequest().body(new MessageResponse("Coupon already used"));
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Coupon not found or is expired"));
		}
	}
}
