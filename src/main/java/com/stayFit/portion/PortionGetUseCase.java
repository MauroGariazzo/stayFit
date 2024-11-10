package com.stayFit.portion;

import java.util.List;
import com.stayFit.models.Portion;

public class PortionGetUseCase implements IPortionGetUseCase{
	private IPortionDAO portionDAO; 
	public PortionGetUseCase(IPortionDAO portionDAO) {
		this.portionDAO = portionDAO;
	}
	
	public List<Portion> getMealPortions(PortionGetRequestDTO pgrDTO)throws Exception{
		return portionDAO.getMealPortions(pgrDTO);
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
		
		return pgrDTO;
	}
}
