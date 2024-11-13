package com.stayFit.mealNutrition;

import com.stayFit.enums.MealType;
import com.stayFit.models.MealNutrition;

public interface IMealNutritionGetUseCase {
	public MealNutrition get(int dailyNutritionId, MealType mealType) throws Exception;
}
