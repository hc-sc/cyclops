package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.HomeopathicDilution;
import ca.gc.hc.nhpd.util.StringComparator;
import ca.gc.hc.nhpd.util.ThreadContext;

/*******************************************************************************
 * This is a web service wrapper class used to represent a homeopathic substance
 * ingredient. It can be constructed by passing a homeopathic substance in the
 * constructor, or as a new homeopathic substance when the constructor is left
 * empty.
 */

public class HomeopathicDilutionWS implements Comparable<HomeopathicDilutionWS> {

    private static final String TRUE = "true";
    private static final String FALSE = "false";
    
    private String version = ThreadContext.getInstance().getVersion();
    
    // ~ Static fields/initializers
    // ---------------------------------------------

    private HomeopathicDilution homeopathicDilution;

    // ~ Constructors
    // -----------------------------------------------------------

    /**
     * Constructor used to instantiate this class with an instance of the
     * homeopathic dilution.
     * 
     * @param homeopathicDilution
     *            being used to instantiate this class.
     */
    public HomeopathicDilutionWS(HomeopathicDilution homeopathicDilution) {
        if (homeopathicDilution == null) {
            throw new IllegalArgumentException("homeopathicSubstance is null!");
        }
        this.homeopathicDilution = homeopathicDilution;
    }

    /**
     * Constructor used to instantiate this class. When called, the homeopathic
     * dilution is instantiated as a new object.
     */
    public HomeopathicDilutionWS() {
        this.homeopathicDilution = new HomeopathicDilution();
    }

    /*----------------------------------------------------------------
     * Public Accessors/Mutators - methods exposed to the Web Service
     *----------------------------------------------------------------*/

    public String getCode() {
        return homeopathicDilution.getCode();
    }
    
    public void setCode(String code) {
        // Not Implemented.
    }
    
    public String getDiluted() {
        if (homeopathicDilution != null
                && homeopathicDilution.getDiluted() != null
                && homeopathicDilution.getDiluted() == true) {
            return TRUE;
        }
        return FALSE;
    }
    
    public void setDiluted(String diluted) {
        //
    }
    
    public String getName() {
        return homeopathicDilution.getName();
    }
    
    public void setName(String name) {
        // Not Implemented.
    }
    
    public Double getRatioToBase() {
    	
    	// New 2.8 functionality.  Null returned for 2.7.
    	if (version.equals(ThreadContext.VERSION_1_4)) {
    		return null;
    	}
    	
        return homeopathicDilution.getRatioToBase();
    }
    
    public void setRatioToBase(Double ratioToBase) {
        // No Implemented.
    }
    
    /**
     * Comparator that uses the getHomeopathicDilution to sort and
     * ensure that we do not get duplicate values.
     */
    public int compareTo(HomeopathicDilutionWS o)
            throws ClassCastException {

        if (getCode() == null) {
            return 1;
        }
        if (o == null || o.getCode() == null) {
            return -1;
        }

        return StringComparator.compare(getCode(), o.getCode());
    }
}
