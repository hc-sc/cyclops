package ca.gc.hc.nhpd.webservice.bilingualmodel;

import ca.gc.hc.nhpd.model.ChemicalSubstance;
import ca.gc.hc.nhpd.model.DefinedOrganismSubstance;
import ca.gc.hc.nhpd.model.HomeopathicSubstance;
import ca.gc.hc.nhpd.model.Ingredient;
import ca.gc.hc.nhpd.model.NhpClassification;
import ca.gc.hc.nhpd.model.OrganismPart;
import ca.gc.hc.nhpd.model.QualifiedSynonym;
import ca.gc.hc.nhpd.model.SubIngredient;
import ca.gc.hc.nhpd.webservice.model.NhpClassificationWS;
import ca.gc.hc.nhpd.webservice.model.QualifiedSynonymWS;
import ca.gc.hc.nhpd.webservice.model.SubIngredientWS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*******************************************************************************
 * An abstract class intended to be extended by different types of bilingual
 * ingredients.
 * 
 * Note: Following attributes are the WS equivalents of the Model objects. WS
 * equivalents are provided to: a) limit the number of attributes exposed by the
 * web service b) hide attributes that are not supported by JAX-WS (e.g.
 * java.util.Locale is not supported because it does not have a no-arg
 * constructor.
 * 
 * Author: Germain Brunet Date: 2009-11-17
 */
public class BilingualIngredientWS {

    private static final Log log = LogFactory
            .getLog(BilingualIngredientWS.class);

    private static final String EMPTY_STRING = "";

    private static final String NA_STRING = "NA";

    // ~ Static fields/initializers
    // ---------------------------------------------

    private String authorizedNameE;

    private String authorizedNameF;

    private Ingredient ingredient;

    private Set<SubIngredientWS> subIngredients;

    private TreeSet<NhpClassificationWS> nhpClassifications;

    // ~ Constructors
    // -----------------------------------------------------------

    public BilingualIngredientWS() {
    }

    /*
     * NB. This constructor initializes the wrapped objects. We can do this once
     * at construction time, since the web service is a read only service.
     */
    public BilingualIngredientWS(Ingredient ingredient) {
        if (ingredient == null) {
            throw new IllegalArgumentException("ingredient is null!");
        }
        this.ingredient = ingredient;
        initializeWSAttributes();
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
     * Authorized Names
     *----------------------------------------------------------------*/

    /**
     * Returns / exposes the ingredient's Authorized Name. The name will be
     * returned in the language specified by the local settings requested by the
     * web service.
     * 
     * @return local specific authorized name.
     */
    public String getAuthorizedName() {
        return ingredient.getAuthorizedName();
    }

    public void setAuthorizedName(String value) {
        // Not Implemented.
    }

    /**
     * Returns / exposes the ingredient's English Authorized Name.
     * 
     * @return English authorized name.
     */
    public String getAuthorizedNameE() {
        return authorizedNameE;
    }

    public void setAuthorizedNameE(String value) {
        // Not Implemented.
    }

    /**
     * Returns / exposes the ingredient's French Authorized Name.
     * 
     * @return French authorized name.
     */
    public String getAuthorizedNameF() {
        return authorizedNameF;
    }

    public void setAuthorizedNameF(String value) {
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
        return initializeQualifiedSynonyms(ingredient.getCommonNames());
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
        return initializeQualifiedSynonyms(ingredient.getProperNames());
    }

    public void setProperNames(List<QualifiedSynonymWS> properNames) {
        // Not Implemented.
    }

    /*----------------------------------------------------------------
     * Type
     *----------------------------------------------------------------*/

    /**
     * Returns / exposes the type of this ingredient.
     */
    public String getType() {
        return ingredient.getType();
    }

    public void setType(String type) {
        // Not Implemented.
    }

    /*----------------------------------------------------------------
     * Nhp Classification
     *----------------------------------------------------------------*/

    /**
     * Returns / exposes the NHP Classification associated with this ingredient.
     * One or more classifications can be returned.
     */
    public Set<NhpClassificationWS> getNhpClassifications() {
        return nhpClassifications;
    }

    public void setNhpClassifications(
            Set<NhpClassificationWS> nhpClassifications) {
        // Not Implemented.
    }

    // ~ Private Methods ---------------------------------------------

    /**
     * Called upon construction to convert Model objects to Web Service model
     * objects
     */
    private void initializeWSAttributes() {

        // Populates the authorized name E/F leveraging the information
        // from the sub-class.
        if (ingredient instanceof DefinedOrganismSubstance) {
            DefinedOrganismSubstance dos = (DefinedOrganismSubstance) ingredient;
            authorizedNameE = dos.getAuthorizedNameE();
            authorizedNameF = dos.getAuthorizedNameF();
        } else if (ingredient instanceof ChemicalSubstance) {
            ChemicalSubstance dos = (ChemicalSubstance) ingredient;
            authorizedNameE = dos.getAuthorizedNameE();
            authorizedNameF = dos.getAuthorizedNameF();
        } else if (ingredient instanceof HomeopathicSubstance) {
            HomeopathicSubstance hs = (HomeopathicSubstance) ingredient;
            authorizedNameE = hs.getAuthorizedNameE();
            authorizedNameF = hs.getAuthorizedNameF();
        }

        // Initializes the subingredients.
        Set<SubIngredient> modelSubIngredients = ingredient.getSubIngredients();
        if (modelSubIngredients != null) {
            subIngredients = new HashSet<SubIngredientWS>();
            for (SubIngredient subIngredient : modelSubIngredients) {
                SubIngredientWS subIngredientWS = new SubIngredientWS(
                        subIngredient);
                // get the Parent Organism Part name if it exists
                OrganismPart orgPart = subIngredient.getParentOrganismPart();
                if (orgPart != null) {
                    subIngredientWS.setOrganismPartName(orgPart.getName());
                }
                subIngredients.add(new SubIngredientWS(subIngredient));
            }
        }

        // Initializes the NHP Classification.
        Set<NhpClassification> modelNhpClassifications = ingredient
                .getNhpClassifications();
        if (modelNhpClassifications != null) {
            nhpClassifications = new TreeSet<NhpClassificationWS>();
            for (NhpClassification nhpClassification : modelNhpClassifications) {
                NhpClassificationWS nhpClassWS = new NhpClassificationWS(
                        nhpClassification, true);
                nhpClassifications.add(nhpClassWS);
            }
        } else {
            log.warn("IngredientWS: InitWSAttrs: Null MCN!!!");
        }

    }

    /**
     * This method converts a list of qualified synonyms into a list of
     * qualified synonyms for the web services.
     * 
     * @param qualifiedSynonyms
     *            being converted to web services.
     * 
     * @return qualifiedSynonyms as Web Services.
     */
    protected List<QualifiedSynonymWS> initializeQualifiedSynonyms(
            Set<QualifiedSynonym> qualifiedSynonyms) {

        if (qualifiedSynonyms == null) {
            return null;
        }

        ArrayList<QualifiedSynonymWS> retNames = new ArrayList<QualifiedSynonymWS>();
        if (qualifiedSynonyms != null) {
            for (QualifiedSynonym qualifiedSynonym : qualifiedSynonyms) {
                if (validSynonym(qualifiedSynonym)) {
                    retNames.add(new QualifiedSynonymWS(qualifiedSynonym));
                }
            }
        } else {
            log
                    .warn("IngredientWS: initializeNames: Null Qualified Synonyms Names");
        }
        return retNames;
    }

    /**
     * Filters out null, blank, and NA (no equivalent in this language) synonyms
     * 
     * @param qualifiedSynonym
     *            being filtered
     * @return the filtered qualifiedSynonym
     */
    private boolean validSynonym(QualifiedSynonym qualifiedSynonym) {
        String name = qualifiedSynonym.getName();
        return (name != null) && (!name.trim().equals(EMPTY_STRING))
                && (!name.trim().equalsIgnoreCase(NA_STRING));
    }

}