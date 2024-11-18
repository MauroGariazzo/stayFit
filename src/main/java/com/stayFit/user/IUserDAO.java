package com.stayFit.user;

import com.stayFit.models.User;

public interface IUserDAO {
	public User getUserInfo(int idUser) throws Exception;
	public void update(RequestUpdateUserDTO request)throws Exception;
}
