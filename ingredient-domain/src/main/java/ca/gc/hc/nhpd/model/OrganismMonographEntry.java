package ca.gc.hc.nhpd.model;

import java.util.Set;

/*******************************************************************************
 * The OrganismMonographEntry extends the MonographEntry and allows for the
 * additional characteristics unique to an Organism Substance.
 */
public class OrganismMonographEntry extends MonographEntry {
    private Set<PreparationTypeGroup> preparationTypeGroups;
    private Set<PreparationType> preparationTypes;

    //~ public -----------------------------------------------------------------

	/***************************************************************************
     * Gets the subset of this OrganismSubstance's PreparationTypeGroups that
     * are applicable to this Monograph.
	 */
    public Set<PreparationTypeGroup> getPreparationTypeGroups() {
        return preparationTypeGroups;
    }

    /***************************************************************************
     * Gets the subset of this OrganismSubstance's PreparationTypes that are
     * applicable to this Monograph.
     */
    public Set<PreparationType> getPreparationTypes() {
        return preparationTypes;
    }

    /***************************************************************************
     * Sets teh subset of this OrganismSubstance's PreparationTypeGroups that
     * are applicable to this Monograph.
     */
    public void setPreparationTypeGroups(Set<PreparationTypeGroup> value) {
        preparationTypeGroups = value;
    }

    /***************************************************************************
     * Sets the subset of this OrganismSubstance's PreparationTypes that are
     * applicable to this Monograph.
     */
    public void setPreparationTypes(Set<PreparationType> value) {
        preparationTypes = value;
    }

    /***************************************************************************
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Subclasses should override this and add
     * their values to the end.
     */
    @Override
    public String getValuesAsString(){
        StringBuilder buffer = new StringBuilder();
        boolean isFirstItem;
       
        buffer.append(super.getValuesAsString());
        buffer.append(", preparationTypeGroups: ");
        if (getPreparationTypeGroups() != null){
            isFirstItem = true;
            buffer.append("[");
            for (PreparationTypeGroup group : getPreparationTypeGroups()) {
                if (isFirstItem) {
                    isFirstItem = false;
                } else {
                    buffer.append(", ");
                }
                buffer.append(group.getNameE());
            }
            buffer.append("]");
        } else {
            buffer.append("null");
        }
        buffer.append(", preparationTypes: ");
        if (getPreparationTypes() != null){
            isFirstItem = true;
            buffer.append("[");
            for (PreparationType type : getPreparationTypes()) {
                if (isFirstItem) {
                    isFirstItem = false;
                } else {
                    buffer.append(", ");
                }
                buffer.append(type.getNameE());
            }
            buffer.append("]");
        } else {
            buffer.append("null");
        }

        return buffer.toString();
    }
}
