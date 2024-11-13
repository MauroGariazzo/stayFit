package com.stayFit.mealNutrition;

public class DailyNutritionDTO {
	//public MealType mealType;
	public int calories;
	public int proteins;
	public int carbs;
	public int fats;
	public int dailyNutritionFk;	
	
	public DailyNutritionDTO(int calories, int proteins, int carbs, int fats,
			int dailyNutritionFk) {
		
		//this.mealType = mealType;
		this.calories = calories;
		this.proteins = proteins;
		this.carbs = carbs;
		this.fats = fats;
		this.dailyNutritionFk = dailyNutritionFk;
	}
}
