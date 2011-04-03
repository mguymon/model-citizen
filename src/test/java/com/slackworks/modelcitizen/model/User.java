package com.slackworks.modelcitizen.model;

import java.util.List;

public class User {

	private String username;
	private List<String> emails;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public List<String> getEmails() {
		return emails;
	}
	
	public void setEmails(List<String> emails) {
		this.emails = emails;
	}
	
	
}
