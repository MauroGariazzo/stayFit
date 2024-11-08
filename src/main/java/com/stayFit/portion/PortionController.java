package com.stayFit.portion;

import java.util.List;

//import com.stayFit.product.ProductCreateRequestDTO;

public class PortionController {
	private IPortionCreateUseCase portionCreateUseCase;
	
	public PortionController(IPortionCreateUseCase portionCreateUseCase) {
		this.portionCreateUseCase = portionCreateUseCase;
	}
	
	public void create(List<PortionCreateRequestDTO> portion, int id)throws Exception {
		try {
			portionCreateUseCase.execute(portion, id);
		}
		catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
}
