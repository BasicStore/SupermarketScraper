package com.sainsburys.services;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.sainsburys.exceptions.ProductScraperException;
import com.sainsburys.model.IProductGroup;
import com.sainsburys.model.ScraperDefinition;
import com.sainsburys.output.IOutputJob;
import com.sainsburys.output.JsonOutputJob;
import com.sainsburys.scraper.*;

/**
 * Scraper manager to scrape all ScraperDefinition product areas 
 * @author Paul
 */
public class ScraperManager implements IScraperManager {    

	public static final int JSON_OUTPUT = 1;
	public static final int XML_OUTPUT = 2;
	
	public List<IProductGroup> scrapeProducts() throws ProductScraperException, FailingHttpStatusCodeException, MalformedURLException, IOException {  
		System.out.println("Scraping the products. This will take a few moments.......\n\n");
		List<IProductGroup> prdGrpList = new ArrayList<IProductGroup>();   
		for (ScraperDefinition def : ScraperDefinition.values()) {
			
			IScraperJob job = createScraperJob(def);
			IProductGroup prdGrp = job.scrapeProducts();
			prdGrpList.add(prdGrp);
		}
		
		return prdGrpList;
	}
	
	
	// creates the particular scraper 
	private IScraperJob createScraperJob(ScraperDefinition def) {
		
		int scraperId = def.getId();
        String monthString;
        switch (scraperId) {
            case 1:  return new BerriesCherriesCurrantsScraperJob();     
        }	
		
		return null;
	}
        
        
	
	/**
	 * Fetches the previously scraped data in the specified format
	 * @param prdGrpList
	 * @param outputFormat - the output string format, such as JSON or XML
	 * @return
	 */
	public String fetchOutput(List<IProductGroup> prdGrpList, int outputFormat) {
		
		IOutputJob job = createOutputJob(outputFormat);
		String output = "";
		for (IProductGroup grp : prdGrpList) {
			output += job.getOutput(grp) + "\n";
		}
		
		return output;
	}
	
	
	private IOutputJob createOutputJob(int outputFormat) {
		
		switch (outputFormat) {
        	case JSON_OUTPUT:  return new JsonOutputJob();     
        	case XML_OUTPUT:   return null;
        	default: return new JsonOutputJob();
		}
	}
		
}
