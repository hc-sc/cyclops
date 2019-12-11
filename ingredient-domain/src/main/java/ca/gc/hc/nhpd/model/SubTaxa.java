package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Set;

/*******************************************************************************
 * An object that represents the rank immediately subordinate to a species. In
 * botany a subtaxa is only one of the ranks that will get a ternary name,
 * while in bacteriology, the terms subspecies and variety are usually
 * interchangeable. Members of one subspecies differ morphologically from members
 * of other subspecies of the species. Subspecies are defined in relation to
 * species.
 * 
 * TODO: Look for all display occurrences of sub-taxa and change them to 
 * Subordinate.
 * TODO: Modify the model (find and replace) and database all references to 
 * subtaxa and change them to subordinate.  (Like we did 3 years ago passing 
 * from subspecies to subtaxa.)  This will remove the ambiguity in using this 
 * object / label.
 */
public class SubTaxa extends PersistentObject implements Comparable,
                                                            TaxonomyNode {
    private static final int TAXONOMY_LEVEL = 40;
    private static final String TAXONOMY_TYPE_E = "Subordinate";
    private static final String TAXONOMY_TYPE_F = "Subordonné";
    private String name;
    private Set<Organism> organisms;
    private Species species;
    
    private static final String TOKEN_SPACE = " ";

    /***************************************************************************
     * Gets the "child" nodes that this relates to.
     */
    public Set getChildren() {
        return getOrganisms();
    }

    /***************************************************************************
     */
    public Family getFamily(){
        if ( getSpecies() == null 
            || getSpecies().getGenus() == null) {
            return null;
        }
        return getSpecies().getFamily();
    }

    /***************************************************************************
     */
    public Genus getGenus(){
        if ( getSpecies() == null 
            || getSpecies().getGenus() == null) {
            return null;
        }
        return getSpecies().getGenus();
    }
    
    /***************************************************************************
     * Gets the display name (Latin binomial) of this node.  For example, when
     * returning a subordinate species name, it's important to also return the genus 
     * and species associated with the subordinate to help uniquely identify the species in 
     * question.  Each taxonomy node should be able to custom display it's name.
     */
    /*
	public String getDisplayName(){
		return getName();
	}*/
	public String getDisplayName(){
		StringBuffer sb = new StringBuffer();
		if(getGenus()!=null){
			sb.append(getGenus().getName());
			sb.append(TOKEN_SPACE);
		}
		if(getSpecies()!=null){
			sb.append(getSpecies().getName());
			sb.append(TOKEN_SPACE);
		}
		sb.append(getName());
		return sb.toString();
	}
	
    /***************************************************************************
     * Gets the display taxonomy type of this node.  For example, when
     * returning a genus / species / subTaxa, it's important to indicate
     * that it's associated to a specific Display Taxonomy Type.
     */
	public String getDisplayTaxonomyType(){
		StringBuffer sb = new StringBuffer();
		if(getGenus()!=null){
			sb.append(getGenus().getTaxonomyType());
			sb.append(TOKEN_SPACE);
		}
		if(getGenus()!=null){
			sb.append(getSpecies().getTaxonomyType());
			sb.append(TOKEN_SPACE);
		}
		sb.append(getTaxonomyType());
		return sb.toString();
	}
	
    /***************************************************************************
     * Gets the name.
     * @return name.
     * @see #setName()
     */
    public String getName(){
        return name;
    }

    /***************************************************************************
     */
    public Set<Organism> getOrganisms(){
        return organisms;
    }

    /***************************************************************************
     * Gets the "parent" node that this relates to. Returns its Genus.
     */
    public TaxonomyNode getParent() {
        return getSpecies();
    }

    /***************************************************************************
     * Gets the short description used to represent this object in drop down
     * lists and other various interfaces. 
     * @return the locale-specific short description.
     */
    public String getShortDescription(){
        
        StringBuffer sb = new StringBuffer();

        sb.append(getName());
        
        return sb.toString();

    }

    /***************************************************************************
     * Gets the long description used to represent this object in drop down
     * lists and other various interfaces.
     * 
     * <p>Notes:  The long description tries to return the same visual information
     * as found in the materialized views.  This ensures that selections made
     * by the client keep the same display text.
     * 
     * @return the locale-specific short description.
     */
    public String getLongDescription(){
        
        StringBuffer sb = new StringBuffer();

        // Add the family.
        if (getFamily() != null
                && getFamily().getName() != null) {
            sb.append(getFamily().getName());
            sb.append(TOKEN_SPACE);
        }
        
        // Add the genus.
        if (getGenus() != null
                && getGenus().getName() != null) {
            sb.append(getGenus().getName());
            sb.append(TOKEN_SPACE);
        }
 
        // Add the species.
        if (getSpecies() != null
                && getSpecies().getName() != null) {
            sb.append(getSpecies().getName());
            sb.append(TOKEN_SPACE);
        }
 
        // Add the subtaxa.
        sb.append(getName());
        
        return sb.toString();

    }

    /***************************************************************************
     */
    public Species getSpecies(){
        return species;
    }

    /***************************************************************************
     * Gets the level of this node in the taxonomy hierarchy, where Family is the
     * lowest number and Organism is the highest. Supports sorting Nodes.
     * @return the level of this node in the taxonomy hierarchy.
     */
    public int getTaxonomyLevel() {
        return TAXONOMY_LEVEL;
    }

    /***************************************************************************
     * Gets the type in the language appropriate for the Locale.
     * @return the locale-specific type.
     */
    public String getTaxonomyType() {
        if (isLanguageFrench()) {
            return TAXONOMY_TYPE_F;
        }
        return TAXONOMY_TYPE_E;
    }

    /***************************************************************************
     * Sets the name.
     * @param newVal name.
     * @see #getName()
     */
    public void setName(String newVal){
        name = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setOrganisms(Set<Organism> newVal){
        organisms = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setSpecies(Species newVal){
        species = newVal;
    }

    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
        if (getName() == null) {
            return 1;
        }
        if (o == null) {
            return -1;
        }
        if (getTaxonomyLevel() != ((TaxonomyNode)o).getTaxonomyLevel()) {
            if (getTaxonomyLevel() < ((TaxonomyNode)o).getTaxonomyLevel()) {
                return -1;
            }
            return 1;
        }
         
    	return StringComparator.compare(getName(),((SubTaxa)o).getName());
    }

    /***************************************************************************
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Over-ridden to add additional values to the
     * end.
     */
    public String getValuesAsString(){
        StringBuffer buffer = new StringBuffer();
        
        buffer.append(super.getValuesAsString());
        buffer.append(", name: ");
        buffer.append(getName());
        buffer.append(", species: ");
        if ( getSpecies() != null ) {
            buffer.append(getSpecies().getName());
        } else {
            buffer.append("null");
        }
        
        return buffer.toString();
    }
}