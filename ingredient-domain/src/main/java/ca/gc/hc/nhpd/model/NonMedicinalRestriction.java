package ca.gc.hc.nhpd.model;

import java.util.Set;

import ca.gc.hc.nhpd.util.StringUtils;

/*******************************************************************************
 * An object that represents a restriction in the way a non- medicinal ingredient
 * is to be used.
 */
public class NonMedicinalRestriction extends BasicRestriction {
    private Set<NonMedicinalPurpose> allowedPurposes;
    private Set<ProductType> productTypes;
    private NonMedicinalRole role;
    private Boolean safetyConcern;
    
    /***************************************************************************
     */
    public Set<NonMedicinalPurpose> getAllowedPurposes(){
        return allowedPurposes;
    }

    /***************************************************************************
     */
    public Set<ProductType> getProductTypes(){
        return productTypes;
    }

    /***************************************************************************
     */
    public NonMedicinalRole getRole(){
        return role;
    }

    /**
     * Getter that returns the boolean safety concern flag.  Gets whether there
     * is a safety concern if this is taken in large amounts.  This is only 
     * applicable to Non-Medicinal ingredients.  Medicinal Ingredients are 
     * considered active and are managed via limits.  Ethanol is the NMI 
     * exception to this rule where Ethanol needs to raise safety concerns
     * when used in large amounts.
     * 
     * Example:  Ethanol
     *
     * @return  boolean flag indicating if the restriction is a safety concern.
     */
    public Boolean getSafetyConcern() {
        return safetyConcern;
    }

    /**
     * Getter used by Hibernate that returns the string flag indicating if 
     * the restriction is a safety concern.
     *
     * Example:  Ethanol
     * 
     * @return  String flag indicating if the restriction is a safety concern.
     */
    public String getSafetyConcernAsString() {
        return StringUtils.booleanToString(safetyConcern);
    }
    
    /***************************************************************************
     * @param newVal
     */
    public void setAllowedPurposes(Set<NonMedicinalPurpose> newVal){
        allowedPurposes = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setProductTypes(Set<ProductType> newVal){
        productTypes = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setRole(NonMedicinalRole newVal){
        role = newVal;
    }

    /**
     * Setter indicating if this restriction is a safety concern.
     *
     * @param  atfRequired  boolean flag indicating if this restriction is a safety 
     *                      concern.
     */
    public void setSafetyConcern(Boolean safetyConcern) {
        this.safetyConcern = safetyConcern;
    }

    /**
     * Setter used by Hibernate indicating if this restriction is a safety concern.
     *
     * @param  atfRequired  string flag indicating if this restriction is a 
     *                      safety concern.
     */
    public void setSafetyConcernAsString(String safetyConcern) {
        this.safetyConcern = StringUtils.stringToBoolean(safetyConcern);
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
        buffer.append(", allowedPurposes: ");
        buffer.append(getAllowedPurposes());
        buffer.append(", productTypes: ");
        buffer.append(getProductTypes());
        buffer.append(", role: ");
        if (getRole() != null) {
            buffer.append(getRole().getType());
        } else {
            buffer.append("null");
        }
        
        return buffer.toString();
    }
}