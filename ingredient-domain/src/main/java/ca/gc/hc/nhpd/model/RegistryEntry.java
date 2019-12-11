package ca.gc.hc.nhpd.model;

/*******************************************************************************
 * An object that represents an entry in a Chemical registry (e.g. the Chemical
 * Abstract Service).
 */
public class RegistryEntry extends PersistentObject implements Comparable {
    static private final String CAS_REFERENCE_CODE = "CAS";

    private String entryNumber;
    private String commentE;
    private String commentF;
    private String nameE;
    private String nameF;
    private Reference reference;

    //~ public -----------------------------------------------------------------

    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>Updated on March 27th, 2007. Puts any entry that has a CAS reference
     * at the top of the list. Leaves the remaining entries regardless of
     * reference in the original CasNumber sort order.
     *
     * @param   o  the object to compare this to.
     *
     * @return  a sort order pointer based on the Reference, then Cas Entry
     *          number
     *
     * @throws  ClassCastException  when the object can not be compared as a
     *                              CasEntry
     */
    public int compareTo(Object o) throws ClassCastException {
        if (getEntryNumber() == null) {
            return 1;
        }

        if (o == null) {
            return -1;
        }

        if (getReference() != ((RegistryEntry) o).getReference()) {

            // Return in priority CAS_REFERENCE_CODE entries.
            if ((getReference() != null)
                    && CAS_REFERENCE_CODE.equals(getReference().getCode())) {
                return -1;
            } else if ((((RegistryEntry) o).getReference() != null)
                    && CAS_REFERENCE_CODE.equals(
                        ((RegistryEntry) o).getReference().getCode())) {
                return 1;
            }
        }

        return getEntryNumber().compareTo(((RegistryEntry) o).getEntryNumber());
    }

    /***************************************************************************
     * @return  DOCUMENT ME!
     */
    public String getEntryNumber() {
        return entryNumber;
    }

    /***************************************************************************
     * Gets the comment in the language appropriate for the Locale.
     * @return  the locale-specific comment.
     */
    public String getComment() {

        if (isLanguageFrench()) {
            return getCommentF();
        }

        return getCommentE();
    }

    /***************************************************************************
     * Gets the comment in English.
     * @return  the comment in English.
     * @see     #setCommentE()
     */
    public String getCommentE() {
        return commentE;
    }

    /***************************************************************************
     * Gets the comment in French.
     * @return  the comment in French.
     * @see     #setCommentF()
     */
    public String getCommentF() {
        return commentF;
    }

    /***************************************************************************
     * Gets the name in the language appropriate for the Locale.
     * @return  the locale-specific name.
     */
    public String getName() {

        if (isLanguageFrench()) {
            return getNameF();
        }

        return getNameE();
    }

    /***************************************************************************
     * Gets the name in English.
     * @return  the name in English.
     * @see     #setNameE()
     */
    public String getNameE() {
        return nameE;
    }

    /***************************************************************************
     * Gets the name in French.
     * @return  the name in French.
     * @see     #setNameF()
     */
    public String getNameF() {
        return nameF;
    }

    /***************************************************************************
     * @return  DOCUMENT ME!
     */
    public Reference getReference() {
        return reference;
    }

    /***************************************************************************
     * Gets the English short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the English specific short description.
     */
    public String getShortDescriptionE() {
        return getEntryNumber();
    }

    /***************************************************************************
     * Gets the English short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the French specific short description.
     */
    public String getShortDescriptionF() {
        return getEntryNumber();
    }
    
    /***************************************************************************
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Over-ridden to add additional values to the
     * end.
     * @return  DOCUMENT ME!
     */
    public String getValuesAsString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(super.getValuesAsString());
        buffer.append(", entryNumber: ");
        buffer.append(getEntryNumber());
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

        buffer.append(", reference: ");
        buffer.append(getReference());

        return buffer.toString();
    }

    /***************************************************************************
     * @param  newVal
     */
    public void setEntryNumber(String newVal) {
        entryNumber = newVal;
    }

    /***************************************************************************
     * Sets the comment in English.
     * @param  newVal  the comment in English
     * @see    #getEmailAddress()
     */
    public void setCommentE(String newVal) {
        commentE = newVal;
    }

    /***************************************************************************
     * Gets the comment in French.
     * @param  newVal  the comment in French
     * @see    #getCommentF()
     */
    public void setCommentF(String newVal) {
        commentF = newVal;
    }

    /***************************************************************************
     * Sets the name in English.
     * @param  newVal  the name in English
     * @see    #getNameE()
     */
    public void setNameE(String newVal) {
        nameE = newVal;
    }

    /***************************************************************************
     * Sets the name in French.
     * @param  newVal  the name in French
     * @see    #getNameF()
     */
    public void setNameF(String newVal) {
        nameF = newVal;
    }

    /***************************************************************************
     * @param  newVal
     */
    public void setReference(Reference newVal) {
        reference = newVal;
    }
}
