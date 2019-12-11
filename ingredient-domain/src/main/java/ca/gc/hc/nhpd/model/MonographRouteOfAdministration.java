package ca.gc.hc.nhpd.model;

import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/*******************************************************************************
 * These are the routes of administration that are associated with the
 * ingredients according to a Monograph. It is a wrapper around a
 * RouteOfAdministration so that the RouteOfAdministration can be qualified with
 * additional citations and optionally be restricted to specific dosage forms.
 */
public class MonographRouteOfAdministration extends PersistentObject
    implements Comparable {
    private static final String COMMA_SPACE = ", ";

    //~ Instance fields --------------------------------------------------------

    private Set<Citation> citations;
	private String code;
    private RouteOfAdministration routeOfAdministration;
    //parent monographs - only used when loading
    private Set<Monograph> monographs;
    private Set<GenericText> dosageFormNotes;
    private Set<DosageForm> specificDosageForms;

    //~ public -----------------------------------------------------------------
    /**
     * Gets a string representation of the citations associated with this Object.
     * @return the citations for this Object.
     */
    public String getCitationsAsString() {
        
        if ( getCitations() == null 
            || getCitations().size() == 0 ) {
                return null;
            }

        StringBuffer citationList = new StringBuffer();
        Iterator<Citation> i = getCitations().iterator();
        while ( i.hasNext() ) {
            Citation citation = i.next();
            if ( citation.getWorkAuthor() != null ) {
                if ( citationList.length() != 0 ) {
                    citationList.append( COMMA_SPACE );
                }
                citationList.append( citation.getAbridgedTitle() );
            }
        }
        return citationList.toString();
    }

    /**
     * Gets the citations associated with this Object.
     * @return the citations Set for this Object.
     */
    public Set<Citation> getCitations() {
        return citations;
    }

    /**
     * Gets the code. Only used in the loading of Monographs.
     *
     * @return  the code.
     *
     * @see     #setCode()
     */
    public String getCode() {
        return code;
    }

	/***************************************************************************
     * Gets a set of dosage forms specific to this monograph. This can be
     * populated with a subset of those for this RouteOfAdministration. Used
     * when only some of the possible dosage forms are applicable to this
     * monograph. For Hibernate use - use getApplicableDosageForms() instead.
     * @return the dosage forms specific to this monograph.
     * @see #getApplicableDosageForms()
     * @see #setSpecificDosageForms()
     */
    public Set<DosageForm> getSpecificDosageForms(){
        return specificDosageForms;
    }

    /***************************************************************************
     * Sets a set of dosage forms specific to this monograph. This can be
     * populated with a subset of those for this RouteOfAdministration. Used
     * when only some of the possible dosage forms are applicable to this
     * monograph. For Hibernate use.
     * @see #getSpecificDosageForms()
     */
    public void setSpecificDosageForms(Set<DosageForm> newVal){
        specificDosageForms = newVal;
    }

    /***************************************************************************
     * Gets the set of dosage form notes.
	 * @return the monograph dosage form notes.
     * @see #setDosageFormNotes()
	 */
    public Set<GenericText> getDosageFormNotes(){
        return dosageFormNotes;
	}

	/***************************************************************************
     * Sets the set of dosage form notes.
     * @see #setDosageFormNotes()
	 */
    public void setDosageFormNotes(Set<GenericText> newVal){
        dosageFormNotes = newVal;
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

        if (getRouteOfAdministration() == null) {
            return 1;
        }

        if (o == null) {
            return -1;
        }

        // Use our new implementation of equals.
        if (this.equals(o)) {
            return 0;
        }

        // Perform the comparison on the Maximum Dose
        return getRouteOfAdministration().compareTo(
                ((MonographRouteOfAdministration) o)
                .getRouteOfAdministration());
    }

    /**
     * Returns the set of all the Dosage Forms to be used by this
     * MonographRouteOfAdministration. It includes the "specific dosage forms"
     * if these have been set, otherwise it returns the dosage forms from the
     * RouteOfAdministration object where no assessment is required. 
     * There are several dosage forms such as time release capsules which 
     * require assessment; product license applications which require assessment
     * can not be considered as compendial.
     *  
     * @return  Set of DosageForms available through this
     *          MonographRouteOfAdministration. Returns a null if no objects are
     *          being returned.
     */
    public Set<DosageForm> getApplicableDosageForms() {
        if (getSpecificDosageForms() != null 
            && !getSpecificDosageForms().isEmpty()) {
            return getSpecificDosageForms();
        }
    	
        TreeSet<DosageForm> retDosageForms = new TreeSet<DosageForm>();
        
        // add the DosageForms from the RouteOfAdministration object.
        if (getRouteOfAdministration() != null) {
            Set<DosageForm> dosageForms = getRouteOfAdministration()
                                          .getPossibleDosageForms();    
            if ((dosageForms != null) && (dosageForms.size() != 0)) {
                for (DosageForm dosageForm : dosageForms) {
            		if (!dosageForm.isAssessmentRequired())
            			retDosageForms.add(dosageForm);
            	}
            }
        }

        // return a null instead of an empty set.
        if (retDosageForms.size() == 0) {
            return null;
        }

        // return the Set of dosageForms.
        return retDosageForms;
    }

    /**
     * Accessors for Hibernate. Only used in the loading of Monographs.
     *
     * @return
     */
    public Set<Monograph> getMonographs() {
        return monographs;
    }

   
    /**
     * Getter for the route of administration.
     *
     * @return  the route of administration
     *
     * @see     #setRouteOfAdministration()
     */
    public RouteOfAdministration getRouteOfAdministration() {
        return routeOfAdministration;
    }

    /**
     * Sets the Citations for this Object.
     *
     * @param newVal the Set of Citations for this Object
     */
    public void setCitations(Set<Citation> newVal) {
        citations = newVal;
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
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Over-ridden to add additional values to the
     * end.
     *
     * @return  this object as a string value
     */
    @Override
    public String getValuesAsString() {
        StringBuilder buffer = new StringBuilder();
        boolean isFirstItem;

        buffer.append(super.getValuesAsString());
        buffer.append(", dosageFormNotes: ");
        buffer.append(getDosageFormNotes());
        buffer.append(", routeOfAdministration: ");
        if (getRouteOfAdministration() != null){
            buffer.append(getRouteOfAdministration().getName());
        } else {
            buffer.append("null");
        }
        buffer.append(", specificDosageForms: ");
        if (getSpecificDosageForms() != null){
            isFirstItem = true;
            buffer.append("[");
            for (DosageForm dosageForm : getSpecificDosageForms()) {
                if (isFirstItem) {
                    isFirstItem = false;
                } else {
                    buffer.append(", ");
                }
                buffer.append(dosageForm.getName());
            }
            buffer.append("]");
        } else {
            buffer.append("null");
        }

        return buffer.toString();
    }

  
    /**
     * Sets the route of administration. The MonographRouteOfAdministration
     * object acts as a wrapper for the RouteOfAdministration object.
     *
     * @param  newVal  the route of administration for this
     *                 MonographRouteOfAdministration object
     *
     * @see    #getRouteOfAdministration()
     */
    public void setRouteOfAdministration(RouteOfAdministration newVal) {
        routeOfAdministration = newVal;
    }

    /**
     * Accessors for Hibernate. Only used in the loading of Monographs.
     *
     * @param  newValue
     */
    public void setMonographs(Set<Monograph> newValue) {
        monographs = newValue;
    }

}
