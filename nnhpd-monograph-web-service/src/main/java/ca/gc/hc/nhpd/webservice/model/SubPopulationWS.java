/*
 * Created on 8-Jun-07
 *
 */
package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.SubPopulation;
import ca.gc.hc.nhpd.util.ThreadContext;

/**
 * @author MRABE
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SubPopulationWS implements Comparable<SubPopulationWS> {

    private String version = ThreadContext.getInstance().getVersion();

    // The wrapped SubPopulation
    private SubPopulation subPopulation;

    public SubPopulationWS(SubPopulation subPop) {
        if (subPop == null) {
            subPopulation = new SubPopulation();
        } else {
            subPopulation = subPop;
        }
    }

    public SubPopulationWS() {
        subPopulation = new SubPopulation();
    }

    public int getMaximumAge() {
        return subPopulation.getMaximumAge();
    }

    public void setMaximumAge(int maximumAge) {
        //
    }

    public UnitsWS getMaximumAgeUnits() {
        return new UnitsWS(subPopulation.getMaximumAgeUnits());
    }

    public void setMaximumAgeUnits(UnitsWS maximumAgeUnits) {
        //
    }

    public int getMinimumAge() {
        return subPopulation.getMinimumAge();
    }

    public void setMinimumAge(int minimumAge) {
        //
    }

    public UnitsWS getMinimumAgeUnits() {
        return new UnitsWS(subPopulation.getMinimumAgeUnits());
    }

    public void setMinimumAgeUnits(UnitsWS minimumAgeUnits) {
        //
    }

    public String getName() {
        return subPopulation.getName();
    }

    public void setName(String name) {
        //
    }

    public String getCode() {
        return subPopulation.getCode();
    }
    
    public void setCode(String code) {
        // Do nothing.
    }
    
    /**
     * Returns this object's ID.  This functionality was removed with the web 
     * service updates and only returns the id for backwards compatibility with
     * the ePLA 1.4 form.
     * 
     * @return this object's ID.
     */
    public Long getId() {
    
    	if (!version.equals(ThreadContext.VERSION_1_4)) {
    		return null;
    	}
    	
        return subPopulation.getId();
    }

    public void setId(Long id) {
        //
    }

    /*
     * Implement Comparable compareTo method. Method required because this
     * object is added to a TreeSet Delegate comparison to the wrapped
     * subPopulation
     */
    public int compareTo(SubPopulationWS o) throws ClassCastException {

        return subPopulation.compareTo(o.subPopulation);

    }

}
