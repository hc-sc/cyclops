package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import ca.gc.hc.nhpd.util.StringUtils;

import java.util.Comparator;
import java.util.Set;

/*******************************************************************************
 * An object that represents the way a substance is to be administered (e.g.
 * orally, topically, etc.).
 */
public class RouteOfAdministration extends PersistentObject
implements ControlledVocabularyObject, Comparable {
    private String code;
    private String commentE;
    private String commentF;
    private String nameE;
    private String nameF;
    private Boolean noFixedLimits;
    private Set<NonMedicinalPurpose> nonMedicinalPurposes;
    private Set<DosageForm> possibleDosageForms;
    private Boolean sterileRequired;

    //JSTL needs this in JavaBeans syntax
	public boolean isHasControlledVocabularyDescription() {
		return true;
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
     * @see #setNameF()
     */
    public String getNameF(){
        return nameF;
    }

    /***************************************************************************
     * Gets whether this has no fixed limits. This would be the case if there
     * can't be too much or too little of a product administered (e.g. a sun
     * screen). These are not measured. Most active ingredients have a maximum,
     * so this is usually false.
     * 
     * @return a boolean - true if this has no fixed limits.
     *  
     * @see #setNoFixedLimits()
     */
    public Boolean getNoFixedLimits() {
        return noFixedLimits;
    }
    
    /***************************************************************************
     * Hibernate Implementation - Required for Database
     * 
     * Gets whether this has no fixed limits. This would be the case if there
     * can't be too much or too little of a product administered (e.g. a sun
     * screen). These are not measured. Most active ingredients have a maximum,
     * so this is usually false.
     * 
     * @return a boolean - true if this has no fixed limits.
     *  
     * @see #setNoFixedLimitsAsString()
     */
    public String getNoFixedLimitsAsString() {
        return StringUtils.booleanToString(noFixedLimits);
    }
    
    /***************************************************************************
     * Gets the NonMedicinalPurposes that use this route of administration.
     * @return the NonMedicinalPurposes that use this route of administration.
     * @see #setNonMedicinalPurposes()
     */
    public Set<NonMedicinalPurpose> getNonMedicinalPurposes(){
        return nonMedicinalPurposes;
    }
    
    /***************************************************************************
     * Gets the possible DosageForms for this route of administration.
     * @return the possible DosageForms for this route of administration.
     * @see #setPossibleDosageForms()
     */
    public Set<DosageForm> getPossibleDosageForms(){
        return possibleDosageForms;
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
     * Sets the commonly used unique code for this object.
     * @param newVal the commonly used unique code for this object.
     * @see #getCode()
     */
    public void setCode(String newVal){
        code = newVal;
    }

    /***************************************************************************
     * Sets the comment in English.
     * @param newVal the comment in English
     * @see #getCommentE()
     */
    public void setCommentE(String newVal){
        commentE = newVal;
    }

    /***************************************************************************
     * Gets the comment in French.
     * @param newVal the comment in French
     * @see #getCommentF()
     */
    public void setCommentF(String newVal){
        commentF = newVal;
    }

    /***************************************************************************
     * Gets the name in English.
     * @return the name in English.
     * @see #setNameE()
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
     * Gets whether this has no fixed limits. This would be the case if there
     * can't be too much or too little of a product administered (e.g. a sun
     * screen). These are not measured. Most active ingredients have a maximum,
     * so this is usually false.
     * 
     * @param noFixedLimits
     * 
     * @see #getNoFixedLimits()
     */
    public void setNoFixedLimits(Boolean noFixedLimits) {
        this.noFixedLimits = noFixedLimits;
    }

    /***************************************************************************
     * Hibernate Implementation - Required for Database
     * 
     * Gets whether this has no fixed limits. This would be the case if there
     * can't be too much or too little of a product administered (e.g. a sun
     * screen). These are not measured. Most active ingredients have a maximum,
     * so this is usually false.
     * 
     * @param noFixedLimits
     * 
     * @see #getNoFixedLimitsAsString()
     */
    public void setNoFixedLimitsAsString(String noFixedLimitsAsString) {
        this.noFixedLimits = StringUtils.stringToBoolean(noFixedLimitsAsString);
    }
    
    /***************************************************************************
     * Sets the NonMedicinalPurposes that use this route of administration.
     * @param newVal the NonMedicinalPurposes that use this route of administration.
     * @see #getNonMedicinalPurposes()
     */
    public void setNonMedicinalPurposes(Set<NonMedicinalPurpose> newVal){
        nonMedicinalPurposes = newVal;
    }

    /***************************************************************************
     * Sets the possible DosageForms for this route of administration.
     * @param newVal the possible DosageForms for this route of administration.
     * @see #getPossibleDosageForms()
     */
    public void setPossibleDosageForms(Set<DosageForm> newVal){
        possibleDosageForms = newVal;
    }
    
    
    /***************************************************************************
     * Returns the comment, which is used as the description for Controlled 
     * Vocabulary.  See ControlledVocabularyObject interface.     
     */
    public String getControlledVocabularyDescription() {
    	return getComment();
    }

    /***************************************************************************
     * Gets whether this route of administration requires a sterile ingredient.
     * Most don't care.
     * 
     * Example:  Ophthalmic is true, the rest is false.
     * 
     * @return true if this route of administration requires a sterile ingredient.
     */
    public Boolean getSterileRequired() {
        return sterileRequired;
    }

    /***************************************************************************
     * Sets whether this route of administration requires a sterile ingredient.
     * Most don't care.
     * 
     * @param sterialeRequired
     */
    public void setSterileRequired(Boolean sterileRequired) {
        this.sterileRequired = sterileRequired;
    }
    
    /***************************************************************************
     * Gets whether this route of administration requires a sterile ingredient.
     * Most don't care.  This is the hibernate implementation.
     * 
     * @return true if this route of administration requires a sterile ingredient.
     */
    public String getSterileRequiredAsString() {
        return StringUtils.booleanToString(sterileRequired);
    }
    
    /***************************************************************************
     * Sets whether this route of administration requires a sterile ingredient.
     * Most don't care.  This is the hibernate implementation.
     * 
     * @param sterialRequiredAsString
     */
    public void setSterileRequiredAsString(String sterileRequiredAsString) {
        this.sterileRequired = StringUtils.stringToBoolean(sterileRequiredAsString);
    }
    
    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
       
        if (o == null) return -1; 
    	return StringComparator.compare(getName(),
    			((RouteOfAdministration)o).getName());
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
        buffer.append(", commentE: ");
        buffer.append(getCommentE());
        buffer.append(", commentF: ");
        buffer.append(getCommentF());
        //buffer.append(", possibleDosageForms: ");
        //buffer.append(getPossibleDosageForms());
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
            if (o1 == null || ((RouteOfAdministration)o1).getNameF() == null) {
                return 1;
            }
            if (o2 == null) {
                return -1;
            }
            return ((RouteOfAdministration)o1).getNameF().compareTo(
                   ((RouteOfAdministration)o2).getNameF());
        }
    }
}