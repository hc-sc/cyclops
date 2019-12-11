package ca.gc.hc.nhpd.model;
import ca.gc.hc.nhpd.util.StringComparator;

/*******************************************************************************
 * An object that represents text (a String) for use by other objects when a
 * collection of text strings are needed. For example, a Monograph uses multiple
 * GenericText entries to define the list of dose notes.
 */
public class GenericText extends PersistentObject implements Text, Comparable {
    private String textE;
    private String textF;

    //~ public -----------------------------------------------------------------
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
     * end.
     *
     * @return  a string representation of this object.
     */
    public String getValuesAsString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(super.getValuesAsString());
        buffer.append(", textE: ");
        buffer.append(getTextE());
        buffer.append(", textF: ");
        buffer.append(getTextF());

        return buffer.toString();
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
    
    public int compareTo (Object obj) throws ClassCastException {
    	if (obj == null) return -1;
    	
    	return StringComparator.compare(getText(),((GenericText)obj).getText());
    	
    }
    	


}
