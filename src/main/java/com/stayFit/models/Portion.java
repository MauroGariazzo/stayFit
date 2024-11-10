package com.stayFit.models;

public class Portion {
	private int id;
	private int fkProduct;
	private String productName;
	private int fkMeal;
	private int mealType;	
	private double grams;
	private double calories;
	private double proteins;
	private double fats;
	private double carbs;
	private double sugars;
	private double salt;
	
	public Portion(int id, String productName, int fkProduct, int fkMeal, double grams, double calories, double proteins, 
			double fats, double carbs, double sugars, double salt, int mealType) {
		this.productName = productName;
		this.id = id;
		this.fkProduct = fkProduct;
		this.fkMeal = fkMeal;
		this.grams = grams;
		this.calories = calories;
		this.proteins = proteins;
		this.fats = fats;
		this.carbs = carbs;
		this.sugars = sugars;
		this.salt = salt;		
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getProductName() {
		return productName;
	}

	public double getCalories() {
		return calories;
	}

	public void setCalories(double calories) {
		this.calories = calories;
	}

	public double getProteins() {
		return proteins;
	}

	public void setProteins(double proteins) {
		this.proteins = proteins;
	}

	public double getFats() {
		return fats;
	}

	public void setFats(double fats) {
		this.fats = fats;
	}

	public double getCarbs() {
		return carbs;
	}

	public void setCarbs(double carbs) {
		this.carbs = carbs;
	}

	public double getSugars() {
		return sugars;
	}

	public void setSugars(double sugars) {
		this.sugars = sugars;
	}

	public double getSalt() {
		return salt;
	}

	public void setSalt(double salt) {
		this.salt = salt;
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
	
	public int getMealType() {
		return mealType;
	}

	public void setMealType(int mealType) {
		this.mealType = mealType;
	}
}
