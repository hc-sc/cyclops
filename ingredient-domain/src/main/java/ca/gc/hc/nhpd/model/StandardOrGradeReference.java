package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import ca.gc.hc.nhpd.util.StringUtils;

/*******************************************************************************
 * An object that represents an authoritative source on how Ingredients are
 * prepared.  This object is used essentially as a reference wrapper object.
 */
public class StandardOrGradeReference extends PersistentObject 
	implements Comparable {

    private Reference reference;
    private Boolean homeopathicStandardOrGrade;
    private Double minimumDilutionAmount;

    /***************************************************************************
     * Gets the boolean flag identifying this object as a Homeopathic Standard
     * Or Grade Reference.
     * @return boolean flag identifying this object as a Homeopathic Standard or 
     *         Grade Reference.
     * @see #setHomeopathicStandardOrGrade()
     */
    public Boolean isHomeopathicStandardOrGrade(){
        return homeopathicStandardOrGrade;
    }

    /***************************************************************************
     * Gets the String identifying this object as a Homeopathic Standard
     * Or Grade Reference.  Used by the Hibernate implementation.
     * @return Flag identifying this object as a Homeopathic Standard or 
     *         Grade Reference.
     * @see #setHomeopathicStandardOrGradeAsString()
     */
    public String getHomeopathicStandardOrGradeAsString(){
        return StringUtils.booleanToString(homeopathicStandardOrGrade);
    }

    /***************************************************************************
     * Gets the minimum amount of dilution that must be used based on a
     * dilution type of "X". A value of 0 indicates that this is not
     * applicable (some pharmacopoeias don't use standard dilution types).
     * 
     * @see #setMinimumDilutionAmount()
     */
    public Double getMinimumDilutionAmount(){
        return minimumDilutionAmount;
    }
    
    
    /***************************************************************************
     * Gets the reference associated with this Standard Or Grade object.
     * @return this object's reference.
     * @see #setReference()
     */
    public Reference getReference(){
        return reference;
    }

	/***************************************************************************
	 * Gets the English short description used to represent this object in 
	 * drop down lists and other various interfaces. 
	 * @return the English specific short description.
	 */
	public String getShortDescriptionE() {
        if (reference == null) {
            return null;
        }
		return reference.getShortDescriptionE();
	}

	/***************************************************************************
	 * Gets the French short description used to represent this object in 
	 * drop down lists and other various interfaces. 
	 * @return the French specific short description.
	 */
	public String getShortDescriptionF() {
        if (reference == null) {
            return null;
        }
		return reference.getShortDescriptionF();
	}

    /***************************************************************************
     * Sets the boolean flag indicating that this object is a Homeopathic 
     * Standard or Grade reference object.
     * @param Boolean flag indicting this object as Homeopathic Standard or 
     *                Grade reference
     * @see #isHomeopathicStandardOrGrade()
     */
    public void setHomeopathicStandardOrGrade(Boolean homeopathicStandardOrGrade) {
        this.homeopathicStandardOrGrade = homeopathicStandardOrGrade;
    }
    
    /***************************************************************************
     * Sets the String flag indicating that this object is a Homeopathic 
     * Standard or Grade reference object.
     * @param String flag indicting this object as Homeopathic Standard or 
     *                Grade reference
     * @see #getHomeopathicStandardOrGradeAsString()
     */
    public void setHomeopathicStandardOrGradeAsString(String homeopathicStandardOrGrade) {
        this.homeopathicStandardOrGrade = StringUtils.stringToBoolean(homeopathicStandardOrGrade);
    }

    /***************************************************************************
     * Gets the minimum amount of dilution that must be used based on a
     * dilution type of "X". A value of 0 indicates that this is not
     * applicable (some pharmacopoeias don't use standard dilution types).
     * 
     * @see #getMinimumDilutionAmount()
     */
    public void setMinimumDilutionAmount(Double minimumDilutionAmount) {
        this.minimumDilutionAmount = minimumDilutionAmount;
    }
    
    /***************************************************************************
     * Sets the reference used by this object.
     * @param reference the reference given to this object.
     * @see #getReference()
     */
    public void setReference(Reference reference){
        this.reference = reference;
    }

    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
        
        /* Note, since References can only be identified once as Standard Or Grade
         * References, and since Normal and Homeopathic Standard Or Grade 
         * References are mutually exclusive, I will compare on the references.
         */
        
        if (o == null 
                || ((StandardOrGradeReference)o).getReference() == null) {
            return -1;
        }
        if (getReference() == null) {
            return 1;
        }
    	return getReference().compareTo(((StandardOrGradeReference)o).getReference());
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
        buffer.append(", reference: ");
        buffer.append(getReference());
        buffer.append(", isHomeopathicStandardOrGrade: ");
        buffer.append(isHomeopathicStandardOrGrade());
        
        return buffer.toString();
    }
}