package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.HomeopathicFormula;
import ca.gc.hc.nhpd.model.HomeopathicMethodOfPreparation;
import ca.gc.hc.nhpd.util.StringComparator;

/*******************************************************************************
 * This is a web service wrapper class used to represent a homeopathic method of
 * preparation object. It can be constructed by passing a homeopathic method of
 * preparation in the constructor, or as a new homeopathic method of preparation
 * when the constructor is left empty.
 */

public class HomeopathicMethodOfPreparationWS implements
        Comparable<HomeopathicMethodOfPreparationWS> {

    // ~ Static fields/initializers
    // ---------------------------------------------

    private static final String SPACE = " ";

    private static final String DASH = "-";

    private HomeopathicMethodOfPreparation homeopathicMethodOfPreparation;

    private HomeopathicFormula homeopathicFormula;

    // ~ Constructors
    // -----------------------------------------------------------

    /**
     * Constructor used to instantiate this class with an instance of the
     * homeopathic method of preparation.
     * 
     * @param homeopathicSubstance
     *            being used to instantiate this class.
     */
    public HomeopathicMethodOfPreparationWS(
            HomeopathicMethodOfPreparation homeopathicMethodOfPreparation,
            HomeopathicFormula homeopathicFormula) {
        if (homeopathicMethodOfPreparation == null
                || homeopathicFormula == null) {
            throw new IllegalArgumentException(
                    "homeopathicMethodOfPreparation is null!");
        }
        this.homeopathicMethodOfPreparation = homeopathicMethodOfPreparation;
        this.homeopathicFormula = homeopathicFormula;
    }

    public HomeopathicMethodOfPreparationWS() {
        this.homeopathicMethodOfPreparation = new HomeopathicMethodOfPreparation();
    }

    /*----------------------------------------------------------------
     * Public Accessors/Mutators - methods exposed to the Web Service
     *----------------------------------------------------------------*/

    /**
     * Returns the Quantity as a string value.
     */
    public String getQuantityAsString() {

        StringBuilder sb = new StringBuilder();
        if (homeopathicMethodOfPreparation != null
                && homeopathicMethodOfPreparation.getMinimum() != null
                && homeopathicMethodOfPreparation.getMinimum() != 0) {
            sb.append(homeopathicMethodOfPreparation.getMinimum());
            if (homeopathicMethodOfPreparation.getMaximum() != null) {
                sb.append(DASH + homeopathicMethodOfPreparation.getMaximum());
            }
        }
        if (homeopathicFormula != null
                && homeopathicFormula.getHomeopathicDilution() != null
                && homeopathicFormula.getHomeopathicDilution().getCode() != null) {
            sb.append(homeopathicFormula.getHomeopathicDilution().getCode());
        }

        return sb.toString();
    }

    public void setQuantityAsString(String value) {
        // Not Implemented.
    }

    /**
     * Returns the preparation type code as a string.
     */
    public String getHomeopathicMethodOfPreparationTypeCode() {

        if (homeopathicMethodOfPreparation != null
                && homeopathicMethodOfPreparation
                        .getHomeopathicPreparationType() != null
                && homeopathicMethodOfPreparation
                        .getHomeopathicPreparationType().getCode() != null) {
            return homeopathicMethodOfPreparation
                    .getHomeopathicPreparationType().getCode();
        }
        return null;
    }

    public void setHomeopathicMethodOfPreparationTypeCode(String value) {
        // Not Implemented.
    }

    /**
     * Returns the minimum as an integer.
     */
    public Integer getMinimum() {
        if (homeopathicMethodOfPreparation != null
                && homeopathicMethodOfPreparation.getMinimum() != null) {
            return homeopathicMethodOfPreparation.getMinimum();
        }
        return null;
    }

    public void setMinimum(Integer value) {
        // Not Implemented.
    }

    /**
     * Returns the maximum as an integer.
     */
    public Integer getMaximum() {
        if (homeopathicMethodOfPreparation != null
                && homeopathicMethodOfPreparation.getMaximum() != null) {
            return homeopathicMethodOfPreparation.getMaximum();
        }
        return null;
    }

    public void setMaximum(Integer value) {
        // Not Implemented.
    }

    /**
     * Returns the homeopathic method of preparation as a string. This method
     * flattens the relationship and build a string composed of the minimum,
     * maximum and homeopathic dilution to facilitate the display of this
     * information.
     * 
     * Note: This field is used by the comparator and should return a unique
     * value for a given Homeopathic Method of Preparation.
     */
    public String getHomeopathicMethodOfPreparationsAsString() {

        if (homeopathicFormula == null
                || homeopathicFormula.getHomeopathicMethodOfPreparations() == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        if (homeopathicMethodOfPreparation != null
                && homeopathicMethodOfPreparation.getMinimum() != null
                && homeopathicMethodOfPreparation.getMinimum() != 0) {
            sb.append(homeopathicMethodOfPreparation.getMinimum());
            if (homeopathicMethodOfPreparation.getMaximum() != null) {
                sb.append(DASH + homeopathicMethodOfPreparation.getMaximum());
            }
        }

        if (homeopathicFormula != null
                && homeopathicFormula.getHomeopathicDilution() != null
                && homeopathicFormula.getHomeopathicDilution().getCode() != null) {
            sb.append(homeopathicFormula.getHomeopathicDilution().getCode());
        }

        if (homeopathicMethodOfPreparation != null
                && homeopathicMethodOfPreparation
                        .getHomeopathicPreparationType() != null
                && homeopathicMethodOfPreparation
                        .getHomeopathicPreparationType().getCode() != null) {
            sb.append(SPACE).append(
                    homeopathicMethodOfPreparation
                            .getHomeopathicPreparationType().getCode());
        }

        return sb.toString();
    }

    public void setHomeopathicMethodOfPreparationsAsString(String value) {
        // Not Implemented.
    }

    /**
     * Comparator that uses the getHomeopathicMethodOfPreparation to sort and
     * ensure that we do not get duplicate values.
     */
    public int compareTo(HomeopathicMethodOfPreparationWS o)
            throws ClassCastException {

        if (getHomeopathicMethodOfPreparationsAsString() == null) {
            return 1;
        }
        if (o == null || o.getHomeopathicMethodOfPreparationsAsString() == null) {
            return -1;
        }

        return StringComparator.compare(
                getHomeopathicMethodOfPreparationsAsString(), o
                        .getHomeopathicMethodOfPreparationsAsString());
    }
}
