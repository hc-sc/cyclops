package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.dao.AbstractDao;
import java.sql.Clob;


/*******************************************************************************
 * An object that represents text used on application pages that is too large or
 * complex to store in resource files, so is stored in persistent store instead.
 * </p>
 *
 * Note by Germain Brunet (March 12th, 2007) Object modified to extend Oracle
 * varchar limit of 4000 characters, by implementing Clobs. Clobs are set and
 * read from the RDBMs, and converted back and forth to their appropriate text
 * values. Application can deal directly with the text implementation without
 * having to know or support Clobs directly. The conversion process is achieved
 * using two conversion methods located in the AbstractDao.
 *
 * Change added 2011/01/25.  Since the application text is used to display
 * Internal AbLS monograph, we need to join it to the monograph table to 
 * determin if this text file should be displayed.  In the case of Internal
 * AbLS monograph, then the display Internal AbLS monograph flag is turned off,
 * this information should not be displayed.
 *
 * @version  1.0
 */
public class ApplicationText extends PersistentObject {

    public static final String BOOLEAN_EMPTY = "e";
    public static final String BOOLEAN_YES = "y";
    public static final String BOOLEAN_NO = "n";
    
	//~ Instance fields --------------------------------------------------------

	private String code;
	private String textE;
	private String textF;
    private String internalAblsMonograph;

	//~ public -----------------------------------------------------------------

	/**
	 * Gets the unique code for this object (used as the key for retrieval).
	 *
	 * @return  the unique code for this object, used as the key for retrieval.
	 *
	 * @see     #setCode()
	 */
	public String getCode() {
		return code;
	}

    /**
     * Gets the English short description used to represent this object in drop
     * down lists and other various interfaces.
     *
     * @return  the English specific short description.
     */
    public String getShortDescriptionE() {
        return getCode();
    }

    /**
     * Gets the English short description used to represent this object in drop
     * down lists and other various interfaces.
     *
     * @return  the French specific short description.
     */
    public String getShortDescriptionF() {
        return getCode();
    }
    
    /**
     * Flag that indicates if this is an Internal AbLS monograph.
     * 
     * @return the flag indicating an Internal AbLS monograph.
     * 
     * @see #setInternalAbLSMonograph()
     */
    public String getInternalAblsMonograph() {
        return internalAblsMonograph;
    }
    
    /**
     * Boolean Flag that indicates if this is an Internal AbLS monograph.
     * 
     * @return the flag indicating an Internal AbLS monograph.
     * 
     * @see #getInternalAbLSMonograph(), #setInternalAbLSMonograph()
     */
    public Boolean isInternalAblsMonograph() {
        return stringToBoolean(getInternalAblsMonograph());
    }
    
	/**
	 * Gets the text in the language appropriate for the Locale.
	 *
	 * @return  the locale-specific text.
	 */
	public String getText() {

		if (isLanguageFrench()) {
			return getTextF();
		}

		return getTextE();
	}

	/**
	 * Gets the text in English.
	 *
	 * @return  the text in English.
	 *
	 * @see     #setTextE()
	 */
	public String getTextE() {

		return textE;
	}

	/**
	 * Gets the text in French.
	 *
	 * @return  the text in French.
	 *
	 * @see     #setTextF()
	 */
	public String getTextF() {

		return textF;
	}

	/**
	 * This provides a list of the instance variable values for this object, and
	 * is called by the PersistentObject's toString(). Intended to provide
	 * useful debugging information. Over-ridden to add additional values to the
	 * end.</p>
	 *
	 * Since clobs are automatically represented as string, they were not added
	 * to this method.
	 *
	 * @return  this value represented as a string.
	 */
	public String getValuesAsString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(super.getValuesAsString());
		buffer.append(", code: ");
		buffer.append(getCode());
		buffer.append(", textE: ");
		buffer.append(getTextE());
		buffer.append(", textF: ");
		buffer.append(getTextF());

		return buffer.toString();
	}

	/**
	 * Sets the unique code for this object (used as the key for retrieval).
	 *
	 * @param  newVal  the unique code for this object, used as the key for
	 *                 retrieval.
	 *
	 * @see    #getCode()
	 */
	public void setCode(String newVal) {
		code = newVal;
	}

    /**
     * Sets the flag indicating that this is an internal AbLS monograph.
     * 
     * @param internalAblsMonograph
     * 
     * @See #getInternalAblsMonograph()
     */
    public void setInternalAblsMonograph(String internalAblsMonograph) {
        this.internalAblsMonograph = internalAblsMonograph;
    }
    
	/**
	 * Sets the text in English.
	 *
	 * @param  newVal  the text in English
	 *
	 * @see    #getTextE()
	 */
	public void setTextE(String newVal) {
		textE = newVal;
	}

	/**
	 * Gets the text in French.
	 *
	 * @param  newVal  the text in French
	 *
	 * @see    #getTextF()
	 */
	public void setTextF(String newVal) {
		textF = newVal;
	}
    
    /**
     * This private method is used to convert the Boolean value to an 
     * appropriate string for Hibernate purposes.
     * 
     * <p>True = y, False = n, Empty = e.
     *  
     * @param value being evalueated
     * @return string equivalent value for Hibernate purposes.
     */
    private String booleanToString(Boolean value) {
        if (value == null) {
            return BOOLEAN_EMPTY;
        }
        if (value.equals(true)) {
            return BOOLEAN_YES;
        }
        if (value.equals(false)) {
            return BOOLEAN_NO;
        }
        return BOOLEAN_EMPTY;
    }
    
    /**
     * This private method is used to convert the String value to an 
     * appropriate Boolean for the object.
     * 
     * <p>True = y, False = n, Empty = e.
     *  
     * @param value being evalueated
     * 
     * @return Boolean equivalent value for the object to use.
     */
    private Boolean stringToBoolean(String value) {
        if (value == null) {
            return null;
        }
        if (value.toLowerCase().startsWith(BOOLEAN_YES)) {
            return true;
        }
        if (value.toLowerCase().startsWith(BOOLEAN_NO)) {
            return false;
        }
        return null;
    }
    
}
