/*
 * Created on 8-Jun-07
 *
 */
package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.CommonTerm;
import ca.gc.hc.nhpd.util.StringComparator;

/**
 * @author GBRUNET
 * 
 * This object is used as a wrapper for CommonTerm
 */
public class CommonTermWS implements Comparable<CommonTermWS> {

    // The wrapped Units
    private CommonTerm commonTerm;

    public CommonTerm getCommonTerm() {
        return commonTerm;
    }
    
    public CommonTermWS(CommonTerm commonTerm) {
        if (commonTerm == null) {
            throw new IllegalArgumentException();
        } else {
            this.commonTerm = commonTerm;
        }
    }

    public CommonTermWS() {
        commonTerm = new CommonTerm();
    }

    public String getCommonTermType() {
        if (commonTerm != null
                && commonTerm.getCommonTermType() != null) {
            return commonTerm.getCommonTermType().getName();
        }
        return null;
    }
    
    public void setCommonTermType(String commonTermType) {
        // Not Implemented.
    }
    
    public String getCode() {
        return commonTerm.getCode();
    }

    public void setCode(String code) {
        // Not Implemented.
    }

    public String getName() {
        return commonTerm.getName();
    }

    public void setName(String name) {
        // Not Implemented.
    }

    /*
     * Method required because this object is added to a TreeSet Delegate to the
     * wrapped object. This method is locale specific and should sort the object
     * based on the language that is selected. This method leverages the
     * NhpClassification object compare method.
     */
    public int compareTo(CommonTermWS o) throws ClassCastException {
        return StringComparator.compare(commonTerm.getCode(),
                ((CommonTermWS) o).getCommonTerm().getCode());
    }
}
