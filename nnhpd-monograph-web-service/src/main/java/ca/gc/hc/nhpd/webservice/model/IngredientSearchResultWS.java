package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.dto.IngredientSearchResult;
import ca.gc.hc.nhpd.dto.PreClearedUrl;
import ca.gc.hc.nhpd.model.DefinedOrganismSubstance;
import ca.gc.hc.nhpd.util.ThreadContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* 
 * Class which encapsulates Ingredient Search results for the Web Service.
 * Wraps an Ingredient model object, but only exposes the Ingredient id, 
 * approved name, common names, and type 
 */
public class IngredientSearchResultWS {
	
	private final static String MONOGRAPH_STATUS_EXTERNAL = "E";
	private final static String MONOGRAPH_GENERATED_KEY = "GeneratedMonoId:";
	
	private String version = ThreadContext.getInstance().getVersion();
	private String language = ThreadContext.getInstance().getUserLanguage();

	// the wrapped ingredientSearchResult
    private IngredientSearchResult ingredientSearchResult;
    private boolean nonMedicinal;

    public IngredientSearchResultWS() {
    }

    public IngredientSearchResultWS(IngredientSearchResult ingredient, boolean nonMedicinal) {
        this.ingredientSearchResult = ingredient;
        this.nonMedicinal = nonMedicinal;
    }

    public String getApprovedName() {
        return ingredientSearchResult.getAuthorizedName();
    }

    public void setApprovedName(String approvedName) {
        //
    }

    public List<String> getCommonNames() {
        return ingredientSearchResult.getCommonNames();
    }

    public void setCommonNames(List<String> commonNames) {
        //
    }

    public Long getId() {
        return ingredientSearchResult.getIngredientId();
    }

    public void setId(Long id) {
        //
    }

    public String getType() {
        return ingredientSearchResult.getType();
    }

    public void setType(String type) {
        //
    }

    /**
     * gets Precleared Information URLS/ids.  Filters out Internal ABLS ids.
     * @return a Map of Monograph Name and URL/monograph id key/value pairs
     * 
     */
    public Map<String,String> getPreClearedUrls() {
    	if (ingredientSearchResult == null ||
    			ingredientSearchResult.getPreClearedUrls() == null) {
    		return null;
    	}
    	// Do not return PCI for nonMedicinal searches.
    	if (nonMedicinal) {
    		return null;
    	}
    	HashMap<String, String> map = new HashMap<String, String>();
    	for (String key : ingredientSearchResult.getPreClearedUrls().keySet()) {
    		PreClearedUrl preClearedUrl = ingredientSearchResult.getPreClearedUrls().get(key);
    		
    		if (preClearedUrl.getMonographStatus().equals(MONOGRAPH_STATUS_EXTERNAL)) {
    			map.put(key, preClearedUrl.getMonographUrl());
    		} else if (preClearedUrl.getMonographId() != null) {
    			if (version.equals(ThreadContext.VERSION_1_4)) {
    				// 1.4 functionality
        			String url = MONOGRAPH_GENERATED_KEY + preClearedUrl.getMonographId();
        			map.put(key, url);
    			} else {
    				// 2.0 functionality
    				StringBuffer url = new StringBuffer();
    				url.append(preClearedUrl.getUrl());
    				url.append(language);
    				map.put(key, url.toString());
    			}
    		}
    	}
    	
    	return map; 
    }
    
    public void setPreClearedUrls(Map<String,String> preClearedUrls) {
    	//null impl for JAXB
    }
 
   
}
