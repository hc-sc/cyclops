package ca.gc.hc.nhpd.model;

import java.util.Set;

/**
 * The RecommendedDietaryAllowances object is associated with MonographUse, 
 * ChemicalMonographEntry, Units and contains a collection of DietaryAllowances.
 * The purpose is to be able to provide the form with a list of acceptable 
 * minimum allowance values for specific sub-population group for validation
 * purposes.
 * 
 * Note:  This was created in response to CR-0047 (IMSD #2171)
 * 
 * @author GEBRUNET
 *
 */

public class RecommendedDietaryAllowance extends PersistentObject {

    private String code;
    private ChemicalMonographEntry chemicalMonographEntry;
    private Set<DietaryAllowance> dietaryAllowances;
    private MonographUse monographUse;
    private Units units;

    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public ChemicalMonographEntry getChemicalMonographEntry() {
        return chemicalMonographEntry;
    }
    
    public void setChemicalMonographEntry(ChemicalMonographEntry chemicalMonographEntry) {
        this.chemicalMonographEntry = chemicalMonographEntry;
    }

    public Set<DietaryAllowance> getDietaryAllowances() {
        return dietaryAllowances;
    }

    public void setDietaryAllowances(Set<DietaryAllowance> dietaryAllowances) {
        this.dietaryAllowances = dietaryAllowances;
    }
    
    public MonographUse getMonographUse() {
        return monographUse;
    }

    public void setMonographUse(MonographUse monographUse) {
        this.monographUse = monographUse;
    }
    
    public Units getUnits() {
        return units;
    }
    
    public void setUnits(Units units) {
        this.units = units;
    }
    
}
