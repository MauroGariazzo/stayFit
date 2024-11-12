package com.stayFit.diet;

import java.time.LocalDate;
import com.stayFit.enums.FitnessState;
import com.stayFit.enums.Gender;
import com.stayFit.enums.Goal;

public class RequestCreateUserInfoDTO {
	public int height;
	public double weight;
	public LocalDate birthday;
	public FitnessState fitnessState;
	public Gender gender;
	public Goal goal;
	public int userCredentials_fk;

	public RequestCreateUserInfoDTO(int height, double weight, LocalDate birthday, FitnessState fitnessState,
			Gender gender, Goal goal, int userCredentials_fk) {
		
		this.height = height;
		this.weight = weight;
		this.birthday = birthday;
		this.fitnessState = fitnessState;
		this.gender = gender;
		this.goal = goal;
		this.userCredentials_fk = userCredentials_fk;
	}

}
