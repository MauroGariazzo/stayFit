package com.stayFit.meal;

import java.time.LocalDate;

import com.stayFit.enums.MealType;

public class MealGetResponseDTO {
	public int id;
	public LocalDate mealUpdateDate;
	public MealType mealType;
	public int fk_user;
	
	public double calories;
	public double proteins;
	public double fats;
	public double carbs;
	public double sugars;
	public double salt;
}
