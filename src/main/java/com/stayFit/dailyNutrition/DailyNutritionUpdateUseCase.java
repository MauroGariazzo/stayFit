package com.stayFit.dailyNutrition;

import java.time.LocalDate;
import java.time.Period;

import com.stayFit.enums.FitnessState;
import com.stayFit.enums.Gender;
import com.stayFit.enums.Goal;
import com.stayFit.models.DailyNutrition;

public class DailyNutritionUpdateUseCase implements IDailyNutritionUpdateUseCase{
	private IDailyNutritionDAO updateDietDAO;
	
	public DailyNutritionUpdateUseCase(IDailyNutritionDAO updateDietDAO) {
		this.updateDietDAO = updateDietDAO;
	}
	
	public DailyNutrition update(UserInfoDTO userInfo)throws Exception {		
		DailyNutritionRequestUpdateDTO diet = calculateDiet(userInfo);		
		return updateDietDAO.update(diet);
	}
		
	private DailyNutritionRequestUpdateDTO calculateDiet(UserInfoDTO userInfoDTO) {
		int age = Period.between(userInfoDTO.birthday, LocalDate.now()).getYears();
		double BMR = 0;
		if(userInfoDTO.gender == Gender.MASCHIO) {
			BMR = (10 * userInfoDTO.weight) + (6.25 * userInfoDTO.height) - (5 * age) + 5;
		}
		
		else {
			BMR = (10 * userInfoDTO.weight) + (6.25 * userInfoDTO.height) - (5 * age) - 161;
		}
				
		double TDEE = calculateTDEE(BMR, userInfoDTO.fitnessState);
		System.out.println("il tuo tdee: " + TDEE);
		return getDiet(TDEE, userInfoDTO);
	}
	
	private DailyNutritionRequestUpdateDTO getDiet(double TDEE, UserInfoDTO userInfoDTO) {
		// Calorie quotidiane TOTAL
		double totalDailyCalories = calculateCalories(TDEE, userInfoDTO.goal);
		double totalDailyGramsProteins = calculateProteins(totalDailyCalories, userInfoDTO.goal); //125g proteine
		double totalDailyGramsCarbs = calculateCarbs(totalDailyCalories, userInfoDTO.goal);
		double totalDailyGramsFats = calculateFats(totalDailyCalories, userInfoDTO.goal);
				
		/*int id, int calories, int proteins, int carbs, int fats, int fk_user)*/
		return new DailyNutritionRequestUpdateDTO((int)totalDailyCalories, (int)totalDailyGramsProteins, 
				(int)totalDailyGramsCarbs, (int)totalDailyGramsFats, userInfoDTO.id);
	}
	
	private double calculateTDEE(double BMR, FitnessState fitnessState) {
		double TDEE = 0;
		switch (fitnessState) {
		case FitnessState.SEDENTARIO:
			TDEE = BMR * 1;
			break;
		case FitnessState.POCO_ATTIVO:
			TDEE = BMR * 1.2;
			break;
		case FitnessState.MEDIAMENTE_ATTIVO:
			TDEE = BMR * 1.375;
			break;
		case FitnessState.MOLTO_ATTIVO:
			TDEE = BMR * 1.55;
			break;
		}
		return TDEE;
	}
		
	
	private double calculateCalories(double TDEE, Goal goal) {		
		double calories = TDEE;		
		switch(goal) {
			case PERDERE_PESO:				
				calories -= 700;
				break;
			case MANTENERE_PESO:
				calories = TDEE;
				break;
			case METTERE_MASSA_MUSCOLARE:
				calories += 450;
				break;
		}		
		return calories;
	}
	
	private double calculateProteins(double calories, Goal goal) {
		double proteins = 0;
		switch(goal) {
			case Goal.PERDERE_PESO:
				proteins = 0.22 * calories;
				proteins /= 4;
				break;
			case Goal.MANTENERE_PESO:
				proteins = 0.2 * calories;
				proteins /= 4;
				break;
			case Goal.METTERE_MASSA_MUSCOLARE:
				proteins = 0.23 * calories;
				proteins /= 4;
				break;
		}
		return proteins;
	}
	
	private double calculateCarbs(double calories, Goal goal) {
		double carbs = 0;
		switch(goal) {
			case Goal.PERDERE_PESO:
				carbs = 0.45 * calories;
				carbs /= 4;
				break;
			case Goal.MANTENERE_PESO:
				carbs = 0.5 * calories;
				carbs /= 4;
				break;
			case Goal.METTERE_MASSA_MUSCOLARE:
				carbs = 0.52 * calories;
				carbs /= 4;
				break;
		}
		return carbs;
	}
	
	private double calculateFats(double calories, Goal goal) {
		double fats = 0;
		switch(goal) {
			case Goal.PERDERE_PESO:
				fats = 0.225 * calories;
				fats /= 9;
				break;
			case Goal.MANTENERE_PESO:
				fats = 0.27 * calories;
				fats /= 9;
				break;
			case Goal.METTERE_MASSA_MUSCOLARE:
				fats = 0.24 * calories;
				fats /= 9;
				break;
		}
		return fats;
	}
}
