package com.sainsburys.client;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.sainsburys.model.IProductGroup;
import com.sainsburys.services.IScraperManager;
import com.sainsburys.services.ScraperManager;


/**
 * @author Paul
 *
 */
public class ApplicationMain {
	
	public ApplicationMain() {
		IScraperManager scraper  = new ScraperManager(); 
		List<IProductGroup> prdGrpList = scraper.scrapeProducts();
		String output = scraper.fetchOutput(prdGrpList);
		System.out.println("JSON OUTPUT:\n\n" + output);
	}
	
	
	public static void main(String[] args) {
		new ApplicationMain();
		
//		BigDecimal tot = new BigDecimal("4.00").add(new BigDecimal("7.00")); 
//		System.out.println("Total = " + tot.toString());
	}

	
}
