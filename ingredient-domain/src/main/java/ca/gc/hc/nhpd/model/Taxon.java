package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
/*******************************************************************************
 * An object that represents an item's latin synonym.
 */
public class Taxon extends PersistentObject implements Comparable {
    private String commentE;
    private String commentF;
    private String name;
    private Reference reference;
    private Organism organism;
    
    public Organism getOrganism() { return organism; }
    
    public void setOrganism( Organism newValue ) { organism = newValue; }

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
    public String getName(){
        return name;
    }

    /***************************************************************************
     */
    public Reference getReference(){
        return reference;
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
     * @param newVal
     */
    public void setName(String newVal){
        name = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setReference(Reference newVal){
        reference = newVal;
    }

    /***************************************************************************
     * Gets the English short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the English specific short description.
     */
    public String getShortDescriptionE() {
        return getName();
    }

    /***************************************************************************
     * Gets the French short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the French specific short description.
     */
    public String getShortDescriptionF() {
        return getName();
    }
    
    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
       
        if (o == null) return -1; 
    	return StringComparator.compare(getName(),((Taxon)o).getName());
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
        buffer.append(", name: ");
        buffer.append(getName());
        if ( getOrganism() != null ) {
            buffer.append(", organism: ");
            buffer.append(getOrganism().getName());
        }
        
        return buffer.toString();
    }
}