package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Set;

/*******************************************************************************
 * An object that represents a user role (roleid).
 */
public class UserRole extends PersistentObject implements Comparable {
    private String roleNameE;
    private String roleNameF;
    private Set<UserAccount> users;
    private Set<Permission> permissions;

    /***************************************************************************
     * Gets the English name of the role (must be unique).
     * @return name of the role.
     * @see #setRoleName()
     */
    public String getRoleNameE(){
        return roleNameE;
    }

    /***************************************************************************
     * Sets the English name of the role (must be unique).
     * @param newVal name of the role.
     * @see #getRoleName()
     */
    public void setRoleNameE(String newVal){
        roleNameE = newVal;
    }

    /***************************************************************************
     * Gets the French name of the role (must be unique).
     * @return name of the role.
     * @see #setRoleName()
     */
    public String getRoleNameF(){
        return roleNameF;
    }

    /***************************************************************************
     * Sets the French name of the role (must be unique).
     * @param newVal name of the role.
     * @see #getRoleName()
     */
    public void setRoleNameF(String newVal){
        roleNameF = newVal;
    }

    /***************************************************************************
     * Gets the role name in the language appropriate for the Locale.
     * 
     * @return the locale-specific name.
     */
    public String getRoleName(){
        if (isLanguageFrench()) {
            return getRoleNameF();
        }
        return getRoleNameE();
    }
    
    /***************************************************************************
     * Gets the English short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the English specific short description.
     */
    public String getShortDescriptionE() {
        return getRoleNameE();
    }

    /***************************************************************************
     * Gets the French short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the French specific short description.
     */
    public String getShortDescriptionF() {
        return getRoleNameF();
    }
    
    /***************************************************************************
     * Gets the users that have this role.
     * @return users that have this role.
     * @see #setRoleName()
     */
    public Set<UserAccount> getUsers(){
        return users;
    }

    /***************************************************************************
     * Sets the users that have this role.
     * @param newVal users of this role.
     * @see #getRoleName()
     */
    public void setUsers(Set<UserAccount> newVal){
        users = newVal;
    }

    /***************************************************************************
     * Gets the permissions associated with this role.
     * @return permissions associated with this role.
     * @see #setPermissions()
     */
    public Set<Permission> getPermissions(){
        return permissions;
    }

    /***************************************************************************
     * Sets the permissions associated with tihs role.
     * @param newVal permissions associated with this role.
     * @see #getPermissions()
     */
    public void setPermissions(Set<Permission> newVal){
        permissions = newVal;
    }

    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
        
        if (o == null) return -1; 
    	return StringComparator.compare(getRoleNameE(),
    									((UserRole)o).getRoleNameE());
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
        buffer.append(", roleNameE: ");
        buffer.append(getRoleNameE());
        buffer.append(", roleNameF: ");
        buffer.append(getRoleNameF());
     
        if (getUsers() != null && !getUsers().isEmpty()) {
            buffer.append(", users: ");
            buffer.append(getUsers());
        }

        if (getPermissions() != null && !getPermissions().isEmpty()) {
            buffer.append(", permissions: ");
            buffer.append(getPermissions());
        }

        return buffer.toString();
    }
}