package com.sainsburys.model;

public abstract class AbstractProduct {

	protected String title;
	protected String description;
	
	
	public AbstractProduct(String title, String description) {
		this.title = title;
		this.description= description;
	}
	
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
		
}
