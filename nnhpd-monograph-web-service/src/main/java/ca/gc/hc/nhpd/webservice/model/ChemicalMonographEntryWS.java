package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.ChemicalMonographEntry;
import ca.gc.hc.nhpd.model.MonographSourceIngredient;
import ca.gc.hc.nhpd.model.MonographSourceOrganismPart;
import ca.gc.hc.nhpd.model.TextMonographSourceMaterial;
import java.util.Set;
import java.util.TreeSet;

public class ChemicalMonographEntryWS extends MonographEntryWS {
    // the wrapped ChemicalMonographEntry
    private ChemicalMonographEntry chemicalMonographEntry;

    public ChemicalMonographEntryWS(
            ChemicalMonographEntry chemicalMonographEntry) {
        super(chemicalMonographEntry);
        this.chemicalMonographEntry = chemicalMonographEntry;
    }

    public ChemicalMonographEntryWS() {
        super(new ChemicalMonographEntry());
        this.chemicalMonographEntry = new ChemicalMonographEntry();
    }

    /*
     * We can expose the Source Ingredients as a Set of Strings here; all we
     * need is the approved name.
     */
    public Set<MonographSourceIngredientWS> getMonographSourceIngredients() {

        Set<MonographSourceIngredientWS> sourceIngredients = new TreeSet<MonographSourceIngredientWS>();

        // wrap up the ingredients
        Set<MonographSourceIngredient> monoSourceIngredients = chemicalMonographEntry
                .getMonographSourceIngredients();
        if (monoSourceIngredients != null) {
            for (MonographSourceIngredient msi : monoSourceIngredients) {
                sourceIngredients.add(new MonographSourceIngredientWS(msi));
            }
        }

        return sourceIngredients;

    }

    public void setMonographSourceIngredients(Set<MonographSourceIngredientWS> monographSourceIngredients) {
        //
    }

    public Set<MonographSourceOrganismPartWS> getMonographSourceOrganismParts() {
        Set<MonographSourceOrganismPartWS> sourceOrgPartsWS = new TreeSet<MonographSourceOrganismPartWS>();

        Set<MonographSourceOrganismPart> msops = chemicalMonographEntry
                .getMonographSourceOrganismParts();
        if (msops != null) {
            for (MonographSourceOrganismPart msop : msops) {
                sourceOrgPartsWS.add(new MonographSourceOrganismPartWS(msop));
            }
        }

        return sourceOrgPartsWS;

    }

    /*
     * Retrieve Source OrganismParts (also known as Source Materials)
     * 
     * public Set<OrganismPartWS> getSourceOrganismParts() { Set<OrganismPartWS>
     * organismPartsWS = new TreeSet<OrganismPartWS>(); //Set orgParts =
     * chemicalMonographEntry.getSourceMaterials(); //wrap up the org parts
     * OrganismPart orgPart = null; chemicalMonographEntry. Iterator iter =
     * chemicalMonographEntry.getSourceMaterials().iterator(); while
     * (iter.hasNext()) { orgPart = (OrganismPart)(iter.next());
     * organismPartsWS.add(new OrganismPartWS(orgPart)); }
     * 
     * return organismPartsWS; }
     */

    public void setMonographSourceOrganismParts(
            Set<MonographSourceOrganismPartWS> sourceOrganismParts) {
        // no impl
    }

    public Set<TextMonographSourceMaterialWS> getOtherMonographSources() {
        Set<TextMonographSourceMaterialWS> testMonoSources = new TreeSet<TextMonographSourceMaterialWS>();
        Set<TextMonographSourceMaterial> tmsms = chemicalMonographEntry
                .getOtherMonographSources();
        if (tmsms != null) {
            for (TextMonographSourceMaterial tmsm : tmsms) {
                testMonoSources.add(new TextMonographSourceMaterialWS(tmsm));
            }
        }

        return testMonoSources;
    }

    /*
     * public Set<String> getOtherSources() { Set<String> retOtherSources =
     * new TreeSet<String>();
     * 
     * //extract the String from GenericText GenericText genericText = null;
     * Iterator iter = chemicalMonographEntry.getOtherSources().iterator();
     * while (iter.hasNext()) { genericText = (GenericText)(iter.next());
     * retOtherSources.add(new String(genericText.getText())); }
     * 
     * return retOtherSources; }
     */

    public void setOtherMonographSources(
            Set<TextMonographSourceMaterialWS> otherSources) {
        // no impl
    }

}
