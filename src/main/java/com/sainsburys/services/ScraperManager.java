package com.sainsburys.services;
import java.util.ArrayList;
import java.util.List;
import com.sainsburys.model.IProductGroup;
import com.sainsburys.model.ScraperDefinition;
import com.sainsburys.output.IOutputJob;
import com.sainsburys.output.JsonOutputJob;
import com.sainsburys.scraper.*;

public class ScraperManager implements IScraperManager {

	// TODO ********************  need Singleton class to get properties:  isJson
	
	public List<IProductGroup> scrapeProducts() {
		
		System.out.println("Scraping the products. This will take a few moments.......\n\n");
		List<IProductGroup> prdGrpList = new ArrayList<IProductGroup>();   
		for (ScraperDefinition def : ScraperDefinition.values()) {
			
			IScraperJob job = initScraperJob(def);
			IProductGroup prdGrp = job.generateProductGroup();
			prdGrpList.add(prdGrp);
		}
		
		return prdGrpList;
	}
	
	
	
	private IScraperJob initScraperJob(ScraperDefinition def) {
		
		int scraperId = def.getId();
        String monthString;
        switch (scraperId) {
            case 1:  return new BerriesCherriesCurrantsScraperJob();      // TODO ********************    IScraperJob ScraperJobFactory
        }	
		
		return null;
	}
        
        
	
	public String fetchOutput(List<IProductGroup> prdGrpList) {
		
		boolean isJson = true; // TODO ********************  
		IOutputJob job = new JsonOutputJob();  // TODO ********************    IOutputJob OutputJobFactory
		String output = "";
		for (IProductGroup grp : prdGrpList) {
			output += job.getOutput(grp) + "\n";
		}
		
		return output;
	}
	
	
	public String doTest() {
		return "47";
	}
	
	
}
