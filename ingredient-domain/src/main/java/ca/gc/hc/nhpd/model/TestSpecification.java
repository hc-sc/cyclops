package ca.gc.hc.nhpd.model;

import java.util.Set;

/*******************************************************************************
 * An object that contains test information (e.g. tolerances). Used by Objects
 * that keep this type of information (typically in a Map keyed on TestCategory
 * or DosageFormGroup).
 */
public class TestSpecification extends PersistentObject {
    private Double lowerTolerance;
    private Set<TestMethod> toleranceTestMethods;
    private Units toleranceUnits;
    private Double upperTolerance;
    
    /***************************************************************************
     */
    public Double getLowerTolerance(){
        return lowerTolerance;
    }

    /***************************************************************************
     */
    public Set<TestMethod> getToleranceTestMethods(){
        return toleranceTestMethods;
    }

    /***************************************************************************
     */
    public Units getToleranceUnits(){
        return toleranceUnits;
    }

    /***************************************************************************
     */
    public Double getUpperTolerance(){
        return upperTolerance;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setLowerTolerance(Double newVal){
        lowerTolerance = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setToleranceTestMethods(Set<TestMethod> newVal){
        toleranceTestMethods = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setToleranceUnits(Units newVal){
        toleranceUnits = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setUpperTolerance(Double newVal){
        upperTolerance = newVal;
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
        buffer.append(", lowerTolerance: ");
        buffer.append(getLowerTolerance());
        buffer.append(", upperTolerance: ");
        buffer.append(getUpperTolerance());
        buffer.append(", toleranceTestMethods: ");
        buffer.append(getToleranceTestMethods());
        buffer.append(", toleranceUnits: ");
        buffer.append(getToleranceUnits());
        
        return buffer.toString();
    }
}
