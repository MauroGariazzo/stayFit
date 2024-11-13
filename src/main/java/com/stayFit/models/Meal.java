package com.stayFit.models;
import java.time.LocalDate;

import com.stayFit.enums.MealType;

public class Meal {
	private int id;
	private LocalDate date;
	private MealType mealType;
	private int fkUser;
	private double calories;
	private double proteins;
	private double fats;
	private double carbs;
	private double sugars;
	private double salt;
	private boolean isTerminated;
		
	public Meal(int id, LocalDate date, MealType mealType, int fkUser) {
		this.id = id;
		this.date = date;
		this.mealType = mealType;
		this.fkUser = fkUser;
	}
	
	public Meal(int id, LocalDate date, int fkUser, double calories, double proteins, double fats,
			double carbs, double sugars, double salt) {
		this.id = id;
		this.date = date;
		//this.mealType = mealType;
		this.fkUser = fkUser;
		this.calories = calories;
		this.proteins = proteins;
		this.fats = fats;
		this.carbs = carbs;
		this.sugars = sugars;
		this.salt = salt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public MealType getMealType() {
		return mealType;
	}

	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}

	public int getFkUser() {
		return fkUser;
	}

	public void setFkUser(int fkUser) {
		this.fkUser = fkUser;
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

	public double getFats() {
		return fats;
	}

	public void setFats(double fats) {
		this.fats = fats;
	}

	public double getCarbs() {
		return carbs;
	}

	public void setCarbs(double carbs) {
		this.carbs = carbs;
	}

	public double getSugars() {
		return sugars;
	}

	public void setSugars(double sugars) {
		this.sugars = sugars;
	}

	public double getSalt() {
		return salt;
	}

	public void setSalt(double salt) {
		this.salt = salt;
	}
	
	public boolean getIsTerminated() {
		return isTerminated;
	}

	public void setIsTerminated(boolean isTerminated) {
		this.isTerminated = isTerminated;
	}

}
