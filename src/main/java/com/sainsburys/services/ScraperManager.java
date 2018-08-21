package com.sainsburys.services;
import java.util.ArrayList;
import java.util.List;
import com.sainsburys.model.IProductGroup;
import com.sainsburys.model.ScraperDefinition;
import com.sainsburys.output.IOutputJob;
import com.sainsburys.output.OutputJob;
import com.sainsburys.scraper.*;

public class ScraperManager implements IScraperManager {

	public String scrapeProductsToJson() {
		List<IProductGroup> prdGrpList = new ArrayList<IProductGroup>();
		for (ScraperDefinition def : ScraperDefinition.values()) {
			
			IScraperJob job = initScraperJob(def);
			IProductGroup prdGrp = job.generateProductGroup();
			prdGrpList.add(prdGrp);
		}
		
		//return "JSON GOES HERE";
		return fetchOutput(prdGrpList);
	}
	
	
	
	private IScraperJob initScraperJob(ScraperDefinition def) {
		
		int scraperId = def.getId();
        String monthString;
        switch (scraperId) {
            case 1:  return new BerriesCherriesCurrantsScraperJob();
        }	
		
		return null;
	}
        
        
	
	private String fetchOutput(List<IProductGroup> prdGrp) {
		
		boolean isJson = true; // TODO ********************
		IOutputJob job = new OutputJob();  // TODO ********************
		String output = "";
		for (IProductGroup grp : prdGrp) {
			output += job.getOutput(grp) + "\n";
		}
		
		return output;
	}
	
	
}
