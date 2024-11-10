package com.stayFit.meal;

import java.util.ArrayList;
import java.util.List;
import com.stayFit.models.Meal;

public class MealController {
	private IMealGetUseCase mealGetUseCase;
	private IMealCreateUseCase mealCreateUseCase;
	
	public MealController(IMealGetUseCase mealGetUseCase, IMealCreateUseCase mealCreateUseCase) {
		this.mealGetUseCase = mealGetUseCase;
		this.mealCreateUseCase = mealCreateUseCase;
	}
	
	
	public int getExistingMeal(MealGetRequestDTO mealGetRequestDTO)throws Exception {
		List<Meal> meals= this.mealGetUseCase.getExistingMeals(mealGetRequestDTO);
		if(meals.size()>0) {
			return meals.get(0).getId();
		}
		return -1;
	}
	
	public int getNewMeal(MealCreateRequestDTO mealCreateRequestDTO)throws Exception {
		return this.mealCreateUseCase.execute(mealCreateRequestDTO);
	}
	
	public List<MealGetResponseDTO>getDailyNutritions(MealGetRequestDTO mealGetRequestDTO)throws Exception{
		List<Meal>meals = this.mealGetUseCase.getDailyNutritionalValues(mealGetRequestDTO);
		List<MealGetResponseDTO>mealsDTO = new ArrayList<>();
		for(Meal meal:meals) {
			MealGetResponseDTO mgrDTO = new MealGetResponseDTO();			
			mgrDTO.fk_user = meal.getFkUser();
			mgrDTO.mealUpdateDate = meal.getDate();
			mgrDTO.calories = meal.getCalories();			
			mgrDTO.proteins = meal.getProteins();
			mgrDTO.fats = meal.getFats();
			mgrDTO.carbs = meal.getCarbs();
			mgrDTO.sugars = meal.getSugars();
			mgrDTO.salt = meal.getSalt();
			mealsDTO.add(mgrDTO);
		}
		return mealsDTO;
	}
}
