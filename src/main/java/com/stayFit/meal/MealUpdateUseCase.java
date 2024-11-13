package com.stayFit.meal;

import java.time.LocalDate;

public class MealUpdateUseCase implements IMealUpdateUseCase {
	private IMealDAO mealDAO;
	
	public MealUpdateUseCase(IMealDAO mealDAO) {
		this.mealDAO = mealDAO;
	}
		
	public void terminateDay(LocalDate date)throws Exception {
		mealDAO.terminateDay(date);
	}
	
}