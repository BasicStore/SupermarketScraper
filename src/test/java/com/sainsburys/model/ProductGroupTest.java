package com.sainsburys.model;
import org.junit.Test;
import static org.junit.Assert.*;
import com.sainsburys.utils.SysProperties;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class ProductGroupTest {

	@Test
	public void testSetResults() {
		// create a product list
		List<AbstractProduct> prodList = new ArrayList<AbstractProduct>();
		prodList.add(new Product("Sainsbury's Strawberries 400g", new BigDecimal("1.75"), "by Sainsbury's strawberries", "33"));
		prodList.add(new Product("Sainsbury's Blueberries 200g", new BigDecimal("1.75"), "by Sainsbury's blueberries", "45"));
		prodList.add(new Product("Sainsbury's Raspberries 225g", new BigDecimal("1.75"), "by Sainsbury's raspberries", "32"));		
		
		// save the product list which should update the total 
		ProductGroup prodGrp = new ProductGroup();
		String currency = null;
		String vatRate = null;
		try {
			prodGrp.setResults(prodList);
			currency = SysProperties.getInstance().getProperty("currency");
			vatRate = SysProperties.getInstance().getProperty("vat");
		} catch(IOException e) {
			
		}
		
		assertNotNull(prodGrp.getTotal());
		String expGrossStr = currency + new BigDecimal("5.25").toString();
		String expVatStr = vatRate + "%";
		assertEquals(expGrossStr, prodGrp.getTotal().getGross());
		assertEquals(expVatStr, prodGrp.getTotal().getVat());
		assertEquals(3, prodGrp.getResults().size());
	}
	
	
}
