package ca.gc.hc.nhpd.model;
import ca.gc.hc.nhpd.util.StringComparator;

/*******************************************************************************
 * An object that represents text (a String) for use by other objects when a
 * collection of text strings are needed. In this case, we are using a copy of 
 * the GenericText as a basis for creating the Homeopathic Generic Text.  It was
 * created to support Homeopathic Common Names, Homeopathic Proper Name and
 * Homeopathic Source Material.  All three of these objects have no relevance
 * on the current object model objects with the same names.
 * 
 * To keep the code clean and seperate, we created this seperate object to 
 * store these Homeopathic string lists.
 * 
 * Note, at this time, we are only supporting the english text.  In future 
 * releases, we may want to support both English and French text.
 */
public class HomeopathicGenericText extends PersistentObject implements Text, Comparable {
    private String textE;
    private String textF;

    //~ public -----------------------------------------------------------------
    /**
     * Gets the text in the language appropriate for the Locale.
     *
     * @return  the locale-specific text.
     */
    public String getText() {

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
        return textE;
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
        // do nothing;
    }
    
    public int compareTo (Object obj) throws ClassCastException {
    	if (obj == null) return -1;
    	
    	return StringComparator.compare(getText(),((HomeopathicGenericText)obj).getText());
    	
    }
    	


}
