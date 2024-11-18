package com.stayFit.user;

import com.stayFit.enums.FitnessState;
import com.stayFit.enums.Gender;
import com.stayFit.enums.Goal;
import com.stayFit.models.User;

public class UserController {
	private IUserGetUseCase getUseCase;
	private IUserUpdateUseCase updateUseCase;
	
	public UserController(IUserGetUseCase getUseCase) {
		this.getUseCase = getUseCase;
	}
	
	public UserController(IUserUpdateUseCase updateUseCase) {
		this.updateUseCase = updateUseCase;
	}
	
	public void update(RequestUpdateUserDTO userDTO) throws Exception{
		if(validate(userDTO)) {
			this.updateUseCase.update(userDTO);
		}
	}
	
	public ResponseGetUserDTO get(int userId) throws Exception{
		User user = this.getUseCase.get(userId);	
		
		return new ResponseGetUserDTO(user.getName(), user.getSurname(), user.getFitnessState(), user.getGender(), user.getGoal(), 
				user.getBirthday(), user.getHeight(), user.getWeight(), user.getUserCredentials());
	}
	
	private boolean validate(RequestUpdateUserDTO userDTO) throws Exception{
		if(userDTO.id == 0) {
			throw new Exception("Id non valido");
		}
		
		if(userDTO.name.isBlank()) {
			throw new Exception("Nome non valido");
		}
		
		if(userDTO.surname.isBlank()) {
			throw new Exception("Cognome non valido");
		}
				
		if(userDTO.birthday == null || userDTO.birthday.toString() == "") {
			throw new Exception("Data di nascita non valida");
		}
		
		if(userDTO.fitnessState == null) {
			throw new Exception("Stato di fitness non valido");
		}
		
		if(userDTO.goal == null) {
			throw new Exception("Obiettivo non valido");
		}
		
		if(userDTO.gender == null) {
			throw new Exception("Sesso non valido");
		}
		
		if(userDTO.height <= 100 || userDTO.height>250) {
			throw new Exception("Altezza non valida");
		}
		
		if(userDTO.weight <= 10 || userDTO.weight>300) {
			throw new Exception("Peso non valido");
		}
		return true;
	}
}
