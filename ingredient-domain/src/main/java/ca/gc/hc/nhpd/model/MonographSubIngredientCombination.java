package ca.gc.hc.nhpd.model;

import java.text.DecimalFormat;

/*******************************************************************************
 * The MonographSubingredientCombination defines a combination of subIngredients
 * that are applicable to a given MonographSubingredientDose. Most of these will
 * only contain a single subIngredient, however occasionally a combination
 * (including the ratios of the two) will be applicable. <br/>
 * This has been separated out into a distinct object (rather than being part of
 * MonographSubingredientDose) since there is a high probability of future
 * refactoring to support combinations of more than two subIngredients. <br/>
 * 
 * The sub-ingredient relationship to the main ingredient must already be defined
 * in the ingredients model for this object to be valid.
 */
public class MonographSubIngredientCombination extends PersistentObject
                                               implements Comparable {
    private String code;
	private Float maximumRatio;
    private Float minimumRatio;
    private SubIngredient primarySubIngredient;
    private SubIngredient secondarySubIngredient;
    
    private static DecimalFormat twoPlaces = new DecimalFormat("0.####");
    
    /**
     * Gets the maximum ratio (if there is one) of the secondary subIngredient
     * to the primary subIngredient. Expressed as a number (to one). For
     * example, "2.5" is equivalent to "2.5:1".
     * @return the maximum ratio (if there is one) of the secondary
     *         subIngredient to the primary subIngredient
     * @see #setMaximumRatio()
     */
    public Float getMaximumRatio() {
        return maximumRatio;
    }

    /**
     * Sets the maximum ratio (if there is one) of the secondary subIngredient
     * to the primary subIngredient. Expressed as a number (to one). For
     * example, "2.5" is equivalent to "2.5:1".
     * @param newVal the maximum ratio (if there is one) of the secondary
     *        subIngredient to the primary subIngredient
     * @see #getMaximumRatio()
     */
    public void setMaximumRatio(Float newVal) {
        maximumRatio = newVal;
    }

    /**
     * Gets the minimum ratio (if there is one) of the secondary subIngredient
     * to the primary subIngredient. Expressed as a number (to one). For
     * example, "2.5" is equivalent to "2.5:1".
     * @return the minimum ratio (if there is one) of the secondary
     *         subIngredient to the primary subIngredient
     * @see #setMinimumRatio()
     */
    public Float getMinimumRatio() {
        return minimumRatio;
    }

    /**
     * Sets the minimum ratio (if there is one) of the secondary subIngredient
     * to the primary subIngredient. Expressed as a number (to one). For
     * example, "2.5" is equivalent to "2.5:1".
     * @param newVal the minimum ratio (if there is one) of the secondary
     *        subIngredient to the primary subIngredient
     * @see #getMinimumRatio()
     */
    public void setMinimumRatio(Float newVal) {
        minimumRatio = newVal;
    }

    /**
     * Gets the primarySubIngredient, which must be populated.
     * @return the base subIngredient of the combination
     * @see #setPrimarySubIngredient()
     */
    public SubIngredient getPrimarySubIngredient() {
        return primarySubIngredient;
    }

    /**
     * Gets the primarySubIngredient, which must be populated.
     * @param newVal the base subIngredient of the combination
     * @see #getPrimarySubIngredient()
     */
    public void setPrimarySubIngredient(SubIngredient newVal) {
        primarySubIngredient = newVal;
    }

    /**
     * Gets the secondarySubIngredient, which is only populated if there is a
     * second subIngredient applicable.
     * @return the second subIngredient of the combination, if there is one.
     * @see #setSecondarySubIngredient()
     */
    public SubIngredient getSecondarySubIngredient() {
        return secondarySubIngredient;
    }

    /**
     * Sets the secondarySubIngredient, which is only populated if there is a
     * second subIngredient applicable.
     * @param newVal the second subIngredient of the combination, if there is
     *        one.
     * @see #getSecondarySubIngredient()
     */
    public void setSecondarySubIngredient(SubIngredient newVal) {
        secondarySubIngredient = newVal;
    }

    
    /**
     * Builds a display string providing details of the ratio. 
     */
    
   /* 
    * Formats the ratios for display purposes
    */
    public String getRatiosForDisplay() {
        
    	StringBuffer buffer = new StringBuffer();
    	
    	if (minimumRatio == null && maximumRatio == null) 
    		return null;
    	
    	if (isLanguageFrench()) 
			buffer.append(" entre ");
		else
			buffer.append(" ratio between ");	
    	buffer.append(twoPlaces.format(minimumRatio));
		buffer.append(":1");
		if (isLanguageFrench()) 
			buffer.append(" et ");
		else
			buffer.append(" and ");	
		buffer.append(twoPlaces.format(maximumRatio));
		buffer.append(":1");
		
		return buffer.toString();
    }

    
    public boolean isHasRatios() {    
    	return (minimumRatio != null || maximumRatio != null); 
    }
    
    /**
     * Compares this object with the specified object for equality and for
     * order. Returns a negative integer, zero, or a positive integer as this
     * object is less than, equal to, or greater than the specified object.
     * @param   o  the object to compare this to.
     * @return  a comparator flag qualifying equalify and order
     * @throws  ClassCastException  when the object provided is of the wrong
     *                              type
     */
    public int compareTo(Object o) throws ClassCastException {
        if (o == null) {
            return -1;
        }

        MonographSubIngredientCombination combination = 
            (MonographSubIngredientCombination)o;

        
        if (getPrimarySubIngredient() == null 
            || combination.getPrimarySubIngredient() == null) {
                return -1; //Should never happen
        }

        if (getPrimarySubIngredient().equals(combination.getPrimarySubIngredient())) {
            if (getSecondarySubIngredient() == null) {
                if (combination.getSecondarySubIngredient() == null) {
                    return 0;
                }
                return 1;
                
            } else {
                if (combination.getSecondarySubIngredient() == null) {
                    return -1;
                }
                
                if (getSecondarySubIngredient().equals(combination.getSecondarySubIngredient())) {
                    if (getMaximumRatio().equals(combination.getMaximumRatio())
                        && getMinimumRatio().equals(combination.getMinimumRatio())) {
                        return 0;
                    }
                    return 1;
                }
                return getSecondarySubIngredient().compareTo(combination.getSecondarySubIngredient());
            }
        }

        return getPrimarySubIngredient().compareTo(combination.getPrimarySubIngredient());
    }

    /**
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Over-ridden to add additional values to the
     * end.
     *
     * @return  this object as a string value
     */
    @Override
    public String getValuesAsString() {
        StringBuilder buffer = new StringBuilder();

        buffer.append(super.getValuesAsString());
        buffer.append(", primarySubIngredient: ");
        if (getPrimarySubIngredient() != null){
            buffer.append(getPrimarySubIngredient().getName());
        } else {
            buffer.append("null");
        }
        if (getSecondarySubIngredient() != null){
            buffer.append(", maximumRatio: ");
            buffer.append(getMaximumRatio());
            buffer.append(", minimumRatio: ");
            buffer.append(getMinimumRatio());
            buffer.append(", secondarySubIngredient: ");
            buffer.append(getSecondarySubIngredient().getName());
        }

        return buffer.toString();
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
