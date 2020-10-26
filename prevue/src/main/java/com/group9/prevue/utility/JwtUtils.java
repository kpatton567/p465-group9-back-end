package com.group9.prevue.utility;

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
	
	public String generateJwtToken(String userId) {
		
		return Jwts.builder()
				.setSubject(userId)
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public String getUserFromToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateToken(String tokenWithBearer) {
		
		if (tokenWithBearer == null || !tokenWithBearer.startsWith("Bearer "))
			return false;
		
		String token = tokenWithBearer.substring(7);
		
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return !(jwtBlacklistRepository.existsByToken(token));
		} catch (Exception e) {
			return false;
		}
	}
}
