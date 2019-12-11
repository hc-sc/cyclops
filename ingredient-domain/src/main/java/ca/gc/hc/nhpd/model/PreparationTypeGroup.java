/*
 * Created on 18-Sep-07
 *
 */
package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Set;

/**
 * The PreparationTypeGroup group class contains a grouping of PreparationType objects for data referencing simplification. 
 */
public class PreparationTypeGroup extends PersistentObject implements Comparable {

	private String code;
	private String nameE;
	private String nameF;
	private Set<PreparationType> preparationTypes;

	public String getCode(){
		return code;
	}

	/**
	 * Gets the description in the language appropriate for the Locale.
	 * @return the locale-specific description.
	 */
	public String getDescription(){
		return "";
	}

	public String getNameE(){
		return nameE;
	}

	public String getNameF(){
		return nameF;
	}

	public Set<PreparationType> getPreparationTypes(){
		return preparationTypes;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setCode(String newVal){
		code = newVal;
	}

    /***************************************************************************
     * Gets the name in the language appropriate for the Locale.
     * @return the locale-specific name.
     */
    public String getName(){
        if (isLanguageFrench()) {
            return getNameF();
        }
        return getNameE();
    }
    
	/**
	 * 
	 * @param newVal
	 */
	public void setNameF(String newVal){
		nameF = newVal;
	}
	public void setNameE(String newVal){
		nameE = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setPreparationTypes(Set<PreparationType> newVal){
		preparationTypes = newVal;
	}
    
    /***************************************************************************
     * Gets the English short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the English specific short description.
     */
    public String getShortDescriptionE() {
        return getNameE();
    }

    /***************************************************************************
     * Gets the English short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the French specific short description.
     */
    public String getShortDescriptionF() {
        return getNameF();
    }
    
    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
       
        if (o == null) return -1; 
    	return StringComparator.compare(getName(),((PreparationTypeGroup)o).getName());
    }
}
