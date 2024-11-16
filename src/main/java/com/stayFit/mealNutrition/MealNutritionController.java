package com.stayFit.mealNutrition;

import com.stayFit.dailyNutrition.DailyNutritionResponseCreateDTO;
import com.stayFit.dailyNutrition.DailyNutritionResponseUpdateDTO;
import com.stayFit.enums.MealType;
import com.stayFit.models.MealNutrition;

public class MealNutritionController {
	private IMealNutritionCreateUseCase mealNutritionCreateUseCase;
	private IMealNutritionGetUseCase mealNutritionGetUseCase;
	private IMealNutritionUpdateUseCase mealNutritionUpdateUseCase;
	
	public MealNutritionController(IMealNutritionCreateUseCase mealNutritionCreateUseCase) {
		this.mealNutritionCreateUseCase = mealNutritionCreateUseCase;
	}
	
	public MealNutritionController(IMealNutritionGetUseCase mealNutritionGetUseCase) {
		this.mealNutritionGetUseCase = mealNutritionGetUseCase;
	}
	
	public MealNutritionController(IMealNutritionUpdateUseCase mealNutritionUpdateUseCase) {
		this.mealNutritionUpdateUseCase = mealNutritionUpdateUseCase;
	}

	
	public void create(DailyNutritionResponseCreateDTO dailyNutritionDTO) throws Exception {
		mealNutritionCreateUseCase.createDiet(dailyNutritionDTO);
	}
	
	public void update(DailyNutritionResponseUpdateDTO dailyNutritionDTO) throws Exception {
		mealNutritionUpdateUseCase.updateDiet(dailyNutritionDTO);
	}
	
	public MealNutritionResponseGetDTO get(int dailyNutritionId, MealType mealType) throws Exception {		
		MealNutrition mealNutrition = mealNutritionGetUseCase.get(dailyNutritionId, mealType);
		//System.out.println(mealType);
		return new MealNutritionResponseGetDTO(mealNutrition.getMealType(), mealNutrition.getCalories(), mealNutrition.getProteins(), 
				mealNutrition.getCarbs(), mealNutrition.getFats());
	}
}
