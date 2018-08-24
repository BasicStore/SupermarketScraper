package com.sainsburys.scraper;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.sainsburys.exceptions.ProductScraperException;
import com.sainsburys.model.IProductGroup;

public interface IScraperJob {

	IProductGroup scrapeProducts() throws ProductScraperException, FailingHttpStatusCodeException, MalformedURLException, IOException;
	
}
