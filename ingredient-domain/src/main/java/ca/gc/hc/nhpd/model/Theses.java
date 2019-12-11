package ca.gc.hc.nhpd.model;

/*******************************************************************************
 * An object that represents a theses that may be cited as a source of information.
 */
public class Theses extends Citation {
    private static final String TYPE_E = "Theses";
    private static final String TYPE_F = "Thèses";
    private String degree;
    private String place;
    private String university;

    /***************************************************************************
     */
    public String getDegree(){
        return degree;
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
    public String getPlace(){
        return place;
    }

    /***************************************************************************
     */
    public String getUniversity(){
        return university;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setDegree(String newVal){
        degree = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setPlace(String newVal){
        place = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setUniversity(String newVal){
        university = newVal;
    }

    /**
     * This returns this object as a bibliographical entry for display purposes.
     * Note that this will only return the "full text" if it is populated, since
     * it does not know how to create a string from its components.
     * @return  this object as a bibliographical entry for display.
     */
    @Override
    public String getValueInBibliographicalFormat() {
        return getFullText();
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
        buffer.append(", place: ");
        buffer.append(getPlace());
        buffer.append(", degree: ");
        buffer.append(getDegree());
        buffer.append(", university: ");
        buffer.append(getUniversity());
        
        return buffer.toString();
    }
}