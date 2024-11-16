package com.stayFit.dailyNutrition;

import com.stayFit.models.DailyNutrition;

public interface IDailyNutritionUpdateUseCase {
	public DailyNutrition update(UserInfoDTO userInfo)throws Exception;
}
