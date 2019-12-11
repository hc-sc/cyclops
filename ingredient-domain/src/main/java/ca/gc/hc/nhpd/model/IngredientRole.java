package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Set;

/*******************************************************************************
 * An abstract class intended to be extended by different types of roles. These
 * are the ways that an ingredient may be used.
 * @version 1.0
 */
public abstract class IngredientRole extends PersistentObject
                                     implements Comparable {
	private Ingredient ingredient;
	private Set<Restriction> restrictions;
	
    /***************************************************************************
	 * @return the ingredient object.
	 */
	public Ingredient getIngredient(){ return ingredient; };
	
    /***************************************************************************
	 * @param value of the ingredient we are setting.
	 */
    public void setIngredient( Ingredient value ) {
		ingredient = value;
	}
	
    /***************************************************************************
     * Gets any Restrictions that apply to this Role. Returns an empty Set
     * if there are none.
     */
    public Set<Restriction> getRestrictions(){
        return restrictions;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setRestrictions(Set<Restriction> newVal){
        restrictions = newVal;
    }

    /***************************************************************************
     * Gets the type in the language appropriate for the Locale.
     * @return the locale-specific type.
     */
    public abstract String getType();

    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
      
        if (o == null) return -1; 
    	return StringComparator.compare(getType(),((IngredientRole)o).getType());
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
        buffer.append(", restrictions: ");
        buffer.append(getRestrictions());
        
        return buffer.toString();
    }
}