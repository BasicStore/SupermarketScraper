package com.sainsburys.scraper;
import com.sainsburys.model.AbstractProduct;
import com.sainsburys.model.CoreProduct;
import com.sainsburys.model.IProductGroup;
import com.sainsburys.model.Product;
import com.sainsburys.model.ProductGroup;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * A scraper to read the Berries, cherries & currants
 * @author Paul
 */
public class BerriesCherriesCurrantsScraperJob implements IScraperJob {

	private static final String SAINSBURYS_URL_BASE = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/";
	private static final String PRODUCT_GROUP_PAGE_URL = SAINSBURYS_URL_BASE + "webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
	
	public BerriesCherriesCurrantsScraperJob() {
	
		
		
	}
		
	
	/** TODO NOT IMPLEMENTED FULLY
	 * Gets a list of all the products listed on this URL, including products on subpages where
	 * they are listed on more than 1 page
	 * @return IProductGroup - the product group
	 */
	public IProductGroup scrapeProducts() {
		WebClient client = new WebClient();  
		client.getOptions().setCssEnabled(false);  
		client.getOptions().setJavaScriptEnabled(false);  
		
		List<AbstractProduct> productList = new ArrayList<AbstractProduct>();
		
		try {  
			// TODO !!!!!!!!! implement paging from here....for now just assume 1 page, which is page 1
			HtmlPage page = client.getPage(PRODUCT_GROUP_PAGE_URL);
			scrapeProductsOnThisPage(client, page, productList);
		} 
		catch(Exception e) {
			System.out.println("Error scraping page: " + e.getMessage() + "\n");
			e.printStackTrace();
		}
		
		IProductGroup prdGrp = new ProductGroup();
		prdGrp.setResults(productList);
		return prdGrp;
	}
	
	
	
	/**
	 * Scrapes a supermarket product webpage and loads product information into
	 * a product group java object.
	 * @param client - the scraper client object
	 * @param page - the html page  
	 * @param List<AbstractProduct> - the cumulative product list for all apges, to which products scraped from this page should be added
	 */
	public void scrapeProductsOnThisPage(WebClient client, HtmlPage page, List<AbstractProduct> productList) throws Exception {
			
		List<HtmlElement> items = (List<HtmlElement>) page.getByXPath(".//div[@class='productNameAndPromotions']");
		
	    if (items != null && !items.isEmpty()) {
			for(HtmlElement item : items) {

				// get the product page html element
				String itemUrl = ((HtmlAnchor) item.getFirstByXPath(".//h3//a")).getHrefAttribute();
				int index = itemUrl.indexOf("shop");
				String productUrl = SAINSBURYS_URL_BASE + itemUrl.substring(index);
				HtmlPage prodPage = client.getPage(productUrl);
				HtmlElement pageContentNode = (HtmlElement) prodPage.getFirstByXPath(".//div[@class='section productContent']");
				
				// get product name 
				HtmlElement prodNameNode = (HtmlElement) pageContentNode.getFirstByXPath(".//div[@class='productTitleDescriptionContainer']");
				String prodName = prodNameNode.asText();
				String title = prodNameNode.asText();
				
				// get product price
				String priceLabel = ((HtmlElement) pageContentNode.getFirstByXPath(".//p[@class='pricePerUnit']")).asText();
				BigDecimal price = CoreProduct.getPrice(priceLabel.substring(0, priceLabel.indexOf("/")));
				
				// get description
				String description = getDescription(pageContentNode);
				
				// get calories
				HtmlElement nutritionTable = (HtmlElement) pageContentNode.getFirstByXPath(".//table[@class='nutritionTable']");	
				if (nutritionTable == null) {
					productList.add(new CoreProduct(title, price, description));
					continue;
				}
				
				String kCalPer100g = null;
				List<HtmlElement> productTextItems = getProductTextItems(pageContentNode);
				HtmlElement productText = productTextItems.get(1);
				List<HtmlElement> trItemsTest = (List<HtmlElement>) nutritionTable.getByXPath(".//tbody/tr");
				if (trItemsTest.size() > 0) {
					HtmlElement tr1 = trItemsTest.get(1);
					HtmlElement td1 = tr1.getFirstByXPath("td");
					kCalPer100g = td1.asText();
				}
				
				productList.add(new Product(title, price, description, kCalPer100g));
			}
		}
	}
	
	
	// get the list of product text item nodes to assist in scraping a value for the product's calories
	private static List<HtmlElement> getProductTextItems(HtmlElement pageContentNode) { 
		List<HtmlElement> productTextItems = (List<HtmlElement>) pageContentNode.getByXPath(".//div[@class='productText']");
	    return productTextItems == null || productTextItems.size() == 0 ? (List<HtmlElement>) pageContentNode.getByXPath(".//div[@class='itemTypeGroupContainer productText']") : productTextItems;
	}
		
	
	// get the product description
	private static String getDescription(HtmlElement pageContentNode) {		
		HtmlElement descNode = descNode = (HtmlElement) pageContentNode.getFirstByXPath(".//div[@class='itemTypeGroupContainer productText']/div[@class='memo']/p");
		if (descNode == null) {
			
			descNode = (HtmlElement) pageContentNode.getFirstByXPath(".//div[@class='itemTypeGroup']");
		} 

		if (descNode == null) {
			
			descNode = ((HtmlElement) pageContentNode.getFirstByXPath(".//div[@class='productText']/p"));
		}
		
		return descNode.asText();         
	}
	
	
	
}
