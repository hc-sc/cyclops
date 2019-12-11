package ca.gc.hc.nhpd.model;

/**
 * An object that represents a homeopathic method of preparation.  Homeopathic formulas
 * sometimes have one or more ways in which they can be prepared.  The homeopathic
 * method of preparation attempts to outline one of these methods.  One or more can be
 * associated with a specific homeopatic formula.
 */
public class HomeopathicMethodOfPreparation extends PersistentObject implements Comparable {

	private static final String SPACE = " ";
	private static final String DASH = "-";

    private HomeopathicFormula         homeopathicFormula;
    private HomeopathicPreparationType homeopathicPreparationType;
    private Integer                    minimum;
    private Integer                    maximum;

    /**
     * Getter for the homeopathic preparation type.
     *  
     * @return the homeopathic preparation type.
     */
    public HomeopathicPreparationType getHomeopathicPreparationType() {
        return homeopathicPreparationType;
    }

    /**
     * The maximum number of "dilutions" involved with this preparation
     * type.  Note that the dilution is qualified in the homeopathic 
     * formula object. 
     *  
     * @return maximum number of "dilutions"
     */
    public Integer getMaximum() {
        return maximum;
    }

    /**
     * The minimum number of "dilutions" involved with this preparation
     * type.  Note that the dilution is qualified in the homeopathic 
     * formula object. 
     *  
     * @return minimum number of "dilutions"
     */    
    public Integer getMinimum() {
        return minimum;
    }

    /**
     * Getter for the homeopathic formula parent object.
     *  
     * @return the parent homeopathic formula object.
     */
    public HomeopathicFormula getHomeopathicFormula() {
        return homeopathicFormula;
    }

    /**
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Over-ridden to add additional values to the
     * end.
     */
    public String getValuesAsString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(super.getValuesAsString());
        buffer.append(", minimum: ");
        buffer.append(getMinimum());
        buffer.append(", maximum: ");
        buffer.append(getMaximum());
        buffer.append(", homeopathicPreparationType: ");
        buffer.append(getHomeopathicPreparationType());

        return buffer.toString();
    }

    /**
     * Setter for the homeopathic formula.
     *  
     * @param homeopathicFormula
     */
    public void setHomeopathicFormula(
            HomeopathicFormula homeopathicFormula) {
        this.homeopathicFormula = homeopathicFormula;
    }

    /**
     * Setter for the homeopathic preparation type.
     *  
     * @param homeopathicPreparationType
     */
    public void setHomeopathicPreparationType(
            HomeopathicPreparationType homeopathicPreparationType) {
        this.homeopathicPreparationType = homeopathicPreparationType;
    }

    /**
     * Setter for the maximum number of dilutions.
     *  
     * @param maximum
     */
    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    /**
     * Setter for the minimum number of dilutions.
     * 
     * @param minimum
     */public void setMinimum(Integer minimum) {
        this.minimum = minimum;
    }
    
    /**
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param  o  the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {

        if (o == null) {
            return -1;
        }

        if (getHomeopathicPreparationType() == null) {
            return 1;
        }

        return getHomeopathicPreparationType().compareTo(
                ((HomeopathicMethodOfPreparation) o)
                        .getHomeopathicPreparationType());
    }

    /**
     * Produces the Quantity as a string value.  The quantity is built using
     * the minimum and maximum values with the homeopathic dilution code
     * appended after it.
     *  
     * @return a string representing the quantity.
     */
    public String getQuantityAsString() {
		
		StringBuilder sb = new StringBuilder();
		if (getMinimum() != null
				&& getMinimum() != 0) {
			sb.append(getMinimum());
			if (getMaximum() != null) {
				sb.append(DASH + getMaximum());
			}
		}
		if (homeopathicFormula.getHomeopathicDilution() != null
				&& homeopathicFormula.getHomeopathicDilution().getCode() != null) {
			sb.append(homeopathicFormula.getHomeopathicDilution().getCode());
		}
		
		return sb.toString();
	}
	
    
}
