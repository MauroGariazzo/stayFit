package com.stayFit.mealNutrition;

import com.stayFit.dailyNutrition.DailyNutritionResponseCreateDTO;
import com.stayFit.enums.MealType;

public class MealNutritionCreateUseCase implements IMealNutritionCreateUseCase{
	
	private IMealNutritionDAO mealNutritionDAO;
	public MealNutritionCreateUseCase(IMealNutritionDAO mealNutritionDAO) {
		this.mealNutritionDAO = mealNutritionDAO;
	}
	
	public void createDiet(DailyNutritionResponseCreateDTO dailyNutritionDTO)throws Exception{
		for(MealNutritionCreateRequestDTO request : calculateMealNutrients(dailyNutritionDTO)) {
			mealNutritionDAO.insert(request);
		}
	}
	
	private MealNutritionCreateRequestDTO[] calculateMealNutrients(DailyNutritionResponseCreateDTO dailyNutritionDTO) {
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
		
		MealNutritionCreateRequestDTO dietBreakfastRequest = new 
				MealNutritionCreateRequestDTO(MealType.COLAZIONE, (int)totalBreakfastCalories,
				(int)breakfastProteins, (int)breakfastCarbs, (int)breakfastFats, dailyNutritionDTO.id);
		
		MealNutritionCreateRequestDTO dietLunchRequest = new 
				MealNutritionCreateRequestDTO(MealType.PRANZO, (int)totalLunchCalories,
				(int)lunchProteins, (int)lunchCarbs, (int)lunchFats, dailyNutritionDTO.id);
		
		MealNutritionCreateRequestDTO dietDinnerRequest = 
				new MealNutritionCreateRequestDTO(MealType.CENA, (int)totalDinnerCalories,
				(int)dinnerProteins, (int)dinnerCarbs, (int)dinnerFats, dailyNutritionDTO.id);
		
		MealNutritionCreateRequestDTO dietSnacksRequest = 
				new MealNutritionCreateRequestDTO(MealType.SPUNTINO, (int)totalSnacksCalories,
				(int)snacksProteins, (int)snacksCarbs, (int)snacksFats, dailyNutritionDTO.id);
		
		return new MealNutritionCreateRequestDTO[] {dietBreakfastRequest, dietLunchRequest, dietDinnerRequest, dietSnacksRequest};
	}
	/*int id, MealType mealType, double calories, double proteins, 
	double carbs, double fats, int dailyNutritionFk*/
	
		// Calorie quotidiane TOTAL
		/*double totalDailyCalories = calculateCalories(TDEE, userInfoDTO.goal);
		// Calorie TOTALI DIVISE per pasto
		double totalBreakfastCalories = 0.25 * totalDailyCalories;
		double totalLunchCalories = 0.3 * totalDailyCalories;
		double totalDinnerCalories = 0.3 * totalDailyCalories;
		double totalSnacksCalories = 0.15 * totalDailyCalories;
		
		// Totale proteine, carboidrati, grassi da assumere al giorno		
		double totalDailyGramsProteins = calculateProteins(totalDailyCalories, userInfoDTO.goal); //125g proteine
		double totalDailyGramsCarbs = calculateCarbs(totalDailyCalories, userInfoDTO.goal);
		double totalDailyGramsFats = calculateFats(totalDailyCalories, userInfoDTO.goal);
		
		// Totale calorie macronutrienti(proteine, carboidrati, grassi) per pasto
		double breakfastProteins = 0.15 * totalDailyGramsProteins;
		double breakfastCarbs = 0.15 * totalDailyGramsCarbs;
		double breakfastFats = 0.15 * totalDailyGramsFats;
		
		double lunchProteins = 0.18 * totalDailyGramsProteins;
		double lunchCarbs = 0.18 * totalDailyGramsCarbs;
		double lunchFats = 0.18 * totalDailyGramsFats;
		
		double dinnerProteins = 0.21 * totalDailyGramsProteins;
		double dinnerCarbs = 0.21 * totalDailyGramsCarbs;
		double dinnerFats = 0.21 * totalDailyGramsFats;
		
		double snacksProteins = 0.45 * totalDailyGramsProteins;
		double snacksCarbs = 0.45 * totalDailyGramsCarbs;
		double snacksFats = 0.45 * totalDailyGramsFats;
		
		
		DietRequestCreateDTO dietBreakfastRequest = new DietRequestCreateDTO(MealType.COLAZIONE, (int)totalBreakfastCalories,
				(int)breakfastProteins, (int)breakfastCarbs, (int)breakfastFats, userInfoDTO.userCredentials_fk);
		
		DietRequestCreateDTO dietLunchRequest = new DietRequestCreateDTO(MealType.PRANZO, (int)totalLunchCalories,
				(int)lunchProteins, (int)lunchCarbs, (int)lunchFats, userInfoDTO.userCredentials_fk);
		
		DietRequestCreateDTO dietDinnerRequest = new DietRequestCreateDTO(MealType.CENA, (int)totalDinnerCalories,
				(int)dinnerProteins, (int)dinnerCarbs, (int)dinnerFats, userInfoDTO.userCredentials_fk);
		
		DietRequestCreateDTO dietSnacksRequest = new DietRequestCreateDTO(MealType.SPUNTINO, (int)totalSnacksCalories,
				(int)snacksProteins, (int)snacksCarbs, (int)snacksFats, userInfoDTO.userCredentials_fk);
		
		return new DietRequestCreateDTO[] {dietBreakfastRequest, dietLunchRequest, dietDinnerRequest, dietSnacksRequest};*/
}
