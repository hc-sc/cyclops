package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;


/**
 * An object that represents homeopathic preparation types.  Because of the
 * nature of the homeopathic ingredients, we kept these preparation types
 * seperate from "standard" organism preparation types. Examples include:  EHP,
 * EHP Class 1, etc.
 */
public class HomeopathicPreparationType extends PersistentObject
    implements Comparable {

    private String code;
    private String commentE;
    private String commentF;
    private String nameE;
    private String nameF;

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
                ((HomeopathicPreparationType) o).getCode());

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

    public String getDescription() {
        return getName();
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
     * Gets the comment in English.
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
}
