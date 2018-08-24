package com.sainsburys.model;

import com.sainsburys.scraper.IScraperJob;

/**
 * Enum to define different types of scraping
 * @author Paul
 *
 */
public enum ScraperDefinition {

	BERRIES_CHERRIES_CURRANTS(1, "berries-cherries-currants");
	
	private final int id; 
	private final String name;
	
	ScraperDefinition(int id, String name) {
		this.id = id;
		this.name= name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
		
	
}

