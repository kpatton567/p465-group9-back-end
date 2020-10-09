package com.group9.prevue.model;

import java.util.Set;

public class JwtResponse {

	private String token;
	private String type = "Bearer";
	private Long id;
	private String email;
	private Set<Role> roles;
	
	public JwtResponse(String token, Long id, String email, Set<Role> roles) {
		this.token = token;
		this.id = id;
		this.email = email;
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
