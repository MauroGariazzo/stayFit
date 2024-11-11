package com.stayFit.login;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.stayFit.models.User;

public class LoginGetUseCase implements ILoginGetUseCase{
	private ILoginDAO loginDAO;
	private BCryptPasswordEncoder passwordEncoder;
			
	public LoginGetUseCase(ILoginDAO loginDAO) {
		this.loginDAO = loginDAO;
		passwordEncoder = new BCryptPasswordEncoder();
	}
	
	public User login(LoginRequestDTO loginRequest)throws Exception{
		String cryptedPassword = loginDAO.getCryptedPassword(loginRequest);		
		if(passwordEncoder.matches(loginRequest.password, cryptedPassword)) {
			loginRequest.password = cryptedPassword;
			return loginDAO.login(loginRequest);
		}
		throw new Exception("Username o password errati");
	}	
}
