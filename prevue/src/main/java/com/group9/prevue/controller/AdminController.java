package com.group9.prevue.controller;

import java.text.SimpleDateFormat;
import java.text.ParseException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.beans.factory.annotation.Autowired;

import com.group9.prevue.model.Coupon;
import com.group9.prevue.model.ERole;
import com.group9.prevue.model.Theater;
import com.group9.prevue.model.User;
import com.group9.prevue.model.request.AddCouponRequest;
import com.group9.prevue.model.request.AddTheaterRequest;
import com.group9.prevue.model.response.MessageResponse;
import com.group9.prevue.repository.CouponRepository;
import com.group9.prevue.repository.UserRepository;
import com.group9.prevue.repository.TheaterRepository;
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
}
