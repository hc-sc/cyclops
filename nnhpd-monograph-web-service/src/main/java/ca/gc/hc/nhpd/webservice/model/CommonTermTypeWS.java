/*
 * Created on 8-Jun-07
 *
 */
package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.CommonTerm;
import ca.gc.hc.nhpd.model.CommonTermType;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author GBRUNET
 * 
 * Wrapper to the Common Term Type object.
 */
public class CommonTermTypeWS {

    // The wrapped Units
    private CommonTermType commonTermType;

    public CommonTermTypeWS(CommonTermType commonTermType) {
        if (commonTermType == null) {
            throw new IllegalArgumentException();
        } else
            this.commonTermType = commonTermType;
    }

    public CommonTermTypeWS() {
        commonTermType = new CommonTermType();
    }
    
    public String getName() {
        return commonTermType.getName();
    }

    public void setName(String name) {
        //
    }

    public Set<CommonTermWS> getCommonTerms() {
        TreeSet<CommonTermWS> commonTerms = new TreeSet<CommonTermWS>();
        if (commonTermType.getCommonTerms() != null
                && commonTermType.getCommonTerms().size() != 0) {
            for (CommonTerm commonTerm : commonTermType.getCommonTerms()) {
                commonTerms.add(new CommonTermWS(commonTerm));
            }
        }
        return commonTerms;
    }

    public void setCommonTerms(Set<CommonTermWS> commonTerms) {
        //
    }

}
