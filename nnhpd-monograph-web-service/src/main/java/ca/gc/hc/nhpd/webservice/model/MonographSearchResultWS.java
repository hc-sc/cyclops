package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.dto.MonographSearchResult;
import java.util.List;

/* 
 * Class which encapsulates Monograph Search results for the Web Service.
 * Wraps a MonographSearchResult model object, but only exposes the 
 * following attributes: 
 * 	- Monograph id, 
 * 	- Monograph name,
 *  - primary ingredient name
 *  - common names (synonyms) 
 */
public class MonographSearchResultWS {

    // the wrapped MonographSearchResult
    private MonographSearchResult monographSearchResult;

    public MonographSearchResultWS() {
    }

    public MonographSearchResultWS(MonographSearchResult monograph) {
        this.monographSearchResult = monograph;
    }

    public void setMonographName(String name) {
        // null impl - method required so that wsimport will generate the
        // client side representation of this class.
    }

    public String getMonographName() {
        return monographSearchResult.getMonographName();
    }

    public String getPrimaryIngredientName() {
        return monographSearchResult.getPrimaryIngredientName();
    }

    public void setPrimaryIngredientName(String approvedName) {
        //
    }

    public List<String> getCommonNames() {
        return monographSearchResult.getSynonyms();
    }

    public void setCommonNames(List<String> commonNames) {
        //
    }

    public Long getId() {
        return monographSearchResult.getMonographId();
    }

    public void setId(Long id) {
        //
    }

}
