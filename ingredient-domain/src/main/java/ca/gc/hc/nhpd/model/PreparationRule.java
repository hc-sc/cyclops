package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Set;

/******************************************************************************
 * The preparation rule object is responsible for controlling the ePLA form 
 * behavior.  This information was previously hard-coded in the form and will
 * now be moved into the ingredient database for reference by the form via
 * the monograph web services.  Based on ePLA user selections, this object
 * will control the following sections indicating their presence, and if their
 * content is required.
 * 
 * Note:  By moving this functionality from the hard-coded form to the 
 * web services for the ePLA form 4.0, we giving NHPD more flexibility in
 * controlling this functionality.  However, since so much of the form is 
 * dependent on this information, changes must be made with care.  
 * 
 * TODO:  Should we version or keep a history of this object?  Once loaded by 
 * the form, should it be persisted with a time/date stamp?
 * 
 * @author GEBRUNET
 */

public class PreparationRule extends PersistentObject
    implements Comparable {

    private static final String BOOLEAN_YES = "y";
    private static final String BOOLEAN_NO = "n";
    private static final String BOOLEAN_EMPTY = "e";
    
    private String code;
    private Boolean freshDryPresent;
    private Boolean quantityCrudeEquivalentPresent;
    private String ratio;
    private Set<Solvent> restrictedSolvents;
    private Boolean solventPresent;
    private Boolean standardizedPresent;

    /***************************************************************************
     * Gets the commonly used unique code for this object.
     * @return the commonly used unique code for this object.
     * @see #setCode()
     */
    public String getCode(){
        return code;
    }

    /***************************************************************************
     * Determins if the Fresh Dry section of the ePLA form will be displayed and
     * require user feedback.
     * @return display/require the Fresh Dry area on the ePLA form.
     * @see #setIsFreshDryRequired() 
     */
    public Boolean getFreshDryPresent() {
        return freshDryPresent;
    }
    
    /***************************************************************************
     * Determins if the Fresh Dry section of the ePLA form will be displayed and
     * require user feedback.  Hibernate Implementation.
     * @return display/require the Fresh Dry area on the ePLA form.
     * @see #setIsFreshDryRequiredAsString() 
     */
    public String getFreshDryPresentAsString() {
        return booleanToString(freshDryPresent);
    }
    
    /***************************************************************************
     * Determins if the Quantity Crude Equivalent section of the ePLA form will 
     * be displayed and require user feedback.
     * @return display/require the Quantity Crude Equivalent area on the ePLA 
     * form.
     * @see #setIsQuantityCrudeEquivalentPresent() 
     */
    public Boolean getQuantityCrudeEquivalentPresent() {
        return quantityCrudeEquivalentPresent;
    }
    
    /***************************************************************************
     * Determins if the Quantity Crude Equivalent section of the ePLA form will 
     * be displayed and require user feedback.  Hibernate implementation.
     * @return display/require the Quantity Crude Equivalent area on the ePLA 
     * form.
     * @see #setIsQuantityCrudeEquivalentPresentAsString() 
     */
    public String getQuantityCrudeEquivalentPresentAsString() {
        return booleanToString(quantityCrudeEquivalentPresent);
    }
    
    /***************************************************************************
     * Establishes the ratio that will be used by the ePLA form.  Possible
     * ratios include: 1:X, X:1 and 1:1.
     * @return ratio for the ePLA form.
     * @see setRatio()
     */
    public String getRatio() {
        return ratio;
    }
    
    /***************************************************************************
     * Returns a list of restricted solvents that should be used by the form.
     * When this field is empty, all solvents are permitted.
     * @return list of restricted solvents.
     * @see setRestrictedSolvents()
     */
    public Set<Solvent> getRestrictedSolvents() {
        return restrictedSolvents;
    }
     
    /***************************************************************************
     * Gets the English short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the English specific short description.
     */
    public String getShortDescriptionE() {
        return getCode();
    }

    /***************************************************************************
     * Gets the French short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the French specific short description.
     */
    public String getShortDescriptionF() {
        return getCode();
    }

    /***************************************************************************
     * Determins if the Solvent section of the ePLA form will be displayed and 
     * require user feedback.  When solvents are present, lookup the values in
     * the restricted solvent list to know if a limited list of solvents should
     * be used.  If the solvent list is empty, then all solvents should be 
     * available for the user selection.
     * @return display/require the solvent area on the ePLA 
     * form.
     * @see #setSolventPresent() 
     */
    public Boolean getSolventPresent() {
        return solventPresent;
    }

    /***************************************************************************
     * Determins if the Solvent section of the ePLA form will be displayed and 
     * require user feedback.  Hibernate implementation.
     * @return display/require the solvent area on the ePLA 
     * form.
     * @see #setSolventPresent() 
     */
    public String getSolventPresentAsString() {
        return booleanToString(solventPresent);
    }

    /***************************************************************************
     * Determins if the Standardized section of the ePLA form will be displayed 
     * and require user feedback.
     * @return display/require the standardized area on the ePLA 
     * form.
     * @see #setIsStandardizedPresent() 
     */
    public Boolean getStandardizedPresent() {
        return standardizedPresent;
    }

    /***************************************************************************
     * Determins if the Standardized section of the ePLA form will be displayed 
     * and require user feedback.  Hibernate Implementation.
     * @return display/require the standardized area on the ePLA 
     * form.
     * @see #setStandardizedPresent() 
     */
    public String getStandardizedPresentAsString() {
        return booleanToString(standardizedPresent);
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
     * Sets the fresh dry present flag for this object.
     * @param newVal the fresh dry value for this object.
     * @see #getFreshDryPresent()
     */
    public void setFreshDryPresent(Boolean newVal) {
        freshDryPresent = newVal;
    }
    
    /***************************************************************************
     * Sets the fresh dry present flag for this object.  Hibernate 
     * Implementation.
     * @param newVal the fresh dry value for this object. 
     * @see #getFreshDryPresent()
     */
    public void setFreshDryPresentAsString(String newVal) {
        freshDryPresent = stringToBoolean(newVal);
    }
    
    /***************************************************************************
     * Sets the quantity crude equivalent present flag for this object.
     * @param newVal the quantity crude equivalent present value for this object.
     * @see #getQuantityCrudeEquivalentPresent()
     */
    public void setQuantityCrudeEquivalentPresent(Boolean newVal) {
        quantityCrudeEquivalentPresent = newVal;
    }
    
    /***************************************************************************
     * Sets the quantity crude equivalent present flag for this object.
     * @param newVal the quantity crude equivalent present value for this object.
     * @see #getQuantityCrudeEquivalentPresent()
     */
    public void setQuantityCrudeEquivalentPresentAsString(String newVal) {
        quantityCrudeEquivalentPresent = stringToBoolean(newVal);
    }
    
    /***************************************************************************
     * Sets the ratio for this object.  Allowable values are X:1, 1:X and 1:1.
     * @param newVal the ratio for this object.
     * @see #getRatio()
     */
    public void setRatio(String newVal) {
        ratio = newVal;
    }
    
    /***************************************************************************
     * Sets the set of restricted solvents for this object.  
     * @param newVal the set of restricted solvents.
     * @see #getRestrictedSolvents
     */
    public void setRestrictedSolvents(Set<Solvent> newVal) {
        restrictedSolvents = newVal;
    }

    /***************************************************************************
     * Sets the solvent present flag for this object.
     * @param newVal the solvent present value for this object.
     * @see #getSolventPresent()
     */
    public void setSolventPresent(Boolean newVal) {
        solventPresent = newVal;
    }

    /***************************************************************************
     * Sets the solvent present flag for this object.  Hibernate Implementation.
     * @param newVal the solvent present value for this object.
     * @see #getSolventPresentAsString()
     */
    public void setSolventPresentAsString(String newVal) {
        solventPresent = stringToBoolean(newVal);
    }

    /***************************************************************************
     * Sets the standardized present flag for this object.
     * @param newVal the standardized present value for this object.
     * @see #getStandardizedPresent()
     */
    public void setStandardizedPresent(Boolean newVal) {
        standardizedPresent = newVal;
    }

    /***************************************************************************
     * Sets the standardized present flag for this object.  Hibernate 
     * Implementation.
     * @param newVal the standardized present value for this object.
     * @see #getStandardizedPresentAsString()
     */
    public void setStandardizedPresentAsString(String newVal) {
        standardizedPresent = stringToBoolean(newVal);
    }

    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
        
        if (o == null) return -1;
        return StringComparator.compare(getCode(),((PreparationRule)o).getCode());
         
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
        buffer.append(", freshDryPresent: ");
        buffer.append(getFreshDryPresent());
        buffer.append(", quantityCrudeEquivalentPresent: ");
        buffer.append(getQuantityCrudeEquivalentPresent());
        buffer.append(", ratio: ");
        buffer.append(getRatio());
        buffer.append(", restrictedSolvents: ");
        if (getRestrictedSolvents() != null) {
            buffer.append(getRestrictedSolvents());
        } else {
            buffer.append("null");
        }
        buffer.append(", solventPresent: ");
        buffer.append(getSolventPresent());
        buffer.append(", standardizedPresent: ");
        buffer.append(getStandardizedPresent());
        
        return buffer.toString();
    }   
    
    /**
     * This private method is used to convert the Boolean value to an 
     * appropriate string for Hibernate purposes.
     * 
     * <p>True = y, False = n, Empty = e.
     *  
     * @param value being evalueated
     * @return string equivalent value for Hibernate purposes.
     */
    private String booleanToString(Boolean value) {
        if (value == null) {
            return BOOLEAN_EMPTY;
        }
        if (value.equals(true)) {
            return BOOLEAN_YES;
        }
        if (value.equals(false)) {
            return BOOLEAN_NO;
        }
        return BOOLEAN_EMPTY;
    }
    
    /**
     * This private method is used to convert the String value to an 
     * appropriate Boolean for the object.
     * 
     * <p>True = y, False = n, Empty = e.
     *  
     * @param value being evalueated
     * 
     * @return Boolean equivalent value for the object to use.
     */
    private Boolean stringToBoolean(String value) {
        if (value == null) {
            return null;
        }
        if (value.toLowerCase().startsWith(BOOLEAN_YES)) {
            return true;
        }
        if (value.toLowerCase().startsWith(BOOLEAN_NO)) {
            return false;
        }
        return null;
    }
    
}
