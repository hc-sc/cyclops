package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.QualifiedSynonym;
import ca.gc.hc.nhpd.model.SubIngredient;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import ca.gc.hc.nhpd.util.StringComparator;

/*******************************************************************************
 * Web Service wrapper for SubIngredient. A Subingredient is an object that
 * represents the sub-ingredient component of a larger (composite) item /
 * compound. These are ChemicalSubstances.
 */
public class SubIngredientWS implements Comparable<SubIngredientWS> {

    // the wrapped SubIngredient
    private SubIngredient subIngredient;

    private String organismPartName;

    public SubIngredientWS() {
        this.subIngredient = new SubIngredient();
    }

    public SubIngredientWS(SubIngredient subIngredient) {
        this.subIngredient = subIngredient;
    }

    /*
     * convenience accessor - the approved name of the SubIngredient is held in
     * the contained Ingredient
     */
    public String getAuthorizedName() {
        if (subIngredient != null
                && subIngredient.getIngredient() != null
                && subIngredient.getIngredient().getAuthorizedName() != null) {
            return subIngredient.getIngredient().getAuthorizedName();
        }
        return null;
    }

    public void setAuthorizedName(String approvedName) {
        // no impl required
    }

    public String getOrganismPartName() {
        return organismPartName;
    }

    public void setOrganismPartName(String organismPartName) {
        this.organismPartName = organismPartName;
    }

    /*
     * Common Names for this SubIngredient. Added to provide support for Mineral
     * type Chemical Substances
     */
    public Set<String> getCommonNames() {
        if (subIngredient == null
                || subIngredient.getIngredient() == null) {
            return null;
        }
        Set<String> commonNames = new TreeSet<String>();
        Set<QualifiedSynonym> modelCommonNames = subIngredient.getIngredient().getCommonNames();
        Iterator<QualifiedSynonym> iter = modelCommonNames.iterator();
        while (iter.hasNext()) {
            QualifiedSynonym commonName = (QualifiedSynonym) iter.next();
            commonNames.add(commonName.getName());
        }
        return commonNames;
    }

    public void setCommonNames(Set<String> commonNames) {
        //
    }
    
    public Set<String> getProperNames() {
        if (subIngredient == null
                || subIngredient.getIngredient() == null) {
            return null;
        }
        Set<String> properNames = new TreeSet<String>();
        Set<QualifiedSynonym> modelProperNames = subIngredient.getIngredient().getProperNames();
        Iterator<QualifiedSynonym> iter = modelProperNames.iterator();
        while (iter.hasNext()) {
            QualifiedSynonym ProperName = (QualifiedSynonym) iter.next();
            properNames.add(ProperName.getName());
        }
        return properNames;
    }

    public void setProperNames(Set<String> ProperNames) {
        //
    }
    
    /*
     * Implement Comparable compareTo method. Method required because this
     * object is added to a TreeSet Does an ignore case compare on the
     * SubIngredient Approved Name
     */
    public int compareTo(SubIngredientWS o) throws ClassCastException {
        // String thisName = getAuthorizedName().toLowerCase();
        // String compName =
        // ((SubIngredientWS)o).getAuthorizedName().toLowerCase();
        return StringComparator.compare(getAuthorizedName(),
                o.getAuthorizedName());

    }

}