package com.group9.prevue.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "profiles")
public class UserProfile {

	@Id
	private String userId;
	
	private String firstName;
	private String lastName;
	private String email;
	private String mobileNumber;
	private Date birthday;
	private Set<EGenre> genres;
	
	public UserProfile() {}
	
	public UserProfile(String userId, String firstName, String lastName, String email, String mobileNumber, Date birthday) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.birthday = birthday;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Set<EGenre> getGenres() {
		return genres;
	}

	public void setGenres(Set<EGenre> genres) {
		this.genres = genres;
	}
	
	
}
