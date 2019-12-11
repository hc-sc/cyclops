package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
/*******************************************************************************
 * This object represents a lowest level term (LLT) from the Medical Dictionary
 * for Regulatory Activities (MedDRA) system. It is the lowest level of the
 * terminology. They are synonyms, lexical variants, quasi-synonyms, sub-elements,
 * or identical to their "parent" preferred term.
 * @version 1.0
 */
public class MeddraLowestLevelTerm extends PersistentObject
                                   implements Comparable {
	private String code;
	private boolean current;
	private String nameE;
	private String nameF;
	private MeddraPreferredTerm preferredTerm;

    /***************************************************************************
	 * Gets the commonly used unique code for this object.
	 * @return the commonly used unique code for this object.
	 * @see #setCode()
	 */
	public String getCode(){
		return code;
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
	public MeddraPreferredTerm getPreferredTerm(){
		return preferredTerm;
	}

    /***************************************************************************
	 * False if this term is very vague, ambiguous, truncated, abbreviated,
	 * out-dated, or mis-spelled.
	 */
	public boolean isCurrent(){
		return current;
	}

    /***************************************************************************
	 * Sets the commonly used unique code for this object.
	 * @param newVal the commonly used unique code for this object.
	 * @see #getCode()
	 * @param newVal
	 */
	public void setCode(String newVal){
		code = newVal;
	}

    /***************************************************************************
	 * False if this term is very vague, ambiguous, truncated, abbreviated,
	 * out-dated, or mis-spelled.
	 * @param newVal
	 */
	public void setCurrent(boolean newVal){
		current = newVal;
	}

    /***************************************************************************
	 * Gets the name in English.
	 * @return the name in English.
	 * @see #setNameE()
	 * @param newVal
	 */
	public void setNameE(String newVal){
		nameE = newVal;
	}

    /***************************************************************************
	 * Sets the name in French.
	 * @param newVal the name in French
	 * @see #getNameF()
	 * @param newVal
	 */
	public void setNameF(String newVal){
		nameF = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setPreferredTerm(MeddraPreferredTerm newVal){
		preferredTerm = newVal;
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
    			((MeddraLowestLevelTerm)o).getName());
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
        buffer.append(", preferredTerm: ");
        if (getPreferredTerm() != null) {
            buffer.append(getPreferredTerm().getName());
            buffer.append("(" + getPreferredTerm().getId() + ")");
        } else {
            buffer.append("null");
        }
        
        return buffer.toString();
    }
}