package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import ca.gc.hc.nhpd.util.LocaleSource;

/*******************************************************************************
 * An object used as a Superclass to all domain objects (Entities). It implements
 * common attributes as well as providing some simple functionality around
 * establishing what language Locale-dependant values should be displayed in.
 * Note that its LocaleSource must be set, otherwise it will always return
 * English.
 */
public abstract class PersistentObject implements Comparable {
    private static final String LANG_FR = Locale.CANADA_FRENCH.getISO3Language(); //ISO 639.2
    private static LocaleSource localeSource =
                                new PersistentObject.SimpleLocaleSource();

	protected String shortDescription;
    protected Long id;
    private Date creationDate;
    private UserAccount lastUpdateAccount;
    private Date lastUpdateDate;
    private Locale userLocale;
    
    // Temporary id assigned to new persistent object that have not
    // yet been persisted.
    public static Long NEW_ID_LONG = new Long(-1);
    public static String NEW_ID_STRING = "-1";
    public static String EMPTY_STRING = "";

    /***************************************************************************
     * Implementation specific method that indicates if the object has
     * been persisted or not.  An unpersisted object does not have an id or
     * if dynamically created, may have an id of -1.  The isPersisted returns
     * a true if the object has been saved (or given an id), and false if the
     * object has not yet been persisted.
     */
    public boolean isPersisted() {
        if (getId() == null || getId().equals(NEW_ID_LONG)) {
            return false;
        }
        return true;
    }
    
	/***************************************************************************
	 * An equality implementation that uses database persisted information (id) 
	 * and the object name to determin equality.  If unavailable, then it 
	 * defaults to the standard equality operator.
	 */
	public boolean equals(Object o) {
		
		if( o != null 
			&& o instanceof PersistentObject 
			&& getId() != null) {
				// Compare using the simpleClassName and id.
				if ( getId().equals(((PersistentObject)o).getId()) &&
				getSimpleClassName().equals(((PersistentObject)o).getSimpleClassName())) {
					return true;        
				} 
		}
        
		// Drop to the default compare.
		return super.equals(o);		
	}

	/***************************************************************************
	 * Returns the simple name of the class.  
	 * Java 5 has a feature to return the simple name without a String formula.
	 */
	public String getSimpleClassName() {
		String className = this.getClass().getName();
		return className.substring( className.lastIndexOf( "." ) + 1, className.length() ); 
	}

    
    /***************************************************************************
     * Gets the short description in the language appropriate for the Locale.
     * @return the locale-specific name.
     */
    public String getShortDescription(){
        if (isLanguageFrench()) {
            return getShortDescriptionF();
        }
        return getShortDescriptionE();
    }

    
	/***************************************************************************
	 * Returns a short description of this object.  
	 * Includes the simple class name and the object's id.
	 * Default value, to be overwritten by the child classes. 
	 */
	public String getShortDescriptionE() {
		if(id != null) {
			return getSimpleClassName() + "-" + id.toString(); 
		}
		return getSimpleClassName();
	}
	
	/***************************************************************************
	 * Returns a short description of this object.  
	 * Includes the simple class name and the object's id.
	 * Default value, to be overwritten by the child classes. 
	 */
	public String getShortDescriptionF() {
		if(id != null) {
			return getSimpleClassName() + "-" + id.toString(); 
		}
		return getSimpleClassName();
	}
	   
    /***************************************************************************
     * Gets the long description used to represent this object in drop down
     * lists and other various interfaces.
     * 
     * <p>Notes:  The long description tries to return the same visual information
     * as found in the materialized views.  This ensures that selections made
     * by the client keep the same display text.
     * 
     * <p>Usage:  Species, SubTaxa, Reference, at this time.
     * 
     * @return the locale-specific short description.
     */
    public String getLongDescription(){
        
        if (isLanguageFrench()) {
            return getLongDescriptionF();
        }
        return getLongDescriptionE();

    }
    
    /***************************************************************************
     * Returns a long description of this object.  
     * Includes the simple class name and the object's id.
     * Default value, to be overwritten by the child classes.
     * 
     * @see getLongDescription()
     */
    public String getLongDescriptionE() {
        
        return null;
        
    }
    
    /***************************************************************************
     * Returns a long description of this object.  
     * Includes the simple class name and the object's id.
     * Default value, to be overwritten by the child classes.
     * 
     * @see getLongDescription()
     */
    public String getLongDescriptionF() {
        
        return null;
        
    }
    
    /***************************************************************************
     * Sets the object that determines the user's Locale. Note that this defaults
     * to SimpleLocaleSource (an inner class), which always returns English.
     * @param aSource the object that determines the user's Locale.
     */
    public static void setLocaleSource(LocaleSource aSource) {
        if (aSource != null) {
            localeSource = aSource;
        }
    }

    /***************************************************************************
     * Gets whether the ISO 639.2 language of the user's Locale is French.
     * @return true if the language of the user's Locale is French.
     */
    public boolean isLanguageFrench() {
        return LANG_FR.equals(getUserLocale().getISO3Language());
    }

    /***************************************************************************
     * Gets the date that this object was initially added to the persistent store.
     * @return the date that this object was initially added to the persistent
     *         store.
     * @see #setCreationDate()
     */
    public Date getCreationDate(){
        return creationDate;
    }

    /***************************************************************************
     * Gets the unique identifier of this object in persistent store.
     * @return the unique identifier of this object in persistent store.
     * @see #setId()
     */
    public Long getId(){
        return id;
    }

    /***************************************************************************
     * Gets the user account that this object was last updated from.
     * @return the user account that this object was last updated from.
     * @see #setLastUpdateAccount()
     */
    public UserAccount getLastUpdateAccount(){
        return lastUpdateAccount;
    }

    /***************************************************************************
     * Gets the date that this object was last updated in the persistent store.
     * @return the date that this object was last updated in the persistent
     *         store.
     * @see #setLastUpdateDate()
     */
    public Date getLastUpdateDate(){
        return lastUpdateDate;
    }

    /***************************************************************************
     * Gets the Locale that this object should use when returning Locale-dependant
     * values. If set to a specific value (e.g. it is being maintained in the
     * Page or Request scope), this will return that value. Otherwise (e.g. it
     * is being maintained in the Session or Application scope), it will
     * get the user's current Locale setting from ApplicationGlobals.
     * @return the Locale (language) that text values should be returned in.
     * @see #setUserLocale(Locale)
     */
    public Locale getUserLocale() {
        if (userLocale != null) {
            return userLocale;
        }
        return localeSource.getUserLocale();
    }
    
    /***************************************************************************
     * Sets the date that this object was initially added to the persistent store.
     * @param aDate the date that this object was initially added to the
     *        persistent store.
     * @see #getCreationDate()
     */
    public void setCreationDate(Date aDate){
        creationDate = aDate;
    }

    /***************************************************************************
     * Sets the unique identifier of this object in persistent store.
     * @param aLong the unique identifier of this object in persistent store.
     * @see #getId()
     * 
     * @param newVal
     */
    public void setId(Long aLong){
        id = aLong;
    }

    /***************************************************************************
     * Sets the user account that this object was last updated from.
     * @param aUserAccount the user account that this object was last updated
     *        from.
     * @see #getLastUpdateAccount()
     */
    public void setLastUpdateAccount(UserAccount aUserAccount){
        lastUpdateAccount = aUserAccount;
    }

    /***************************************************************************
     * Sets the date that this object was last updated in the persistent store.
     * @param aDate the date that this object was last updated in the persistent
     *        store.
     * @see #getLastUpdateDate()
     */
    public void setLastUpdateDate(Date aDate){
        lastUpdateDate = aDate;
    }

    /***************************************************************************
     * Sets the Locale that this object should use when returning Locale-dependant
     * values. Only set when this object should always return values based on
     * the same Locale (e.g. it is being maintained in the Page or Request scope).
     * @param aLocale the Locale (language) that text values should be returned
     *        in.
     * @see #getUserLocale()
     */
    public void setUserLocale(Locale aLocale) {
        userLocale = aLocale;
    }

    /***************************************************************************
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Subslasses should override this and add
     * their values to the end.
     */
    public String getValuesAsString(){
        StringBuffer buffer = new StringBuffer();
        
        buffer.append("id: ");
        buffer.append(getId());
        buffer.append(", creationDate: ");
        buffer.append(getCreationDate());
        buffer.append(", lastUpdateDate: ");
        buffer.append(getLastUpdateDate());
        buffer.append(", lastUpdateAccount: ");
        if (getLastUpdateAccount() != null) {
            buffer.append(getLastUpdateAccount().getAccountName());
        } else {
            buffer.append("null");
        }
        
        return buffer.toString();
    }

    /***************************************************************************
     * Over-ridden to provide useful debugging information.
     */
    public String toString(){
        StringBuffer buffer = new StringBuffer();
        
        //buffer.append(getClass().getSimpleName()); //Requires newer version of Java
        buffer.append(getClass().getName());

        buffer.append(" [");
        buffer.append(getValuesAsString());
        buffer.append("]");
        
        return buffer.toString();
    }

    /***************************************************************************
     * Supports subclass cloning (it can't clone itself since it's abstract).
     * @param aClone the subclass's clone to be updated.
     */
    protected void updateClone(PersistentObject aClone) {
        if (getId() != null) {
            aClone.setId(getId()); //Immutable
        }
        if (userLocale != null) {
            aClone.setUserLocale((Locale)userLocale.clone());
        }
    }
    
	/***************************************************************************
	 * Compares this object with the specified object for order. Returns a
	 * negative integer, zero, or a positive integer as this object is less than,
	 * equal to, or greater than the specified object.
	 * @param o the object to compare this to.
	 */
	public int compareTo(Object o) throws ClassCastException {
	
		if (o == null) return -1; 
	    return StringComparator.compare(getShortDescription(),
	    	((PersistentObject)o).getShortDescription());
	}
		
    //+++ Inner Classes ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /***************************************************************************
     * A simple implementation of LocaleSource that always returns English.
     */
    private static class SimpleLocaleSource implements LocaleSource {
        /***********************************************************************
         * Gets an english Locale.
         */
        public Locale getUserLocale() {
            return Locale.ENGLISH;
        }
    }
    
	/***************************************************************************
	 * A function that stamps the object with fields required for saving.
	 * The useraccount is used to identify the indivual performing the change.
	 */
    public void prepForUpdate( UserAccount useraccount ) {
		setLastUpdateAccount( useraccount );
		
		/* MySQL requires that the dates be set, however Oracle trigger fires before 
		 * insert statement and so the dates are not required in Oracle.  For portability
		 * reasons, we have left the dates in place.
		 */
		Date d = new Date();
		setLastUpdateDate( d );
		setCreationDate( d );	
    }
    
	//+++ Inner Classes ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/***************************************************************************
	 * An implementation of Comparator that allows these objects to be sorted
	 * using their French names.
	 */
	public static class FrenchComparator implements Comparator {
		/***********************************************************************
		 * Compares the passed objects for order. Returns a negative integer,
		 * zero, or a positive integer as the first object is less than, equal
		 * to, or greater than the second.
		 * @param o1 the first object to be compared.
		 * @param o2 the second object to be compared.
		 */
		public int compare(Object o1, Object o2) throws ClassCastException {
			if (o1 == null || ((PersistentObject)o1).getShortDescriptionF() == null) {
				return 1;
			}
			if (o2 == null || ((PersistentObject)o2).getShortDescriptionF() == null) {
				return -1;
			}
			return ((PersistentObject)o1).getShortDescriptionF().compareTo(
				   ((PersistentObject)o2).getShortDescriptionF());
		}
	}

    //+++ Inner Classes ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /***************************************************************************
     * An implementation of Comparator that allows these objects to be sorted
     * using their English names.
     */
    public static class EnglishComparator implements Comparator {
        /***********************************************************************
         * Compares the passed objects for order. Returns a negative integer,
         * zero, or a positive integer as the first object is less than, equal
         * to, or greater than the second.
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         */
        public int compare(Object o1, Object o2) throws ClassCastException {
            if (o1 == null || ((PersistentObject)o1).getShortDescriptionE() == null) {
                return 1;
            }
            if (o2 == null || ((PersistentObject)o2).getShortDescriptionE() == null) {
                return -1;
            }
            
            return ((PersistentObject)o1).getShortDescriptionE().compareTo(
                   ((PersistentObject)o2).getShortDescriptionE());
        }
    }
    
    public static class ObjectComparator implements Comparator {
        /***********************************************************************
         * Compares the passed objects for order. Returns a negative integer,
         * zero, or a positive integer as the first object is less than, equal
         * to, or greater than the second.
         * 
         * This was added to support the complex synonym / common lists that are
         * being persisted into the system. 
         * 
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         */
        public int compare(Object o1, Object o2) throws ClassCastException {
            if (o1 == null 
                    || ((PersistentObject)o1).getSimpleClassName() == null
                    || ((PersistentObject)o1).getId() == null) {
                return 1;
            }
            if (o2 == null
                    || ((PersistentObject)o2).getSimpleClassName() == null
                    || ((PersistentObject)o2).getId() == null) {
                return -1;
            }
            // If the names are not equal, then compare on the name first.
            if (!((PersistentObject)o1).getSimpleClassName().equals(((PersistentObject)o2).getSimpleClassName())) {
                ((PersistentObject)o1).getSimpleClassName().compareTo(((PersistentObject)o2).getSimpleClassName());
            }
            // If the names are equal, then compare on the ID.
            return ((PersistentObject)o1).getId().compareTo(
                   ((PersistentObject)o2).getId());
        }
    }
    
}



