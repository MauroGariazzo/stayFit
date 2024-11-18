package com.stayFit.nutrientsReport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stayFit.enums.NutrientsReportInterval;

public class NutrientsReportGetUseCase implements INutrientsReportGetUseCase {
	private INutrientsReportDAO nutrientsReportDAO;
	
	public NutrientsReportGetUseCase(INutrientsReportDAO nutrientsReportDAO) {
		this.nutrientsReportDAO = nutrientsReportDAO;
	}

	public Map<NutrientsReportInterval, List<NutrientsReportGetResponseDTO>> get(int user_fk) throws Exception {
	    List<NutrientsReportGetResponseDTO> dailyNutrients = nutrientsReportDAO.getDailyNutrients(user_fk);
	    List<NutrientsReportGetResponseDTO> weeklyNutrients = nutrientsReportDAO.getWeeklyNutrients(user_fk);
	    List<NutrientsReportGetResponseDTO> monthlyNutrients = nutrientsReportDAO.getMonthlyNutrients(user_fk);
	    
	    Map<NutrientsReportInterval, List<NutrientsReportGetResponseDTO>> nutrients = new HashMap<>();
	    nutrients.put(NutrientsReportInterval.GIORNALIERO, dailyNutrients);
	    nutrients.put(NutrientsReportInterval.SETTIMANALE, weeklyNutrients);
	    nutrients.put(NutrientsReportInterval.MENSILE, monthlyNutrients);
	    
	    return nutrients;
	}
	
}
