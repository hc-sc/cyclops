package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.NhpClassification;
import ca.gc.hc.nhpd.util.ThreadContext;

/*******************************************************************************
 * An object that represents an accepted non-medicinal purpose.
 * 
 * @version 1.0
 */
public class NhpClassificationWS implements Comparable<NhpClassificationWS> {

    // ~ Static fields/initializers
    // ---------------------------------------------

    private NhpClassification nhpClassification;

    private boolean bilingual;

    private String version = ThreadContext.getInstance().getVersion();

    // ~ Constructors
    // -----------------------------------------------------------

    public NhpClassificationWS() {
        nhpClassification = new NhpClassification();
    }

    public NhpClassificationWS(NhpClassification nhpClassification,
            boolean bilingual) {

        if (nhpClassification == null) {
            throw new IllegalArgumentException("nhpClassification is null!");
        }
        this.nhpClassification = nhpClassification;
        this.bilingual = bilingual;
    }

    /*----------------------------------------------------------------
     * Public Accessors/Mutators - methods exposed to the Web Service
     *----------------------------------------------------------------*/

    /**
     * Returns the locale specific name.
     * 
     * @return the locale specific name.
     */
    public String getName() {
        return nhpClassification.getName();
    }

    public void setName(String name) {
        // Not Implemented.
    }

    /**
     * Returns the English name when the bilingual flag is true.
     * 
     * @return the English name.
     */
    public String getNameE() {
        if (bilingual) {
            return nhpClassification.getNameE();
        }
        return null;
    }

    public void setNameE(String name) {
        // Not Implemented.
    }

    /**
     * Returns the French name when the bilingual flag is true.
     * 
     * @return the French name.
     */
    public String getNameF() {
        if (bilingual) {
            return nhpClassification.getNameF();
        }
        return null;
    }

    public void setNameF(String name) {
        // Not Implemented.
    }

    /**
     * Returns the code for the NHP Classification
     * 
     * @return the code for the NHP Classification
     */
    public String getCode() {
        return nhpClassification.getCode();
    }

    public void setCode(String code) {
        // Not Implemented.
    }

    /**
     * Returns the locale specific description for the NHP Classification.  This 
     * functionality was removed with the web service updates and only returns 
     * the description for backwards compatibility with the ePLA 1.4 form.
     * 
     * @return the locale specific description.
     */
    public String getDescription() {
    
    	if (!version.equals(ThreadContext.VERSION_1_4)) {
    		return null;
    	}
    
        return nhpClassification.getDescription();
    }

    public void setDescription(String description) {
        // Not Implemented.
    }

    /**
     * Returns the English description for the NHP Classification when the
     * bilingual flag is set to true.
     * 
     * @return the English description.
     */
    public String getDescriptionE() {
        if (bilingual) {
            return nhpClassification.getDescriptionE();
        }
        return null;
    }

    public void setDescriptionE(String descriptionE) {
        // Not Implemented.
    }

    /**
     * Returns the French description for the NHP Classification when the
     * bilingual flag is set to true.
     * 
     * @return the French description.
     */
    public String getDescriptionF() {
        if (bilingual) {
            return nhpClassification.getDescriptionF();
        }
        return null;
    }

    public void setDescriptionF(String descriptionF) {
        // Not Implemented.
    }

    /*
     * Method required because this object is added to a TreeSet Delegate to the
     * wrapped object. This method is locale specific and should sort the object
     * based on the language that is selected. This method leverages the
     * NhpClassification object compare method.
     */
    public int compareTo(NhpClassificationWS o) throws ClassCastException {
        NhpClassificationWS nhpClass = (NhpClassificationWS) o;
        return this.nhpClassification.compareTo(nhpClass.nhpClassification);
    }
}
