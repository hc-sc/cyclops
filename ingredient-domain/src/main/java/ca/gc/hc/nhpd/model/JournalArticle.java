package ca.gc.hc.nhpd.model;

/*******************************************************************************
 * An object that represents a journal article that may be cited as a source of
 * information.
 */
public class JournalArticle extends Citation implements BibliographicalEntry {

    //~ Static fields/initializers ---------------------------------------------

    static private final String TYPE_E = "Journal Article";
    static private final String TYPE_F = "Article de revue";

    //~ Instance fields --------------------------------------------------------

    private Journal journal;
    private String pageNumbers;
    private String part;
    private String volume;

    //~ public -----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Journal getJournal() {
        return journal;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getPageNumbers() {
        return pageNumbers;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getPart() {
        return part;
    }

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

        if ((getJournal() != null) && (getJournal().getTitles() != null)) {

            // Remove any trailling period from the title.
            String title = getJournal().getTitles();

            if (title.endsWith(PERIOD)) {
                title = title.substring(0, title.length() - 1);
            }
            buffer.append(title);
            buffer.append(PERIOD);
            buffer.append(SPACE);
        }

        if (getWorkYearString() != null) {
            buffer.append(getWorkYearString());
            buffer.append(SPACE);
        }

        if (getVolume() != null) {
            buffer.append(getVolume());
            buffer.append(COLON);
            buffer.append(SPACE);
        }

        if (getPageNumbers() != null) {
            buffer.append(getPageNumbers());
        }

        buffer.append(PERIOD);

        return buffer.toString();
    }

    /**
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Over-ridden to add additional values to the
     * end.
     *
     * @return  DOCUMENT ME!
     */
    public String getValuesAsString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(super.getValuesAsString());
        buffer.append(", journal: ");
        buffer.append(getJournal());
        buffer.append(", pageNumbers: ");
        buffer.append(getPageNumbers());
        buffer.append(", part: ");
        buffer.append(getPart());
        buffer.append(", volume: ");
        buffer.append(getVolume());

        return buffer.toString();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getVolume() {
        return volume;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  newVal
     */
    public void setJournal(Journal newVal) {
        journal = newVal;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  newVal
     */
    public void setPageNumbers(String newVal) {
        pageNumbers = newVal;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  newVal
     */
    public void setPart(String newVal) {
        part = newVal;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  newVal
     */
    public void setVolume(String newVal) {
        volume = newVal;
    }

}
