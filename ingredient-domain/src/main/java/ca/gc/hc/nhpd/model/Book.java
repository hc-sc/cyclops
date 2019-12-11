package ca.gc.hc.nhpd.model;

/*******************************************************************************
 * An object that represents a book that may be cited as a source of information.
 * @version 1.0
 */
public class Book extends PublishedWork implements BibliographicalEntry {
    private static final String TYPE_E = "Book";
    private static final String TYPE_F = "Livre";
    private String edition;

    /***************************************************************************
     */
    public String getEdition(){
        if ( edition != null 
            && edition.endsWith( DOT_ZERO )) {
            edition = edition.substring( 0, edition.length() - 2);
        }
        return edition;
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
    public void setEdition(String newVal){
        edition = newVal;
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
        buffer.append(", edition: ");
        buffer.append(getEdition());
        
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
        
        if (getFullText() != null) {
            return getFullText();
        }
        
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
        if ( getEdition() != null ) {
            buffer.append( getEdition() );
            buffer.append( getEditionSuffix(getEdition()));
            buffer.append( SPACE );
            buffer.append( EDITION_STRING );
            buffer.append( PERIOD );
            buffer.append( SPACE );
        }
        if ( getPlace() != null ) {
            buffer.append( getPlace() );
            buffer.append( COLON );
            buffer.append( SPACE );
        }
        if ( getPublisher() != null ) {
            buffer.append( getPublisher() );
            // buffer.append( COLON );
            // buffer.append( SPACE );
            if ( getWorkYearString() != null ) {
                buffer.append( SEMI_COLON );
                buffer.append( SPACE );
                buffer.append( getWorkYearString() );
            }
        }
        buffer.append( PERIOD );
        return buffer.toString();
    }
}