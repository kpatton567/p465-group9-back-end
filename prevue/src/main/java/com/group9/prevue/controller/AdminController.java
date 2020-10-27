package com.group9.prevue.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.beans.factory.annotation.Autowired;

import com.group9.prevue.model.Theater;
import com.group9.prevue.model.request.AddTheaterRequest;
import com.group9.prevue.model.response.MessageResponse;
import com.group9.prevue.repository.UserRepository;
import com.group9.prevue.repository.TheaterRepository;
import com.group9.prevue.utility.JwtUtils;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "http://localhost:5555"})
@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private TheaterRepository theaterRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping("add_theater")
	public ResponseEntity<?> addTheater(@RequestHeader(name = "Authorization") String token, @RequestBody AddTheaterRequest request){
		
		if (!jwtUtils.validateToken(token))
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		
		Theater theater = new Theater(request.getName());
		theater.setManager(userRepository.findByUserId(jwtUtils.getUserFromToken(token.substring(7))));
		
		theaterRepository.save(theater);
		return ResponseEntity.ok(new MessageResponse("Theater added successfully"));
	}
}
