package com.stayFit.portion;

import java.time.LocalDate;

import com.stayFit.enums.MealType;

public class PortionGetRequestDTO {
	public MealType mealType;
	public LocalDate mealUpdateDate;
	public int fk_user;
	
	
	public PortionGetRequestDTO(MealType mealType, LocalDate mealUpdateDate, int fk_user) {
		this.mealType = mealType;
		this.mealUpdateDate = mealUpdateDate;
		this.fk_user = fk_user;
	}
}
