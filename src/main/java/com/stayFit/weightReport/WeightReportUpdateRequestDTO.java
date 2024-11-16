package com.stayFit.weightReport;

import java.time.LocalDate;

public class WeightReportUpdateRequestDTO {
	public int id;
	public double weight;
	public LocalDate weightDateRegistration;
	public int user_fk;
	
	public WeightReportUpdateRequestDTO(int id, double weight, int user_fk) {
		this.id = id;
		this.weight = weight;
		this.user_fk = user_fk;
	}
}
