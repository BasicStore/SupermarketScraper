package com.sainsburys.scraper;
import com.sainsburys.exceptions.ProductScraperException;
import com.sainsburys.model.AbstractProduct;
import com.sainsburys.model.CoreProduct;
import com.sainsburys.model.IProductGroup;
import com.sainsburys.model.Product;
import com.sainsburys.model.ProductGroup;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
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
	public IProductGroup scrapeProducts() throws ProductScraperException, FailingHttpStatusCodeException, MalformedURLException, IOException {
		WebClient client = new WebClient();  
		client.getOptions().setCssEnabled(false);  
		client.getOptions().setJavaScriptEnabled(false);  
		
		List<AbstractProduct> productList = new ArrayList<AbstractProduct>();
		HtmlPage page = client.getPage(PRODUCT_GROUP_PAGE_URL);
		scrapeProductsOnThisPage(client, page, productList);
		
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
	public void scrapeProductsOnThisPage(WebClient client, HtmlPage page, List<AbstractProduct> productList) throws ProductScraperException, FailingHttpStatusCodeException, 
																													MalformedURLException, IOException {
			
		List<HtmlElement> items = (List<HtmlElement>) page.getByXPath(".//div[@class='productNameAndPromotions']");
		
	    if (items != null && !items.isEmpty()) {
			for(HtmlElement item : items) {

				// get the product page html element
				HtmlElement pageContentNode = getPageContentNode(client, item);

				// create product
    			String prodName = getProductName(pageContentNode);
				BigDecimal prodPrice = getProductPrice(pageContentNode);
				String prodDesc = getDescription(pageContentNode);
				
				// get calories
				HtmlElement nutritionTable = (HtmlElement) pageContentNode.getFirstByXPath(".//table[@class='nutritionTable']");	
				if (nutritionTable == null) {
					productList.add(new CoreProduct(prodName, prodPrice, prodDesc));
					continue;
				} else {
					String prodKCalPer100g = getProductCalories(pageContentNode, nutritionTable); 
					productList.add(new Product(prodName, prodPrice, prodDesc, prodKCalPer100g));
				}
			}
		}
	}
	
	
	
	private String getProductName(HtmlElement pageContentNode) throws ProductScraperException {
		
		HtmlElement prodNameNode = (HtmlElement) pageContentNode.getFirstByXPath(".//div[@class='productTitleDescriptionContainer']");
		
		if (prodNameNode == null || StringUtils.isBlank(prodNameNode.asText())) {
			throw new ProductScraperException("Product name could not be scraped");
		}

		return prodNameNode.asText();
	}
	
	
	private BigDecimal getProductPrice(HtmlElement pageContentNode) throws ProductScraperException {
		
		String priceLabel = ((HtmlElement) pageContentNode.getFirstByXPath(".//p[@class='pricePerUnit']")).asText();
		BigDecimal price = CoreProduct.getPrice(priceLabel.substring(0, priceLabel.indexOf("/")));
		if (price == null) {
			throw new ProductScraperException("Product price could not be scraped");
		}
		
		return price;
	}

	
	
	private String getProductCalories(HtmlElement pageContentNode, HtmlElement nutritionTable) throws ProductScraperException {
		List<HtmlElement> productTextItems = getProductTextItems(pageContentNode);
		HtmlElement productText = productTextItems.get(1);
		List<HtmlElement> trItemsTest = (List<HtmlElement>) nutritionTable.getByXPath(".//tbody/tr");
		if (trItemsTest.size() > 0) {
			HtmlElement tr1 = trItemsTest.get(1);
			
			if (tr1 == null || tr1.getFirstByXPath("td") == null || StringUtils.isBlank(((HtmlElement)tr1.getFirstByXPath("td")).asText())) {
				throw new ProductScraperException("Prodcut calories could not be scraped");
			}
			
			HtmlElement td1 = tr1.getFirstByXPath("td");
			String kCalPer100g = td1.asText();
			return kCalPer100g;
		}
		
		throw new ProductScraperException("Prodcut calories could not be scraped");
	}
	
	
	
	private HtmlElement getPageContentNode(WebClient client, HtmlElement item) throws ProductScraperException, FailingHttpStatusCodeException, 
																					  MalformedURLException, IOException {
		String itemUrl = ((HtmlAnchor) item.getFirstByXPath(".//h3//a")).getHrefAttribute();
		int index = itemUrl.indexOf("shop");
		String productUrl = SAINSBURYS_URL_BASE + itemUrl.substring(index);
		HtmlPage prodPage = client.getPage(productUrl);
		HtmlElement pageContentNode = (HtmlElement) prodPage.getFirstByXPath(".//div[@class='section productContent']");

		if (pageContentNode == null) {
			throw new ProductScraperException("Unable to generate page content node");
		}
		
		return pageContentNode;
	}
		
	
	// helper method to get the list of product text item nodes to assist in scraping a value for the product's calories.
	// assume pageContentNode argument is not null
	private static List<HtmlElement> getProductTextItems(HtmlElement pageContentNode) { 
		List<HtmlElement> productTextItems = (List<HtmlElement>) pageContentNode.getByXPath(".//div[@class='productText']");
	    return productTextItems == null || productTextItems.size() == 0 ? (List<HtmlElement>) pageContentNode.getByXPath(".//div[@class='itemTypeGroupContainer productText']") : productTextItems;
	}
		
	
	// helper method to get the product description from the given node
	private static String getDescription(HtmlElement pageContentNode) throws ProductScraperException {		
		HtmlElement descNode = descNode = (HtmlElement) pageContentNode.getFirstByXPath(".//div[@class='itemTypeGroupContainer productText']/div[@class='memo']/p");
		if (descNode == null) {
			descNode = (HtmlElement) pageContentNode.getFirstByXPath(".//div[@class='itemTypeGroup']");
		} 

		if (descNode == null) {
			
			descNode = ((HtmlElement) pageContentNode.getFirstByXPath(".//div[@class='productText']/p"));
		}
		
		if (descNode == null) {
			throw new ProductScraperException("Product description could not be scraped");
		}
		
		return descNode.asText();         
	}
	
	
	
}
