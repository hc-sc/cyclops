package ca.gc.hc.nhpd.model;

import java.util.Set;
import java.util.TreeSet;
import ca.gc.hc.nhpd.util.StringComparator; 

/*******************************************************************************
 * MonographSubIngredientDoseCombination contains a collection of 
 * MonographSubIngredientDoses.  When multiple MonographSubIngredientDoses are 
 * contained in this object they are used with "logical and" semantics; they 
 * must be used as a combination.  When the collection contains a single 
 * MonographSubIngredientDose, "logical or" semantics are applied across the
 * collection of MonographSubIngredientDoseCombination objects contained by
 * MonographDose objects; that is, any of the listed 
 * MonographSubIngredientDoseCombinations may be used in isolation. 
 * 
 * See Milk Thistle Monograph
 */
public class MonographSubIngredientDoseCombination extends PersistentObject 
												   implements Comparable {
    private String code;
	
    private Set<MonographSubIngredientDose> monographSubIngredientDoses;
    //parent MonographDose
    //private MonographDose parentMonographDose;

    /**
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Over-ridden to add additional values to the
     * end.
     *
     * @return  this object as a string value
     */
    @Override
    public String getValuesAsString() {
        StringBuilder buffer = new StringBuilder();
        /* TODO implement
        buffer.append(super.getValuesAsString());
        buffer.append(", monographDoses: ");
        buffer.append(getMonographSubIngredientDoses());
		*/
        return buffer.toString();
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<MonographSubIngredientDose> getMonographSubIngredientDoses() {
		return monographSubIngredientDoses;
	}

	public void setMonographSubIngredientDoses(
			Set<MonographSubIngredientDose> monographSubIngredientDoses) {
		this.monographSubIngredientDoses = monographSubIngredientDoses;
	}
	
	public boolean isHasMultipleSubIngredientDoses() {
		if (monographSubIngredientDoses == null) {
			return false;
		}
		if (monographSubIngredientDoses.size() > 1) {
		    return true;
		}
		return false;
	}
	
	public int compareTo(Object obj) throws ClassCastException {

        if (this.equals(obj)) {
             return 0;
         }

         if (getId() == null) {
             return 1;
         }

         if ((obj == null) || (((MonographSubIngredientDoseCombination) obj).getId() == null)) {
             return -1;
         }
         
		 return StringComparator.compare(this.getId().toString(), 
				((MonographSubIngredientDoseCombination)obj).getId().toString());	
	
	}

}
