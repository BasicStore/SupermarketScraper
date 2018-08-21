package com.sainsburys.model;

public abstract class AbstractProduct {

	protected String title;
	protected String unitPrice;
	protected String description;
	
	
	public AbstractProduct(String title, String unitPrice, String description) {
		this.title = title;
		this.unitPrice = unitPrice;
		this.description= description;
	}
	
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUnitPrice() {
		return unitPrice;
	}
	
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
		
}
