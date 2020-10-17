package com.group9.prevue.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;

import com.group9.prevue.model.Theater;
import com.group9.prevue.model.request.AddTheaterRequest;
import com.group9.prevue.model.response.MessageResponse;
import com.group9.prevue.repository.TheaterRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private TheaterRepository theaterRepository;
	
	@PostMapping("add_theater")
	public ResponseEntity<?> addTheater(@RequestBody AddTheaterRequest request){
		Theater theater = new Theater(request.getName());
		theaterRepository.save(theater);
		return ResponseEntity.ok(new MessageResponse("Theater added successfully"));
	}
}
