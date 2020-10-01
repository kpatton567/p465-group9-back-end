package com.group9.prevue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.group9.prevue.model.User;
import com.group9.prevue.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/login")
	void login(@RequestBody String username, @RequestBody String password) {
		// log in to an account with corresponding username if password is correct
	}
	
	@PostMapping("/register")
	void register(@RequestBody User accountInfo) {
		// register a new account in the database
	}
}
