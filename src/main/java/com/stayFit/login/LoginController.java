package com.stayFit.login;

import com.stayFit.models.User;
import com.stayFit.registration.ResponseUserDTO;

public class LoginController {
	private ILoginGetUseCase loginGetUseCase;
	public LoginController(ILoginGetUseCase loginGetUseCase) {
		this.loginGetUseCase = loginGetUseCase;
	}

	public ResponseUserDTO login(LoginRequestDTO loginRequest)throws Exception {
		User user = loginGetUseCase.login(loginRequest);
		return new ResponseUserDTO(user.getId(), user.getName(), user.getSurname(), user.getHeight(), user.getWeight(),
				user.getBirthday(), user.getFitnessState(), user.getBMI(), user.getGender(), user.getGoal(),
				user.getSubscriptionDate(), user.getUserCredentials_fk());
	}
}
