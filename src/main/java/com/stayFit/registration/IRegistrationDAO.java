package com.stayFit.registration;

import com.stayFit.models.User;

public interface IRegistrationDAO {
	public User register(RegistrationRequestDTO registrationRequestDTO)throws Exception;
}
