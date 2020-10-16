package com.group9.prevue.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.group9.prevue.repository.JwtBlacklistRepository;

import java.util.Date;
import io.jsonwebtoken.*;

@Component
public class JwtUtils {

	@Value("${prevue.app.jwtSecret}")
	private String jwtSecret;
	@Value("${prevue.app.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	@Autowired
	private JwtBlacklistRepository jwtBlacklistRepository;
	
	/*public String generateJwtToken(String email) {
		
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	/*public String getEmailFromToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}*/
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return !(jwtBlacklistRepository.existsByToken(token));
		} catch (Exception e) {
			return false;
		}
	}
}
