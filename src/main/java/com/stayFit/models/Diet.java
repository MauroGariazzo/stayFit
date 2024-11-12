package com.stayFit.models;

import com.stayFit.enums.MealType;

public class Diet {
	private int id;
	private MealType mealType;
	private double calories;
	private double proteins;
	private double sugars;
	private double fats;
	private int fk_user;
	
	public Diet(int id, MealType mealType, double calories, double proteins, double sugars, double fats, int fk_user) {
		this.id = id;
		this.mealType = mealType;
		this.calories = calories;
		this.proteins = proteins;
		this.sugars = sugars;
		this.fats = fats;
		this.fk_user = fk_user;
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

	public double getCalories() {
		return calories;
	}

	public void setCalories(double calories) {
		this.calories = calories;
	}

	public double getProteins() {
		return proteins;
	}

	public void setProteins(double proteins) {
		this.proteins = proteins;
	}

	public double getSugars() {
		return sugars;
	}

	public void setSugars(double sugars) {
		this.sugars = sugars;
	}

	public double getFats() {
		return fats;
	}

	public void setFats(double fats) {
		this.fats = fats;
	}

	public int getFk_user() {
		return fk_user;
	}

	public void setFk_user(int fk_user) {
		this.fk_user = fk_user;
	}
}
