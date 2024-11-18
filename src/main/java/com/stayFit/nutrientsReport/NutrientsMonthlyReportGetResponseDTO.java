package com.stayFit.nutrientsReport;

public class NutrientsMonthlyReportGetResponseDTO extends NutrientsReportGetResponseDTO{
	public int year;
	public int month;	
		
	public NutrientsMonthlyReportGetResponseDTO(int year, int month, double calories, double proteins,
			double fats, double carbs) {
		super(calories, proteins, fats, carbs);
		this.year = year;
		this.month = month;
	}
}
