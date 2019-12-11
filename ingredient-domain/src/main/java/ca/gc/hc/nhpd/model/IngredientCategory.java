package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Comparator;

/*******************************************************************************
 * An object that represents the category that an ingredient falls into. This is
 * finer grained than the ingredient subclasses. Examples are AAN (Approved Name)
 * and AHN (Approved Herbal Names).
 */
public class IngredientCategory extends PersistentObject
	   implements ControlledVocabularyObject, Cloneable, Comparable {
                                    
    public static String PROTEIN_CODE = "APN";    
    public static String HERBAL_COMPONENT_CODE = "HCN";   
    public static String HOMEOPATHIC_SUBSTANCE_CODE = "HMN";
    private String code;
    private String nameE;
    private String nameF;
    private IngredientSpecialization specialization;

    //JSTL needs this in JavaBeans syntax
	public boolean isHasControlledVocabularyDescription() {
		return false;
	}

    /***************************************************************************
     */
    public IngredientSpecialization getSpecialization(){
        return specialization;
    }


    /***************************************************************************
     * @param newVal
     */
    public void setSpecialization(IngredientSpecialization newVal){
        specialization = newVal;
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
        IngredientCategory aClone = new IngredientCategory();
        
        super.updateClone(aClone);
        aClone.setCode(getCode());
        aClone.setNameE(getNameE());
        aClone.setNameF(getNameF());
        aClone.setSpecialization(getSpecialization());

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
    	return StringComparator.compare(getName(),((IngredientCategory)o).getName());
        
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
            if (o1 == null || ((IngredientCategory)o1).getNameE() == null) {
                return 1;
            }
            if (o2 == null) {
                return -1;
            }
            return ((IngredientCategory)o1).getNameE().compareTo(
                   ((IngredientCategory)o2).getNameE());
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
            if (o1 == null || ((IngredientCategory)o1).getNameF() == null) {
                return 1;
            }
            if (o2 == null) {
                return -1;
            }
            return ((IngredientCategory)o1).getNameF().compareTo(
                   ((IngredientCategory)o2).getNameF());
        }
    }
}