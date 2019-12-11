package ca.gc.hc.nhpd.model;

import java.util.Set;

/*******************************************************************************
 * This Interface must be implemented by all nodes in an Organism's taxonomy
 * structure, including the Organism itself.
 */
public interface TaxonomyNode {

    /***************************************************************************
	 * Gets the "child" nodes that this relates to. For example, a Family would
	 * return all Genera that are under it.
	 */
	public Set getChildren();

    /***************************************************************************
     * Gets the display name (Latin binomial) of this node.  For example, when
     * returning a species name, it's important to also return the genus 
     * associated with the species to help uniquely identify the species in 
     * question.  Each taxonomy node should be able to custom display it's name.
     */
	public String getDisplayName();

    /***************************************************************************
     * Gets the display taxonomy type of this node.  For example, when
     * returning a genus / species display name, it's important to indicate
     * that it's associated to a specific Display Taxonomy Type.
     */
	public String getDisplayTaxonomyType();

    /***************************************************************************
     * Gets the name (Latin binomial) of this node.
     */
	public String getName();

    /***************************************************************************
	 * Gets the "parent" node that this relates to. For example, a Genus would
	 * return its Family. Returns null if there is no parent.
	 */
	public TaxonomyNode getParent();

    /***************************************************************************
	 * Gets the level of this node in the taxonomy hierarchy, where Family is the
	 * lowest number and Organism is the highest. Supports sorting Nodes.
	 * @return the level of this node in the taxonomy hierarchy.
	 */
	public int getTaxonomyLevel();

    /***************************************************************************
	 * Gets the type in the language appropriate for the Locale.
	 * @return the locale-specific type.
	 */
	public String getTaxonomyType();

}