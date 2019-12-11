package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import ca.gc.hc.nhpd.dto.RelatedSourceIngredient;
import ca.gc.hc.nhpd.dto.SourceOrganismPart;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.TreeMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*******************************************************************************
 * An object that represents a generally accepted chemical substance definition.
 */
public class ChemicalSubstance extends Ingredient 
    implements ChemicalImpurity {
    
    private static final Log log = LogFactory.getLog(ChemicalSubstance.class);
    
    public static final String TYPE_CHEMICAL_SUBSTANCE_E = "Chemical Substance";
    public static final String TYPE_CHEMICAL_SUBSTANCE_F = "Substance chimique";
    public static final String TYPE_PROTEIN_E = "Protein Substance";
    public static final String TYPE_PROTEIN_F = "Substance prot�ique";
    public static final String TYPE_HERBAL_COMPONENT_E = "Herbal Component";
    public static final String TYPE_HERBAL_COMPONENT_F = "Composante d'origine v�g�tale";
	
    private RegistryEntry casEntry;
    private ChemicalSubclass chemicalSubclass;
    private Attachment image;
    private String authorizedNameE;
    private String authorizedNameF;
   
	private Set<RegistryEntry> otherRegistryEntries;
    private Set<TestMethod> purityTestMethods;
    private Reference reference;
    private Set<SubIngredient> relatedSources;
    private String smilesNotation;
    private Set<Reference> standardGradeReferences;
    private BasicRestriction toxicityRestriction;
    
    /** used for IWS only - expose DTO collections */
    private List<SourceOrganismPart> sourceOrganismPartDtos;
    private List<RelatedSourceIngredient> relatedSourceIngredients;
    
	private boolean group;

	/**
     * Public Constructure required by hibernate 3.3 to eliminate the 
     * "Javassist Enhancement failed" error when running the Ingredient Loader. 
     */
    public ChemicalSubstance() {}
    
	
   /*
    * Ingredient Web Service specific collections
    */	
    public List<SourceOrganismPart> getSourceOrganismPartDtos() {
		return sourceOrganismPartDtos;
	}
	public void setSourceOrganismPartDtos(
			List<SourceOrganismPart> sourceOrganismPartDtos) {
		this.sourceOrganismPartDtos = sourceOrganismPartDtos;
	}
	public List<RelatedSourceIngredient> getRelatedSourceIngredientDtos() {
		return relatedSourceIngredients;
	}
	public void setRelatedSourceIngredientDtos(
			List<RelatedSourceIngredient> relatedSourceIngredients) {
		this.relatedSourceIngredients = relatedSourceIngredients;
	}

	/***************************************************************************
     */
    public RegistryEntry getCasEntry(){
        return casEntry;
    }

	/***************************************************************************
	 * Gets the display name in the language appropriate for the Locale.
	 * @return the locale-specific display name.
	 */
	public String getDisplayableString(){
		return getAuthorizedName();
	}

    public String getSourceMaterialName()
    {
        return getAuthorizedName();
    }        
    
    
    /***************************************************************************
     * Gets the chemicalSubclass (Phytochemical group) for this component.
     * @return the chemicalSubclass (Phytochemical group) for this component.
     */
    public ChemicalSubclass getChemicalSubclass(){
        return chemicalSubclass;
    }

    
	/***************************************************************************
 	 * Gets whether this is single or a group using a string.
 	 * Added to circumvent the PropertyAccessException generated by hibernate
 	 * when non-boolean data (example y, n, null) is being referenced.
 	 * @return true if this is a group.
 	 * @see #setGroup(), @link #isGroup(), #getGroupAsString()
 	 */
	public void setGroupAsString(String value){
		if ( "Y".equals(value) ) {
			group = true;
		} else {
			group = false;
		}
	}

	/***************************************************************************
 	 * Gets whether this is single or a group in a string format.
 	 * Added to circumvent the PropertyAccessException generated by hibernate
 	 * when non-boolean data (example y, n, null) is being referenced.
  	 * @return true if this is a group.
 	 * @see #setGroup(), #isGroup(), #setGroupAsString()
	 */
	public String getGroupAsString(){
		if ( group ) {
			return "Y";
		} else {
			return "N";
		}
	}

	/***************************************************************************
	 * Gets whether this is single or a group.
	 * @return true if this is a group.
	 * @see #setGroup(), #setGroupAsString(String)
	 */
	public boolean isGroup(){
		return group;
	}

    /***************************************************************************
     */
    public Attachment getImage(){
        return image;
    }

    /***************************************************************************
     * Gets the name in the language appropriate for the Locale.
     * @return the locale-specific name.
     */
    public String getAuthorizedName(){
        if (isLanguageFrench()) {
            return getAuthorizedNameF();
        }
        return getAuthorizedNameE();
    }

    /***************************************************************************
     * Gets the authorized name in English.
     * @return the authorized name in English.
     * @see #setAuthorizedNameE()
     */
    public String getAuthorizedNameE(){
        return authorizedNameE;
    }

    /***************************************************************************
     * Gets the authorized name in French.
     * @return the authorized name in French.
     * @see #setAuthorizedNameF()
     */
    public String getAuthorizedNameF(){
        return authorizedNameF;
    }

    /***************************************************************************
     */
    public Set<RegistryEntry> getOtherRegistryEntries(){
        return otherRegistryEntries;
    }

    /***************************************************************************
     */
    //TODO appears not to be used
    /*
    public Set getParentIngredients() {
        return parentIngredients;
    }*/

    /***************************************************************************
     */
    //TODO appears not to be used
    /*
    public void setParentIngredients(Set newval) {
        parentIngredients = newval;
    }*/

    /***************************************************************************
     */
    public Set<TestMethod> getPurityTestMethods(){
        return purityTestMethods;
    }

    /***************************************************************************
     */
    public Reference getReference(){
        return reference;
    }

	/***************************************************************************
     * Gets the SMILES notation for this chemical. The Simplified Molecular
     * Input Line Entry System is a line notation (a typographical method using
     * printable characters) for entering and representing molecules and
     * reactions.
     * @return the SMILES notation for this chemical.
     * @see #setSmilesNotation()
     */
    public String getSmilesNotation(){
        return smilesNotation;
    }

    /***************************************************************************
     */
    public Set<Reference> getStandardGradeReferences(){
        return standardGradeReferences;
    }

    /***************************************************************************
     * Gets the SubIngredient objects that wrap around this ChemicalSubstance.
     * Through these, the "parent" Ingredients and OrganismParts that can be
     * used as sources for this ChemicalSubstance can be accessed.
     */
    public Set<SubIngredient> getRelatedSources(){
        return relatedSources;
    }

	/***************************************************************************
	 * Traverses the Ingredient - SubIngredient relationship through the related 
	 * sources to return a set of Source Ingredients.
	 */
	public Set<Ingredient> getRelatedSourceIngredients(){
		if (relatedSources == null) {
			return null;
		}
		Set<Ingredient> relatedSourceIngredients = new TreeSet<Ingredient>();
		for (SubIngredient subIngredient : relatedSources) {
			if ( subIngredient.isIngredientSource() ) {
				relatedSourceIngredients.add(
                        subIngredient.getParentIngredient());
			}
		}
		return relatedSourceIngredients;
	}

	/***************************************************************************
	 * Traverses the Ingredient - SubIngredient relationship through the related 
	 * sources to return a set of Source Organism Parts.
	 */
	public Set<OrganismPart> getRelatedSourceOrganismParts(){
		
		long start = System.currentTimeMillis();
		if (relatedSources == null) {
			return null;
		}
		Set<OrganismPart> relatedSourceOrganismParts = 
                          new TreeSet<OrganismPart>();
        for (SubIngredient subIngredient : relatedSources) {
			if ( subIngredient.isOrganismPartSource() ) {
				relatedSourceOrganismParts.add(
                        subIngredient.getParentOrganismPart());
			}
		}
        long elapsed = System.currentTimeMillis() - start;
        log.debug("ChemicalSubstance.getRelatedSourceOrganismParts: time:" + elapsed );
		return relatedSourceOrganismParts;
	}

    /***************************************************************************
     * Returns the string list generated from the Taxonomy Node List.
     * Only used for the web services as part of the fix for ITS
     * issue 3480. Also for reference ITS 8379. The list of taxonomy nodes 
     * will be used to create a source organism part list.
     */
    public TreeMap<String, Set<String>> getTaxonomyPartsList() {
    	Set<OrganismPart> partList = getRelatedSourceOrganismParts();
    	Set<String> taxonomyParts = new TreeSet<String>(); 
    	TreeMap<String, Set<String>> taxonomyPartList = new TreeMap<String, Set<String>>();
    	
 		if (partList != null){
  			for (OrganismPart orgPart : partList){
  			    if (orgPart.getOrganismGroup() != null) {
	  				Set<TaxonomyNode> tnSet = orgPart.getOrganismGroup().getIncludedTaxonomyNodes();
	  				for (TaxonomyNode tn:tnSet) {
	  					taxonomyParts.add(tn.getDisplayName());
	  				}
	  				taxonomyPartList.put(orgPart.getName(), taxonomyParts);
	  				taxonomyParts = new TreeSet<String>();
  			    }
  			}
  		}
 		
        return taxonomyPartList;
    }
    
    /***************************************************************************
     */
    public BasicRestriction getToxicityRestriction(){
        return toxicityRestriction;
    }

    /***************************************************************************
     * Gets the type in the language appropriate for the Locale.
     * Modified to return the name "Protein" when the ingredient
     * category is an "APN" or the name "Herbal Component" 
     * when the ingredient category is an "HCN".
     * @return the locale-specific type.
     */
    public String getType(){
        // Return the protein if category code is APN
        if ( getCategory() != null ) {
            if ( IngredientCategory.PROTEIN_CODE.equals(getCategory().getCode())) {
            	if (isLanguageFrench()) {
                	return TYPE_PROTEIN_F;
            	}
            	return TYPE_PROTEIN_E;
            } else if ( IngredientCategory.HERBAL_COMPONENT_CODE.equals(getCategory().getCode())) {
				if (isLanguageFrench()) {
					return TYPE_HERBAL_COMPONENT_F;
				}
				return TYPE_HERBAL_COMPONENT_E;
            }
        }
        // Return the normal chemical.
        if (isLanguageFrench()) {
            return TYPE_CHEMICAL_SUBSTANCE_F;
        }
        return TYPE_CHEMICAL_SUBSTANCE_E;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setCasEntry(RegistryEntry newVal){
        casEntry = newVal;
    }

    /***************************************************************************
     * Sets the chemicalSubclass (Phytochemical group) for this component.
     * @param value the chemicalSubclass (Phytochemical group) for this
     *        component.
     */
    public void setChemicalSubclass(ChemicalSubclass value){
        chemicalSubclass = value;
    }

	/***************************************************************************
	 * Sets whether this is single or a group.
	 * @param newVal true if this is a group.
	 * @see #isGroup(), #setGroupAsString(String), #getGroupAsString(String)
	 */
	public void setGroup(boolean newVal){
		group = newVal;
	}
	
    /***************************************************************************
     * @param newVal
     */
    public void setImage(Attachment newVal){
        image = newVal;
    }

    /***************************************************************************
     * Sets the authorized name in English.
     * @param newVal the authorized name in English
     * @see #getAuthorizedNameE()
     */
    public void setAuthorizedNameE(String newVal){
		// Reset the allNames to ensure it pick up new name value.
		// super.allNames = null;
        authorizedNameE = newVal;
    }

    /***************************************************************************
     * Sets the name in French.
     * @param newVal the name in French
     * @see #getNameF()
     */
    public void setAuthorizedNameF(String newVal){
		// Reset the allNames to ensure it pick up new name value.
		// super.allNames = null;
        authorizedNameF = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setOtherRegistryEntries(Set<RegistryEntry> newVal){
        otherRegistryEntries = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setPurityTestMethods(Set<TestMethod> newVal){
        purityTestMethods = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setReference(Reference newVal){
        reference = newVal;
    }

    /***************************************************************************
     * Sets the SubIngredient objects that wrap around this ChemicalSubstance.
     * Through these, the "parent" Ingredients and OrganismParts that can be
     * used as sources for this ChemicalSubstance can be accessed.
     * @param newVal
     */
    public void setRelatedSources(Set<SubIngredient> newVal){
        relatedSources = newVal;
    }
    
    /***************************************************************************
     * Sets the SMILES notation for this chemical. The Simplified Molecular
     * Input Line Entry System is a line notation (a typographical method using
     * printable characters) for entering and representing molecules and
     * reactions.
     * @param aString the SMILES notation for this chemical.
     * @see #getSmilesNotation()
     */
    public void setSmilesNotation(String aString){
        smilesNotation = aString;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setStandardGradeReferences(Set<Reference> newVal){
        standardGradeReferences = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setToxicityRestriction(BasicRestriction newVal){
        toxicityRestriction = newVal;
    }

    /***************************************************************************
     * Over-ridden to support collections of ChemicalImpurities.
     */
    public int compareTo(Object o) throws ClassCastException {
        if (getAuthorizedName() == null) {
            return 1;
        }
        if (o == null) {
            return -1;
        }
        if (o instanceof ChemicalImpurity) {
            //case insensitive, Locale sensitive sort
            return StringComparator.compare(getAuthorizedName(),
        			 ((ChemicalImpurity)o).getAuthorizedName());
        }
        if (!(o instanceof Ingredient)) {
            log.error("ChemicalSubstance can't sort with a " +
                      o.getClass().getName());
        }
        return super.compareTo(o); //Must be another type of Ingredient
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
        buffer.append(", authorizedNameE: ");
        buffer.append(getAuthorizedNameE());
        buffer.append(", authorizedNameF: ");
        buffer.append(getAuthorizedNameF());
        buffer.append(", chemicalSubclass: ");
        buffer.append(getChemicalSubclass());
        buffer.append(", casEntry: ");
        buffer.append(getCasEntry());
        if (getOtherRegistryEntries() != null 
            && !getOtherRegistryEntries().isEmpty()) {
            buffer.append(", otherRegistryEntries: ");
            buffer.append(getOtherRegistryEntries());
        }
        buffer.append(", smilesNotation: ");
        buffer.append(getSmilesNotation());
        buffer.append(", standardGradeReferences: ");
        buffer.append(getStandardGradeReferences());
        buffer.append(", purityTestMethods: ");
        buffer.append(getPurityTestMethods());
        buffer.append(", toxicityRestriction: ");
        buffer.append(getToxicityRestriction());
        buffer.append(", reference: ");
        buffer.append(getReference());

        
        return buffer.toString();
    }
    
}
