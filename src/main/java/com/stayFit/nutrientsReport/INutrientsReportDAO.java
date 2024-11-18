package com.stayFit.nutrientsReport;

import java.util.List;

public interface INutrientsReportDAO {
	public List<NutrientsReportGetResponseDTO> getDailyNutrients(int user_fk) throws Exception;
	public List<NutrientsReportGetResponseDTO> getWeeklyNutrients(int user_fk) throws Exception;
	public List<NutrientsReportGetResponseDTO> getMonthlyNutrients(int user_fk) throws Exception;
}
