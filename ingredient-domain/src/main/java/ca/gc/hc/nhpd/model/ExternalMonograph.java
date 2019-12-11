package ca.gc.hc.nhpd.model;

/*******************************************************************************
 * This class represents an entry in a Compendium that is not stored within this
 * system (see Monograph for further details). In this case, rather than
 * attempting to fully model the content, this only contains the external
 * publication URL.
 */
public class ExternalMonograph extends Monograph implements NamedObject {
    
	private String webPageContentE;
    private String webPageContentF;

    /**
     * Gets the English web page contents.
     *
     * @return  the English web page contents.
     *
     * @see     #setWebPageContentE()
     */
    public String getWebPageContentE() {
        return webPageContentE;
    }

    /**
     * Gets the French web page contents.
     *
     * @return  the French web page contents.
     *
     * @see     #setWebPageContentF()
     */
    public String getWebPageContentF() {
        return webPageContentF;
    }
    
    /**
     * Sets the English web page contents.
     *
     * @param  newVal  the English web page contents
     *
     * @see    #getWebPageContentE()
     */
    public void setWebPageContentE(String newVal) {
        webPageContentE = newVal;
    }

    /**
     * Sets the French web page contents.
     *
     * @param  newVal  the French web page contents
     *
     * @see    #getWebPageContentF()
     */
    public void setWebPageContentF(String newVal) {
        webPageContentF = newVal;
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
        buffer.append(", English web page content size: ");
        if (getWebPageContentE() == null) {
        	buffer.append("null");
        } else {
        	buffer.append(getWebPageContentE().length());
        }
        if (getWebPageContentF() == null) {
        	buffer.append("null");
        } else {
        	buffer.append(getWebPageContentF().length());
        }

        return buffer.toString();
    }
}
