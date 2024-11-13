package com.stayFit.mealNutrition;

import com.stayFit.enums.MealType;

public class MealNutritionRequestGetDTO {
	public int id;
	public MealType mealType;
	public int calories;
	public int proteins;
	public int carbs;
	public int fats;
	public int dailyNutritionFk;
	
	public MealNutritionRequestGetDTO(int id, MealType mealType, int calories, int proteins, int carbs, int fats,
			int dailyNutritionFk) {
		
		this.id = id;
		this.mealType = mealType;
		this.calories = calories;
		this.proteins = proteins;
		this.carbs = carbs;
		this.fats = fats;
		this.dailyNutritionFk = dailyNutritionFk;
	}
}
