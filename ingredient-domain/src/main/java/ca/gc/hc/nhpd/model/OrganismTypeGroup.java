package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Set;

/*******************************************************************************
 * An object that represents a group of organism types, and whether geographical
 * distribution or country of origin is pertinent to this group. A type can only
 * be in one group.
 * @version 1.0
 */
public class OrganismTypeGroup extends PersistentObject implements Comparable {
    	
	// These 2 constants are for fixing ITS#8920. -MD20120321
    public final static String NON_HUMAN_ANIMAL_ENGLISH = "Non-human Animal";
    public final static String NON_HUMAN_ANIMAL_FRENCH = "Animal non-humain";
    public final static String UNDEFINED = "undefined";
    
    private String commentE;
    private String commentF;
    private boolean countriesOfOriginRelevant;
    private boolean geographicDistributionRelevant;
    private String nameE;
    private String nameF;
    private Set<OrganismPartType> possiblePartTypes;
    //private Set types;

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
    public Set<OrganismPartType> getPossiblePartTypes(){
        return possiblePartTypes;
    }

    /***************************************************************************
     * Gets the English short description used to represent this object in drop
     * down lists and other various interfaces.
     *
     * @return  the English specific short description.
     */
    public String getShortDescriptionE() {
        return getNameE();
    }

    /***************************************************************************
     * Gets the English short description used to represent this object in drop
     * down lists and other various interfaces.
     *
     * @return  the French specific short description.
     */
    public String getShortDescriptionF() {
        return getNameF();
    }

    /***************************************************************************
     */
    /*public Set getTypes(){
        return types;
    }*/

    /***************************************************************************
     */
    public boolean isCountriesOfOriginRelevant(){
        return countriesOfOriginRelevant;
    }

    /***************************************************************************
     */
    public boolean isGeographicDistributionRelevant(){
        return geographicDistributionRelevant;
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
    public void setCountriesOfOriginRelevant(boolean newVal){
        countriesOfOriginRelevant = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setGeographicDistributionRelevant(boolean newVal){
        geographicDistributionRelevant = newVal;
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
    public void setPossiblePartTypes(Set<OrganismPartType> newVal){
        possiblePartTypes = newVal;
    }

    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
        
        if (o == null) return -1; 
    	return StringComparator.compare(getName(),((OrganismTypeGroup)o).getName());
    
    }
    
    
    /***************************************************************************
     * @param newVal
     */
    /*public void setTypes(Set newVal){
        types = newVal;
    }*/

    /***************************************************************************
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Over-ridden to add additional values to the
     * end.
     */
    public String getValuesAsString(){
        StringBuffer buffer = new StringBuffer();
        
        buffer.append(super.getValuesAsString());
        buffer.append(", nameE: ");
        buffer.append(getNameE());
        buffer.append(", nameF: ");
        buffer.append(getNameF());
        buffer.append(", countriesOfOriginRelevant: ");
        buffer.append(isCountriesOfOriginRelevant());
        buffer.append(", geographicDistributionRelevant: ");
        buffer.append(isGeographicDistributionRelevant());
        buffer.append(", commentE: ");
        buffer.append(getCommentE());
        buffer.append(", commentF: ");
        buffer.append(getCommentF());
        buffer.append(", possiblePartTypes: ");
        buffer.append(getPossiblePartTypes());
        
        return buffer.toString();
    }
}