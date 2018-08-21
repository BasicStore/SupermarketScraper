package com.sainsburys.model;
import java.util.List;
import java.util.ArrayList;

public class ProductGroup implements IProductGroup {
	
	private List<AbstractProduct> results = new ArrayList<AbstractProduct>();
	private Total total;
	
	public Total getTotal() {
		return total;
	}
	
	
	private void calculateTotalInfo() {
	
		// TODO UPDATE TOTAL info ------->  gross + vat

		
		total = new Total("5.00", "0.83");
	}

	
	public List<AbstractProduct> getResults() {
		return results;
	}
	

	public void setResults(List<AbstractProduct> results) {
		this.results = results;
		calculateTotalInfo();
	}
		
}
