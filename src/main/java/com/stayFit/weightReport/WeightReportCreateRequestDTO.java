package com.stayFit.weightReport;

import java.time.LocalDate;

public class WeightReportCreateRequestDTO {	
	public double weight;
	public LocalDate weightDateRegistration;
	public int user_fk;
	
	public WeightReportCreateRequestDTO(double weight, LocalDate weightDateRegistration, int user_fk) {
		this.weight = weight;
		this.weightDateRegistration = weightDateRegistration;
		this.user_fk = user_fk;
	}
}
