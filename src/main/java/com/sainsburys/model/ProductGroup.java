package com.sainsburys.model;
import com.sainsburys.utils.*;
import java.util.List;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;


/**
 * Class representing a group of products, together with total price information
 * @author Paul
 *
 */
public class ProductGroup implements IProductGroup {
	
	private List<AbstractProduct> results = new ArrayList<AbstractProduct>();
	private Total total;
	
	public Total getTotal() {
		return total;
	}
	
	
	private void calculateTotalInfo() throws IOException {
		// read property values
		String currency = SysProperties.getInstance().getProperty("currency");
		String vat = SysProperties.getInstance().getProperty("vat");
		
		// calculate total amount
		BigDecimal tot = new BigDecimal("0.00");
		for (AbstractProduct aprd :results) {
			CoreProduct prod = (CoreProduct)aprd;
			tot = tot.add(CoreProduct.getPrice(prod.getUnitPrice()));
		}

		total = new Total(currency + tot.setScale(2).toString(), vat);
	}

	
	public List<AbstractProduct> getResults() {
		return results;
	}
	

	public void setResults(List<AbstractProduct> results) throws IOException {
		this.results = results;
		calculateTotalInfo();
	}
		
}
