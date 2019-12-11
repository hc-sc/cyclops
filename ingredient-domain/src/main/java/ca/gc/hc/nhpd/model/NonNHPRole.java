package ca.gc.hc.nhpd.model;


/*******************************************************************************
 * An object that indicates that an ingredient is not known  to be natural health 
 * product 
 * @version 1.2
 */
public class NonNHPRole extends IngredientRole {
    private static final String TYPE_E = "Non-NHP";
    private static final String TYPE_F = "Non-PSN";
    private Rationale rationale;
    private Restriction restriction;

    /***************************************************************************
     */
    public Rationale getRationale(){
        return rationale;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setRationale(Rationale newVal){
        rationale = newVal;
    }
    /***************************************************************************
         * Gets the Restriction that apply to this Role. Returns null
         * if there is none.
         */
        public Restriction getRestriction(){
            return restriction;
        }

        /***************************************************************************
         * @param newVal
         */
        public void setRestriction(Restriction newVal){
            restriction = newVal;
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
        buffer.append(", rationale: ");
        if (getRationale() != null) {
              buffer.append(getRationale().toString());
               } else {
                   buffer.append("null");
               }
         buffer.append(", restriction: ");
        if (getRestriction()!= null) {
                  buffer.append(getRestriction().toString());
                      } else {
                          buffer.append("null");
                      }
        
        return buffer.toString();
    }
}