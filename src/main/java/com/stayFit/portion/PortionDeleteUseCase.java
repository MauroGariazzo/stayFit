package com.stayFit.portion;

public class PortionDeleteUseCase implements IPortionDeleteUseCase{
	private IPortionDAO portionDAO;
	public PortionDeleteUseCase(IPortionDAO portionDAO) {
		this.portionDAO = portionDAO;
	}
	
	public void delete(int id)throws Exception {
		this.portionDAO.delete(id);
	}
}
