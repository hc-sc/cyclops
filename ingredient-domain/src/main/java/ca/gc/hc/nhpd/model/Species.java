package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Set;

/*******************************************************************************
 * An object that represents one of the basic units of biodiversity. In scientific
 * classification, a species is assigned a two-part name, treated as Latin. The
 * genus is listed first (with its leading letter capitalized), followed by a
 * second term. For example, humans belong to the genus Homo, and are in the
 * species Homo sapiens. The name of the species is the whole binomial, not just
 * the second term (which may be called specific epithet, for plants, or specific
 * name, for animals).
 */
public class Species extends PersistentObject implements Comparable,
                                                         TaxonomyNode {
    private static final int TAXONOMY_LEVEL = 30;
    private static final String TAXONOMY_TYPE_E = "Species";
    private static final String TAXONOMY_TYPE_F = "Espèces";
    private Genus genus;
    private String name;
    //TODO - no hibernate mapping for this collection - do we need it?
    private Set<Organism> organisms;
    private Set<SubTaxa> subTaxa;

    private static final String TOKEN_SPACE = " ";
    
    /***************************************************************************
     * Gets the "child" nodes that this relates to.
     */
    public Set getChildren() {
        Set children = getSubTaxa();
        
        if (children != null) {
            children.addAll(getOrganisms());
            return children;
        }
        return getOrganisms();
    }

    /***************************************************************************
     * Gets the display name (Latin binomial) of this node.  For example, when
     * returning a species name, it's important to also return the genus 
     * associated with the species to help uniquely identify the species in 
     * question.  Each taxonomy node should be able to custom display it's name.
     */
	public String getDisplayName(){
		StringBuffer sb = new StringBuffer();
		if(getGenus()!=null){
			sb.append(getGenus().getName());
			sb.append(TOKEN_SPACE);
		}
		sb.append(getName());
		return sb.toString();
	}

    /***************************************************************************
     * Gets the display taxonomy type of this node.  For example, when
     * returning a genus / species display name, it's important to indicate
     * that it's associated to a specific Display Taxonomy Type.
     */
	public String getDisplayTaxonomyType(){
		StringBuffer sb = new StringBuffer();
		if(getGenus()!=null){
			sb.append(getGenus().getTaxonomyType());
			sb.append(TOKEN_SPACE);
		}
		sb.append(getTaxonomyType());
		return sb.toString();
	}
    
    /***************************************************************************
     */
    public Family getFamily(){
        if ( getGenus() == null ) {
            return null;
        }
        return getGenus().getFamily();
    }


    /***************************************************************************
     */
    public Genus getGenus(){
        return genus;
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
        return getGenus();
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
        sb.append(getName());
        
        return sb.toString();
        
    }

    /***************************************************************************
     */
    public Set<SubTaxa> getSubTaxa(){
        return subTaxa;
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
    public void setGenus(Genus newVal){
        genus = newVal;
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
    public void setSubTaxa(Set<SubTaxa> newVal){
        subTaxa = newVal;
    }

    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
        
        if (o == null) {
            return -1;
        }
        if (getTaxonomyLevel() != ((TaxonomyNode)o).getTaxonomyLevel()) {
            if (getTaxonomyLevel() < ((TaxonomyNode)o).getTaxonomyLevel()) {
                return -1;
            }
            return 1;
        }
         
    	return StringComparator.compare(getName(),((Species)o).getName());
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
        if ( getGenus() != null ) {
            buffer.append(", genus: ");
            buffer.append(getGenus().getName());
        }
        
        buffer.append(", subTaxa: ");
        buffer.append(getSubTaxa());
        
        return buffer.toString();
    }
}