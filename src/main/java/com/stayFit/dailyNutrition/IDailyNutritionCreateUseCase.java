package com.stayFit.dailyNutrition;

import com.stayFit.models.DailyNutrition;

public interface IDailyNutritionCreateUseCase {
	public DailyNutrition create(UserInfoDTO userInfo)throws Exception;
}
