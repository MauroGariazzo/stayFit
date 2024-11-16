package com.stayFit.dailyNutrition;

public class DailyNutritionResponseUpdateDTO {
	public int id;
	public double dailyCalories;
	public double dailyProteins;
	public double dailyCarbs;
	public double dailyFats;		
	public int fk_user;

	
	public DailyNutritionResponseUpdateDTO(int id, int calories, int proteins, int carbs, int fats, int fk_user) {
		this.id = id;
		this.dailyCalories = calories;
		this.dailyProteins = proteins;
		this.dailyCarbs = carbs;
		this.dailyFats = fats;
		this.fk_user = fk_user;
	}
}
