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
		
	
	/**
	 * Applies the given scraper type to a supermarket web page and converts the relevant product 
	 * information to an IProductGroup java object
	 * @param scraper - the type of scraper
	 * @return IProductGroup - the group of products and related data   
	 */
	public IProductGroup scrapeProducts(ScraperDefinition scraper) throws ProductScraperException, FailingHttpStatusCodeException, MalformedURLException, IOException {  
		System.out.println("Scraping the products. This will take a few moments.......\n\n");
		List<IProductGroup> prdGrpList = new ArrayList<IProductGroup>();   
		IScraperJob job = createScraperJob(scraper);
		return job.scrapeProducts();
	}
	
	
	
	/**
	 * Applies all the scrapers defined in com.sainsburys.model.ScraperDefinition for supermarket web page(s), 
	 * and converts the resulting product information into a list if IProductGroup java objects
	 * @return List<IProductGroup> - the group of products and related data   
	 */
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
	protected IScraperJob createScraperJob(ScraperDefinition def) {
		
		int scraperId = def.getId();
        String monthString;
        switch (scraperId) {
            case 1:  return new BerriesCherriesCurrantsScraperJob();     
        }	
		
		return null;
	}
	
	
	public String fetchOutput(IProductGroup prdGrp, int outputFormat) {
		
		IOutputJob job = createOutputJob(outputFormat);
		return job.getOutput(prdGrp) + "\n";
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
	
	
	protected IOutputJob createOutputJob(int outputFormat) {
		
		switch (outputFormat) {
        	case JSON_OUTPUT:  return new JsonOutputJob();     
        	case XML_OUTPUT:   return null;
        	default: return new JsonOutputJob();
		}
	}
		
}
