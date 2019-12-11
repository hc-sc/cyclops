package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*******************************************************************************
 * An object that represents a chemical substance that only exists in the system
 * as an impurity.
 */
public class ChemicalImpuritySubstance extends BasicImpuritySubstance
                                       implements ChemicalImpurity, Comparable {
    private static final Log LOG = LogFactory.getLog(ChemicalImpuritySubstance.class);
	private BasicRestriction toxicityRestriction;

    /***************************************************************************
     * TODO - need to revisit.  Added to avoid error caused by ChemicalImpurity
     * implementation.
     */
    public String getDescription(){
        return "";
    }

    /***************************************************************************
     */
	public BasicRestriction getToxicityRestriction(){
		return toxicityRestriction;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setToxicityRestriction(BasicRestriction newVal){
        toxicityRestriction = newVal;
	}

    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
        /*
    	if (getAuthorizedName() == null) {
            return 1;
        }
        if (o == null) {
            return -1;
        }
        if (!(o instanceof ChemicalImpurity)) {
            LOG.error("ChemicalImpuritySubstance can't sort with a " +
                      o.getClass().getName());
        }
        //Case Insensitive sort
        return getAuthorizedName().toUpperCase().compareTo(
                   ((ChemicalImpurity)o).getAuthorizedName().toUpperCase());
    	*/
    	
    	if (o == null) return -1;
    	return StringComparator.compare(getAuthorizedName(),
    			((ChemicalImpurity)o).getAuthorizedName());
    	
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
        buffer.append(", toxicityRestriction: ");
        buffer.append(getToxicityRestriction());
        
        return buffer.toString();
    }
}