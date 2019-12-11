package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Set;

/*******************************************************************************
 * An object that represents an entry in the International Conference of
 * Harmonization.
 */
public class IchClass extends PersistentObject implements Comparable {
    private Set<TestMethod> allowableTestMethods;
    private String code;
    private String descriptionE;
    private String descriptionF;
    private Double impurityResidualLimit;
    private Units impurityResidualUnits;
    private String nameE;
    private String nameF;

    /***************************************************************************
     */
    public Set<TestMethod> getAllowableTestMethods(){
        return allowableTestMethods;
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
     * Gets the description in the language appropriate for the Locale.
     * @return the locale-specific description.
     */
    public String getDescription(){
        if (isLanguageFrench()) {
            return getDescriptionF();
        }
        return getDescriptionE();
    }

    /***************************************************************************
     * Gets the description in English.
     * @return the description in English.
     * @see #setDescriptionE()
     */
    public String getDescriptionE(){
        return descriptionE;
    }

    /***************************************************************************
     * Gets the description in French.
     * @return the description in French.
     * @see #setDescriptionF()
     */
    public String getDescriptionF(){
        return descriptionF;
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
     */
    public Double getImpurityResidualLimit(){
        return impurityResidualLimit;
    }
    
    /***************************************************************************
     */
    public Units getImpurityResidualUnits(){
        return impurityResidualUnits;
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
     * @see #setNameF
     */
    public String getNameF(){
        return nameF;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setAllowableTestMethods(Set newVal){
        allowableTestMethods = newVal;
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
     * @param newVal
     */
    public void setDescriptionE(String newVal){
        descriptionE = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setDescriptionF(String newVal){
        descriptionF = newVal;
    }

    /***************************************************************************
     */
    public void setImpurityResidualLimit(Double aDouble){
        impurityResidualLimit = aDouble;
    }
    
    /***************************************************************************
     */
    public void setImpurityResidualUnits(Units newVal){
        impurityResidualUnits = newVal;
    }
    
    /***************************************************************************
     * Sets the name in English.
     * @param newVal the name in English
     * @see #getNameE()
     */
    public void setNameE(String newVal){
        nameE = newVal;
    }

    /***************************************************************************
     * Sets the name in French.
     * @param newVal the name in French
     * @see #getNameF()
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
    	return StringComparator.compare(getName(),((IchClass)o).getName());
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
        if (getDescriptionE() != null) {
            buffer.append(", descriptionE: ");
            buffer.append(getDescriptionE());
        }
        if (getDescriptionF() != null) {
            buffer.append(", descriptionF: ");
            buffer.append(getDescriptionF());
        }
        buffer.append(", impurityResidualLimit: ");
        buffer.append(getImpurityResidualLimit());
        buffer.append(", impurityResidualUnits: ");
        buffer.append(getImpurityResidualUnits());
        buffer.append(", allowableTestMethods: ");
        buffer.append(getAllowableTestMethods());
        
        return buffer.toString();
    }
}