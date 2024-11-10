package com.stayFit.portion;

import java.util.ArrayList;
import java.util.List;

import com.stayFit.enums.MealType;
import com.stayFit.models.Portion;
import com.stayFit.product.ProductGetResponseDTO;

//import com.stayFit.product.ProductCreateRequestDTO;

public class PortionController {
	private IPortionCreateUseCase portionCreateUseCase;
	private IPortionGetUseCase portionGetUseCase;
	private IPortionDeleteUseCase portionDeleteUseCase;
	
	public PortionController(IPortionCreateUseCase portionCreateUseCase, IPortionGetUseCase portionGetUseCase) {
		this.portionCreateUseCase = portionCreateUseCase;
		this.portionGetUseCase = portionGetUseCase;
	}
	
	public PortionController(IPortionCreateUseCase portionCreateUseCase) {
		this.portionCreateUseCase = portionCreateUseCase;
	}
	
	public PortionController(IPortionGetUseCase portionGetUseCase) {
		this.portionGetUseCase = portionGetUseCase;
	}
	
	public PortionController(IPortionDeleteUseCase portionDeleteUseCase) {
		this.portionDeleteUseCase = portionDeleteUseCase;
	}
	
	public void create(List<PortionCreateRequestDTO> portion, int id)throws Exception {
		try {
			portionCreateUseCase.execute(portion, id);
		}
		catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	public PortionGetResponseDTO getPortionDTO(PortionCreateRequestDTO pcrDTO)throws Exception {
		return portionGetUseCase.getPortionDTO(pcrDTO);
	}
	
	//traformare il model in dto
	public List<PortionGetResponseDTO>getPortionsDTO(PortionGetRequestDTO pgRequestDTO)throws Exception{
		List<PortionGetResponseDTO>portionsDTO = new ArrayList<>();
		try {
			List<Portion>portions = this.portionGetUseCase.getMealPortions(pgRequestDTO);
			
			for(Portion portion:portions) {
				PortionGetResponseDTO pgrDTO = new PortionGetResponseDTO();
				//System.out.println(portion.getId());
				pgrDTO.id = portion.getId();
				pgrDTO.product_name = portion.getProductName();
				pgrDTO.product_fk = portion.getFkProduct();
				pgrDTO.grams = portion.getGrams();
				pgrDTO.meal_fk = portion.getFkMeal();
				pgrDTO.mealType = MealType.getStringFromCode(portion.getMealType());
				pgrDTO.calories = portion.getCalories();
				pgrDTO.carbs = portion.getCarbs();
				pgrDTO.fats = portion.getFats();
				pgrDTO.proteins = portion.getProteins();
				pgrDTO.salt = portion.getSalt();
				pgrDTO.sugars = portion.getSugars();
				portionsDTO.add(pgrDTO);
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
		return portionsDTO;
	}
	
	public void delete(int id)throws Exception {
		portionDeleteUseCase.delete(id);
	}
}
