package ca.gc.hc.nhpd.webservice.model;
import ca.gc.hc.nhpd.model.DietaryAllowance;

public class DietaryAllowanceWS implements Comparable<DietaryAllowanceWS> {

    // Wrap the recommended dietary allowance
    private DietaryAllowance dietaryAllowance;

    public DietaryAllowanceWS(DietaryAllowance dietaryAllowance) {
        if (dietaryAllowance == null) {
            throw new IllegalArgumentException("dietaryAllowance is null!");
        }
        this.dietaryAllowance = dietaryAllowance;
    }

    public DietaryAllowanceWS() {
        this.dietaryAllowance = new DietaryAllowance();
    }

    public SubPopulationWS getSubPopulation() {
        if (dietaryAllowance.getSubPopulation() != null) {
            return new SubPopulationWS(dietaryAllowance.getSubPopulation());
        }
        return null;
    }

    public void setSubPopulation(SubPopulationWS subPopulationWS) {
        // Not Implemented.
    }

    public Float getMinimumThreshold() {
        return dietaryAllowance.getMinimumThreshold();
    }

    public void setMinimumThreshold(Float minimumThreshold) {
        //
    }

    /**
     * Implements Comparable compareTo method. Method required because this
     * object is added to a TreeSet.  Delegates the compare operation to the 
     * SubPopulation contained by the DietaryAllowance model object. 
     */
    public int compareTo(DietaryAllowanceWS o) throws ClassCastException {
        
       /*
    	* Delegate the Compare test to the contained sub-population.  
    	* Assuming that sub-population should never be null...  
        */
    	if (dietaryAllowance.getSubPopulation() != null &&
            o.getSubPopulation() != null) {
            return getSubPopulation().compareTo(o.getSubPopulation());
        }
        
       /*
        * Fallback: Compare on the minimum threshold.  This is only a fallback and
    	* not guaranteed to work since several DietaryAllowances may have the same
    	* minimumThreshold. 
    	*/
        return dietaryAllowance.getMinimumThreshold().compareTo(o.getMinimumThreshold());
    }
       
}
