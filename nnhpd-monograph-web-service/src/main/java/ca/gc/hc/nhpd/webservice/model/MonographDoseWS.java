/*
 * Created on 8-Jun-07
 *
 */
package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.MonographDirectionForUse;
import ca.gc.hc.nhpd.model.MonographSubIngredientDoseCombination;
import ca.gc.hc.nhpd.model.MonographDose;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author MRABE
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MonographDoseWS implements Comparable<MonographDoseWS> {

    // The wrapped MonographDose
    private MonographDose monographDose;

    // The associated dependencies
    private MonographDependenciesWS monographDependenciesWS;

    public MonographDependenciesWS getMonographDependenciesWS() {
        MonographDependenciesWS monographDependenciesWS = new MonographDependenciesWS(monographDose);
        if (monographDependenciesWS.hasDependency()) {
            return monographDependenciesWS;
        }
        return null;
    }

    public void setMonographDependenciesWS(
            MonographDependenciesWS monographDependenciesWS) {
        // this.monographDependenciesWS = monographDependenciesWS;
    }

    public MonographDoseWS(MonographDose monographDose) {

        // ensure that the monographDose contained by the
        // MonographDependenciesWS
        // is not null - JAXB doesn't like null objects...
        if (monographDose == null)
            this.monographDose = new MonographDose();
        else
            this.monographDose = monographDose;
        this.monographDependenciesWS = new MonographDependenciesWS(
                this.monographDose);

    }

    public MonographDoseWS() {
        monographDose = new MonographDose();
        monographDependenciesWS = new MonographDependenciesWS(
                this.monographDose);
    }

    /*
     * Returns the Set of MonographDirectionForUseWS wrappers. These wrappers
     * may also have dependencies.
     */
    public Set<MonographDirectionForUseWS> getMonographDirectionsForUse() {
        Set<MonographDirectionForUseWS> retMonoDirections = new TreeSet<MonographDirectionForUseWS>();

        Set<MonographDirectionForUse> monoDirections = monographDose
                .getMonographDirectionsForUse();

        if (monoDirections != null) {
            for (MonographDirectionForUse monoDirection : monoDirections) {
                retMonoDirections.add(new MonographDirectionForUseWS(
                        monoDirection));
            }
        }

        return retMonoDirections;
    }

    public void setMonographDirectionsForUse(
            Set<MonographDirectionForUseWS> directions) {
        //
    }

    public Set<MonographSubIngredientDoseCombinationWS> getMonographSubIngredientDoseCombinations() {

        Set<MonographSubIngredientDoseCombinationWS> msidcs = new TreeSet<MonographSubIngredientDoseCombinationWS>();

        for (MonographSubIngredientDoseCombination msidc : monographDose
                .getMonographSubIngredientDoseCombinations()) {
            msidcs.add(new MonographSubIngredientDoseCombinationWS(msidc));
        }

        return msidcs;
    }

    public void setMonographSubIngredientDoseCombinations(
            Set<MonographSubIngredientDoseCombinationWS> msidcs) {
        //
    }

    public Float getDoseMaximum() {
        return monographDose.getDoseMaximum();
    }

    public void setDoseMaximum(Float doseMaximum) {
        //
    }

    public Float getDoseMinimum() {
        return monographDose.getDoseMinimum();
    }

    public void setDoseMinimum(Float doseMinimum) {
        //
    }

    /*
     * This method was changed to return the default dose units of "g"
     * when the getDoseUnits returns a value of null.  This is in 
     * response to IMSD #2055.  This solution is temporary and we should 
     * implement this business logic in another location within the
     * application.
     * 
     * TODO - This should be revisited once we have access to make changes
     * to the data model.
     */
    public UnitsWS getDoseUnits() {
        if (monographDose.getDoseUnits() == null)
            // return new UnitsWS();
            return new UnitsWS(monographDose.getDefaultDoseUnits());
        return new UnitsWS(monographDose.getDoseUnits());
    }

    public void setDoseUnits(UnitsWS doseUnits) {
        //
    }

    public int getFrequencyMaximum() {
        return monographDose.getFrequencyMaximum();
    }

    public void setFrequencyMaximum(int frequencyMaximum) {
        //
    }

    public int getFrequencyMinimum() {
        return monographDose.getFrequencyMinimum();
    }

    public void setFrequencyMinimum(int frequencyMinimum) {
        //
    }

    public UnitsWS getFrequencyUnits() {
        if (monographDose.getFrequencyUnits() == null)
            return new UnitsWS();
        return new UnitsWS(monographDose.getFrequencyUnits());
    }

    public void setFrequencyUnits(UnitsWS frequencyUnits) {
        //
    }

    /*
     * delegate the compareTo call to the wrapped object TODO - if the web
     * service exposes Lists rather than Sets, we could get rid of this
     * Comparable stuff. Note that order is preserved when transforming a Set to
     * an ArrayList...
     */
    public int compareTo(MonographDoseWS o) throws ClassCastException {
        return monographDose.compareTo(o.monographDose);
    }

    public RestrictionTypeWS getRestrictionType() {
        return new RestrictionTypeWS(monographDose.getRestrictionType());
    }

    public void setRestrictionType(RestrictionTypeWS restrictionType) {
        // 
    }

    public String getCode() {
        return monographDose.getCode();
    }
    
    public void setCode(String code) {
        //
    }
    
}
