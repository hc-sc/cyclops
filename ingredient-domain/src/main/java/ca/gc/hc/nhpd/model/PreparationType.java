package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import ca.gc.hc.nhpd.util.StringUtils;
import java.util.Comparator;
import java.util.Set;

/*******************************************************************************
 * An object that repesents a method of preparation applied to a part of an
 * organism.
 */
public class PreparationType extends PersistentObject 
	implements ControlledVocabularyObject, Comparable {
    
    private static final String BOOLEAN_YES = "y";
    private static final String BOOLEAN_NO = "n";
    private static final String BOOLEAN_EMPTY = "e";
    
    private String code;
    private Boolean extract;
    private Set<Ingredient> ingredients;
    private String descriptionE;
    private String descriptionF;
    private String nameE;
    private String nameF;
    private String ratioType;
    private Set<Solvent> restrictedSolvents;
    private Boolean standardized;
    private boolean visible;

    //JSTL needs this in JavaBeans syntax
	public boolean isHasControlledVocabularyDescription() {
		return true;
	}

	/***************************************************************************
     * Returns the comment, which is used as the description for Controlled 
     * Vocabulary.  See ControlledVocabularyObject interface.     
     */
    public String getControlledVocabularyDescription() {
    	return getDescription();
    }
	
    /***************************************************************************
     * Gets the commonly used unique code for this object.
     * @return the commonly used unique code for this object.
     * @see #setCode()
     */
    public String getCode(){
        return code;
    }

    /***************************************************************************
     */
    public Set<Ingredient> getIngredients(){
        return ingredients;
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
     * Gets the extract associated with the Preparation Type.
     * @return the extract.
     * @see #setExtract() 
     */
    public Boolean getExtract() {
        return extract;
    }
    
    /***************************************************************************
     * Gets the extract associated with the Preparation Type.  Hibernate
     * implementation.
     * @return the extract as a String value. 
     * @see #setExtractAsString() 
     */
    public String getExtractAsString() {
        return StringUtils.booleanToString(extract);
    }
    
    /***************************************************************************
     * Gets the name in the language appropriate for the Locale.
     * @return the locale-specific name.
     */
    public String getName(){
        if (isLanguageFrench()) {
            return getNameF();
        }
        return getNameE();
    }

    /***************************************************************************
     * Gets the name in English.
     * @return the name in English.
     * @see #setNameE()
     */
    public String getNameE(){
        return nameE;
    }

    /***************************************************************************
     * Gets the name in French.
     * @return the name in French.
     * @see #setNameF
     */
    public String getNameF(){
        return nameF;
    }

    /***************************************************************************
     * Establishes the ratio type that will be used for this Preparation Type.
     * Possible ratio types include: 1:X, X:1 and 1:1.
     * @return ratio for this Preparation Type.
     * @see setRatioType()
     */
    public String getRatioType() {
        return ratioType;
    }
    
    /***************************************************************************
     * Returns a list of restricted solvents that should be used by this
     * preparation type.  When this field is empty, all solvents are permitted.
     * @return list of restricted solvents.
     * @see setRestrictedSolvents()
     */
    public Set<Solvent> getRestrictedSolvents() {
        return restrictedSolvents;
    }
    
	/***************************************************************************
	 * Gets the English short description used to represent this object in 
	 * drop down lists and other various interfaces. 
	 * @return the English specific short description.
	 */
	public String getShortDescriptionE() {
		return getNameE();
	}

	/***************************************************************************
	 * Gets the English short description used to represent this object in 
	 * drop down lists and other various interfaces. 
	 * @return the French specific short description.
	 */
	public String getShortDescriptionF() {
		return getNameF();
	}

    /***************************************************************************
     * Determins if this preparation type is Standardized.
     * @return display/require the standardized flag. 
     * form.
     * @see #isStandardized() 
     */
    public Boolean getStandardized() {
        return standardized;
    }

    /***************************************************************************
     * Determins if this preparation type is Standardized.  Hibernate
     * implementation.
     * @return display/require the standardized flag. 
     * @see #setStandardizedPresent() 
     */
    public String getStandardizedAsString() {
        return StringUtils.booleanToString(standardized);
    }
    
    /***************************************************************************
     * Flag that indicates if this Preparation Type should be visible in the
     * list of controlled vocabulary items.
     *  
     * @return visible flag for the list of controlled vocabulary items.
     */
    public boolean isVisible() {
        return visible;
    }

    
    /***************************************************************************
     * Sets the commonly used unique code for this object.
     * @param newVal the commonly used unique code for this object.
     * @see #getCode()
     */
    public void setCode(String newVal){
        code = newVal;
    }
    
    /***************************************************************************
     * @param newVal
     */
    public void setIngredients(Set<Ingredient> newVal){
        ingredients = newVal;
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
     * Sets the extract associated with this preparation type.
     * @param newVal the extract for this object.
     * @see #getExtract()
     */
    public void setExtract(Boolean newVal) {
        extract = newVal;
    }

    /***************************************************************************
     * Sets the extract associated with this preparation type.  Hibernate 
     * Implementation.
     * @param newVal the extract as a String.
     * @see #getExtractAsString()
     */
    public void setExtractAsString(String newVal) {
        extract = StringUtils.stringToBoolean(newVal);
    }      
    
    /***************************************************************************
     * Sets the name in English.
     * @param newVal the name in English
     * @see #getNameE()
     */
    public void setNameE(String newVal){
        nameE = newVal;
    }

    /***************************************************************************
     * Sets the name in French.
     * @param newVal the name in French
     * @see #getNameF()
     */
    public void setNameF(String newVal){
        nameF = newVal;
    }

    /***************************************************************************
     * Sets the ratio type for this object.  Allowable values are X:1, 1:X and 
     * 1:1.
     * @param newVal the ratio type for this object.
     * @see #getRatioType()
     */
    public void setRatioType(String newVal) {
        ratioType = newVal;
    }

    /***************************************************************************
     * Sets the set of restricted solvents for this object.  
     * @param newVal the set of restricted solvents.
     * @see #getRestrictedSolvents
     */
    public void setRestrictedSolvents(Set<Solvent> newVal) {
        restrictedSolvents = newVal;
    }    

    /***************************************************************************
     * Sets the standardized present flag for this object.
     * @param newVal the standardized present value for this object.
     * @see #isStandardized()
     */
    public void setStandardized(Boolean newVal) {
        standardized = newVal;
    }

    /***************************************************************************
     * Sets the standardized present flag for this object.  Hibernate 
     * Implementation.
     * @param newVal the standardized present value for this object.
     * @see #getStandardizedAsString()
     */
    public void setStandardizedAsString(String newVal) {
        standardized = StringUtils.stringToBoolean(newVal);
    }    
    
    /***************************************************************************
     * Flag that determins if this Perparation Type is going to be visible
     * in the controlled vocabulary list.
     * @param visible flag for the controlled vocabulary list.
     * @see #isVisible()
     */
    public void setVisible(boolean visible){
        this.visible = visible;
    }
    
    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
        
        if (o == null) return -1; 
    	return StringComparator.compare(getName(),((PreparationType)o).getName());
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
        buffer.append(", code: ");
        buffer.append(getCode());
        buffer.append(", nameE: ");
        buffer.append(getNameE());
        buffer.append(", nameF: ");
        buffer.append(getNameF());
        if (getDescriptionE() != null) {
            buffer.append(", descriptionE: ");
            buffer.append(getDescriptionE());
        }
        if (getDescriptionF() != null) {
            buffer.append(", descriptionF: ");
            buffer.append(getDescriptionF());
        }
        
        return buffer.toString();
    }
    
    //+++ Inner Classes ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /***************************************************************************
     * An implementation of Comparator that allows these objects to be sorted
     * using their French names.
     */
    public static class FrenchComparator implements Comparator {
        /***********************************************************************
         * Compares the passed objects for order. Returns a negative integer,
         * zero, or a positive integer as the first object is less than, equal
         * to, or greater than the second.
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         */
        public int compare(Object o1, Object o2) throws ClassCastException {
            if (o1 == null || ((PreparationType)o1).getNameF() == null) {
                return 1;
            }
            if (o2 == null) {
                return -1;
            }
            return ((PreparationType)o1).getNameF().compareTo(
                   ((PreparationType)o2).getNameF());
        }
    }
}