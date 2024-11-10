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
		List<Meal> meals= this.mealGetUseCase.execute(mealGetRequestDTO);
		if(meals.size()>0) {
			return meals.get(0).getId();
		}
		return -1;
	}
	
	public int getNewMeal(MealCreateRequestDTO mealCreateRequestDTO)throws Exception {
		return this.mealCreateUseCase.execute(mealCreateRequestDTO);
	}
	
	public List<MealGetResponseDTO>get(MealGetRequestDTO mealGetRequestDTO)throws Exception{
		List<Meal>meals = this.mealGetUseCase.execute(mealGetRequestDTO);
		List<MealGetResponseDTO>mealsDTO = new ArrayList<>();
		for(Meal meal:meals) {
			MealGetResponseDTO mgrDTO = new MealGetResponseDTO();
			mgrDTO.mealType = meal.getMealType();
			mgrDTO.fk_user = meal.getFkUser();
			mgrDTO.mealUpdateDate = meal.getDate();
			mealsDTO.add(mgrDTO);
		}
		return mealsDTO;
	}
}
