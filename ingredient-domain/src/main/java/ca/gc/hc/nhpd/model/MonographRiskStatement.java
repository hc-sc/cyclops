package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*******************************************************************************
 * These are the risk statements that are associated with the ingredients
 * according to a Monograph.
 */
public class MonographRiskStatement extends DependantObject implements Comparable {
	private String code;
	private RiskStatement riskStatement;
    //parent Monographs 
	private Set<Monograph> monographs;
	private String notesE;
	private String notesF;
    private Set<Ingredient> sourceIngredients;
    private static final Log log = LogFactory.getLog(Ingredient.class);
    /**
     * Gets the code.  Only used in the loading of Monographs.
     * @return  the code.
     * @see     #setCode()
     */
    public String getCode() {
        return code;
    }
	
	/***************************************************************************
	 * Gets the notes in the language appropriate for the Locale.
	 * @return the locale-specific notes.
	 */
	public String getNotes(){
		if (isLanguageFrench()) {
			return getNotesF();
		}
		return getNotesE();
	}

	/***************************************************************************
	 * Gets the notes in English.
	 * @return the notes in English.
	 * @see #setNotesE()
	 */
	public String getNotesE(){
		return notesE;
	}

	/***************************************************************************
	 * Gets the notes in French.
	 * @return the notes in French.
	 * @see #setNotesF()
	 */
	public String getNotesF(){
		return notesF;
	}

	/***************************************************************************
	 * Sets the notes in English.
	 * @see #setNotesE()
	 */
	public void setNotesE(String newVal){
		notesE = newVal;
	}

	/***************************************************************************
	 * Sets the notes in French.
	 * @see #getNotesF()
	 */
	public void setNotesF(String newVal){
		notesF = newVal;
	}
    
	/* 
	 * Temporary convenience method to get dose restriction type code.  Look thru
	 * all the contained doses to see if they are all the same. 
	 * TODO - upcoming model tweak may store the restriction type code at the
	 * MDC level.  If we do set dose frequencies in the Combo objects, maybe we should 
	 * have a code which indicates to look into the child object when dealing with "fake"
	 * MDCs which just wrap a single MonographDose...   
	 */
	public String getDoseRestrictionTypeCode() {
		
		String returnCode = "";
		String currentCode = null;
		if (getMonographDoseCombination().getMonographDoses() != null) {
			for (MonographDose monoDose: getMonographDoseCombination().getMonographDoses()) {
				currentCode = monoDose.getRestrictionType().getCode();
				if (returnCode.equals("")) {  //first time?
					returnCode = currentCode;
				}
				else
					if (currentCode != returnCode) {
						log.warn("Multiple Dose RestrictionType codes encountered for MonographDoseCombination: " 
							+ getMonographDoseCombination().getCode());
					}
			}
		}
		return returnCode;
	}

	/**
	 * Helper method used to determin if we display the MonographDose as part of the risk statement.
     * 
     * Note:  Use this method when you are looking for a Dose Maximum and no Dose Minimum. 
	 */
	public boolean isDisplayMonographDoseCombination() {

		// Hide the Monograph Dose Combination if it does not exist.
        if (getMonographDoseCombination() == null) {
        	//log ("isDisplayMonographDoseCombination MDC is null (false)");
        	return false;
        }

        if (getMonographDoseCombination().getMonographDoses() != null) {
	        for (MonographDose monoDose: getMonographDoseCombination().getMonographDoses()) {
                
	        	if ((monoDose.getDoseMinimum() == null || monoDose.getDoseMinimum() == 0) &&
	        	    (monoDose.getDoseMaximum() != null && monoDose.getDoseMaximum() > 0))
	        		//log ("isDisplayMonographDoseCombination Contained doses null (false)"); 
	        		return true;
	        }
        }
        
        return false;
    }

    /**
     * Helper method used to determin if we display the MonographDose as part of the risk statement.
     * 
     * Note:  Use this method when you are looking for both a Maximum and Minimum dose.
     */
    public boolean isDisplayMonographBothDoseCombination() {

        // Hide the Monograph Dose Combination if it does not exist.
        if (getMonographDoseCombination() == null) {
            //log ("isDisplayMonographDoseCombination MDC is null (false)");
            return false;
        }

        if (getMonographDoseCombination().getMonographDoses() != null) {
            for (MonographDose monoDose: getMonographDoseCombination().getMonographDoses()) {
                
                if ((monoDose.getDoseMinimum() != null || monoDose.getDoseMinimum() >= 0) &&
                    (monoDose.getDoseMaximum() != null && monoDose.getDoseMaximum() > 0))
                    //log ("isDisplayMonographDoseCombination Contained doses null (false)"); 
                    return true;
            }
        }
        
        return false;
    }

    /**
     * Helper method used to determin if we display the MonographDose as part of the risk statement.
     
    public boolean isDisplayMonographDose() {
        // Hide the Monograph Dose if it does not exists.
        if(getMonographDose()==null) {
            return false;
        }
        // Hide the Monograph Dose if the Minimum is empty, or the Maximum is filled.
        if(getMonographDose().getDoseMinimum()==null
            ||getMonographDose().getDoseMaximum()!=null){
            return false;
        }
        return true;
    }
*/
    /**
     * Helper method used to determin if we display the IngredientSource as part of the risk statement.
     */
    public boolean isDisplayIngredientSource() {
        // Hide the Ingredient Source if it does not exist.
        if(getMonographSourceIngredient()==null) {
            return false;
        }
        return true;
    }

    /**
     * Helper method used to determine if we display a normal Dependency object as part 
     * of the risk statement.
     */
    public boolean isDisplayOtherDependency() {
        if(isDisplayMonographBothDoseCombination()) {
            return false;
        }
        if(isDisplayIngredientSource()){
            return false;    
        }
        if(isDisplayMonographDoseCombination()){
            return false;
        }
        return true;
    }

	/**
	 * Returns the parent monographs.  Only used in the loading of Monographs.
	 * @return
	 */
    public Set<Monograph> getMonographs() {
    	return monographs;
	}
	
	
    /***************************************************************************
     */
    public RiskStatement getRiskStatement(){
        return riskStatement;
	}
    
	public void setMonographs(Set<Monograph> monographs) {
		this.monographs = monographs;
	}
	
    /***************************************************************************
     * @param newVal
     */
    public void setRiskStatement(RiskStatement newVal){
        riskStatement = newVal;
	}

    /**
     * Getter for the set of Source Ingredients. These are Ingredients that this
     * chemical ingredient could be retrieved from within the context of a
     * particular monograph.
     *
     * @return  a set of Ingredient objects comprizing the source material for
     *          this monograph entry.
     *
     * @see     setSourceIngredients()
	 */
    public Set<Ingredient> getSourceIngredients() {
        return sourceIngredients;
	}

    /**
     * Setter for the set of Source Ingredients. These are Ingredients that this
     * chemical ingredient could be retrieved from within the context of a
     * particular monograph.
     * 
     * @param value  a set of Ingredients
     * 
     * @see getSourceIngredients()
	 */
    public void setSourceIngredients(Set<Ingredient> value) {
        sourceIngredients = value;
	}
    
    /**
     * Compares this object with the specified object for equality and for
     * order. Returns a negative integer, zero, or a positive integer as this
     * object is less than, equal to, or greater than the specified object.
     *
     * @param   o  the object to compare this to.
     *
     * @return  a comparator flag qualifying equality and order
     *
     * @throws  ClassCastException  when the object provided is of the wrong
     *                              type
     */
   public int compareTo(Object o) throws ClassCastException {

        if (this.equals(o)) {
            return 0;
        }

        if (getRiskStatement() == null 
            || getRiskStatement().getRiskTypeName() == null ) {        
            return 1;
        }

        if (o == null 
            || ((MonographRiskStatement)o).getRiskStatement().getRiskTypeName() == null) {
            return -1;
        }

        // Move entries with dependencies at bottom of list.
        if ((this.getDependencyAsString() != null) 
            && (((MonographRiskStatement) o).getDependencyAsString() == null)) {
                return 1;
            }
        if ((this.getDependencyAsString() == null) 
            && (((MonographRiskStatement) o).getDependencyAsString() != null)) {
                return -1;
            }

        // Sort on Dependency if available and not equal.
        if ((this.getDependencyAsString() != null)
                && (((MonographRiskStatement) o).getDependencyAsString() != null)
                && !this.getDependencyAsString().equals(
                    ((MonographRiskStatement) o).getDependencyAsString())) {
        //    return this.getDependencyAsString().compareTo(((DependantObject) o)
        //            .getDependencyAsString());
            return StringComparator.compare(getDependencyAsString(),
                    ((DependantObject)o).getDependencyAsString());
        }
        
        // Sort by RiskTypeName
        if (!getRiskStatement().getRiskTypeName().equals(((MonographRiskStatement)o).getRiskStatement().getRiskTypeName())) {
            //return getRiskStatement().getRiskTypeName().compareTo(((MonographRiskStatement)o).getRiskStatement().getRiskTypeName());
            return StringComparator.compare(getRiskStatement().getRiskTypeName(),
                    ((MonographRiskStatement)o).getRiskStatement().getRiskTypeName());
        
        }            

        // Perform the comparison on the Maximum Dose
        return getRiskStatement().compareTo(((MonographRiskStatement) o).getRiskStatement());
        
    }




    
    
	/**
	 * This provides a list of the instance variable values for this object, and
	 * is called by the PersistentObject's toString(). Intended to provide
	 * useful debugging information. Over-ridden to add additional values to the
	 * end.
	 */
    @Override
	public String getValuesAsString() {
        StringBuilder buffer = new StringBuilder();
        boolean isFirstItem;

		buffer.append(super.getValuesAsString());
        buffer.append(", notesE: ");
        buffer.append(getNotesE());
		buffer.append(", riskStatement: ");
		if (getRiskStatement() != null){
			buffer.append(getRiskStatement().getName());
		} else {
			buffer.append("null");
		}
        buffer.append(", sourceIngredients: ");
        if (getSourceIngredients() != null){
            isFirstItem = true;
            buffer.append("[");
            for (Ingredient ingredient : getSourceIngredients()) {
                if (isFirstItem) {
                    isFirstItem = false;
                } else {
                    buffer.append(", ");
                }
                buffer.append(ingredient.getAuthorizedName());
            }
            buffer.append("]");
        } else {
            buffer.append("null");
        }
		return buffer.toString();
	}

	/**
     * Sets the code.  Only used in the loading of Monographs.
     *
     * @param  newValue  code.
     */
    public void setCode(String newValue) {
        code = newValue;
    }

}