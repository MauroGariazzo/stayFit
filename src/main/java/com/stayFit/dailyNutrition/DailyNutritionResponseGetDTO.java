package com.stayFit.dailyNutrition;

public class DailyNutritionResponseGetDTO {
	public int id;
	public int dailyCalories;
	public int dailyProteins;
	public int dailyCarbs;
	public int dailyFats;
		
	public int fk_user;

	public DailyNutritionResponseGetDTO(int id, int dailyCalories, int dailyProteins, 
			int dailyCarbs, int dailyFats, int fk_user) {
		this.id = id;
		this.dailyCalories = dailyCalories;
		this.dailyProteins = dailyProteins;
		this.dailyCarbs = dailyCarbs;
		this.dailyFats = dailyFats;
		this.fk_user = fk_user;
	}
	
	public DailyNutritionResponseGetDTO() {
		
	}
}
