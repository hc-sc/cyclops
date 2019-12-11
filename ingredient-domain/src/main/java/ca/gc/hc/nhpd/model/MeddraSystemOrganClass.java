package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Iterator;
import java.util.Set;

/*******************************************************************************
 * This object represents a system organ class (SOC) from the Medical Dictionary
 * for Regulatory Activities (MedDRA) system. It is the highest level of the
 * hierarchy that provides the broadest concept for data retrieval. These comprise
 * groupings by etiology, manifestation site, and purpose.
 * @version 1.0
 */
public class MeddraSystemOrganClass extends PersistentObject 
                                    implements Comparable {
	private String code;
	private String nameE;
	private String nameF;
	private Set<MeddraHighLevelGroupTerm> highLevelGroupTerms;

    /***************************************************************************
	 * Gets the commonly used unique code for this object.
	 * @return the commonly used unique code for this object.
	 * @see #setCode()
	 */
	public String getCode(){
		return code;
	}

    /***************************************************************************
     */
	public Set<MeddraHighLevelGroupTerm> getHighLevelGroupTerms(){
		return highLevelGroupTerms;
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
	 * Sets the commonly used unique code for this object.
	 * @param newVal the commonly used unique code for this object.
	 * @see #getCode()
	 * @param newVal
	 */
	public void setCode(String newVal){
		code = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setHighLevelGroupTerms(Set<MeddraHighLevelGroupTerm> newVal){
		highLevelGroupTerms = newVal;
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
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
        
        if (o == null) return -1; 
    	return StringComparator.compare(getName(),((MeddraSystemOrganClass)o).getName());
    }

    /***************************************************************************
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Over-ridden to add additional values to the
     * end.
     */
    public String getValuesAsString(){
        StringBuffer buffer = new StringBuffer();
        boolean first;
        MeddraHighLevelGroupTerm highLevelGroupTerm;
        
        buffer.append(super.getValuesAsString());
        buffer.append(", code: ");
        buffer.append(getCode());
        buffer.append(", nameE: ");
        buffer.append(getNameE());
        buffer.append(", nameF: ");
        buffer.append(getNameF());
        buffer.append(", highLevelGroupTerms: ");
        if (getHighLevelGroupTerms() != null 
            && !getHighLevelGroupTerms().isEmpty()) {
            first = true;
            buffer.append("[");
            for(Iterator it=getHighLevelGroupTerms().iterator(); it.hasNext();) {
                if (first) {
                    first = false;
                } else {
                    buffer.append(", ");
                }
                highLevelGroupTerm = (MeddraHighLevelGroupTerm)it.next();
                buffer.append(highLevelGroupTerm.getName());
                buffer.append("(" + highLevelGroupTerm.getId() + ")");
            }
            buffer.append("]");
        } else {
            buffer.append("none");
        }
        
        return buffer.toString();
    }
}