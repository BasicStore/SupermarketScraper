package com.sainsburys.model;
import java.util.List;
import java.util.ArrayList;

public class ProductGroup implements IProductGroup {
	
	private List<AbstractProduct> productList = new ArrayList<AbstractProduct>();
	private String gross;
	private String vat;
	
	
	public void addProduct(AbstractProduct product) {
		
		// do calculate
		
		
		// updateTotalInfo()
		
	}
	
	
	private void updateTotalInfo() {
	
		
	}


	public List<AbstractProduct> getProductList() {
		return productList;
	}


	public String getGross() {
		return gross;
	}


	public String getVat() {
		return vat;
	}
	

}
