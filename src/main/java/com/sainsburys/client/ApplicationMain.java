package com.sainsburys.client;
import com.sainsburys.model.IProductGroup;
import com.sainsburys.model.ScraperDefinition;
import com.sainsburys.services.IScraperManager;
import com.sainsburys.services.ScraperManager;


/**
 * Class to invoke the scraper, and then output the results in JSON format
 * @author Paul
 *
 */
public class ApplicationMain {
	
	/**
	 * Constructor to invoke the scraper, and then output the results in JSON format
	 */
	public ApplicationMain() {
		IScraperManager scraper  = new ScraperManager(); 
		
		try {
			IProductGroup prdGrp = scraper.scrapeProducts(ScraperDefinition.BERRIES_CHERRIES_CURRANTS);
			String output = scraper.fetchOutput(prdGrp, ScraperManager.JSON_OUTPUT);
			System.out.println("JSON OUTPUT:\n\n" + output);
		} catch(Exception e) {
			System.out.println("Error scraping page: " + e.getMessage() + "\n");
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		new ApplicationMain();
	}
	
}
