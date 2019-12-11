package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringUtils;

/*******************************************************************************
 * An object that represents a restriction in the way a medicinal ingredient is
 * to be used.
 * @version 1.0
 */
public class MedicinalRestriction extends BasicRestriction {
    private String failureMessageE;
    private String failureMessageF;
    private MedicinalRole role;

    /***************************************************************************
     * Gets the failure message in the language appropriate for the Locale.
     * @return the locale-specific failure message.
     */
    public String getFailureMessage(){
        if (isLanguageFrench()) {
            return getFailureMessageF();
        }
        return getFailureMessageE();
    }

    /***************************************************************************
     * Gets the failure message in English.
     * @return the failure message in English.
     * @see #setFailureMessageE()
     */
    public String getFailureMessageE(){
        return failureMessageE;
    }

    /***************************************************************************
     * Gets the failure message in French.
     * @return the failure message in French.
     * @see #setFailureMessageF()
     */
    public String getFailureMessageF(){
        return failureMessageF;
    }

    /***************************************************************************
     */
    public MedicinalRole getRole(){
        return role;
    }

    /***************************************************************************
     * Sets the failure message in English.
     * @param newVal the failure message in English
     * @see #getFailureMessageE()
     */
    public void setFailureMessageE(String newVal){
        failureMessageE = newVal;
    }

    /***************************************************************************
     * Sets the failure message in French.
     * @param newVal the failure message in French
     * @see #getFailureMessageF()
     */
    public void setFailureMessageF(String newVal){
        failureMessageF = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setRole(MedicinalRole newVal){
        role = newVal;
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
        buffer.append(", failureMessageE: ");
        buffer.append(getFailureMessageE());
        buffer.append(", failureMessageF: ");
        buffer.append(getFailureMessageF());
        buffer.append(", role: ");
        if (getRole() != null) {
            buffer.append(getRole().getType());
        } else {
            buffer.append("null");
        }
        
        return buffer.toString();
    }
}