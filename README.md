
# Supermarket Scraper
 
GitHub repository: https://github.com/BasicStore/SupermarketScraper 

Run app SupermarketScraper from the main method of com.sainsburys.client.ApplicationMain

Implemented test classes (running on JUnit 4.12):
- com.sainsburys.scraper.BerriesCherriesCurrantsScraperJobTest
- com.sainsburys.output.JsonOutputJobTest
- com.sainsburys.model.CoreProductTest
- com.sainsburys.model.ProductGroupTest

Dependencies listed in the pm file:  
- JUnit 
- Commons lang3 
- HtmlUnit 
- Jackson mapper for JSON conversion

The client, com.sainsburys.client.ApplicationMain, invokes methods from the services to perform the following tasks:
- invokes the scraper to store all the data in a java object
- converts the java object into JSON string format and prints this to the console

The design aims to allow for as much extensibility in future as possible, in particular: 
- cater for more than 1 scraper to be used if desired (defined in com.sainsburys.model.ScraperDefinition) 
- give the opportunity to add a different supermarket scraper, maybe for a different section, not necessarily for edible products, and not even necessarily from Sainsbury's
- give the opportunity to add different output format possibilities. Currently on JSON is implemented, but it would be easy to add an XML or some other output
- allow for different implementations of product in the com.sainsburys.model package
- allow the a different implementation in the com.sainsburys.services package, should this be desired  

Known limitations 
- There is no implementation or testing of the scenario where the 'berries, cherries and currants'
  page is populated with enough products to cover more than 1 page of output 
- There should be some mockito unit testing on com.sainsburys.services.ScraperManager
- There should be some integration testing, that simulates the actions of
  com.sainsburys.client.ApplicationMain
  

  
   



