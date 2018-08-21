package com.sainsburys.model;

public class CoreProduct extends AbstractProduct {
	
	private String unitPrice;
	
	public CoreProduct(String title, String unitPrice, String description) {
		super(title, description);
		this.unitPrice = unitPrice; 
	}
	
	public String getUnitPrice() {
		return unitPrice;
	}
	
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	
}
