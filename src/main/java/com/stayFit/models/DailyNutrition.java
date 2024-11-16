package com.stayFit.models;


public class DailyNutrition {
	private int id;	
	private int calories;
	private int proteins;
	private int carbs;
	private int fats;
	private int fk_user;
	
	public DailyNutrition() {
		
	}
	
	public DailyNutrition(int id, int calories, int proteins, int carbs, int fats, int fk_user) {
		this.id = id;		
		this.calories = calories;
		this.proteins = proteins;
		this.carbs = carbs;
		this.fats = fats;
		this.fk_user = fk_user;
	}
	
	public DailyNutrition(int calories, int proteins, int carbs, int fats, int fk_user) {		
		this.calories = calories;
		this.proteins = proteins;
		this.carbs = carbs;
		this.fats = fats;
		this.fk_user = fk_user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getFk_user() {
		return fk_user;
	}

	public void setFk_user(int fk_user) {
		this.fk_user = fk_user;
	}
}
