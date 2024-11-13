package com.stayFit.dailyNutrition;

public class DailyNutritionRequestCreateDTO {	
	public int calories;
	public int proteins;
	public int carbs;
	public int fats;
	public int fk_user;
	
	public DailyNutritionRequestCreateDTO(int calories, int proteins, int carbs, int fats, int fk_user) {		
		this.calories = calories;
		this.proteins = proteins;
		this.carbs = carbs;
		this.fats = fats;
		this.fk_user = fk_user;
	}
}
