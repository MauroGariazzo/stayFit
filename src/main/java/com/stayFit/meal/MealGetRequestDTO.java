package com.stayFit.meal;

import java.time.LocalDate;

import com.stayFit.enums.MealType;

public class MealGetRequestDTO {
	public LocalDate mealUpdateDate;
	public MealType mealType;
	public int fk_user;
	
	public MealGetRequestDTO(LocalDate mealUpdateDate, MealType mealType, int fk_user) {
		this.mealUpdateDate = mealUpdateDate;
		this.mealType = mealType;
		this.fk_user = fk_user;
	}
}
