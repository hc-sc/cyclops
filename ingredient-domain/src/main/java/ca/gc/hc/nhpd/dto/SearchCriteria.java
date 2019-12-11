/*
 * 
 * Models generic Search Criteria.   
 *
 * Created on 4-Jun-07
 *
 */
package ca.gc.hc.nhpd.dto;

import ca.gc.hc.nhpd.exception.IngredientsException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author MRABE
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public  class SearchCriteria
{
	private List<SearchCriterion> criteria = new ArrayList<SearchCriterion>();

	protected  boolean validateCriterion(SearchCriterion criterion)
    {
        return false;  //default behaviour
    }
	
	public void addCriterion(SearchCriterion criterion)
	{
		if (!validateCriterion(criterion))
			throw new IngredientsException
				("Invalid search criterion specified:  Attribute Name: "
			    + criterion.getAttributeName() 
			    + "  Attribute Value: " + criterion.getAttributeValue());
		criteria.add(criterion);
	}
       
	/**
	 * @return
	 */
	public List<SearchCriterion> getCriteria() {
		return criteria;
	}

	/**
	 * @param list
	 */
	public void setCriteria(List<SearchCriterion> list) {
		criteria = list;
	}
	
	public SearchCriterion getCriterionByName(String criterionName)
	{
		//iterate thru the list to get the named criterion value
		Iterator iter = criteria.iterator();
		SearchCriterion searchCriterion = null;
		while (iter.hasNext())
		{
			searchCriterion = (SearchCriterion)iter.next();
			if (searchCriterion.getAttributeName().equals(criterionName))
				return searchCriterion;
		}
		return null;   //not found
	}

}
