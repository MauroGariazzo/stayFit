package com.stayFit.registration;

public class RequestCreateUserCredentialsDTO {
	public String username;
	public String email;
	public String password;
	
	public RequestCreateUserCredentialsDTO(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
}
