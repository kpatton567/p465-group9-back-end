package com.group9.prevue.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;

import com.group9.prevue.model.Showtime;
import com.group9.prevue.repository.ShowtimeRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/home")
public class HomeController {

	@Autowired
	private ShowtimeRepository showtimeRepository;
	
	@PostMapping("/showtimes")
	public List<Showtime> getShowtimes(@RequestBody Long theaterId) {
		return showtimeRepository.findByTheaterId(theaterId);
	}
}
