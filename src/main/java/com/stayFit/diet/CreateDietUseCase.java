package com.stayFit.diet;

import java.time.LocalDate;
import java.time.Period;

import com.stayFit.enums.FitnessState;
import com.stayFit.enums.Goal;
import com.stayFit.enums.MealType;

public class CreateDietUseCase implements ICreateDietUseCase{
	private ICreateDietDAO createDietDAO;
	
	public CreateDietUseCase(ICreateDietDAO createDietDAO) {
		this.createDietDAO = createDietDAO;
	}
	
	public void create(UserInfoDTO userInfo)throws Exception {
		//calcolo calorie, proteine ecc.
		DietRequestCreateDTO[]diets = calculateMaleDiet(userInfo);
		for(DietRequestCreateDTO diet:diets) {
			createDietDAO.insert(diet);
		}		
	}
	
	
	private DietRequestCreateDTO[] calculateMaleDiet(UserInfoDTO userInfoDTO) {
		int age = Period.between(userInfoDTO.birthday, LocalDate.now()).getYears();
		double BMR = (10 * userInfoDTO.weight) + (6.25 * userInfoDTO.height) - (5 * age) + 5;		
		double TDEE = calculateTDEE(BMR, userInfoDTO.fitnessState);
		return calculateGrammageForEachNutrient(TDEE, userInfoDTO);				
	}

	private double calculateTDEE(double BMR, FitnessState fitnessState) {
		double TDEE = 0;
		switch (fitnessState) {
		case FitnessState.SEDENTARIO:
			TDEE = BMR * 1.2;
			break;
		case FitnessState.POCO_ATTIVO:
			TDEE = BMR * 1.375;
			break;
		case FitnessState.MEDIAMENTE_ATTIVO:
			TDEE = BMR * 1.55;
			break;
		case FitnessState.MOLTO_ATTIVO:
			TDEE = BMR * 1.725;
			break;
		}
		return TDEE;
	}
	
	private DietRequestCreateDTO[] calculateGrammageForEachNutrient(double TDEE, UserInfoDTO userInfoDTO) {
		// Calorie quotidiane TOTAL
		double totalDailyCalories = calculateCalories(TDEE, userInfoDTO.goal);
		// Calorie TOTALI DIVISE per pasto
		double totalBreakfastCalories = 0.25 * totalDailyCalories;
		double totalLunchCalories = 0.3 * totalDailyCalories;
		double totalDinnerCalories = 0.3 * totalDailyCalories;
		double totalSnacksCalories = 0.15 * totalDailyCalories;
		
		// Totale proteine, carboidrati, grassi da assumere al giorno		
		double totalDailyGramsProteins = calculateProteins(totalDailyCalories, userInfoDTO.goal); //125g proteine
		double totalDailyGramsCarbs = calculateCarbs(totalDailyCalories, userInfoDTO.goal);
		double totalDailyGramsFats = calculateFats(totalDailyCalories, userInfoDTO.goal);
		
		// Totale calorie macronutrienti(proteine, carboidrati, grassi) per pasto
		double breakfastProteins = 0.15 * totalDailyGramsProteins;
		double breakfastCarbs = 0.15 * totalDailyGramsCarbs;
		double breakfastFats = 0.15 * totalDailyGramsFats;
		
		double lunchProteins = 0.18 * totalDailyGramsProteins;
		double lunchCarbs = 0.18 * totalDailyGramsCarbs;
		double lunchFats = 0.18 * totalDailyGramsFats;
		
		double dinnerProteins = 0.21 * totalDailyGramsProteins;
		double dinnerCarbs = 0.21 * totalDailyGramsCarbs;
		double dinnerFats = 0.21 * totalDailyGramsFats;
		
		double snacksProteins = 0.45 * totalDailyGramsProteins;
		double snacksCarbs = 0.45 * totalDailyGramsCarbs;
		double snacksFats = 0.45 * totalDailyGramsFats;
		
		/*MealType mealType, double calories, double proteins, double sugars, double fats, int fk_user*/
		DietRequestCreateDTO dietBreakfastRequest = new DietRequestCreateDTO(MealType.COLAZIONE, (int)totalBreakfastCalories,
				(int)breakfastProteins, (int)breakfastCarbs, (int)breakfastFats, userInfoDTO.userCredentials_fk);
		
		DietRequestCreateDTO dietLunchRequest = new DietRequestCreateDTO(MealType.PRANZO, (int)totalLunchCalories,
				(int)lunchProteins, (int)lunchCarbs, (int)lunchFats, userInfoDTO.userCredentials_fk);
		
		DietRequestCreateDTO dietDinnerRequest = new DietRequestCreateDTO(MealType.CENA, (int)totalDinnerCalories,
				(int)dinnerProteins, (int)dinnerCarbs, (int)dinnerFats, userInfoDTO.userCredentials_fk);
		
		DietRequestCreateDTO dietSnacksRequest = new DietRequestCreateDTO(MealType.SPUNTINO, (int)totalSnacksCalories,
				(int)snacksProteins, (int)snacksCarbs, (int)snacksFats, userInfoDTO.userCredentials_fk);
		
		return new DietRequestCreateDTO[] {dietBreakfastRequest, dietLunchRequest, dietDinnerRequest, dietSnacksRequest};
	}
	
	
	private double calculateCalories(double TDEE, Goal goal) {		
		double calories = TDEE;
		switch(goal) {
			case PERDERE_PESO:				
				calories -= 450;
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
