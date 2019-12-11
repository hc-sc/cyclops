package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.Organism;
import ca.gc.hc.nhpd.model.OrganismPart;
import ca.gc.hc.nhpd.model.OrganismPartType;
import ca.gc.hc.nhpd.model.QualifiedSynonym;
import ca.gc.hc.nhpd.model.SubIngredient;
import ca.gc.hc.nhpd.model.Synonym;
import ca.gc.hc.nhpd.util.StringComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/*******************************************************************************
 * Wraps the model OrganismPart to expose it to the web service. It was created
 * to expose bilingual attributes to support the moving information into NHPSAS.
 * 
 * Note: The bilingual flag has been implemented to control the visibility of
 * attributes. When set to true, English / French specific attributes will
 * return a value. (If one is available) Otherwise, English / French attributes
 * will not return any values and will not be displayed in the web services.
 ******************************************************************************/

public class OrganismPartWS implements Comparable<OrganismPartWS> {

    // ~ Static fields/initializers
    // ---------------------------------------------

    private OrganismPart organismPart;

    private boolean bilingual;

    // ~ Constructors
    // -----------------------------------------------------------

    public OrganismPartWS(OrganismPart organismPart, boolean bilingual) {
        if (organismPart == null) {
            throw new IllegalArgumentException("organismPart is null!");
        }
        this.organismPart = organismPart;
        this.bilingual = bilingual;
    }

    public OrganismPartWS() {
        this.organismPart = new OrganismPart();
    }

    /*----------------------------------------------------------------
     * Public Accessors/Mutators - methods exposed to the Web Service
     *----------------------------------------------------------------*/
    
    /* Constituents - (e.g. chemical constituents) AKA SubIngredients.  */
    public Set<String> getConstituents()
    {
        TreeSet<String> constituents = new TreeSet<String>();
        if (organismPart.getSubIngredients() != null)
        {
            for (SubIngredient subIngredient : organismPart.getSubIngredients()) {
                if (subIngredient.getIngredient() != null
                        && subIngredient.getIngredient().getAuthorizedName() != null) {
                    constituents.add(subIngredient.getIngredient().getAuthorizedName());
                }
            }
        }
        return constituents;   
    }
    
    public void  setConstituents(Set<String> constituents) {
        //no impl
    }

    /**
     * Returns / exposes the name of the Organism Part. The name will be
     * returned in the language specified by the local settings requested by the
     * web service.
     * 
     * @return the local specific name for this Organism Part.
     */
    public String getName() {
        return organismPart.getName();
    }

    public void setName(String name) {
        // Not Implemented.
    }

    /**
     * Returns / exposes the English name for this Organism Part. This will not
     * return anything unless the boolean flag is set to true.
     * 
     * @return English name for this Organism Part.
     */
    public String getNameE() {
        if (bilingual) {
            return organismPart.getNameE();
        }
        return null;
    }

    public void setNameE(String nameE) {
        // Not Implemented.
    }

    /**
     * Returns / exposes the French name for this Organism Part. This will not
     * return anything unless the boolean flag is set to true.
     * 
     * @return French name for this Organism Part.
     */
    public String getNameF() {
        if (bilingual) {
            return organismPart.getNameF();
        }
        return null;
    }

    public void setNameF(String nameF) {
        // Not Implemented.
    }

    /**
     * Returns / exposes the Organism name associated to this Organism Part.
     * 
     * @return The Organism Name for this Organism Part.
     */
    public String getOrganism() {
        if (organismPart.getOrganism() != null
                && organismPart.getOrganism().getName() != null) {
            return organismPart.getOrganism().getName();
        }
        return null;
    }

    public void setOrganism(String organism) {
        // Not Implemented.
    }
    
    public List<String> getOrganismSynonyms() {
        if (organismPart == null
                || organismPart.getOrganism() == null) {
            return null;
        }
        
        List<String> organismSynonyms = new ArrayList<String>();
        Organism organism = organismPart.getOrganism();
        for (QualifiedSynonym qualifiedSynonym : organism.getQualifiedSynonyms()) {
            if (qualifiedSynonym != null && qualifiedSynonym.getDisplayName() != null) {
                organismSynonyms.add(qualifiedSynonym.getDisplayName());
            }
        }
        return organismSynonyms;
    }
    
    public void setOrganismSynonyms(List<String> organismSynomyms) {
        // Not Implemented.
    }

    /**
     * Returns / exposes the Part Type Code for this Organism Part.
     */
    public String getPartTypeCode() {
        if (organismPart.getType() != null) {
            return organismPart.getType().getCode();
        } else {
            return null;
        }
    }

    public void setPartTypeCode(String code) {
        // Not Implemented.
    }

    /**
     * Returns / exposes the locale specific Part Type Name for this Organism
     * Part. This will not return anything unless the boolean flag is set to
     * true.
     */
    public String getPartTypeName() {
        if (organismPart.getType() != null) {
            return organismPart.getType().getName();
        }
        return null;
    }

    public void setPartTypeName(String partTypeName) {
        // Not Implemented.
    }

    /**
     * Returns / exposes the English Part Type Name for this Organism Part. This
     * will not return anything unless the boolean flag is set to true.
     */
    public String getPartTypeNameE() {
        if (bilingual) {
            if (organismPart.getType() != null) {
                return organismPart.getType().getNameE();
            }
        }
        return null;
    }

    public void setPartTypeNameE(String partTypeNameE) {
        // Not Implemented.
    }

    /**
     * Returns / exposes the French Part Type Name for this Organism Part. This
     * will not return anything unless the boolean flag is set to true.
     */
    public String getPartTypeNameF() {
        if (bilingual) {
            if (organismPart.getType() != null) {
                return organismPart.getType().getNameF();
            }
        }
        return null;
    }

    public void setPartTypeNameF(String partTypeNameF) {
        // Not Implemented.
    }

    /**
     * Returns the synonyms associated with this Organism Part Type.
     * 
     * @return the list of synonyms associated with this Organism Part Type.
     */
    public List<String> getPartTypeSynonyms() {
        List<String> synonyms = new ArrayList<String>();
        if (organismPart != null && organismPart.getType() != null) {
            OrganismPartType organismPartType = organismPart.getType();
            if (organismPartType != null
                    && organismPartType.getSynonyms() != null) {
                Set<Synonym> optSynonyms = organismPartType.getSynonyms();
                if (optSynonyms != null && optSynonyms.size() > 0) {
                    for (Synonym synonym : optSynonyms) {
                        if (synonym.getName() != null) {
                            synonyms.add(synonym.getName());
                        }
                    }
                }

            }
        }
        return synonyms;
    }

    public void setPartTypeSynonyms(List<String> partTypeSynonyms) {
        // Not Implemented.
    }

    /*
     * Method required because this object is added to a TreeSet Does an ignore
     * case compare on the Organism Part Name
     */
    public int compareTo(OrganismPartWS o) throws ClassCastException {

        if (getName() == null) {
            return 1;
        }
        if (o == null || o.getName() == null) {
            return -1;
        }

        return StringComparator.compare(organismPart.getName().toLowerCase(),
                ((OrganismPartWS) o).getName().toLowerCase());
    }

}