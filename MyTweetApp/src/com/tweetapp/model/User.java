package com.tweetapp.model;

public class User {

	private String first_name;
	private String last_name;
	private String gender;
	private String dob;
	private String email;
	private String password;
	private boolean status;

	public User() {
		super();
	}

	public String getFirst_name() {
		return first_name;
	}


	public String getLast_name() {
		return last_name;
	}


	public String getGender() {
		return gender;
	}



	public String getDob() {
		return dob;
	}


	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User [first_name=" + first_name + ", email=" + email + ", password=" + password + "]";
	}
}
