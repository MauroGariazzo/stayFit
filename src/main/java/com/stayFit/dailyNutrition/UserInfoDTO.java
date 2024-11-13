package com.stayFit.dailyNutrition;

import java.time.LocalDate;

import com.stayFit.enums.FitnessState;
import com.stayFit.enums.Gender;
import com.stayFit.enums.Goal;

public class UserInfoDTO {
	public Gender gender;
	public Goal goal;
	public FitnessState fitnessState;
	public int userCredentials_fk;
	public LocalDate birthday;
	public int height;
	public double weight;
	
	public UserInfoDTO(Gender gender, Goal goal, FitnessState fitnessState, LocalDate birthday, int height, double weight, 
			int userCredentials_fk) {
		this.goal = goal;
		this.gender = gender;
		this.fitnessState = fitnessState;
		this.birthday = birthday;
		this.height = height;
		this.weight = weight;
		this.userCredentials_fk = userCredentials_fk;
	}
}
