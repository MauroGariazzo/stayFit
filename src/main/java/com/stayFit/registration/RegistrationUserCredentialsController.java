package com.stayFit.registration;

import com.stayFit.models.UserCredentials;

public class RegistrationUserCredentialsController{
	IRegistrationUserCredentialsCreateUseCase registrationCreateUseCase;
	
	public RegistrationUserCredentialsController(IRegistrationUserCredentialsCreateUseCase registrationCreateUseCase) {
		this.registrationCreateUseCase = registrationCreateUseCase;
	}
		
	public ResponseUserCredentialsDTO create(RequestCreateUserCredentialsDTO registrationRequestDTO)throws Exception {
		if(registrationRequestDTO.username.isBlank()) {
			throw new Exception("Il campo username non può essere vuoto");
		}
		
		if(registrationRequestDTO.email.isBlank()) {
			throw new Exception("L'indirizzo mail non può essere vuoto");
		}
		
		if(registrationRequestDTO.password.isBlank()) {
			throw new Exception("La password non può essere vuota");
		}
		UserCredentials user = this.registrationCreateUseCase.create(registrationRequestDTO);
		ResponseUserCredentialsDTO response = new ResponseUserCredentialsDTO(user.getId(), 
				user.getUsername(), user.getPassword(), user.getEmail()); 

		return response;
	}
	
	
	/*public void update(RequestCreateUserDTO registrationRequestDTO)throws Exception {
		if(registrationRequestDTO.name.isBlank()) {
			throw new Exception("Inserisci il nome");
		}
		
		if(registrationRequestDTO.surname.isBlank()) {
			throw new Exception("Inserisci il cognome");
		}
		
		if(registrationRequestDTO.birthday == null) {
			throw new Exception("Inserisci la data di nascita");
		}
		
		if(registrationRequestDTO.height <= 0) {
			throw new Exception("Inserisci l'altezza");
		}
		
		if(registrationRequestDTO.weight <= 0) {
			throw new Exception("Inserisci il peso");
		}
		
		if(registrationRequestDTO.fitnessState == null) {
			throw new Exception("Inserisci il tuo stato di fitness");
		}
		this.registrationCreateUseCase.update(registrationRequestDTO);
	}*/
}
