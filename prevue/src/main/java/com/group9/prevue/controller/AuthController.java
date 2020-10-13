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
import com.group9.prevue.utility.JwtUtils;
import com.group9.prevue.repository.RoleRepository;
import com.group9.prevue.repository.JwtBlacklistRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private JwtBlacklistRepository jwtBlacklistRepository;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/login")
	ResponseEntity<?> login(@RequestBody LoginRequest request) {
		// log in to an account with corresponding email if password is correct
		
		if (! userRepository.existsByEmail(request.getEmail()))
			return ResponseEntity.badRequest().body(new MessageResponse("Email not found"));
		
		User user = userRepository.findByEmail(request.getEmail()).get();
		if (user.getPassword() != passwordEncoder().encode(request.getPassword()))
			return ResponseEntity.badRequest().body(new MessageResponse("Incorrect password"));
		
		String jwt = jwtUtils.generateJwtToken(request.getEmail());
		
		return ResponseEntity.ok(new JwtResponse(jwt, user.getId(), user.getEmail(), user.getRoles()));
	}
	
	@PostMapping("/otp")
	ResponseEntity<?> otp(@RequestBody Email user) throws AddressException, MessagingException {
		SendEmail email = new SendEmail();
		final String from = "prevuebooking";
    	final String password = "P465565group9";
    	String to = user.getEmail();
    	String cc = "";
    	String title = "One Time Password";
    	OneTimePassword otp = new OneTimePassword();
    	String OTP = otp.pass(6);
		email.Send(from, password, to, cc, title, OTP);
		return ResponseEntity.ok(new MessageResponse("OTP sent successfully"));
		
		
	}
	
	@PostMapping("/register")
	ResponseEntity<?> register(@RequestBody LoginRequest request) {
		// register a new account in the database
		if (userRepository.existsByEmail(request.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use"));
		}
		
		User user = new User(request.getEmail(), passwordEncoder().encode(request.getPassword()));
		
		Set<Role> roles = new HashSet<>();
		
		Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
				.orElseThrow(() -> new RuntimeException("Role not found"));
		roles.add(userRole);
		
		user.setRoles(roles);
		userRepository.save(user);
		
		return ResponseEntity.ok(new MessageResponse("User registered successfully"));
	}
	
	@PostMapping("/logout")
	ResponseEntity<?> logout(@RequestHeader(name = "Authorization") String token){
		if (token != null && token.startsWith("Bearer ")) {
			JwtBlacklist jwtBlacklist = new JwtBlacklist(token.substring(7));
			jwtBlacklistRepository.save(jwtBlacklist);
			return ResponseEntity.ok(new MessageResponse("User logged out successfully"));
		}
		
		return ResponseEntity.badRequest().body(new MessageResponse("No token in request"));
		
	}
}
