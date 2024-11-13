package com.stayFit.dailyNutrition;

import com.stayFit.models.DailyNutrition;

public interface IDailyNutritionDAO {
	public DailyNutrition insert(DailyNutritionRequestCreateDTO dietRequestCreate)throws Exception;
	public DailyNutrition get(int id)throws Exception;
}
