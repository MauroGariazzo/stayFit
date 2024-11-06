package com.stayFit.models;

import java.time.LocalDate;

import com.stayFit.enums.FitnessState;

public class User {
	private int id;
	private String name;
	private String surname;
	private int height;
	private double weight;
	private LocalDate birthday;
	private FitnessState fitnessState;	
	private String username;
	private String password;
	private double BMI;
	
	public User(int id, String name, String surname, int height, double weight, LocalDate birthday, FitnessState fitnessState,
			String username, String password, double BMI) {
		
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.height = height;
		this.weight = weight;
		this.birthday = birthday;
		this.fitnessState = fitnessState;
		this.username = username;
		this.password = password;
		this.BMI = BMI;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public double getBMI() {
		return BMI;
	}
	public void setBMI(double bMI) {
		BMI = bMI;
	}
	
	public String toString() {
		return String.format("%s %s", this.name, this.surname);
	}
	
}
