package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Comparator;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*******************************************************************************
 * An object that represents a citation (a reference to a publication or part of
 * a publication as an authoritative source).
 *
 * @version  1.0
 */
abstract public class Citation extends PersistentObject implements Comparable,
    BibliographicalEntry {

    //~ Static fields/initializers ---------------------------------------------

    static protected final String SPACE = " ";
    static protected final String COMMA = ",";
    static protected final String PERIOD = ".";
    static protected final String COLON = ":";
    static protected final String EDITION_STRING = "ed";
    static protected final String SEMI_COLON = ";";
    static protected final String DOT_ZERO = ".0";

    // Patern that tries to extract the year from a string.
    static protected final Pattern yearExtractor = Pattern.compile("[^\\d]*([0-9]*[0-9]*).*"); 
    
    //~ Instance fields --------------------------------------------------------

    private String abridgedTitleE;
    private String abridgedTitleF;

    private String commentE;
    private String commentF;
    private final String[] EDITION_SUFFIXES = {
            "st", "nd", "rd", "th"
        };

    /**
     * This provides a formatted edition based on the edition number provided.
     *
     * @return  a formatted edition
     */
    private final String[] EDITIONS = {
            "1", "2", "3"
        };
    private String fullTextE;
    private String fullTextF;
    private String workAuthorE;
    private String workAuthorF;
    private Date workDate;
    private String workTitleE;
    private String workTitleF;
    private String workYearString;
    private String code;

    //~ public -----------------------------------------------------------------

    /**
     * Gets the type in the language appropriate for the Locale.
     *
     * @return  the locale-specific type.
     */
    abstract public String getType();

    /**
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param   o  the object to compare this to.
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ClassCastException  DOCUMENT ME!
     */
    public int compareByNameTo(Object o) throws ClassCastException {

        if (getWorkTitle() == null) {
            return 1;
        }

        if (o == null) {
            return -1;
        }

        if (((BibliographicalEntry) this).getValueInBibliographicalFormat()
                == null) {
            return 1;
        }

        if (((BibliographicalEntry) o).getValueInBibliographicalFormat()
                == null) {
            return -1;
        }

        // return getWorkTitle().compareTo(((Citation)o).getWorkTitle());
        return ((BibliographicalEntry) this).getValueInBibliographicalFormat()
            .compareTo(((BibliographicalEntry) o)
                .getValueInBibliographicalFormat());
    }

    /**
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param   o  the object to compare this to.
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ClassCastException  DOCUMENT ME!
     */
    public int compareTo(Object o) throws ClassCastException {

        if (getWorkTitle() == null) {
            return 1;
        }

        if (o == null) {
            return -1;
        }

        // If both available, first sort by year.
        if (!((workYearString == null)
                    && (((Citation) o).getWorkYearString() == null))) {

            if (workYearString == null) {
                return 1;
            }

            if (((Citation) o).getWorkYearString() == null) {
                return -1;
            }

            if (!((Citation) o).getWorkYearString().equals(
                        getWorkYearString())) {
                return ((Citation) o).getWorkYearString().compareTo(
                        getWorkYearString());
            }
        }

        // Next sort by bibliography name.
        if (((BibliographicalEntry) this).getValueInBibliographicalFormat()
                == null) {
            return 1;
        }

        if (((BibliographicalEntry) o).getValueInBibliographicalFormat()
                == null) {
            return -1;
        }

        return ((BibliographicalEntry) this).getValueInBibliographicalFormat()
            .compareTo(((BibliographicalEntry) o)
                .getValueInBibliographicalFormat());
            
    }

    /**
     * Gets the abridged title in the language appropriate for the Locale.
     *
     * @return  the locale-specific abridged title.
     */
    public String getAbridgedTitle() {

        if (isLanguageFrench()) {
            return getAbridgedTitleF();
        }

        return getAbridgedTitleE();
    }

    /**
     * Gets the year from the Abridge Title.  Uses a regex expression
     * to extract the numeric value from the Abridge Title.  We expect
     * that the Abridge Title will contain a 4 digit year number.  This
     * information is then used in sorting the citations on the 
     * monograph page.
     * 
     * @return the year from the Abridge Title.
     */
    public Integer getAbridgeTitleYear() {

        if (getAbridgedTitle() == null) {
            return null;
        }
        
        Matcher m = yearExtractor.matcher(getAbridgedTitle()); 
        if (m.find()) { 
            String yearString = (m.group(1));
            try {
                Integer year = Integer.valueOf(yearString);
                return year;
            } catch (java.lang.NumberFormatException e) {
                System.err.println("Error trying to get the year from string: " + getAbridgedTitle());
            }
        } 
        return null;
    }
    
    /**
     * Gets the abridgedTitleE.
     *
     * @return  the abridgedTitleE.
     */
    public String getAbridgedTitleE() {
        return abridgedTitleE;
    }

    /**
     * Gets the abridgedTitleF.
     *
     * @return  the abridgedTitleF.
     */
    public String getAbridgedTitleF() {
        return abridgedTitleF;
    }

    /**
     * Sets the code.  Used in the loading of Monograph Citations.  A Unique constraint was not added because normal citations currently do not persist their code.
     *
     * @return  the code.
     *
     * @see     #setCode()
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets the comment in the language appropriate for the Locale.
     *
     * @return  the locale-specific comment.
     */
    public String getComment() {

        if (isLanguageFrench()) {
            return getCommentF();
        }

        return getCommentE();
    }


    /**
     * Gets the comment in English.
     *
     * @return  the comment in English.
     *
     * @see     #setCommentE()
     */
    public String getCommentE() {
        return commentE;
    }

    /**
     * Gets the comment in French.
     *
     * @return  the comment in French.
     *
     * @see     #setCommentF()
     */
    public String getCommentF() {
        return commentF;
    }

    public String getEditionSuffix(String editionValue) {

        for (int i = 0; i < EDITIONS.length; i++) {

            if (EDITIONS[i].equals(editionValue.trim())) {
                return EDITION_SUFFIXES[i];
            }
        }

        return EDITION_SUFFIXES[EDITION_SUFFIXES.length - 1];
    }

    /**
     * Gets the full text title in the language appropriate for the Locale.
     *
     * @return  the locale-specific full text title.
     */
    public String getFullText() {

        if (isLanguageFrench()) {
            return getFullTextF();
        }

        return getFullTextE();
    }

    /**
     * Full Text is used by the monograph to store the entire reference string
     * into a single block of text. This was implemented to facilitate the
     * loading of the many monograph reference objects.
     *
     * <p>Gets the fullTextE.
     *
     * @return  the fullTextE.
     */
    public String getFullTextE() {
        return fullTextE;
    }

    /**
     * Gets the fullTextF.
     *
     * @return  the fullTextF.
     */
    public String getFullTextF() {
        return fullTextF;
    }

    /**
     * Gets the English short description used to represent this object in drop
     * down lists and other various interfaces.
     *
     * @return  the English specific short description.
     */
    public String getShortDescriptionE() {
        return getWorkTitle();
    }

    /**
     * Gets the English short description used to represent this object in drop
     * down lists and other various interfaces.
     *
     * @return  the French specific short description.
     */
    public String getShortDescriptionF() {
        return getWorkTitle();
    }

    /**
     * This returns this object as a bibliographical entry for display purposes.
     * Note that this should return the full text if it is populated.
     *
     * @return  this object as a bibliographical entry for display.
     */
    public abstract String getValueInBibliographicalFormat();

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
        buffer.append(", workAuthorE: ");
        buffer.append(getWorkAuthorE());
        buffer.append(", workAuthorF: ");
        buffer.append(getWorkAuthorF());
        buffer.append(", workTitleE: ");
        buffer.append(getWorkTitleE());
        buffer.append(", workTitleF: ");
        buffer.append(getWorkTitleF());
        buffer.append(", workDate: ");
        buffer.append(getWorkDate());
        buffer.append(", workYearString: ");
        buffer.append(getWorkYearString());

        if (getCommentE() != null) {
            buffer.append(", commentE: ");
            buffer.append(getCommentE());
        }

        if (getCommentF() != null) {
            buffer.append(", commentF: ");
            buffer.append(getCommentF());
        }

        return buffer.toString();
    }

    /**
     * Gets the WorkAuthor in the language appropriate for the Locale.
     *
     * @return  the locale-specific book work author.
     */
    public String getWorkAuthor() {

        if (isLanguageFrench()) {
            return getWorkAuthorF();
        }

        return getWorkAuthorE();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getWorkAuthorE() {
        return workAuthorE;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getWorkAuthorF() {
        return workAuthorF;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Date getWorkDate() {
        return workDate;
    }

    /**
     * Gets the WorkAuthor in the language appropriate for the Locale.
     *
     * @return  the locale-specific book work author.
     */
    public String getWorkTitle() {

        if (isLanguageFrench()) {
            return getWorkTitleF();
        }

        return getWorkTitleE();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getWorkTitleE() {
        return workTitleE;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getWorkTitleF() {
        return workTitleF;
    }

    /**
     * Gets a simplified version of the work date, when only part of it is
     * known.
     *
     * @return  a simplified version of the work date, when only part of it is
     *          known.
     *
     * @see     #setWorkYearString()
     */
    public String getWorkYearString() {

        if ((workYearString != null) && workYearString.endsWith(DOT_ZERO)) {
            workYearString = workYearString.substring(0,
                    workYearString.length() - 2);
        }

        return workYearString;
    }

    /**
     * Sets the abridgedTitleE.
     *
     * @param  newValue  abridgedTitleE.
     */
    public void setAbridgedTitleE(String newValue) {
        abridgedTitleE = newValue;
    }

    /**
     * Sets the abridgedTitleF.
     *
     * @param  newValue  abridgedTitleF.
     */
    public void setAbridgedTitleF(String newValue) {
        abridgedTitleF = newValue;
    }

    /**
     * Sets the code.  Used in the loading of Monograph Citations.
     *
     * @param  newValue  code.
     */
    public void setCode(String newValue) {
        code = newValue;
    }

    /**
     * Sets the comment in English.
     *
     * @param  newVal  the comment in English
     *
     * @see    #getCommentE()
     */
    public void setCommentE(String newVal) {
        commentE = newVal;
    }

    /**
     * Gets the comment in French.
     *
     * @param  newVal  the comment in French
     *
     * @see    #getCommentF()
     */
    public void setCommentF(String newVal) {
        commentF = newVal;
    }

    /**
     * Sets the fullTextE.
     *
     * @param  newValue  fullTextE.
     */
    public void setFullTextE(String newValue) {
        fullTextE = newValue;
    }

    /**
     * Sets the fullTextF.
     *
     * @param  newValue  fullTextF.
     */
    public void setFullTextF(String newValue) {
        fullTextF = newValue;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  newVal
     */
    public void setWorkAuthorE(String newVal) {
        workAuthorE = newVal;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  newVal
     */
    public void setWorkAuthorF(String newVal) {
        workAuthorF = newVal;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  newVal
     */
    public void setWorkDate(Date newVal) {
        workDate = newVal;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  newVal
     */
    public void setWorkTitleE(String newVal) {
        workTitleE = newVal;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  newVal
     */
    public void setWorkTitleF(String newVal) {
        workTitleF = newVal;
    }

    /**
     * Gets a simplified version of the work date, when only part of it is
     * known.
     *
     * @param  value  DOCUMENT ME!
     *
     * @see    #getWorkYearString()
     */
    public void setWorkYearString(String value) {
        workYearString = value;
    }

    //~ Inner Classes ----------------------------------------------------------

    // +++ Inner Classes
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /*******************************************************************************
     * An implementation of Comparator that allows these objects to be sorted
     * using their English names.
     */
    static public class NameComparator implements Comparator<BibliographicalEntry> {

        /**
         * Compares the passed objects for order. Returns a negative integer,
         * zero, or a positive integer as the first object is less than, equal
         * to, or greater than the second.
         *
         * @param   o1  the first object to be compared.
         * @param   o2  the second object to be compared.
         *
         * @return  DOCUMENT ME!
         *
         * @throws  ClassCastException  DOCUMENT ME!
         */
        public int compare(BibliographicalEntry o1, BibliographicalEntry o2) throws ClassCastException {

            if (o1 == null) {
                return 1;
            }

            if (o2 == null) {
                return -1;
            }

            if (((BibliographicalEntry) o1).getValueInBibliographicalFormat()
                    == null) {
                return 1;
            }

            if (((BibliographicalEntry) o2).getValueInBibliographicalFormat()
                    == null) {
                return -1;
            }

            return ((BibliographicalEntry) o1).getValueInBibliographicalFormat()
                .toLowerCase().compareTo(((BibliographicalEntry) o2)
                    .getValueInBibliographicalFormat().toLowerCase());
        }
    }

    /*******************************************************************************
     * An implementation of Comparator that allows these objects to be sorted
     * using their English names.
     */
    static public class AbridgedTitleComparator implements Comparator {

        /**
         * Compares the passed objects for order. Returns a negative integer,
         * zero, or a positive integer as the first object is less than, equal
         * to, or greater than the second.
         *
         * @param   o1  the first object to be compared.
         * @param   o2  the second object to be compared.
         *
         * @return  DOCUMENT ME!
         *
         * @throws  ClassCastException  DOCUMENT ME!
         */
        public int compare(Object o1, Object o2) throws ClassCastException {

            if ((o1 == null) || (((Citation) o1).getAbridgedTitle() == null)) {
                return 1;
            }

            if (o2 == null) {
                return -1;
            }
            
            String abridgedTitle1 = ((Citation) o1).getAbridgedTitle();
            String abridgedTitle2 = ((Citation) o2).getAbridgedTitle();
            if (abridgedTitle1 == null) {
                return 1;
            }

            if (abridgedTitle2 == null) {
                return -1;
            }

            //return ((Citation) o1).getAbridgedTitle().toLowerCase().compareTo(
            //        ((Citation) o2).getAbridgedTitle().toLowerCase());
            return StringComparator.compare(abridgedTitle1,abridgedTitle2);

        }
    }

    
    
    // +++ Inner Classes ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /***************************************************************************
     * An implementation of Comparator that allows these objects to be sorted
     * using their English names.
     */
    public static class CitationComparator implements Comparator<Citation> {
        /***********************************************************************
         * Used to order citations by their date.  The highest date should be
         * listed first.
         * 
         * Compares the passed objects for order. Returns a negative integer,
         * zero, or a positive integer as the first object is less than, equal
         * to, or greater than the second.
         * 
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         */
        public int compare(Citation o1, Citation o2) throws ClassCastException {
            if (o1 == null || o1.getAbridgeTitleYear() == null) {
                return 1;
            }
            if (o2 == null) {
                return -1;
            }
            // If the same year, compare on title.
            if (o2.getAbridgeTitleYear().equals(
                   o1.getAbridgeTitleYear())) {
                return o1.getAbridgedTitle().compareTo(o2.getAbridgedTitle());
            }
            return o2.getAbridgeTitleYear().compareTo(
                   o1.getAbridgeTitleYear());
        }
    }
    
}
