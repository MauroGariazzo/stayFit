package com.stayFit.meal;

import java.time.LocalDate;
import java.util.List;
import com.stayFit.models.Meal;

public interface IMealDAO {
	public int insert(MealCreateRequestDTO mealCreateRequestDTO) throws Exception;
	//public void update() throws Exception;
	//public void delete() throws Exception;
	//public Meal get() throws Exception;
	public List<Meal> getExistingMeals(MealGetRequestDTO mealGetRequestDTO) throws Exception;
	public List<Meal> getDailyNutritionalValues(MealGetRequestDTO mealGetRequestDTO) throws Exception;
	public void terminateDay(LocalDate date) throws Exception;
}
