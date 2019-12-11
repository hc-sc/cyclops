package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*******************************************************************************
 * An object that repesents a natural health product classification assigned by
 * NHPD to ingredients used in Medicinal roles. Also referred to as a Schedule 1
 * classification.
 */
public class NhpClassification extends PersistentObject 
	implements ControlledVocabularyObject, Comparable {
    
    private static final Log LOG = LogFactory.getLog(NhpClassification.class);

    public static final String NHP_CLASSIFICATION_CODE_AA = "AA";
    public static final String NHP_CLASSIFICATION_CODE_ALGA = "ALGA";
    public static final String NHP_CLASSIFICATION_CODE_BACT = "BACT";
    public static final String NHP_CLASSIFICATION_CODE_ENZ = "ENZ";
    public static final String NHP_CLASSIFICATION_CODE_ESFA = "ESFA";
    public static final String NHP_CLASSIFICATION_CODE_EXT = "EXT";
    public static final String NHP_CLASSIFICATION_CODE_FUNG = "FUNG";
    public static final String NHP_CLASSIFICATION_CODE_ISO = "ISO";
    public static final String NHP_CLASSIFICATION_CODE_MIN = "MIN";
    public static final String NHP_CLASSIFICATION_CODE_NHANML = "NHANML";
    public static final String NHP_CLASSIFICATION_CODE_PLANT = "PLANT";
    public static final String NHP_CLASSIFICATION_CODE_PROB = "PROB";
    public static final String NHP_CLASSIFICATION_CODE_SD = "SD";
    public static final String NHP_CLASSIFICATION_CODE_VIT = "VIT";
    
    private Set<ChemicalImpuritySubstance> chemicalImpuritySubstances = 
    	new TreeSet<ChemicalImpuritySubstance>();
    private Set<ChemicalSubstance> chemicalSubstances = 
    	new TreeSet<ChemicalSubstance>();
    private String code;
    private String descriptionE;
    private String descriptionF;
    private Set<TestMethod> identityTestMethods;
    private boolean manufactured;
    private Set<MicrobiologicalImpurity> microbiologicalImpurities;
    private String nameE;
    private String nameF;
    private Double quantityLowerTolerance;
    private Set<TestMethod> quantityTestMethods;
    private QuantityToleranceModel quantityTestModel;
    private Double quantityUpperTolerance;

    
    //JSTL needs this in JavaBeans syntax
	public boolean isHasControlledVocabularyDescription() {
		return true;
	}

	public String getControlledVocabularyDescription() {
		return getDescription();
	}
    /***************************************************************************
     * Gets the ChemicalImpurities that have been identified with this
     * classification. Note that this set is immutable, since it is the
     * composite of entries from 2 other sets. Use the add, remove, and set
     * methods to make changes to it.
     * @return the ChemicalImpurities that have been identified with this
     *         classification
     * @see #addChemicalImpurity()
     * @see #removeChemicalImpurity()
     * @see #setChemicalImpurities()
     */
    public Set getChemicalImpurities(){
        Set chemicalImpurities = new TreeSet(getChemicalImpuritySubstances());
        
        chemicalImpurities.addAll(getChemicalSubstances());
        return Collections.unmodifiableSet(chemicalImpurities);
    }

    /***************************************************************************
     * Gets the ChemicalImpuritySubstances that have been identified with this
     * classification (these are impurities that are not otherwise stored
     * in the system as Ingredients). Normally chemical impurities are dealt
     * with in an abstract sense, however Hibernate requires the two different
     * types be dealt with separately since they are stored in different parts
     * of the persistent store. This method is typically only used by Hibernate.
     * @return the ChemicalImpuritySubstances that have been identified with
     *         this classification
     * @see #getChemicalImpurities()
     * @see #setChemicalImpuritySubstances()
     */
    public Set<ChemicalImpuritySubstance> getChemicalImpuritySubstances(){
        return chemicalImpuritySubstances;
    }

    /***************************************************************************
     * Gets the ChemicalSubstance impurities that have been identified with this
     * classification. Normally chemical impurities are dealt with in an
     * abstract sense, however Hibernate requires the two different types be
     * dealt with separately since they are stored in different parts of the
     * persistent store. This method is typically only used by Hibernate.
     * @return the ChemicalSubstance impurities that have been identified with
     *         this classification
     * @see #getChemicalImpurities()
     * @see #setChemicalSubstances()
     */
    public Set<ChemicalSubstance> getChemicalSubstances(){
        return chemicalSubstances;
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
     */
    public Set<TestMethod> getIdentityTestMethods(){
        return identityTestMethods;
    }

    /***************************************************************************
     */
    public Set getMicrobiologicalImpurities(){
        return microbiologicalImpurities;
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
     */
    public Double getQuantityLowerTolerance(){
        return quantityLowerTolerance;
    }

    /***************************************************************************
     */
    public Set getQuantityTestMethods(){
        return quantityTestMethods;
    }

    /***************************************************************************
     */
    public QuantityToleranceModel getQuantityTestModel(){
        return quantityTestModel;
    }

    /***************************************************************************
     */
    public Double getQuantityUpperTolerance(){
        return quantityUpperTolerance;
    }

    /***************************************************************************
     */
    public boolean isManufactured(){
        return manufactured;
    }

    /***************************************************************************
     * Sets the ChemicalImpurities that have been identified with this
     * classification.
     * @param newVal the ChemicalImpurities that have been identified with this
     *        classification
     * @see #getChemicalImpurities()
     * @see #addChemicalImpurity()
     * @see #removeChemicalImpurity()
     */
    public void setChemicalImpurities(Set newVal){
        Object impurity;
        
        getChemicalImpuritySubstances().clear();
        getChemicalSubstances().clear();
        if (newVal != null) {
            for (Iterator it=newVal.iterator(); it.hasNext(); ) {
                impurity = it.next();
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
     * Sets the ChemicalImpuritySubstances that have been identified with this
     * classification (these are impurities that are not otherwise stored
     * in the system as Ingredients). Normally chemical impurities are dealt
     * with in an abstract sense, however Hibernate requires the two different
     * types be dealt with separately since they are stored in different parts
     * of the persistent store. This method is typically only used by Hibernate.
     * @param newVal the ChemicalImpuritySubstances that have been identified
     *               with this classification
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
     * Sets the ChemicalSubstance impurities that have been identified with this
     * classification. Normally chemical impurities are dealt with in an
     * abstract sense, however Hibernate requires the two different types be
     * dealt with separately since they are stored in different parts of the
     * persistent store. This method is typically only used by Hibernate.
     * @param newVal the ChemicalSubstance impurities that have been identified
     *               with this classification
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
     * @param newVal
     */
    public void setIdentityTestMethods(Set<TestMethod> newVal){
        identityTestMethods = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setManufactured(boolean newVal){
        manufactured = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setMicrobiologicalImpurities(Set<MicrobiologicalImpurity> newVal){
        microbiologicalImpurities = newVal;
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
     * @param newVal
     */
    public void setQuantityLowerTolerance(Double newVal){
        quantityLowerTolerance = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setQuantityTestMethods(Set newVal){
        quantityTestMethods = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setQuantityTestModel(QuantityToleranceModel newVal){
        quantityTestModel = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setQuantityUpperTolerance(Double newVal){
        quantityUpperTolerance = newVal;
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
     * Gets the French short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the French specific short description.
     */
    public String getShortDescriptionF() {
        return getNameF();
    }
    
    /***************************************************************************
     * Adds the passed ChemicalImpurity to the set of impurities that have been
     * identified with this classification.
     * @param impurity the ChemicalImpurity to be added
     * @see #getChemicalImpurities()
     * @see #setChemicalImpurities()
     * @see #removeChemicalImpurity()
     */
    public void addChemicalImpurity(ChemicalImpurity impurity){
        if (impurity instanceof ChemicalSubstance) { //Must be a ChemicalSubstance
            getChemicalSubstances().add((ChemicalSubstance)impurity);
        } else { //Must be a ChemicalImpuritySubstance
            getChemicalImpuritySubstances().add((ChemicalImpuritySubstance)impurity);
        }
    }

    /***************************************************************************
     * Removes the passed ChemicalImpurity from the set of impurities that have
     * been identified with this classification.
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
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param o the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {
       
        if (o == null) return -1; 
    	return StringComparator.compare(getName(),((NhpClassification)o).getName());
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
        buffer.append(", chemicalImpurities: ");
        buffer.append(getChemicalImpurities());
        buffer.append(", manufactured: ");
        buffer.append(isManufactured());
        buffer.append(", microbiologicalImpurities: ");
        buffer.append(getMicrobiologicalImpurities());
        buffer.append(", quantityLowerTolerance: ");
        buffer.append(getQuantityLowerTolerance());
        buffer.append(", quantityUpperTolerance: ");
        buffer.append(getQuantityUpperTolerance());
        buffer.append(", quantityTestModel: ");
        buffer.append(getQuantityTestModel());
        buffer.append(", quantityTestMethods: ");
        buffer.append(getQuantityTestMethods());
        buffer.append(", identityTestMethods: ");
        buffer.append(getIdentityTestMethods());
        
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
            if (o1 == null || ((NhpClassification)o1).getNameF() == null) {
                return 1;
            }
            if (o2 == null) {
                return -1;
            }
            return ((NhpClassification)o1).getNameF().compareTo(
                   ((NhpClassification)o2).getNameF());
        }
    }
}