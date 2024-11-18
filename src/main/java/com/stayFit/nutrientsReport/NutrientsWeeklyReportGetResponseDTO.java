package com.stayFit.nutrientsReport;

public class NutrientsWeeklyReportGetResponseDTO extends NutrientsReportGetResponseDTO{
	public int year;
	public int week;
		
	public NutrientsWeeklyReportGetResponseDTO(int year, int week, double calories, double proteins,
			double fats, double carbs) {
		super(calories, proteins, fats, carbs);
		this.year = year;
		this.week = week;		

	}
}
