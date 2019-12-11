package ca.gc.hc.nhpd.model;

import java.util.Date;

/*******************************************************************************
 */
public class Rationale extends PersistentObject implements Comparable {
	private String notesE;
	private String notesF;
	private Date date;
	private Reference reference;

	/***************************************************************************
	 * Gets the notes in the language appropriate for the Locale.
	 * @return the locale-specific name.
	 */
	public String getNotes(){
        if (isLanguageFrench()) {
            return getNotesF();
        }
        return getNotesE();
	}

	/***************************************************************************
	 * Gets the notes in English.
	 * @return the notes in English.
	 * @see #setNotesE()
	 */
	public String getNotesE(){
		return notesE;
	}

	/***************************************************************************
	 * Gets the notes in French.
	 * @return the notes in French.
	 * @see #setNotesE()
	 */
	public String getNotesF(){
		return notesF;
	}

	/***************************************************************************
	 * Gets the date of this rationale.
	 * @return the date for this rationale.
	 * @see #setDate()
	 */
	public Date getDate(){
		return date;
	}

	/***************************************************************************
	 * Gets the reference for rationale.
	 * @return the reference for this rationale.
	 * @see #setReference()
	 */
	public Reference getReference(){
		return reference;
	}
	
	/***************************************************************************
	 * Sets the notes in English. 
	 * @param newVal
	 * @see #getNotesE()
	 */
	public void setNotesE(String newVal){
		notesE = newVal;
	}

	/***************************************************************************
	 * Sets the notes in Fnglish. 
	 * @param newVal
	 * @see #getNotesF()
	 */
	public void setNotesF(String newVal){
		notesF = newVal;
	}

	/***************************************************************************
	 * Sets the date of this rationale.
	 * @param newVal
	 * @see #getDate()
	 */
	public void setDate(Date newVal){
		date = newVal;
	}

	/***************************************************************************
	 * Sets the reference for this rationale.
	 * @param newVal
	 * @see #setReference()
	 */
	public void setReference(Reference newVal){
		reference = newVal;
	}
	
	/***************************************************************************
	 * Compares this object with the specified object for order. Returns a
	 * negative integer, zero, or a positive integer as this object is less than,
	 * equal to, or greater than the specified object.
	 * @param o the object to compare this to.
	 */
	public int compareTo(Object o) throws ClassCastException {
		if (getId() == null) {
			return 1;
		}
		if (o == null) {
			return -1;
		}
		//Case Insensitive sort
		return getId().compareTo(((Rationale)o).getId());
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
        buffer.append(", notesE: ");
        buffer.append(getNotesE());
        buffer.append(", notesF: ");
        buffer.append(getNotesF());
        buffer.append(", date: ");
        buffer.append(getDate());
        buffer.append(", reference: ");
        if (getReference() != null) {
            buffer.append(getReference().getCode());
        } else {
            buffer.append("null");
        }
        
        return buffer.toString();
    }
 }
