/*
 * Created on 8-Jun-07
 *
 */
package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.MonographSubIngredientDose;
import ca.gc.hc.nhpd.util.ThreadContext;

/**
 * @author MRABE
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MonographSubIngredientDoseWS implements Comparable<MonographSubIngredientDoseWS> {

    private String version = ThreadContext.getInstance().getVersion();
	
    // The wrapped MonographSubIngredientDose
    private MonographSubIngredientDose monographSubIngredientDose;

    public MonographSubIngredientDoseWS(
            MonographSubIngredientDose monographSubIngredientDose) {
        if (monographSubIngredientDose == null)
            this.monographSubIngredientDose = new MonographSubIngredientDose();
        else
            this.monographSubIngredientDose = monographSubIngredientDose;
    }

    public MonographSubIngredientDoseWS() {
        monographSubIngredientDose = new MonographSubIngredientDose();
    }

    public MonographSubIngredientCombinationWS getMonographSubIngredientCombination() {
        return new MonographSubIngredientCombinationWS(
                monographSubIngredientDose.getSubIngredientCombination());
    }

    public void setMonographSubIngredientCombination(
            MonographSubIngredientCombinationWS monographSubIngredientCombination) {
        //
    }

    public Float getDoseMaximum() {
        return monographSubIngredientDose.getDoseMaximum();
    }

    public void setDoseMaximum(Float doseMaximum) {
        //
    }

    public Float getDoseMinimum() {
        return monographSubIngredientDose.getDoseMinimum();
    }

    public void setDoseMinimum(Float doseMinimum) {
        //
    }

    public UnitsWS getDoseUnits() {
        if (monographSubIngredientDose.getDoseUnits() == null)
            return new UnitsWS();
        return new UnitsWS(monographSubIngredientDose.getDoseUnits());
    }

    public void setDoseUnits(UnitsWS doseUnits) {
        //
    }

    public int getFrequencyMaximum() {
        return monographSubIngredientDose.getFrequencyMaximum();
    }

    public void setFrequencyMaximum(int frequencyMaximum) {
        //
    }

    public int getFrequencyMinimum() {
        return monographSubIngredientDose.getFrequencyMinimum();
    }

    public void setFrequencyMinimum(int frequencyMinimum) {
        //
    }

    public UnitsWS getFrequencyUnits() {
        if (monographSubIngredientDose.getFrequencyUnits() == null)
            return new UnitsWS();
        return new UnitsWS(monographSubIngredientDose.getFrequencyUnits());
    }

    public void setFrequencyUnits(UnitsWS frequencyUnits) {
        //
    }

    public RestrictionTypeWS getRestrictionType() {
        return new RestrictionTypeWS(monographSubIngredientDose
                .getRestrictionType());
    }

    public void setRestrictionType(RestrictionTypeWS restrictionType) {
        // 
    }

    /*
     * delegate the compareTo call to the wrapped object TODO - if the web
     * service exposes Lists rather than Sets, we could get rid of this
     * Comparable stuff. Note that order is preserved when transforming a Set to
     * an ArrayList...
     */
    public int compareTo(MonographSubIngredientDoseWS o) throws ClassCastException {
        return monographSubIngredientDose
                .compareTo(o.monographSubIngredientDose);
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
    	
        return monographSubIngredientDose.getId();
    }

    public void setId(Long id) {
        // Not Implemented.
    }
    
    public String getCode() {
        return monographSubIngredientDose.getCode();
    }
    
    public void setCode(String value) {
        // Not Implemented.
    }

}
