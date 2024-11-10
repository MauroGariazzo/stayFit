package com.stayFit.portion;

import java.util.List;

import com.stayFit.models.Portion;
import com.stayFit.product.ProductGetResponseDTO;

public interface IPortionGetUseCase {
	public List<Portion> getMealPortions(PortionGetRequestDTO pgrDTO)throws Exception;
	public PortionGetResponseDTO getPortionDTO(PortionCreateRequestDTO pcrDTO)throws Exception;
}
