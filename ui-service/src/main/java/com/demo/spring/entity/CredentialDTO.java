package com.demo.spring.entity;

public class CredentialDTO {

	private String username;
	private String password;

	public CredentialDTO() {
		
	}
	
	
	
	public CredentialDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}



	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
