package com.stayFit.nutrientsReport;

public class NutrientsReportGetResponseDTO {
	public double calories;
	public double proteins;
	public double fats;
	public double carbs;
		
	public NutrientsReportGetResponseDTO(double calories, double proteins, double fats, double carbs) {		
		this.calories = calories;
		this.proteins = proteins;
		this.fats = fats;
		this.carbs = carbs;
	}
}
