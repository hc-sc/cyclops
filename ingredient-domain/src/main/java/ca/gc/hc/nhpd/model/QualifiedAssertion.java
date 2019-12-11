package ca.gc.hc.nhpd.model;

import java.util.Set;

/*******************************************************************************
 * An object that represents an assertion (statement of fact) that has to be
 * qualified by an authoritative source.
 */
public class QualifiedAssertion extends GenericText {
    private Set<Citation> citationsE;
    private Set<Citation> citationsF;

    //~ public -----------------------------------------------------------------

    /**
     * Gets the citations in the language appropriate for the Locale.
     *
     * @return  the locale-specific citations.
     */
    public Set<Citation> getCitations() {

        if (isLanguageFrench()) {
            return getCitationsF();
        }

        return getCitationsE();
    }

    /**
     * Gets the set of citations for the English text.
     *
     * @return  the set of citations for the English text.
     *
     * @see     #setCitationE()
     */
    public Set<Citation> getCitationsE() {
        return citationsE;
    }

    /**
     * Gets the set of citations for the French text.
     *
     * @return  the set of citations for the French text.
     *
     * @see     #setCitationsE()
     */
    public Set<Citation> getCitationsF() {
        return citationsF;
    }

    /**
     * Sets the set of citations qualifying the English text.
     *
     * @param  newVal  the set of citations for the English text.
     *
     * @see    #getCitationsE()
     */
    public void setCitationsE(Set<Citation> newVal) {
        citationsE = newVal;
    }

    /**
     * Gets the set of citations qualifying the French text.
     *
     * @param  newVal  the set of citations for the French text.
     *
     * @see    #getCitationsF()
     */
    public void setCitationsF(Set<Citation> newVal) {
        citationsF = newVal;
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
        buffer.append(", citationsE: ");
        buffer.append(getCitationsE());
        buffer.append(", citationsF: ");
        buffer.append(getCitationsF());

        return buffer.toString();
    }
}
