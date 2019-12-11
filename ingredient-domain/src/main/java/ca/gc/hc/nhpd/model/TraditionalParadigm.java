package ca.gc.hc.nhpd.model;

/*******************************************************************************
 * An object that represents the type of traditional medicine a use is for.
 * Typically this is the region or area where it has been practiced.
 */
public class TraditionalParadigm extends PersistentObject { //TODO New Class
	private String code;
	private String descriptionE;
	private String descriptionF;
	private String nameE;
	private String nameF;

    /***************************************************************************
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
	 * @param newVal
	 */
	public void setCode(String newVal){
		code = newVal;
	}

    /***************************************************************************
	 * Sets the description in English.
	 * @param newVal the description in English
	 * @see #getDescriptionE()
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
                 buffer.append(", descriptionE: ");
                 buffer.append(getDescriptionE());                 
                 return buffer.toString();
             }
    

}