package com.stayFit.registration;

import com.stayFit.models.User;

public interface IRegistrationUserCreateUseCase {
	public User create(RequestCreateUserDTO regitrationRequestDTO) throws Exception;
	public void update(RequestCreateUserDTO regitrationRequestDTO)throws Exception;
	public double calculateBMI(double height, double weight)throws Exception;
}
