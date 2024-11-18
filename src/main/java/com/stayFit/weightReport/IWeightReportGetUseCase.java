package com.stayFit.weightReport;

import java.util.List;
import java.util.Map;
import com.stayFit.enums.NutrientsReportInterval;
import com.stayFit.models.WeightDiary;

public interface IWeightReportGetUseCase {
	public List<WeightDiary>getWeightReport(int userId)throws Exception;
}
