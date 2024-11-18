package com.stayFit.weightReport;

import java.time.LocalDate;

public class WeightReportGetResponseDTO {
	public int id;
	public LocalDate dateRegistration;
	public double weight;
	public int user_fk;
	
	public WeightReportGetResponseDTO(LocalDate dateRegistration, double weight, int user_fk) {
		this.dateRegistration = dateRegistration;
		this.weight = weight;
		this.user_fk = user_fk;
	}
}
