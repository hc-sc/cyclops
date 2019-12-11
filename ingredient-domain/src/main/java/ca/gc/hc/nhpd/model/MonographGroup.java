package ca.gc.hc.nhpd.model;

import java.util.Set;

/*******************************************************************************
 * This class is a "virtual" published monograph. Refer to the Monograph object
 * for a complete description as to what that is. <br/>
 *
 * This contains a group of Monograph objects (although in most cases, it  only
 * has one). The group as a whole represents the published monograph. The
 * published monograph's contents may be separated into smaller components
 * (Monograph objects) for ease of implementation. Often the child
 * Monograph objects are distinguished by different routes of administration.
 */
public class MonographGroup extends PersistentObject {

    //~ Instance fields --------------------------------------------------------

    private Set<Monograph> monographs;
    private String nameE;
    private String nameF;

    //~ public -----------------------------------------------------------------

    /**
     * Gets the monographs associated with this MonographGroup.
     *
     * @return  the set of monographs associated with this MonographGroup.
     */
    public Set<Monograph> getMonographs() {
        return monographs;
    }


    /**
     * Gets the name in the language appropriate for the Locale.
     *
     * @return  the locale-specific name.
     */
    public String getName() {

        if (isLanguageFrench()) {
            return getNameF();
        }

        return getNameE();
    }

    /**
     * Gets the name in English.
     *
     * @return  the name in English.
     *
     * @see     #setNameE()
     */
    public String getNameE() {
        return nameE;
    }

    /**
     * Gets the name in French.
     *
     * @return  the name in French.
     *
     * @see     #setNameF()
     */
    public String getNameF() {
        return nameF;
    }

    /**
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Over-ridden to add additional values to the
     * end.
     *
     * @return  this object as a String
     */
    @Override
    public String getValuesAsString() {
        StringBuilder buffer = new StringBuilder();

        buffer.append(super.getValuesAsString());
        buffer.append(", nameE: ");
        buffer.append(getNameE());
        buffer.append(", monographs: ");
        buffer.append(getMonographs());

        return buffer.toString();
    }

    /**
     * Sets the monographs associated with this MonographGroup.
     *
     * @param  value  a set of Monograph objects
     * 
     * @see #getMonographs()
     */
    public void setMonographs(Set<Monograph> value) {
        monographs = value;
    }

    /**
     * Gets the name in English.
     *
     * @param  newVal  the name in English
     *
     * @see    #setNameE()
     */
    public void setNameE(String newVal) {
        nameE = newVal;
    }

    /**
     * Sets the name in French.
     *
     * @param  newVal  the name in French
     *
     * @see    #getNameF()
     */
    public void setNameF(String newVal) {
        nameF = newVal;
    }
}
