package com.sainsburys.model;
import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;

public class ProductGroup implements IProductGroup {
	
	private List<AbstractProduct> results = new ArrayList<AbstractProduct>();
	private Total total;
	
	public Total getTotal() {
		return total;
	}
	
	
	private void calculateTotalInfo() {
		BigDecimal tot = new BigDecimal("0.00");
		
		for (AbstractProduct aprd :results) {
			CoreProduct prod = (CoreProduct)aprd;
			
			
			String priceStr = prod.getUnitPrice();
			BigDecimal priaceBD = CoreProduct.getPrice(prod.getUnitPrice());
			
			tot = tot.add(CoreProduct.getPrice(prod.getUnitPrice()));
		}
		
		String totalStr = Total.CURRENCY + tot.setScale(2).toString();
		total = new Total(totalStr, Total.VAT);
	}

	
	public List<AbstractProduct> getResults() {
		return results;
	}
	

	public void setResults(List<AbstractProduct> results) {
		this.results = results;
		calculateTotalInfo();
	}
		
}
