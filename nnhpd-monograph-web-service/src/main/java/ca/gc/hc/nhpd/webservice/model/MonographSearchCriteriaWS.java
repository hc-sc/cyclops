/*
 *
 * Provides a Web Service wrapper for the MonographSearchCriteria.
 *
 * Created on 4-Jun-07
 *
 */
package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.dto.MonographSearchCriteria;
import ca.gc.hc.nhpd.dto.SearchCriterion;

import java.util.List;

/**
 * @author MRABE
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MonographSearchCriteriaWS {
    private MonographSearchCriteria searchCriteria; // the wrapped criteria

    public MonographSearchCriteriaWS(MonographSearchCriteria criteria) {
        this.searchCriteria = criteria;
    }

    public MonographSearchCriteriaWS() {
        this.searchCriteria = new MonographSearchCriteria();
    }

    public List<SearchCriterion> getCriteria() {
        return searchCriteria.getCriteria();
    }

    public void setCriteria(List<SearchCriterion> criteria) {

    }

}
