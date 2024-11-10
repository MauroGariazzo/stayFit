package com.stayFit.portion;

public class PortionCreateRequestDTO {
	public int product_fk;
	public int meal_fk;
	public double grams;
	public double calories;
	public double proteins;
	public double fats;
	public double carbs;
	public double sugars;
	public double salt;
	
	public PortionCreateRequestDTO(int product_fk, int meal_fk, double grams, double calories,
			double proteins, double fats, double carbs, double sugars, double salt) 
	{		
		this.product_fk = product_fk;
		this.meal_fk = meal_fk;
		this.grams = grams;
		this.calories = calories;
		this.proteins = proteins;
		this.fats = fats;
		this.carbs = carbs;
		this.sugars = sugars;
		this.salt = salt;
	}
	
	public PortionCreateRequestDTO() {
		
	}
}
