package com.group9.prevue.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.group9.prevue.model.Showtime;
import com.group9.prevue.model.request.AddShowtimeRequest;
import com.group9.prevue.model.response.MessageResponse;
import com.group9.prevue.repository.ShowtimeRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/manage")
public class ManagerController {

	@Autowired
	private ShowtimeRepository showtimeRepository;
	
	@PostMapping
	public ResponseEntity<?> addShowtime(@RequestBody AddShowtimeRequest request){
		Showtime showtime = new Showtime(request.getTheaterId(), request.getMovieId(), request.getShowtime());
		showtimeRepository.save(showtime);
		return ResponseEntity.ok(new MessageResponse("Showtime added successfully"));
	}
}
