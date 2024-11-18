package com.stayFit.nutrientsReport;

import java.util.List;
import java.util.Map;

import com.stayFit.enums.NutrientsReportInterval;

public interface INutrientsReportGetUseCase {
	public Map<NutrientsReportInterval, List<NutrientsReportGetResponseDTO>> get(int user_fk) throws Exception;
}
