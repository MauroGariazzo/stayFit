package com.stayFit.login;
import com.stayFit.models.User;

public interface ILoginGetUseCase {
	public User login(LoginRequestDTO loginRequest)throws Exception;
}
