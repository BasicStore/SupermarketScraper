package com.sainsburys.output;
import com.sainsburys.model.IProductGroup;
import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;


/**
 * Converts a product group into a JSON string
 * @author Paul
 *
 */
public class JsonOutputJob implements IOutputJob {
	
	/**
	 * Returns the JSON representation of this group of products
	 * @param prdGrp - the product group 
	 * @return String - the JSON product group representation
	 */
	public String getOutput(IProductGroup prdGrp) {
		ObjectMapper mapperObj = new ObjectMapper();
		try {
            return mapperObj.writeValueAsString(prdGrp);
        } catch (IOException e) {
        	e.printStackTrace();
        	return "Error: Anable to scrape products - " + e.getMessage();
        }
	}
}
