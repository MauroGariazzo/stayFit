package com.stayFit.user;

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
		this.updateUseCase.update(userDTO);
	}
	
	public void get(int userId) throws Exception{
		this.updateUseCase.update(null);
	}
}
