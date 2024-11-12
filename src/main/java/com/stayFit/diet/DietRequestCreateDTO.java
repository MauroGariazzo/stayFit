package com.stayFit.diet;

import com.stayFit.enums.MealType;

public class DietRequestCreateDTO {
	public MealType mealType;
	public double calories;
	public double proteins;
	public double carbs;
	public double fats;
	public int fk_user;
	
	public DietRequestCreateDTO(MealType mealType, double calories, double proteins, double carbs, double fats, int fk_user) {
		this.mealType = mealType;
		this.calories = calories;
		this.proteins = proteins;
		this.carbs = carbs;
		this.fats = fats;
		this.fk_user = fk_user;
	}
}
