package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Comparator;

/*******************************************************************************
 * An object that represents an available IngredientRole subclass.
 * @version 1.0
 */
public class IngredientRoleSpecialization extends PersistentObject
                                          implements Comparable {
    private String className;
    private String nameE;
    private String nameF;

    /***************************************************************************
     * Gets the name of the subclass this object represents.
     * @return the name of the subclass this object represents.
     * @see #setClassName()
     */
    public String getClassName(){
        return className;
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
     * Sets the name of the subclass this object represents.
     * @param newVal the name of the subclass this object represents.
     * @see #getClassName()
     */
    public void setClassName(String newVal){
        className = newVal;
    }

    /***************************************************************************
     * Gets the name in English.
     * @return the name in English.
     * @see #setNameE()
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
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
       
        if (o == null) return -1; 
    	return StringComparator.compare(getName(),
    			((IngredientRoleSpecialization)o).getName());
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
        buffer.append(", className: ");
        buffer.append(getClassName());
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
            if (o1 == null || ((IngredientRoleSpecialization)o1).getNameF() == null) {
                return 1;
            }
            if (o2 == null) {
                return -1;
            }
            return ((IngredientRoleSpecialization)o1).getNameF().compareTo(
                   ((IngredientRoleSpecialization)o2).getNameF());
        }
    }
}
