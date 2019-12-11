package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import ca.gc.hc.nhpd.util.StringUtils;


/**
 * An object that represents homeopathic dilutions.  Examples of these dilutions
 * include X, D, CH, and MT.
 */
public class HomeopathicDilution extends PersistentObject
    implements Comparable {

    private String  code;
    private String  commentE;
    private String  commentF;
    private String  descriptionE;
    private String  descriptionF;
    private Boolean diluted;
    private String  nameE;
    private String  nameF;
    private Double  ratioToBase;

    /**
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param  o  the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {

        if (o == null) {
            return -1;
        }

        return StringComparator.compare(getCode(),
                ((HomeopathicDilution) o).getCode());

    }

    /**
     * Gets the commonly used unique code for this object.
     *
     * @return  the commonly used unique code for this object.
     *
     * @see     #setCode()
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets the comment in the language appropriate for the Locale.
     *
     * @return  the locale-specific comment.
     */
    public String getComment() {

        if (isLanguageFrench()) {
            return getCommentF();
        }

        return getCommentE();
    }

    /**
     * Gets the comment in English.
     *
     * @return  the comment in English.
     *
     * @see     #setCommentE()
     */
    public String getCommentE() {
        return commentE;
    }

    /**
     * Gets the comment in French.
     *
     * @return  the comment in French.
     *
     * @see     #setCommentF()
     */
    public String getCommentF() {
        return commentF;
    }

    /**
     * Gets the description in English.
     *
     * @return  the description in English.
     *
     * @see     #setDescriptionE()
     */
    public String getDescriptionE() {
        return descriptionE;
    }

    /**
     * Gets the description in French.
     *
     * @return  the description in French.
     *
     * @see     #setDescriptionF()
     */
    public String getDescriptionF() {
        return descriptionF;
    }

    /**
     * Gets the description in the language appropriate for the Locale.
     *
     * @return  the locale-specific name.
     */
    public String getDescription() {

        if (isLanguageFrench()) {
            return getDescriptionF();
        }

        return getDescriptionE();
    }
    
    /**
     * Gets whether this is actually diluting the ingredient (most will).
     * 
     * @return a boolean that indicates whether this is actually diluting the
     *         ingredient.  
     *         
     * @see    setDiluted()        
     */
    public Boolean getDiluted() {
        return diluted;
    }

    /**
     * Getter used by Hibernate that returns the string flag indicating if 
     * the HomeopathicDilution is diluted.
     *
     * @return  String flag indicating if this object is diluted.
     */
    public String getDilutedAsString() {
        return StringUtils.booleanToString(diluted);
    }
    
    /**
     * Gets the name in the language appropriate for the Locale.
     *
     * @return  the locale-specific name.
     */
    public String getName() {

        if (isLanguageFrench()) {
            return getNameF();
        }

        return getNameE();
    }

    /**
     * Gets the name in English.
     *
     * @return  the name in English.
     *
     * @see     #setNameE()
     */
    public String getNameE() {
        return nameE;
    }

    /**
     * Gets the name in French.
     *
     * @return  the name in French.
     *
     * @see     #setNameF()
     */
    public String getNameF() {
        return nameF;
    }

    /**
     * Gets the ratio of this Dilution to a "base" Dilution (in this case, X).
     * Used for converting Dilutions.
     * 
     * @return a Number that contains the ratio of this Dilution to the base
     *         "X" Dilution.
     * 
     * @see    #setRatioToBase
     */
    public Double getRatioToBase() {
        return ratioToBase;
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
     * Gets the French short description used to represent this object in drop
     * down lists and other various interfaces.
     *
     * @return  the French specific short description.
     */
    public String getShortDescriptionF() {
        return getNameF();
    }

    /**
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Over-ridden to add additional values to the
     * end.
     */
    public String getValuesAsString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(super.getValuesAsString());
        buffer.append(", code: ");
        buffer.append(getCode());
        buffer.append(", nameE: ");
        buffer.append(getNameE());
        buffer.append(", nameF: ");
        buffer.append(getNameF());
        buffer.append(", commentE: ");
        buffer.append(getCommentE());
        buffer.append(", commentF: ");
        buffer.append(getCommentF());
        buffer.append(", descriptionE: ");
        buffer.append(getDescriptionE());
        buffer.append(", descriptionF: ");
        buffer.append(getDescriptionF());

        return buffer.toString();
    }

    /**
     * Sets the commonly used unique code for this object.
     *
     * @param  newVal  the commonly used unique code for this object.
     *
     * @see    #getCode()
     */
    public void setCode(String newVal) {
        code = newVal;
    }

    /**
     * Sets the comment in English.
     *
     * @return  the comment in English.
     *
     * @see     #setCommentE()
     */
    public void setCommentE(String newVal) {
        commentE = newVal;
    }

    /**
     * Sets the comment in French.
     *
     * @param  newVal  the comment in French
     *
     * @see    #getCommentF()
     */
    public void setCommentF(String newVal) {
        commentF = newVal;
    }

    /**
     * Sets the description in English.
     *
     * @return  the description in English.
     *
     * @see     #getDescriptionE()
     */
    public void setDescriptionE(String newVal) {
        descriptionE = newVal;
    }

    /**
     * Sets the description in French.
     *
     * @param  newVal  the description in French
     *
     * @see    #getDescriptionF()
     */
    public void setDescriptionF(String newVal) {
        descriptionF = newVal;
    }

    /**
     * Sets whether this is actually diluting the ingredient (most will).
     * 
     * @param  diluted a boolean that indicates whether this is actually 
     *         diluting the ingredient.  
     *
     * @see    #getDiluted()
     */
    public void setDiluted(Boolean diluted) {
        this.diluted = diluted;
    }

    /**
     * Setter used by Hibernate indicating if this Homeopathic Dilution is diluted.
     *
     * @param  diluted  string flag indicating if this object is diluted.
     */
    public void setDilutedAsString(String diluted) {
        this.diluted = StringUtils.stringToBoolean(diluted);
    }
    
    /**
     * Gets the name in English.
     *
     * @return  the name in English.
     *
     * @see     #setNameE()
     */
    public void setNameE(String newVal) {
        nameE = newVal;
    }

    /**
     * Sets the name in French.
     *
     * @param  newVal  the name in French
     *
     * @see    #getNameF()
     */
    public void setNameF(String newVal) {
        nameF = newVal;
    }
    
    /**
     * Sets the ratio of this Dilution to a "base" Dilution (in this case, X).
     * Used for converting Dilutions.
     * 
     * @param  a Number that contains the ratio of this Dilution to the base
     *         "X" Dilution.
     * 
     * @see    #getRatioToBase
     */
    public void setRatioToBase(Double newVal) {
        ratioToBase = newVal;
    }

}
