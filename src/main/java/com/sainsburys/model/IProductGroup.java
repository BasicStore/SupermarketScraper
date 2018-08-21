/**
 * 
 */
package com.sainsburys.model;

import java.util.List;

/**
 * @author Paul
 *
 */
public interface IProductGroup {

	void addProduct(AbstractProduct product);
	
	List<AbstractProduct> getProductList();

	String getGross();

	String getVat();
	
}
