package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import ca.gc.hc.nhpd.util.StringUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/*******************************************************************************
 * An object that represents an accepted non-medicinal purpose.
 * @version 1.0
 */
public class NonMedicinalPurpose extends PersistentObject
	implements ControlledVocabularyObject, MultiNamedObject, Comparable {
    private Map<TestCategory, TestSpecification> categoryTestSpecMap = new HashMap<TestCategory, TestSpecification>();
    private Boolean artificialOrNatural;
    private String code;
    private String commentE;
    private String commentF;
    private String nameE;
    private String nameF;
    private Set<RouteOfAdministration> routesOfAdministration;
    private Set<Synonym> synonyms;
    private Integer upperLimitPercentage;
    
    //JSTL needs this in JavaBeans syntax
	public boolean isHasControlledVocabularyDescription() {
		return true;
	}
	
	public String getControlledVocabularyDescription() {
		return getDescription();
	}
	
    /***************************************************************************
     * Used by Hibernate. Use getToleranceTestMethodsForCategory(),
     * getToleranceUnitsForCategory and getUpperToleranceForCategory() instead.
     */
    public Map getCategoryTestSpecMap(){
        return categoryTestSpecMap;
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
     * Gets the ways the related ingredient can be administered for this purpose.
     * @return the ways the related ingredient can be administered for this
     *         purpose.
     * @see #getRoutesOfAdministration()
     */
    public Set<RouteOfAdministration> getRoutesOfAdministration(){
        return routesOfAdministration;
    }
    
    /***************************************************************************
     * Gets whether this purpose makes a distinction as to whether the ingredient
     * is artificial or natural.  Most don't care.
     * 
     * Example:  Flavour Enhancer is true, the rest is false.
     * 
     * @return true if the fact that the ingredient is artificial or natural
     *         matters.
     */
    public Boolean getArtificalOrNatural() {
        return artificialOrNatural;
    }

    /***************************************************************************
     * Sets whether this purpose makes a distinction as to whether the ingredient
     * is artificial or natural.  Most don't care.
     * 
     * @param artificialOrNatural
     */
    public void setArtificialOrNatural(Boolean artificialOrNatural) {
        this.artificialOrNatural = artificialOrNatural;
    }
    
    /***************************************************************************
     * Gets whether this purpose makes a distinction as to whether the ingredient
     * is artificial or natural.  Most don't care.  Hibernate Implementation.
     * 
     * @return true if the fact that the ingredient is artificial or natural
     *         matters.
     */
    public String getArtificialOrNaturalAsString() {
        return StringUtils.booleanToString(artificialOrNatural);
    }

    /***************************************************************************
     * Sets whether this purpose makes a distinction as to whether the ingredient
     * is artificial or natural.  Most don't care.
     * 
     * @param artificialOrNaturalAsString
     */
    public void setArtificialOrNaturalAsString(String artificialOrNaturalAsString) {
        this.artificialOrNatural = StringUtils.stringToBoolean(artificialOrNaturalAsString);
    }
    
    /***************************************************************************
     * Gets the commonly used unique code for this object.
     * @return the commonly used unique code for this object.
     * @see #getSynonyms()
     */
    public Set<Synonym> getDisplaySynonyms(){
    	//return all non-null or blank items in the collection.
		
    	TreeSet<Synonym> retSynonyms = new TreeSet<Synonym>();
		
    	if (synonyms != null) {
			Iterator iter = synonyms.iterator();
			while (iter.hasNext()) {
			    Synonym synonym = (Synonym)iter.next();
			    if (synonym.getName() != null && 
			        synonym.getName().trim() != "") {
			    	retSynonyms.add(synonym);
			    }
			}
		}
		
		return retSynonyms;
    }
    
    /***************************************************************************
     * Gets the synonyms associated with this object.
     * @return the synonyms associated with this object.
     * @see #getSynonyms()
     */
    public Set<Synonym> getSynonyms(){
    	return synonyms;
    }
    	
    /***************************************************************************
     * Gets a collection of the TestCategories that have tolerance information
     * for this DosageFormGroup.
     */
    public Set getTestCategoriesWithToleranceInfo(){
        return categoryTestSpecMap.keySet();
    }

    /***************************************************************************
     */
    public Integer getUpperLimitPercentage(){
        return upperLimitPercentage;
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
     * Used by Hibernate. Use setToleranceTestMethodsForCategory(),
     * setToleranceUnitsForCategory and setUpperToleranceForCategory() instead.
     */
    public void setCategoryTestSpecMap(Map<TestCategory, TestSpecification> aMap){
        if (aMap != null) {
            categoryTestSpecMap = aMap;
        } else {
            categoryTestSpecMap.clear();
        }
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
     * Sets the ways the related ingredient can be administered for this purpose.
     * @param newVal the ways the related ingredient can be administered for
     *        this purpose.
     * @see #setRoutesOfAdministration()
     */
    public void setRoutesOfAdministration(Set<RouteOfAdministration> newVal){
        routesOfAdministration = newVal;
    }

    /***************************************************************************
     * Sets the commonly used unique code for this object.
     * @param newVal the commonly used unique code for this object.
     * @see #setSynonyms()
     */
    public void setSynonyms(Set<Synonym> newVal){
        synonyms = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setUpperLimitPercentage(Integer newVal){
        upperLimitPercentage = newVal;
    }

    /***************************************************************************
     * @param aTestCategory
     */
    public Set getToleranceTestMethodsForCategory(TestCategory aTestCategory){
        TestSpecification testSpecification = 
            (TestSpecification)categoryTestSpecMap.get(aTestCategory);

        if (testSpecification != null) {
            return testSpecification.getToleranceTestMethods();
        }
        return null;
    }

    /***************************************************************************
     * @param aTestCategory
     * @param testMethods
     */
    public void setToleranceTestMethodsForCategory(TestCategory aTestCategory,
                                                   Set<TestMethod> testMethods){
        TestSpecification testSpecification = 
            (TestSpecification)categoryTestSpecMap.get(aTestCategory);

        if (testSpecification == null) {
            testSpecification = new TestSpecification();
            categoryTestSpecMap.put(aTestCategory, testSpecification);
        }
        testSpecification.setToleranceTestMethods(testMethods);
    }

    /***************************************************************************
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
       
        if (o == null) return -1; 
    	return StringComparator.compare(getName(),((NonMedicinalPurpose)o).getName());
    
    }

    public String getDescription()
    {
    	return getComment();
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
        if (getCommentE() != null) {
            buffer.append(", commentE: ");
            buffer.append(getCommentE());
        }
        if (getCommentF() != null) {
            buffer.append(", commentF: ");
            buffer.append(getCommentF());
        }
        buffer.append(", categoryTestSpecMap: ");
        buffer.append(getCategoryTestSpecMap());
        buffer.append(", routesOfAdministration: ");
        buffer.append(getRoutesOfAdministration());
        buffer.append(", synonyms: ");
        buffer.append(getSynonyms());
        buffer.append(", upperLimitPercentage: ");
        buffer.append(getUpperLimitPercentage());
        
        return buffer.toString();
    }
}