package com.stayFit.diet;

import com.stayFit.models.Diet;

public interface ICreateDietDAO {
	public void insert(DietRequestCreateDTO dietRequestCreate)throws Exception;
}
