package ca.gc.hc.nhpd.model;

import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;

/*******************************************************************************
 * The MonographDuration object helps qualify the length of time the ingredients
 * should be used according to the Monograph.
 */
public class MonographDuration extends DependantObject {

    //~ Instance fields --------------------------------------------------------

	private String code;
    private String durationStatementE;
    private String durationStatementF;
    private int maximumDuration;
    private int minimumDuration;
    private Units units;

    //~ public -----------------------------------------------------------------

    /**
     * Gets the code. Only used in the loading of Monographs.
     * @return  the code.
     * @see     #setCode()
     */
    public String getCode() {
        return code;
    }
    
    /**
     * Helper method that returns this Dependency class as a single string. This
     * is helpful as it reduces the number of formulas required in the JSP and
     * helps in the grouping of values.
     * 
     * @return  a String representing this class for grouping purposes
     */
    @Override
    public String getDependencyAsString() {
        if (getMonographUses() != null && getMonographUses().size() != 0) {
            StringBuffer sb = new StringBuffer();
            Iterator i = getMonographUses().iterator();
            while ( i.hasNext() ) {
                MonographUse monographUse = (MonographUse) i.next();
                if ( sb.length() != 0 ) {
                    sb.append( DependantObject.SEMI_SPACE );
                }
                sb.append( monographUse.getShortStatement() );              
            }
            return sb.toString();
        }
        if ( getMonographDoseCombination() != null ) {
            return getMonographDoseCombination().getQuantitiesForDisplay();
        }
        if ( getSubPopulation() != null ) {
        	return getSubPopulation().getName();
        }
        return null;
    }
 
    
    /**
     * Helper method that returns this Dependency class as a list of strings
     * to be used by the display.
     * 
     * @return  an ArrayList representing this class for grouping purposes
     */
    public ArrayList<MonographUse> getDependencyTitleAsList() {
        ArrayList list = new ArrayList<MonographUse>();
        if (getMonographUses() != null && getMonographUses().size() != 0) {
            Iterator i = getMonographUses().iterator();
            while ( i.hasNext() ) {
                MonographUse monographUse = (MonographUse) i.next();
                list.add(monographUse);
            }
        }
        return list;
    }    
    
    
    
    /**
     * Helper method used to determine if we display the MonographDoseCombination as part
     * of the duration statement.
     */
    
    public boolean isDisplayMonographDoseCombination() {
        // Hide the Monograph Dose if it does not exist.
        
    	if(getMonographDoseCombination() == null) {
            return false;
        }
       
    	
        // Hide the Monograph Dose if the Minimum is empty, or the Maximum is filled.
        /*
        if(getMonographDoseCombination(). getDoseMinimum()==null
            ||getMonographDoseCombination(). getDoseMaximum()!=null){
            return false;
        }
        */
    	//TODO - what is the hide logic above doing???
    	//Set<MonographDose> monoDoses = 
    	//	getMonographDoseCombination().getMonographDoses();
        return true;
    }
 	
    
    /**
     * Gets the DurationStatement in the language appropriate for the Locale.
     *
     * @return  the locale-specific DurationStatement.
     */
    public String getDurationStatement() {
        
        if (isLanguageFrench()) {
            return getDurationStatementF();
        }

        return getDurationStatementE();

    }

    /**
     * Gets the DurationStatement in the English language.
     *
     * @return  the DurationStatement in English.
     *
     * @see     #setDurationStatementE()
     */
    public String getDurationStatementE() {
        return durationStatementE;
    }

    /**
     * Gets the DurationStatement in the French language.
     *
     * @return  the DurationStatement in French.
     *
     * @see     #setDurationStatementF()
     */
    public String getDurationStatementF() {
        return durationStatementF;
    }

    /**
     * Gets the MaximumDuration integer, qualified by the Units (Hour, Day,
     * Week).
     *
     * @return  the MaximumDuration integer.
     *
     * @see     #setMaximumDuration()
     */
    public int getMaximumDuration() {
        return maximumDuration;
    }

    /**
     * Gets the MinimumDuration integer, qualified by the Units (Hour, Day,
     * Week).
     *
     * @return  the MinimumDuration integer.
     *
     * @see     #setMinimumDuration()
     */
    public int getMinimumDuration() {
        return minimumDuration;
    }

    /**
     * Getter for the units defining the minimum and maximum length of time
     * qualifying this Monograph Duration. Units should be a subset of all units
     * with a specific time qualifiter. (Day, Week, Month, etc)
     *
     * @return  the units qualifying the minimum and maximum duration values
     *          found in this MonographDuration object.
     *
     * @see     #setUnits()
     */
    public Units getUnits() {
        return units;
    }

	/**
     * Sets the code.  Used in the loading of Monographs.
     *
     * @param  newValue  code.
     */
    public void setCode(String newValue) {
        code = newValue;
    }
    
    /**
     * Sets the value for the English duration statement qualifying this
     * MonographDuration object.
     *
     * @param  newVal  for the English duration statement.
     *
     * @see    getDurationStatementE()
     */
    public void setDurationStatementE(String newVal) {
        durationStatementE = newVal;
    }

    /**
     * Sets the value for the French duration statement qualifying this
     * MonographDuration object.
     *
     * @param  newVal  for the French duration statement.
     *
     * @see    getDurationStatementF()
     */
    public void setDurationStatementF(String newVal) {
        durationStatementF = newVal;
    }

    /**
     * Sets the value for the Maximum duration as an integer qualifying this
     * MonographDuration object.
     *
     * @param  newVal  integer for the Maximum duration for this duration
     *                 object.
     *
     * @see    getMaximumDuration()
     */
    public void setMaximumDuration(int newVal) {
        maximumDuration = newVal;
    }

    /**
     * Sets the value for the Minimum duration as an integer qualifying this
     * MonographDuration object.
     *
     * @param  newVal  integer for the Minimum duration for this duration
     *                 object.
     *
     * @see    getMinimumDuration()
     */
    public void setMinimumDuration(int newVal) {
        minimumDuration = newVal;
    }

    /**
     * Sets the units qualifying the minimum and maximum duration for this
     * Monograph Duration.
     *
     * @param  newVal  the frequency units that qualify the minimum and maximum
     *                 duration.
     *
     * @see    #getUnits()
     */
    public void setUnits(Units newVal) {
        units = newVal;
    }

    /***************************************************************************
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Subslasses should override this and add
     * their values to the end.
     */
    @Override
    public String getValuesAsString(){
        StringBuilder buffer = new StringBuilder();
       
        buffer.append(super.getValuesAsString());
        buffer.append(", durationStatementE: ");
        buffer.append(getDurationStatementE());
        buffer.append(", maximumDuration: ");
        buffer.append(getMaximumDuration());
        buffer.append(", minimumDuration: ");
        buffer.append(getMinimumDuration());
        buffer.append(", units: ");
        if (getUnits() != null){
            buffer.append(getUnits().getName());
        } else {
            buffer.append("null");
        }

        return buffer.toString();
    }
}
