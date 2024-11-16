package com.stayFit.mealNutrition;

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
		
		MealNutrition mealNutrition = new MealNutrition();		
		mealNutrition.setCalories(Math.round(mealNutrition.getCalories()*100)/100);
		mealNutrition.setProteins(Math.round(mealNutrition.getProteins()*100)/100);
		mealNutrition.setCarbs(Math.round(mealNutrition.getCarbs()*100)/100);
		mealNutrition.setFats(Math.round(mealNutrition.getFats()*100)/100);
		if(nutritions.size() == 0) {
			return new MealNutrition();
		}
		return nutritions.get(mealType);
	}
}
