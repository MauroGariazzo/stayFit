package com.stayFit.models;

import javafx.scene.image.Image;

public class Product {
	private int id;
	private String productName;
	private String brand;
	private String category;
	private double calories;
	private double proteins;
	private double fats;
	private double carbs;
	private double sugars;
	private double salt;
	private Image productImage;
	
	public Product() {
		
	}
	
	public Product(String productName, String brand, String category, Image productImage) {
		this.productName = productName;
		this.brand = brand;
		this.category = category;
		this.productImage = productImage;
	}

	public Product(int id, String productName, String brand, String category, double calories, double proteins,
			double fats, double carbs, double sugars, double salt, Image productImage) {
		this.id = id;
		this.productName = productName;
		this.brand = brand;
		this.category = category;
		this.calories = calories;
		this.proteins = proteins;
		this.fats = fats;
		this.carbs = carbs;
		this.sugars = sugars;
		this.salt = salt;
		this.productImage = productImage;
	}

	public Product(String productName, String brand, String category, double calories, double proteins, double fats,
			double carbs, double sugars, double salt, Image productImage) {		
		this.productName = productName;
		this.brand = brand;
		this.category = category;
		this.calories = calories;
		this.proteins = proteins;
		this.fats = fats;
		this.carbs = carbs;
		this.sugars = sugars;
		this.salt = salt;
		this.productImage = productImage;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setCalories(double calories) {
		this.calories = calories;
	}

	public void setProteins(double proteins) {
		this.proteins = proteins;
	}

	public void setFats(double fats) {
		this.fats = fats;
	}

	public void setCarbs(double carbs) {
		this.carbs = carbs;
	}

	public void setSugars(double sugars) {
		this.sugars = sugars;
	}

	public void setSalt(double salt) {
		this.salt = salt;
	}

	public void setProductImage(Image productImage) {
		this.productImage = productImage;
	}

	public int getId() {
		return id;
	}

	public String getProductName() {
		return productName;
	}

	public String getBrand() {
		return brand;
	}

	public String getCategory() {
		return category;
	}

	public double getCalories() {
		return calories;
	}

	public double getProteins() {
		return proteins;
	}

	public double getFats() {
		return fats;
	}

	public double getCarbs() {
		return carbs;
	}

	public double getSugars() {
		return sugars;
	}

	public double getSalt() {
		return salt;
	}

	public Image getProductImage() {
		return productImage;
	}
	
	@Override
	public String toString() {
		return this.productName;
	}
}
