package com.sainsburys.model;

public class Product extends CoreProduct {

	protected String kCalPer100g;
	
	
	public Product(String title, String unitPrice, String description, String kCalPer100g) {
		super(title, unitPrice, description);
		this.kCalPer100g = kCalPer100g;
	}


	public String getkCalPer100g() {
		return kCalPer100g;
	}


	public void setkCalPer100g(String kCalPer100g) {
		this.kCalPer100g = kCalPer100g;
	}
	
			
}
