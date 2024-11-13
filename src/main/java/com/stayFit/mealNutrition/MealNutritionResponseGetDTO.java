package com.stayFit.mealNutrition;

import com.stayFit.enums.MealType;

public class MealNutritionResponseGetDTO {
	//public int id;
	public MealType mealType;
	public double calories;
	public double proteins;
	public double carbs;
	public double fats;		
	//public int dailyNutritionFk;

	public MealNutritionResponseGetDTO(MealType mealType, double calories, double proteins, 
			double carbs, double fats) {
		//this.id = id;
		this.mealType = mealType;
		this.calories = calories;
		this.proteins = proteins;
		this.carbs = carbs;
		this.fats = fats;
		//this.dailyNutritionFk = dailyNutritionFk;
		this.mealType = mealType;
	}
}