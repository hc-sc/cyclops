package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Set;

/*******************************************************************************
 * An object that represents a biological classification, either a rank or a taxon
 * in that rank.
 */
public class Family extends PersistentObject implements Comparable,
                                                        TaxonomyNode {
    private static final int TAXONOMY_LEVEL = 10;
    private static final String TAXONOMY_TYPE_E = "Family";
    private static final String TAXONOMY_TYPE_F = "Famille";
    private Set<Genus> genera;
    private String name;

    /***************************************************************************
     * Gets the "child" nodes that this relates to.
     */
    public Set getChildren() {
        return getGenera();
    }

    /***************************************************************************
     * Gets the display name (Latin binomial) of this node.  For example, when
     * returning a species name, it's important to also return the genus 
     * associated with the species to help uniquely identify the species in 
     * question.  Each taxonomy node should be able to custom display it's name.
     */
	public String getDisplayName(){
		return getName();
	}

    /***************************************************************************
     * Gets the display taxonomy type of this node.  For example, when
     * returning a genus / species display name, it's important to indicate
     * that it's associated to a specific Display Taxonomy Type.
     */
	public String getDisplayTaxonomyType(){
		return getTaxonomyType();
	}

    /***************************************************************************
     */
    public Set<Genus> getGenera(){
        return genera;
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
     * Gets the "parent" node that this relates to. Returns null.
     */
    public TaxonomyNode getParent() {
        return null;
    }

    /***************************************************************************
     * Gets the short description used to represent this object in drop down
     * lists and other various interfaces. 
     * @return the locale-specific short description.
     */
    public String getShortDescription(){
        return getName();
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
     * @param newVal
     */
    public void setGenera(Set<Genus> newVal){
        genera = newVal;
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
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
        
    	if (o == null) return -1;
    	if (getTaxonomyLevel() != ((TaxonomyNode)o).getTaxonomyLevel()) {
            if (getTaxonomyLevel() < ((TaxonomyNode)o).getTaxonomyLevel()) {
                return -1;
            }
            return 1;
        }
    	return StringComparator.compare(getName(),((Family)o).getName());
 
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
        
        buffer.append(", genera: ");
        buffer.append(getGenera());
        
        return buffer.toString();
    }
}