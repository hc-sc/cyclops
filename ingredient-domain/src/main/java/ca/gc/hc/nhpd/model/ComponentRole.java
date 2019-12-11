package ca.gc.hc.nhpd.model;

/*******************************************************************************
 * An object that, when present, indicates that an ingredient can be used as an
 * Herbal Component.
 */
public class ComponentRole extends IngredientRole {
    private static final String TYPE_E = "Component";
    private static final String TYPE_F = "Composant";

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
}