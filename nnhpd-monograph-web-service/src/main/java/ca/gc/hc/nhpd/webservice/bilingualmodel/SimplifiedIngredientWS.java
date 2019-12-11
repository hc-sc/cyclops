package ca.gc.hc.nhpd.webservice.bilingualmodel;

import ca.gc.hc.nhpd.model.ChemicalSubstance;
import ca.gc.hc.nhpd.model.DefinedOrganismSubstance;
import ca.gc.hc.nhpd.model.HomeopathicSubstance;
import ca.gc.hc.nhpd.model.Ingredient;
import ca.gc.hc.nhpd.util.StringComparator;

/*******************************************************************************
 * This object is used to expose a simplified ingredient object model when
 * presenting an ingredient as a web service. This is used when ingredients are
 * exposed as sources ingredients by the BilingualChemicalSubstanceWS. It limits
 * the attributes being exposed, and presents the ingredient in a bilingual
 * form.
 * 
 * Author: Germain Brunet Date: 2009-11-17
 */

public class SimplifiedIngredientWS implements
        Comparable<SimplifiedIngredientWS> {

    // ~ Static fields/initializers
    // ---------------------------------------------

    private Ingredient ingredient;

    private String name;

    private String nameE;

    private String nameF;

    // ~ Constructors
    // -----------------------------------------------------------

    public SimplifiedIngredientWS(Ingredient ingredient) {
        if (ingredient == null) {
            throw new IllegalArgumentException("ingredient is null!");
        }
        this.ingredient = ingredient;
        init();
    }

    public SimplifiedIngredientWS() {
        this.ingredient = new ChemicalSubstance();
    }

    /*----------------------------------------------------------------
     * Public Accessors/Mutators - methods exposed to the Web Service
     *----------------------------------------------------------------*/

    /**
     * Returns / exposes the ingredient's ID.
     */
    public Long getId() {
        return ingredient.getId();
    }

    public void setId(Long id) {
        // Not Implemented.
    }

    /*----------------------------------------------------------------
     * Authorized Name
     *----------------------------------------------------------------*/

    /**
     * Returns / exposes the ingredient's Authorized Name. The name will be
     * returned in the language specified by the local settings requested by the
     * web service.
     * 
     * @return local specific authorized name.
     */
    public String getAuthorizedName() {
        return name;
    }

    public void setAuthorizedName(String name) {
        // Not Implemented.
    }

    /**
     * Returns / exposes the ingredient's English Authorized Name.
     * 
     * @return English authorized name.
     */
    public String getAuthorizedNameE() {
        return nameE;
    }

    public void setAuthorizedNameE(String nameE) {
        // Not Implemented.
    }

    /**
     * Returns / exposes the ingredient's French Authorized Name.
     * 
     * @return French authorized name.
     */
    public String getAuthorizedNameF() {
        return nameF;
    }

    public void setAuthorizedNameF(String nameF) {
        // Not Implemented.
    }

    /**
     * Method required because this object is added to a TreeSet Does an ignore
     * case compare on the Ingredient Authorized Name
     */
    public int compareTo(SimplifiedIngredientWS o) throws ClassCastException {
        return StringComparator.compare(getAuthorizedName(),
                ((SimplifiedIngredientWS) o).getAuthorizedName());
    }

    /**
     * Called upon construction to extract specific fields form the Ingredient /
     * Chemical Substance or DefinedOrganismSubstance.
     */
    private void init() {
        if (ingredient instanceof ChemicalSubstance) {
            ChemicalSubstance chemicalSubstance = (ChemicalSubstance) ingredient;
            nameE = chemicalSubstance.getAuthorizedNameE();
            nameF = chemicalSubstance.getAuthorizedNameF();
            name = chemicalSubstance.getAuthorizedName();
        } else if (ingredient instanceof DefinedOrganismSubstance) {
            DefinedOrganismSubstance definedOrganismSubstance = (DefinedOrganismSubstance) ingredient;
            nameE = definedOrganismSubstance.getAuthorizedNameE();
            nameF = definedOrganismSubstance.getAuthorizedNameF();
            name = definedOrganismSubstance.getAuthorizedName();
        } else if (ingredient instanceof HomeopathicSubstance) {
            HomeopathicSubstance homeopathicSubstance = (HomeopathicSubstance) ingredient;
            nameE = homeopathicSubstance.getAuthorizedNameE();
            nameF = homeopathicSubstance.getAuthorizedNameF();
            name = homeopathicSubstance.getAuthorizedName();
        }
    }

}