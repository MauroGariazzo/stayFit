package com.stayFit.diet;

import com.stayFit.enums.MealType;

public class DietResponseCreateDTO {
	public int id;
	public MealType mealType;
	public double calories;
	public double proteins;
	public double sugars;
	public double fats;
	public int fk_user;

	public DietResponseCreateDTO(int id, MealType mealType, double calories, double proteins, double sugars,
			double fats, int fk_user) {
		this.id = id;
		this.mealType = mealType;
		this.calories = calories;
		this.proteins = proteins;
		this.sugars = sugars;
		this.fats = fats;
		this.fk_user = fk_user;
	}
}
