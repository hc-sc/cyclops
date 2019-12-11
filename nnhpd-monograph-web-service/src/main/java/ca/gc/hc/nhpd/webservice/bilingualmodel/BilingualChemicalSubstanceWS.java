package ca.gc.hc.nhpd.webservice.bilingualmodel;

import ca.gc.hc.nhpd.model.ChemicalSubstance;

/*******************************************************************************
 * This bilingual object represents a Chemical Substance that returns both
 * French and English attributes. This was created to support a web service feed
 * into NHPSAS.
 * 
 * Author: Germain Brunet Date: 2009-11-17
 */

public class BilingualChemicalSubstanceWS extends BilingualIngredientWS {

    // ~ Static fields/initializers
    // ---------------------------------------------

    private ChemicalSubstance chemicalSubstance;

    // ~ Constructors
    // -----------------------------------------------------------

    public BilingualChemicalSubstanceWS(ChemicalSubstance chemicalSubstance) {
        super(chemicalSubstance);
        if (chemicalSubstance == null) {
            throw new IllegalArgumentException("chemicalSubstance is null!");
        }
        this.chemicalSubstance = chemicalSubstance;
    }

    public BilingualChemicalSubstanceWS() {
        super(new ChemicalSubstance());
        this.chemicalSubstance = new ChemicalSubstance();
    }

    /*----------------------------------------------------------------
     * Public Accessors/Mutators - methods exposed to the Web Service
     *----------------------------------------------------------------*/
    
    //These attributes currently not required for Ingredient Synchronization.  
    /**
     * Returns source organism parts associated with the Chemical Substance.
    
    public Set<OrganismPartWS> getSourceOrganismParts() {
        Set<OrganismPartWS> organismParts = new TreeSet<OrganismPartWS>();
        Set<OrganismPart> orgParts = chemicalSubstance
                .getRelatedSourceOrganismParts();
        if (orgParts != null) {
            for (OrganismPart organismPart : orgParts) {
                organismParts.add(new OrganismPartWS(organismPart, true));
            }
        }
        return organismParts;
    }

    public void setSourceOrganismParts(Set<OrganismPartWS> sourceOrganismParts) {
        // Not Implemented.
    }

    /**
     * Returns related source ingredients associated with the Chemical
     * Substance.
    
    public Set<SimplifiedIngredientWS> getRelatedSourceIngredients() {
        Set<SimplifiedIngredientWS> relatedIngredients = new TreeSet<SimplifiedIngredientWS>();
        Set<Ingredient> ingredients = chemicalSubstance
                .getRelatedSourceIngredients();
        for (Ingredient ingredient : ingredients) {
            relatedIngredients.add(new SimplifiedIngredientWS(ingredient));
        }
        return relatedIngredients;
    }

    public void setRelatedSourceIngredients(
            Set<SimplifiedIngredientWS> relatedIngredients) {
        // Not Implemented.
    }
	*/
}
