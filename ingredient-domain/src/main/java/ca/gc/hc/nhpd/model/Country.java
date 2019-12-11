package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Comparator;
import java.util.Set;

/*******************************************************************************
 * An object that represents a Country.
 * @version 1.0
 */
public class Country extends PersistentObject 
			         implements ControlledVocabularyObject, Comparable {
    private Risk bseRisk;
    private String code;
    private String nameE;
    private String nameF;
    private Set<ProvinceState> provincesStates;

    //JSTL needs this in JavaBeans syntax
	public boolean isHasControlledVocabularyDescription() {
		return false;
	}

    /***************************************************************************
     * Gets the type/amount of risk that cattle may have Bovine Spongiform
     * Encephalopathy (mad cow disease).
     */
    public Risk getBseRisk(){
        return bseRisk;
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
     * Gets the name of the Country in the language appropriate for the Locale.
     * @return the locale-specific name of the country.
     */
    public String getName(){
        if (isLanguageFrench()) {
            return getNameF();
        }
        return getNameE();
    }

    /***************************************************************************
     * Gets the English name of the Country.
     * @return the English name of the country.
     */
    public String getNameE(){
        return nameE;
    }

    /***************************************************************************
     * Gets the French name of the Country.
     * @return the French name of the country.
     */
    public String getNameF(){
        return nameF;
    }
    
    /***************************************************************************
     * Gets the Provinvces / States associated with this country.
     * @return the Provinces / States associated with this country.
     */
    public Set<ProvinceState> getProvincesStates() {
        return provincesStates;
    }
    
    
	/***************************************************************************
	 * Gets the short description used to represent this object in drop down
	 * lists and other various interfaces. 
	 * @return the locale-specific short description.
	 */
	public String getShortDescription(){
		if (isLanguageFrench()) {
			return getShortDescriptionF();
		}
		return getShortDescriptionE();
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
     * Sets the type/amount of risk that cattle may have Bovine Spongiform
     * Encephalopathy (mad cow disease).
     * @param newVal    newVal
     */
    public void setBseRisk(Risk newVal){
        bseRisk = newVal;
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
     * Sets the English country name.
     * @param newVal    the English country name.
     */
    public void setNameE(String newVal){
        nameE = newVal;
    }

    /***************************************************************************
     * Sets the French country name.
     * @param newVal    the French country name.
     */
    public void setNameF(String newVal){
        nameF = newVal;
    }

    /***************************************************************************
     * Sets the list of Provinces States associated with this country.
     * @param newVal the Provinces States associated with this country.
     */
    public void setProvincesStates(Set<ProvinceState> newVal) {
        provincesStates = newVal;
    }
    
    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
        /*
    	if (getName() == null) {
            return 1;
        }
        if (o == null) {
            return -1;
        }
        return getName().compareTo(((Country)o).getName());
    	*/
    	
    	if (o == null) return -1;
    	return StringComparator.compare(getName(),((Country)o).getName());

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
        buffer.append(", bseRisk: ");
        if (getBseRisk() != null && getBseRisk().getName() != null) {
            buffer.append(getBseRisk().getName());
        } else {
            buffer.append("null");
        }
        buffer.append(", nameE: ");
        buffer.append(getNameE());
        buffer.append(", nameF: ");
        buffer.append(getNameF());
        
        return buffer.toString();
    }
    
    //+++ Inner Classes ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /***************************************************************************
     * An implementation of Comparator that allows these objects to be sorted
     * using their French names.
     */
    public static class FrenchComparator implements Comparator<Country> {
        /***********************************************************************
         * Compares the passed objects for order. Returns a negative integer,
         * zero, or a positive integer as the first object is less than, equal
         * to, or greater than the second.
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         */
        public int compare(Country o1, Country o2) throws ClassCastException {
            if (o1 == null || (o1).getNameF() == null) {
                return 1;
            }
            if (o2 == null) {
                return -1;
            }
            return (o1).getNameF().compareTo(
                   (o2).getNameF());
        }
    }
}