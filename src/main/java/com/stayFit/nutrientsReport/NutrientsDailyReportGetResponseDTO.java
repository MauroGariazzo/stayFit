package com.stayFit.nutrientsReport;

public class NutrientsDailyReportGetResponseDTO extends NutrientsReportGetResponseDTO{
	public int year;
	public int month;
	public int day;	
		
	public NutrientsDailyReportGetResponseDTO(int year, int month, int day, double calories, double proteins,
			double fats, double carbs) {
		super(calories, proteins, fats, carbs);
		this.year = year;
		this.month = month;
		this.day = day;
	}
}
