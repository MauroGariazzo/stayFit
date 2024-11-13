package com.stayFit.dailyNutrition;

import com.stayFit.models.DailyNutrition;

public interface IDailyNutritionGetUseCase {
	public DailyNutrition getDiet(int userId)throws Exception;
	
}
