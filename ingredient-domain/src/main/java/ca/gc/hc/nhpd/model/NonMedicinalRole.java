package ca.gc.hc.nhpd.model;

import java.util.Set;

/*******************************************************************************
 * An object that, when present, indicates that an ingredient can be used for the
 * defined non-medical purposes as long as it follows the defined non-medical
 * restrictions.
 * @version 1.0
 */
public class NonMedicinalRole extends IngredientRole {
    private static final String TYPE_E = "Non-medicinal";
    private static final String TYPE_F = "Non médicinal";
    private Set<NonMedicinalPurpose> purposes;

    /***************************************************************************
     */
    public Set<NonMedicinalPurpose> getPurposes(){
        return purposes;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setPurposes(Set<NonMedicinalPurpose> newVal){
        purposes = newVal;
    }

    /***************************************************************************
     * Gets the type in the language appropriate for the Locale from the class
     * constants.
     * @return the locale-specific type.
     */
    public String getType(){
        if (isLanguageFrench()) {
            return TYPE_F;
        }
        return TYPE_E;
    }

    /***************************************************************************
     * Gets the English short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the English specific short description.
     */
    public String getShortDescriptionE() {
        return TYPE_E;
    }

    /***************************************************************************
     * Gets the English short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the French specific short description.
     */
    public String getShortDescriptionF() {
        return TYPE_F;
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
        buffer.append(", purposes: ");
        buffer.append(getPurposes());
        
        return buffer.toString();
    }
}