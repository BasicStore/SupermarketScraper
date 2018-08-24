package com.sainsburys.model;
import java.math.BigDecimal;

/**
 * Class representing a grocery product
 * @author Paul
 *
 */
public class Product extends CoreProduct {

	protected String kCalPer100g;
	
	
	public Product(String title, BigDecimal unitPrice, String description, String kCalPer100g) {
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
