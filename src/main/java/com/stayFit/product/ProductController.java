package com.stayFit.product;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import com.stayFit.models.Product;

public class ProductController {	
	private IProductCreateUseCase productCreateUseCase;
	private IProductGetUseCase productGetUseCase;
	
	public ProductController(IProductCreateUseCase productCreateUseCase, IProductGetUseCase productGetUseCase) {
		this.productCreateUseCase = productCreateUseCase;
		this.productGetUseCase = productGetUseCase;
	}
	
	public void create(ProductCreateRequestDTO product)throws Exception {
		try {
			productCreateUseCase.execute(product);
		}
		catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	public List<ProductGetResponseDTO>get(ProductGetRequestDTO productGetRequestDTO){
		List<ProductGetResponseDTO>productsDTO = new ArrayList<>();
		
		try {
			List<Product>products = productGetUseCase.execute(productGetRequestDTO);			
			
			for(Product product:products) {
				//System.out.println(product.getSalt());
				ProductGetResponseDTO productGetResponseDTO = new ProductGetResponseDTO();
				productGetResponseDTO.id = product.getId();
				productGetResponseDTO.productName = product.getProductName();
				productGetResponseDTO.brand = product.getBrand();
				productGetResponseDTO.category = product.getCategory();
				productGetResponseDTO.calories = product.getCalories();
				productGetResponseDTO.proteins = product.getProteins();
				productGetResponseDTO.fats = product.getFats();
				productGetResponseDTO.carbs = product.getCarbs();
				productGetResponseDTO.sugars = product.getSugars();
				productGetResponseDTO.salt = product.getSalt();
				productGetResponseDTO.productImage = product.getProductImage();
				//System.out.println(productGetResponseDTO.productImage.getClass());
				productsDTO.add(productGetResponseDTO);
			}
		}
		catch(Exception ex) {
			//throw new Exception(ex.getMessage());
		}
		return productsDTO;
	}
}

