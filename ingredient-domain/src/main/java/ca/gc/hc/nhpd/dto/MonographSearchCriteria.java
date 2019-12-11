/*
 * Created on 5-Jun-07
 *
 */
package ca.gc.hc.nhpd.dto;
import ca.gc.hc.nhpd.dto.SearchCriteria;
import ca.gc.hc.nhpd.dto.SearchCriterion;

/**
 * @author MRABE
 *
 * Models Monograph specific SearchCriteria.  Currnetly limited to just a 
 * simple text search string.  The implementation code (e.g Materialized view) 
 * searched through Monographs, Ingredients, Synonymns, etc. for occurences
 * of the namestring.  
 * 
 * This class adheres to the existing generic SearchCriteria design, although 
 * only the text string is used curently, it provides a placeholder for future 
 * search attributes.  
 * 
 * TODO - should put "validateNameString" method in base class.  
 */
public class MonographSearchCriteria extends SearchCriteria {

    //boolean values for search parameters
    public static final String TRUE = "true";
    public static final String FALSE = "false";

	//generic search text param - searches multiple columns
	public static final String NAMESTRING  	         = "NAMESTRING";
    public static final String DISPLAY_ABLS          = "DISPLAY_ABLS";
    public static final String DISPLAY_ABLS_INTERNAL = "DISPLAY_ABLS_INTERNAL";
    public static final String DISPLAY_PRODUCTS      = "DISPLAY_PRODUCTS"; 
	
	public boolean validateCriterion(SearchCriterion criterion) {
		if (criterion == null) return false;
		String attrName = criterion.getAttributeName();
        String attrValue = criterion.getAttributeValue();

        if (attrName == null) return false;
        if ( (!attrName.equals(NAMESTRING)) &&
             (!attrName.equals(DISPLAY_ABLS)) &&
             (!attrName.equals(DISPLAY_ABLS_INTERNAL)) &&
             (!attrName.equals(DISPLAY_PRODUCTS)))
            return false;

        if (attrName.equals(NAMESTRING)) return validateNameString(attrValue);
        if (attrName.equals(DISPLAY_ABLS)) return validateDisplayBoolean(attrValue);
        if (attrName.equals(DISPLAY_ABLS_INTERNAL)) return validateDisplayBoolean(attrValue);
        if (attrName.equals(DISPLAY_PRODUCTS)) return validateDisplayBoolean(attrValue);

        return true;

	}

	private boolean validateNameString(String name) {
		char invalidChars[] = {'?', ';', '{', '}'};

		//null or null string name is OK
		if ( (name == null) || (name.equals("")) )
			return true;
		
		for (int x=0;x<name.length();x++)
			for (int y=0;y<invalidChars.length;y++)
				if (name.charAt(x) == invalidChars[y])
					return false;
		
		return true;
	}
	
    private boolean validateDisplayBoolean(String parameter) {
        if (parameter.trim().toLowerCase().equals(TRUE) 
                || parameter.trim().toLowerCase().equals(FALSE)) {
            return true;
        }
        return false;
    }
	
}
