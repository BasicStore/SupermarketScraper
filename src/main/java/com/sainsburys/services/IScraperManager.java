package com.sainsburys.services;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.sainsburys.exceptions.ProductScraperException;
import com.sainsburys.model.IProductGroup;
import com.sainsburys.model.ScraperDefinition;

public interface IScraperManager {
	
	IProductGroup scrapeProducts(ScraperDefinition scraper) throws ProductScraperException, FailingHttpStatusCodeException, MalformedURLException, IOException;
	
	List<IProductGroup> scrapeProducts() throws ProductScraperException, FailingHttpStatusCodeException, MalformedURLException, IOException; 
	
	String fetchOutput(IProductGroup prdGrp, int outputFormat);
	
	String fetchOutput(List<IProductGroup> prdGrpList, int outputFormat);
		
}
