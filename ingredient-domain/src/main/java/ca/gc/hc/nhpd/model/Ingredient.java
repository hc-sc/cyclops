package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import ca.gc.hc.nhpd.util.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/*******************************************************************************
 * An abstract class intended to be extended by different types of ingredients.
 */
public abstract class Ingredient extends PersistentObject
                                 implements Comparable {
    private static final Log LOG = LogFactory.getLog(Ingredient.class);

    public static final String INGREDIENT_DOS = "DefinedOrganismSubstance";
    private String additionalDetailE;
    private String additionalDetailF;
    private IngredientCategory category;
    private Set<ChemicalImpuritySubstance> chemicalImpuritySubstances =
            new TreeSet<ChemicalImpuritySubstance>();
    private Set<ChemicalSubstance> chemicalSubstances = 
            new TreeSet<ChemicalSubstance>();
    private String commentE;
    private String commentF;
    private Set<QualifiedSynonym> commonNames;
    private Set<QualifiedSynonym> properNames;
    private Set<QualifiedSynonym> qualifiedSynonyms;
    
    private String descriptionE;
    private String descriptionF;
    private Map<DosageFormGroup, TestSpecification> dosageFormGroupTestSpecMap =
            new HashMap<DosageFormGroup, TestSpecification>();
    private Set<MicrobiologicalImpurity> microbiologicalImpurities;
    private Set<MonographEntry> monographEntries;
    private Set<NhpClassification> nhpClassifications;
    private Double quantityLowerTolerance;
    private Double quantityUpperTolerance;
    private Set<IngredientRole> roles;
    private IngredientSource source;
    private Set<SubIngredient> subIngredients;

    /***************************************************************************
     * Gets the name in the language appropriate for the Locale.
     * @return the locale-specific name.
     */
    public abstract String getAuthorizedName();
    
	/***************************************************************************
	 * Gets the list of Proper Names.
	 * 
	 * @return the list of Proper Names.
	 */
	public Set<QualifiedSynonym> getProperNames() {
		return properNames;
	}
	
	/* massages the proper names for display purposes.  We reuse the Synonym 
	 * class here as a container of id and name attributes.  
	 */ 
	public Set<Synonym> getDisplayProperNames() {
		
		Set<Synonym> displayProperNames = new TreeSet<Synonym>();
		Synonym retSynonym = null;
		String retName = null;
		try {
			if (properNames != null) {
				Iterator iter = properNames.iterator();
				while (iter.hasNext()) {
					QualifiedSynonym qs = (QualifiedSynonym)iter.next();
					String name = qs.getName();
					
					if (name == null) {	
						LOG.warn("null qualified Synonym Name!");
					}
					else {
						retSynonym = new Synonym();
						retSynonym.setId(qs.getId());
						retName = qs.getName();
						//gets rid of unbreakable sequences
						if (retName.length() > 40) {
							retName = StringUtils.breakText(retName,40);
						}
						if (isLanguageFrench())
							retSynonym.setNameF(retName);
						else
							retSynonym.setNameE(retName);
						displayProperNames.add(retSynonym);
					}
					
				}
			}
		}
		catch (Throwable ex) {
			ex.printStackTrace();
		}
		
		return displayProperNames;
	
	}
	
	
	/***************************************************************************
	 * Sets the list of Proper Names.
	 * 
	 * @return the list of Proper Names.
	 */
	public void setProperNames(Set<QualifiedSynonym> newValue) {
		properNames = newValue;
	}

	/***************************************************************************
	 * Gets a list of scientific names in the language appropriate for the Locale.
	 * The proper name implementation varies from type to type and depending on 
	 * other object specific business rules over written by specific types.
	 * 1.  In general, the scientific name is null and is over-written by
	 * specific types. 
	 * @return the locale-specific list of scientific names.
	 */
    //TODO is this still required?
	public Set<String> getScientificNames(){
		return null;
	}

    /***************************************************************************
     * Gets the type in the language appropriate for the Locale.
     * @return the locale-specific name.
     */
    public abstract String getType();

    /***************************************************************************
     */
    public IngredientCategory getCategory(){
        return category;
    }

    /***************************************************************************
     * The additional detail was added as part of CR-0049, RedMine issue #2290
     * by GB on 2010/03/30.
     */
    public String getAdditionalDetailE(){
        return additionalDetailE;
    }

    public String getAdditionalDetailF(){
        return additionalDetailF;
    }
    
    public void setAdditionalDetailE(String additionalDetailE) {
        this.additionalDetailE = additionalDetailE;
    }

    public void setAdditionalDetailF(String additionalDetailF) {
        this.additionalDetailF = additionalDetailF;
    }

    public String getAdditionalDetail(){
        if (isLanguageFrench()) {
            return additionalDetailF;
        } else {
            return additionalDetailE;
        }
    }
    
    /***************************************************************************
     * Method that returns all the names used by an ingredient.  Names include
     * approved names, proper names, scientific names, common names, and 
     * depending on the type of ingredient (ChemicalSubstance) will also return
     * related subingredients, related source ingredients, and related source
     * organism parts.
     */
    private String listAllNames(){

        StringBuilder sb = new StringBuilder();

        sb.append( "\n\n" );
        sb.append( "Ingredient Id: " + getId() + "\n");
        sb.append( "Ingredient Authorized Name: \n" );
        sb.append( "  => " + getAuthorizedName() + "\n" );
        
        sb.append( "Ingredient Proper Names:\n");
        sb.append( testExtractNames( "properNames" ) );

        sb.append( "Ingredient Scientific Names:\n");
        sb.append( testExtractNames( "scientificNames" ) );

        sb.append( "Ingredient Common/All Names:\n");
        sb.append( testExtractNames( "allNames" ) );

        
        if ( getClass().getName().equals("ca.gc.hc.nhpd.model.ChemicalSubstance")){
            
            ChemicalSubstance cs = (ChemicalSubstance) this;

            sb.append( "Related SubIngredients:\n" );
            if ( cs.getSubIngredients().size() == 0 ) {
                sb.append( "  => N.A.\n");
            } else {
                Iterator i = cs.getSubIngredients().iterator();
                while ( i.hasNext() ) {
                    SubIngredient subIngredient = (SubIngredient) i.next();
                    sb.append( "  => " + subIngredient.getIngredient().getAuthorizedName() + "\n" );
                }
            }
            
            sb.append( "Related Source Ingredients:\n" );
            if ( cs.getRelatedSourceIngredients().size() == 0 ) {
                sb.append( "  => N.A.\n");
            } else {
                Iterator i = cs.getRelatedSourceIngredients().iterator();
                while ( i.hasNext() ) {
                    Ingredient sourceIngredient = (Ingredient) i.next();
                    sb.append( "  => " + sourceIngredient.getAuthorizedName() + "\n" );
                }
            }
            
            sb.append( "Related Source Organism Part:\n" );
            if ( cs.getRelatedSourceOrganismParts().size() == 0 ) {
                sb.append( "  => N.A.\n");
            } else {
                Iterator i = cs.getRelatedSourceOrganismParts().iterator();
                while ( i.hasNext() ) {
                    OrganismPart sourceOrganisms = (OrganismPart) i.next();
                    sb.append( "  => " + sourceOrganisms.getName() + "\n" );
                }
            }
        }
        
        return sb.toString();
    }
    
    /**
     * Test method that uses introspection to convert a list of names into string. 
     * @param o object being explored
     * @param targetString name of the object field we are exploring.  The field
     * must be contained in a set of string.
     * @return a new line seperated string reprenting the target object text values.
     */
    private String testExtractNames( String targetString ) {
        String returnString = "";
        try {
            Set properNames = (Set) PropertyUtils.getSimpleProperty(this, targetString);
            if ( properNames == null || properNames.size() == 0 ) {
                returnString = "  => N.A.\n";
            } else {
                Iterator i = properNames.iterator();
                while ( i.hasNext() ) {
                    String ingredientName = (String) i.next();
                    returnString = returnString + "  => " + ingredientName + "\n";
                }
            }
        } catch ( Exception e ) { 
            e.printStackTrace();
        }
        return returnString;        
    }


    /***************************************************************************
     * Gets the ChemicalImpurities that exist in this Ingredient. Note that this
     * set is immutable, since it is the composite of entries from 2 other sets.
     * Use the add, remove, and set methods to make changes to it.
     * @return the ChemicalImpurities that exist in this Ingredient
     * @see #addChemicalImpurity()
     * @see #removeChemicalImpurity()
     * @see #setChemicalImpurities()
     */
    public Set<ChemicalImpurity> getChemicalImpurities(){
        Set<ChemicalImpurity> chemicalImpurities = new TreeSet<ChemicalImpurity>(
                              getChemicalImpuritySubstances());
        
        chemicalImpurities.addAll(getChemicalSubstances());
        return Collections.unmodifiableSet(chemicalImpurities);
    }

    /***************************************************************************
     * Gets the ChemicalImpuritySubstances that exist in this Ingredient (these
     * are impurities that are not otherwise stored in the system as
     * Ingredients). Normally chemical impurities are dealt with in an abstract
     * sense, however Hibernate requires the two different types be dealt with
     * separately since they are stored in different parts of the persistent
     * store. This method is typically only used by Hibernate.
     * @return the ChemicalImpuritySubstances that exist in this Ingredient
     * @see #getChemicalImpurities()
     * @see #setChemicalImpuritySubstances()
     */
    public Set<ChemicalImpuritySubstance> getChemicalImpuritySubstances(){
        return chemicalImpuritySubstances;
    }

    /***************************************************************************
     * Gets the ChemicalSubstance impurities that exist in this Ingredient.
     * Normally chemical impurities are dealt with in an abstract sense, however
     * Hibernate requires the two different types be dealt with separately since
     * they are stored in different parts of the persistent store. This method
     * is typically only used by Hibernate.
     * @return the ChemicalSubstance impurities that exist in this Ingredient
     * @see #getChemicalImpurities()
     * @see #setChemicalSubstances()
     */
    public Set<ChemicalSubstance> getChemicalSubstances(){
        return chemicalSubstances;
    }
    
    /***************************************************************************
     */
    public Set<QualifiedSynonym> getCommonNames(){
        return commonNames;
    }

    /***************************************************************************
     */
    public Set<QualifiedSynonym> getQualifiedSynonyms(){
        return qualifiedSynonyms;
    }
    
    /***************************************************************************
     */
    public void setQualifiedSynonyms(Set<QualifiedSynonym> newValue){
        qualifiedSynonyms = newValue;
    }
    
    
    /***************************************************************************
     * Gets the comment in the language appropriate for the Locale.
     * @return the locale-specific comment.
     */
    public String getComment(){
        if (isLanguageFrench()) {
            return getCommentF();
        }
        return getCommentE();
    }

    /***************************************************************************
     * Gets the comment in English.
     * @return the comment in English.
     * @see #setCommentE()
     */
    public String getCommentE(){
        return commentE;
    }

    /***************************************************************************
     * Gets the comment in French.
     * @return the comment in French.
     * @see #setCommentF()
     */
    public String getCommentF(){
        return commentF;
    }

    /***************************************************************************
     * Gets the description in the language appropriate for the Locale.
     * @return the locale-specific description.
     */
    public String getDescription(){
        if (isLanguageFrench()) {
            return getDescriptionF();
        }
        return getDescriptionE();
    }

    /***************************************************************************
     * Gets the description in English.
     * @return the description in English.
     * @see #setDescriptionE()
     */
    public String getDescriptionE(){
        return descriptionE;
    }

    /***************************************************************************
     * Gets the description in French.
     * @return the description in French.
     * @see #setDescriptionF()
     */
    public String getDescriptionF(){
        return descriptionF;
    }

    /***************************************************************************
     * Used by Hibernate. Use getQtyLowerToleranceForDosageForm() and
     * getQtyUpperToleranceForDosageForm() instead.
     */
    public Map<DosageFormGroup, TestSpecification> getDosageFormGroupTestSpecMap(){
        return dosageFormGroupTestSpecMap;
    }

    /***************************************************************************
     * Gets a collection of the DosageFormGroups that have identified tolerance
     * information for this Ingredient.
     */
    public Set<DosageFormGroup> getDosageFormGroupsWithToleranceInfo(){
        return dosageFormGroupTestSpecMap.keySet();
    }

    /***************************************************************************
     */
    public Set<MicrobiologicalImpurity> getMicrobiologicalImpurities(){
        return microbiologicalImpurities;
    }

    /***************************************************************************
     */
    public Set<MonographEntry> getMonographEntries(){
        return monographEntries;
    }

    /**
     * Returns a set of non Abls Monograph Entries.
     * 
     * @return a set of single ingredient monograph entries.
     */
    public Set<MonographEntry> getNonAblsMonographEntries(){
    	
    	Set<MonographEntry> nonAblsMonographEntries = new TreeSet<MonographEntry>();
        
    	for(MonographEntry monographEntry : getMonographEntries()) {
            
    		if (!monographEntry.getMonograph().isAbls() && ! monographEntry.getMonograph().isAblsInternal()) {
    			nonAblsMonographEntries.add(monographEntry);
    		}
    	}
    	return nonAblsMonographEntries;
    }
    
    /**
     * Returns a set of AbLS monograph entries.  This list contains
     * monographs that have the SQL validation level of 'ALS' and not 'Product'
     * 
     * @return a set of ALS monograph entries.
     */
    public Set<MonographEntry> getAblsMonographEntries(){
    	Set<MonographEntry> ablsMonographEntries = new TreeSet<MonographEntry>();
    	for(MonographEntry monographEntry : getMonographEntries()) {
    		if (monographEntry.getMonograph().isAbls() 
    				&& !monographEntry.getMonograph().isAblsInternal()
    				&& !monographEntry.getMonograph().isProductAbLS()) {
    			ablsMonographEntries.add(monographEntry);
    		}
    	}
    	return ablsMonographEntries;
    }

    /**
     * Returns a set of Product AbLS monograph entries.  This list contains
     * monographs that have the SQL validation level of 'ALS' and 'Product'.
     * 
     * @return a set of ALS monograph entries.
     */
    public Set<MonographEntry> getAblsProductMonographEntries(){
    	Set<MonographEntry> ablsMonographEntries = new TreeSet<MonographEntry>();
    	for(MonographEntry monographEntry : getMonographEntries()) {
    		if (monographEntry.getMonograph().isAbls() 
    				&& !monographEntry.getMonograph().isAblsInternal()
    				&& monographEntry.getMonograph().isProductAbLS()) {
    			ablsMonographEntries.add(monographEntry);
    		}
    	}
    	return ablsMonographEntries;
    }

    /**
     * Returns a set of AbLS Internal monograph entries.  This list contains
     * monographs that have the SQL validation level of 'ALS'.
     * 
     * @return a set of ALS internal monograph entries.
     */
    public Set<MonographEntry> getAblsInternalMonographEntries(){
        Set<MonographEntry> ablsMonographEntries = new TreeSet<MonographEntry>();
        for(MonographEntry monographEntry : getMonographEntries()) {
            if (monographEntry.getMonograph().isAblsInternal()) {
                ablsMonographEntries.add(monographEntry);
            }
        }
        return ablsMonographEntries;
    }

    /**
     * Returns a set of product monograph entries.  This list contains
     * monographs that have the SQL validation level of '???'.
     * 
     * @return a set of product monograph entries.
     */
    public Set<MonographEntry> getProductMonographEntries(){
    	Set<MonographEntry> monographEntries = new TreeSet<MonographEntry>();
    	for(MonographEntry monographEntry : monographEntries) {
    		if (monographEntry.getMonograph().isProduct()) {
    			monographEntries.add(monographEntry);
    		}
    	}
    	return monographEntries;
    }

    /***************************************************************************
     */
    public Set<NhpClassification> getNhpClassifications(){
        return nhpClassifications;
    }

    /***************************************************************************
     */
    public Double getQuantityLowerTolerance(){
        return quantityLowerTolerance;
    }

    /***************************************************************************
     */
    public Double getQuantityUpperTolerance(){
        return quantityUpperTolerance;
    }

    /***************************************************************************
     */
    public Set<IngredientRole> getRoles(){
        return roles;
    }

	/***************************************************************************
	 * Gets the English short description used to represent this object in 
	 * drop down lists and other various interfaces. 
	 * @return the English specific short description.
	 */
	public String getShortDescriptionE() {
		return getAuthorizedName();
	}

	/***************************************************************************
	 * Gets the English short description used to represent this object in 
	 * drop down lists and other various interfaces. 
	 * @return the French specific short description.
	 */
	public String getShortDescriptionF() {
		return getAuthorizedName();
	}

    /***************************************************************************
     */
    public IngredientSource getSource(){
        return source;
    }

    /***************************************************************************
     */
    public Set<SubIngredient> getSubIngredients(){
        return subIngredients;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setCategory(IngredientCategory newVal){
        category = newVal;
    }

    /***************************************************************************
     * Sets the ChemicalImpurities that exist in this Ingredient.
     * @param newVal the ChemicalImpurities that exist in this Ingredient
     * @see #getChemicalImpurities()
     * @see #addChemicalImpurity()
     * @see #removeChemicalImpurity()
     */
    public void setChemicalImpurities(Set<ChemicalImpurity> newVal){
        getChemicalImpuritySubstances().clear();
        getChemicalSubstances().clear();
        if (newVal != null) {
            for (ChemicalImpurity impurity : newVal) {
               if (impurity instanceof ChemicalImpurity) {
                    addChemicalImpurity((ChemicalImpurity)impurity);
                } else {
                    LOG.error("Item not a ChemicalImpurity: " +
                              impurity.getClass().getName());
                }
            }
        }
    }

    /***************************************************************************
     * Sets the ChemicalImpuritySubstances that exist in this Ingredient (these
     * are impurities that are not otherwise stored in the system as
     * Ingredients). Normally chemical impurities are dealt with in an abstract
     * sense, however Hibernate requires the two different types be dealt with
     * separately since they are stored in different parts of the persistent
     * store. This method is typically only used by Hibernate.
     * @param newVal the ChemicalImpuritySubstances that exist in this Ingredient
     * @see #setChemicalImpurities()
     * @see #getChemicalImpuritySubstances()
     */
    public void setChemicalImpuritySubstances(Set<ChemicalImpuritySubstance> newVal){
        if (newVal != null) {
            chemicalImpuritySubstances = newVal;
        } else {
            chemicalImpuritySubstances.clear();
        }
    }

    /***************************************************************************
     * Sets the ChemicalSubstance impurities that exist in this Ingredient.
     * Normally chemical impurities are dealt with in an abstract sense, however
     * Hibernate requires the two different types be dealt with separately since
     * they are stored in different parts of the persistent store. This method
     * is typically only used by Hibernate.
     * @param newVal the ChemicalSubstance impurities that exist in this
     *        Ingredient
     * @see #setChemicalImpurities()
     * @see #getChemicalSubstances()
     */
    public void setChemicalSubstances(Set<ChemicalSubstance> newVal){
        if (newVal != null) {
            chemicalSubstances = newVal;
        } else {
            chemicalSubstances.clear();
        }
    }

    /***************************************************************************
     * @param newVal
     */
    public void setCommonNames(Set<QualifiedSynonym> newVal){
    	commonNames = newVal;
     }

    /***************************************************************************
     * @param newVal
     */
    public void setCommentE(String newVal){
        commentE = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setCommentF(String newVal){
        commentF = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setDescriptionE(String newVal){
        descriptionE = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setDescriptionF(String newVal){
        descriptionF = newVal;
    }

    /***************************************************************************
     * Used by Hibernate. Use setQtyLowerToleranceForDosageForm() and
     * setQtyUpperToleranceForDosageForm() instead.
     */
    public void setDosageFormGroupTestSpecMap(
                Map<DosageFormGroup, TestSpecification> aMap){
        if (aMap != null) {
            dosageFormGroupTestSpecMap = aMap;
        } else {
            dosageFormGroupTestSpecMap.clear();
        }
    }

    /***************************************************************************
     * @param newVal
     */
    public void setMicrobiologicalImpurities(Set<MicrobiologicalImpurity> newVal){
        microbiologicalImpurities = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setMonographEntries(Set<MonographEntry> newVal){
        monographEntries = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setNhpClassifications(Set<NhpClassification> newVal){
        nhpClassifications = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setQuantityLowerTolerance(Double newVal){
        quantityLowerTolerance = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setQuantityUpperTolerance(Double newVal){
        quantityUpperTolerance = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setRoles(Set<IngredientRole> newVal){
        roles = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setSource(IngredientSource newVal){
        source = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setSubIngredients(Set<SubIngredient> newVal){
        subIngredients = newVal;
    }

    /***************************************************************************
     * Adds the passed ChemicalImpurity to the set of impurities that exist in
     * this Ingredient.
     * @param impurity the ChemicalImpurity to be added
     * @see #getChemicalImpurities()
     * @see #setChemicalImpurities()
     * @see #removeChemicalImpurity()
     */
    public void addChemicalImpurity(ChemicalImpurity impurity){
        if (impurity instanceof ChemicalSubstance) {
            getChemicalSubstances().add((ChemicalSubstance)impurity);
        } else { //Must be a ChemicalImpuritySubstance
            getChemicalImpuritySubstances().add((ChemicalImpuritySubstance)impurity);
        }
    }

    /***************************************************************************
     * Removes the passed ChemicalImpurity from the set of impurities that exist
     * in this Ingredient.
     * @param impurity the ChemicalImpurity to be removed
     * @see #getChemicalImpurities()
     * @see #setChemicalImpurities()
     * @see #addChemicalImpurity()
     */
    public void removeChemicalImpurity(ChemicalImpurity impurity){
        if (impurity instanceof Ingredient) { //Must be a ChemicalSubstance
            getChemicalSubstances().remove(impurity);
        } else { //Must be a ChemicalImpuritySubstance
            getChemicalImpuritySubstances().remove(impurity);
        }
    }

    /***************************************************************************
     * @param aDosageFormGroup
     */
    public Double getQtyLowerToleranceForDosageFormGroup(
                  DosageFormGroup aDosageFormGroup){
        TestSpecification testSpecification = 
                          dosageFormGroupTestSpecMap.get(aDosageFormGroup);

        if (testSpecification != null) {
            return testSpecification.getLowerTolerance();
        }
        return null;
    }

    /***************************************************************************
     * @param aDosageFormGroup
     */
    public Double getQtyUpperToleranceForDosageFormGroup(
                  DosageFormGroup aDosageFormGroup){
        TestSpecification testSpecification = 
                          dosageFormGroupTestSpecMap.get(aDosageFormGroup);

        if (testSpecification != null) {
            return testSpecification.getUpperTolerance();
        }
        return null;
    }

    /***************************************************************************
     * @param aDosageFormGroup
     * @param tolerance
     */
    public void setQtyLowerToleranceForDosageFormGroup(
                DosageFormGroup aDosageFormGroup, Double tolerance){
        TestSpecification testSpecification = 
                          dosageFormGroupTestSpecMap.get(aDosageFormGroup);

        if (testSpecification == null) {
            testSpecification = new TestSpecification();
            dosageFormGroupTestSpecMap.put(aDosageFormGroup,
                                           testSpecification);
        }
        testSpecification.setLowerTolerance(tolerance);
    }

    /***************************************************************************
     * @param aDosageFormGroup
     * @param tolerance
     */
    public void setQtyUpperToleranceForDosageFormGroup(
                DosageFormGroup aDosageFormGroup, Double tolerance){
        TestSpecification testSpecification = 
                          dosageFormGroupTestSpecMap.get(aDosageFormGroup);

        if (testSpecification == null) {
            testSpecification = new TestSpecification();
            dosageFormGroupTestSpecMap.put(aDosageFormGroup,
                                           testSpecification);
        }
        testSpecification.setUpperTolerance(tolerance);
    }

    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
        
        if (o == null) return -1; 
    	return StringComparator.compare(getAuthorizedName(),
    								    ((Ingredient)o).getAuthorizedName());
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
        buffer.append(", category: ");
        if (getCategory() != null) {
            buffer.append(getCategory().getName());
        } else {
            buffer.append("null");
        }
        if (getNhpClassifications() != null) {
            buffer.append(", nhpClassification: ");
            buffer.append(getNhpClassifications());
        }
        buffer.append(", source: ");
        if (getSource() != null) {
            buffer.append(getSource().getName());
        } else {
            buffer.append("null");
        }
        if (getDescriptionE() != null) {
            buffer.append(", descriptionE: ");
            buffer.append(getDescriptionE());
        }
        if (getDescriptionF() != null) {
            buffer.append(", descriptionF: ");
            buffer.append(getDescriptionF());
        }
        if (getCommentE() != null) {
            buffer.append(", commentE: ");
            buffer.append(getCommentE());
        }
        if (getCommentF() != null) {
            buffer.append(", commentF: ");
            buffer.append(getCommentF());
        }
        if (getCommonNames() != null && !getCommonNames().isEmpty()) {
            buffer.append(", commonNames: ");
            buffer.append(getCommonNames());
        }
        buffer.append(", chemicalImpurities: ");
        buffer.append(getChemicalImpurities());
        buffer.append(", microbiologicalImpurities: ");
        buffer.append(getMicrobiologicalImpurities());
        buffer.append(", quantityLowerTolerance: ");
        buffer.append(getQuantityLowerTolerance());
        buffer.append(", quantityUpperTolerance: ");
        buffer.append(getQuantityUpperTolerance());
        buffer.append(", dosageFormGroupTestSpecMap: ");
        buffer.append(getDosageFormGroupTestSpecMap());
        if (getRoles() != null && !getRoles().isEmpty()) {
            buffer.append(", roles: ");
            buffer.append(getRoles());
        }
        if (getSubIngredients() != null && !getSubIngredients().isEmpty()) {
            buffer.append(", subIngredients: ");
            buffer.append(getSubIngredients());
        }
        
        return buffer.toString();
    }
    
    /**
     * This function returns true when it encounters a Homeopathic Substance.
     *  
     * @return true when the ingredient is a Homeopathic Substance.
     */
    public boolean isHomeopathicSubstance() {
        if (getCategory() != null
                && IngredientCategory.HOMEOPATHIC_SUBSTANCE_CODE.equals(getCategory().getCode())) {
            return true;
        }
        return false;
    }
    
}