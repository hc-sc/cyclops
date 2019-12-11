package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.NonMedicinalPurpose;
import ca.gc.hc.nhpd.model.NonMedicinalRole;
import ca.gc.hc.nhpd.model.NonMedicinalRestriction;
import ca.gc.hc.nhpd.model.Restriction;
import ca.gc.hc.nhpd.util.StringComparator;
import ca.gc.hc.nhpd.util.ThreadContext;

import java.util.Set;
import java.util.TreeSet;

/*******************************************************************************
 * Web Service wrapper for NonMedicinalRole.
 * @author MRABE
 * @version 1.0
 * 
 */
public class NonMedicinalRoleWS implements Comparable<NonMedicinalRoleWS> {

    // the wrapped NonMedicinalRole
    private NonMedicinalRole nonMedicinalRole;

    public NonMedicinalRoleWS() {
    	nonMedicinalRole = new NonMedicinalRole();
    }

    public NonMedicinalRoleWS(NonMedicinalRole nonMedicinalRole) {
        this.nonMedicinalRole = nonMedicinalRole;
    }
    
    public Set<NonMedicinalRestrictionWS> getRestrictions() {
    	Set<NonMedicinalRestrictionWS> nmRestrictions = 
    		new TreeSet<NonMedicinalRestrictionWS>();
    	
    	for (Restriction restriction: nonMedicinalRole.getRestrictions()) {
    		//should always be, but lets be safe...
    		if (restriction instanceof NonMedicinalRestriction) {
    			nmRestrictions.add(new NonMedicinalRestrictionWS((NonMedicinalRestriction)restriction));
    		}
    	}
    	return nmRestrictions;
    	
    }
    
    public void setRestrictions(Set<NonMedicinalRestrictionWS> restrictions) {
    	//null impl
    }
    
    
    public Set<NonMedicinalPurposeWS> getNonMedicinalPurposes() {
    	Set<NonMedicinalPurposeWS> nmPurposes = new TreeSet<NonMedicinalPurposeWS>();
    	for (NonMedicinalPurpose nmPurpose : nonMedicinalRole.getPurposes()) {
    		nmPurposes.add(new NonMedicinalPurposeWS(nmPurpose));
    	}
    	return nmPurposes;
    }
    
    public void setNonMedicinalPurposes(Set<NonMedicinalPurposeWS> nonMedicinalPurposes) {
    	//null impl
    }

    //Comparable compareTo impl;  NonMedicinalRoleWS is contained in a Set.
    public int compareTo(NonMedicinalRoleWS o) throws ClassCastException {

        if (nonMedicinalRole == null) {
            return 1;
        }
        if (o == null || o.getNonMedicinalPurposes() == null) {
            return -1;
        }

        return 0;
    }
}
