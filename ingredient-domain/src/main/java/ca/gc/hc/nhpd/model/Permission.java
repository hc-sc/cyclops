package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;

/*******************************************************************************
 * An object that represents a permission.  Permissions are managed from the 
 * role.
 */
public class Permission extends PersistentObject implements Comparable {
    private String nameF;
    private String nameE;

    /***************************************************************************
     * Gets the English name of the permission (must be unique).
     * @return English name of the permission.
     * @see #setPermission()
     */
    public String getNameE(){
        return nameE;
    }

    /***************************************************************************
     * Sets the English name of the role (must be unique).
     * @param English newVal name of the role.
     * @see #getRoleName()
     */
    public void setNameE(String newVal){
        nameE = newVal;
    }

    /***************************************************************************
     * Gets the French name of the permission (must be unique).
     * @return French name of the permission.
     * @see #setPermission()
     */
    public String getNameF(){
        return nameF;
    }

    /***************************************************************************
     * Sets the French name of the role (must be unique).
     * @param French newVal name of the role.
     * @see #getRoleName()
     */
    public void setNameF(String newVal){
        nameF = newVal;
    }

    /***************************************************************************
     * Gets the name in the language appropriate for the Locale.
     * @return the locale-specific name.
     */
    public String getName(){
        if (isLanguageFrench()) {
            return getNameF();
        }
        return getNameE();
    }
    
    /***************************************************************************
     * Gets the English short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the English specific short description.
     */
    public String getShortDescriptionE() {
        return getNameE();
    }

    /***************************************************************************
     * Gets the French short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the French specific short description.
     */
    public String getShortDescriptionF() {
        return getNameF();
    }
    
    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
        
        if (o == null) return -1; 
    	return StringComparator.compare(getNameE(),
    									((Permission)o).getNameE());
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
        buffer.append(", nameE: ");
        buffer.append(getNameE());
        buffer.append(", nameF: ");
        buffer.append(getNameF());
        
        return buffer.toString();
    }
}