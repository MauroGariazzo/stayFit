package com.stayFit.nutrientsReport;

import java.util.List;
import java.util.Map;

import com.stayFit.enums.NutrientsReportInterval;

public class NutrientsReportController {
	private INutrientsReportGetUseCase getUseCase;
	
	public NutrientsReportController(INutrientsReportGetUseCase getUseCase) {
		this.getUseCase = getUseCase;
	}
	
	public Map<NutrientsReportInterval, List<NutrientsReportGetResponseDTO>> get(int user_fk) throws Exception {
		return this.getUseCase.get(user_fk);
	}
}
