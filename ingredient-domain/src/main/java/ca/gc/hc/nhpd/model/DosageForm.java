package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import ca.gc.hc.nhpd.util.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/*******************************************************************************
 * An object that represents the form the ingredient has when administered (e.g.
 * pill, liquid, etc.).
 * 
 * Germain - Added reference to the Dosage Form Units on 2011/02/09
 */
public class DosageForm extends PersistentObject 
	implements ControlledVocabularyObject, MultiNamedObject, Comparable {

    //~ Static fields/initializers ---------------------------------------------
    static public int ASSESSMENT_REQUIRED = 1;
    static public int ASSESSMENT_NOT_REQUIRED = 2;
    static public int ALL = 3;
    static public String YES = "Y";
    static public String NO = "N";
    
    //~ Instance fields --------------------------------------------------------
    private Set<RouteOfAdministration> acceptableRoutesOfAdmin;
    private Boolean allowIngredientUnits;
    private int assessmentRequiredFlag;
    private String assessmentRequiredString;
    private Map<TestCategory, TestSpecification> categoryTestSpecMap = new HashMap<TestCategory, TestSpecification>();
    private String code;
    private String descriptionE;
    private String descriptionF;
    private Set<DosageUnit> dosageUnits;
    private Set<DosageFormGroup> groups;
    private String nameE;
    private String nameF;
    private Set<Synonym> synonyms;

    //JSTL needs this in JavaBeans syntax
	public boolean isHasControlledVocabularyDescription() {
		return true;
	}

	public String getControlledVocabularyDescription() {
		return getDescription();
	}
	
    //~ public -----------------------------------------------------------------

    /**
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param   o  the object to compare this to.
     *
     * @return  int value based on compare result
     *
     * @throws  ClassCastException  thrown if supplied object of wrong class.
     */
    
    public int compareTo(Object o) throws ClassCastException {
    	
        if (o == null) return -1;
        
        return StringComparator.compare(getName(),((DosageForm) o).getName());
        
    }    
    
    /**
     * Gets whether it is allowable to use the same Units as was used to
     * indicate the amount of an Ingredient present in a Product to express the
     * amount of this DosageForm.
     * 
     * @return a boolean that indicates whether the Ingredient amount Units can
     *         be used to express the amount of this DosageForm.
     *         
     * @see    #setAllowIngredientUnit()
     */
    public Boolean isAllowIngredientUnits() {
        return allowIngredientUnits;
    }

    /**
     * Getter used by Hibernate that returns a boolean flag indicating if 
     * ingredient units can be used when using this Dosage Form.
     *
     * @return  String flag indicating if ingredient units can be used when
     *          using this Dosage Form.
     */
    public String getAllowIngredientUnitsAsString() {
        return StringUtils.booleanToString(allowIngredientUnits);
    }
    
    /**
     * Gets the acceptable routes of administration for this dosage form.
     *
     * @return  the acceptable routes of administration for this dosage form.
     *
     * @see     #setAcceptableRoutesOfAdmin()
     */
    public Set<RouteOfAdministration> getAcceptableRoutesOfAdmin() {
        return acceptableRoutesOfAdmin;
    }

    /**
     * For Hibernate Use Only! Getter for the AssessmentRequiredString flag.
     *
     * @return  AssessmentRequired as a Y/N string.
     */
    public String getAssessmentRequiredString() {
        return assessmentRequiredString;
    }

    /**
     * Used by Hibernate. Use getToleranceTestMethodsForCategory(),
     * getToleranceUnitsForCategory and getUpperToleranceForCategory() instead.
     *
     * @return  the categoryTestSpecMap
     */
    public Map getCategoryTestSpecMap() {
        return categoryTestSpecMap;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets the description in the language appropriate for the Locale.
     *
     * @return  the locale-specific description.
     */
    public String getDescription() {

        if (isLanguageFrench()) {
            return getDescriptionF();
        }

        return getDescriptionE();
    }

    /**
     * Gets the name in English.
     *
     * @return  the name in English.
     *
     * @see     #setNameE()
     */
    public String getDescriptionE() {
        return descriptionE;
    }

    /**
     * Gets the name in French.
     *
     * @return  the name in French.
     *
     * @see     #setNameF()
     */
    public String getDescriptionF() {
        return descriptionF;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Set<DosageFormGroup> getGroups() {
        return groups;
    }

    /**
     * Gets the Dosage Units associated with this Dosage Form object. 
     * Dosage form units are non-standardized units that describe a 
     * quantity.  Example:  1/2 teaspoon, cap full, gel, paste, etc.
     *
     * @return  dosageUnits associated with this Dosage Form.
     */
    public Set<DosageUnit> getDosageUnits() {
        return dosageUnits;
    }


    /**
     * Gets the name in the language appropriate for the Locale.
     *
     * @return  the locale-specific name.
     */
    public String getName() {

        if (isLanguageFrench()) {
            return getNameF();
        }

        return getNameE();
    }

    /**
     * Gets the name in English.
     *
     * @return  the name in English.
     *
     * @see     #setNameE()
     */
    public String getNameE() {
        return nameE;
    }

    /**
     * Gets the name in French.
     *
     * @return  the name in French.
     *
     * @see     #setNameF()
     */
    public String getNameF() {
        return nameF;
    }

    /**
     * Gets the English short description used to represent this object in drop
     * down lists and other various interfaces.
     *
     * @return  the English specific short description.
     */
    public String getShortDescriptionE() {
        return getNameE();
    }

    /**
     * Gets the English short description used to represent this object in drop
     * down lists and other various interfaces.
     *
     * @return  the French specific short description.
     */
    public String getShortDescriptionF() {
        return getNameF();
    }

    /**
     * Gets a collection of the TestCategories that have tolerance information
     * for this DosageForm.
     *
     * @return  DOCUMENT ME!
     */
    public Set getTestCategoriesWithToleranceInfo() {
        return categoryTestSpecMap.keySet();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   aTestCategory
     *
     * @return  DOCUMENT ME!
     */
    public Set getToleranceTestMethodsForCategory(TestCategory aTestCategory) {
        TestSpecification testSpecification = (TestSpecification)
            categoryTestSpecMap.get(aTestCategory);

        if (testSpecification != null) {
            return testSpecification.getToleranceTestMethods();
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   aTestCategory
     *
     * @return  DOCUMENT ME!
     */
    public Units getToleranceUnitsForCategory(TestCategory aTestCategory) {
        TestSpecification testSpecification = (TestSpecification)
            categoryTestSpecMap.get(aTestCategory);

        if (testSpecification != null) {
            return testSpecification.getToleranceUnits();
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   aTestCategory
     *
     * @return  DOCUMENT ME!
     */
    public Double getUpperToleranceForCategory(TestCategory aTestCategory) {
        TestSpecification testSpecification = (TestSpecification)
            categoryTestSpecMap.get(aTestCategory);

        if (testSpecification != null) {
            return testSpecification.getUpperTolerance();
        }

        return null;
    }


    /**
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Over-ridden to add additional values to the
     * end.
     *
     * @return  DOCUMENT ME!
     */
    public String getValuesAsString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(super.getValuesAsString());
        buffer.append(", allowIngredientUnits: ");
        buffer.append(isAllowIngredientUnits());
        buffer.append(", code: ");
        buffer.append(getCode());
        buffer.append(", group: ");

        if (getGroups() != null) {
            for (DosageFormGroup dosageFormGroup : getGroups()) {
                buffer.append(dosageFormGroup.getName());
                buffer.append(" ");
            }
        } else {
            buffer.append("null");
        }

        buffer.append(", nameE: ");
        buffer.append(getNameE());
        buffer.append(", nameF: ");
        buffer.append(getNameF());
        buffer.append(", descriptionE: ");
        buffer.append(getDescriptionE());
        buffer.append(", descriptionF: ");
        buffer.append(getDescriptionF());
        buffer.append(", acceptableRoutesOfAdmin: ");
        buffer.append(getAcceptableRoutesOfAdmin());
        buffer.append(", categoryTestSpecMap: ");
        buffer.append(getCategoryTestSpecMap());

        return buffer.toString();
    }

    /**
     * Indicates if an assessment is required. Dosage forms that require
     * assessments are not allowed in Compendial Submissions because they
     * require an additional level of verification. For example a Pill dosage
     * form does not require additional assessment, however a Time Release Pill
     * does.
     *
     * @return  true/false depending assessment requirement.
     *
     * @see     setAssessmentRequired(), getAssessmentRequiredString(),
     *          setAssessmentRequiredString()
     */
    public boolean isAssessmentRequired() {

        if (assessmentRequiredFlag == ASSESSMENT_REQUIRED) {
            return true;
        }

        return false;
    }

    /**
     * Sets the flag indicating that we allow the selection of ingredient
     * units with this dosage form.
     * 
     * @param allowIngredientUnit to be used with this Dosage Form
     * 
     * @see   isAllowIngredientUnit();
     */
    public void setAllowIngredientUnits(Boolean allowIngredientUnits) {
        this.allowIngredientUnits = allowIngredientUnits;
    }

    /**
     * Setter used by Hibernate for the string flag indicating that this 
     * dosage form can also use the Ingredient units.
     *
     * @param  allowIngredientUnit
     */
    public void setAllowIngredientUnitsAsString(String allowIngredientUnits) {
        this.allowIngredientUnits = StringUtils.stringToBoolean(allowIngredientUnits);
    }
    
    /**
     * Sets the acceptable routes of administration for this dosage form.
     *
     * @param  newVal  DOCUMENT ME!
     *
     * @see    #getAcceptableRoutesOfAdmin()
     */
    public void setAcceptableRoutesOfAdmin(Set<RouteOfAdministration> newVal) {
        acceptableRoutesOfAdmin = newVal;
    }

    /**
     * Setter for the AssessmentRequired flag.
     *
     * @param  value  use the ASSESSMENT_REQUIRED or ASSESSMENT_NOT_REQUIRED
     *                values.
     *
     * @see    isAssessmentRequired(), getAssessmentRequiredString(),
     *         setAssessmentRequiredString()
     */
    public void setAssessmentRequired(int value) {

        if (value == ASSESSMENT_REQUIRED) {
            assessmentRequiredFlag = ASSESSMENT_REQUIRED;
            assessmentRequiredString = YES;
        }

        // Default to not required for all other values.
        assessmentRequiredFlag = ASSESSMENT_NOT_REQUIRED;
        assessmentRequiredString = NO;
    }

    /**
     * For Hibernate Use Only! Setter for the AssessmentRequiredString flag.
     *
     * @param  value  AssessmentRequired as a Y/N string. Defaults to N for any
     *                inappropriate values.
     *
     * @see    isAssessmentRequired(), setAssessmentRequired(),
     *         getAssessmentRequiredString()
     */
    public void setAssessmentRequiredString(String value) {
		assessmentRequiredString = value;
        if (value != null 
        	&& value.toUpperCase().equals(YES)) {
            assessmentRequiredFlag = ASSESSMENT_REQUIRED;
        } else {
            assessmentRequiredFlag = ASSESSMENT_NOT_REQUIRED;
            assessmentRequiredString = NO;
        }
    }

    /**
     * Used by Hibernate. Use setToleranceTestMethodsForCategory(),
     * setToleranceUnitsForCategory and setUpperToleranceForCategory() instead.
     *
     * @param  aMap  DOCUMENT ME!
     */
    public void setCategoryTestSpecMap(Map<TestCategory, TestSpecification> aMap) {

        if (aMap != null) {
            categoryTestSpecMap = aMap;
        } else {
            categoryTestSpecMap.clear();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  newVal
     */
    public void setCode(String newVal) {
        code = newVal;
    }

    /**
     * Sets the name in English.
     *
     * @param  newVal  the name in English
     *
     * @see    #getNameE()
     */
    public void setDescriptionE(String newVal) {
        descriptionE = newVal;
    }

    /**
     * Sets the name in French.
     *
     * @param  newVal  the name in French
     *
     * @see    #getNameF()
     */
    public void setDescriptionF(String newVal) {
        descriptionF = newVal;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  newVal
     */
    public void setGroups(Set<DosageFormGroup> newVal) {
        groups = newVal;
    }

    /**
     * Setter for the Dosage Units.
     *
     * @param  newVal set of DosageUnits
     */
    public void setDosageUnits(Set<DosageUnit> newVal) {
        dosageUnits = newVal;
    }

    /**
     * Sets the name in English.
     *
     * @param  newVal  the name in English
     *
     * @see    #getNameE()
     */
    public void setNameE(String newVal) {
        nameE = newVal;
    }

    /**
     * Sets the name in French.
     *
     * @param  newVal  the name in French
     *
     * @see    #getNameF()
     */
    public void setNameF(String newVal) {
        nameF = newVal;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  aTestCategory
     * @param  testMethods
     */
    public void setToleranceTestMethodsForCategory(TestCategory aTestCategory,
        Set<TestMethod> testMethods) {
        TestSpecification testSpecification = (TestSpecification)
            categoryTestSpecMap.get(aTestCategory);

        if (testSpecification == null) {
            testSpecification = new TestSpecification();
            categoryTestSpecMap.put(aTestCategory, testSpecification);
        }

        testSpecification.setToleranceTestMethods(testMethods);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  aTestCategory
     * @param  units
     */
    public void setToleranceUnitsForCategory(TestCategory aTestCategory,
        Units units) {
        TestSpecification testSpecification = (TestSpecification)
            categoryTestSpecMap.get(aTestCategory);

        if (testSpecification == null) {
            testSpecification = new TestSpecification();
            categoryTestSpecMap.put(aTestCategory, testSpecification);
        }

        testSpecification.setToleranceUnits(units);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  aTestCategory
     * @param  tolerance
     */
    public void setUpperToleranceForCategory(TestCategory aTestCategory,
        Double tolerance) {
        TestSpecification testSpecification = (TestSpecification)
            categoryTestSpecMap.get(aTestCategory);

        if (testSpecification == null) {
            testSpecification = new TestSpecification();
            categoryTestSpecMap.put(aTestCategory, testSpecification);
        }

        testSpecification.setUpperTolerance(tolerance);
    }

    /*
     * (non-Javadoc)
     * @see ca.gc.hc.nhpd.model.MultiNamedObject#getSynonyms()
     */
	public Set<Synonym> getDisplaySynonyms() {
		
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
	
	public void setSynonyms(Set<Synonym> synonyms) {
		this.synonyms = synonyms;
	}
}
