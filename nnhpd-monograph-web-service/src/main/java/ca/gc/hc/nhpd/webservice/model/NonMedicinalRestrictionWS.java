package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.NonMedicinalPurpose;
import ca.gc.hc.nhpd.model.NonMedicinalRestriction;
import ca.gc.hc.nhpd.util.StringComparator;
import ca.gc.hc.nhpd.util.ThreadContext;

import java.util.Set;
import java.util.TreeSet;

/*******************************************************************************
 * Web Service wrapper for NonMedicinalRestriction.
 * 
 * @version 1.0
 */
public class NonMedicinalRestrictionWS implements Comparable<NonMedicinalRestrictionWS> {

    private static final String TRUE = "true";
    private static final String FALSE = "false";
    
	private String version = ThreadContext.getInstance().getVersion();

    // the wrapped NonMedicinalRestriction
    private NonMedicinalRestriction nonMedicinalRestriction;

    public NonMedicinalRestrictionWS() {
    	nonMedicinalRestriction = new NonMedicinalRestriction();
    }

    public NonMedicinalRestrictionWS(NonMedicinalRestriction nonMedicinalRestriction) {
        this.nonMedicinalRestriction = nonMedicinalRestriction;
    }

    public Set<NonMedicinalPurposeWS> getAllowedPurposes() {
    	Set<NonMedicinalPurposeWS> allowedPurposes = 
    		new TreeSet<NonMedicinalPurposeWS>();
    	for (NonMedicinalPurpose nonMedPurpose: nonMedicinalRestriction.getAllowedPurposes()) {
    		allowedPurposes.add(new NonMedicinalPurposeWS(nonMedPurpose));
    	}
    	return allowedPurposes;
    			
    }
    public void setAllowedPurposes(Set<NonMedicinalPurposeWS> allowedPurposes) {
    	//null impl for JAXB/JAXWS
    }
    
    /**
     * Returns the restriction text details. 
     * 
     * @return the restriction text details. 
     */
    public String getDetails() {
    	
    	// Return null for 2.0.
    	if (version.equals(ThreadContext.VERSION_2_0)) {
    		return null;
    	}
    	
        return nonMedicinalRestriction.getDetail();
    }

    public void setDetails(String name) {
        // Not Implemented.
    }

    public String getType() {
    	if (nonMedicinalRestriction.getType() == null) {
    		return null;
    	}	
    	return nonMedicinalRestriction.getType().getName();
    }
    
    public void setType(String type) {
    	//null impl for JAXB/JAXWS
    }
    
    public String getRouteOfAdmin() {
    	if (nonMedicinalRestriction.getRouteOfAdministration() == null) {
    		return null;
    	}
    	return nonMedicinalRestriction.getRouteOfAdministration().getName();
    }

    public void setRouteOfAdmin(String routeOfAdmin) {
    	//null impl for JAXB/JAXWS
    }
    
    public String getSafetyConcern() {
    	
    	// New 2.8 functionality.  Null returned for 2.7.
    	if (version.equals(ThreadContext.VERSION_1_4)) {
    		return null;
    	}
    	
        if (nonMedicinalRestriction.getSafetyConcern() != null 
                && nonMedicinalRestriction.getSafetyConcern() == true) {
            return TRUE;
        }
        return FALSE;
    }
    
    public void setSafetyConcern(String value) {
        // null impl for JAXB/JAXWS
    }
   
    //Comparable compareTo impl;  NonMedicinalRestrictionWS is contained in a Set.
    public int compareTo(NonMedicinalRestrictionWS o) throws ClassCastException {

        if (nonMedicinalRestriction == null) {
            return 1;
        }
        if (o == null || o.getDetails() == null) {
            return -1;
        }

        return StringComparator.compare(getDetails(),
                ((NonMedicinalRestrictionWS) o).getDetails());
    }

   

}