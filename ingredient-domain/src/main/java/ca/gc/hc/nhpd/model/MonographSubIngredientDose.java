package ca.gc.hc.nhpd.model;

import org.hibernate.Query;
import org.hibernate.Session;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/*******************************************************************************
 * The MonographSubingredientDose defines the quanitity and frequency of a
 * subIngredient when specifically defined in the Monograph. Normally dose is 
 * only defined by a MonographDose at the ingredient level, but on some occasions
 * this level of detail is also specified. <br/>
 *
 * The sub-ingredient relationship to the main ingredient must already be defined
 * in the ingredients model for this object to be valid. <br/>
 *
 * Also, the maximum and minimum doses should fall within the range defined for
 * the sub-ingredient.
 */
public class MonographSubIngredientDose extends PersistentObject 
										implements Comparable {
    private static final String CHAR_DASH="-";
    private static final String COMMA_SPACE=", ";
    private static final String CHAR_SPACE=" ";
    private static final String FREQUENCY_QUALIFIER="x/";
     
    //~ Instance fields --------------------------------------------------------
    //used primarily for loading
    private String code;    
        
    //parent MonographDoses.  Only used in loading    
    //private Set<MonographDose> monographDoses;
   
    private Float doseMaximum;
    private Float doseMinimum;
    private Units doseUnits;
    private int frequencyMinimum;
    private int frequencyMaximum;
    private Units frequencyUnits;
	private RestrictionType restrictionType;
    private MonographSubIngredientCombination subIngredientCombination;

    //~ public -----------------------------------------------------------------

    /**
     * Compares this object with the specified object for equality and for
     * order. Returns a negative integer, zero, or a positive integer as this
     * object is less than, equal to, or greater than the specified object.
     *
     * @param   o  the object to compare this to.
     *
     * @return  a comparator flag qualifying equalify and order
     *
     * @throws  ClassCastException  when the object provided is of the wrong
     *                              type
     */
    public int compareTo(Object o) throws ClassCastException {

		try {

        if (o == null) {
            return -1;
        }

        // First compare on the SubIngredient (if different)
        if (getSubIngredientCombination() != null && 
          ((MonographSubIngredientDose)o).getSubIngredientCombination() != null) { 
        	int compareCode = getSubIngredientCombination()
        		.compareTo(((MonographSubIngredientDose) o).getSubIngredientCombination());
        	if (compareCode != 0) {
        		return compareCode;
        	}
        }

        // Compare on the Min Dose (if different)
        Float passedDoseMinimum = ((MonographSubIngredientDose) o).getDoseMinimum();
        if (doseMinimum != null && passedDoseMinimum != null &&
        	!doseMinimum.equals(passedDoseMinimum) ) {
        	return doseMinimum.compareTo(passedDoseMinimum);
        }
        //lets equate zero to null (not provided)
        if (doseMinimum == null && 
        	passedDoseMinimum != null && passedDoseMinimum.floatValue() != 0f) {
        	return 1;
        }
        if (doseMinimum != null && 
        	(passedDoseMinimum == null || passedDoseMinimum.floatValue() == 0f)) {
        	return -1;
        }

        // Then compare on the Max Dose (if different)
        Float passedDoseMaximum = ((MonographSubIngredientDose) o).getDoseMaximum();
        if (doseMaximum != null && passedDoseMaximum != null &&
        	!doseMaximum.equals(passedDoseMaximum) ) {
        	return doseMaximum.compareTo(passedDoseMaximum);
        }
        //lets equate zero to null (not provided)
        if (doseMaximum == null && 
        	passedDoseMaximum != null && passedDoseMaximum.floatValue() != 0f) {
        	return 1;
        }
        if (doseMinimum != null && 
        	(passedDoseMaximum == null || passedDoseMaximum.floatValue() == 0f)) {
        	return -1;
        }


		} catch ( Exception e ) {
			e.printStackTrace();
		}

        //finally, compare on the code.
		return getCode().compareTo(((MonographSubIngredientDose) o).getCode());
    }

    /**
     * Getter for the maximum dose. This maximum dose should be equal or less
     * than the maximum dose defined in the sub-ingredient object.
     *
     * <p>GUI Implementation: If this field is empty and the minimum dose field
     * is filled, then it should take on the value of the minimum dose field.
     *
     * @return  the maximum dose for this MonographSubIngredientDose.
     *
     * @see     #setDoseMaximum()
     */
    public Float getDoseMaximum() {

        return doseMaximum;
    }

	public int getFrequencyMaximum() {

		return frequencyMaximum;
	}

	public int getFrequencyMinimum() {

		return frequencyMinimum;
	}

	public Units getFrequencyUnits() {

		return frequencyUnits;
	}

    /**
     * Getter for the minimum dose. This minimum dose should be equal or less
     * than the maximum dose defined in the sub-ingredient object.
     *
     * <p>GUI Implementation: If this field is empty and the maximum dose field
     * is filled, then it should take on the value of the maximum dose field.
     *
     * @return  the minimum dose for this MonographSubIngredientDose.
     *
     * @see     #setDoseMinimum()
     */
    public Float getDoseMinimum() {

        return doseMinimum;
    }

    /**
     * Getter for the dose units. The units should co-incide with the units used
     * in the sub-ingredient object. At this time, we have no code for unit
     * conversion.
     *
     * <p>When presenting this information to the user, we should perhaps
     * default the Dose Unit information from the SubIngredient units.
     *
     * Note:  The above functionality has been disabled.  It was causing issues
     * with the Green Tea Extract Monograph web services returning the 
     * subingredient microgram/g.
     *
     * @return  the units qualifying the dose information for this
     *          MonographSubIngredientDose.
     *
     * @see     #setDoseUnits()
     */
    public Units getDoseUnits() {

        /**
        if (doseUnits == null && getSubIngredientCombination()!= null) {
            if (getSubIngredientCombination().getPrimarySubIngredient() != null
                && getSubIngredientCombination().getPrimarySubIngredient()
                   .getUnits() != null) {
                return getSubIngredientCombination().getPrimarySubIngredient()
                       .getUnits();
            }
        }
        */

        return doseUnits;
    }

    /**
     * Getter for the sub-ingredient. The sub-ingredient combination is the
     * object being qualified by the MonographSubIngredientDose object.
     * @return the sub-ingredient combination used by this
     *         MonographSubIngredientDose object.
     * @see    #setSubIngredientCombination()
     */
    public MonographSubIngredientCombination getSubIngredientCombination() {
        return subIngredientCombination;
    }

    
	public RestrictionType getRestrictionType() {
		return restrictionType;
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
        StringBuffer buffer = new StringBuffer();

        buffer.append(super.getValuesAsString());
        buffer.append(", subIngredientCombination: ");
        buffer.append(getSubIngredientCombination());
        buffer.append(", doseMaximum: ");
        buffer.append(getDoseMaximum());
        buffer.append(", doseMinimum: ");
        buffer.append(getDoseMinimum());
        buffer.append(", doseUnits: ");
        if (getDoseUnits() != null){
            buffer.append(getDoseUnits().getName());
        } else {
            buffer.append("null");
        }
        buffer.append(", frequencyMaximum: ");
        buffer.append(getFrequencyMaximum());
        buffer.append(", frequencyMinimum: ");
        buffer.append(getFrequencyMinimum());
        buffer.append(", frequencyUnits: ");
        if (getFrequencyUnits() != null){
            buffer.append(getFrequencyUnits().getName());
        } else {
            buffer.append("null");
        }
        buffer.append(", restrictionType: ");
        if (getRestrictionType() != null){
            buffer.append(getRestrictionType().getName());
        } else {
            buffer.append("null");
        }

        return buffer.toString();
    }

    /**
     * Sets the maximum dose for this object.
     *
     * @param  newVal  the maximum dose
     *
     * @see    #getDoseMaximum()
     */
    public void setDoseMaximum(Float newVal) {
        doseMaximum = newVal;
    }

    /**
     * Sets the minimum dose for this object.
     *
     * @param  newVal  the minimum dose
     *
     * @see    #getDoseMinimum()
     */
    public void setDoseMinimum(Float newVal) {
        doseMinimum = newVal;
    }

    /**
     * Sets the units qualifying the minimum and maximum dose information for
     * this object.
     *
     * @param  newVal  the units qualifying the minimum and maximum dose
     *
     * @see    #getDoseUnits()
     */
    public void setDoseUnits(Units newVal) {
        doseUnits = newVal;
    }

	public void setFrequencyUnits(Units newVal) {
		frequencyUnits = newVal;
	}

	public void setFrequencyMaximum(int newVal) {
		frequencyMaximum = newVal;
	}

	public void setFrequencyMinimum(int newVal) {
		frequencyMinimum = newVal;
	}


    /**
     * Sets the qualifying sub-ingredient combination for which this object is
     * being created.
     * @param newVal the sub-ingredient combination being qualified by this
     *                 object.
     * @see   #getSubIngredientCombination()
     */
    public void setSubIngredientCombination(
                MonographSubIngredientCombination newVal) {
        subIngredientCombination = newVal;
    }

    /***************************************************************************
     */
	public void setRestrictionType(RestrictionType newVal) {
		restrictionType = newVal;
	}

    /***************************************************************************
     */
    //TODO consider refactoring into another object
    public String getQuantityForDisplay() {
        
        //might just be a ratio we need.
    	if ((doseMinimum == null || doseMinimum.floatValue() == 0f) &&
        	(doseMaximum == null || doseMaximum.floatValue() == 0f)) {
        	return "";
        }
        	
    	// Units must be present to display any value.  If not available, then return a null.
        if ( getDoseUnits() == null ) { 
            return "";
        }
        
        DecimalFormat twoPlaces = new DecimalFormat("0.####");
        StringBuffer output = new StringBuffer();

        
        //both doses the same - display single dose
        if ( doseMinimum != null && doseMaximum != null && 
        	 doseMinimum.equals(doseMaximum)) {
        	output.append( twoPlaces.format(doseMinimum) );
        } 
        //min-max dose
        else if ( doseMinimum != null && getDoseMaximum() != null ) {
        	output.append( twoPlaces.format(doseMinimum) + CHAR_DASH + twoPlaces.format(doseMaximum) );
        } 
        //only min dose
        else if ( doseMinimum != null ) {
        	if (isLanguageFrench()) 
        		output.append(MonographDose.INCLUDING_F);
			else
				output.append(MonographDose.INCLUDING_E);
        	output.append( twoPlaces.format(doseMinimum) );
        }
        //only max dose
        else if ( doseMaximum != null ) {
        	if (isLanguageFrench()) 
        		output.append(" un maximum de ");
			else
				output.append(" no more than ");
        	output.append( twoPlaces.format(doseMaximum) );
        }
          
        // Dose Units
        if ( getDoseUnits() != null ) {
            output.append( CHAR_SPACE + getDoseUnits().getName());
        }
         
        return output.toString();
    }

        
        
    
    
    /***************************************************************************
     */
    //TODO consider refactoring into another object
    public String getFrequencyForDisplay() {
        if ( getFrequencyMinimum() == 0 && getFrequencyMaximum() == 0 ) {
            return null;
        }
        StringBuffer output = new StringBuffer();
        
        if ( getFrequencyMinimum() != 0 
             && getFrequencyMaximum() != 0 
             && getFrequencyMinimum() != getFrequencyMaximum()) {
             output.append( getFrequencyMinimum() + CHAR_DASH + getFrequencyMaximum() );
        } else if ( getFrequencyMinimum() != 0 
             && getFrequencyMinimum() != getFrequencyMaximum()) {
             output.append( getFrequencyMinimum() );
        } else if ( getFrequencyMaximum() != 0 ) {
             output.append( getFrequencyMaximum() );
        }
        // Frequency Units
        if ( getFrequencyUnits() != null ) {
            output.append( FREQUENCY_QUALIFIER + getFrequencyUnits().getName().toLowerCase());
        }
        return output.toString();
        
    }

    /***************************************************************************
     */
    //TODO consider refactoring into another object
    public String getValueForMonographDisplay() {
        StringBuffer output = new StringBuffer();
        //output.append("TODO (PreparationGroup): ");
        /*
        if ( getPreparationType() != null ) {
            output.append( getPreparationType().getName() + COLON_SPACE );
        } else if ( getPreparationTypeGroup() != null ) {
            output.append( getPreparationTypeGroup().getName() + COLON_SPACE );
        }
        */
        
        // Minimum / Maximum Dose
        if ( getDoseMinimum() != null && getDoseMaximum() != null 
                        && getDoseMinimum().equals(getDoseMaximum())) {
                    output.append( getDoseMinimum() );
                } else if ( getDoseMinimum() != null && getDoseMaximum() != null ) {
                    output.append( getDoseMinimum() + CHAR_DASH + getDoseMaximum() );
                } else if ( getDoseMinimum() != null ) {
                    output.append( getDoseMinimum() );
                } else if ( getDoseMaximum() != null ) {
                    output.append( getDoseMaximum() );
                }
                // Dose Units
        if ( getDoseUnits() != null ) {
            output.append( CHAR_SPACE + getDoseUnits().getCode());
        }
        
        if ( getSubIngredientCombination() != null) {
            if ( getSubIngredientCombination().getPrimarySubIngredient() != null 
                && getSubIngredientCombination().getPrimarySubIngredient().getIngredient() != null
                && getSubIngredientCombination().getPrimarySubIngredient().getIngredient().getAuthorizedName() != null) {
                output.append( " of " + getSubIngredientCombination().getPrimarySubIngredient().getIngredient().getAuthorizedName());

                if ( getSubIngredientCombination().getSecondarySubIngredient() != null 
                        && getSubIngredientCombination().getSecondarySubIngredient().getIngredient() != null
                        && getSubIngredientCombination().getSecondarySubIngredient().getIngredient().getAuthorizedName() != null) {
                        output.append( " and " + getSubIngredientCombination().getSecondarySubIngredient().getIngredient().getAuthorizedName());
                    }
            }
        }
        
        // Append a comma space.
        output.append( COMMA_SPACE );
        
//      Minimum / Maximum Frequency
             if ( getFrequencyMinimum() != 0 
                 && getFrequencyMaximum() != 0 
                 && getFrequencyMinimum() != getFrequencyMaximum()) {
                 output.append( getFrequencyMinimum() + CHAR_DASH + getFrequencyMaximum() );
             } else if ( getFrequencyMinimum() != 0 
                 && getFrequencyMinimum() != getFrequencyMaximum()) {
                 output.append( getFrequencyMinimum() );
             } else if ( getFrequencyMaximum() != 0 ) {
                 output.append( getFrequencyMaximum() );
             }
             // Frequency Units
             if ( getFrequencyUnits() != null ) {
                 output.append( FREQUENCY_QUALIFIER + getFrequencyUnits().getName().toLowerCase());
             }
        
        
        return output.toString();
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


}
