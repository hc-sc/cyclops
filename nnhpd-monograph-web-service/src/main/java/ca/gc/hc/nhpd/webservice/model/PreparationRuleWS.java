package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.PreparationRule;
import ca.gc.hc.nhpd.model.Solvent;
import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class exposes the preparation rule as a web service.
 * 
 * @author GEBRUNET
 */

public class PreparationRuleWS implements Comparable<PreparationRuleWS> {

    // the wrapped PreparationRule
    private PreparationRule preparationRule;

    public PreparationRuleWS(PreparationRule preparationRule) {
        if (preparationRule == null)
            this.preparationRule = new PreparationRule();
        else
            this.preparationRule = preparationRule;
    }

    public PreparationRuleWS() {
        this.preparationRule = new PreparationRule();
    }

    public String getCode() {
        return preparationRule.getCode();
    }

    public void setCode(String code) {
        // Not implemented.
    }

    public Boolean getFreshDryPresent() {
        return preparationRule.getFreshDryPresent();
    }
    
    public void setFreshDryPresent(Boolean freshDryPresent) {
        // Not implemented.
    }
    
    public Boolean getQuantityCrudeEquivalentPresent() {
        return preparationRule.getQuantityCrudeEquivalentPresent();
    }
    
    public void setQuantityCrudeEquivalentPresnet(Boolean quantityCrudeEquivalentPresent) {
        // Not implemented.
    }

    public String getRatio() {
        return preparationRule.getRatio();
    }
    
    public void setRatio(String ratio) {
        // Not implemented.
    }
    
    public Set<SolventWS> getRestrictedSolvents() {
        if (preparationRule.getRestrictedSolvents() == null) {
            return null;
        }
        Set<SolventWS> solventsWS = new TreeSet<SolventWS>();
        for (Solvent solvent : preparationRule.getRestrictedSolvents()) {
            solventsWS.add(new SolventWS(solvent));
        }
        return solventsWS;
    }
    
    public void setRestrictedSolvents(Set<SolventWS> solvents) {
        // Not implemented.
    }

    public Boolean getSolventPresent() {
        return preparationRule.getSolventPresent();
    }
    
    public void setSolventPresent(Boolean solventPresent) {
        // Not implemented.
    }
    
    public Boolean getStandardizedPresent() {
        return preparationRule.getStandardizedPresent();
    }
    
    public void setStandardizedPresent(Boolean standardizedPresent) {
        // Not implemented.
    }
        
    /*
     * Implement Comparable compareTo method. Method required because this
     * object is added to a TreeSet Does an ignore case compare on the
     * Preparation Name
     */
    public int compareTo(PreparationRuleWS o) throws ClassCastException {
        return StringComparator.compare(preparationRule.getCode(),
                ((PreparationRuleWS) o).getCode());

    }
}
