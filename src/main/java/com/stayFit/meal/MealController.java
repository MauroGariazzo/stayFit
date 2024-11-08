package com.stayFit.meal;

public class MealController {
	private IMealCreateUseCase mealCreateUseCase;	
	
	public MealController(IMealCreateUseCase mealCreateUseCase) {
		this.mealCreateUseCase = mealCreateUseCase;		
	}
	
	public int create(MealCreateRequestDTO mealCreateRequestDTO)throws Exception {
		return this.mealCreateUseCase.execute(mealCreateRequestDTO);
	}
}
