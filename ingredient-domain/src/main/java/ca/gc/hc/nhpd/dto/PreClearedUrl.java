package ca.gc.hc.nhpd.dto;

import java.util.Set;
import java.util.TreeSet;
import ca.gc.hc.nhpd.model.Monograph;
import ca.gc.hc.nhpd.model.MonographType;

/**
 * Helper object.  Used to parse out the complex information found in the 
 * ENGLISH|FRENCH_PRECLEARED_URL column of the INGREDIENT_SYNONYMS_MV.  This
 * information is then used to display the precleared URL on the ingredient
 * search result page and for the web services.
 * 
 * PreClearedUrl are a represent of the linking between ingredients and 
 * monographs, and are defined via the MonogarphEntry object.  From a business
 * perspective, when a client searches for an ingredient, he/she should be
 * able to see, in the search results, that a monograph exists for this 
 * ingredient.  Submitting an application for a monograph is easier than
 * submitting an application for an ingredient.
 * 
 * Exmaples:  The following 3 examples illustrate the nature of the information
 * we are getting from the PreClearred view:
 * 
 * 1.  Generated Monograph - Alfalfa
 * 2.  External Monograph hosted by the Ingredient Database - Arnica
 * 3.  External Monograph hosted externally - Garlic
 * 
 * @author GEBRUNET
 */

public class PreClearedUrl {

	private final static String TILDA="~";
	private final static String SEMI_COLON=";";
	private final static String GENERATED_MONOGRAPH_URL_START = "monoReq.do?id=";
    private final static String GENERATED_MONOGRAPH_URL_MIDDLE = "&lang=";
    
	private String monographName;
	private Set<MonographType> monographTypes;
	private String monographStatus;
	private String monographUrl;
	private Integer monographId;
    
	/**
	 * Constructor.  Used to build the object and populate all the fields.
	 * 
	 * @param builder string containing the MV information.
	 */
	public PreClearedUrl(String builder) {
		String[] component = builder.split(TILDA);
		setMonographName(component[0]);
		setMonographTypes(buildMonographType(component[1]));
		setMonographStatus(component[2]);
		setMonographUrl(component[3]);
		setMonographId(new Integer(component[4]));
	}
	
	/**
	 * Setters and Getters.
	 */
	public String getMonographName() {
		return monographName;
	}
	public void setMonographName(String monographName) {
		this.monographName = monographName;
	}
	
	public Set<MonographType> getMonographTypes() {
		return monographTypes;
	}
	public void setMonographTypes(Set<MonographType> monographTypes) {
		this.monographTypes = monographTypes;
	}
	
	public String getMonographStatus() {
		return monographStatus;
	}
	public void setMonographStatus(String monographStatus) {
		this.monographStatus = monographStatus;
	}
	
	public String getMonographUrl() {
		return monographUrl;
	}
	public void setMonographUrl(String monographUrl) {
		this.monographUrl = monographUrl;
	}
	
	public Integer getMonographId() {
		return monographId;
	}
	public void setMonographId(Integer monographId) {
		this.monographId = monographId;
	}

    /**
     * This function is used by both the Ingredient Web Application and the Web 
     * Services to return the appropriate monograph url.  In the cases of 
     * Generated Monographs, this URL must be manually built.  Otherwise, we 
     * can leverate the Monograph URL that was loaded in the spreadsheets.
     * 
     * @return the URL to this PreClearedURL monograph.
     */
    public String getUrl() {
        // When the Monograph is generated monograph.  We need to build the URL.
        if (monographStatus.equals(Monograph.MONOGRAPH_STATUS_GENERATED)) {
            StringBuilder url = new StringBuilder();
            url.append(GENERATED_MONOGRAPH_URL_START);
            url.append(getMonographId());
            url.append(GENERATED_MONOGRAPH_URL_MIDDLE);
            return url.toString();
        }
        // Otherwise, return the URL.
        return getMonographUrl();
    }
    
	/**
	 * Returns a true if the Monograph Type collection contains an
	 * Internal Abls Monograph.
	 * 
	 * @return true when Monograph Type collection contains Internal AbLS
	 */
	public boolean isInternalAbls() {
		if (monographTypes != null && monographTypes.size() != 0) {
			if (monographTypes.contains(MonographType.MONOGRAPH_TYPE_ABLS_INTERNAL)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns a text version of this object.  Used for debugging purposes.
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Monograph Name: " + getMonographName() + ", ");
		sb.append("Monograph Types: ");
		if (monographTypes == null || monographTypes.size() == 0) {
			sb.append("null, ");
		} else {
			sb.append(getMonographTypes() + ", ");
		}
		sb.append("Monograph Status: " + getMonographStatus() + ", ");
		sb.append("Monograph Url: " + getMonographUrl() + ", ");
		sb.append("Monograph Id: " + getMonographId());
		return sb.toString();
	}

	/**
	 * Method used to decifer the Type string into appropriate MonographTypes.
	 * 
	 * @param values semi-colon delimited string representing Monograph Types.
	 * 
	 * @return equivalent MonographType objects.
	 */
	private Set<MonographType> buildMonographType(String values) {
		if (values == null || values.length() == 0) {
			return null;
		}
		
		String[] typeString = values.split(SEMI_COLON);

		Set<MonographType> monographTypes = new TreeSet<MonographType>();

		for (String type : typeString) {
			if (type.equals(MonographType.MONOGRAPH_TYPE_CODE_ABLS)) {
				monographTypes.add(MonographType.MONOGRAPH_TYPE_ABLS);
			}
			if (type.equals(MonographType.MONOGRAPH_TYPE_CODE_ABLS_INTERNAL)) {
				monographTypes.add(new MonographType(MonographType.MONOGRAPH_TYPE_CODE_ABLS_INTERNAL));
			}
			if (type.equals(MonographType.MONOGRAPH_TYPE_CODE_COMPENDIAL)) {
				monographTypes.add(MonographType.MONOGRAPH_TYPE_COMPENDIAL);
			}
			if (type.equals(MonographType.MONOGRAPH_TYPE_CODE_PRODUCT)) {
				monographTypes.add(MonographType.MONOGRAPH_TYPE_PRODUCT);
			}
			if (type.equals(MonographType.MONOGRAPH_TYPE_CODE_TPDCAT4)) {
				monographTypes.add(MonographType.MONOGRAPH_TYPE_TPDCAT4);
			}
		}
		
		return monographTypes;
	}
}
