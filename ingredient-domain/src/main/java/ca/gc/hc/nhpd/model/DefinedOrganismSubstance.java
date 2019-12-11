package ca.gc.hc.nhpd.model;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/*******************************************************************************
 * An object that represents an organism substance ingredient that has a generally
 * accepted (and named) definition.
 */
public class DefinedOrganismSubstance extends Ingredient { 

	// Changed as part of IMSD - NHPOL - 8021
	// Previous - public static final String TYPE_OS_E = "Defined Organism Substance - Whole Organism";
    // Previous - public static final String TYPE_OS_F = "Substance dérivée d'un organisme défini - Organism en entier";
	public static final String TYPE_OS_E = "Organism";
    public static final String TYPE_OS_F = "Organisme";

    public static final String TYPE_DOS_E = "Defined Organism Substance";
    public static final byte[] TYPE_DOS_F_BYTES = 
    	{0x53, 0x75, 0x62, 0x73, 0x74, 0x61, 0x6e, 0x63, 0x65, 0x20,
    	 0x64, (byte)0xc3, (byte)0xa9, 0x72, 0x69, 0x76, (byte)0xc3, (byte)0xa9, 0x65, 0x20,
    	 0x64, 0x27, 0x75, 0x6e, 0x20,
    	 0x6f, 0x72, 0x67, 0x61, 0x6e, 0x69, 0x73, 0x6d, 0x65, 0x20,
    	 0x64, (byte)0xc3, (byte)0xa9, 0x66, 0x69, 0x6e, 0x69
    	 };
    public static final String TYPE_DOS_F = new String (TYPE_DOS_F_BYTES, StandardCharsets.UTF_8);
    public static final String AHN_CATEGORY = "AHN";
    
    private String authorizedNameE;
    private String authorizedNameF;
    private OrganismGroup organismGroup; //Defined using the Interface so Hibernate works
    private Map<Organism, Set<OrganismPart>> partMap;
    private Set<OrganismPart> parts;
    private Set<PreparationType> preparations;
    private Reference reference;
    private Set<Reference> standardGradeReferences;
    private transient Set<String> scientificNames;

	/***************************************************************************
	 * Gets the display name in the language appropriate for the Locale.
	 * @return the locale-specific display name.
	 */
	public String getDisplayableString(){
		return getAuthorizedName();
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
     * Gets the name in English.
     * @return the name in English.
     * @see #setNameE()
     */
    public String getAuthorizedNameE(){
        return authorizedNameE;
    }

    /***************************************************************************
     * Gets the name in French.
     * @return the name in French.
     * @see #setNameF()
     */
    public String getAuthorizedNameF(){
        return authorizedNameF;
    }
  
    /***************************************************************************
     * Returns the part list generated from the Taxonomy Node List.
     * Only used for the web services as part of the fix for ITS
     * issue 8379. We are created a list of organsim parts which 
     * is the cartesian product of the TaxonomyNode's name and 
     * each part. 
     */
    public Set<OrganismPart> getTaxonomyPartsList() {

    	Set<OrganismPart> completePartList  = getParts();
    	Set<OrganismPart> newPartList = new TreeSet<OrganismPart>();
 	
 		if (completePartList != null) { 			 
 			//iterate through the regular list of parts
 			for (OrganismPart orgPart : completePartList){
 				//add in the cartesian product of the taxonomy node
 				//name tree and the regular part.
 				for (String nodeName : getTaxonomyNodeNames()) {
 					newPartList.add(new OrganismPart(nodeName, orgPart.getType(), 
 							orgPart.getSubIngredients(), 
 							orgPart.getLastUpdateAccount()));
 				}
 			}
 		}
    	
        return newPartList;
    }
    
    /***************************************************************************
     * Returns the Source Material name.  Implements SourceMaterial method
     * @return the source material name
     */
    public String getSourceMaterialName()
    {
        return getAuthorizedName();
    }
    
    
	/***************************************************************************
	 * Specific implementation of the getScientific that returns the following
	 * information:
	 * 1.  Taxa of the organism of the same name in the case of a AHN
	 * 2.  Taxa of the parent organism in all other cases.
	 */
	public Set<String> getScientificNames() {
		if ( scientificNames == null ) {
			scientificNames = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);

			if ((getOrganismGroup() != null) && 
				(getOrganismGroup().getIncludedOrganisms() != null)) {	
				for (Organism organism : getOrganismGroup().getIncludedOrganisms()) {
					// In the case of a AHN, return only the taxa for the organism 
					// matching the ingredient.  In all other cases, return all taxa. 
					if ((getCategory().getCode().equals(AHN_CATEGORY)
						 && organism.getName().equalsIgnoreCase(getAuthorizedName()))
						|| !getCategory().getCode().equals(AHN_CATEGORY)) {
						for (Taxon taxon : organism.getTaxa()) {
							scientificNames.add( taxon.getName() );
						}
					}
				}
			}
		}
		return scientificNames;
	}

    /***************************************************************************
     */
    public OrganismGroup getOrganismGroup(){
        return organismGroup;
    }

    /**
     * Retrieves a Set of all Organism parts.  Used for
     * the WS Call.
     */
    public Set<OrganismPart> getPartList(){
    	Set<OrganismPart> partSet = new HashSet<OrganismPart>();
    	
    	if (isWholeOrganism()) {
    		partSet = getAllOrganismParts();
    	} else {
    		partSet.addAll(getParts());
    	}
    	
    	Set<OrganismPart> parts_taxonomy = getTaxonomyPartsList();
       	if (parts_taxonomy != null) {
       		partSet.addAll(parts_taxonomy);
       	}    		
   		return partSet;
     }
    
    /***************************************************************************
     * Gets the map of parts to organisms (keyed on Organism). Normally this
     * wouldn't be exposed (getPartsForOrganism() would be used), however it is
     * easier to code the JSP components directly to the Map.
     */
    public Map<Organism, Set<OrganismPart>> getPartMap(){
        if (partMap == null) { //Lazy initialize
            Set<OrganismPart> partSet;
            
            partMap = new HashMap<Organism, Set<OrganismPart>>();
            for (OrganismPart part : getParts()) {
                partSet = partMap.get(part.getOrganism());
                if (partSet == null) {
                    partSet = new HashSet<OrganismPart>();
                    partMap.put(part.getOrganism(), partSet);
                }
                partSet.add(part);
            }
        }
        return partMap;
    }

    /***************************************************************************
     * Note that clearPartMap() should be called if this Set is changed.
     */
    public Set<OrganismPart> getParts(){
        return parts;
    }

    /***************************************************************************
     */
    public Set<PreparationType> getPreparations(){
        return preparations;
    }

    /***************************************************************************
     */
    public Reference getReference(){
        return reference;
    }

    /***************************************************************************
     */
    public Set<Reference> getStandardGradeReferences(){
        return standardGradeReferences;
    }

    /***************************************************************************
     * Gets the type in the language appropriate for the Locale.
     * @return the locale-specific type.
     */
    public String getType(){
        if (isWholeOrganism()) {
            if (isLanguageFrench()) {
                return TYPE_OS_F;
            }
            return TYPE_OS_E;
        } else {
            if (isLanguageFrench()) {
                return TYPE_DOS_F;
            }
            return TYPE_DOS_E;
        }
    }
    
    /***************************************************************************
     * Gets a flag indicating that this Defined Organism Substance is a whole 
     * organism.  A whole organism is identified when the Defined Organism
     * Substance name is the same as that of the Organism it identifies.
     */
    public boolean isWholeOrganism() {
        if (getOrganismGroup() != null) {
            if (getOrganismGroup().getIncludedOrganisms() != null) {
                for (Organism organism : getOrganismGroup().getIncludedOrganisms()) {
                    if (organism.getName().toLowerCase().equals(getAuthorizedNameE().toLowerCase())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /***************************************************************************
     * Sets the name in English.
     * @param newVal the name in English
     * @see #getNameE()
     */
    public void setAuthorizedNameE(String newVal){
        authorizedNameE = newVal;
    }

    /***************************************************************************
     * Sets the name in French.
     * @param newVal the name in French
     * @see #getNameF()
     */
    public void setAuthorizedNameF(String newVal){
        authorizedNameF = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setOrganismGroup(SimpleOrganismGroup newVal){
        organismGroup = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setParts(Set<OrganismPart> newVal){
        clearPartMap(); //So it will be reloaded.
        parts = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setPreparations(Set<PreparationType> newVal){
        preparations = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setReference(Reference newVal){
        reference = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setStandardGradeReferences(Set<Reference> newVal){
        standardGradeReferences = newVal;
    }

    /***************************************************************************
     * Clears the map of parts to organisms so it will be reloaded the next time
     * that it is requested. This must be called whenever the collection of parts
     * is changed.
     */
    public void clearPartMap(){
        partMap = null;
    }

    /***************************************************************************
     * Gets the parts of the passed organism that are used as a source for this
     * substance. Returns null if there aren't any.
     * @param organism The organism in question.
     * @return the parts of the passed organism that are used as a source for
     *         this substance.
     */
    public Set<OrganismPart> getPartsForOrganism(Organism organism){
        return getPartMap().get(organism);
    }

    /**
     * Retrieves all Organism parts.  Use when this DOS is an Organism
     * (e.g. associated an Organism at the Taxonomy node level)
     * NB. Re-added from Stage 3 branch  
    	*/
    private Set<OrganismPart> getAllOrganismParts(){
        TreeSet<OrganismPart> organismParts = new TreeSet<OrganismPart>();
        
     	
        /* 
   	    * Check for simple organism type.  There are rare cases where 
   		* there may be more than one Organism in a SimpleOrganismGroup,
   		* so go thru the loop.  The organism part name is prefixed with the 
   		* organism, so the returned organismParts will be unique in the
   		* rare case of multiple Organisms in a SimpleOrganismGroup 
   		*/
        if (organismGroup instanceof SimpleOrganismGroup){
            for (Organism organism : organismGroup.getIncludedOrganisms()){
   				//add the collection to the return collection
   				organismParts.addAll(organism.getOrganismParts());
   			}            
        }
        
   		return organismParts;
     } 	
  	    
   /*
    * Extract the taxonomy node names from the DOS.  
 	* Path is:  OrganismGroup -> 
 	* 				Named|SimpleOrganismGroup -> 
 	* 					TaxonomyNodes|includedOrganisms
 	* @return Set of String taxonomy node names
 	*/
   	private Set<String> getTaxonomyNodeNames(){
 		Set<String> taxonomyNodeNames = new TreeSet<String>();
 			
 		if (organismGroup == null) {
 			return taxonomyNodeNames;   //empty TreeSet
        }
 		Set taxonomyNodes = null;       //<TaxonomyNode>
 		
 		if (organismGroup instanceof NamedOrganismGroup){
 			taxonomyNodes = ((NamedOrganismGroup)organismGroup)
                             .getIncludedTaxonomyNodes();

        }else{
 			taxonomyNodes = organismGroup.getIncludedOrganisms();	
 		}
 			
 		if (taxonomyNodes != null){
 			Iterator iter = taxonomyNodes.iterator();
 	   		while (iter.hasNext()){
 	   			TaxonomyNode node = (TaxonomyNode)iter.next();
 	   			taxonomyNodeNames.add(node.getDisplayName());
 	   		}
 		}

 		return taxonomyNodeNames;
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
        buffer.append(", nameE: ");
        buffer.append(getAuthorizedNameE());
        buffer.append(", nameF: ");
        buffer.append(getAuthorizedNameF());
        buffer.append(", standardGradeReferences: ");
        buffer.append(getStandardGradeReferences());
        
        return buffer.toString();
    }
}