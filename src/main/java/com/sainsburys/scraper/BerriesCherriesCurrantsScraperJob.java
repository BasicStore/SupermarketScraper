package com.sainsburys.scraper;
import com.sainsburys.model.AbstractProduct;
import com.sainsburys.model.CoreProduct;
import com.sainsburys.model.IProductGroup;
import com.sainsburys.model.Product;
import com.sainsburys.model.ProductGroup;
import java.util.ArrayList;
import java.util.List;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class BerriesCherriesCurrantsScraperJob implements IScraperJob {

	private static final String SAINSBURYS_URL_BASE = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/";
	private static final String PRODUCT_GROUP_PAGE_URL = SAINSBURYS_URL_BASE + "webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
	
	public BerriesCherriesCurrantsScraperJob() {
	
		
		
	}
	
	
	public IProductGroup generateProductGroup() {
		// create and optimize client
		WebClient client = new WebClient();  
		client.getOptions().setCssEnabled(false);  
		client.getOptions().setJavaScriptEnabled(false);  
		
		List<AbstractProduct> productList = new ArrayList<AbstractProduct>();
		
		try {  
			// !!!!!!!!! implement paging from here?
			HtmlPage page = client.getPage(PRODUCT_GROUP_PAGE_URL);
			
			
			
			// TODO implement paging by looping from this point, after first iteration
			List<HtmlElement> items = (List<HtmlElement>) page.getByXPath(".//div[@class='productNameAndPromotions']");
			//List<HtmlElement> items = null; // new ArrayList<HtmlElement>();
			
			
		    if (items != null && !items.isEmpty()) {
				for(HtmlElement item : items) {

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
					//System.out.println("product name = " + prodName);
					String title = prodNameNode.asText();
					
					// get product price
					String rawPrice = ((HtmlElement) pageContentNode.getFirstByXPath(".//p[@class='pricePerUnit']")).asText();
					rawPrice = rawPrice.substring(0, rawPrice.indexOf("/"));
					//System.out.println("price = " + rawPrice);
					
					// get description
					//String desc = getDescription(pageContentNode);
					//System.out.println("Description = " + desc);
					String description = getDescription(pageContentNode);
					
					// get calories
					HtmlElement nutritionTable = (HtmlElement) pageContentNode.getFirstByXPath(".//table[@class='nutritionTable']");	
					if (nutritionTable == null) {
						//System.out.println("Calories = NOT AVAILABLE\n");
						productList.add(new CoreProduct(title, rawPrice, description));
						continue;
					}
					
					String kCalPer100g = null;
					List<HtmlElement> productTextItems = getProductTextItems(pageContentNode);
					HtmlElement productText = productTextItems.get(1);
					List<HtmlElement> trItemsTest = (List<HtmlElement>) nutritionTable.getByXPath(".//tbody/tr");
					if (trItemsTest.size() > 0) {
						HtmlElement tr1 = trItemsTest.get(1);
						HtmlElement td1 = tr1.getFirstByXPath("td");
						String cals1 = td1.asText();
						//System.out.println("Calories = " + cals1 + "\n");
						kCalPer100g = td1.asText();
					}
					
					productList.add(new Product(title, rawPrice, description, kCalPer100g));
					

					// CATCH HERE:  structural (PriceFormatException + NodeNotFoundException)
				}
			}
		} catch(Exception e) {
			
			// TODO: IOException, FailingHttpStatusCodeException  ELSE  
			
			
		    e.printStackTrace();
		}    
	
		IProductGroup prdGrp = new ProductGroup();
		prdGrp.setResults(productList);
		return prdGrp;
	}
	
	
	
	private static List<HtmlElement> getProductTextItems(HtmlElement pageContentNode) { 
		List<HtmlElement> productTextItems = (List<HtmlElement>) pageContentNode.getByXPath(".//div[@class='productText']");
	    return productTextItems == null || productTextItems.size() == 0 ? (List<HtmlElement>) pageContentNode.getByXPath(".//div[@class='itemTypeGroupContainer productText']") : productTextItems;
	}
		
	
	
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
