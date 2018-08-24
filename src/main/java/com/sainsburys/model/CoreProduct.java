package com.sainsburys.model;
import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;

public class CoreProduct extends AbstractProduct {
	
	private BigDecimal unitPrice;
	
	public CoreProduct(String title, BigDecimal unitPrice, String description) {
		super(title, description);
		this.unitPrice = unitPrice; 
	}
	
	public String getUnitPrice() {
		if (unitPrice == null) {
			return StringUtils.EMPTY;
		}

		return "�" + unitPrice.setScale(2).toString();
	}
	
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	
	public static BigDecimal getPrice(String priceStr) {
		
		if (StringUtils.isBlank(priceStr)) {
			// TODO throw new exception
		}
		
		return new BigDecimal(priceStr.substring(1)).setScale(2);
	}
    
	
}
