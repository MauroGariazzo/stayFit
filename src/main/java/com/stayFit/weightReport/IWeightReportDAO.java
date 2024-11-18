package com.stayFit.weightReport;

import java.util.List;

import com.stayFit.models.WeightDiary;

public interface IWeightReportDAO {
	public void upsert(WeightReportCreateRequestDTO requestDTO)throws Exception;	
	public List<WeightDiary> getDailyWeightReport(int userId)throws Exception;	
}
