package com.stayFit.models;

import java.time.LocalDate;

public class WeightDiary {
	private int id;
	private double weight;
	private LocalDate weightDateRegistration;
	private int user_fk;
	
	public WeightDiary(int id, double weight, LocalDate weightDateRegistration, int user_fk) {
		this.id = id;
		this.weight = weight;
		this.weightDateRegistration = weightDateRegistration;
		this.user_fk = user_fk;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public LocalDate getWeightDateRegistration() {
		return weightDateRegistration;
	}

	public void setWeightDateRegistration(LocalDate weightDateRegistration) {
		this.weightDateRegistration = weightDateRegistration;
	}

	public int getUser_fk() {
		return user_fk;
	}

	public void setUser_fk(int user_fk) {
		this.user_fk = user_fk;
	}
}
