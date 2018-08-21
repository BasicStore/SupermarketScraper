/**
 * 
 */
package com.sainsburys.client;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.sainsburys.services.IScraperManager;
import com.sainsburys.services.ScraperManager;

import java.math.BigDecimal;

/**
 * @author Paul
 *
 */
public class ApplicationMain {

	
	public ApplicationMain() {
		IScraperManager scraper  = new ScraperManager(); 
		scrapeProductsToJson(scraper);
	}
	
	
	public String scrapeProductsToJson(IScraperManager scraper) {
		return scraper.scrapeProductsToJson();
	}
	
	
	
	public static void sainsTest() {

		final String SAINSBURYS_URL_BASE = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/";
		final String PRODUCT_GROUP_PAGE_URL = SAINSBURYS_URL_BASE + "webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
		
		// create and optimize client
		WebClient client = new WebClient();  
		client.getOptions().setCssEnabled(false);  
		client.getOptions().setJavaScriptEnabled(false);  
		
		try {  
		    
			
		    
			HtmlPage page = client.getPage(PRODUCT_GROUP_PAGE_URL);
			
			// TODO implement paging by looping from this point, after first iteration
			List<HtmlElement> items = (List<HtmlElement>) page.getByXPath(".//div[@class='productNameAndPromotions']");
			int i = 0;
			
			
		    if (items.isEmpty()){  
			    System.out.println("No items found !");
			} else {
				for(HtmlElement item : items) {
					
					System.out.println("i = " + i);

					// get the product page html element
					HtmlAnchor itemAnchor =  ((HtmlAnchor) item.getFirstByXPath(".//h3//a"));
					String itemUrl = itemAnchor.getHrefAttribute() ;
					int index = itemUrl.indexOf("shop");
					String productUrl = SAINSBURYS_URL_BASE + itemUrl.substring(index);
					HtmlPage prodPage = client.getPage(productUrl);
					HtmlElement pageContentNode = (HtmlElement) prodPage.getFirstByXPath(".//div[@class='section productContent']");
					
					// get product name 
					HtmlElement prodNameNode = (HtmlElement) pageContentNode.getFirstByXPath(".//div[@class='productTitleDescriptionContainer']");
					String prodName = prodNameNode.asText();
					System.out.println("product name = " + prodName);

					
					// get product price
					String rawPrice = ((HtmlElement) pageContentNode.getFirstByXPath(".//p[@class='pricePerUnit']")).asText();
					rawPrice = rawPrice.substring(0, rawPrice.indexOf("/"));
					System.out.println("price = " + rawPrice);

					// get description
					String desc = getDescription(pageContentNode, i);
					System.out.println("Description = " + desc);
					
					
					// get calories
					HtmlElement nutritionTable = (HtmlElement) pageContentNode.getFirstByXPath(".//table[@class='nutritionTable']");	
					if (nutritionTable == null) {
						System.out.println("Calories = NOT AVAILABLE\n");
						i++;
						continue;
					}
					
					List<HtmlElement> productTextItems = getProductTextItems(pageContentNode);
					HtmlElement productText = productTextItems.get(1);
					List<HtmlElement> trItemsTest = (List<HtmlElement>) nutritionTable.getByXPath(".//tbody/tr");
					if (trItemsTest.size() > 0) {
						HtmlElement tr1 = trItemsTest.get(1);
						HtmlElement td1 = tr1.getFirstByXPath("td");
						String cals1 = td1.asText();
						System.out.println("Calories = " + cals1 + "\n");
					}
					
					
					// CATCH HERE:  structural (PriceFormatException + NodeNotFoundException)
					
					i++;
				}
			}
		} catch(Exception e) {
			
			// TODO: IOException, FailingHttpStatusCodeException  ELSE  
			
			
		    e.printStackTrace();
		}    
	}	
		
	
	
	private static List<HtmlElement> getProductTextItems(HtmlElement pageContentNode) { 
		List<HtmlElement> productTextItems = (List<HtmlElement>) pageContentNode.getByXPath(".//div[@class='productText']");
	    return productTextItems == null || productTextItems.size() == 0 ? (List<HtmlElement>) pageContentNode.getByXPath(".//div[@class='itemTypeGroupContainer productText']") : productTextItems;
	}
		
	
	
	private static String getDescription(HtmlElement pageContentNode, int i) {		
		HtmlElement descNode = descNode = (HtmlElement) pageContentNode.getFirstByXPath(".//div[@class='itemTypeGroupContainer productText']/div[@class='memo']/p");
		if (descNode == null) {
			
			descNode = (HtmlElement) pageContentNode.getFirstByXPath(".//div[@class='itemTypeGroup']");
		} 

		if (descNode == null) {
			
			descNode = ((HtmlElement) pageContentNode.getFirstByXPath(".//div[@class='productText']/p"));
		}
		
		return descNode.asText();         
	}
	
	
	
	
	public static void main(String[] args) {
		sainsTest();
	}

	
}
