package ca.gc.hc.nhpd.model;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*******************************************************************************
 * This object contains a group of Organisms. This implementation only contains
 * those for a specific substance, and typically only contains a single Organism.
 */
public class SimpleOrganismGroup extends PersistentObject
                                 implements OrganismGroup {
    private static final Log LOG = LogFactory.getLog(SimpleOrganismGroup.class);
    private Set<Organism> includedOrganisms = new TreeSet<Organism>();
    private String organismList;

    /***************************************************************************
     * Gets the Organisms that are included in this OrganismGroup.
     * @see #setIncludedOrganisms()
     */
    public Set<Organism> getIncludedOrganisms(){
        return includedOrganisms;
    }

    /***************************************************************************
     * Gets whether this group only contains a single Organism.
     * @return true if this group only contains a single Organism.
     */
    public boolean isSingleOrganism(){
        return (getIncludedOrganisms().size() == 1);
    }

    /***************************************************************************
     * Sets the Organisms that are included in this OrganismGroup.
     * @see #getIncludedOrganisms()
     * @param newVal
     */
    public void setIncludedOrganisms(Set<Organism> newVal){
        if (newVal != null) {
            includedOrganisms = newVal;
        } else {
            includedOrganisms.clear();
        }
    }

    /***************************************************************************
     * Gets the list of associated organisms in a string format for display
     * purposes. 
     * 
     * @return the List of associated organisms.
     */
    public String getOrganismList() {
        
        // Try to return a cached value first.
        if (organismList != null) {
            return organismList;
        }
        
        StringBuffer buffer = new StringBuffer();
        Iterator iterator = null;
        if (!getIncludedOrganisms().isEmpty()) {
            iterator = getIncludedOrganisms().iterator();
            buffer.append(((Organism)iterator.next()).getName()); //Has to be one, since it isn't empty
            while (iterator.hasNext()) {
                buffer.append(", ");
                buffer.append(((Organism)iterator.next()).getName());
            }
        } else {
            // Leave an empty string so that we do not repeat this operation again.
            organismList = EMPTY_STRING;
        }

        organismList = buffer.toString();
        return organismList;
        
    }
    
    public Set<Organism> getDistinctCitationOrganisms() {
  
    	Hashtable<String,Organism> organismsByCitation = 
    		new Hashtable<String,Organism>(); 
    	TreeSet<Organism> distinctCitationOrganisms = new TreeSet<Organism>();
    	for (Organism organism: getIncludedOrganisms()) {
    		organismsByCitation.put
    			(organism.getReference().getCitation().getAbridgedTitle(),organism);
    	}
    	
    	for (Organism organism: organismsByCitation.values()) {
    		distinctCitationOrganisms.add(organism);
    	}
    	return distinctCitationOrganisms;
	}
	
	
    /***************************************************************************
     * Gets the English short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the English specific short description.
     */
    public String getShortDescriptionE() {
        return getOrganismList();
    }

    /***************************************************************************
     * Gets the French short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the French specific short description.
     */
    public String getShortDescriptionF() {
        return getOrganismList();
    }
    
    /***************************************************************************
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Over-ridden to add additional values to the
     * end.
     */
    public String getValuesAsString(){
        StringBuffer buffer = new StringBuffer();
        Iterator iterator = null;
        
        buffer.append(super.getValuesAsString());
        if (!getIncludedOrganisms().isEmpty()) {
            iterator = getIncludedOrganisms().iterator();
            buffer.append(", included organisms: [");
            buffer.append(((Organism)iterator.next()).getName()); //Has to be one, since it isn't empty
            while (iterator.hasNext()) {
                buffer.append(", ");
                buffer.append(((Organism)iterator.next()).getName());
            }
            buffer.append("]");
        }
        
        return buffer.toString();
    }
}