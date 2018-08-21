package com.sainsburys.model;
import java.util.List;
import com.sainsburys.model.Total;

/**
 * @author Paul
 *
 */
public interface IProductGroup {

	List<AbstractProduct> getResults();

	Total getTotal();

	void setResults(List<AbstractProduct> results);
	
}
