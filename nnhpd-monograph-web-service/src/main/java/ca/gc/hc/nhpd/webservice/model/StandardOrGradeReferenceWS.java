/*
 * Created on 8-Jun-07
 *
 */
package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.StandardOrGradeReference;
import ca.gc.hc.nhpd.util.ThreadContext;

/**
 * @author GBRUNET
 * 
 * Wrapper for the Standard or Grade Reference class.
 */
public class StandardOrGradeReferenceWS {

	private String version = ThreadContext.getInstance().getVersion();
	
    // The wrapped Units
    private StandardOrGradeReference standardOrGradeReference;

    public StandardOrGradeReferenceWS(StandardOrGradeReference standardOrGradeReference) {
        // create interim objects to avoid JAX-WS NullPointerExceptions...
        if (standardOrGradeReference == null) {
            throw new IllegalArgumentException();
        } else
            this.standardOrGradeReference = standardOrGradeReference;
    }

    public StandardOrGradeReferenceWS() {
        standardOrGradeReference = new StandardOrGradeReference();
    }

    public String getCode() {
        if (standardOrGradeReference == null
                || standardOrGradeReference.getReference() == null) {
            return null;
        }
        return standardOrGradeReference.getReference().getCode();
    }

    public void setCode(String code) {
        //
    }

    public String getName() {
        if (standardOrGradeReference == null
                || standardOrGradeReference.getReference() == null
        		|| standardOrGradeReference.getReference().getCitation() == null) {
            return null;
        }
        
        return standardOrGradeReference.getReference().getCitation().getWorkTitle();
        
    }

    public void setName(String name) {
        //
    }

    public Double getMinimumDilutionAmount() {
    	
    	// New 2.8 functionality.  Null returned for 2.7.
    	if (version.equals(ThreadContext.VERSION_1_4)) {
    		return null;
    	}
    	
        if (standardOrGradeReference != null 
                && standardOrGradeReference.getMinimumDilutionAmount() != null) {
            return standardOrGradeReference.getMinimumDilutionAmount();
        }
        return null;
    }
    
    public void setMinimumDilutionAmount(Double value) {
        // not implemented.
    }
    
    public Boolean isHomeopathicStandardOrGrade() {
        return standardOrGradeReference.isHomeopathicStandardOrGrade();
    }

    public void setHomeopathicStandardOrGrade(Boolean standardOrGradeReference) {
        //
    }

}
