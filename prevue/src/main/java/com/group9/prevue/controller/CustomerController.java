package com.group9.prevue.controller;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

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
import com.group9.prevue.model.request.EditProfileRequest;
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
	
	//@Autowired
	//private PaymentInfoRepository paymentInfoRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
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
		payment.setMovie(movieRepository.findById(request.getMovieId()).orElseThrow(() -> new RuntimeException("Error: Movie not found")));
		payment.setUser(user);
		payment.setPaymentDate(new Date());
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
		
		showtime.setCapacity(showtime.getCapacity() - request.getTicketQuantity());
		showtimeRepository.save(showtime);
		
		return ResponseEntity.ok(new MessageResponse("Payment successful"));
	}
	
	@PostMapping("request_refund/{paymentNum}")
	public ResponseEntity<?> requestRefund(@RequestHeader(name = "Authorization") String token, @PathVariable Long paymentNum) {
		if(!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User user = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		Payment payment = paymentRepository.findById(paymentNum).orElseThrow(() -> new RuntimeException("Error: Payment not found"));
		
		if (!(payment.getUser().getUserId().equals(user.getUserId())))
			return new ResponseEntity<String>("User does not match payment", HttpStatus.FORBIDDEN);
		
		if (payment.getStatus() == EPaymentStatus.REFUNDABLE) {
			if (payment.getShowtime().getShowtime().compareTo(new Date()) <= 0) {
				payment.setStatus(EPaymentStatus.FINAL);
				paymentRepository.save(payment);
				return new ResponseEntity<String>("Not refundable: Showtime has passed", HttpStatus.FORBIDDEN);
			}
			
			payment.setStatus(EPaymentStatus.REQUESTED);
			paymentRepository.save(payment);
			return new ResponseEntity<String>("Refund requested", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Not refundable", HttpStatus.FORBIDDEN);
		}
	}
	
	@PostMapping("post_review")
	public ResponseEntity<?> postReview(@RequestHeader(name = "Authorization") String token, @RequestBody SimpleReview request) {
		if(!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User user = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		Movie movie = movieRepository.findById(request.getMovieId()).orElseThrow(() -> new RuntimeException("Error: Theater not found"));
		
		Review review = new Review(request.getStars(), request.getHeadline(), request.getReview(), user, movie);
		reviewRepository.save(review);
		
		return ResponseEntity.ok(new MessageResponse("Review posted"));
	}
	
	@PostMapping("edit_profile")
	public ResponseEntity<?> postReview(@RequestHeader(name = "Authorization") String token, @RequestBody EditProfileRequest request) {
		if(!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User user = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		
		try {
			UserProfile profile = profileRepository.findById(user.getUserId()).orElseThrow(() -> new RuntimeException("Profile not found"));
			
			if (request.getFirstName() != null)
				profile.setFirstName(request.getFirstName());
			if (request.getLastName() != null)
				profile.setLastName(request.getLastName());
			if (request.getEmail() != null)
				profile.setEmail(request.getEmail());
			if (request.getMobileNumber() != null)
				profile.setMobileNumber(request.getMobileNumber());
			if (request.getBirthday() != null) {
				try {
					profile.setBirthday(format.parse(request.getBirthday()));
				} catch (ParseException e) {
					profile.setBirthday(null);
				}
			}
			if (request.getGenres() != null)
				profile.setGenres(request.getGenres());
			
			profileRepository.save(profile);
		} catch (RuntimeException e) {
			UserProfile profile;
			try {
				profile = new UserProfile(user.getUserId(), request.getFirstName(), request.getLastName(), request.getEmail(), request.getMobileNumber(), request.getBirthday() == null ? null : format.parse(request.getBirthday()));
				profile.setGenres(request.getGenres());
			} catch (ParseException pe) {
				profile = new UserProfile(user.getUserId(), request.getFirstName(), request.getLastName(), request.getEmail(), request.getMobileNumber(), null);
				profile.setGenres(request.getGenres());
			}
			profileRepository.save(profile);
		}
		
		return ResponseEntity.ok(new MessageResponse("Profile information saved"));
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
		
		for (int i = 0; i < payments.size(); i++) {
			Payment payment = payments.get(i);
			if (payment.getStatus() != EPaymentStatus.REFUNDED && payment.getShowtime().getShowtime().compareTo(new Date()) <= 0) {
				payment.setStatus(EPaymentStatus.FINAL);
				paymentRepository.save(payment);
				payments.set(i, payment);
			}
		}
		
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
			
			BigDecimal bd = new BigDecimal(Double.toString(total[0]));
			bd = bd.setScale(2, RoundingMode.HALF_UP);
			total[0] = bd.doubleValue();
			
			CustomerTransaction transaction = new CustomerTransaction(payment.getId(), ShowtimeInfo.dateString(payment.getPaymentDate()), payment.getMovie().getId(), payment.getMovie().getTitle(), payment.getTheater().getId(), payment.getTheater().getName(), payment.getPaymentInfo().getNumber().substring(12), snacks, total[0], payment.getStatus());
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
			Coupon coupon = couponRepository.findByCodeAndExpirationDateGreaterThan(couponCode.toUpperCase(), new Date()).orElseThrow(() -> new RuntimeException("Error: Coupon not found"));
			if (!(user.getUsedCoupons().contains(coupon)))
				return new ResponseEntity<Coupon>(coupon, HttpStatus.OK);
			else
				return ResponseEntity.badRequest().body(new MessageResponse("Coupon already used"));
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Coupon not found or is expired"));
		}
	}
	
	@GetMapping("profile")
	public ResponseEntity<?> getProfileInfo(@RequestHeader(name = "Authorization") String token) {
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User user = userRepository.findById(jwtUtils.getUserFromToken(token.substring(7))).orElseThrow(() -> new RuntimeException("Error: User not found"));
		
		try {
			UserProfile profile = profileRepository.findById(user.getUserId()).orElseThrow(() -> new RuntimeException("Profile not found"));
			return new ResponseEntity<UserProfile>(profile, HttpStatus.OK);
		} catch (RuntimeException e) {
			UserProfile profile = new UserProfile(user.getUserId(), null, null, null, null, null);
			return new ResponseEntity<UserProfile>(profile, HttpStatus.OK);
		}
	}
}
