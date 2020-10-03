package com.group9.prevue.model;

import javax.persistence.*;

@Entity
@Table(name = "jwt_blacklist", uniqueConstraints = { @UniqueConstraint(columnNames = "token")})
public class JwtBlacklist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String token;
	
	public JwtBlacklist() {}
	
	public JwtBlacklist(String token) {
		this.token = token;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
