package ca.gc.hc.nhpd.model;

import java.util.Comparator;

import ca.gc.hc.nhpd.util.StringUtils;

/*******************************************************************************
 * An object that represents a subset of the population that a combination of
 * ingredients has been tested on / is suitable for.
 */
public class SubPopulation extends PersistentObject implements Comparable { //TODO New Class
	
	private String code;
    private String commentE;
	private String commentF;
	private String descriptionE;
	private String descriptionF;
	private String exclusionCriterionE;
	private String exclusionCriterionF;
	private String inclusionCriterionE;
	private String inclusionCriterionF;
	private int maximumAge;
	private Units maximumAgeUnits;
	private int minimumAge;
	private Units minimumAgeUnits;
	private String nameE;
	private String nameF;
	private Boolean preferred;

    /**
     * Gets the code.  Used in the loading of Monographs.
     *
     * @return  the code.
     *
     * @see     #setCode()
     */
    public String getCode() {
        return code;
    }
	
    /***************************************************************************
	 * Gets the comment in the language appropriate for the Locale.
	 * @return the locale-specific comment.
	 */
	public String getComment(){
        if (isLanguageFrench()) {
            return getCommentF();
        }
        return getCommentE();
	}

    /***************************************************************************
	 * Gets the comment in English.
	 * @return the comment in English.
	 * @see #setCommentE()
	 */
	public String getCommentE(){
		return commentE;
	}

    /***************************************************************************
	 * Gets the comment in French.
	 * @return the comment in French.
	 * @see #setCommentF()
	 */
	public String getCommentF(){
		return commentF;
	}

    /***************************************************************************
     */
	public String getDescription(){
        if (isLanguageFrench()) {
            return getDescriptionF();
        }
        return getDescriptionE();
	}

    /***************************************************************************
     */
	public String getDescriptionE(){
		return descriptionE;
	}

    /***************************************************************************
     */
	public String getDescriptionF(){
		return descriptionF;
	}

    /***************************************************************************
     */
	public String getExclusionCriterion(){
        if (isLanguageFrench()) {
            return getExclusionCriterionF();
        }
        return getExclusionCriterionE();
	}

    /***************************************************************************
     */
	public String getExclusionCriterionE(){
		return exclusionCriterionE;
	}

    /***************************************************************************
     */
	public String getExclusionCriterionF(){
		return exclusionCriterionF;
	}

    /***************************************************************************
     */
	public String getInclusionCriterion(){
        if (isLanguageFrench()) {
            return getInclusionCriterionF();
        }
        return getInclusionCriterionE();
	}

    /***************************************************************************
     */
	public String getInclusionCriterionE(){
		return inclusionCriterionE;
	}

    /***************************************************************************
     */
	public String getInclusionCriterionF(){
		return inclusionCriterionF;
	}

    /***************************************************************************
     */
	public int getMaximumAge(){
		return maximumAge;
	}

    /***************************************************************************
     */
	public Units getMaximumAgeUnits(){
		return maximumAgeUnits;
	}

    /***************************************************************************
     */
	public int getMinimumAge(){
		return minimumAge;
	}

    /***************************************************************************
     */
	public Units getMinimumAgeUnits(){
		return minimumAgeUnits;
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
	 * @see #setNameF()
	 */
	public String getNameF(){
		return nameF;
	}
	
	/***************************************************************************
     * Boolean flag that indicates if this unit is a preferred subpopulation.  
     * Preferred units are sometimes emphasized to give a user an easier selection
     * when presenting the subpopulation list.  
     * 
     * @return preferred flag
     * 
     * @see setPreferred()
     */
    public Boolean isPreferred() {
        return preferred;
    }
    
    /***************************************************************************
     * Boolean flag that indicates if this unit is a preferred subpopulation.  
     * Preferred units are sometimes emphasized to give a user an easier selection
     * when presenting the subpopulation list.  
     * Hibernate Implementation.
     * 
     * @return preferred flag
     * 
     * @see setPreferredAsString()
     */
    public String getPreferredAsString() {
        return StringUtils.booleanToString(preferred);
    }	

    /**
     * Sets the code.  Used in the loading of Monographs.
     *
     * @param  newValue  code.
     */
    public void setCode(String newValue) {
        code = newValue;
    }

    /***************************************************************************
	 * Sets the comment in English.
	 * @param newVal the comment in English
	 * @see #getCommentE()
	 * @param newVal
	 */
	public void setCommentE(String newVal){
		commentE = newVal;
	}

    /***************************************************************************
	 * Gets the comment in French.
	 * @param newVal the comment in French
	 * @see #getCommentF()
	 * @param newVal
	 */
	public void setCommentF(String newVal){
		commentF = newVal;
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
	 * @param newVal
	 */
	public void setExclusionCriterionE(String newVal){
		exclusionCriterionE = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setExclusionCriterionF(String newVal){
		exclusionCriterionF = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setInclusionCriterionE(String newVal){
		inclusionCriterionE = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setInclusionCriterionF(String newVal){
		inclusionCriterionF = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setMaximumAge(int newVal){
		maximumAge = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setMaximumAgeUnits(Units newVal){
		maximumAgeUnits = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setMinimumAge(int newVal){
		minimumAge = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setMinimumAgeUnits(Units newVal){
		minimumAgeUnits = newVal;
	}

    /***************************************************************************
	 * Gets the name in English.
	 * @return the name in English.
	 * @see #setNameE()
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
     * Boolean flag that indicates if this unit is a preferred subpopulation.  
     * Preferred units are sometimes emphasized to give a user an easier selection
     * when presenting the subpopulation list.  
     * 
     * @param preferred flag
     * 
     * @see isPreferred()
     */
    public void setPreferred(Boolean preferred) {
        this.preferred = preferred;
    }

	/***************************************************************************
     * Boolean flag that indicates if this unit is a preferred subpopulation.  
     * Preferred units are sometimes emphasized to give a user an easier selection
     * when presenting the subpopulation list.  
     * Hibernate Implementation.
     * 
     * @param preferred flag
     * 
     * @see getPreferredAsString()
     */
    public void setPreferredAsString(String preferred) {
        this.preferred = StringUtils.stringToBoolean(preferred);
    }
	
	/**
	 * Compares this object with the specified object for equalify and for
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

        if (getMinimumAgeUnits() == null) {
			return -1;
		}

		if (o == null) {
			return 1;
		}
        
        //the object to which this is being compared
        SubPopulation compSubPop = (SubPopulation)o;
        if (compSubPop.getId() != null && getId() != null && 
                compSubPop.getId().equals(getId())) {
            return 0;
        }

        // Get O's values.
        SubPopulation oSub = (SubPopulation) o;
        int o_min_age = ageInMonths(oSub.getMinimumAge(), oSub.getMinimumAgeUnits());
        int o_max_age = ageInMonths(oSub.getMaximumAge(), oSub.getMaximumAgeUnits());

        // Get this values.
        int t_min_age = ageInMonths(getMinimumAge(), getMinimumAgeUnits());
        int t_max_age = ageInMonths(getMaximumAge(), getMaximumAgeUnits());
        
		// Sort with the smallest minimum age first.
        // If same, sort with the smallest maximum age second.
		if ( o_min_age < t_min_age ) { 
			return 1;
        } else if ( o_min_age > t_min_age ) {
            return -1;
		} else {
            if ( o_max_age < t_max_age) {
                return 1;
            } else if ( o_max_age > t_max_age) {
                return -1;
            }
        }
		return 1;
	}
    
    private int ageInMonths(int count, Units units) {
        
        int multiplier = 1;
        
        if (units == null) {
            
            multiplier = 0;
            
        } else if (units.equals(Units.UNIT_CODE_MONTH)) {
            
            multiplier = 12;

        }
        
        return count * multiplier; 
        
    }
    
    public static class SubPopulationComparator implements Comparator {
        /***********************************************************************
         * Compares the passed objects for order. Returns a negative integer,
         * zero, or a positive integer as the first object is less than, equal
         * to, or greater than the second.
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         */
        public int compare(Object o1, Object o2) throws ClassCastException {
            
            if (o1 == null || ((SubPopulation)o1).getCode() == null) {
                return 1;
            }
            if (o2 == null || ((SubPopulation)o2).getCode() == null) {
                return -1;
            }
            return ((SubPopulation)o1).compareTo(((SubPopulation)o2));
        }
    }

}