package com.stayFit.meal;

public class MealCreateUseCase implements IMealCreateUseCase {
	private MealDAO mealDAO;
	public MealCreateUseCase(MealDAO mealDAO) {
		this.mealDAO = mealDAO;
	}
	
	
	public int execute(MealCreateRequestDTO mealCreateRequestDTO)throws Exception {
		return mealDAO.insert(mealCreateRequestDTO);
	}
	
}
