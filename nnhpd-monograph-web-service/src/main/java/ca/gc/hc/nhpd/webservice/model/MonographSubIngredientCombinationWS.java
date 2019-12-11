/*
 * Created on 8-Jun-07
 *
 */
package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.MonographSubIngredientCombination;
import ca.gc.hc.nhpd.util.ThreadContext;

/**
 * @author MRABE
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MonographSubIngredientCombinationWS implements Comparable<MonographSubIngredientCombinationWS> {

    private String version = ThreadContext.getInstance().getVersion();
	
    // The wrapped MonographSubIngredientDose
    private MonographSubIngredientCombination monographSubIngredientCombination;

    public MonographSubIngredientCombinationWS(
            MonographSubIngredientCombination monographSubIngredientCombination) {
        if (monographSubIngredientCombination == null)
            this.monographSubIngredientCombination = new MonographSubIngredientCombination();
        else
            this.monographSubIngredientCombination = monographSubIngredientCombination;
    }

    public MonographSubIngredientCombinationWS() {
        monographSubIngredientCombination = new MonographSubIngredientCombination();
    }

    public SubIngredientWS getPrimarySubIngredient() {
        return new SubIngredientWS(monographSubIngredientCombination
                .getPrimarySubIngredient());
    }

    public SubIngredientWS getSecondarySubIngredient() {
        return new SubIngredientWS(monographSubIngredientCombination
                .getSecondarySubIngredient());
    }

    public void setPrimarySubIngredient(SubIngredientWS subIngredient) {
        // null impl
    }

    public void setSecondarySubIngredient(SubIngredientWS subIngredient) {
        // null impl
    }

    public Float getMinimumRatio() {
        return monographSubIngredientCombination.getMinimumRatio();
    }

    public Float getMaximumRatio() {
        return monographSubIngredientCombination.getMaximumRatio();
    }

    public void setMinimumRatio(Float value) {
        // null impl
    }

    public void setMaximumRatio(Float value) {
        // null impl
    }

    /*
     * delegate the compareTo call to the wrapped object TODO - if the web
     * service exposes Lists rather than Sets, we could get rid of this
     * Comparable stuff. Note that order is preserved when transforming a Set to
     * an ArrayList...
     */
    public int compareTo(MonographSubIngredientCombinationWS o) throws ClassCastException {
        return monographSubIngredientCombination
                .compareTo(o.monographSubIngredientCombination);
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
    
        return monographSubIngredientCombination.getId();
    }

    public void setId(Long id) {
        //
    }
    
    public String getCode() {
        return monographSubIngredientCombination.getCode();
    }
    
    public void setCode(String value) {
        // Not Implemented.
    }

}
