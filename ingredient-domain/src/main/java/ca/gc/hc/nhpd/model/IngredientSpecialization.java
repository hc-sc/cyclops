package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Comparator;
import java.util.Set;
//TODO Need to use a LinkedHashSet to preserve order! (requires 1.4.2). See clone() for details.
//import java.util.LinkedHashSet;
import java.util.TreeSet;

/*******************************************************************************
 * An object that represents an available Ingredient subclass. Note that the
 * different subclasses have their own valid categories.
 * @version 1.0
 */
public class IngredientSpecialization extends PersistentObject
                                      implements Cloneable, Comparable {
    private String className;
    private String nameE;
    private String nameF;
    private Set<IngredientCategory> categories; 

    /***************************************************************************
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
     */
    public Set<IngredientCategory> getCategories(){
        return categories;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setClassName(String newVal){
        className = newVal;
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
     * @param newVal
     */
    public void setCategories(Set<IngredientCategory> newVal){
        categories = newVal;
    }

    /**
     * Gets the English short description used to represent this object in drop
     * down lists and other various interfaces.
     *
     * @return  the English specific short description.
     */
    public String getShortDescriptionE() {
        return getNameE();
    }

    /**
     * Gets the English short description used to represent this object in drop
     * down lists and other various interfaces.
     *
     * @return  the French specific short description.
     */
    public String getShortDescriptionF() {
        return getNameF();
    }
    
    /***************************************************************************
     * Overriden so that clones can be made to support English and French (the
     * categories are re-sorted depending on language).
     */
    public Object clone() {
        IngredientSpecialization aClone = new IngredientSpecialization();
        
        super.updateClone(aClone);
        aClone.setClassName(getClassName());
        aClone.setNameE(getNameE());
        aClone.setNameF(getNameF());
        if (getCategories() != null) {
            aClone.setCategories(new TreeSet(getCategories()));
        }

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
    	return StringComparator.compare(getName(),
    			((IngredientSpecialization)o).getName());
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
        buffer.append(", categories: ");
        if (getCategories() != null) {
             buffer.append(getCategories());
        } else {
            buffer.append("null");
        }
        
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
            if (o1 == null || ((IngredientSpecialization)o1).getNameF() == null) {
                return 1;
            }
            if (o2 == null) {
                return -1;
            }
            return ((IngredientSpecialization)o1).getNameF().compareTo(
                   ((IngredientSpecialization)o2).getNameF());
        }
    }
}