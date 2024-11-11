package com.stayFit.registration;

import com.stayFit.models.User;
import com.stayFit.models.UserCredentials;

public interface IRegistrationUserCredentialsDAO {
	public UserCredentials register(RequestCreateUserCredentialsDTO registrationRequestDTO)throws Exception;
	//public User update(RequestCreateUserDTO registrationRequestDTO)throws Exception;
}
