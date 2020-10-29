package com.group9.prevue.controller;

import java.util.Set;
import java.util.HashSet;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group9.prevue.model.ERole;
import com.group9.prevue.model.User;
import com.group9.prevue.model.response.JwtResponse;
import com.group9.prevue.model.response.MessageResponse;
import com.group9.prevue.model.JwtBlacklist;
import com.group9.prevue.repository.UserRepository;
import com.group9.prevue.utility.JwtUtils;
import com.group9.prevue.repository.JwtBlacklistRepository;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "http://localhost:5555"})
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	
	//@Autowired
	//private RoleRepository roleRepository;
	
	@Autowired
	private JwtBlacklistRepository jwtBlacklistRepository;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("check_user")
	Boolean checkForUser(@RequestParam String userId) {
		return userRepository.existsByUserId(userId);
	}
	
	@PostMapping("/get_token")
	ResponseEntity<?> getToken(@RequestParam String userId) {
		
		String jwt = jwtUtils.generateJwtToken(userId);
		
		return ResponseEntity.ok(new JwtResponse(jwt, userId));
	}
	
	@PostMapping("/register")
	ResponseEntity<?> register(@RequestParam String userId, @RequestParam ERole role) {
		
		userRepository.save(new User(userId, role));
		
		return ResponseEntity.ok(new MessageResponse("User added successfully"));
	}
	
	@PostMapping("/user_role")
	ResponseEntity<?> getUserRole(@RequestHeader(name = "Authorization") String token) {
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		User user = userRepository.findByUserId(jwtUtils.getUserFromToken(token.substring(7)));
		return ResponseEntity.ok(user.getRole());
	}
	
	@PostMapping("invalidate_token")
	ResponseEntity<?> invalidateToken(@RequestHeader(name = "Authorization") String token) {
		if (token != null && token.startsWith("Bearer ")) {
			JwtBlacklist jwtBlacklist = new JwtBlacklist(token.substring(7));
			jwtBlacklistRepository.save(jwtBlacklist);
			return ResponseEntity.ok(new MessageResponse("Token invalidated"));
		}
		
		return ResponseEntity.badRequest().body(new MessageResponse("No token in request"));
	}
	
	@GetMapping("user/{userId}")
	User getUser(@PathVariable String userId) {
		return userRepository.findByUserId(userId);
	}
}
