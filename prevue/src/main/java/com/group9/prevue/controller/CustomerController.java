package com.group9.prevue.controller;

import java.util.List;

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
import org.springframework.http.ResponseEntity;

import com.group9.prevue.model.response.MessageResponse;
import com.group9.prevue.model.*;
import com.group9.prevue.repository.*;
import com.group9.prevue.utility.JwtUtils;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "http://localhost:5555"})
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private PaymentInfoRepository paymentInfoRepository;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping("customer_payment")
	public ResponseEntity<?> submitCustomerPayment(){
		
		return ResponseEntity.ok(new MessageResponse("Payment successful"));
	}
	
	@PostMapping("guest_payment")
	public ResponseEntity<?> submitGuestPayment(){
		
		return ResponseEntity.ok(new MessageResponse("Payment successful"));
	}
	
	@GetMapping("payment_options")
	public List<PaymentInfo> getPaymentOptions(@RequestHeader(name = "Authorization") String token){
		
		/*
		 * Validate token
		 */
		
		String userId = jwtUtils.getUserFromToken(token.substring(7));
		User user = userRepository.findByUserId(userId);
		
		return paymentInfoRepository.findByUser(user);
	}
	
	@GetMapping("payment_history")
	public List<Payment> getPaymentHistory(@RequestHeader(name = "Authorization") String token){
		
		/*
		 * Validate token
		 */
		
		String userId = jwtUtils.getUserFromToken(token.substring(7));
		User user = userRepository.findByUserId(userId);
		
		return paymentRepository.findByUser(user);
	}
}
