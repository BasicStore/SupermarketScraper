package com.sainsburys.scraper;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.sainsburys.exceptions.ProductScraperException;
import com.sainsburys.model.AbstractProduct;
import com.sainsburys.model.Product;


/**
 * Class to test methods in the com.sainsburys.scraper.BerriesCherriesCurrantsScraperJob class
 * @author Paul
 *
 */
public class BerriesCherriesCurrantsScraperJobTest {
	
	
	@Test
	public void testScrapeProductsSuccessfullyOnListHtmlPage() throws ProductScraperException, FailingHttpStatusCodeException, MalformedURLException, IOException {
		WebClient client = makeWebClient();
		File file = new File(getClass().getClassLoader().getResource("berries-cherries-currants6039.html").getFile());
		HtmlPage page = client.getPage(file.toURI().toURL()); 
		
		BerriesCherriesCurrantsScraperJob scraper = new BerriesCherriesCurrantsScraperJob();
		List<AbstractProduct> productList = new ArrayList<AbstractProduct>();
		scraper.scrapeProducts(client, page, productList);
		
		assertNotNull(productList);
		assertEquals(17, productList.size());

		Product prod1 = (Product)productList.get(0);
		assertEquals("Sainsbury's Strawberries 400g", prod1.getTitle());
		assertEquals("£1.75", prod1.getUnitPrice());
		assertEquals("by Sainsbury's strawberries", prod1.getDescription());
		assertEquals("33kcal", prod1.getkCalPer100g());
	}
	
	
	
	@Test
	public void testNoProductsFoundWithABlankListHtmlPage() throws ProductScraperException, FailingHttpStatusCodeException, MalformedURLException, IOException {
		WebClient client = makeWebClient();
		File file = new File(getClass().getClassLoader().getResource("empty-berries-cherries-currants6040.html").getFile());
		HtmlPage page = client.getPage(file.toURI().toURL());
		List<AbstractProduct> productList = new ArrayList<AbstractProduct>();
		BerriesCherriesCurrantsScraperJob scraper = new BerriesCherriesCurrantsScraperJob();
		scraper.scrapeProducts(client, page, productList);
		assertEquals(0, productList.size());
	}
	
	@Test
	public void testScrapingSpecificProductDetailsSuccessfully() throws ProductScraperException, FailingHttpStatusCodeException, MalformedURLException, IOException {
		HtmlElement pageContentNode = makePageContentNode("Sainsburys_Redcurrants_150g.html");
		BerriesCherriesCurrantsScraperJob scraper = new BerriesCherriesCurrantsScraperJob();
		String actName = scraper.getProductName(pageContentNode);
		String actDesc = BerriesCherriesCurrantsScraperJob.getDescription(pageContentNode, actName);
		BigDecimal actPrice = scraper.getProductPrice(pageContentNode, actName);
		HtmlElement nutritionTable = (HtmlElement) pageContentNode.getFirstByXPath(".//table[@class='nutritionTable']");
		String actCalories = scraper.getProductCalories(pageContentNode, nutritionTable, actName);
		assertEquals("Sainsbury's Redcurrants 150g", actName);
		assertEquals("by Sainsbury's redcurrants", actDesc);
		assertEquals(new BigDecimal("2.50"), actPrice);
		assertEquals("71kcal", actCalories);		
		
		pageContentNode = makePageContentNode("Sainsburys_Cherry_Punnet_Taste_the_Difference_200g.html");
		actName = scraper.getProductName(pageContentNode);
		actDesc = BerriesCherriesCurrantsScraperJob.getDescription(pageContentNode, actName);
		actPrice = scraper.getProductPrice(pageContentNode, actName);
		actCalories = scraper.getProductCalories(pageContentNode, nutritionTable, actName);
		assertEquals("Sainsbury's Cherry Punnet, Taste the Difference 200g", actName);
		assertEquals("Cherry Punnet", actDesc);
		assertEquals(new BigDecimal("2.50"), actPrice);
		assertEquals("71kcal", actCalories);		
		
		pageContentNode = makePageContentNode("Sainsburys_British_Cherry_Strawberry_Pack_600g_no_calories.html");
		actName = scraper.getProductName(pageContentNode);
		actDesc = BerriesCherriesCurrantsScraperJob.getDescription(pageContentNode, actName);
		actPrice = scraper.getProductPrice(pageContentNode, actName);
		assertEquals("Sainsbury's British Cherry & Strawberry Pack 600g", actName);
		assertEquals("British Cherry & Strawberry Mixed Pack", actDesc);
		assertEquals(new BigDecimal("4.00"), actPrice);
	}
	
		
	
	@Test
	public void testExceptionThrownWithUnreadableProductName() throws ProductScraperException, FailingHttpStatusCodeException, MalformedURLException, IOException {
		HtmlElement pageContentNode = makePageContentNode("Sainsburys_Redcurrants_150g_no_name.html");
		BerriesCherriesCurrantsScraperJob scraper = new BerriesCherriesCurrantsScraperJob();
		
		boolean crashed = false;
		try {
			String actName = scraper.getProductName(pageContentNode);
		} catch(ProductScraperException e) {
			crashed = true;
			assertEquals("Product name could not be scraped", e.getMessage());
		}
		
		assertTrue(crashed);
	}
	
	
	
	@Test
	public void testExceptionThrownWithUnreadableProductDescription() throws ProductScraperException, FailingHttpStatusCodeException, MalformedURLException, IOException {
		HtmlElement pageContentNode = makePageContentNode("Sainsburys_Cherry_Punnet_Taste_the_Difference_200g_bad_desc.html");
		BerriesCherriesCurrantsScraperJob scraper = new BerriesCherriesCurrantsScraperJob();
		String prodName = scraper.getProductName(pageContentNode);
		
		boolean crashed = false;
		try {
			String actDesc = BerriesCherriesCurrantsScraperJob.getDescription(pageContentNode, prodName);
			
			System.out.println("description = " + actDesc);
			
		} catch(ProductScraperException e) {
			crashed = true;
			assertEquals("Product description could not be scraped for product " + prodName, e.getMessage());
		}
		
		assertTrue(crashed);
	}
	
	
	
	@Test
	public void testExceptionThrownWithUnreadableProductPriceData() throws ProductScraperException, FailingHttpStatusCodeException, MalformedURLException, IOException {
		HtmlElement pageContentNode = makePageContentNode("Sainsburys_Redcurrants_150g_bad_price_bad_calorie_data.html");
		BerriesCherriesCurrantsScraperJob scraper = new BerriesCherriesCurrantsScraperJob();
		String prodName = scraper.getProductName(pageContentNode);
		
		boolean crashed = false;
		try {
			BigDecimal actPrice = scraper.getProductPrice(pageContentNode, prodName);
		} catch(ProductScraperException e) {
			crashed = true;
			assertEquals("Product price could not be scraped for product " + prodName, e.getMessage());
		}
		
		assertTrue(crashed);
	}
	
		
	
	@Test
	public void testExceptionThrownWithIssuesReadingCalorieData() throws ProductScraperException, FailingHttpStatusCodeException, MalformedURLException, IOException {
//		WebClient client = makeWebClient();
//		File file = new File(getClass().getClassLoader().getResource("Sainsburys_Redcurrants_150g_bad_price_bad_calorie_data.html").getFile());
//		HtmlPage page = client.getPage(file.toURI().toURL());
//		HtmlElement pageContentNode = (HtmlElement) page.getFirstByXPath(".//div[@class='section productContent']");
		HtmlElement pageContentNode = makePageContentNode("Sainsburys_Redcurrants_150g_bad_price_bad_calorie_data.html");
		BerriesCherriesCurrantsScraperJob scraper = new BerriesCherriesCurrantsScraperJob();
		String prodName = scraper.getProductName(pageContentNode);
		
		boolean crashed = false;
		try {
			HtmlElement nutritionTable = (HtmlElement) pageContentNode.getFirstByXPath(".//table[@class='nutritionTable']");
			String actCalories = scraper.getProductCalories(pageContentNode, nutritionTable, prodName);
		} catch(ProductScraperException e) {
			crashed = true;
			assertEquals("Product calories could not be scraped for product " + prodName, e.getMessage());
		}
		
		assertTrue(crashed);
	}
	
	
	
	private WebClient makeWebClient() {
		WebClient client = new WebClient();  
		client.getOptions().setCssEnabled(false);  
		client.getOptions().setJavaScriptEnabled(false);  
		return client;
	}

	
	private HtmlElement makePageContentNode(String filename) throws ProductScraperException, FailingHttpStatusCodeException, MalformedURLException, IOException {
		WebClient client = makeWebClient();
		File file = new File(getClass().getClassLoader().getResource(filename).getFile());
		HtmlPage page = client.getPage(file.toURI().toURL());
		return (HtmlElement) page.getFirstByXPath(".//div[@class='section productContent']");
	}
	
	
}

