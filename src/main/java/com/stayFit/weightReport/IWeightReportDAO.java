package com.stayFit.weightReport;

public interface IWeightReportDAO {
	public void upsert(WeightReportCreateRequestDTO requestDTO)throws Exception;
}
