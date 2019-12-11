package ca.gc.hc.nhpd.model;


/*******************************************************************************
 * This class represents the static text appendix of a Monograph.  The contents
 * of a Monographs appendix are scraped from the legacy static pages and stored
 * via this object in the NHP Ingredients Database. 
 */
public class MonographAppendix extends PersistentObject {

    private Monograph monograph;
    private String appendicesE;
    private String appendicesF;

    //~ public -----------------------------------------------------------------

    /**
     * Getter for the English Appendices.
     *
     * @return  the English version of the Appendices.
     */
    public String getAppendicesE() {
        return appendicesE;
    }

    /**
     * Getter for the French Appendices.
     *
     * @return  the French version of the Appendices.
     */
    public String getAppendicesF() {
        return appendicesF;
    }

    /**
     * Gets the Appendice in the language appropriate for the Locale.
     *
     * @return  the locale-specific Appendixes.
     */
    public String getAppendices() {

        if (isLanguageFrench()) {
            return getAppendicesF();
        }

        return getAppendicesE();
    }

    /**
     * Gets the Monograph associated to this appendix.
     * 
     * @return
     * 
     * @see setMonograph()
     */
    public Monograph getMonograph() {
    	return monograph;
    }
    
    /**
     * Sets the English appendix for this object.
     *
     * @param  newVal
     */
    public void setAppendicesE(String newVal) {
        appendicesE = newVal;
    }

    /**
     * Sets the French appendix for this object
     *
     * @param  newVal
     */
    public void setAppendicesF(String newVal) {
        appendicesF = newVal;
    }

    /**
     * Sets the Monograph associated to this appendix.
     * 
     * @param newVal monograph for this appendix.
     * 
     * @see getMonograph()
     */
    public void setMonograph(Monograph newVal) {
    	monograph = newVal;
    }

}

