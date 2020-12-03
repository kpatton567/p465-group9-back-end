package com.group9.prevue.model.request;

import java.util.Set;

import com.group9.prevue.model.EGenre;

public class EditProfileRequest {

	private String firstName;
	private String lastName;
	private String email;
	private String mobileNumber;
	private String birthday;
	private Set<EGenre> genres;
	
	public EditProfileRequest(String firstName, String lastName, String email, String mobileNumber, String birthday, Set<EGenre> genres) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.birthday = birthday;
		this.genres = genres;
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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Set<EGenre> getGenres() {
		return genres;
	}

	public void setGenres(Set<EGenre> genres) {
		this.genres = genres;
	}
	
}
