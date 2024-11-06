package com.stayFit.registration;

public class RegistrationUseCase implements IRegistrationUseCase{
	private IRegistrationDAO registrationDAO;
	
	public RegistrationUseCase(IRegistrationDAO registrationDAO) {
		this.registrationDAO = registrationDAO;
	}
	public void execute(RegistrationRequestDTO regitrationRequestDTO) throws Exception{
		double height = (double)regitrationRequestDTO.height/100; 
		regitrationRequestDTO.BMI = regitrationRequestDTO.weight/Math.pow(height, 2);
		regitrationRequestDTO.BMI = Math.round(regitrationRequestDTO.BMI * 100.0) / 100.0;
	}
}
