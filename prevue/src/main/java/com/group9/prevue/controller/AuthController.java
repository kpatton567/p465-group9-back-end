package com.group9.prevue.controller;

import java.util.Set;
import java.util.HashSet;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.group9.prevue.model.ERole;
import com.group9.prevue.model.Email;
import com.group9.prevue.model.OneTimePassword;
import com.group9.prevue.model.SendEmail;
import com.group9.prevue.model.User;
import com.group9.prevue.model.request.LoginRequest;
import com.group9.prevue.model.response.JwtResponse;
import com.group9.prevue.model.response.MessageResponse;
import com.group9.prevue.model.Role;
import com.group9.prevue.model.JwtBlacklist;
import com.group9.prevue.repository.UserRepository;
import com.group9.prevue.security.JwtUtils;
import com.group9.prevue.repository.RoleRepository;
import com.group9.prevue.repository.JwtBlacklistRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	
	//@Autowired
	//private RoleRepository roleRepository;
	
	//@Autowired
	//private JwtBlacklistRepository jwtBlacklistRepository;
	
	//@Bean
	//public PasswordEncoder passwordEncoder() {
	//	return new BCryptPasswordEncoder();
	//}
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("check_user")
	Boolean checkForUser(@RequestParam String userId) {
		return userRepository.existsByUserId(userId);
	}
	
	
	@PostMapping("/register")
	ResponseEntity<?> register(@RequestParam String userId) {
		
		userRepository.save(new User(userId));
		
		return ResponseEntity.ok(new MessageResponse("User added successfully"));
	}
}
