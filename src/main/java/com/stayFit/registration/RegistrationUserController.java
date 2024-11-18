package com.stayFit.registration;

import java.time.LocalDate;
import java.time.Period;

import com.stayFit.enums.FitnessState;
import com.stayFit.enums.Gender;
import com.stayFit.enums.Goal;
import com.stayFit.models.User;


public class RegistrationUserController {
	private IRegistrationUserCreateUseCase registrationUseCase;

	public RegistrationUserController(IRegistrationUserCreateUseCase registrationUseCase) {
		this.registrationUseCase = registrationUseCase;
	}

	public ResponseUserDTO insert(RequestCreateUserDTO registrationRequestDTO)throws Exception {		
		
		if(registrationRequestDTO.name.isBlank()) {
			throw new Exception("Il nome non può essere vuoto");
		}
		
		if(registrationRequestDTO.surname.isBlank()) {
			throw new Exception("Il cognome non può essere vuoto");
		}
		
		if(registrationRequestDTO.height < 100 || registrationRequestDTO.height > 250) {
			throw new Exception("L'altezza deve essere maggiore o uguale a 100 cm e minore o uguale a 250 cm");
		}
		
		if(registrationRequestDTO.weight < 30 || registrationRequestDTO.weight > 200) {
			throw new Exception("Il peso deve essere maggiore o uguale a 30 kg e minore o uguale a 300 kg");
		}
		
		if(registrationRequestDTO.birthday == null) {
			throw new Exception("La data di nascita non può essere vuota");
		}
		
		if(Period.between(registrationRequestDTO.birthday, LocalDate.now()).getYears() < 18) {
			throw new Exception("L'applicazione è riservata ai soli maggiorenni");
		}
		
		if(registrationRequestDTO.fitnessState == null) {
			throw new Exception("Inserisci il tuo stato di fitness attuale");
		}
		
		if(registrationRequestDTO.gender == null) {
			throw new Exception("Inserisci il tuo sesso");
		}
		
		if(registrationRequestDTO.goal == null) {
			throw new Exception("Inserisci il tuo obiettivo");
		}

		User user = registrationUseCase.create(registrationRequestDTO);
		return new ResponseUserDTO(user.getId(), user.getName(), user.getSurname(), user.getHeight(),
				user.getWeight(), user.getBirthday(), user.getFitnessState(), user.getGender(),
				user.getGoal(), user.getSubscriptionDate(), user.getUserCredentials_fk()); 
	}

	/*public void update(RequestCreateUserDTO registrationRequestDTO)throws Exception {
		//validazione dei dati
		registrationUseCase.update(registrationRequestDTO);
	}*/
	
	public double getBMI(double height, double weight)throws Exception {
		return registrationUseCase.calculateBMI(height, weight);
	}
	
}
