package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Comparator;

/*******************************************************************************
 */
public class EvidenceType extends PersistentObject implements Comparable {
    private String code;
    private String displayNameE;
    private String displayNameF;
    private String descriptionE;
    private String descriptionF;
    private String nameE;
    private String nameF;
    private String pickNameE;
    private String pickNameF;

    /***************************************************************************
     * Gets the commonly used unique code for this object.
     * @return the commonly used unique code for this object.
     * @see #setCode()
     */
    public String getCode(){
        return code;
    }

    /***************************************************************************
     * Gets the full name in the language appropriate for the Locale, suitable
     * for general display. A concatenation of name and code.
     * @return locale-specific full name, suitable for general display.
     */
    public String getDisplayName(){
        if (isLanguageFrench()) {
            if (displayNameF == null) {
                displayNameF = getNameF() + " (" + getCode() + ")";
            }
            return displayNameF;
        }
        if (displayNameE == null) {
            displayNameE = getNameE() + " (" + getCode() + ")";
        }
        return displayNameE;
    }

	/***************************************************************************
	 * Gets the description in the language appropriate for the Locale.
	 * @return the locale-specific name.
	 */
	public String getDescription(){
		if (isLanguageFrench()) {
			return getDescriptionF();
		}
		return getDescriptionE();
	}

	/***************************************************************************
	 * Gets the description in English.
	 * @return the description in English.
	 * @see #setDescriptionE()
	 */
	public String getDescriptionE(){
		return descriptionE;
	}

	/***************************************************************************
	 * Gets the description in French.
	 * @return the description in French.
	 * @see #setDescriptionF()
	 */
	public String getDescriptionF(){
		return descriptionF;
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

    /***************************************************************************
     * Gets the name in English.
     * @return the name in English.
     * @see #setNameE()
     */
    public String getNameE(){
        return nameE;
    }

    /***************************************************************************
     * Gets the name in French.
     * @return the name in French.
     * @see #setNameF()
     */
    public String getNameF(){
        return nameF;
    }

    /***************************************************************************
     * Gets the full name in the language appropriate for the Locale, suitable
     * for display in a picklist. A concatenation of code and name.
     * @return locale-specific full name, suitable for display in a picklist.
     */
    public String getPickName(){
        if (isLanguageFrench()) {
            if (pickNameF == null) {
                pickNameF = getCode() + " - " + getNameF();
            }
            return pickNameF;
        }
        if (pickNameE == null) {
            pickNameE = getCode() + " - " + getNameE();
        }
        return pickNameE;
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
     * Sets the commonly used unique code for this object.
     * @param newVal the commonly used unique code for this object.
     * @see #getCode()
     */
    public void setCode(String newVal){
        code = newVal;
        displayNameE = null;
        displayNameF = null;
        pickNameE = null;
        pickNameF = null;
    }

	/***************************************************************************
	 * Gets the description in English.
	 * @return the description in English.
	 * @see #setDescriptionE()
	 */
	public void setDescriptionE(String newVal){
		descriptionE = newVal;
	}

	/***************************************************************************
	 * Sets the description in French.
	 * @param newVal the description in French
	 * @see #getDescriptionF()
	 */
	public void setDescriptionF(String newVal){
		descriptionF = newVal;
	}

    /***************************************************************************
     * Gets the name in English.
     * @return the name in English.
     * @see #setNameE()
     */
    public void setNameE(String newVal){
        nameE = newVal;
        displayNameE = null;
        pickNameE = null;
    }

    /***************************************************************************
     * Sets the name in French.
     * @param newVal the name in French
     * @see #getNameF()
     */
    public void setNameF(String newVal){
        nameF = newVal;
        displayNameF = null;
        pickNameF = null;
    }

    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
       	
    	if (o == null) return -1;
        return StringComparator.compare(getName(),((EvidenceType)o).getName());

    }
    

    /***************************************************************************
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Over-ridden to add additional values to the
     * end.
     */
    public String getValuesAsString(){
        StringBuffer buffer = new StringBuffer();
        
        buffer.append(super.getValuesAsString());
        buffer.append(", code: ");
        buffer.append(getCode());
        buffer.append(", nameE: ");
        buffer.append(getNameE());
        buffer.append(", nameF: ");
        buffer.append(getNameF());
        
        return buffer.toString();
    }
    
    //+++ Inner Classes ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /***************************************************************************
     * An implementation of Comparator that allows these objects to be sorted
     * using their French names.
     */
    public static class FrenchComparator implements Comparator {
        /***********************************************************************
         * Compares the passed objects for order. Returns a negative integer,
         * zero, or a positive integer as the first object is less than, equal
         * to, or greater than the second.
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         */
        public int compare(Object o1, Object o2) throws ClassCastException {
            if (o1 == null || ((EvidenceType)o1).getNameF() == null) {
                return 1;
            }
            if (o2 == null) {
                return -1;
            }
            return ((EvidenceType)o1).getNameF().compareTo(
                   ((EvidenceType)o2).getNameF());
        }
    }
}