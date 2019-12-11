package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Set;

/*******************************************************************************
 * An object that represents the sub-ingredient component of a larger (composite)
 * item / compound. These are ChemicalSubstances.
 */
public class SubIngredient extends PersistentObject implements Comparable {
	private String commentE;
	private String commentF;
    private Set<DosageForm> dosageForms;
	private Ingredient ingredient;		//The ChemicalSubstance which is the subIngredient
	private Double lowerYield;
	private boolean mandatory;
    private Set<Reference> references;
	private Set<RouteOfAdministration> routesOfAdministration;
	private Units units;
	private Double upperYield;
   /** 
    * The ChemicalSubstance which contains this SubIngredient.  
	* Example = Calcium Carbonate (Chemical Substance) contains Calcium (SubIngredient) 
	*/
	private Ingredient parentIngredient;	
   /**
    * The OrganismPart which contains this SubIngredient.  
	* Example = Oyster Shell contains Calcium (SubIngredient) 
	*/
	private OrganismPart parentOrganismPart;

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
     */
	public Set<DosageForm> getDosageForms(){
		return dosageForms;
	}

    /***************************************************************************
	 * Gets the sub-ingredient this is describing, a ChemicalSubstance.
	 */
	public Ingredient getIngredient(){
		return ingredient;
	}

    /***************************************************************************
     * Builds a composite name based on the ingredient name and parent ingredient
     * or parent organism part.
     * @return String representing this SubIngredient name.
     */
    
    public String getName() {
        StringBuffer sb = new StringBuffer();
        if ( getIngredient() == null ||
             getIngredient().getAuthorizedName() == null ) {
            sb.append( "Ingredient Name Not Available" ); 
        } else {
            sb.append( getIngredient().getAuthorizedName() );
        }
        sb.append( " - ");
        if ( isIngredientSource() ) {
            if ( getParentIngredient() == null || 
                 getParentIngredient().getAuthorizedName() == null ) {
                sb.append( "Parent Ingredient Name Not Available");
            } else {
                sb.append( getParentIngredient().getAuthorizedName() );
            }
        }
        if ( isOrganismPartSource() ) {
            if ( getParentOrganismPart() == null ||
                 getParentOrganismPart().getName() == null ) {
                sb.append( "Parent Organism Part Sources Not Available" );
            } else {
                sb.append( getParentOrganismPart().getName() );
            }
        }
        return sb.toString();
    }

    /***************************************************************************
	 * Gets the 'parent' Ingredient if this SubIngredient is related to a
	 * ChemicalSubstance.  Null if this is related to an OrganismPart.
	 */
	public Ingredient getParentIngredient(){
		return parentIngredient;
	}

    /***************************************************************************
	 * Gets the 'parent' OrganismPart this is related to. Null if this is
     * related to an OrganismPart.
	 */
	public OrganismPart getParentOrganismPart(){
		return parentOrganismPart;
	}

    /***************************************************************************
     */
	public Double getLowerYield(){
		return lowerYield;
	}

    /***************************************************************************
     * Gets the references that were used to determine the specifics of this
     * SubIngredient. These are only populated for Organism (plant) part
     * SubIngredients.
     */
    public Set<Reference> getReferences(){
        return references;
    }

    /***************************************************************************
     */
	public Set<RouteOfAdministration> getRoutesOfAdministration(){
		return routesOfAdministration;
	}

    /***************************************************************************
     */
	public Units getUnits(){
		return units;
	}

    /***************************************************************************
     */
	public Double getUpperYield(){
		return upperYield;
	}

    /***************************************************************************
     * Gets whether this sub-ingredient is present in a sufficient volume as to
     * require it to be claimed.
     */
    public boolean isMandatory(){
        return mandatory;
    }

	/***************************************************************************
     * Gets wheter this sub-ingredient has a Parent Ingredient Source.
     * 
     */ 
	public boolean isIngredientSource(){   
		if ( getParentIngredient() != null ) {
			return true; 
		}
		return false;
	}

	/***************************************************************************
	 * Gets whether this sub-ingredient has an Organism Part Source.
	 * 
	 */ 
	public boolean isOrganismPartSource(){   
		if ( getParentOrganismPart() != null ) {
			return true; 
		}
		return false;
	}

    /***************************************************************************
	 * Gets whether this sub-ingredient has a parent that is an Ingredient or an
     * OrganismPart.
     * 
     * Note:  This function was disabled because it negatively impacted the 
     * getParentIngredient function, used to display the ParentIngredient name.
     * 
	 * @return true if this sub-ingredient has a parent that is an Ingredient.
	 */
    /*
	public boolean isParentIngredient(){
		return getIngredient() != null;
	}
    */

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
	public void setDosageForms(Set<DosageForm> newVal){
		dosageForms = newVal;
	}

    /***************************************************************************
	 * Sets the sub-ingredient this is describing, an herbal_component or
	 * chemical_substance.
	 */
	public void setIngredient(Ingredient newVal){
		ingredient = newVal;
	}

    /***************************************************************************
	 * Sets the 'parent' Ingredient this is related to, a
     * ChemicalSubstance. Null if this is related to an OrganismPart.
	 */
	public void setParentIngredient(Ingredient newVal){
		parentIngredient = newVal;
	}

    /***************************************************************************
	 * Sets the 'parent' OrganismPart this is related to. Null if this is
     * related to an OrganismPart.
	 */
	public void setParentOrganismPart(OrganismPart newVal){
		parentOrganismPart = newVal;
	}
	
    /***************************************************************************
	 * @param newVal
	 */
	public void setLowerYield(Double newVal){
		lowerYield = newVal;
	}

    /***************************************************************************
	 * Sets whether this sub-ingredient is present in a sufficient volume as to
	 * require it to be claimed.
	 */
	public void setMandatory(boolean newVal){
		mandatory = newVal;
	}

    /***************************************************************************
     * Sets the references that were used to determine the specifics of this
     * SubIngredient. These are only populated for Organism (plant) part
     * SubIngredients.
     * @param newVal
     */
    public void setReferences(Set<Reference> newVal){
        references = newVal;
    }

    /***************************************************************************
	 * @param newVal
	 */
	public void setRoutesOfAdministration(Set<RouteOfAdministration> newVal){
		routesOfAdministration = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setUnits(Units newVal){
		units = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setUpperYield(Double newVal){
		upperYield = newVal;
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
    
    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
        
        if (o == null) return -1; 
    	return StringComparator.compare(getName(),((SubIngredient)o).getName());
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
        buffer.append(", mandatory: ");
        buffer.append(isMandatory());
        buffer.append(", lowerYield: ");
        buffer.append(getLowerYield());
        buffer.append(", upperYield: ");
        buffer.append(getUpperYield());
        buffer.append(", units: ");
        buffer.append(getUnits());
        buffer.append(", ingredient: ");
        if (getIngredient() == null || getIngredient().getAuthorizedName() == null) {
            buffer.append("null");
        } else {
            buffer.append(getIngredient().getAuthorizedName());
        }
        if (getCommentE() != null) {
            buffer.append(", commentE: ");
            buffer.append(getCommentE());
        }
        if (getCommentF() != null) {
            buffer.append(", commentF: ");
            buffer.append(getCommentF());
        }
        if (getRoutesOfAdministration() != null 
            && !getRoutesOfAdministration().isEmpty()) {
            buffer.append(", routesOfAdministration: ");
            buffer.append(getRoutesOfAdministration());
        }
        if (getDosageForms() != null && !getDosageForms().isEmpty()) {
            buffer.append(", dosageForms: ");
            buffer.append(getDosageForms());
        }
        buffer.append(", references: ");
        buffer.append(getReferences());
        
        return buffer.toString();
    }
    
}