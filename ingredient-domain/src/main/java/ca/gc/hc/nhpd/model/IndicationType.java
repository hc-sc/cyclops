package ca.gc.hc.nhpd.model;

/*******************************************************************************
 * This object represents the main categories of indications: Therapeutic, Risk
 * reduction, and Structure-function.
 */
public class IndicationType extends PersistentObject { //TODO New Class
	private String code;
	private String nameE;
	private String nameF;
	private String descriptionE;
	private String descriptionF;

    /***************************************************************************
	 * Gets the commonly used unique code for this object.
	 * @return the commonly used unique code for this object.
	 * @see #setCode()
	 */
	public String getCode(){
		return code;
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
	 */
	public void setCode(String newVal){
		code = newVal;
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
	 * Gets the description in English.
	 * @return the description in English.
	 * @see #setDescriptionE()
	 */
	public void setDescriptionE(String newVal){
		descriptionE = newVal;
	}

	/***************************************************************************
	 * Sets the description in French.
	 * @param newVal the description in French
	 * @see #getDescriptionF()
	 */
	public void setDescriptionF(String newVal){
		descriptionF = newVal;
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
           return buffer.toString();
       }


}