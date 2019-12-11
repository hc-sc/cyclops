package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.CustomOrganismSubstance;

import java.util.List;

/*******************************************************************************
 * An object that represents an organism substance ingredient, which is a part
 * of an Organism that has been prepared in a specific way.
 */

public class CustomOrganismSubstanceWS extends IngredientWS {

    // The wrapped CustomOrganismSubstance
    private CustomOrganismSubstance customOrganismSubstance;

    public CustomOrganismSubstanceWS(
            CustomOrganismSubstance customOrganismSubstance, boolean bilingual) {
        super(customOrganismSubstance);
        this.customOrganismSubstance = customOrganismSubstance;
        this.bilingual = bilingual;
    }

    public CustomOrganismSubstanceWS() {
        super(new CustomOrganismSubstance());
        this.customOrganismSubstance = new CustomOrganismSubstance();
    }

    /***************************************************************************
     * Gets the name, which is a concatenation of the organism, part, and
     * preparation names. Since these are all Latin, it is not Locale-specific.
     * 
     * @return the Latin name.
     */

    public String getAuthorizedName() {
        return customOrganismSubstance.getAuthorizedName();
    }

    public void setAuthorizedName(String value) {
        // NO IMPL
    }

    /***************************************************************************
     * Gets the type in the language appropriate for the Locale.
     * 
     * @return the locale-specific type.
     */
    /*
     * try the superclass impl. Probably won't work with WebSphere, because IBM
     * doesn't use JAXWS 2.1, which supports type substitution across the web
     * service.
     */
    /*
     * public String getType(){
     * 
     * return "Custom Organism Substance"; }
     * 
     * public void setType(String type){ //NO IMPL }
     */

    public List<String> getCommonNames() {
        return initializeNames(customOrganismSubstance.getCommonNames(),
                "Common");
    }

    public void setCommonNames(List<String> commonNames) {
    }
}