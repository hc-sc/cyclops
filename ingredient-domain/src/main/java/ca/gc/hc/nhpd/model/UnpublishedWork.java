package ca.gc.hc.nhpd.model;

/*******************************************************************************
 * An object that represents a web site that may be cited as a source of
 * information.
 */
public class UnpublishedWork extends Citation implements BibliographicalEntry {

    //~ Static fields/initializers ---------------------------------------------

    static private final String TYPE_E = "Unpublished Work";
    static private final String TYPE_F = "Unpublished Work (fr)";

    //~ public -----------------------------------------------------------------

    /**
     * Gets the type in the language appropriate for the Locale.
     *
     * @return  the locale-specific type.
     */
    public String getType() {

        if (isLanguageFrench()) {
            return TYPE_F;
        }

        return TYPE_E;
    }

    /**
     * This returns this object as a bibliographical entry for display purposes.
     * Note that this returns the "full text" if it is populated. Otherwise, it
     * creates the string from its components.
     * @return  this object as a bibliographical entry for display.
     */
    @Override
    public String getValueInBibliographicalFormat() {
        if (getFullText() != null && getFullText().length() > 0) {
            return getFullText();
        }
        
        StringBuffer buffer = new StringBuffer();

        if (getFullText() != null) {
            return getFullText();
        }

        if (getWorkAuthor() != null) {
            buffer.append(getWorkAuthor());
            buffer.append(PERIOD);
            buffer.append(SPACE);
        }

        if (getWorkTitle() != null) {
            buffer.append(getWorkTitle());
            buffer.append(PERIOD);
            buffer.append(SPACE);
        }

        /*
         * if ( getJournal() != null ) { buffer.append( getJournal() );
         * buffer.append( PERIOD ); buffer.append( SPACE ); }
         */
        if (getWorkYearString() != null) {
            buffer.append(getWorkYearString());
            buffer.append(SPACE);
        }
        // buffer.append( PERIOD );

        return buffer.toString();
    }

    /**
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Over-ridden to add additional values to the
     * end.
     *
     * @return  provides a string representation of this object.
     */
    public String getValuesAsString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(super.getValuesAsString());

        return buffer.toString();
    }
}
