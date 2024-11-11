package com.stayFit.registration;

import com.stayFit.models.User;

public interface IRegistrationUserDAO {
	public User insert(RequestCreateUserDTO registrationRequestDTO)throws Exception;
	public void update(RequestCreateUserDTO registrationRequestDTO)throws Exception;
}
