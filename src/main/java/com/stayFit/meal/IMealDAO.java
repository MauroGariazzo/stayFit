package com.stayFit.meal;

import java.time.LocalDate;
import java.util.List;

import com.stayFit.enums.MealType;
import com.stayFit.models.Meal;

public interface IMealDAO {
	public int insert(MealCreateRequestDTO mealCreateRequestDTO) throws Exception;
	//public void update() throws Exception;
	//public void delete() throws Exception;
	//public Meal get() throws Exception;
	public List<Meal> getAll(MealGetRequestDTO mealGetRequestDTO) throws Exception;
}
