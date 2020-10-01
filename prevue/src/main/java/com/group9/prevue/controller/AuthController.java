package com.group9.prevue.controller;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group9.prevue.model.ERole;
import com.group9.prevue.model.LoginRequest;
import com.group9.prevue.model.JwtResponse;
import com.group9.prevue.model.MessageResponse;
import com.group9.prevue.model.User;
import com.group9.prevue.model.Role;
import com.group9.prevue.repository.UserRepository;
import com.group9.prevue.repository.RoleRepository;
import com.group9.prevue.security.JwtUtils;
import com.group9.prevue.security.UserDetailsImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired 
	AuthenticationManager authManager;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/login")
	ResponseEntity<?> login(@RequestBody LoginRequest request) {
		// log in to an account with corresponding email if password is correct
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwt = jwtUtils.generateJwtToken(auth);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles));
	}
	
	@PostMapping("/register")
	ResponseEntity<?> register(@RequestBody LoginRequest request) {
		// register a new account in the database
		if (userRepository.existsByEmail(request.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use"));
		}
		
		User user = new User(request.getEmail(), encoder.encode(request.getPassword()));
		
		Set<Role> roles = new HashSet<>();
		
		Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
				.orElseThrow(() -> new RuntimeException("Role not found"));
		roles.add(userRole);
		
		user.setRoles(roles);
		userRepository.save(user);
		
		return ResponseEntity.ok(new MessageResponse("User registered successfully"));
	}
}
