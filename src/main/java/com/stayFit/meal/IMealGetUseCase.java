package com.stayFit.meal;

import java.util.List;
import com.stayFit.models.Meal;

public interface IMealGetUseCase {
	public List<Meal>execute(MealGetRequestDTO mealGetRequestDTO)throws Exception;
}
