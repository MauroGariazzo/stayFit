package com.stayFit.product;

import javafx.scene.image.Image;

public class ProductCreateRequestDTO {
	//private int id;
	public String productName;
	public String brand;
	public String category;
	public double calories;
	public double proteins;
	public double fats;
	public double carbs;
	public double sugars;
	public double salt;
	public Image productImage;

	
	@Override
	public String toString() {
		return this.productName;
	}
}
