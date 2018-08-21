package com.sainsburys.output;
import com.sainsburys.model.IProductGroup;
import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;

public class OutputJob implements IOutputJob {
	
	public String getOutput(IProductGroup prdGrp) {
		ObjectMapper mapperObj = new ObjectMapper();
		
		try {
            // get Employee object as a json string
            return mapperObj.writeValueAsString(prdGrp);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
	}
}
