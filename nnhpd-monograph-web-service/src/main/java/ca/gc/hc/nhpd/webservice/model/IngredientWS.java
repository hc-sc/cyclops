package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.Ingredient;
import ca.gc.hc.nhpd.model.IngredientRole;
import ca.gc.hc.nhpd.model.NhpClassification;
import ca.gc.hc.nhpd.model.NonMedicinalRole;
import ca.gc.hc.nhpd.model.OrganismPart;
import ca.gc.hc.nhpd.model.QualifiedSynonym;
import ca.gc.hc.nhpd.model.SubIngredient;
import ca.gc.hc.nhpd.util.ThreadContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*******************************************************************************
 * An abstract class intended to be extended by different types of ingredients.
 */
public class IngredientWS {
    private static final Log log = LogFactory.getLog(IngredientWS.class);

    /** the wrapped Ingredient */
    private Ingredient ingredient;

    protected boolean bilingual = false;

    private String version = ThreadContext.getInstance().getVersion();
    
    public IngredientWS() {
    }

    /*
     * NB. This constructor initializes the wrapped objects. We can do this once
     * at construction time, since the web service is a read only service.
     */
    public IngredientWS(Ingredient ingredient) {
        this.ingredient = ingredient;
        initializeWSAttributes();
    }

    public IngredientWS(Ingredient ingredient, boolean bilingual) {
        this.ingredient = ingredient;
        this.bilingual = bilingual;
        initializeWSAttributes();
    }

    /**
     * Following attributes are the WS equivalents of the Model objects. WS
     * equivalents are provided to: a) limit the number of attributes exposed by
     * the web service b) hide attributes that are not supported by JAX-WS (e.g.
     * java.util.Locale is not supported because it does not have a no-arg
     * constructor.
     */

    private Set<SubIngredientWS> subIngredients;
    
    private TreeSet<NhpClassificationWS> nhpClassifications;

    // Called upon construction to convert Model objects to
    // Web Service model objects
    private void initializeWSAttributes() {

        // initializes proper and common names collections
        List<String> commonNames = initializeNames(ingredient.getCommonNames(), "Common");
        List<String> properNames = initializeNames(ingredient.getProperNames(), "Proper");

    	//Subingredients
        Set<SubIngredient> modelSubIngredients = ingredient.getSubIngredients();
        if (modelSubIngredients != null) {
            SubIngredient subIngredient = null;
            SubIngredientWS subIngredientWS = null;
            subIngredients = new HashSet<SubIngredientWS>();
            Iterator<SubIngredient> iter = modelSubIngredients.iterator();
            while (iter.hasNext()) {
                subIngredient = (SubIngredient) iter.next();
                subIngredientWS = new SubIngredientWS(subIngredient);
                // get the Parent Organism Part name if it exists
                OrganismPart orgPart = subIngredient.getParentOrganismPart();
                if (orgPart != null) {
                    subIngredientWS.setOrganismPartName(orgPart.getName());
                }
                subIngredients.add(new SubIngredientWS(subIngredient));
            }
        }

        //NhpClassifications
        Set<NhpClassification> modelNhpClassifications = ingredient.getNhpClassifications();

        if (modelNhpClassifications != null) {
            nhpClassifications = new TreeSet<NhpClassificationWS>();

            Iterator<NhpClassification> iter = modelNhpClassifications.iterator();

            while (iter.hasNext()) {
                NhpClassification nhpClass = (NhpClassification) iter.next();
                NhpClassificationWS nhpClassWS = new NhpClassificationWS(
                        nhpClass, false);

                nhpClassifications.add(nhpClassWS);

            }

        } else {
            log.warn("IngredientWS: InitWSAttrs: Null MCN!!!");
        }
        
    }

    protected List<String> initializeNames(
            Set<QualifiedSynonym> qualifiedNames, String type) {

        ArrayList<String> retNames = new ArrayList<String>();
        if (qualifiedNames != null) {
            Iterator<QualifiedSynonym> iter = qualifiedNames.iterator();
            while (iter.hasNext()) {
                QualifiedSynonym qualifiedSynonym = (QualifiedSynonym) iter
                        .next();
                if (validSynonym(qualifiedSynonym)) {
                    retNames.add(qualifiedSynonym.getName());
                }
            }
        } else {
            log.warn("IngredientWS: initializeNames: Null " + type + " Names");
        }

        return retNames;
    }

    protected List<QualifiedSynonymWS> initializeQualifiedSynonyms(
            Set<QualifiedSynonym> qualifiedNames) {

        ArrayList<QualifiedSynonymWS> retNames = new ArrayList<QualifiedSynonymWS>();
        if (qualifiedNames != null) {
            Iterator<QualifiedSynonym> iter = qualifiedNames.iterator();
            while (iter.hasNext()) {
                QualifiedSynonym qualifiedSynonym = (QualifiedSynonym) iter
                        .next();
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

    // filters out null, blank, and NA (no equivalent in this language) synonyms
    private boolean validSynonym(QualifiedSynonym qualifiedSynonym) {
        String name = qualifiedSynonym.getName();
        return (name != null) && (!name.trim().equals(""))
                && (!name.trim().equalsIgnoreCase("NA"));
    }

    /*----------------------------------------------------------------
     * Public Accessors/Mutators - methods exposed to the Web Service
     *----------------------------------------------------------------*/
    public Long getId() {
        return ingredient.getId();
    }

    public void setId(Long id) {
        // ingredient.setId(id);
    }

    public String getAuthorizedName() {
        return ingredient.getAuthorizedName();
    }

    public void setAuthorizedName(String value) {
        // no impl
    }

    /**
     * Returns the simple class name of the ingredient.  Used by the 
     * form to control some of the business logic.
     * 
     * @return the ingredient's simple class name.
     */
    public String getClassName() {

    	// New 2.8 functionality.  Null returned for 2.7.
    	if (version.equals(ThreadContext.VERSION_1_4)) {
    		return null;
    	}
    	
        return ingredient.getClass().getSimpleName();
    }
    
    public void setClassName(String value) {
        // no impl
    }
    
    public String getType() {
        return ingredient.getType();
    }

    public void setType(String type) {
        //
    }

    public List<String> getProperNames() {
        return initializeNames(ingredient.getProperNames(), "Proper");
    }

    public void setProperNames(List<String> properNames) {
        //
    }

    public Set<String> getScientificNames() {
        return ingredient.getScientificNames();
    }

    public void setScientificNames(Set<String> scientificNames) {
        //
    }

    /*
     * AllNames consists of all synonyms in addition to the ingredient's
     * approved name
     */
    public List<String> getCommonNames() {
        return initializeNames(ingredient.getCommonNames(), "Common");
    }

    public void setCommonNames(List<String> commonNames) {
        // no impl
    }

    public Set<NhpClassificationWS> getNhpClassifications() {
        Set<NhpClassificationWS> classifications = new TreeSet<NhpClassificationWS>();
        Set<NhpClassification> modelNhpClassifications = ingredient.getNhpClassifications();

        NhpClassification nhpClassification = null;
        if (modelNhpClassifications != null) {
            Iterator<NhpClassification> iter = modelNhpClassifications.iterator();
            while (iter.hasNext()) {
                nhpClassification = (NhpClassification) iter.next();
                classifications.add(new NhpClassificationWS(nhpClassification,
                        false));
            }
        }
        return classifications;

    }

    public void setNhpClassifications(
            Set<NhpClassificationWS> nhpClassifications) {
        //
    }

    public void setProductTypeCode(String productTypeCode) {
        // do nothing.
    }

    /**
     * The following code was refactored from the previous implementation
     * to return a NonMedicinalRole object since ingredients can only
     * have a single NonMedicinalRole.  This is prevented at the SQL
     * level with a unique key constraint.  GB - May 9th, 2011
     * 
     * Note:  This is version 2.0 only functionality.  
     * 
     * @return the nonMedicinalRole for this Ingredient;
     */
    public NonMedicinalRoleWS getNonMedicinalRole() {
    	
    	if (!version.equals(ThreadContext.VERSION_2_0)) {
    		return null;
    	}
    	
        for (IngredientRole ingredRole: ingredient.getRoles()) {
            if (ingredRole instanceof NonMedicinalRole) {
                return new NonMedicinalRoleWS((NonMedicinalRole) ingredRole);
            }
        }
        return null;
    }

    public void setNonMedicinalRole(NonMedicinalRoleWS nonMedicinalRole) {
        // null iml: for JAXB/JAXWS
    }
    
    /**
     * Returns the non medicinal roles associated with this ingredient.    
     * 
     * Note:  This is version 1.4 only functionality.  Version 2.0 returns the
     * a single non medicinal role for a given ingredient.
     */
    public Set<NonMedicinalRoleWS> getNonMedicinalRoles() {
    
    	if (!version.equals(ThreadContext.VERSION_1_4)) {
    		return null;
    	}
    
        Set<NonMedicinalRoleWS> nonMedicinalRoles = new TreeSet<NonMedicinalRoleWS>();
    	for (IngredientRole ingredRole: ingredient.getRoles()) {
    		if (ingredRole instanceof NonMedicinalRole) {
    			nonMedicinalRoles.add
    				(new NonMedicinalRoleWS((NonMedicinalRole)ingredRole));
    		}
    	}
    	
    	return nonMedicinalRoles;
    }
    
    public void setNonMedicinalRoles(Set<NonMedicinalRoleWS> nonMedicinalRoles) {
        //null impl: for JAXB/JAXWS
    }
 
    /**
     * Retrieves a list of IngredientRole class names that the current ingredient supports
     * Note:  This is version 2.0 only functionality.
     * 
     * @return a set of strings that are the class names of the IngredientRole subtypes.
     */
    public Set<String> getRoles() {
    
    	if (!version.equals(ThreadContext.VERSION_2_0)) {
    		return null;
    	}
    	
    	Set<String> roles = new TreeSet<String>();
    	for (IngredientRole role: ingredient.getRoles()) {
    		roles.add(role.getClass().getSimpleName());
    	}

    	return roles;
    }
    
    public void setRoles(Set<String> roles) {
        //null impl: for JAXB/JAXWS
    }
    
    public Ingredient getIngredient() {
        return ingredient;
    }

}