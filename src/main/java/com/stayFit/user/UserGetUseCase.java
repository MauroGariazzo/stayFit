package com.stayFit.user;

import com.stayFit.models.User;

public class UserGetUseCase implements IUserGetUseCase {
	private IUserDAO userDAO;
	
	public UserGetUseCase(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public User get(int userId)throws Exception {
		return userDAO.getUserInfo(userId);
	}
}
