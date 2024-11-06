package com.stayFit.models;
import java.time.LocalDate;

import com.stayFit.enums.MealType;

public class Meal {
	private int id;
	private LocalDate date;
	private MealType mealType;
	private int fkUser;
	
	public Meal(int id, LocalDate date, MealType mealType, int fkUser) {
		this.id = id;
		this.date = date;
		this.mealType = mealType;
		this.fkUser = fkUser;
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
	
	
}
