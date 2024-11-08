package com.stayFit.product;

public class ProductCreateUseCase implements IProductCreateUseCase{
	IProductDAO productDAO;
	
	public ProductCreateUseCase(IProductDAO productDAO) {
		this.productDAO = productDAO;
	}
	
	public void execute(ProductCreateRequestDTO productPostDTO)throws Exception {
		productDAO.insert(productPostDTO);
	}
}
