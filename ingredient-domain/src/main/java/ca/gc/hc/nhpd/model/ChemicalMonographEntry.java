package ca.gc.hc.nhpd.model;

import java.util.Set;
import java.util.TreeSet;

/*******************************************************************************
 * The ChemicalMonographEntry extends the MonographEntry and allows for the
 * additional characteristics unique to a Chemical Ingredient.
 */
public class ChemicalMonographEntry extends MonographEntry {
    
   
    private Set<MonographSourceIngredient> monographSourceIngredients;

    //~ public -----------------------------------------------------------------

    /**
     * Getter for the set of MonographSourceIngredient. These are Ingredients 
     * that this chemical ingredient could be retrieved from within the context
     * of a particular monograph.
	 *
	 * @return  a set of MonographSourceIngredient objects comprising the source
	 *          material for this monograph entry.
	 * @see     setMonographSourceIngredients()
	 */
    public Set<MonographSourceIngredient> getMonographSourceIngredients() {
		return monographSourceIngredients;
	}

    /**
     * Setter for the set of MonographSourceIngredients. These are Ingredients
     * that this chemical ingredient could be retrieved from within the context
     * of a particular monograph.
	 * 
	 * @param value  a set of Ingredient
	 * @see getSourceIngredients()
	 */
    public void setMonographSourceIngredients(Set<MonographSourceIngredient> value) {
    	monographSourceIngredients = value;
	}
    
    
    /***************************************************************************
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Subslasses should override this and add
     * their values to the end.
     */
    @Override
    public String getValuesAsString(){
        StringBuilder buffer = new StringBuilder();
        boolean isFirstItem;
       
        buffer.append(super.getValuesAsString());
        buffer.append(", otherSources: ");
       
        buffer.append(", sourceIngredients: ");
        if (getMonographSourceIngredients() != null){
            isFirstItem = true;
            buffer.append("[");
            for (MonographSourceIngredient monoSourceIngred : getMonographSourceIngredients()) {
                if (isFirstItem) {
                    isFirstItem = false;
                } else {
                    buffer.append(", ");
                }
                buffer.append(monoSourceIngred.getIngredient().getAuthorizedName());
            }
            buffer.append("]");
        } else {
            buffer.append("null");
        }

        return buffer.toString();
    }
 
}
