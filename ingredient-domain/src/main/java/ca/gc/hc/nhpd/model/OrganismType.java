package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Comparator;

/*******************************************************************************
 * An object that represents a type of organism (its Kingdom or Phylum).
 * @version 1.0
 */
public class OrganismType extends PersistentObject implements Comparable {
    private String code;
    private String commentE;
    private String commentF;
    private OrganismTypeGroup group;
    private String nameE;
    private String nameF;

    /***************************************************************************
     * Gets the commonly used unique code for this object.
     * @return the commonly used unique code for this object.
     * @see #setCode()
     */
    public String getCode(){
        return code;
    }

    /***************************************************************************
     * Gets the comment in the language appropriate for the Locale.
     * @return the locale-specific comment.
     */
    public String getComment(){
        if (isLanguageFrench()) {
            return getCommentF();
        }
        return getCommentE();
    }

    /***************************************************************************
     * Gets the comment in English.
     * @return the comment in English.
     * @see #setCommentE()
     */
    public String getCommentE(){
        return commentE;
    }

    /***************************************************************************
     * Gets the comment in French.
     * @return the comment in French.
     * @see #setCommentF()
     */
    public String getCommentF(){
        return commentF;
    }

    /***************************************************************************
     */
    public OrganismTypeGroup getGroup(){
        return group;
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
     * Sets the commonly used unique code for this object.
     * @param newVal the commonly used unique code for this object.
     * @see #getCode()
     */
    public void setCode(String newVal){
        code = newVal;
    }

    /***************************************************************************
     * Sets the comment in English.
     * @param newVal the comment in English
     * @see #getEmailAddress()
     */
    public void setCommentE(String newVal){
        commentE = newVal;
    }

    /***************************************************************************
     * Gets the comment in French.
     * @param newVal the comment in French
     * @see #getCommentF()
     */
    public void setCommentF(String newVal){
        commentF = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setGroup(OrganismTypeGroup newVal){
        group = newVal;
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
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
       
        if (o == null) return -1; 
    	return StringComparator.compare(getName(),((OrganismType)o).getName());
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
        buffer.append(", group: ");
        if (getGroup() != null) {
            buffer.append(getGroup().getName());
        } else {
            buffer.append("null");
        }
        buffer.append(", nameE: ");
        buffer.append(getNameE());
        buffer.append(", nameF: ");
        buffer.append(getNameF());
        buffer.append(", commentE: ");
        buffer.append(getCommentE());
        buffer.append(", commentF: ");
        buffer.append(getCommentF());
        
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
            if (o1 == null || ((OrganismType)o1).getNameF() == null) {
                return 1;
            }
            if (o2 == null) {
                return -1;
            }
            return ((OrganismType)o1).getNameF().compareTo(
                   ((OrganismType)o2).getNameF());
        }
    }
}