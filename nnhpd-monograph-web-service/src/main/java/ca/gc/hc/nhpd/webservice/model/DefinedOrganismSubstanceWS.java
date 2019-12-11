package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.DefinedOrganismSubstance;
import ca.gc.hc.nhpd.model.NamedOrganismGroup;
import ca.gc.hc.nhpd.model.NhpClassification;
import ca.gc.hc.nhpd.model.Organism;
import ca.gc.hc.nhpd.model.OrganismGroup;
import ca.gc.hc.nhpd.model.OrganismPart;
import ca.gc.hc.nhpd.model.OrganismTypeGroup;
import ca.gc.hc.nhpd.model.PreparationType;
import ca.gc.hc.nhpd.model.SimpleOrganismGroup;
import ca.gc.hc.nhpd.util.ThreadContext;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/*******************************************************************************
 * An object that represents an organism substance ingredient that has a
 * generally accepted (and named) definition.
 */

public class DefinedOrganismSubstanceWS extends IngredientWS {

	private String version = ThreadContext.getInstance().getVersion();

    // The wrapped DefinedOrganismSubstance
    private DefinedOrganismSubstance definedOrganismSubstance;

    // see enum below - this attribute is exposed to the service for convenience

    private static enum organismGroupTypes {
        NAMEDORGANISMGROUP("NAMEDORGANISMGROUP"), SIMPLEORGANISMGROUP(
                "SIMPLEORGANISMGROUP"), SINGLEORGANISM("SINGLEORGANISM");

        private final String groupName;

        organismGroupTypes(String groupName) {
            this.groupName = groupName;
        }

        public String groupName() {
            return groupName;
        }
    }

    public DefinedOrganismSubstanceWS(
            DefinedOrganismSubstance definedOrganismSubstance, boolean bilingual) {
        super(definedOrganismSubstance);
        this.definedOrganismSubstance = definedOrganismSubstance;
        this.bilingual = bilingual;
    }

    public DefinedOrganismSubstanceWS() {
        super(new DefinedOrganismSubstance());
        this.definedOrganismSubstance = new DefinedOrganismSubstance();
    }

    /***************************************************************************
     * Gets the name in the language appropriate for the Locale.
     * 
     * @return the locale-specific name.
     */
    public String getAuthorizedName() {
        return definedOrganismSubstance.getAuthorizedName();
    }

    public void setAuthorizedName(String name) {
        // this.name = name;
    }

    /*
     * public String getType(){ return definedOrganismSubstance.getType(); }
     * 
     * public void setType(String value){ //No IMPL }
     */

    public List<String> getProperNames() {
        return initializeNames(definedOrganismSubstance.getProperNames(),
                "Proper");
    }

    public void setProperNames(List<String> properNames) {
        //
    }

    public Set<String> getScientificNames() {
        return definedOrganismSubstance.getScientificNames();
    }

    public void setScientificNames(Set<String> scientificNames) {
        //
    }

    public List<String> getCommonNames() {
        return initializeNames(definedOrganismSubstance.getCommonNames(),
                "Common");
    }

    public void setCommonNames(List<String> commonNames) {
    }

    public Set<OrganismPartWS> getAllOrganismParts() {
    	//For maintaining the old ePLA version.
    	//used in ePLA's 2.0.0.B20 and earlier. Once those ePLAs are retired,
    	//this method is no longer needed.
    	  		
    	Set<OrganismPartWS> orgParts = new TreeSet<OrganismPartWS>();
        OrganismPart orgPart = null;
      	
        Set<OrganismPart> parts = definedOrganismSubstance.getPartList();
        
    	Iterator<OrganismPart> orgPartsIter = parts.iterator();
    	while (orgPartsIter.hasNext()) {
    		orgPart = (OrganismPart) orgPartsIter.next();
    		orgParts.add(new OrganismPartWS(orgPart, bilingual));
    	}
	        
        return orgParts;
    }

    public void setAllOrganismParts(Set<OrganismPartWS> organismParts) {
        // no impl
    }
    
    /*
     * Gets the organism part names from the wrapped DefinedOrganismSubstance
     */
    public Set<OrganismPartWS> getOrganismParts() {
        Set<OrganismPartWS> orgParts = new TreeSet<OrganismPartWS>();
        OrganismPart orgPart = null;
      	
        Set<OrganismPart> parts = definedOrganismSubstance.getPartList();
        
    	Iterator<OrganismPart> orgPartsIter = parts.iterator();
    	while (orgPartsIter.hasNext()) {
    		orgPart = (OrganismPart) orgPartsIter.next();
    		orgParts.add(new OrganismPartWS(orgPart, bilingual));
    	}
        
        return orgParts;

    }

    public void setOrganismParts(Set<OrganismPartWS> organismParts) {
        // no impl
    }
    
    /*
     * Gets the part preparations from the wrapped DefinedOrganismSubstance
     */
    public Set<PreparationTypeWS> getPartPreparations() {
        Set<PreparationTypeWS> partPreps = new TreeSet<PreparationTypeWS>();
        PreparationType partPrep = null;

        Set<PreparationType> modelPartPreps = definedOrganismSubstance
                .getPreparations();
        if (modelPartPreps != null) {
            Iterator<PreparationType> prepTypesIter = modelPartPreps.iterator();
            while (prepTypesIter.hasNext()) {
                partPrep = (PreparationType) prepTypesIter.next();
                partPreps.add(new PreparationTypeWS(partPrep));
            }
        }
        return partPreps;

    }

    public void setPartPreparations(Set<PreparationTypeWS> partPreps) {
        // no impl
    }

    /*
     * convenience accessor for client to distinguish organism type.
     */
    public Set<String> getOrganismTypeCodes() {
        Set<String> organismTypeCodes = new TreeSet<String>();
        OrganismGroup orgGroup = definedOrganismSubstance.getOrganismGroup();
        if (orgGroup != null) {
            Set<Organism> organisms = orgGroup.getIncludedOrganisms();
            for (Organism organism : organisms) {
                if (organism != null) {
                    organismTypeCodes.add(organism.getType().getCode());
                }
            }
        }
        return organismTypeCodes;
    }

    public void setOrganismTypeCodes(Set<String> organismTypeCodes) {
        // for JAX-WS only
    }

    public Set<OrganismTypeWS> getOrganismTypes() {
        Set<OrganismTypeWS> organismTypes = new TreeSet<OrganismTypeWS>();
        OrganismGroup orgGroup = definedOrganismSubstance.getOrganismGroup();
        if (orgGroup != null) {
            Set<Organism> organisms = orgGroup.getIncludedOrganisms();
            for (Organism organism : organisms) {
                if (organism != null) {
                    organismTypes.add(new OrganismTypeWS(organism.getType()));
                }
            }
        }
        return organismTypes;
    }

    public void setOrganismTypes(Set<OrganismTypeWS> organismTypes) {
        // for JAX-WS only
    }

    /*
     * convenience accessor for client to distinguish organism group type. NB.
     * There are currently a few cases where a SimpleOrganismGroup has a few
     * organisms, but typically there's just one.
     */
    public String getOrganismGroupType() {

        OrganismGroup orgGroup = definedOrganismSubstance.getOrganismGroup();
        if (orgGroup == null)
            return organismGroupTypes.SINGLEORGANISM.groupName();

        if (orgGroup instanceof NamedOrganismGroup) { // assume that there's
            // some there...
            return organismGroupTypes.NAMEDORGANISMGROUP.groupName();
        } else if (orgGroup instanceof SimpleOrganismGroup) {
            SimpleOrganismGroup simpleOrgGroup = (SimpleOrganismGroup) orgGroup;
            Set<Organism> organisms = simpleOrgGroup.getIncludedOrganisms();
            if (organisms != null) {
                int size = organisms.size();
                if (size <= 1) {
                    return organismGroupTypes.SINGLEORGANISM.groupName();
                } else {
                    return organismGroupTypes.SIMPLEORGANISMGROUP.groupName();
                }
            }
        }
        return organismGroupTypes.SINGLEORGANISM.groupName();
    }

    public void setOrganismGroupType(String organismGroupType) {
        // for JAX-WS only
    }

    /*
     * Convenience accessor to indicate if this ingredient is derived from
     * animal tissue. If the OrganismTypeGroup is of the type NON_HUMAN_ANIMAL_ENGLISH or FRENCH
     */
    public String getIsAnimalTissue() {
        if (getOrganismTypeGroup().equals(OrganismTypeGroup.NON_HUMAN_ANIMAL_ENGLISH) ||
        		getOrganismTypeGroup().equals(OrganismTypeGroup.NON_HUMAN_ANIMAL_FRENCH)) {
            return "true";
        } else if (getOrganismTypeGroup().equals(OrganismTypeGroup.UNDEFINED)) {
        	Set<NhpClassification> nhpClassifications = definedOrganismSubstance.getNhpClassifications();
        	for (NhpClassification classification:nhpClassifications) {
        		if (classification.getCode().equals(NhpClassification.NHP_CLASSIFICATION_CODE_NHANML)) {
        			return "true";
        		}
        	}
        	//if we can't determine the type return empty which will force the client to answer the
        	//animal tissue question
        	return "";
        }
        
        //if the OrganismTypeGroup is not equal to NON_HUMAN_ANIMAL_ENGLISH or FRENCH or UNDEFINED,
        //then no animal tissue is required.
        return "false";
    }

    public void setIsAnimalTissue(String isAnimalTissue) {
        // do nothing.
    }

    /*
     * Convenience accessor to indicate if this ingredient is a
     * whole organism.
     */
    public Boolean getIsWholeOrganism() {
    	
    	// New 2.8 functionality.  Null returned for 2.7.
    	if (version.equals(ThreadContext.VERSION_1_4)) {
    		return null;
    	}
    	
        return definedOrganismSubstance.isWholeOrganism();
    }

    public void setIsWholeOrganism(Boolean isWholeOrganism) {
        // do nothing.
    }
    
    /*
     * Convenience accessor for client to distinguish organism type group. This
     * is used to filter part types to only those applicable for this group
     * (e.g. plant, fungus, etc.). Note that it will only be populated if this
     * uses a SimpleOrganismGroup and all of its organisms are from the same
     * type group.
     */
    public String getOrganismTypeGroup() {
        OrganismGroup orgGroup = definedOrganismSubstance.getOrganismGroup();

        if (orgGroup != null && orgGroup instanceof SimpleOrganismGroup
                && !orgGroup.getIncludedOrganisms().isEmpty()) {
            Iterator<Organism> iter = orgGroup.getIncludedOrganisms().iterator();
            String typeGroup = ((Organism) iter.next()).getType().getGroup()
                    .getName();

            while (iter.hasNext()) {
                if (!typeGroup.equals(((Organism) iter.next()).getType()
                        .getGroup().getName())) {
                    return "multiple";
                }
            }
            return typeGroup;
        }
        return "undefined";
    }

    public void setOrganismTypeGroup(String organismTypeGroup) {
        // for JAXB only
    } 
    
    /**
     * We override the default behaviour in order to provide the old ePLA functionality.
     */
    public String getType() {

    	// Returns the 1.4 functionality.  This supports returning only the 
    	// "Defined Organism Substance".
    	if (version.equals(ThreadContext.VERSION_1_4)) {
    		if (definedOrganismSubstance.isLanguageFrench()) {
    			return DefinedOrganismSubstance.TYPE_DOS_F;
    		} else {
    			return DefinedOrganismSubstance.TYPE_DOS_E;
    		}
    	}

    	// Return the new 2.0 functionality.  This supports both the 
    	// "Defined Organism Substance" and "Defined Organism Substance - Whole Organism".
    	return definedOrganismSubstance.getType();
    	
    }
    
    public void setType(String type) {
    	// Not Implemented.
    }

}
