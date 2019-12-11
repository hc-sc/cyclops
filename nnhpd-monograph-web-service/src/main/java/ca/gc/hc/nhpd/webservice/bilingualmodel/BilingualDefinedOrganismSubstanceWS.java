package ca.gc.hc.nhpd.webservice.bilingualmodel;

import ca.gc.hc.nhpd.model.DefinedOrganismSubstance;
import ca.gc.hc.nhpd.model.Organism;
import ca.gc.hc.nhpd.model.OrganismGroup;
import ca.gc.hc.nhpd.model.OrganismPart;
import ca.gc.hc.nhpd.util.ThreadContext;
import ca.gc.hc.nhpd.webservice.model.OrganismPartWS;
import ca.gc.hc.nhpd.webservice.model.OrganismTypeWS;

import java.util.Set;
import java.util.TreeSet;

/*******************************************************************************
 * This bilingual object represents a Defined Organism Substance that returns
 * both French and English attributes. This was created to support a web service
 * feed into NHPSAS.
 * 
 * Author: Germain Brunet Date: 2009-11-17
 */

public class BilingualDefinedOrganismSubstanceWS extends BilingualIngredientWS {

    // ~ Static fields/initializers
    // ---------------------------------------------

	private String version = ThreadContext.getInstance().getVersion();
	
    private DefinedOrganismSubstance definedOrganismSubstance;

    // ~ Constructors
    // -----------------------------------------------------------

    public BilingualDefinedOrganismSubstanceWS(
            DefinedOrganismSubstance definedOrganismSubstance) {
        super(definedOrganismSubstance);
        if (definedOrganismSubstance == null) {
            throw new IllegalArgumentException(
                    "definedOrganismSubstance is null!");
        }
        this.definedOrganismSubstance = definedOrganismSubstance;
    }

    public BilingualDefinedOrganismSubstanceWS() {
        super(new DefinedOrganismSubstance());
        this.definedOrganismSubstance = new DefinedOrganismSubstance();
    }

    /*----------------------------------------------------------------
     * Public Accessors/Mutators - methods exposed to the Web Service
     *----------------------------------------------------------------*/

    /**
     * Returns source organism parts associated with the Defined Organism
     * Substance.
     */
    public Set<OrganismPartWS> getSourceOrganismParts() {
        Set<OrganismPartWS> organismParts = new TreeSet<OrganismPartWS>();
        Set<OrganismPart> parts = definedOrganismSubstance.getParts();
        if (parts != null) {
            for (OrganismPart organismPart : parts) {
                organismParts.add(new OrganismPartWS(organismPart, true));
            }
        }
        return organismParts;
    }

    public void setSourceOrganismParts(Set<OrganismPartWS> organismParts) {
        // Not Implemented.
    }

    /**
     * Returns source organism types associated with the Defined Organism
     * Substance. Normally, only one organism type should be returned.
     */
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
        // Not Implemented.
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
