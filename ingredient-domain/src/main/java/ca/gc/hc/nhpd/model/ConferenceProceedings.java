package ca.gc.hc.nhpd.model;

import java.util.Date;

/*******************************************************************************
 * An object that represents conference proceedings that may be cited as a source
 * of information.
 * @version 1.0
 */
public class ConferenceProceedings extends PublishedWork {
    private static final String TYPE_E = "Conference Proceedings";
    private static final String TYPE_F = "Compte-rendus de conférence";
    private String editor;
    private Date meetingDate;
    private String meetingPlace;
    private String pageNumbers;
    private String proceedingsTitle;
    private String volume;

    /***************************************************************************
     */
    public String getEditor(){
        return editor;
    }

    /***************************************************************************
     */
    public Date getMeetingDate(){
        return meetingDate;
    }

    /***************************************************************************
     */
    public String getMeetingPlace(){
        return meetingPlace;
    }

    /***************************************************************************
     */
    public String getPageNumbers(){
        return pageNumbers;
    }

    /***************************************************************************
     */
    public String getProceedingsTitle(){
        return proceedingsTitle;
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
     */
    public String getVolume(){
        return volume;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setEditor(String newVal){
        editor = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setMeetingDate(Date newVal){
        meetingDate = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setMeetingPlace(String newVal){
        meetingPlace = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setPageNumbers(String newVal){
        pageNumbers = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setProceedingsTitle(String newVal){
        proceedingsTitle = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setVolume(String newVal){
        volume = newVal;
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
        buffer.append(", editor: ");
        buffer.append(getEditor());
        buffer.append(", meetingDate: ");
        buffer.append(getMeetingDate());
        buffer.append(", meetingPlace: ");
        buffer.append(getMeetingPlace());
        buffer.append(", pageNumbers: ");
        buffer.append(getPageNumbers());
        buffer.append(", proceedingsTitle: ");
        buffer.append(getProceedingsTitle());
        buffer.append(", volume: ");
        buffer.append(getVolume());
        
        return buffer.toString();
    }
}