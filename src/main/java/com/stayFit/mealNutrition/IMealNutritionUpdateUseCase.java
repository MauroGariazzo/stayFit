package com.stayFit.mealNutrition;

import com.stayFit.dailyNutrition.DailyNutritionResponseUpdateDTO;

public interface IMealNutritionUpdateUseCase {
	public void updateDiet(DailyNutritionResponseUpdateDTO dailyNutritionDTO)throws Exception;
}
