package ca.gc.hc.nhpd.model;

/*******************************************************************************
 * This interface is implemented by Objects that have names so that they may
 * be displayed, as well as sorted, generically. Useful when displaying a
 * collection of otherwise heterogeneous objects, since toString() is already
 * being used for debugging purposes.
 */
public interface NamedObject {
    /**
     * Gets the name in the language appropriate for the Locale.
     *
     * @return  the locale-specific name.
     */
    public String getName();

    /**
     * Gets the name in English.
     *
     * @return  the name in English.
     *
     * @see     #setNameE()
     */
    public String getNameE();

    /**
     * Gets the name in French.
     *
     * @return  the name in French.
     *
     * @see     #setNameF()
     */
    public String getNameF();
}
