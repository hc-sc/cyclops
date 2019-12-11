package ca.gc.hc.nhpd.dto;

import ca.gc.hc.nhpd.format.NameDisplayUtils;
import ca.gc.hc.nhpd.model.ChemicalSubstance;
import ca.gc.hc.nhpd.model.DefinedOrganismSubstance;
import ca.gc.hc.nhpd.model.HomeopathicSubstance;
import ca.gc.hc.nhpd.model.Organism;
import ca.gc.hc.nhpd.util.LocaleSensitiveString;
import ca.gc.hc.nhpd.util.StringUtils;
import ca.gc.hc.nhpd.util.StringComparator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import org.apache.log4j.Logger;
/**
 * Lighweight DTO (Data Transfer Object) which encapsulates attributes of an 
 * Ingredient search result.  Used by both IDB web app and IWS (web service)
 * Enables performant queries against the denormalized Ingredient_Synonyms
 * Materialized View without instantiating a large Ingredient object graph.  
 *   
 * @author MRABE
 *
 */
public class IngredientSearchResult { 
	// public static final String INTERNAL_ABLS_PREFIX = "InternalAblsId:"; 
	// public static final String INTERNAL_TEXT_ABLS_PREFIX = "InternalAblsUrl:"; 
	
	private static final String nameSeparator = "<br/>";
	
	private long ingredientId;
	private List<String> commonNames;
	private List<String> properNames;
	private String authorizedName;
    private List<String> synonyms;
    private Map<String,PreClearedUrl> preClearedUrlMap;
	private String type;     //ingredient type, e.g. class name
	private boolean includeAblsInternalMonographs;
	
	/* 
	 * constructor which creates an IngredientSearchResult instance from
	 * a single row of the database search result
	 */
	public IngredientSearchResult(Object[] searchResult,
								  boolean isFrench,
								  boolean includeProperNames,
								  boolean includeAblsInternalMonographs) {

        this.includeAblsInternalMonographs = includeAblsInternalMonographs;
		ingredientId = ((BigDecimal)searchResult[0]).longValue();
		
		authorizedName = (String)searchResult[1];
		synonyms = StringUtils.extractDelimitedNames((String)searchResult[2],
													 nameSeparator,null);
        properNames = StringUtils.extractDelimitedNames((String)searchResult[3],
        		 										 nameSeparator,null);
        commonNames = StringUtils.extractDelimitedNames((String)searchResult[4],
        				 								 nameSeparator,null);
        preClearedUrlMap = extractPreClearedUrls((String)searchResult[5]);
		type = getTypeForCategory((String)searchResult[6],isFrench);
		
	}
	
	/*
	 * builds Map entries: Monograph Name, Precleared Url from materialized 
	 * view column 
	 */
	private Map<String,PreClearedUrl> extractPreClearedUrls(String preClearedUrlString) {
		
		Map<String,PreClearedUrl> retPreClearedUrls = new HashMap<String,PreClearedUrl>();
		
		if (preClearedUrlString != null && preClearedUrlString.length() > 0) {	
			String[] preClearedUrls = preClearedUrlString.trim().split(nameSeparator);
			for (String preClearedUrl : preClearedUrls) {
				PreClearedUrl preClearedUrlObject = new PreClearedUrl(preClearedUrl);
                if (!includeAblsInternalMonographs && preClearedUrlObject.isInternalAbls()) {
                    // This is an internla AbLS and we are not configured to display this information.  
                    // Dont add it to the map.
                } else {
                    retPreClearedUrls.put(preClearedUrlObject.getMonographName(),preClearedUrlObject);
                }
			}
		}
		
		return retPreClearedUrls;
		
	}
	
	
	/*
	 * removes the Proper Names from the synonyms
	 */
	/*
	private List<String> removeProperNames() {
		
		//put the synonyms into a TreeSet 
		TreeSet<LocaleSensitiveString> commonNamesSet = new TreeSet<LocaleSensitiveString>();
		for (String commonName: commonNames) {
			commonNamesSet.add(new LocaleSensitiveString(commonName));	
		}
		
		for (String properName: properNames) {
			commonNamesSet.remove(new LocaleSensitiveString(properName)); 
		}
		
		//back to List
		List<String> results = new ArrayList<String>();
		for (LocaleSensitiveString synonym: commonNamesSet) {
			results.add(synonym.getString()); 
		}
		
		return results;
		
	}*/
	
	/*
	 * Maps the Category to an ingredient type, based on the bilingual
	 * type strings exposed by the model objects.
	 * 
	 */
	private String getTypeForCategory(String category, boolean isFrench) {
        
		if (isFrench) {
            if (category == null) 
                return "Indéfini";
			if (category.equals("ABN") || category.equals("AHN")) 
				return Organism.TAXONOMY_TYPE_F;
			if (category.equals("ABS") || 
				category.equals("AFN") || 
				category.equals("AHS"))
			    return DefinedOrganismSubstance.TYPE_DOS_F;
			if (category.equals("ACN")) 
				return ChemicalSubstance.TYPE_CHEMICAL_SUBSTANCE_F;
			if (category.equals("HCN")) 
				return ChemicalSubstance.TYPE_HERBAL_COMPONENT_F;
			if (category.equals("APN")) 
				return ChemicalSubstance.TYPE_PROTEIN_F;
			if (category.equals("HMN"))
				return HomeopathicSubstance.TYPE_F;
			
			return "Indéfini";
		}
		else {
		    if (category == null) 
                return "Undefined";
        	if (category.equals("ABN") || category.equals("AHN")) 
				return Organism.TAXONOMY_TYPE_E;
			if (category.equals("ABS") || 
				category.equals("AFN") || 
				category.equals("AHS"))
			    return DefinedOrganismSubstance.TYPE_DOS_E;
			if (category.equals("ACN")) 
				return ChemicalSubstance.TYPE_CHEMICAL_SUBSTANCE_E;
			if (category.equals("HCN")) 
				return ChemicalSubstance.TYPE_HERBAL_COMPONENT_E;
			if (category.equals("APN")) 
				return ChemicalSubstance.TYPE_PROTEIN_E;
			if (category.equals("HMN"))
				return HomeopathicSubstance.TYPE_E;
			
			return "Undefined";
		}
	
	}
	
	
	public long getIngredientId() {
		return ingredientId;
	}
	public void setIngredientId(long ingredientId) {
		this.ingredientId = ingredientId;
	}
	public List<String> getCommonNames() {
		return NameDisplayUtils.breakNames(commonNames);
	}
	public void setCommonNames(List<String> commonNames) {
		this.commonNames = commonNames;
	}
	
	public List<String> getProperNames() {	
		return NameDisplayUtils.breakNames(properNames);
	}
	
	public void setProperNames(List<String> properNames) {
		this.properNames = properNames;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public boolean equals(Object object) {
		return this.ingredientId == ((IngredientSearchResult)object).ingredientId; 
	}
	
	
	public String getAuthorizedName() {
		return authorizedName;
	}

	public void setAuthorizedName(String authorizedName) {
		this.authorizedName = authorizedName;
	}
	
	
	
	public Map<String, PreClearedUrl> getPreClearedUrlMap() {
		return preClearedUrlMap;
	}
	
	/**
	 * Convenience accessor allowing client to specify whether or not they 
	 * want internal Abls PCI 
	 */
	public Map<String,PreClearedUrl> getPreClearedUrls() {
        return preClearedUrlMap;
	}
	
	
	
	public void setPreClearedUrlMap(Map<String, PreClearedUrl> preClearedUrlMap) {
		this.preClearedUrlMap = preClearedUrlMap;
	}
	
	/** 
	 * Determines whether this search result has any generated or legacy static page
	 * PCI.  This is referred to as external PCI, as opposed to Internal (ABLS) PCI, 
	 * which is only exposed if the web app is configured to do so.   This is used by 
	 * ingredientSearchBody.jsp 
	 * @return boolean flag
	 */
	public boolean isHasExternalPci() {
		// Do we have any precleared URLs?
		if (getPreClearedUrls() == null || getPreClearedUrls().size() == 0) {
			return false;
		}
		// Does it contain any values?
		if (getPreClearedUrls().keySet() != null 
				&& getPreClearedUrls().keySet().size() != 0) {
			return true;
		}
		return false;
	}
	
}
