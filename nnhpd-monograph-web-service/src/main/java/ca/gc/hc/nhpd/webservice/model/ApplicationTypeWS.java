/*
 * Created on 8-Jun-07
 *
 */
package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.ApplicationType;
import ca.gc.hc.nhpd.util.StringComparator;

/**
 * @author MRABE
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ApplicationTypeWS implements Comparable<ApplicationTypeWS>{

    // The wrapped Application Type
    private ApplicationType applicationType;

    public ApplicationTypeWS(ApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    public ApplicationTypeWS() {
        applicationType = new ApplicationType();
    }

    public String getCode() {
        return applicationType.getCode();
    }

    public void setCode(String code) {
        //
    }

    public String getName() {
        return applicationType.getName();
    }

    public void setName(String name) {
        //
    }

    /*
     * Implement Comparable compareTo method. Method required because this
     * object is added to a TreeSet Does an ignore case compare on the
     * Preparation Name
     */
    public int compareTo(ApplicationTypeWS o) throws ClassCastException {
        return StringComparator.compare(applicationType.getCode(),
                ((ApplicationTypeWS) o).getCode());

    }
    
}
