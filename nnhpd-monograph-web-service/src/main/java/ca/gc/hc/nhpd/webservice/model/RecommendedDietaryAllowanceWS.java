package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.ChemicalMonographEntry;
import ca.gc.hc.nhpd.model.ChemicalSubstance;
import ca.gc.hc.nhpd.model.DietaryAllowance;
import ca.gc.hc.nhpd.model.Ingredient;
import ca.gc.hc.nhpd.model.RecommendedDietaryAllowance;
import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Set;
import java.util.TreeSet;

public class RecommendedDietaryAllowanceWS implements Comparable<RecommendedDietaryAllowanceWS> {

    // Wrap the recommended dietary allowance
    private RecommendedDietaryAllowance recommendedDietaryAllowance;

    public RecommendedDietaryAllowanceWS(RecommendedDietaryAllowance recommendedDietaryAllowance) {
        if (recommendedDietaryAllowance == null) {
            throw new IllegalArgumentException("recommendedDietaryAllowance is null!");
        }
        this.recommendedDietaryAllowance = recommendedDietaryAllowance;
    }

    public RecommendedDietaryAllowanceWS() {
        this.recommendedDietaryAllowance = new RecommendedDietaryAllowance();
    }

    public String getCode() {
        return recommendedDietaryAllowance.getCode();
    }

    public void setCode(String code) {
        recommendedDietaryAllowance.setCode(code);
    }

    public String getIngredientAuthorizedName() {
        if (recommendedDietaryAllowance.getChemicalMonographEntry() != null) {
            ChemicalMonographEntry chemicalMonographEntry = recommendedDietaryAllowance.getChemicalMonographEntry();
            if (chemicalMonographEntry.getIngredient() != null) {
                Ingredient ingredient = chemicalMonographEntry.getIngredient();
                if (ingredient instanceof ChemicalSubstance) {
                    ChemicalSubstance chemicalSubstance = (ChemicalSubstance) ingredient;
                    return chemicalSubstance.getAuthorizedName();
                }
            }
        }
        return null;
    }

    public void setIngredientAuthorizedName(String ingredientAuthorizedName) {
        //
    }
    
    public UnitsWS getUnits() {
        return new UnitsWS(recommendedDietaryAllowance.getUnits());
    }
    

    public void setUnits(UnitsWS units) {
        //
    }

    public Set<DietaryAllowanceWS> getDietaryAllowances() {
        Set<DietaryAllowanceWS> dietaryAllowances = new TreeSet<DietaryAllowanceWS>();
        for (DietaryAllowance dietaryAllowance : recommendedDietaryAllowance.getDietaryAllowances()) {
            dietaryAllowances.add(new DietaryAllowanceWS(dietaryAllowance));
        }
        return dietaryAllowances;
    }

    public void setDietaryAllowances(Set<DietaryAllowanceWS> dietaryAllowances) {
        // 
    }

    /**
     * Implements Comparable compareTo method.  Method required because this
     * object is added to a TreeSet.  Does an ignore case compare on the
     * Ingredient's Authorized Name
     */
    public int compareTo(RecommendedDietaryAllowanceWS o) throws ClassCastException {
        return StringComparator.compare(getIngredientAuthorizedName(),
                o.getIngredientAuthorizedName());
    }
    
}
