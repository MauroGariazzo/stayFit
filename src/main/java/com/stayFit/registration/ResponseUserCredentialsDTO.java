package com.stayFit.registration;

public class ResponseUserCredentialsDTO {
	public int id;
	public String username;
	public String email;
	public String password;
	
	public ResponseUserCredentialsDTO(int id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public ResponseUserCredentialsDTO() {
		
	}
}
