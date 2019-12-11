package ca.gc.hc.nhpd.model;

/*******************************************************************************
 * An object that indicates where the quantity tolerances are defined for an
 * Ingredient with a given NhpClassification. They may be in the NhpClassification
 * (for all of its ingredients), in the Ingredient (for all of its DosageForms),
 * or in the Ingredient DosageForm combination.
 */
public class QuantityToleranceModel extends PersistentObject {
	private String code;
	private String descriptionE;
	private String descriptionF;
	private String nameE;
	private String nameF;

    /***************************************************************************
	 * Gets the commonly used unique code for this object.
	 * @return the commonly used unique code for this object.
	 * @see #setCode()
	 */
	public String getCode(){
		return code;
	}

    /***************************************************************************
	 * Gets the description in the language appropriate for the Locale.
	 * @return the locale-specific description.
	 */
	public String getDescription(){
        if (isLanguageFrench()) {
            return getDescriptionF();
        }
        return getDescriptionE();
	}

    /***************************************************************************
	 * Gets the description in English.
	 * @return the description in English.
	 * @see #setDescriptionE()
	 */
	public String getDescriptionE(){
		return descriptionE;
	}

    /***************************************************************************
	 * Gets the description in French.
	 * @return the description in French.
	 * @see #setDescriptionF()
	 */
	public String getDescriptionF(){
		return descriptionF;
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
	 * Sets the commonly used unique code for this object.
	 * @param newVal the commonly used unique code for this object.
	 * @see #getCode()
	 * @param newVal
	 */
	public void setCode(String newVal){
		code = newVal;
	}

    /***************************************************************************
	 * Sets the description in English.
	 * @param newVal the description in English
	 * @see #getDescriptionE()
	 * @param newVal
	 */
	public void setDescriptionE(String newVal){
		descriptionE = newVal;
	}

    /***************************************************************************
	 * Gets the description in French.
	 * @param newVal the description in French
	 * @see #getDescriptionF()
	 * @param newVal
	 */
	public void setDescriptionF(String newVal){
		descriptionF = newVal;
	}

    /***************************************************************************
	 * Gets the name in English.
	 * @return the name in English.
	 * @see #setNameE()
	 * @param newVal
	 */
	public void setNameE(String newVal){
		nameE = newVal;
	}

    /***************************************************************************
	 * Sets the name in French.
	 * @param newVal the name in French
	 * @see #getNameF()
	 * @param newVal
	 */
	public void setNameF(String newVal){
		nameF = newVal;
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
        buffer.append(", nameE: ");
        buffer.append(getNameE());
        buffer.append(", nameF: ");
        buffer.append(getNameF());
        buffer.append(", descriptionE: ");
        buffer.append(getDescriptionE());
        buffer.append(", descriptionF: ");
        buffer.append(getDescriptionF());
        
        return buffer.toString();
    }
}