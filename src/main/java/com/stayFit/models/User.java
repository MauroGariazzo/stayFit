package com.stayFit.models;

import java.time.LocalDate;

import com.stayFit.enums.FitnessState;
import com.stayFit.enums.Gender;
import com.stayFit.enums.Goal;

public class User {
	private int id;
	private String name;
	private String surname;
	private int height;
	private double weight;
	private LocalDate birthday;
	private FitnessState fitnessState;	
	private double BMI;
	private Gender gender;
	private Goal goal;
	private LocalDate subscriptionDate;
	private int userCredentials_fk;
	
	public User() {
		
	}

	public User(int id, String name, String surname, int height, double weight, LocalDate birthday, 
			FitnessState fitnessState, double BMI, Gender gender, Goal goal, LocalDate subscriptionDate,
			int userCredentials_fk) {	
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
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Goal getGoal() {
		return goal;
	}

	public void setGoal(Goal goal) {
		this.goal = goal;
	}

	public LocalDate getSubscriptionDate() {
		return subscriptionDate;
	}

	public void setSubscriptionDate(LocalDate subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}

	public int getUserCredentials_fk() {
		return userCredentials_fk;
	}

	public void setUserCredentials_fk(int userCredentials_fk) {
		this.userCredentials_fk = userCredentials_fk;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public LocalDate getBirthday() {
		return birthday;
	}
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	public FitnessState getFitnessState() {
		return fitnessState;
	}
	public void setFitnessState(FitnessState fitnessState) {
		this.fitnessState = fitnessState;
	}

	public double getBMI() {
		return BMI;
	}
	public void setBMI(double bMI) {
		BMI = bMI;
	}
	
	public int getUserCredentials() {
		return userCredentials_fk;
	}

	public void setUserCredentials(int userCredentials) {
		this.userCredentials_fk = userCredentials;
	}
	
	public String toString() {
		return String.format("%s %s", this.name, this.surname);
	}
	
}
