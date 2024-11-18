package com.stayFit.weightReport;

import java.util.ArrayList;
import java.util.List;
import com.stayFit.models.WeightDiary;

public class WeightReportController {
	private IWeightReportCreateUseCase createUseCase;
	private IWeightReportGetUseCase getUseCase;
	
	public WeightReportController(IWeightReportCreateUseCase createUseCase) {
		this.createUseCase = createUseCase;
	}
	
	public WeightReportController(IWeightReportGetUseCase getUseCase) {
		this.getUseCase = getUseCase;
	}
		
	public void create(WeightReportCreateRequestDTO request) throws Exception{
		createUseCase.create(request);
	}
	
	public List<WeightReportGetResponseDTO>getWeightReport(int userId)throws Exception{
		List<WeightDiary> weightDiaryList = this.getUseCase.getWeightReport(userId);
		List<WeightReportGetResponseDTO> responseList = new ArrayList<>();
		
		for(WeightDiary wd : weightDiaryList) {
			WeightReportGetResponseDTO response = new WeightReportGetResponseDTO(wd.getWeightDateRegistration(),
					wd.getWeight(), wd.getUser_fk());
			responseList.add(response);
		}
		return responseList;
	}
}
