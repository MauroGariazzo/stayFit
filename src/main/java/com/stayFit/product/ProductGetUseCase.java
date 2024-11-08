package com.stayFit.product;

import java.util.List;
//import java.util.Map;

import com.stayFit.models.Product;

public class ProductGetUseCase implements IProductGetUseCase{
	IProductDAO productDAO;

	public ProductGetUseCase(IProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	public List<Product> execute(ProductGetRequestDTO productGetDTO) throws Exception {
		return productDAO.get(productGetDTO);
	}
}
