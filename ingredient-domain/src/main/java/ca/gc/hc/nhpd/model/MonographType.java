package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;

import java.util.Comparator;

/*******************************************************************************
 * An object that represents the various types of monographs that can be 
 * loaded into the system.  The types currently supported include:
 * 
 * <p>ABLS - A
 * <p>PRODUCT - P
 * <p>COMPENDIAL - C
 * <p>TPDCAT4 - T
 * 
 */
public class MonographType extends PersistentObject
	   implements ControlledVocabularyObject, Cloneable, Comparable {

	// For use by the loader and by the DAO in the selection formula.
    public static final String MONOGRAPH_TYPE_CODE_ABLS = "A";    
    public static final String MONOGRAPH_TYPE_CODE_PRODUCT = "P";
    public static final String MONOGRAPH_TYPE_CODE_COMPENDIAL = "C";
    public static final String MONOGRAPH_TYPE_CODE_TPDCAT4 = "T";
    public static final String MONOGRAPH_TYPE_CODE_ABLS_INTERNAL = "I";

    /**
     * These monograph types are not meant to be persisted to the database.  They are only available
     * to facilitate finding out of a monograph belongs to a specific type.
     */
    public static final MonographType MONOGRAPH_TYPE_ABLS = new MonographType(MONOGRAPH_TYPE_CODE_ABLS);
    public static final MonographType MONOGRAPH_TYPE_PRODUCT = new MonographType(MONOGRAPH_TYPE_CODE_PRODUCT);
    public static final MonographType MONOGRAPH_TYPE_COMPENDIAL = new MonographType(MONOGRAPH_TYPE_CODE_COMPENDIAL);
    public static final MonographType MONOGRAPH_TYPE_TPDCAT4 = new MonographType(MONOGRAPH_TYPE_CODE_TPDCAT4);
    public static final MonographType MONOGRAPH_TYPE_ABLS_INTERNAL = new MonographType(MONOGRAPH_TYPE_CODE_ABLS_INTERNAL);

    private String code;
    private String nameE;
    private String nameF;
    
    // Generic Constructor.
    public MonographType(){};
    
    public MonographType(String code) {
    	this.code = code;
    }
    
    //JSTL needs this in JavaBeans syntax
	public boolean isHasControlledVocabularyDescription() {
		return false;
	}

    /***************************************************************************
     */
    public String getCode(){
        return code;
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
     * @param newVal
     */
    public void setCode(String newVal){
        code = newVal;
    }

    /***************************************************************************
     * Sets the name in English.
     * @param newVal the name in English
     * @see #getNameE()
     */
    public void setNameE(String newVal){
        nameE = newVal;
    }

    /***************************************************************************
     * Sets the name in French.
     * @param newVal the name in French
     * @see #getNameF()
     */
    public void setNameF(String newVal){
        nameF = newVal;
    }

    /***************************************************************************
     * Overriden so that clones can be made.
     */
    public Object clone() {
        MonographType aClone = new MonographType();
        
        super.updateClone(aClone);
        aClone.setCode(getCode());
        aClone.setNameE(getNameE());
        aClone.setNameF(getNameF());

        return aClone;
    }

    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
        
        if (o == null) return -1; 
    	return StringComparator.compare(getCode(),((MonographType)o).getCode());
        
    }
    
    /***************************************************************************
     * Determins if this object should be equal Compares this object with another object Equals this object to another object using the code.
     */
    public boolean equals(Object obj) {
        if (obj instanceof MonographType) {
            if (getCode() != null) {
                return (getCode().equals(((MonographType) obj).getCode()));
            } else {
                return (((MonographType) obj).getCode() == null);
            }
        } else {
            return false;
        }
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
     * using their English names.
     */
    public static class EnglishComparator implements Comparator {
        /***********************************************************************
         * Compares the passed objects for order. Returns a negative integer,
         * zero, or a positive integer as the first object is less than, equal
         * to, or greater than the second.
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         */
        public int compare(Object o1, Object o2) throws ClassCastException {
            if (o1 == null || ((MonographType)o1).getNameE() == null) {
                return 1;
            }
            if (o2 == null) {
                return -1;
            }
            return ((MonographType)o1).getNameE().compareTo(
                   ((MonographType)o2).getNameE());
        }
    }

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
            if (o1 == null || ((MonographType)o1).getNameF() == null) {
                return 1;
            }
            if (o2 == null) {
                return -1;
            }
            return ((MonographType)o1).getNameF().compareTo(
                   ((MonographType)o2).getNameF());
        }
    }
}