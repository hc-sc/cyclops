package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*******************************************************************************
 * An object that represents an abstract grouping of forms that an ingredient
 * may be administered by (e.g. pill, liquid, etc.).
 */
public class DosageFormGroup extends PersistentObject implements Comparable {
    private Map categoryTestSpecMap = new HashMap();
	private String code;
	private Set<DosageForm> dosageForms;
	private String nameE;
	private String nameF;
    
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
     */
	public Set<DosageForm> getDosageForms(){
		return dosageForms;
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
     * Gets a collection of the TestCategories that have tolerance information
     * for this DosageFormGroup.
     */
    public Set getTestCategoriesWithToleranceInfo(){
        return categoryTestSpecMap.keySet();
    }

    /***************************************************************************
     * Used by Hibernate. Use setToleranceTestMethodsForCategory(),
     * setToleranceUnitsForCategory and setUpperToleranceForCategory() instead.
     */
    public void setCategoryTestSpecMap(Map aMap){
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
	 * @param newVal
	 */
	public void setCode(String newVal){
		code = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setDosageForms(Set<DosageForm> newVal){
		dosageForms = newVal;
	}

    /***************************************************************************
	 * Gets the name in English.
	 * @return the name in English.
	 * @see #setNameE()
	 * @param newVal
	 */
	public void setNameE(String newVal){
		nameE = newVal;
	}

    /***************************************************************************
	 * Sets the name in French.
	 * @param newVal the name in French
	 * @see #getNameF()
	 * @param newVal
	 */
	public void setNameF(String newVal){
		nameF = newVal;
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
     */
    public Units getToleranceUnitsForCategory(TestCategory aTestCategory){
        TestSpecification testSpecification = 
            (TestSpecification)categoryTestSpecMap.get(aTestCategory);

        if (testSpecification != null) {
            return testSpecification.getToleranceUnits();
        }
        return null;
    }

    /***************************************************************************
     * @param aTestCategory
     */
    public Double getUpperToleranceForCategory(TestCategory aTestCategory){
        TestSpecification testSpecification = 
            (TestSpecification)categoryTestSpecMap.get(aTestCategory);

        if (testSpecification != null) {
            return testSpecification.getUpperTolerance();
        }
        return null;
    }

    /***************************************************************************
     * @param aTestCategory
     * @param units
     */
    public void setToleranceUnitsForCategory(TestCategory aTestCategory,
                                             Units units){
        TestSpecification testSpecification = 
            (TestSpecification)categoryTestSpecMap.get(aTestCategory);

        if (testSpecification == null) {
            testSpecification = new TestSpecification();
            categoryTestSpecMap.put(aTestCategory, testSpecification);
        }
        testSpecification.setToleranceUnits(units);
    }

    /***************************************************************************
     * @param aTestCategory
     * @param testMethods
     */
    public void setToleranceTestMethodsForCategory(TestCategory aTestCategory,
                                                   Set testMethods){
        TestSpecification testSpecification = 
            (TestSpecification)categoryTestSpecMap.get(aTestCategory);

        if (testSpecification == null) {
            testSpecification = new TestSpecification();
            categoryTestSpecMap.put(aTestCategory, testSpecification);
        }
        testSpecification.setToleranceTestMethods(testMethods);
    }

    /***************************************************************************
     * @param aTestCategory
     * @param tolerance
     */
    public void setUpperToleranceForCategory(TestCategory aTestCategory,
                                             Double tolerance){
        TestSpecification testSpecification = 
            (TestSpecification)categoryTestSpecMap.get(aTestCategory);

        if (testSpecification == null) {
            testSpecification = new TestSpecification();
            categoryTestSpecMap.put(aTestCategory, testSpecification);
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
    	return StringComparator.compare(getName(),((DosageFormGroup)o).getName());

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
        buffer.append(", categoryTestSpecMap: ");
        buffer.append(getCategoryTestSpecMap());
        buffer.append(", dosageForms: ");
        buffer.append(getDosageForms());
        
        return buffer.toString();
    }
}