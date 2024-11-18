package com.stayFit.portion;

import java.util.List;
import com.stayFit.models.Portion;

public class PortionGetUseCase implements IPortionGetUseCase{
	private IPortionDAO portionDAO; 
	public PortionGetUseCase(IPortionDAO portionDAO) {
		this.portionDAO = portionDAO;
	}
	
	public List<Portion> getMealPortions(PortionGetRequestDTO pgrDTO)throws Exception{
		List<Portion>portions = portionDAO.getMealPortions(pgrDTO);
		
		if(portions.size()>0) {
			for(int i = 0; i<portions.size();i++) {
				Portion portion = portions.get(i);				
				portion.setCalories(Math.round(portion.getCalories()*100)/100);
				portion.setCarbs(Math.round(portion.getCarbs()*100)/100);
				portion.setProteins(Math.round(portion.getProteins()*100)/100);
				portion.setFats(Math.round(portion.getFats()*100)/100);
				portion.setSugars(Math.round(portion.getSugars()*100)/100);
				portion.setSalt(Math.round(portion.getSalt()*100)/100);
			}
		}
		return portions;
	}
	
	public PortionGetResponseDTO getPortionDTO(PortionCreateRequestDTO pcrDTO)throws Exception {
		PortionGetResponseDTO pgrDTO = new PortionGetResponseDTO();
	
		pgrDTO.grams = pcrDTO.grams;
		pgrDTO.product_fk = pcrDTO.product_fk;
		pgrDTO.calories = (pcrDTO.calories * pcrDTO.grams)/100;
		pgrDTO.carbs = (pcrDTO.carbs * pcrDTO.grams)/100;
		pgrDTO.fats = (pcrDTO.fats * pcrDTO.grams)/100;
		pgrDTO.sugars = (pcrDTO.sugars * pcrDTO.grams)/100;
		pgrDTO.salt = (pcrDTO.salt * pcrDTO.grams)/100;
		pgrDTO.proteins = (pcrDTO.proteins * pcrDTO.grams)/100;
		
		System.out.println("proteine: " + pgrDTO.proteins);
		
		return pgrDTO;
	}
}

