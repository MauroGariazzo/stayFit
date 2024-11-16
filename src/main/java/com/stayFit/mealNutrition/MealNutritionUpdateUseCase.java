package com.stayFit.mealNutrition;

import com.stayFit.dailyNutrition.DailyNutritionResponseUpdateDTO;
import com.stayFit.enums.MealType;

public class MealNutritionUpdateUseCase implements IMealNutritionUpdateUseCase{
private IMealNutritionDAO mealNutritionDAO;
	
	public MealNutritionUpdateUseCase(IMealNutritionDAO mealNutritionDAO) {
		this.mealNutritionDAO = mealNutritionDAO;
	}
	
	public void updateDiet(DailyNutritionResponseUpdateDTO dailyNutritionDTO)throws Exception{
		for(MealNutritionRequestUpdateDTO request : calculateMealNutrients(dailyNutritionDTO)) {
			mealNutritionDAO.update(request);
		}
	}
	
	private MealNutritionRequestUpdateDTO[] calculateMealNutrients(DailyNutritionResponseUpdateDTO dailyNutritionDTO) {
		double totalBreakfastCalories = 0.25 * dailyNutritionDTO.dailyCalories;
		double totalLunchCalories = 0.3 * dailyNutritionDTO.dailyCalories;
		double totalDinnerCalories = 0.3 * dailyNutritionDTO.dailyCalories;
		double totalSnacksCalories = 0.15 * dailyNutritionDTO.dailyCalories;
		
		double breakfastProteins = 0.15 * dailyNutritionDTO.dailyProteins;
		double breakfastCarbs = 0.15 * dailyNutritionDTO.dailyCarbs;
		double breakfastFats = 0.15 * dailyNutritionDTO.dailyFats;
		
		double lunchProteins = 0.18 * dailyNutritionDTO.dailyProteins;
		double lunchCarbs = 0.18 * dailyNutritionDTO.dailyCarbs;
		double lunchFats = 0.18 * dailyNutritionDTO.dailyFats;
		
		double dinnerProteins = 0.21 * dailyNutritionDTO.dailyProteins;
		double dinnerCarbs = 0.21 * dailyNutritionDTO.dailyCarbs;
		double dinnerFats = 0.21 * dailyNutritionDTO.dailyFats;
		
		double snacksProteins = 0.45 * dailyNutritionDTO.dailyProteins;
		double snacksCarbs = 0.45 * dailyNutritionDTO.dailyCarbs;
		double snacksFats = 0.45 * dailyNutritionDTO.dailyFats;
		
		MealNutritionRequestUpdateDTO dietBreakfastRequest = new 
				MealNutritionRequestUpdateDTO(MealType.COLAZIONE, (int)totalBreakfastCalories,
				(int)breakfastProteins, (int)breakfastCarbs, (int)breakfastFats, dailyNutritionDTO.id);
		
		MealNutritionRequestUpdateDTO dietLunchRequest = new 
				MealNutritionRequestUpdateDTO(MealType.PRANZO, (int)totalLunchCalories,
				(int)lunchProteins, (int)lunchCarbs, (int)lunchFats, dailyNutritionDTO.id);
		
		MealNutritionRequestUpdateDTO dietDinnerRequest = 
				new MealNutritionRequestUpdateDTO(MealType.CENA, (int)totalDinnerCalories,
				(int)dinnerProteins, (int)dinnerCarbs, (int)dinnerFats, dailyNutritionDTO.id);
		
		MealNutritionRequestUpdateDTO dietSnacksRequest = 
				new MealNutritionRequestUpdateDTO(MealType.SPUNTINO, (int)totalSnacksCalories,
				(int)snacksProteins, (int)snacksCarbs, (int)snacksFats, dailyNutritionDTO.id);
		
		return new MealNutritionRequestUpdateDTO[] {dietBreakfastRequest, dietLunchRequest, dietDinnerRequest, dietSnacksRequest};
	}
}
