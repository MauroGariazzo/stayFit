package com.stayFit.meal;

import java.util.List;

import com.stayFit.models.Meal;

public class MealGetUseCase implements IMealGetUseCase {
	private IMealDAO mealDAO;
	public MealGetUseCase(IMealDAO mealDAO) {
		this.mealDAO = mealDAO;
	}
	/* Metodo per capire se il pasto esiste già o deve crearlo*/
	public List<Meal>getExistingMeals(MealGetRequestDTO mealGetRequestDTO)throws Exception{
		return mealDAO.getExistingMeals(mealGetRequestDTO);
	}
	
	public List<Meal> getDailyNutritionalValues(MealGetRequestDTO mealGetRequestDTO) throws Exception{
		return mealDAO.getDailyNutritionalValues(mealGetRequestDTO);
	}
}
