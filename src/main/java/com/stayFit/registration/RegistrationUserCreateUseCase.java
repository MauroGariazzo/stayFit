package com.stayFit.registration;

import com.stayFit.models.User;

public class RegistrationUserCreateUseCase implements IRegistrationUserCreateUseCase{
	private IRegistrationUserDAO registrationDAO;
	
	public RegistrationUserCreateUseCase(IRegistrationUserDAO registrationDAO) {
		this.registrationDAO = registrationDAO;
	}
	public User create(RequestCreateUserDTO regitrationRequestDTO) throws Exception{
		return this.registrationDAO.insert(regitrationRequestDTO);
	}
	
	
	public double calculateBMI(double height, double weight)throws Exception {		 
		height = (double)height/100; 
		double BMI = weight/Math.pow(height, 2);
		BMI = Math.round(BMI * 100.0) / 100.0;
		
		return BMI;
	}
}
