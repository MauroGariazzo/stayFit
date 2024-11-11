package com.stayFit.login;
import com.stayFit.models.User;

public interface ILoginDAO {
	public User login(LoginRequestDTO loginRequestDTO)throws Exception;
	public String getCryptedPassword(LoginRequestDTO loginRequestDTO)throws Exception;
}
