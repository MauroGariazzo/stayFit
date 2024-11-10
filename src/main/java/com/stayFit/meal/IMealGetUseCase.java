package com.stayFit.meal;

import java.util.List;
import com.stayFit.models.Meal;

public interface IMealGetUseCase {
	public List<Meal>getExistingMeals(MealGetRequestDTO mealGetRequestDTO)throws Exception;
	public List<Meal> getDailyNutritionalValues(MealGetRequestDTO mealGetRequestDTO) throws Exception;
}
