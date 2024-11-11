package com.stayFit.login;

public class LoginRequestDTO {
	public String username;
	public String password;
	
	public LoginRequestDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
