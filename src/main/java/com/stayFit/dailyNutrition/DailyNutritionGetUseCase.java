package com.stayFit.dailyNutrition;

import com.stayFit.models.DailyNutrition;

public class DailyNutritionGetUseCase implements IDailyNutritionGetUseCase{
	private IDailyNutritionDAO dailyNutritionDAO;
	public DailyNutritionGetUseCase(IDailyNutritionDAO dietDAO) {
		this.dailyNutritionDAO = dietDAO;
	}
	
	public DailyNutrition getDiet(int userId)throws Exception{
		return dailyNutritionDAO.get(userId);
	}		
}
