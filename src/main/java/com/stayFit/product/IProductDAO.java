package com.stayFit.product;

import java.util.List;
import com.stayFit.models.Product;

public interface IProductDAO {
	public void insert(ProductCreateRequestDTO product)throws Exception;
	public List<Product> get(ProductGetRequestDTO productDTO)throws Exception;
}
