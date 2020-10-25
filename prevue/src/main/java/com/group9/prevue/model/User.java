package com.group9.prevue.model;

import javax.persistence.*;
import java.util.Set;
import java.util.HashSet;


@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "userId") } )
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String userId;
	
	public User() {}
	
	public User(String userId) {
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
