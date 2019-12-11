/*
 * Created on 8-Jun-07
 *
 */
package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.Solvent;
import ca.gc.hc.nhpd.util.StringComparator;

/**
 * @author MRABE
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SolventWS implements Comparable<SolventWS>{

    // The wrapped Solvent
    private Solvent solvent;

    public SolventWS(Solvent solvent) {
        this.solvent = solvent;
    }

    public SolventWS() {
        solvent = new Solvent();
    }

    public String getCode() {
        return solvent.getCode();
    }

    public void setCode(String code) {
        //
    }

    public String getName() {
        return solvent.getName();
    }

    public void setName(String name) {
        //
    }

    /*
     * Implement Comparable compareTo method. Method required because this
     * object is added to a TreeSet Does an ignore case compare on the
     * Preparation Name
     */
    public int compareTo(SolventWS o) throws ClassCastException {
        return StringComparator.compare(solvent.getCode(),
                ((SolventWS) o).getCode());

    }
    
}
