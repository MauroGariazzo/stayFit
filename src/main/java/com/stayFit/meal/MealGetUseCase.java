package com.stayFit.meal;

import java.util.List;

import com.stayFit.models.Meal;

public class MealGetUseCase implements IMealGetUseCase {
	private IMealDAO mealDAO;
	public MealGetUseCase(IMealDAO mealDAO) {
		this.mealDAO = mealDAO;
	}
	
	public List<Meal>execute(MealGetRequestDTO mealGetRequestDTO)throws Exception{
		return mealDAO.getAll(mealGetRequestDTO);
	}
}
