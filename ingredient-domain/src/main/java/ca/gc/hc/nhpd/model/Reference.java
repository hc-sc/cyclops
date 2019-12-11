package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;

/*******************************************************************************
 * An object that represents a commonly used authoritative reference. These are
 * assigned codes and ultimately refer to a citation as the source.
 * @version 1.0
 */
public class Reference extends PersistentObject implements Comparable {
	
	public static final String REFERENCE_CODE_NHPD_PROPER_NAME = "NHPD-ProperName"; 
	
    private static final String STRING_OPEN_SQUARE_BRACKET = "[";
    private static final String STRING_CLOSE_SQUARE_BRACKET = "]";
    private static final String EMPTY_STRING = "";
    private static final String STRING_SPACE = " ";
    
    private Citation citation;
    private String code;
    private String commentE;
    private String commentF;
    private String nameE;
    private String nameF;

    /***************************************************************************
     */
    public Citation getCitation(){
        return citation;
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
     * Gets the English long description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the English specific short description.
     */
    public String getLongDescriptionE() {
        
        StringBuilder sb = new StringBuilder();
        
        if (getCode() != null 
                && !EMPTY_STRING.equals(getCode())) {
            sb.append(STRING_OPEN_SQUARE_BRACKET);
            sb.append(getCode());
            sb.append(STRING_CLOSE_SQUARE_BRACKET);
            sb.append(STRING_SPACE);
        }

        sb.append(getNameE());
        
        return sb.toString();
    }

    /***************************************************************************
     * Gets the English long description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the French specific short description.
     */
    public String getLongDescriptionF() {
        
        StringBuilder sb = new StringBuilder();
        
        if (getCode() != null 
                && !EMPTY_STRING.equals(getCode())) {
            sb.append(STRING_OPEN_SQUARE_BRACKET);
            sb.append(getCode());
            sb.append(STRING_CLOSE_SQUARE_BRACKET);
            sb.append(STRING_SPACE);
        }
        
        sb.append(getNameF());
        
        return sb.toString();
    }
    
    /***************************************************************************
     * @param newVal
     */
    public void setCitation(Citation newVal){
        citation = newVal;
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
     * @param newVal
     */
    public void setNameE(String newVal){
        nameE = newVal;
    }

    /***************************************************************************
     * @param newVal
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
		return StringComparator.compare(getName(),((Reference)o).getName());
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
        buffer.append(", citation: ");
        buffer.append(getCitation());
        
        return buffer.toString();
    }
}