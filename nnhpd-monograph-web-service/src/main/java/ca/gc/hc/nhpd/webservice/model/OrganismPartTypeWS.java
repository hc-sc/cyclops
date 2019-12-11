package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.OrganismPartType;
import ca.gc.hc.nhpd.model.OrganismTypeGroup;
import ca.gc.hc.nhpd.model.Synonym;
import ca.gc.hc.nhpd.util.StringComparator;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/*******************************************************************************
 * An object that represents the type of a specific part of an organism (e.g. a
 * stem).
 * 
 * @version 1.0
 */
public class OrganismPartTypeWS implements Comparable<OrganismPartTypeWS> {

    // ~ Static fields/initializers
    // ---------------------------------------------

    private OrganismPartType organismPartType;

    // ~ Constructors
    // -----------------------------------------------------------

    public OrganismPartTypeWS() {
        organismPartType = new OrganismPartType();
    }

    public OrganismPartTypeWS(OrganismPartType organismPartType) {
        if (organismPartType == null) {
            throw new IllegalArgumentException("organismPartType is null!");
        }
        this.organismPartType = organismPartType;
    }

    /*----------------------------------------------------------------
     * Public Accessors/Mutators - methods exposed to the Web Service
     *----------------------------------------------------------------*/

    /**
     * Returns the unique identifying code associated with this Organism Part
     * Type.
     */
    public String getCode() {
        return organismPartType.getCode();
    }

    public void setCode(String newVal) {
        // Not Implemented.
    }

    /**
     * Returns the locale specific name associated with this Organism Part Type
     * 
     * @return locale specific name.
     */
    public String getName() {
        return organismPartType.getName();
    }

    public void setName(String newVal) {
        // Not Implemented.
    }

    /**
     * Retrieves the Organism Part Type synonyms.  We use the model class 
     * "getDisplaySynonyms" method because it provides the appropriate 
     * filtering when synonyms are only available in one language.  
     * @return Set of synonyms
     */
    public Set<String> getSynonyms() {
    	Set<Synonym> modelSynonyms = organismPartType.getDisplaySynonyms();
    	Set<String> retSynonyms = new TreeSet<String>();
    	if (modelSynonyms != null && modelSynonyms.size() > 0) {
    		for (Synonym synonym: modelSynonyms) {
    			retSynonyms.add(synonym.getName());    			
    		}
    	}
    	return retSynonyms;
    }
    
    public void setSynonyms(Set<String> synonyms) {	
    	// Not Implemented.  For JAX-WS only.
    }
    
    
    /**
     * This method returns the Organism Type Groups associated with this
     * organism part type.
     * 
     * @return Organism Type Groups
     */
    public Set<String> getTypeGroups() {
        Set<String> typeGroups = new TreeSet<String>();
        Set<OrganismTypeGroup> existingGroups = organismPartType
                .getTypeGroups();

        if (existingGroups != null) {
            Iterator<OrganismTypeGroup> iter = existingGroups.iterator();
            while (iter.hasNext()) {
                typeGroups.add(((OrganismTypeGroup) iter.next()).getName());
            }
        }
        return typeGroups;
    }

    public void setTypeGroups(Set<String> typeGroups) {
        // Not Implemented.
    }

    // Do an ignore case compare of the OrganismPartTypeWS.
    public int compareTo(OrganismPartTypeWS o) throws ClassCastException {

        if (getName() == null) {
            return 1;
        }
        if (o == null || o.getName() == null) {
            return -1;
        }

        return StringComparator.compare(organismPartType.getName()
                .toLowerCase(), ((OrganismPartTypeWS) o).getName()
                .toLowerCase());
    }

}
