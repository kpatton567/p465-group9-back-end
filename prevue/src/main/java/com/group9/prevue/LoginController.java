package com.group9.prevue;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

	@PostMapping("/login")
	void login(@RequestBody String username, @RequestBody String password) {
		// log in to an account with corresponding username if password is correct
	}
	
	@PutMapping("/register")
	void register(@RequestBody UserAccount accountInfo) {
		// register a new account in the database
	}
}
