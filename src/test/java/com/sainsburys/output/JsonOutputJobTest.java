package com.sainsburys.output;
import org.junit.Test;
import com.sainsburys.model.AbstractProduct;
import com.sainsburys.model.IProductGroup;
import com.sainsburys.model.Product;
import com.sainsburys.model.ProductGroup;
import static org.junit.Assert.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to test methods in the com.sainsburys.output.JsonOutputJob class  
 * @author Paul
 */
public class JsonOutputJobTest {	

	@Test
	public void testGetOutput() throws IOException{
		List<AbstractProduct> prodList = new ArrayList<AbstractProduct>();
		prodList.add(new Product("Sainsbury's Strawberries 400g", new BigDecimal("1.75"), "by Sainsbury's strawberries", "33"));
		prodList.add(new Product("Sainsbury's Blueberries 200g", new BigDecimal("1.75"), "by Sainsbury's blueberries", "45"));
		prodList.add(new Product("Sainsbury's Raspberries 225g", new BigDecimal("1.75"), "by Sainsbury's raspberries", "32"));		
		
		// save the product list which should update the total 
		IProductGroup prdGrp = new ProductGroup();
		prdGrp.setResults(prodList);
		String actOutput = new JsonOutputJob().getOutput(prdGrp);
		String expOutputStart = "{\"results\":[{\"title\":\"Sainsbury's Strawberries 400g\",\"description\":\"by Sainsbury's strawberries\",\"unitPrice\":";
		assertTrue(actOutput.startsWith(expOutputStart));
	}
	

}
