package ca.gc.hc.nhpd.webservice.bilingualmodel;

import ca.gc.hc.nhpd.model.HomeopathicFormula;
import ca.gc.hc.nhpd.model.HomeopathicGenericText;
import ca.gc.hc.nhpd.model.HomeopathicRole;
import ca.gc.hc.nhpd.model.HomeopathicSubstance;
import ca.gc.hc.nhpd.model.IngredientRole;
import ca.gc.hc.nhpd.model.Synonym;
import ca.gc.hc.nhpd.webservice.model.QualifiedSynonymWS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*******************************************************************************
 * This bilingual object represents a Homeopathic Substance that returns both
 * French and English attributes. This was created to support a web service feed
 * into NHPSAS.
 * 
 * Author: Germain Brunet Date: 2009-11-17
 */

public class BilingualHomeopathicSubstanceWS extends BilingualIngredientWS {

    // ~ Static fields/initializers
    // ---------------------------------------------

    private HomeopathicSubstance homeopathicSubstance;

    private HomeopathicFormula homeopathicFormula;

    // ~ Constructors
    // -----------------------------------------------------------

    public BilingualHomeopathicSubstanceWS(
            HomeopathicSubstance homeopathicSubstance) {
        super(homeopathicSubstance);
        if (homeopathicSubstance == null) {
            throw new IllegalArgumentException("homeopathicSubstance is null!");
        }
        this.homeopathicSubstance = homeopathicSubstance;
        init();
    }

    public BilingualHomeopathicSubstanceWS() {
        super(new HomeopathicSubstance());
        this.homeopathicSubstance = new HomeopathicSubstance();
    }

    /*----------------------------------------------------------------
     * Public Accessors/Mutators - methods exposed to the Web Service
     *----------------------------------------------------------------*/

    /**
     * Returns the source material associated with the Homeopathic Substance.
     */
    public List<String> getSourceMaterials() {
        if (homeopathicFormula == null
                || homeopathicFormula.getSourceMaterials() == null) {
            return null;
        }
        List<String> sourceMaterials = new ArrayList<String>();
        for (HomeopathicGenericText homeopathicSourceMaterial : homeopathicFormula
                .getSourceMaterials()) {
            sourceMaterials.add(homeopathicSourceMaterial.getText());
        }
        return sourceMaterials;
    }

    public void setSourceMaterials(List<String> value) {
        // Not Implemented.
    }

    /*----------------------------------------------------------------
     * Common Names
     *----------------------------------------------------------------*/

    /**
     * Returns / exposes the set of Common Names associated with this
     * ingredient.
     */
    public List<QualifiedSynonymWS> getCommonNames() {
        List<QualifiedSynonymWS> qualifiedSynonymsWS = new ArrayList<QualifiedSynonymWS>();
        for (Synonym commonName : homeopathicSubstance.getDisplayCommonNames()) {
            qualifiedSynonymsWS.add(new QualifiedSynonymWS(commonName));
        }
        return qualifiedSynonymsWS;
    }

    public void setCommonNames(List<QualifiedSynonymWS> commonNames) {
        // Not Implemented.
    }

    /*----------------------------------------------------------------
     * Proper Names
     *----------------------------------------------------------------*/

    /**
     * Returns / exposes the set of Proper Names associated with this
     * ingredient.
     */
    public List<QualifiedSynonymWS> getProperNames() {
        List<QualifiedSynonymWS> qualifiedSynonymsWS = new ArrayList<QualifiedSynonymWS>();
        for (Synonym properName : homeopathicSubstance.getDisplayProperNames()) {
            qualifiedSynonymsWS.add(new QualifiedSynonymWS(properName));
        }
        return qualifiedSynonymsWS;
    }

    public void setProperNames(List<QualifiedSynonymWS> properNames) {
        // Not Implemented.
    }

    /**
     * Leveraging a valid / available homeopathic substance, this init method
     * hooks into the associated homeopathic role. In this current version of
     * the Homeopathic Substance implementation, a Homeopathic Substance can
     * have only one homeopathic formula. This may change in the future.
     */
    private void init() {
        if (homeopathicSubstance != null) {
            Set<IngredientRole> roles = homeopathicSubstance.getRoles();
            if (roles != null) {
                HomeopathicRole homeopathicRole = (HomeopathicRole) roles
                        .iterator().next();
                if (homeopathicRole != null) {
                    Set<HomeopathicFormula> homeopathicFormulas = homeopathicRole
                            .getHomeopathicFormulas();
                    this.homeopathicFormula = (HomeopathicFormula) homeopathicFormulas
                            .iterator().next();
                }
            }
        }
    }

}
