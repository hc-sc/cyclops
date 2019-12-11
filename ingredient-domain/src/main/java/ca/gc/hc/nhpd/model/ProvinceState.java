package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;

/**
 * Class used to house Province and State details. 
 *
 */
public class ProvinceState extends PersistentObject 
    implements ControlledVocabularyObject, Comparable {
    
    private String code;
    private String nameE;
    private String nameF;
    private Country country;

    //JSTL needs this in JavaBeans syntax
    public boolean isHasControlledVocabularyDescription() {
        return false;
    }

    /**
     * Gets the code attribute value that identifies a Province or State.
     * @return Code value that identifies a Province or State.
     */
    public String getCode() {
        return code;
    }


    /**
     * Sets the code attribute of the ProvinceState object.
     * @param code - Code value that identifies a Province or State.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the country associated with this state / province.
     * @return this state or province's country.
     */
    public Country getCountry() {
        return country;
    }
    
    /**
     * Sets the country associated with this state / province.
     * @param country - Country associated with this Province or State.
     */
    public void setCountry(Country country) {
        this.country = country;
    }
    
    /**
     * Gets the English name attribute of the ProvinceState object.
     * @return the English name value.
     */
    public String getNameE() {
        return nameE;
    }

    /**
     * Set the English name attribute of the ProvinceState object. 
     * @param nameE - English name for a Province or State.
     */
    public void setNameE(String nameE) {
        this.nameE = nameE;
    }


    /**
     * Set the French name attribute of the ProvinceState object.
     * @return the French name value.
     */
    public String getNameF() {
        return nameF;
    }


    public void setNameF(String nameF) {
        this.nameF = nameF;
    }
    
    /**
     * Gets the name value in the language appropriate for the Locale.
     * 
     * @return the locale-specific name.
     */
    public String getName(){
        if (isLanguageFrench()) {
            return getNameF();
        }
        return getNameE();
    }
    
    /**
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
        if (o == null) return -1;
        return StringComparator.compare(getName(),((ProvinceState)o).getName());
    }

}
