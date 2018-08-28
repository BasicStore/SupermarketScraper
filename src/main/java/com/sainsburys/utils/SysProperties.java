package com.sainsburys.utils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;


public class SysProperties {

	// object to hold the singleton instance of this class
	private static SysProperties sysProperties;
	
	// store the properties in a map
	private Map<String, String> propVals = new HashMap<String, String>();
		
	private SysProperties() throws IOException {
		load();
	}
	
	
	public static SysProperties getInstance() throws IOException {
		if (sysProperties == null) {
			return new SysProperties();
		}
		
		return sysProperties;
	}
	
	
	private void load() throws IOException {
		Properties prop = new Properties();
		InputStream input = new FileInputStream("src/main/resources/config.properties");
		Exception failure = null;
		
		try {
			prop.load(input);
			Enumeration<?> e = prop.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = prop.getProperty(key);
				propVals.put(key, value);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			failure = ex;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
					failure = e;
				}
			}
		}
		
		if (failure != null) {
			throw new IOException("Error retrieving data from config.properties");
		}
	}
	
	
	public String getProperty(String key) throws IOException {
		
		if (propVals == null) {
			throw new IOException("Property " + key + " could not be retrieved");
		}
		
		return propVals.get(key);
	}
	
	
}
