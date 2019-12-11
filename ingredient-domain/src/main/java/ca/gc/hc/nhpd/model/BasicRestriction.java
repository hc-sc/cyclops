package ca.gc.hc.nhpd.model;

import java.util.Set;

/*******************************************************************************
 * An object that represents a restriction in the way an ingredient is to be
 * used. Note that this should be called Restriction, however since it is not
 * abstract. Hibernate requires us to make Restriction an interface and this to
 * be a concrete implementation so that it can support subclassing (Hibernate
 * requires the base to be an interface or abstract class, since instances
 * cannot be created).
 * @see Restriction
 */
public class BasicRestriction extends PersistentObject implements Restriction, Comparable {
    private Double lowerLimit;
    private Reference reference;
    private RouteOfAdministration routeOfAdministration;
    private RestrictionType type;
    private Units units;
    private Double upperLimit;
    private Set<Citation> citations;
    private String detailE;
    private String detailF;
    
    public void setCitations( Set<Citation> value ) { citations = value; }
    public Set<Citation> getCitations() { return citations; }
    
    /***************************************************************************
     * Gets the detail in the language appropriate for the Locale.
     * @return the locale-specific detail.
     */
    public String getDetail(){
        if (isLanguageFrench()) {
            return getDetailF();
        }
        return getDetailE();
    }

    public void setDetailE( String value ) { detailE = value; }
    public String getDetailE() { return detailE; }
    
    public void setDetailF( String value ) { detailF = value; }
    public String getDetailF() { return detailF; }
    
    
    /***************************************************************************
     */
    public Double getLowerLimit(){
        return lowerLimit;
    }

    /***************************************************************************
     */
    public Reference getReference(){
        return reference;
    }

    /***************************************************************************
     */
    public RouteOfAdministration getRouteOfAdministration(){
        return routeOfAdministration;
    }

    /***************************************************************************
     */
    public RestrictionType getType(){
        return type;
    }

    /***************************************************************************
     */
    public Units getUnits(){
        return units;
    }

    /***************************************************************************
     */
    public Double getUpperLimit(){
        return upperLimit;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setLowerLimit(Double newVal){
        lowerLimit = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setReference(Reference newVal){
        reference = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setRouteOfAdministration(RouteOfAdministration newVal){
        routeOfAdministration = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setType(RestrictionType newVal){
        type = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setUnits(Units newVal){
        units = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setUpperLimit(Double newVal){
        upperLimit = newVal;
    }

    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
        /*
        if (getId() == null) {
            return 1;
        }
        if (o == null) {
            return -1;
        }
        return getId().compareTo(((Restriction)o).getId());
        */
        
        if (getType() == null) {
            return 1;
        }
        if (o == null) {
            return -1;
        }
        int typeComparison = getType().compareTo(((Restriction)o).getType());
        if (typeComparison == 0) {
            return 1;
        } else {
           return typeComparison;
        }
        
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
        buffer.append(", type: ");
        if (getType() != null) {
            buffer.append(getType().getName());
        } else {
            buffer.append("null");
        }
        buffer.append(", detail: ");
        buffer.append(getDetailE());
        buffer.append(", lowerLimit: ");
        buffer.append(getLowerLimit());
        buffer.append(", upperLimit: ");
        buffer.append(getUpperLimit());
        buffer.append(", units: ");
        if (getUnits() != null) {
            buffer.append(getUnits().getCode());
        } else {
            buffer.append("null");
        }
        buffer.append(", citations: ");
        buffer.append(getCitations());
        buffer.append(", routeOfAdministration: ");
        if (getRouteOfAdministration() != null) {
            buffer.append(getRouteOfAdministration().getName());
        } else {
            buffer.append("null");
        }
        buffer.append(", reference: ");
        buffer.append(getReference());
        
        return buffer.toString();
    }
    
}