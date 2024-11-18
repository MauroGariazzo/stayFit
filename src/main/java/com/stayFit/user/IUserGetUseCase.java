package com.stayFit.user;

import com.stayFit.models.User;

public interface IUserGetUseCase {
	public User get(int userId) throws Exception;
}
