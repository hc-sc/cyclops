package ca.gc.hc.nhpd.model;

/*******************************************************************************
 * This class encapsulates the information typically stored by different
 * combinations of keys in a Monograph (see MonographEntry for examples). As such
 * it is really a value object.
 */
public class DosageRange extends PersistentObject { //TODO New Class
	private Units dosageUnits;
	private Double maximumDosage;
	private Double minimumDosage;

    /***************************************************************************
     */
	public Units getDosageUnits(){
		return dosageUnits;
	}

    /***************************************************************************
     */
	public Double getMaximumDosage(){
		return maximumDosage;
	}

    /***************************************************************************
     */
	public Double getMinimumDosage(){
		return minimumDosage;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setDosageUnits(Units newVal){
		dosageUnits = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setMaximumDosage(Double newVal){
		maximumDosage = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setMinimumDosage(Double newVal){
		minimumDosage = newVal;
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
               if (getDosageUnits() != null) {
                   buffer.append(", dosageUnits: ");
                   buffer.append(getDosageUnits());
               }
               if (getMaximumDosage() != null) {
                   buffer.append(", maximumDosage: ");
                   buffer.append(getMaximumDosage().toString());
               }
               if (getMinimumDosage() != null) {
                   buffer.append(", minimumDosage: ");
                   buffer.append(getMinimumDosage().toString());
               }
               return buffer.toString();
           }
    

}