package com.stayFit.user;

import java.time.LocalDate;
import com.stayFit.enums.FitnessState;
import com.stayFit.enums.Gender;
import com.stayFit.enums.Goal;

public class RequestUpdateUserDTO {
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

	
	public RequestUpdateUserDTO(int id, String name, String surname, LocalDate birthday, FitnessState fitnessState, Gender gender, 
			Goal goal, int height, double weight) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.height = height;
		this.weight = weight;
		this.birthday = birthday;
		this.fitnessState = fitnessState;
		this.gender = gender;
		this.goal = goal;
	}
}
