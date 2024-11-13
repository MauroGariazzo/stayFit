package com.stayFit.mealNutrition;

import java.util.List;
import java.util.Map;

import com.stayFit.enums.MealType;
import com.stayFit.models.MealNutrition;

public class MealNutritionGetUseCase implements IMealNutritionGetUseCase {
	private IMealNutritionDAO mealNutritionDAO;
	
	public MealNutritionGetUseCase(IMealNutritionDAO mealNutritionDAO) {
		this.mealNutritionDAO = mealNutritionDAO;
	}
	
	public MealNutrition get(int dailyNutritionId, MealType mealType) throws Exception{
		Map<MealType, MealNutrition> nutritions = mealNutritionDAO.get(dailyNutritionId);
		return nutritions.get(mealType);
	}
}
