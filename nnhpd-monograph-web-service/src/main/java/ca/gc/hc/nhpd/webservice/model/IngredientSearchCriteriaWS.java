/*
 *
 * Provides a Web Service wrapper for the IngredientSearchCriteria.
 *
 * Created on 4-Jun-07
 *
 */
package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.dto.IngredientSearchCriteria;
import ca.gc.hc.nhpd.dto.SearchCriterion;

import java.util.List;

/**
 * @author MRABE
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class IngredientSearchCriteriaWS {
    private IngredientSearchCriteria searchCriteria; // the wrapped criteria

    public IngredientSearchCriteriaWS(IngredientSearchCriteria criteria) {
        this.searchCriteria = criteria;
    }

    public IngredientSearchCriteriaWS() {
        this.searchCriteria = new IngredientSearchCriteria();
    }

    public SearchCriterion[] getCriteria() {
        List<SearchCriterion> criteria = searchCriteria.getCriteria();
        return (SearchCriterion[]) criteria.toArray(new SearchCriterion[0]);
    }

    public void setCriteria(SearchCriterion[] criteria) {
        searchCriteria = new IngredientSearchCriteria();
        for (int x = 0; x < criteria.length; x++) {
            searchCriteria.addCriterion(criteria[x]);
        }
    }

}
