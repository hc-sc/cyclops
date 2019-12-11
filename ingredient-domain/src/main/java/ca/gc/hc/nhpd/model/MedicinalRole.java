package ca.gc.hc.nhpd.model;

import java.util.Set;

/*******************************************************************************
 * An object that, when present, indicates that an ingredient can be used for
 * medical purposes as long as it follows the defined medical restrictions.
 */
public class MedicinalRole extends IngredientRole {
    private static final String TYPE_E = "Medicinal";
    private static final String TYPE_F = "Médicinal";
    private Rationale rationale;
    private Set<ManufacturingProcess> manufacturingProcesses;

    /***************************************************************************
     */
    public Set<ManufacturingProcess> getManufacturingProcesses(){
        return manufacturingProcesses;
    }

    /***************************************************************************
     * Gets the rationale.
     * @return the rationale.
     */
    public Rationale getRationale(){
    	return rationale;
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
     * @param newVal
     */
    public void setManufacturingProcesses(Set<ManufacturingProcess> newVal){
        manufacturingProcesses = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setRationale(Rationale newVal){
        rationale = newVal;
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
        if ( getRationale() != null ) {
            buffer.append( getRationale() );
        } else {
            buffer.append("null");
        }
        buffer.append(", manufacturingProcesses: ");
        buffer.append(getManufacturingProcesses());
        
        return buffer.toString();
    }
}