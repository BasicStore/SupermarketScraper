package com.sainsburys.model;
import org.junit.Test;
import static org.junit.Assert.*;
import com.sainsburys.utils.SysProperties;
import java.io.IOException;
import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;


/**
 * Class to test methods within the com.sainsburys.model.CoreProduct class
 * @author Paul
 *
 */
public class CoreProductTest {

	
	@Test
	public void testGetPrice() {
		String priceStr = null;
		assertNull(CoreProduct.getPrice(priceStr));
		
		priceStr = "£5.99";
		assertEquals(new BigDecimal("5.99"), CoreProduct.getPrice(priceStr));
		
		priceStr = "£5.9";
		assertEquals(new BigDecimal("5.90"), CoreProduct.getPrice(priceStr));
		
		priceStr = "ZQ£5.9ABCDE";
		assertNull(CoreProduct.getPrice(priceStr));
	}
	
	
	@Test
	public void testGetUnitPrice() throws IOException {
		
		CoreProduct coreProduct = new CoreProduct("Name123", null, "desc123");
		assertEquals(StringUtils.EMPTY, coreProduct.getUnitPrice());
		
		String val = "15.50";
		coreProduct = new CoreProduct("Name123", new BigDecimal(val), "desc123");
		String expUnitPrice = SysProperties.getInstance().getProperty("currency") + val;
		assertEquals(expUnitPrice, coreProduct.getUnitPrice());		
	}
		
	
}
