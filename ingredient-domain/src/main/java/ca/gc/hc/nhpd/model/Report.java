package ca.gc.hc.nhpd.model;

/*******************************************************************************
 * An object that represents a report that may be cited as a source of information.
 */
public class Report extends PublishedWork implements BibliographicalEntry {
    private static final String TYPE_E = "Report";
    private static final String TYPE_F = "Rapport";
    private String reportNumber;

    /***************************************************************************
     */
    public String getReportNumber(){
        return reportNumber;
    }

    /***************************************************************************
     * Gets the type in the language appropriate for the Locale.
     * @return the locale-specific type.
     */
    public String getType(){
        if (isLanguageFrench()) {
            return TYPE_F;
        }
        return TYPE_E;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setReportNumber(String newVal){
        reportNumber = newVal;
    }

    /***************************************************************************
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Over-ridden to add additional values to the
     * end.
     */
    public String getValuesAsString(){
        StringBuffer buffer = new StringBuffer();
        
        buffer.append(super.getValuesAsString());
        buffer.append(", reportNumber: ");
        buffer.append(getReportNumber());
        
        return buffer.toString();
    }
    
    /**
         * This returns this object as a bibliographical entry for display purposes.
     * Note that this returns the "full text" if it is populated. Otherwise, it
     * creates the string from its components.
         * @return this object as a bibliographical entry for display.
         */
    @Override
        public String getValueInBibliographicalFormat(){
        if (getFullText() != null && getFullText().length() > 0) {
            return getFullText();
        }
        
            StringBuffer buffer = new StringBuffer();
            if ( getWorkAuthor() != null ) {
                buffer.append( getWorkAuthor() );
                buffer.append( PERIOD );
                buffer.append( SPACE );
            }
            if ( getWorkTitle() != null ) {
                buffer.append( getWorkTitle() );
                buffer.append( PERIOD );
                buffer.append( SPACE );
            }
            /*
            if ( getJournal() != null ) {
                buffer.append( getJournal() );
                buffer.append( PERIOD );
                buffer.append( SPACE );
            }
            */
            if ( getWorkYearString() != null ) {
                buffer.append( getWorkYearString() );
                buffer.append( SPACE );
            }
            /*
            if ( getVolume() != null ) {
                buffer.append( getVolume() );
                buffer.append( COLON );
                buffer.append( SPACE );
            }
            if ( getPageNumbers() != null ) {
                buffer.append( getVolume() );
            }
            */
            buffer.append( PERIOD );
            return buffer.toString();
        }
}