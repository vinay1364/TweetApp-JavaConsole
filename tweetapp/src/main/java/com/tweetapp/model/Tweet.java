package com.tweetapp.model;

import java.sql.Date;

public class Tweet {

	private String description;
	private int userId;
	private Date createdOn;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		return "Tweet [description=" + description + ", userId=" + userId + ", createdOn=" + createdOn + "]";
	}
}
