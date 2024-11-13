package com.stayFit.mealNutrition;
import java.util.Map;

import com.stayFit.enums.MealType;
import com.stayFit.models.MealNutrition;

public interface IMealNutritionDAO {
	public void insert(MealNutritionCreateRequestDTO request)throws Exception;
	public Map<MealType, MealNutrition> get(int daily_nutrition_fk) throws Exception;
}
