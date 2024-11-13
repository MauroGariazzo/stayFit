package com.stayFit.dailyNutrition;

public class DailyNutritionResponseCreateDTO {
	public int id;
	public double dailyCalories;
	public double dailyProteins;
	public double dailyCarbs;
	public double dailyFats;		
	public int fk_user;

	public DailyNutritionResponseCreateDTO(int id, double dailyCalories, double dailyProteins, 
			double dailyCarbs, double dailyFats, int fk_user) {
		this.id = id;
		this.dailyCalories = dailyCalories;
		this.dailyProteins = dailyProteins;
		this.dailyCarbs = dailyCarbs;
		this.dailyFats = dailyFats;
		this.fk_user = fk_user;
	}
}
