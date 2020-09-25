package com.group9.prevue;

public class UserAccount {

	private String firstName, lastName;
	private String username, password;
	private String email;
	
	public UserAccount() {}
	
	public UserAccount(String firstName, String lastName, String username, String password, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
	}
}
