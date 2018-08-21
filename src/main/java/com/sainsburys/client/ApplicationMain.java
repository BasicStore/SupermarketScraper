/**
 * 
 */
package com.sainsburys.client;
import com.sainsburys.services.IScraperManager;
import com.sainsburys.services.ScraperManager;

/**
 * @author Paul
 *
 */
public class ApplicationMain {

	
	public ApplicationMain() {
		IScraperManager scraper  = new ScraperManager(); 
		String output = scrapeProductsToJson(scraper);
		System.out.println("JSON OUTPUT:\n\n" + output);
	}
	
	
	public String scrapeProductsToJson(IScraperManager scraper) {
		return scraper.scrapeProductsToJson();
	}
	
	
	public static void main(String[] args) {
		new ApplicationMain();
	}

	
}
