package com.stayFit.portion;

import java.util.List;

public class PortionCreateUseCase implements IPortionCreateUseCase{
	private IPortionDAO portionDAO;
	public PortionCreateUseCase(IPortionDAO portionDAO) {
		this.portionDAO = portionDAO;
	}
	public void execute(List<PortionCreateRequestDTO> portions, int id)throws Exception {
		for(PortionCreateRequestDTO p : portions) {
			p.meal_fk = id;
			portionDAO.insert(p);
		}		
	}
}
