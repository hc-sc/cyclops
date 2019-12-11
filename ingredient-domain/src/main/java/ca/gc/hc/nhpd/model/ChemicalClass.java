package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Set;

/*******************************************************************************
 * An object that represents an abstract Phytochemical group.
 * @version 1.0
 */
public class ChemicalClass extends PersistentObject 
						   implements ControlledVocabularyObject, Comparable {
    private String code;
    private String commentE;
    private String commentF;
    private String nameE;
    private String nameF;
    private Set<ChemicalSubclass> subclasses;
    
    
    //JSTL needs this in JavaBeans syntax
	public boolean isHasControlledVocabularyDescription() {
		return true;
	}
    
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
     * Gets the collecion of specific Phytochemical groups that make up this
     * abstract group.
     * @return the collecion of specific Phytochemical groups that make up this
     *         abstract group.
     * @see #setSubclasses()
     */
    public Set<ChemicalSubclass> getSubclasses(){
        return subclasses;
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
     * @see #getCommentE()
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
     * Sets the collecion of specific Phytochemical groups that make up this
     * abstract group.
     * @param newVal the collecion of specific Phytochemical groups that make up
     *        this abstract group.
     * @see #getSubclasses()
     */
    public void setSubclasses(Set<ChemicalSubclass> newVal){
        subclasses = newVal;
    }

    public String getControlledVocabularyDescription() {
    	return getComment();
    }
    
    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
        
		if (o == null) {
		    return -1;
		}
		return StringComparator.compare(getName(),((ChemicalClass)o).getName());

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
        if (getCommentE() != null) {
            buffer.append(", commentE: ");
            buffer.append(getCommentE());
        }
        if (getCommentF() != null) {
            buffer.append(", commentF: ");
            buffer.append(getCommentF());
        }
        buffer.append(", subclasses: ");
        buffer.append(getSubclasses());
        
        return buffer.toString();
    }

}