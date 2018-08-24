package com.sainsburys.services;
import java.util.List;

import com.sainsburys.model.IProductGroup;
import com.sainsburys.model.ScraperDefinition;

public interface IScraperManager {
		
	List<IProductGroup> scrapeProducts(); 
	
	String fetchOutput(List<IProductGroup> prdGrpList, int outputFormat);
		
}
