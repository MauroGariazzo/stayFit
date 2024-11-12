package com.stayFit.diet;

import com.stayFit.models.Diet;

public interface ICreateDietUseCase {
	public void create(UserInfoDTO userInfo)throws Exception;
}
