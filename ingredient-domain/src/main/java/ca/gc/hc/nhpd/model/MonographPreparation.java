package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;

/*******************************************************************************
 * A MonographPreparation contains preparation details that are a condition of a
 * DependantObject. They support varying degrees of specificity. For example,
 * one may only indicate a preparation type group, while others may be defined
 * down to the required extraction ratios.
 */
public class MonographPreparation extends PersistentObject implements Comparable {
    private String code;
    private String nameE;
    private String nameF;
	private Float maximumExtractionRatio;
    private Float minimumExtractionRatio;
    private PreparationType preparationType;
    private PreparationTypeGroup preparationTypeGroup;

    /**
     * Gets the maximum allowable extraction ratio for this preparation. This is
     * only populated when this restriction is required (rare).
     * @return the maximum allowable extraction ratio for this preparation
     * @see #setMaximumExtractionRatio()
     */
    public Float getMaximumExtractionRatio() {
        return maximumExtractionRatio;
    }

    /**
     * Gets the minimum allowable extraction ratio for this preparation. This is
     * only populated when this restriction is required (rare).
     * @return the minimum allowable extraction ratio for this preparation
     * @see #setMinimumExtractionRatio()
     */
    public Float getMinimumExtractionRatio() {
        return minimumExtractionRatio;
    }

    /**
     * Gets the preparationType of this preparation. This is required for all
     * definitions except those that only specify the preparationTypeGroup.
     * @return the preparationType of this preparation
     * @see #setPreparationType()
     */
    public PreparationType getPreparationType() {
        return preparationType;
    }

    /**
     * Gets the preparationTypeGroup of this preparation. This is only required
     * for high-level definitions where this is the only attribute being
     * specified. Otherwise, it is typically left empty.
     * @return the preparationTypeGroup of this preparation
     * @see #setPreparationTypeGroup()
     */
    public PreparationTypeGroup getPreparationTypeGroup() {
        return preparationTypeGroup;
    }

    /**
     * Sets the maximum allowable extraction ratio for this preparation. This is
     * only populated when this restriction is required (rare).
     * @param newVal the maximum allowable extraction ratio for this preparation
     * @see #getMaximumExtractionRatio()
     */
    public void setMaximumExtractionRatio(Float maximumExtractionRatio) {
        this.maximumExtractionRatio = maximumExtractionRatio;
    }

    /**
     * Sets the minimum allowable extraction ratio for this preparation. This is
     * only populated when this restriction is required (rare).
     * @param newVal the minimum allowable extraction ratio for this preparation
     * @see #getMinimumExtractionRatio()
     */
    public void setMinimumExtractionRatio(Float minimumExtractionRatio) {
        this.minimumExtractionRatio = minimumExtractionRatio;
    }

    /**
     * Sets the preparationType of this preparation. This is required for all
     * definitions except those that only specify the preparationTypeGroup.
     * @param newVal the preparationType of this preparation
     * @see #getPreparationType()
     */
    public void setPreparationType(PreparationType newVal) {
        preparationType = newVal;
    }

    /**
     * Sets the preparationTypeGroup of this preparation. This is only required
     * for high-level definitions where this is the only attribute being
     * specified. Otherwise, it is typically left empty.
     * @param newVal the preparationTypeGroup of this preparation
     * @see #getPreparationTypeGroup()
     */
    public void setPreparationTypeGroup(PreparationTypeGroup newVal) {
        preparationTypeGroup = newVal;
    }


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
        buffer.append(", code: ");
        buffer.append(getCode());
        buffer.append(", nameE: ");
        buffer.append(getNameE());
        buffer.append(", nameF: ");
        buffer.append(getNameF());
        buffer.append(", preparationType: ");
        if (getPreparationType() != null){
            buffer.append(getPreparationType().getName());
        } else {
            buffer.append("null");
        }
        buffer.append(", preparationTypeGroup: ");
        if (getPreparationTypeGroup() != null){
            buffer.append(getPreparationTypeGroup().getName());
        } else {
            buffer.append("null");
        }
        buffer.append(", minimumExtractionRatio: ");
        buffer.append(getMinimumExtractionRatio());
        buffer.append(", maximumExtractionRatio: ");
        buffer.append(getMaximumExtractionRatio());

        return buffer.toString();
    }

	public int compareTo(Object obj) throws ClassCastException {
		
		if (obj == null) 
			return -1;
		
		MonographPreparation passedObj = (MonographPreparation)obj;
		return StringComparator.compare(code,passedObj.getCode());
	}
	
}
