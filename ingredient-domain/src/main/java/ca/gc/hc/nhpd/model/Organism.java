package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


/*******************************************************************************
 * An object that represents an organism.
 */
public class Organism extends PersistentObject implements Comparable,
	TaxonomyNode {

	//~ Static fields/initializers ---------------------------------------------
	static public final String TAXONOMY_TYPE_E = "Organism";
	static public final String TAXONOMY_TYPE_F = "Organisme";
	static private final int TAXONOMY_LEVEL = 50;
	
	//~ Instance fields --------------------------------------------------------
	private String commentE;
	private String commentF;
	private Set<QualifiedSynonym> qualifiedSynonyms;
	private Set<Country> countriesOfOrigin;

	// private Set countriesWhereEndangered;
	private String descriptionE;
	private String descriptionF;
	private Set<GeographicArea> geographicAreas;
	private String name;
	private Set<OrganismPart> organismParts;
	
	private Set<OrganismPart> organismPartsWithConstituents;

	// private Set parts;
	private Reference reference;
	private Species species;
	private SubTaxa subTaxa;
	private Set<Taxon> taxa;
	private OrganismType type;

	//~ public -----------------------------------------------------------------

	/**
	 * Compares this object with the specified object for order. Returns a
	 * negative integer, zero, or a positive integer as this object is less
	 * than, equal to, or greater than the specified object.
	 *
	 * @param   o  the object to compare this to.
	 *
	 * @return  integer value depicting equality.
	 *
	 * @throws  ClassCastException  returned when the object being compared is
	 *                              not an OrganismPart
	 */
	public int compareTo(Object o) throws ClassCastException {

		if (getName() == null) {
			return 1;
		}

		if (o == null) {
			return -1;
		}

		if (getTaxonomyLevel() != ((TaxonomyNode) o).getTaxonomyLevel()) {
			if (getTaxonomyLevel() < ((TaxonomyNode) o).getTaxonomyLevel()) {
				return -1;
			}
			return 1;
		}

	    return StringComparator.compare(getName(),((Organism)o).getName());
		
	}

	/**
	 * Gets the biological group this Organism is in. This should always be a
	 * Species or SubSpecies.
	 *
	 * @return  the Species or SubSpecies that this Organism is part of.
	 */
	public TaxonomyNode getBiologicalGroup() {

		if (getSubTaxa() != null) {
			return getSubTaxa();
		}

		return getSpecies();
	}


	/**
	 * Gets the "child" nodes that this relates to.
	 *
	 * @return  The Set of Children
	 */
	public Set getChildren() {
		return null;
	}

	/**
	 * Gets the comment in the language appropriate for the Locale.
	 *
	 * @return  the locale-specific comment.
	 */
	public String getComment() {

		if (isLanguageFrench()) {
			return getCommentF();
		}

		return getCommentE();
	}

	/**
	 * Gets the comment in English.
	 *
	 * @return  the comment in English.
	 *
	 * @see     #setCommentE()
	 */
	public String getCommentE() {
		return commentE;
	}

	/**
	 * Gets the comment in French.
	 *
	 * @return  the comment in French.
	 *
	 * @see     #setCommentF()
	 */
	public String getCommentF() {
		return commentF;
	}

	/**
	 * Gets the Set of QualifiedSynonyms.
	 *
	 * @return  the Set of QualifiedSynonyms
	 *
	 * @see     #setQualifiedSynonyms()
	 */
	public Set<QualifiedSynonym> getQualifiedSynonyms() {
		return qualifiedSynonyms;
	}

	/**
	 * Gets the Set of CountriesOfOrigin.
	 *
	 * @return  the Set of CountriesOfOrigin
	 *
	 * @see     #setCountriesOfOrigin()
	 */
	public Set<Country> getCountriesOfOrigin() {
		return countriesOfOrigin;
	}

	/**
	 * Gets the description in the language appropriate for the Locale.
	 *
	 * @return  the locale-specific description.
	 *
	 * @see     #getDescriptionE(), #getDescriptionF()
	 */
	public String getDescription() {

		if (isLanguageFrench()) {
			return getDescriptionF();
		}

		return getDescriptionE();
	}

	/**
	 * Gets the description in English.
	 *
	 * @return  the description in English.
	 *
	 * @see     #setDescriptionE()
	 */
	public String getDescriptionE() {
		return descriptionE;
	}

	/**
	 * Gets the description in French.
	 *
	 * @return  the description in French.
	 *
	 * @see     #setDescriptionF()
	 */
	public String getDescriptionF() {
		return descriptionF;
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
	
	/**
	 * Gets the Set of GeographicAreas.
	 *
	 * @return  the Set of GeographicAreas
	 *
	 * @see     #setOfGeographicAreas
	 */
	public Set<GeographicArea> getGeographicAreas() {
		return geographicAreas;
	}

	/**
	 * Gets the Name for this Organism.
	 *
	 * @return  this Organism Name
	 *
	 * @see     #setName()
	 */
	public String getName() {
		return name;
	}


	/**
	 * Gets the Set of OrganismParts.
	 *
	 * @return  the Set of OrganismParts
	 *
	 * @see     #setOrganismParts()
	 */
	public Set<OrganismPart> getOrganismParts() {
		return organismParts;
	}

	/**
	 * Organism Parts With Constituents are organism parts with defined
	 * sub-ingredients. This operation returns a subset of OrganismParts that
	 * have subIngredients.
	 *
	 * @return  OrganismParts that have SubIngredients.
	 */
	public Set<OrganismPart> getOrganismPartsWithConstituents() {

		// Have we cached this value, if so return it.
		if (organismPartsWithConstituents != null) {
			return organismPartsWithConstituents;
		}

		// Is organismParts null, if so return a null.
		if (getOrganismParts() == null) {
			return null;
		}

		// Extract the organismPartsWithConstituents from the organismParts
		organismPartsWithConstituents = new TreeSet<OrganismPart>();

		Iterator i = getOrganismParts().iterator();

		while (i.hasNext()) {
			OrganismPart organismPart = (OrganismPart) i.next();

			if (organismPart.hasSubIngredients()) {
				organismPartsWithConstituents.add(organismPart);
			}
		}

		return organismPartsWithConstituents;
	}

	/**
	 * Gets the "parent" node that this relates to. Returns its Genus.
	 *
	 * @return  the Parent Taxonomy node
	 */
	public TaxonomyNode getParent() {
		return getBiologicalGroup();
	}

	/**
	 * Gets the Reference.
	 *
	 * @return  the Reference
	 *
	 * @see     #setReference()
	 */
	public Reference getReference() {
		return reference;
	}

    /***************************************************************************
     */
    public Family getFamily(){
        if ( getSpecies() != null ) {
            return getSpecies().getFamily();
        }
        if ( getSubTaxa() != null ) {
            return getSubTaxa().getFamily();
        }
        return null;
    }

	/**
	 * Gets the Species this Organism is part of. This should always be assigned
	 * to a Species or SubSpecies. If it is part of a SubSpecies, this will
	 * return the SubSpecies' Species.
	 *
	 * @return  the Species that this Organism is part of (even if it is part of
	 *          a SubSpecies within that Species).
	 */
	public Species getRelatedSpecies() {

		if (getSubTaxa() != null) {
			return getSubTaxa().getSpecies();
		}

		return getSpecies();
	}

    /***************************************************************************
     * Gets the English short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the English specific short description.
     */
    public String getShortDescriptionE() {
        return getName();
    }

    /***************************************************************************
     * Gets the French short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the French specific short description.
     */
    public String getShortDescriptionF() {
        return getName();
    }

	/**
	 * Gets the Species.
	 *
	 * @return  the Species
	 *
	 * @see     #setSpecies()
	 */
	public Species getSpecies() {
		return species;
	}

	/**
	 * Gets the SubTaxa.
	 *
	 * @return  the SubTaxa
	 *
	 * @see     #setSubTaxa()
	 */
	public SubTaxa getSubTaxa() {
		return subTaxa;
	}

	/**
	 * Gets the Taxa.
	 *
	 * @return  the Taxa
	 *
	 * @see     #setTaxa()
	 */
	public Set<Taxon> getTaxa() {
		return taxa;
	}

	/**
	 * Gets the level of this node in the taxonomy hierarchy, where Family is
	 * the lowest number and Organism is the highest. Supports sorting Nodes.
	 *
	 * @return  the level of this node in the taxonomy hierarchy.
	 */
	public int getTaxonomyLevel() {
		return TAXONOMY_LEVEL;
	}

	/**
	 * Gets the type in the language appropriate for the Locale.
	 *
	 * @return  the locale-specific type.
	 */
	public String getTaxonomyType() {

		if (isLanguageFrench()) {
			return TAXONOMY_TYPE_F;
		}

		return TAXONOMY_TYPE_E;
	}

	/**
	 * Gets the OrganismType.
	 *
	 * @return  the OrganismType
	 *
	 * @see     #setType()
	 */
	public OrganismType getType() {
		return type;
	}

	/**
	 * This provides a list of the instance variable values for this object, and
	 * is called by the PersistentObject's toString(). Intended to provide
	 * useful debugging information. Over-ridden to add additional values to the
	 * end.
	 *
	 * @return  This object as a String
	 */
	public String getValuesAsString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(super.getValuesAsString());
		buffer.append(", name: ");
		buffer.append(getName());
		buffer.append(", type: ");

		if (getType() != null) {
			buffer.append(getType().getName());
		} else {
			buffer.append("null");
		}

		buffer.append(", biologicalGroup: ");
		buffer.append(getBiologicalGroup());
		buffer.append(", descriptionE: ");
		buffer.append(getDescriptionE());
		buffer.append(", descriptionF: ");
		buffer.append(getDescriptionF());
		buffer.append(", commentE: ");
		buffer.append(getCommentE());
		buffer.append(", commentF: ");
		buffer.append(getCommentF());

		if ((getCountriesOfOrigin() != null)
				&& !getCountriesOfOrigin().isEmpty()) {
			buffer.append(", countriesOfOrigin: ");
			buffer.append(getCountriesOfOrigin());
		}

		if ((getGeographicAreas() != null) && !getGeographicAreas().isEmpty()) {
			buffer.append(", geographicAreas: ");
			buffer.append(getGeographicAreas());
		}

		if ((getQualifiedSynonyms() != null) && !getQualifiedSynonyms().isEmpty()) {
			buffer.append(", qualifiedSynonyms: ");
			buffer.append(getQualifiedSynonyms());
		}

		if ((getTaxa() != null) && !getTaxa().isEmpty()) {
			buffer.append(", taxa: ");
			buffer.append(getTaxa());
		}

		buffer.append(", reference: ");
		buffer.append(getReference());

		return buffer.toString();
	}

	/**
	 * Sets the English Comment.
	 *
	 * @param  newVal
	 *
	 * @see    #getCommentE()
	 */
	public void setCommentE(String newVal) {
		commentE = newVal;
	}

	/**
	 * Sets the French Comment.
	 *
	 * @param  newVal
	 *
	 * @see    #getCommentF()
	 */
	public void setCommentF(String newVal) {
		commentF = newVal;
	}

	/**
	 * Sets the Set of QualifiedSynonyms.
	 *
	 * @param  newVal
	 *
	 * @see    #getQualifiedSynonyms()
	 */
	public void setQualifiedSynonyms(Set<QualifiedSynonym> newVal) {
		qualifiedSynonyms = newVal;
	}

	/**
	 * Sets the Set of CountriesOfOrigin.
	 *
	 * @param  newVal
	 *
	 * @see    #getCountriesOfOrigin()
	 */
	public void setCountriesOfOrigin(Set<Country> newVal) {
		countriesOfOrigin = newVal;
	}

	/**
	 * Sets the English Description.
	 *
	 * @param  newVal
	 *
	 * @see    #getDescriptionE()
	 */
	public void setDescriptionE(String newVal) {
		descriptionE = newVal;
	}

	/**
	 * Sets the French Description.
	 *
	 * @param  newVal
	 *
	 * @see    #getDescrptionF()
	 */
	public void setDescriptionF(String newVal) {
		descriptionF = newVal;
	}

	/**
	 * Sets the Set of GeographicAreas.
	 *
	 * @param  newVal
	 *
	 * @see    #getGeographicAreas
	 */
	public void setGeographicAreas(Set<GeographicArea> newVal) {
		geographicAreas = newVal;
	}

	/**
	 * Sets the Name.
	 *
	 * @param  newVal
	 *
	 * @see    #getName()
	 */
	public void setName(String newVal) {
		name = newVal;
	}

	/**
	 * Sets the Set of OrganismParts.
	 *
	 * @param  newVal
	 *
	 * @see    #getOrganismParts()
	 */
	public void setOrganismParts(Set<OrganismPart> newVal) {
		organismParts = newVal;
	}

	/**
	 * Sets the Reference.
	 *
	 * @param  newVal
	 *
	 * @see    #getReference
	 */
	public void setReference(Reference newVal) {
		reference = newVal;
	}

	/**
	 * Sets the Species.
	 *
	 * @param  newVal
	 *
	 * @see    #getSpecies()
	 */
	public void setSpecies(Species newVal) {
		species = newVal;
	}

	/**
	 * Sets the SubTaxa.
	 *
	 * @param  newVal
	 *
	 * @see    #getSubTaxa()
	 */
	public void setSubTaxa(SubTaxa newVal) {
		subTaxa = newVal;
	}

	/**
	 * Sets the Taxa.
	 *
	 * @param  newVal
	 *
	 * @see    #getTaxa()
	 */
	public void setTaxa(Set<Taxon> newVal) {
		taxa = newVal;
	}

	/**
	 * Sets the OrganismType.
	 *
	 * @param  newVal
	 *
	 * @see    #getType()
	 */
	public void setType(OrganismType newVal) {
		type = newVal;
	}
}
