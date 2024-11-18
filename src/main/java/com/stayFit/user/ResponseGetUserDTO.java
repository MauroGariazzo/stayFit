package com.stayFit.user;

import java.time.LocalDate;

import com.stayFit.enums.FitnessState;
import com.stayFit.enums.Gender;
import com.stayFit.enums.Goal;

public class ResponseGetUserDTO {
	public int id;
	public String name;
	public String surname;
	public int height;
	public double weight;
	public LocalDate birthday;
	public FitnessState fitnessState;	
	public double BMI;
	public Gender gender;
	public Goal goal;
	public LocalDate subscriptionDate;
	public int userCredentials_fk;

	
	/*return new ResponseGetUserDTO(user.getName(), user.getSurname(), user.getFitnessState(), user.getGender(), user.getGoal(), 
				user.getBirthday(), user.getHeight(), user.getWeight(), user.getUserCredentials());*/
	public ResponseGetUserDTO(String name, String surname, FitnessState fitnessState, Gender gender, Goal goal, LocalDate birthday, 
			int height, double weight, int userCredentials_fk) {
		this.name = name;
		this.surname = surname;
		this.height = height;
		this.weight = weight;
		this.birthday = birthday;
		this.fitnessState = fitnessState;
		this.gender = gender;
		this.goal = goal;
		this.userCredentials_fk = userCredentials_fk;
	}
}
