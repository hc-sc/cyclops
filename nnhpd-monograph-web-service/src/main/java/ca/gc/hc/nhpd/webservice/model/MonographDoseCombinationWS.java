package ca.gc.hc.nhpd.webservice.model;

import java.util.Set;
import java.util.TreeSet;

import ca.gc.hc.nhpd.model.MonographDose;
import ca.gc.hc.nhpd.model.MonographDoseCombination;

public class MonographDoseCombinationWS implements Comparable<MonographDoseCombinationWS> {

    // The wrapped MonographDoseCombination
    private MonographDoseCombination monographDoseCombination;

  
    /*
     * NB. New pattern: do not call the MonographDependenciesWS
     * constructor here; this could lead to an infinite loop in 
     * the wrapper constructors for cases where there are circular
     * dependencies.  For example; Fish Oil has circular dependencies
     * where MonographDoseCombinations have dependencies on MongraphUses 
     * and vice versa.
     */
    public MonographDoseCombinationWS(
            MonographDoseCombination monographDoseCombination,
            boolean includeDependencies) {
        this.monographDoseCombination = monographDoseCombination;
        
    }

    public MonographDoseCombinationWS() {
        monographDoseCombination = new MonographDoseCombination();
    }
    
    
    public boolean isVisible() {
        return monographDoseCombination.isVisible();
    }

    public void setVisible(boolean visible) {
        //
    }

    public String getCode() {
        return monographDoseCombination.getCode();
    }

    public void setCode(String code) {
        //
    }
  

    public Set<MonographDoseWS> getMonographDoses() {
        Set<MonographDoseWS> monoDoses = new TreeSet<MonographDoseWS>();
        for (MonographDose monoDose : monographDoseCombination
                .getMonographDoses()) {
            monoDoses.add(new MonographDoseWS(monoDose));
        }
        return monoDoses;
    }

    public void setMonographDoses(Set<MonographDoseWS> moonDoses) {
        //
    }
    
    
    /*
     * Dependencies are comprised of a set of base class attributes of the mdo.
     * 
     * We'll build a distinct MonographDependencies object for the web service,
     * and initialize it on demand.
     */
    public MonographDependenciesWS getMonographDependenciesWS() {
        MonographDependenciesWS monographDependenciesWS = new MonographDependenciesWS(monographDoseCombination);
        if (monographDependenciesWS.hasDependency()) {
            return monographDependenciesWS;
        }
        return null;

    }

    public void setMonographDependenciesWS(
            MonographDependenciesWS monographDependenciesWS) {
        //No impl: for JAXB only
    }
    
     
    public int compareTo(MonographDoseCombinationWS mdcWS) throws ClassCastException {
        return monographDoseCombination
                .compareTo(mdcWS.monographDoseCombination);

    }

}
