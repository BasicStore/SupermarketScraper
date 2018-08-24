package com.sainsburys.exceptions;

/**
 * Exception class to represent all errors thrown when scraping products from the web
 * @author Paul
 */
public class ProductScraperException extends Exception {
		
	public ProductScraperException(String msg) {
		super(msg);
	}

}
