package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/*******************************************************************************
 * An object that represents a user account (userid).
 */
public class UserAccount extends PersistentObject implements Comparable {
    private String accountName;
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String password;
    private Set<UserRole> roles;
    private Set<Permission> permissions;
    private Set<Long> permissionIds;
    
    /***************************************************************************
     * Gets the name of the account (must be unique).
     * @return name of the account.
     * @see #setAccountName()
     */
    public String getAccountName(){
        return accountName;
    }

    /***************************************************************************
     * Sets the name of the account (must be unique).
     * @param newVal name of the account.
     * @see #getAccountName()
     */
    public void setAccountName(String newVal){
        accountName = newVal;
    }

    /***************************************************************************
     * Gets the English short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the English specific short description.
     */
    public String getShortDescriptionE() {
        return getAccountName();
    }

    /***************************************************************************
     * Gets the French short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the French specific short description.
     */
    public String getShortDescriptionF() {
        return getAccountName();
    }
    
    /***************************************************************************
     * Gets the user's email address (must be unique).
     * @return the user's email address.
     * @see #setEmailAddress()
     */
    public String getEmailAddress(){
        return emailAddress;
    }

    /***************************************************************************
     * Sets the user's email address (must be unique).
     * @param newVal the user's email address.
     * @see #getEmailAddress()
     */
    public void setEmailAddress(String newVal){
        emailAddress = newVal;
    }

    /***************************************************************************
     * Gets the user's first name.
     * @return the user's first name.
     * @see #setFirstName()
     */
    public String getFirstName(){
        return firstName;
    }

    /***************************************************************************
     * Sets the user's first name.
     * @param newVal the user's first name.
     * @see #getFirstName()
     */
    public void setFirstName(String newVal){
        firstName = newVal;
    }

    /***************************************************************************
     * Gets the user's last name.
     * @return the user's last name.
     * @see #setLastName()
     */
    public String getLastName(){
        return lastName;
    }

    /***************************************************************************
     * Sets the user's last name.
     * @param newVal the user's last name.
     * @see #getLastName()
     */
    public void setLastName(String newVal){
        lastName = newVal;
    }

    /***************************************************************************
     * Gets the user's password.  (This should be a hashed value!)
     * @return the user's password.
     * @see #setPassword()
     */
    public String getPassword(){
        return password;
    }

    /***************************************************************************
     * Sets the user's password.  (This should be a hashed value!)
     * @param newVal the user's password.
     * @see #getPassword()
     */
    public void setPassword(String newVal){
        password = newVal;
    }

    /***************************************************************************
     * Gets the roles associated with this account.
     * @return roles associated with this account.
     * @see #setRoles()
     */
    public Set<UserRole> getRoles(){
        return roles;
    }

    /***************************************************************************
     * Sets the roles associated with this user.
     * @param newVal roles associated with this user.
     * @see #getRoles()
     */
    public void setRoles(Set<UserRole> newVal){
        roles = newVal;
    }

    /***************************************************************************
     * Gets the permissions associated with this user.
     * 
     * <p>TODO - Need to expire the permission cached information.
     * 
     * @see #hasPermission()
     */
    public Set<Permission> getPermissions(){
        
        // If chached, return the cached permissions.
        if (permissions != null) {
            return permissions;
        }
        
        permissions = new TreeSet<Permission>();
        
        // Else, get the permissions via the role.
        Iterator i = getRoles().iterator();
        while (i.hasNext()) {
            UserRole userRoles = (UserRole) i.next();
            permissions.addAll(userRoles.getPermissions());
        }

        return permissions;
    }

    /***************************************************************************
     * Gets the permission ids associated with this user.
     * 
     * <p>TODO - Need to expire the permission id cached information.
     * 
     * @see #hasPermission()
     */
    public Set<Long> getPermissionIds(){
        
        // If chached, return the cached permissions.
        if (permissionIds != null) {
            return permissionIds;
        }
        
        permissionIds = new TreeSet<Long>();
        
        // Else, get the permissions via the role.
        Iterator i = getPermissions().iterator();
        while (i.hasNext()) {
            Permission permission = (Permission) i.next();
            permissionIds.add(permission.getId());
        }

        return permissionIds;
    }
    
    
    /***************************************************************************
     * Returns a boolean value (true / false) depending on if this user has
     * the specified permission id.
     * 
     * @see #getPermissions()
     */
    public boolean hasPermission(Long permissionIds){
        return getPermissionIds().contains(permissionIds);
    }
    
    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
        
        if (o == null) return -1; 
        if (getAccountName() == null) return 1;
        
        // Updated the code to compare the user account it.  The account name
        // previously being used was not unique.
        return ((UserAccount)o).getAccountName().toLowerCase().compareTo(getAccountName().toLowerCase());
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
        buffer.append(", accountName: ");
        buffer.append(getAccountName());
        buffer.append(", firstName: ");
        buffer.append(getFirstName());
        buffer.append(", lastName: ");
        buffer.append(getLastName());
        buffer.append(", emailAddress: ");
        buffer.append(getEmailAddress());
        
        if (getRoles() != null && !getRoles().isEmpty()) {
            buffer.append(", roles: ");
            int count = 0;
            for (UserRole userRole : getRoles()) {
                if (count != 0 ) {
                    buffer.append(", ");   
                }
                if (userRole != null) {
                    buffer.append(userRole.getRoleName());
                }
                count++;
            }
        }
        
        return buffer.toString();
    }
}