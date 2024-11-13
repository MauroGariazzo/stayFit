package com.stayFit.models;

import com.stayFit.enums.MealType;

public class MealNutrition {
	private int id;
	private MealType mealType;
	private int calories;
	private int proteins;
	private int carbs;
	private int fats;
	private int dailyNutritionFk;
	
	public MealNutrition() {
		
	}
	
	public MealNutrition(int id, MealType mealType, int calories, int proteins, int carbs, int fats,
			int dailyNutritionFk) {
		this.id = id;
		this.mealType = mealType;
		this.calories = calories;
		this.proteins = proteins;
		this.carbs = carbs;
		this.fats = fats;
		this.dailyNutritionFk = dailyNutritionFk;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MealType getMealType() {
		return mealType;
	}

	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}

	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public int getProteins() {
		return proteins;
	}

	public void setProteins(int proteins) {
		this.proteins = proteins;
	}

	public int getCarbs() {
		return carbs;
	}

	public void setCarbs(int carbs) {
		this.carbs = carbs;
	}

	public int getFats() {
		return fats;
	}

	public void setFats(int fats) {
		this.fats = fats;
	}

	public int getDailyNutritionFk() {
		return dailyNutritionFk;
	}

	public void setDailyNutritionFk(int dailyNutritionFk) {
		this.dailyNutritionFk = dailyNutritionFk;
	}


}
