package com.group9.prevue.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;

import com.group9.prevue.model.*;
import com.group9.prevue.model.request.AddCouponRequest;
import com.group9.prevue.model.request.AddTheaterRequest;
import com.group9.prevue.model.request.AdminPaymentRequest;
import com.group9.prevue.model.response.MessageResponse;
import com.group9.prevue.repository.*;
import com.group9.prevue.utility.JwtUtils;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "http://localhost:5555", "https://prevuemovies.herokuapp.com"})
@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private TheaterRepository theaterRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private ShowtimeRepository showtimeRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private SnackRepository snackRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping("add_theater")
	public ResponseEntity<?> addTheater(@RequestBody AddTheaterRequest request){
		
		Theater theater = new Theater(request.getName(), request.getCapacity(), request.getAddress(), request.getLatitude(), request.getLongitude());
		theater.setManager(userRepository.findById(request.getManagerId()).orElseThrow(() -> new RuntimeException("Error: User not found")));
		
		theaterRepository.save(theater);
		return ResponseEntity.ok(new MessageResponse("Theater added successfully"));
	}
	
	@PostMapping("add_coupon")
	public ResponseEntity<?> addCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody AddCouponRequest request) {
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User admin = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		if (admin.getRole() != ERole.ROLE_ADMIN)
			return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
		
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		
		try {
			Coupon coupon = new Coupon(request.getCode().toUpperCase(), request.getDescription(), request.getImageLink(), request.getPercentOff(), format.parse(request.getDate()));
			couponRepository.save(coupon);
		} catch (ParseException e) {
			return new ResponseEntity<String>("Improperly formatted date", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>("Coupon added successfully", HttpStatus.OK);
	}
	
	@PostMapping("delete_movie/{movieId}")
	public ResponseEntity<?> deleteMovie(@RequestHeader(name = "Authorization") String token, @PathVariable Long movieId) {
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User admin = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		if (admin.getRole() != ERole.ROLE_ADMIN)
			return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
		
		Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Error: Movie not found"));
		
		movieRepository.delete(movie);
		
		return new ResponseEntity<String>("Movie deleted successfully", HttpStatus.OK);
	}
	
	@PostMapping("delete_theater/{theaterId}")
	public ResponseEntity<?> deleteTheater(@RequestHeader(name = "Authorization") String token, @PathVariable Long theaterId) {
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User admin = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		if (admin.getRole() != ERole.ROLE_ADMIN)
			return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
		
		Theater theater = theaterRepository.findById(theaterId).orElseThrow(() -> new RuntimeException("Error: Theater not found"));
		
		theaterRepository.delete(theater);
		
		return new ResponseEntity<String>("Theater deleted successfully", HttpStatus.OK);
	}
	
	@PostMapping("delete_showtime/{showtimeId}")
	public ResponseEntity<?> deleteShowtime(@RequestHeader(name = "Authorization") String token, @PathVariable Long showtimeId) {
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User admin = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		if (admin.getRole() != ERole.ROLE_ADMIN)
			return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
		
		Showtime showtime = showtimeRepository.findById(showtimeId).orElseThrow(() -> new RuntimeException("Error: Showtime not found"));
		
		showtimeRepository.delete(showtime);
		
		return new ResponseEntity<String>("Showtime deleted successfully", HttpStatus.OK);
	}
	
	@PostMapping("delete_snack/{snackId}")
	public ResponseEntity<?> deleteSnack(@RequestHeader(name = "Authorization") String token, @PathVariable Long snackId) {
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User admin = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		if (admin.getRole() != ERole.ROLE_ADMIN)
			return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
		
		Snack snack = snackRepository.findById(snackId).orElseThrow(() -> new RuntimeException("Error: Snack not found"));
		
		snackRepository.delete(snack);
		
		return new ResponseEntity<String>("Snack deleted successfully", HttpStatus.OK);
	}
	
	@PostMapping("delete_user/{userId}")
	public ResponseEntity<?> deleteUser(@RequestHeader(name = "Authorization") String token, @PathVariable String userId) {
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User admin = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		if (admin.getRole() != ERole.ROLE_ADMIN)
			return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
		
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Error: User not found"));
		
		userRepository.delete(user);
		
		return new ResponseEntity<String>("User deleted successfully", HttpStatus.OK);
	}
	
	@PostMapping("delete_payment/{paymentId}")
	public ResponseEntity<?> deletePayment(@RequestHeader(name = "Authorization") String token, @PathVariable Long paymentId) {
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User admin = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		if (admin.getRole() != ERole.ROLE_ADMIN)
			return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
		
		Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new RuntimeException("Error: Payment not found"));
		
		paymentRepository.delete(payment);
		
		return new ResponseEntity<String>("Payment deleted successfully", HttpStatus.OK);
	}
	
	@PostMapping("delete_review/{reviewId}")
	public ResponseEntity<?> deleteReview(@RequestHeader(name = "Authorization") String token, @PathVariable Long reviewId) {
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User admin = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		if (admin.getRole() != ERole.ROLE_ADMIN)
			return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
		
		Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Error: Review not found"));
		
		reviewRepository.delete(review);
		
		return new ResponseEntity<String>("Review deleted successfully", HttpStatus.OK);
	}
	
	@PostMapping("delete_profile/{userId}")
	public ResponseEntity<?> deleteProfile(@RequestHeader(name = "Authorization") String token, @PathVariable String userId) {
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User admin = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		if (admin.getRole() != ERole.ROLE_ADMIN)
			return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
		
		UserProfile profile = profileRepository.findById(userId).orElseThrow(() -> new RuntimeException("Error: Profile not found"));
		
		profileRepository.delete(profile);
		
		return new ResponseEntity<String>("Profile deleted successfully", HttpStatus.OK);
	}
	
	@PostMapping("add_payment")
	public ResponseEntity<?> addPayment(@RequestHeader(name = "Authorization") String token, @RequestBody AdminPaymentRequest request) {
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User admin = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		if (admin.getRole() != ERole.ROLE_ADMIN)
			return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
		
		User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("Error: User not found"));
		Theater theater = theaterRepository.findById(request.getTheaterId()).orElseThrow(() -> new RuntimeException("Error: Theater not found"));
		Movie movie = movieRepository.findById(request.getMovieId()).orElseThrow(() -> new RuntimeException("Error: Movie not found"));
		Showtime showtime = showtimeRepository.findById(request.getShowtimeId()).orElseThrow(() -> new RuntimeException("Error: Showtime not found"));
		
		Payment payment = new Payment();
		payment.setTheater(theater);
		payment.setMovie(movie);
		payment.setUser(user);
		payment.setPaymentDate(request.getDate());
		payment.setShowtime(showtime);
		payment.setTicketCount(request.getTicketQuantity());
		payment.setStatus(EPaymentStatus.REFUNDABLE);
		
		PaymentInfo paymentInfo = new PaymentInfo(request.getCreditCardNumber(), request.getCardExpiration(), request.getCvv(), request.getName(), request.getZip());
		payment.setPaymentInfo(paymentInfo);
		
		try {
			Coupon coupon = couponRepository.findByCodeAndExpirationDateGreaterThan(request.getCouponCode().toUpperCase(), new Date()).orElseThrow(() -> new RuntimeException("Error: Coupon not found"));
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

		return ResponseEntity.ok(new MessageResponse("Payment successful"));

	}
	
	@GetMapping("all_payments")
	public ResponseEntity<?> allPayments(@RequestHeader(name = "Authorization") String token) {
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User admin = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		if (admin.getRole() != ERole.ROLE_ADMIN)
			return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
		
		return new ResponseEntity<List<Payment>>(paymentRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("all_reviews")
	public ResponseEntity<?> allReviews(@RequestHeader(name = "Authorization") String token) {
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User admin = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		if (admin.getRole() != ERole.ROLE_ADMIN)
			return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
		
		return new ResponseEntity<List<Review>>(reviewRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("all_snacks")
	public ResponseEntity<?> allSnacks(@RequestHeader(name = "Authorization") String token) {
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User admin = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		if (admin.getRole() != ERole.ROLE_ADMIN)
			return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
		
		return new ResponseEntity<List<Snack>>(snackRepository.findAll(), HttpStatus.OK);
	}
}
