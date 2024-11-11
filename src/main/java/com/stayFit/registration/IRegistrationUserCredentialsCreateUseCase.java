package com.stayFit.registration;

import com.stayFit.models.UserCredentials;

public interface IRegistrationUserCredentialsCreateUseCase {
	public UserCredentials create(RequestCreateUserCredentialsDTO regitrationRequestDTO) throws Exception;
	//public void update(RequestCreateUserDTO regitrationRequestDTO)throws Exception;
}
