package com.stayFit.registration;

import com.stayFit.models.UserCredentials;
import com.stayFit.utils.EmailValidatorUtil;

public class RegistrationUserCredentialsController{
	IRegistrationUserCredentialsCreateUseCase registrationCreateUseCase;
	
	public RegistrationUserCredentialsController(IRegistrationUserCredentialsCreateUseCase registrationCreateUseCase) {
		this.registrationCreateUseCase = registrationCreateUseCase;
	}
		
	public ResponseUserCredentialsDTO create(RequestCreateUserCredentialsDTO registrationRequestDTO)throws Exception {
		if(registrationRequestDTO.username.isBlank()) {
			throw new Exception("Il campo username non può essere vuoto");
		}
		
		if(registrationRequestDTO.email.isBlank() || !EmailValidatorUtil.isValid(registrationRequestDTO.email)) {
			throw new Exception("Indirizzo mail non corretto");
		}
		
		if(registrationRequestDTO.password.isBlank()) {
			throw new Exception("La password non può essere vuota");
		}
		UserCredentials user = this.registrationCreateUseCase.create(registrationRequestDTO);
		ResponseUserCredentialsDTO response = new ResponseUserCredentialsDTO(user.getId(), 
				user.getUsername(), user.getPassword(), user.getEmail()); 

		return response;
	}
}
