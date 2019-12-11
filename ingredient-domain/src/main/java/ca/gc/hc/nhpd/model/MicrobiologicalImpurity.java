package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
/*******************************************************************************
 * An object that represents the full definition of a microbiological impurity,
 * which includes its tolerance level. Note that the tolerance level is kept
 * separate from the microbiological substance since it may vary depending on
 * context. As a result, the microbiological substance is kept as a separate
 * BasicImpuritySubstance which is reused by numerous MicrobiologicalImpurities.
 */
public class MicrobiologicalImpurity extends PersistentObject
                                     implements Comparable {
	private BasicImpuritySubstance substance;
	private Double toleranceLevel;
	private Units toleranceUnits;

    /***************************************************************************
     */
	public BasicImpuritySubstance getSubstance(){
		return substance;
	}

    /***************************************************************************
     */
	public Double getToleranceLevel(){
		return toleranceLevel;
	}

    /***************************************************************************
     */
	public Units getToleranceUnits(){
		return toleranceUnits;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setSubstance(BasicImpuritySubstance newVal){
		substance = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setToleranceLevel(Double newVal){
		toleranceLevel = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setToleranceUnits(Units newVal){
		toleranceUnits = newVal;
	}

    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
       
        if (o == null) return -1; 
    	return StringComparator.compare(getSubstance().getAuthorizedName(),
    			((MicrobiologicalImpurity)o).getSubstance().getAuthorizedName());
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
        buffer.append(", substance: ");
        buffer.append(getSubstance());
        buffer.append(", toleranceLevel: ");
        buffer.append(getToleranceLevel());
        buffer.append(", toleranceUnits: ");
        buffer.append(getToleranceUnits());
        
        return buffer.toString();
    }

    
	/***************************************************************************
	 * Returns a short description of this object.  
	 * Includes the simple class name and the object's id.
	 * Default value, to be overwritten by the child classes. 
	 */
	public String getShortDescriptionE() {
		return getSubstance().getAuthorizedName() + "-" + getToleranceLevel() + "-" + getToleranceUnits();
	}
	
	/***************************************************************************
	 * Returns a short description of this object.  
	 * Includes the simple class name and the object's id.
	 * Default value, to be overwritten by the child classes. 
	 */
	public String getShortDescriptionF() {
		return getSubstance().getAuthorizedName() + "-" + getToleranceLevel() + "-" + getToleranceUnits();
	}    
}