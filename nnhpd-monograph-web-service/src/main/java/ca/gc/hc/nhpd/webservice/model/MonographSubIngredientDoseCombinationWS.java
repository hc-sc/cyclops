/*
 * Created on 8-Jun-07
 *
 */
package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.MonographSubIngredientDose;
import ca.gc.hc.nhpd.model.MonographSubIngredientDoseCombination;
import ca.gc.hc.nhpd.util.ThreadContext;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author MRABE
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MonographSubIngredientDoseCombinationWS implements Comparable<MonographSubIngredientDoseCombinationWS> {

    private String version = ThreadContext.getInstance().getVersion();
	
    // The wrapped MonographSubIngredientDose
    private MonographSubIngredientDoseCombination monographSubIngredientDoseCombination;

    // attrs to expose
    /*
     * main /max ratios primary, secondary subingredients.
     */

    public MonographSubIngredientDoseCombinationWS(
            MonographSubIngredientDoseCombination monographSubIngredientDoseCombination) {
        if (monographSubIngredientDoseCombination == null)
            this.monographSubIngredientDoseCombination = new MonographSubIngredientDoseCombination();
        else
            this.monographSubIngredientDoseCombination = monographSubIngredientDoseCombination;
    }

    public MonographSubIngredientDoseCombinationWS() {
        monographSubIngredientDoseCombination = new MonographSubIngredientDoseCombination();
    }

    /*
     * A MonographSubIngredientDoseCombination always has at least one
     * MonographSubIngredientDose
     */
    public Set<MonographSubIngredientDoseWS> getMonographSubIngredientDoses() {

        Set<MonographSubIngredientDoseWS> retMonoSubDoses = new TreeSet<MonographSubIngredientDoseWS>();

        // MonographSubIngredientDoses are contained by our wrapped
        // MonographDose.
        Set<MonographSubIngredientDose> monoSubDoses = monographSubIngredientDoseCombination
                .getMonographSubIngredientDoses();

        for (MonographSubIngredientDose msid : monoSubDoses) {
            retMonoSubDoses.add(new MonographSubIngredientDoseWS(msid));
        }
        return retMonoSubDoses;
    }

    public void setMonographSubIngredientDoses(
            Set<MonographSubIngredientDoseWS> monographSubIngredientDoses) {
        // void impl
    }

    /*
     * delegate the compareTo call to the wrapped object
     */
    public int compareTo(MonographSubIngredientDoseCombinationWS o) throws ClassCastException {
        return monographSubIngredientDoseCombination
                .compareTo(o.monographSubIngredientDoseCombination);
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
    
        return monographSubIngredientDoseCombination.getId();
    }

    public void setId(Long id) {
        //
    }
    
    public String getCode() {
        return monographSubIngredientDoseCombination.getCode();
    }
    
    public void setCode(String value) {
        // Not Implemented.
    }


}
