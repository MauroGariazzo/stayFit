package com.stayFit.user;

public class UserUpdateUseCase implements IUserUpdateUseCase{
	private IUserDAO userDAO;
	public UserUpdateUseCase(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public void update(RequestUpdateUserDTO request)throws Exception {
		userDAO.update(request);
	}
}
