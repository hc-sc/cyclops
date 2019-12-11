/*
 * Created on 5-Jun-07
 *
 */
package ca.gc.hc.nhpd.dto;
import ca.gc.hc.nhpd.dto.SearchCriteria;
import ca.gc.hc.nhpd.dto.SearchCriterion;

import java.util.List;
import java.util.HashMap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;

/**
 * @author MRABE
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class IngredientSearchCriteria extends SearchCriteria
{
	
	//boolean values for search parameters
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	
	//search filter attributes (name of the name/value pair)
	public static final String ROLE 			= "ROLE";
	public static final String CATEGORY 		= "CATEGORY";
	public static final String CLASSNAME  	    = "CLASSNAME";
	//generic search text param - searches multiple columns
	public static final String NAMESTRING  	    = "NAMESTRING";
	
	//optional search parameters that help control the information that is 
	//presented by the web services.
	public static final String DISPLAY_HOMEOPATHIC = "DISPLAY_HOMEOPATHIC";
	
	//optional search parameters that returns ingredients that were updated
	//from a specified date.  The list of fields being tracked are 
	//specific to NHPSAS.
	public static final String NHPSASCHANGEDATE     = "NHPSASCHANGEDATE";
	
	//application type.  Currently supports COMPENDIAL and COMPENDIALGENERATED
	public static final String APPLICATIONTYPE  	= "APPLICATIONTYPE";
	//ClassName values
	public static final String ORGANISMSUBSTANCE    = "ORGANISMSUBSTANCE";
	public static final String ORGANISM 		    = "ORGANISM";
	public static final String CHEMICALSUBSTANCE    = "CHEMICALSUBSTANCE";
	public static final String HOMEOPATHICSUBSTANCE = "HOMEOPATHICSUBSTANCE";

	//Role values
	public static final String MEDICINAL 		= "MEDICINAL";
	public static final String NONMEDICINAL 	= "NONMEDICINAL";
	public static final String HOMEOPATHIC      = "HOMEOPATHIC";

	

	//database equivalents for role and class values.  Provides a level of 
	//abstraction between the client and DB implementation.  Also, provides
	//consistent all CAPS literals to the WS client
	public static final String MEDICINALROLE    		 = "MedicinalRole";
	public static final String NONMEDICINALROLE 		 = "NonMedicinalRole";
	public static final String COMPONENTROLE    		 = "ComponentRole";
	public static final String NONNHPROLE 				 = "NonNHPRole";
	public static final String HOMEOPATHICROLE			 = "HomeopathicRole";
	
	public static final String CHEMICALSUBSTANCECLASS 	 = "ChemicalSubstance";
	public static final String ORGANISMSUBSTANCECLASS	 = "DefinedOrganismSubstance";
	public static final String HOMEOPATHICSUBSTANCECLASS = "HomeopathicSubstance";


   /*
    * Compendial Search Types:
	* For Ingredient searches on Compendial applications, the search result
	* set needs to be restricted to only those Ingredients which are associated
	* to Monographs.   
	*/
	/** Compendial - legacy static pages - not in object model yet */
	public static final String COMPENDIAL 			= "COMPENDIAL";
	public static final String COMPENDIALGENERATED 	= "COMPENDIALGENERATED";
    public static final String ABLS                 = "ABLS";
    public static final String ABLSGENERATED        = "ABLSGENERATED";
    
	
	//Category code values
	//TODO - this should be an enum when we move to JDK 1.5
	public static final String ABN 	= "ABN";	//Approved Biological Name
	public static final String ABS  = "ABS";	//Approved Biological Substance Name
	public static final String ACN  = "ACN";	//Approved Chemical Name
	public static final String ADN  = "ADN";	//Approved Device Name
	public static final String AFN  = "AFN";	//Approved Food Name
	public static final String AHN  = "AHN";	//Approved Herbal Name
	public static final String AHS  = "AHS";	//Approved Herbal Substance Name
	public static final String APN  = "APN";	//Approved Protein Name
	public static final String HCN  = "HCN";	//Herbal Component Name
	public static final String HMN  = "HMN";    //Homeophatic Substances
	
	static HashMap<String, String> classMap = new HashMap<String, String>();
	static HashMap<String, String> roleMap = new HashMap<String, String>();

	/* 
	 * These tables map web tier literal values to their database equivalents 
	 * for both the web services and web app.
	 */
	static {

		roleMap.put(MEDICINAL, MEDICINALROLE);
		roleMap.put(MEDICINALROLE, MEDICINALROLE);
		roleMap.put(NONMEDICINAL, NONMEDICINALROLE);
		roleMap.put(NONMEDICINALROLE, NONMEDICINALROLE);
		roleMap.put(MEDICINALROLE, MEDICINALROLE);
		roleMap.put(COMPONENTROLE, COMPONENTROLE);
		roleMap.put(NONNHPROLE, NONNHPROLE);
		roleMap.put(HOMEOPATHIC, HOMEOPATHICROLE);
		roleMap.put(HOMEOPATHICROLE, HOMEOPATHICROLE);
		
		classMap.put(CHEMICALSUBSTANCE, CHEMICALSUBSTANCECLASS);
		classMap.put(ORGANISM, ORGANISMSUBSTANCECLASS);
		classMap.put(ORGANISMSUBSTANCE, ORGANISMSUBSTANCECLASS);
		classMap.put(HOMEOPATHIC, HOMEOPATHICSUBSTANCECLASS);

	}
	
	public boolean validateCriterion(SearchCriterion criterion) {
		if (criterion == null) return false;
		String attrName = criterion.getAttributeName();
        String attrValue = criterion.getAttributeValue();

        if (attrName == null) return false;
		if ( (!attrName.equals(ROLE)) &&
			 (!attrName.equals(CATEGORY)) &&
			 (!attrName.equals(CLASSNAME)) &&
			 (!attrName.equals(NAMESTRING)) && 
			 (!attrName.equals(DISPLAY_HOMEOPATHIC)) &&
			 (!attrName.equals(APPLICATIONTYPE)) &&
			 (!attrName.equals(NHPSASCHANGEDATE)) ) 
		    return false;

		if (attrName.equals(ROLE)) return validateRole(attrValue);
        if (attrName.equals(CATEGORY)) return validateCategory(attrValue);
        if (attrName.equals(CLASSNAME)) return validateClassName(attrValue);
        if (attrName.equals(NAMESTRING)) return validateNameString(attrValue);
        if (attrName.equals(DISPLAY_HOMEOPATHIC)) return validateDisplayHomeopathic(attrValue);
        if (attrName.equals(APPLICATIONTYPE)) return validateApplicationType(attrValue);
        if (attrName.equals(NHPSASCHANGEDATE)) return validateNHPSASChangeDate(attrValue);
        
		return true;
	}

	private boolean validateRole(String role) {
		return roleMap.containsKey(role);
	}

	private boolean validateCategory(String category) {
		if ( (!category.equals(ABN)) &&
			 (!category.equals(ABS)) &&
			 (!category.equals(ACN)) &&
			 (!category.equals(ADN)) &&
			 (!category.equals(AFN)) &&
			 (!category.equals(AHN)) &&
			 (!category.equals(AHS)) &&
			 (!category.equals(APN)) &&
			 (!category.equals(HCN)) &&
			 (!category.equals(HMN)) )
			return false;
		return true;
	}

	private boolean validateClassName(String className) {

		if ( (!className.equals(CHEMICALSUBSTANCE)) &&
			 (!className.equals(ORGANISMSUBSTANCE)) &&
			 (!className.equals(HOMEOPATHICSUBSTANCE)) &&
			 (!className.equals(ORGANISM)) )
			return false;
		
		return classMap.containsKey(className);

	}
	
	private boolean validateApplicationType(String plaType) {
		if ( (!plaType.equals(COMPENDIAL)) &&
             (!plaType.equals(COMPENDIALGENERATED)) &&
             (!plaType.equals(ABLS)) &&
			 (!plaType.equals(ABLSGENERATED)) )
			return false;
		return true;
	}
	
	private boolean validateNameString(String name) {
		//No validation required at this time
		return true;
	}

	private boolean validateDisplayHomeopathic(String parameter) {
		if (parameter.trim().toLowerCase().equals(TRUE)
				|| parameter.trim().toLowerCase().equals(FALSE)) {
			return true;
		}
		return false;
	}

	// The following routine validates that the parameter is a valid
	// date format earlier than today. 
	private boolean validateNHPSASChangeDate(String parameter) {
		try {
	        // Convert the parameter text into a usable date format.
	        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = (Date) formatter.parse(parameter);
	        
	        // Get handle on today's date.
	        Calendar today = new GregorianCalendar();
	        if (date.after(today.getTime())) {
	        	return false;
	        }
	        return true;
		} catch (Exception e) {
	    	return false;
	    }
	}
	
	/* 
	 * converts the client side criteria (e.g. the all uppercase values)
	 * to their DB equivalents
	 */
	public void convertToDBCriteria() {
		
		List criteria = getCriteria();
		SearchCriterion criterion = null;
		String attrName = null;
		String attrValue = null;
		
		for (int x=0;x<criteria.size();x++) {
			criterion = (SearchCriterion)criteria.get(x);
			attrName = criterion.getAttributeName();
			attrValue = criterion.getAttributeValue();
			if (attrName.equals(ROLE)) {
				if (roleMap.containsKey(attrValue)) {	
					criterion.setAttributeValue(roleMap.get(attrValue));
				}
			}
			if (attrName.equals(CLASSNAME)) {
				if (classMap.containsKey(attrValue)) {	
					criterion.setAttributeValue(classMap.get(attrValue));
				}
			}
		}
		
	}
	
}
