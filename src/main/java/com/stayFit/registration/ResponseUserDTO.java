package com.stayFit.registration;

import java.time.LocalDate;
import com.stayFit.enums.FitnessState;
import com.stayFit.enums.Gender;
import com.stayFit.enums.Goal;

public class ResponseUserDTO {
	public int id;
	public String name;
	public String surname;
	public int height;
	public double weight;
	public LocalDate birthday;
	public FitnessState fitnessState;
	public LocalDate subscriptionDate;
	public double BMI;
	public Gender gender;
	public Goal goal;
	public int userCredentials_fk;
	
	public ResponseUserDTO(int id, String name, String surname, int height, double weight, LocalDate birthday,
			FitnessState fitnessState, Gender gender, Goal goal, LocalDate subscriptionDate, int userCredentials_fk) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.height = height;
		this.weight = weight;
		this.birthday = birthday;
		this.fitnessState = fitnessState;
		this.gender = gender;
		this.goal = goal;
		this.subscriptionDate = subscriptionDate;
		this.userCredentials_fk = userCredentials_fk;
	}
	
	public ResponseUserDTO(int id, String name, String surname, int height, double weight, LocalDate birthday,
			FitnessState fitnessState, double BMI, Gender gender, Goal goal, LocalDate subscriptionDate, int userCredentials_fk) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.height = height;
		this.weight = weight;
		this.birthday = birthday;
		this.fitnessState = fitnessState;
		this.BMI = BMI;
		this.gender = gender;
		this.goal = goal;
		this.subscriptionDate = subscriptionDate;
		this.userCredentials_fk = userCredentials_fk;
	}
}


/*String query = "INSERT INTO stayfit.stayfituser(stayFitUser_name, stayFitUser_surname, "
+ "height, weight, fitnessState, bmi, birthday, subscriptionDate, userCredentials_fk) "
+ "VALUES(?,?,?,?,?,?,?,?,?)";*/
