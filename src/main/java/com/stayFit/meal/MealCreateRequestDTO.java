package com.stayFit.meal;

import java.time.LocalDate;

import com.stayFit.enums.MealType;

public class MealCreateRequestDTO {
	public LocalDate mealUpdateDate;
	public MealType mealType;
	public int fk_user;
}
