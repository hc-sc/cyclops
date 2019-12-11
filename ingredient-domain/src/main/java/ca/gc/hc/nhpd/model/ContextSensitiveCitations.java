package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Set;

/*******************************************************************************
 * A context sensitive citation object is an object that qualifies the set of
 * citation between any two Persistent Objects. For example, when an Organism
 * Part found in Monograph Entry needs to be qualified with citations, we use
 * this object.
 * 
 * <p>The context object is the object that contains the relevant relationship.
 * In our example, the MonographEntry is the object that contains the
 * relationship to the Organism Part. Accessors are on this side, and we
 * normally populate the relationship from this object.
 *
 * <p>The primary object is the object who's relationship to the context object
 * needs to be qualified with a series of citations.
 */
public class ContextSensitiveCitations extends PersistentObject
    implements Comparable {

    //~ Instance fields --------------------------------------------------------

    private Set<Citation> citations;

    private Long contextObjectId;
    private String contextObjectName;
    private Long primaryObjectId;
    private String primaryObjectName;

    //~ public -----------------------------------------------------------------

    /**
     * Default constructor. 
     */
    public ContextSensitiveCitations() {
    }

    /**
     * Constructor for use when adding this object combination to a hashmap 
     * or other list constructs.
     *  
     * @param contextObject 
     * @param primaryObject
     */
    public ContextSensitiveCitations(PersistentObject contextObject,
        PersistentObject primaryObject) {

        // Throw an exception is something is missing.
        if ((contextObject == null) || (primaryObject == null)) {
            return;
        }

        setContextObjectId(contextObject.getId());
        setContextObjectName(contextObject.getSimpleClassName());
		setPrimaryObjectId(primaryObject.getId());
		setPrimaryObjectName(primaryObject.getSimpleClassName());
    }

	/**
	 * Constructor for use when adding this object combination to a hashmap 
	 * or other list constructs.
	 *  
	 * @param contextObject 
	 * @param primaryObject
	 */
	public ContextSensitiveCitations(PersistentObject contextObject,
		PersistentObject primaryObject, Set citationsValue) {

		// Throw an exception is something is missing.
		if ((contextObject == null) || (primaryObject == null)) {
			return;
		}

		setContextObjectId(contextObject.getId());
		setContextObjectName(contextObject.getSimpleClassName());
		setPrimaryObjectId(primaryObject.getId());
		setPrimaryObjectName(primaryObject.getSimpleClassName());
		setCitations(citationsValue);
	}

    //~ public -----------------------------------------------------------------

    /**
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param   o  the object to compare this to.
     *
     * @return  a compare integer
     *
     * @throws  ClassCastException  thrown when the object being compared is not
     *                              of the correct class
     */
    public int compareTo(Object o) throws ClassCastException {

        if ( (getContextObjectId() == null) 	|| 
        	 (getContextObjectName() == null) 	||
             (getPrimaryObjectId() == null) 	||
             (getPrimaryObjectName() == null) ) {
            return 1;
        }

        if (o == null) {
            return -1;
        }

        // Return equals based on context object id.
        if (!getContextObjectId().equals(
                    ((ContextSensitiveCitations) o).getContextObjectId())) {
            return getContextObjectId().compareTo(
                    ((ContextSensitiveCitations) o).getContextObjectId());
        }

        // Return equals based on primary object id.
        if (!getPrimaryObjectId().equals(
                    ((ContextSensitiveCitations) o).getPrimaryObjectId())) {
            return getPrimaryObjectId().compareTo(
                    ((ContextSensitiveCitations) o).getPrimaryObjectId());
        }
        /*
        // Return equals based on context object name.
        if (!getContextObjectName().equals(
                    ((ContextSensitiveCitations) o).getContextObjectName())) {
            return getContextObjectName().compareTo(
                    ((ContextSensitiveCitations) o).getContextObjectName());
        }

        // Return equals based on primary object name.
        if (!getPrimaryObjectName().equals(
                    ((ContextSensitiveCitations) o).getPrimaryObjectName())) {
            return getPrimaryObjectName().compareTo(
                    ((ContextSensitiveCitations) o).getPrimaryObjectName());
        }
        */
        // Return equals based on context object name.
        if (!getContextObjectName().equals(
                    ((ContextSensitiveCitations) o).getContextObjectName())) {
            return StringComparator.compare(getContextObjectName(),
                ((ContextSensitiveCitations) o).getContextObjectName());
        }

        // Return equals based on primary object name.
        if (!getPrimaryObjectName().equals(
                    ((ContextSensitiveCitations) o).getPrimaryObjectName())) {
            return StringComparator.compare(getPrimaryObjectName(),
                ((ContextSensitiveCitations) o).getPrimaryObjectName());
        }
        
        return 0;
    }

    /**
     * Gets the set of citations associated with this object.
     *
     * @return  the set of citations.
     *
     * @see     #setCitations()
     */
    public Set<Citation> getCitations() {
        return citations;
    }

    /**
     * Gets the id of the context object as Long.
     *
     * @return  the id of the context object.
     *
     * @see     #setContextObjectId()
     */
    public Long getContextObjectId() {
        return contextObjectId;
    }

    /**
     * Gets the name of the context object as Long. For this we use the short
     * form of the object class name.
     *
     * @return  the name of the context object.
     *
     * @see     #setContextObjectName()
     */
    public String getContextObjectName() {
        return contextObjectName;
    }


    /**
     * Gets the id of the primary object as Long.
     *
     * @return  the id of the primary object.
     *
     * @see     #setPrimaryObjectId()
     */
    public Long getPrimaryObjectId() {
        return primaryObjectId;
    }

    /**
     * Gets the name of the primary object as Long. For this we use the short
     * form of the object class name.
     *
     * @return  the name of the primary object.
     *
     * @see     #setPrimaryObjectName()
     */
    public String getPrimaryObjectName() {
        return primaryObjectName;
    }

    /**
     * Sets the name of the primary object. For this we use the short form of
     * the object class name.
     *
     * @param  newVal  the name of the primary object.
     *
     * @see    #getPrimaryObjectName()
     */
    public void setPrimaryObjectName(String newVal) {
        primaryObjectName = newVal;
    }

    /**
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Over-ridden to add additional values to the
     * end.
     *
     * @return  this object in a String format
     */
    public String getValuesAsString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(super.getValuesAsString());
        buffer.append(", contextObjectName: ");
        buffer.append(getContextObjectName());
        buffer.append(", contextObjectId: ");
        buffer.append(getContextObjectId());
        buffer.append(", primaryObjectName: ");
        buffer.append(getPrimaryObjectName());
        buffer.append(", primaryObjectId: ");
        buffer.append(getPrimaryObjectId());
        buffer.append(", citations: ");

        if (getCitations() != null) {
            buffer.append(getCitations());
        } else {
            buffer.append("null");
        }

        return buffer.toString();
    }

    // Returns a hashcode to uniquely identify this object.
    public int hashCode() {
        int output = (getContextObjectId().hashCode() * 100000)
            + getPrimaryObjectId().hashCode()
            + getContextObjectName().hashCode()
            + getPrimaryObjectName().hashCode();

        return output;
    }

    /**
     * Sets the citations associated with this object.
     *
     * @param  newVal  the set of citations.
     *
     * @see    #getCitations()
     */
    public void setCitations(Set<Citation> newVal) {
        citations = newVal;
    }

    /**
     * Sets the id of the context object.
     *
     * @param  newVal  the id of the context object.
     *
     * @see    #getContextObjectId()
     */
    public void setContextObjectId(Long newVal) {
        contextObjectId = newVal;
    }

    /**
     * Sets the name of the context object. For this we use the short form of
     * the object class name.
     *
     * @param  newVal  the name of the context object.
     *
     * @see    #getContextObjectName()
     */
    public void setContextObjectName(String newVal) {
        contextObjectName = newVal;
    }

    /**
     * Sets the id of the primary object.
     *
     * @param  newVal  the id of the primary object.
     *
     * @see    #getPrimaryObjectId()
     */
    public void setPrimaryObjectId(Long newVal) {
        primaryObjectId = newVal;
    }


}
