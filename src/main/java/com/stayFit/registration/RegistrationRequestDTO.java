package com.stayFit.registration;

import java.time.LocalDate;

import com.stayFit.enums.FitnessState;

public class RegistrationRequestDTO {
	public String name;
	public String surname;
	public int height;
	public double weight;
	public LocalDate birthday;
	public FitnessState fitnessState;
	public String username;
	public String password;
	public double BMI;
}

