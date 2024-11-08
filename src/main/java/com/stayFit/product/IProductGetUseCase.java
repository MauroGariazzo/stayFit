package com.stayFit.product;

import java.util.List;
import java.util.Map;

import com.stayFit.models.Product;

public interface IProductGetUseCase {
	public List<Product> execute(ProductGetRequestDTO productGetRequestDTO)throws Exception;
}
