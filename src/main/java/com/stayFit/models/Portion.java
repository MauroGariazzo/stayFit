package com.stayFit.models;

public class Portion {
	private int id;
	private int fkProduct;
	private int fkMeal;
	private double grams;

	public Portion(int id, int fkProduct, int fkMeal, double grams) {
		this.id = id;
		this.fkProduct = fkProduct;
		this.fkMeal = fkMeal;
		this.grams = grams;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFkProduct() {
		return fkProduct;
	}

	public void setFkProduct(int fkProduct) {
		this.fkProduct = fkProduct;
	}

	public int getFkMeal() {
		return fkMeal;
	}

	public void setFkMeal(int fkMeal) {
		this.fkMeal = fkMeal;
	}

	public double getGrams() {
		return grams;
	}

	public void setGrams(double grams) {
		this.grams = grams;
	}
}
