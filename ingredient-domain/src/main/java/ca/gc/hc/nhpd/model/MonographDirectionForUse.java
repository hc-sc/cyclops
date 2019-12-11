package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;

import java.util.Set;

/*******************************************************************************
 * The MonographDirectionForUse helps qualify how a dose of the ingredients
 * should be used according to a monograph.
 */
public class MonographDirectionForUse extends DependantObject implements Comparable  {
    
	private String code;
    private String directionForUseE;
    private String directionForUseF;
    
    //parent doses - TODO refactor out by modifying loader.
    private Set<MonographDose> monographDoses;
    
    private transient String useDependenciesTitle;
  

    //~ public -----------------------------------------------------------------

	public String getUseDependenciesTitle() {
		return useDependenciesTitle;
	}

	public void setUseDependenciesTitle(String useDependenciesTitle) {
		this.useDependenciesTitle = useDependenciesTitle;
	}

	/**
	 * Compares this object with the specified object for equality and for
	 * order. Returns a negative integer, zero, or a positive integer as this
	 * object is less than, equal to, or greater than the specified object.
	 *
	 * @param   o  the object to compare this to.
	 *
	 * @return  a comparator flag qualifying equality and order
	 *
	 * @throws  ClassCastException  when the object provided is of the wrong
	 *                              type
	 */
	public int compareTo(Object o) throws ClassCastException {
		
		if (o == null) return -1;
		if (this.equals(o)) return 0;
		
	    return StringComparator.compare(getDirectionForUse(),
	    				((MonographDirectionForUse)o).getDirectionForUse());
	
	}

    /**
     * Gets the code.  Used in the loading of Monographs.
     *
     * @return  the code.
     *
     * @see     #setCode()
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets the DirectionForUse in the language appropriate for the Locale.
     *
     * @return  the locale-specific DirectionForUse.
     */
    public String getDirectionForUse() {

        if (isLanguageFrench()) {
            return getDirectionForUseF();
        }

        return getDirectionForUseE();

    }

    /**
     * Gets the DirectionForUse in the English language.
     *
     * @return  the DirectionForUse in English.
     * 
     * @see    #setDirectionForUseE()
     */
    public String getDirectionForUseE() {
        return directionForUseE;
    }

    /**
     * Gets the DirectionForUse in the French language.
     *
     * @return  the DirectionForUse in French.
     * 
     * @see    #setDirectionForUseF()
     */
    public String getDirectionForUseF() {
        return directionForUseF;
    }

	/**
     * Accessors for Hibernate. Only used in the loading of MonographDoses.
     *
     * @return
     */
    public Set<MonographDose> getMonographDoses() {
        return monographDoses;
    }
	
	/**
     * Sets the code.  Used in the loading of Monographs.
     *
     * @param  newValue  code.
     */
    public void setCode(String newValue) {
        code = newValue;
    }

	/**
	 * Sets the English direction for use String for this Monograph Direction For Use.
	 *
	 * @param  newVal  the English direction for use.
	 *
	 * @see    #getDirectionForUseE()
	 */
    public void setDirectionForUseE(String newVal) {
        directionForUseE = newVal;
    }

    /**
     * Sets the French direction for use String for this Monograph Direction For Use.
     *
     * @param  newVal  the French direction for use.
     *
     * @see    #getDirectionForUseF()
     */
    public void setDirectionForUseF(String newVal) {
        directionForUseF = newVal;
    }

    /**
     * Accessors for Hibernate. Only used in the loading of MonographDoses.
     *
     * @return
     */
    public void setMonographDoses(Set<MonographDose> newVal) {
        monographDoses = newVal;
    }

    
    public boolean equals(Object obj) {
    	if (! (obj instanceof MonographDirectionForUse))
    		return false;
    	MonographDirectionForUse passedMdfu = (MonographDirectionForUse)obj;
    
    	if (getDirectionForUse() != null &&
    		passedMdfu.getDirectionForUse() != null) {
    		return getDirectionForUse().trim().equals
    			(passedMdfu.getDirectionForUse().trim()); 
    	}
    	
    	return (passedMdfu.getDirectionForUse() == null);
    		 
    		
    }
    
    /***************************************************************************
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Subslasses should override this and add
     * their values to the end.
     */
    @Override
    public String getValuesAsString(){
        StringBuilder buffer = new StringBuilder();
       
        buffer.append(super.getValuesAsString());
        buffer.append(", code: ");
        buffer.append(getCode());
        buffer.append(", directionForUseE: ");
        buffer.append(getDirectionForUseE());

        return buffer.toString();
    }
    
}
