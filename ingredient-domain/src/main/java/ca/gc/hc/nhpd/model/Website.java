package ca.gc.hc.nhpd.model;

import java.util.Date;

/*******************************************************************************
 * An object that represents a web site that may be cited as a source of
 * information.
 */
public class Website extends Citation implements BibliographicalEntry {
    private static final String TYPE_E = "Website";
    private static final String TYPE_F = "Site Web";
    private Date accessDate;
    private String siteTitleE;
    private String siteTitleF;
    private String urlE;
    private String urlF;

    /***************************************************************************
     */
    public Date getAccessDate(){
        return accessDate;
    }

    /***************************************************************************
    * Gets the address part of the url. Returns the url if it can't figure it out.
     */
    public String getSiteAddress(){
        String address = getUrl();

        if (address != null && address.length() > 0) {
            int startPos = address.indexOf("//") + 2;
            
            if (startPos >= 2) {
                int endPos = address.indexOf("/", startPos);
                
                if (endPos != -1) {
                    return address.substring(startPos, endPos);
                }
                return address.substring(startPos);
            }
        }
        
        return address;
    }

    /***************************************************************************
     * Gets the site title in the language appropriate for the Locale.
     * @return the locale-specific name.
     */
    public String getSiteTitle(){
        if (isLanguageFrench()) {
            return getSiteTitleF();
        }
        return getSiteTitleE();
    }
    
    /***************************************************************************
     */
    public String getSiteTitleE(){
        return siteTitleE;
    }

    /***************************************************************************
     */
    public String getSiteTitleF(){
        return siteTitleF;
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
     * Gets the url in the language appropriate for the Locale.
     * @return the locale-specific name.
     */
    public String getUrl(){
        if (isLanguageFrench()) {
            return getUrlF();
        }
        return getUrlE();
    }

    /***************************************************************************
     */
    public String getUrlE(){
        return urlE;
    }

    /***************************************************************************
     */
    public String getUrlF(){
        return urlF;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setAccessDate(Date newVal){
        accessDate = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setSiteTitleE(String newVal){
        siteTitleE = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setSiteTitleF(String newVal){
        siteTitleF = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setUrlE(String newVal){
        urlE = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setUrlF(String newVal){
        urlF = newVal;
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
        buffer.append(", siteTitleE: ");
        buffer.append(getSiteTitleE());
        buffer.append(", siteTitleF: ");
        buffer.append(getSiteTitleF());
        buffer.append(", urlE: ");
        buffer.append(getUrlE());
        buffer.append(", urlF: ");
        buffer.append(getUrlF());
        buffer.append(", accessDate: ");
        buffer.append(getAccessDate());
        
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
            // buffer.append( PERIOD );
            
            return buffer.toString();
        }
}