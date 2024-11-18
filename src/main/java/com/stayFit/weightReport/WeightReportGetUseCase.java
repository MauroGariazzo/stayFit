package com.stayFit.weightReport;
import java.util.List;
import com.stayFit.models.WeightDiary;

public class WeightReportGetUseCase implements IWeightReportGetUseCase{
	
	private IWeightReportDAO weightReportDAO;
	public WeightReportGetUseCase(IWeightReportDAO weightReportDAO) {
		this.weightReportDAO = weightReportDAO;
	}
	
	public List<WeightDiary>getWeightReport(int userId)throws Exception {
		return weightReportDAO.getDailyWeightReport(userId);
	}
}
