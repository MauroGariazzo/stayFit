package com.stayFit.mealNutrition;

import com.stayFit.dailyNutrition.DailyNutritionResponseCreateDTO;

public interface IMealNutritionCreateUseCase {
	public void createDiet(DailyNutritionResponseCreateDTO dailyNutritionDTO)throws Exception;	
}
