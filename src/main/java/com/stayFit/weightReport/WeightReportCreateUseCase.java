package com.stayFit.weightReport;

public class WeightReportCreateUseCase implements IWeightReportCreateUseCase{
	private WeightReportDAO reportDAO;
	
	public WeightReportCreateUseCase(WeightReportDAO reportDAO) {
		this.reportDAO = reportDAO;
	}
	
	public void create(WeightReportCreateRequestDTO requestDTO) throws Exception{
		reportDAO.upsert(requestDTO);
	}
}
