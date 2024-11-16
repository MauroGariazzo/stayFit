package com.stayFit.dailyNutrition;

import com.stayFit.models.DailyNutrition;

public class DailyNutritionController {
	private IDailyNutritionCreateUseCase dailyNutritionCreateUseCase;
	private IDailyNutritionGetUseCase dailyNutritionGetUseCase;
	private IDailyNutritionUpdateUseCase dailyNutritionUpdateUseCase;
	
	public DailyNutritionController(IDailyNutritionCreateUseCase dailyNutritionCreateUseCase) {
		this.dailyNutritionCreateUseCase = dailyNutritionCreateUseCase;
	}
	
	public DailyNutritionController(IDailyNutritionGetUseCase dailyNutritionGetUseCase) {
		this.dailyNutritionGetUseCase = dailyNutritionGetUseCase;
	}
	
	public DailyNutritionController(IDailyNutritionUpdateUseCase dailyNutritionUpdateUseCase) {
		this.dailyNutritionUpdateUseCase = dailyNutritionUpdateUseCase;
	}

	public DailyNutritionResponseCreateDTO create(UserInfoDTO userInfoDTO) throws Exception {
		DailyNutrition dailyNutrition =  dailyNutritionCreateUseCase.create(userInfoDTO);	
		return new DailyNutritionResponseCreateDTO(dailyNutrition.getId(), dailyNutrition.getCalories(), 
				dailyNutrition.getProteins(), dailyNutrition.getCarbs(), dailyNutrition.getFats(), dailyNutrition.getFk_user());
	}
	
	public DailyNutritionResponseUpdateDTO update(UserInfoDTO userInfoDTO) throws Exception {
		DailyNutrition dailyNutrition = dailyNutritionUpdateUseCase.update(userInfoDTO);	
		/*int id, int calories, int proteins, int carbs, int fats, int fk_user*/
		return new DailyNutritionResponseUpdateDTO(dailyNutrition.getId(), dailyNutrition.getCalories(), 
				dailyNutrition.getProteins(), dailyNutrition.getCarbs(), dailyNutrition.getFats(), 
				dailyNutrition.getFk_user());
	}
	
	public DailyNutritionResponseGetDTO get(int userId) throws Exception{		
		DailyNutrition dailyNutrition =  dailyNutritionGetUseCase.getDiet(userId);
		return new DailyNutritionResponseGetDTO(dailyNutrition.getId(), dailyNutrition.getCalories(),
				dailyNutrition.getProteins(), dailyNutrition.getCarbs(), dailyNutrition.getFats(),
				dailyNutrition.getFk_user());
	}	
}

