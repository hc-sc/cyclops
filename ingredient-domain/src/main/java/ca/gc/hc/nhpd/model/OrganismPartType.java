package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/*******************************************************************************
 * An object that represents the type of a specific part of an organism (e.g. a
 * stem).
 * @version 1.0
 */
public class OrganismPartType extends PersistentObject
	implements ControlledVocabularyObject, MultiNamedObject, Comparable {
    
    private String code;
    private String descriptionE;
    private String descriptionF;
    private String nameE;
    private String nameF;
    private Set<Synonym> synonyms;
    private Set<OrganismTypeGroup> typeGroups;

    //JSTL needs this in JavaBeans syntax
	public boolean isHasControlledVocabularyDescription() {
		//no descriptions currently populated in DB
		return false;
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
     * Returns the synonyms, with blank or null synonyms filtered out.
     * NB. the original "getSynonyms" method must be retained because Hibernate 
     * does equality checks for collections by object identity, not object values.
     *  
     * @return Set the commonly used unique code for this object.
     * @see #getSynonyms()
     */
    public Set<Synonym> getDisplaySynonyms() {
        
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
     * Gets the collection of synonyms associated with object.
     * @return the collection of synonyms associated with object.
     */
    public Set<Synonym> getSynonyms() {
        return synonyms;
    }

    /***************************************************************************
     * Gets the commonly used unique code for this object.
     * @return the commonly used unique code for this object.
     * @see #getCommonNames()
     */
    public Set<OrganismTypeGroup> getTypeGroups(){
        return typeGroups;
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
     * Sets the commonly used unique code for this object.
     * @param newVal the commonly used unique code for this object.
     * @see #getSynonyms()
     */
    public void setSynonyms(Set<Synonym> newVal){
    	synonyms = newVal;
    }

    /***************************************************************************
     * Sets the commonly used unique code for this object.
     * @param newVal the commonly used unique code for this object.
     * @see #getTypeGroups()
     */
    public void setTypeGroups(Set<OrganismTypeGroup> newVal){
        typeGroups = newVal;
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
    									((OrganismPartType)o).getName());
        
        
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
        buffer.append(", descriptionE: ");
        buffer.append(getDescriptionE());
        buffer.append(", descriptionF: ");
        buffer.append(getDescriptionF());
        buffer.append(", synonyms: ");
        buffer.append(getSynonyms());
        
        return buffer.toString();
    }
}