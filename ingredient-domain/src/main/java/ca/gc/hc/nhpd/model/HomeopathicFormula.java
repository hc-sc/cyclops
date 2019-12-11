package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringUtils;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


/**
 * An object that represents a homeopathic formula.  These objects are used to
 * describe how source materials are diluted to result in the homeopathic
 * formula.  In some cases, several different method of preparations are
 * possible for a given formula and they are made available through the
 * getHomeopathicMethodOfPreparation accessor.
 */
public class HomeopathicFormula extends PersistentObject implements Comparable {

    private Boolean                             atfRequired;
    private Boolean                             atfRequiredInProcessingMi;
    private HomeopathicDilution                 homeopathicDilution;
    private Set<HomeopathicMethodOfPreparation> homeopathicMethodOfPreparations;
    private HomeopathicRole                     homeopathicRole;
    private Boolean                             knownAsSynthetic;
    private String                              notes;
    private Reference                           reference;
    private Set<HomeopathicGenericText>         sourceMaterials;
    private Boolean                             toxic;


    /**
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param  o  the object to compare this to.
     */
    public int compareTo(Object o) throws ClassCastException {

        if (o == null) {
            return -1;
        }

        // So far, I've only sceen a single formula per pharmacopia per
        // role.
        // TODO - may need to revisit compareTo as more data becomes evident.
        return getReference().compareTo(((HomeopathicFormula) o)
                    .getReference());

    }

    /**
     * Getter that returns the homeopathic dilution.
     *
     * @return  the homeopathic dilution used in this formula.
     */
    public HomeopathicDilution getHomeopathicDilution() {
        return homeopathicDilution;
    }

    /**
     * Getter that returns the possible homeopathic methods of preparations
     *
     * @return  the homeopathic method of preparations.
     */
    public Set<HomeopathicMethodOfPreparation> getHomeopathicMethodOfPreparations() {
        return homeopathicMethodOfPreparations;
    }

    /**
     * Getter that returns the ingredient role (homeopathic role) that this
     * formula is qualifying.
     *
     * @return  parent ingredient role
     */
    public HomeopathicRole getHomeopathicRole() {
        return homeopathicRole;
    }

    /**
     * Getter that returns any notes associated with this formula.
     *
     * @return  notes associated with this formula.
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Getter that returns any reference associated with this formula.
     *
     * @return  the reference (pharmacopia) that qualifies this formula.
     */
    public Reference getReference() {
        return reference;
    }

    /**
     * Getter that returns the source material associated with this formula.
     *
     * @return  the source material for this formula.
     */
    public Set<HomeopathicGenericText> getSourceMaterials() {
        return sourceMaterials;
    }

    /**
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Over-ridden to add additional values to the
     * end.
     */
    public String getValuesAsString() {
        StringBuilder buffer = new StringBuilder();

        buffer.append(super.getValuesAsString());
        buffer.append(", atfRequired: ");
        buffer.append(getAtfRequired());
        buffer.append(", atfRequiredInProcessingMi: ");
        buffer.append(getAtfRequiredInProcessingMi());
        buffer.append(", homeopathicMethodOfPreparations: ");
        buffer.append(getHomeopathicMethodOfPreparations());
        buffer.append(", knownAsSynthetic: ");
        buffer.append(getKnownAsSynthetic());
        buffer.append(", notes: ");
        buffer.append(getNotes());
        buffer.append(", reference: ");
        buffer.append(getReference());
        buffer.append(", sourceMaterials: ");
        if (getSourceMaterials() != null) {
            int counter = 0;
            Iterator<HomeopathicGenericText> i = getSourceMaterials().iterator();
            while (i.hasNext()) {
                HomeopathicGenericText sourceMaterial = (HomeopathicGenericText) i.next();
                if (counter != 0) {
                    buffer.append(", ");
                }
                buffer.append(sourceMaterial.getText());
                counter++;
            }
        } else {
            buffer.append("null");
        }
        buffer.append(", toxic: ");
        buffer.append(getToxic());
        return buffer.toString();
    }


    /**
     * Getter that returns the boolean flag indicating if the ATF is required.
     *
     * @return  boolean flag indicating if the ATF is required.
     */
    public Boolean getAtfRequired() {
        return atfRequired;
    }

    /**
     * Getter used by Hibernate that returns the string flag indicating if 
     * the ATF is required.
     *
     * @return  String flag indicating if the ATF is required.
     */
    public String getAtfRequiredAsString() {
    	return StringUtils.booleanToString(atfRequired);
    }
    
    /**
     * Getter that returns the boolean flag indicating if the ATF is required in
     * the processing of the MI.
     *
     * @return  boolean flag indicating if the ATF is required in the processing
     *          of this MI.
     */
    public Boolean getAtfRequiredInProcessingMi() {
        return atfRequiredInProcessingMi;
    }

    /**
     * Getter used by Hibernate that returns the boolean flag indicating if 
     * the ATF is required in the processing of the MI.
     *
     * @return  String flag indicating if the ATF is required in the processing
     *          of this MI.
     */
    public String getAtfRequiredInProcessingMiAsString() {
    	return StringUtils.booleanToString(atfRequiredInProcessingMi);
    }

    /**
     * Getter that returns a boolean flag indicating if this formula is also
     * known as a synthetic.
     *
     * @return  boolean flag indicating if this formula is also known as a
     *          synthetic.
     */
    public Boolean getKnownAsSynthetic() {
        return knownAsSynthetic;
    }

    /**
     * Getter used by Hibernate that returns a boolean flag indicating if 
     * this formula is also known as a synthetic.
     *
     * @return  String flag indicating if this formula is also known as a
     *          synthetic.
     */
    public String getKnownAsSyntheticAsString() {
        return StringUtils.booleanToString(knownAsSynthetic);
    }

    /**
     * Gets whether this formula / ingredient is considered toxic and must be highly
     * diluted to be used safely.
     * 
     * @return a boolean that indicates whether this is considered toxic and
     *         must be highly diluted to be used safely.
     *         
     * @see    setToxic()
     */
    public Boolean getToxic() {
        return toxic;
    }

    /**
     * Gets whether this formula / ingredient is considered toxic and must be highly
     * diluted to be used safely.  Used by Hibernate.
     * 
     * @return a String that indicates whether this is considered toxic and
     *         must be highly diluted to be used safely.
     *         
     * @see    setToxicAsString()        
     */
    public String getToxicAsString() {
        return StringUtils.booleanToString(toxic);
    }

    /**
     * Setter indicating if the ATF is required.
     *
     * @param  atfRequired  boolean flag indicating if the ATF is required.
     */
    public void setAtfRequired(Boolean atfRequired) {
        this.atfRequired = atfRequired;
    }

    /**
     * Setter used by Hibernate indicating if the ATF is required.
     *
     * @param  atfRequired  string flag indicating if the ATF is required.
     */
    public void setAtfRequiredAsString(String atfRequired) {
        this.atfRequired = StringUtils.stringToBoolean(atfRequired);
    }
        
    /**
     * Setter indicating if the ATF is required in the processing of the MI.
     *
     * @param  atfRequiredInProcessingMi  boolean flag indicating if the ATF is
     *                                    required in the processing of the MI.
     */
    public void setAtfRequiredInProcessingMi(
            Boolean atfRequiredInProcessingMi) {
        this.atfRequiredInProcessingMi = atfRequiredInProcessingMi;
    }

    /**
     * Setter used by Hibernate indicating if the ATF is required in the 
     * processing of the MI.
     *
     * @param  atfRequiredInProcessingMi  string flag indicating if the ATF is
     *                                    required in the processing of the MI.
     */
    public void setAtfRequiredInProcessingMiAsString(
            String atfRequiredInProcessingMi) {
        this.atfRequiredInProcessingMi = StringUtils.stringToBoolean(atfRequiredInProcessingMi);
    }

    /**
     * Setter for the homeopathic dilution.
     *
     * @param  homeopathicDilution
     */
    public void setHomeopathicDilution(
            HomeopathicDilution homeopathicDilution) {
        this.homeopathicDilution = homeopathicDilution;
    }

    /**
     * Setter for the homeopathic method of preparations.
     *
     * @param  homeopathicMethodOfPreparations
     */
    public void setHomeopathicMethodOfPreparations(
            Set<HomeopathicMethodOfPreparation> homeopathicMethodOfPreparations) {
        this.homeopathicMethodOfPreparations = homeopathicMethodOfPreparations;
    }

    /**
     * Setter for the parent homeopathic role (ingredient role).
     *
     * @param  homeopathicRole
     */
    public void setHomeopathicRole(HomeopathicRole homeopathicRole) {
        this.homeopathicRole = homeopathicRole;
    }

    /**
     * Setter for the boolean flag indicating that this formula is also known as
     * a synthetic.
     *
     * @param  knownAsSynthetic
     */
    public void setKnownAsSynthetic(Boolean knownAsSynthetic) {
        this.knownAsSynthetic = knownAsSynthetic;
    }

    /**
     * Setter used by Hibernate for the string flag indicating that this 
     * formula is also known as a synthetic.
     *
     * @param  knownAsSynthetic
     */
    public void setKnownAsSyntheticAsString(String knownAsSynthetic) {
        this.knownAsSynthetic = StringUtils.stringToBoolean(knownAsSynthetic);
    }

    /**
     * Setter for the notes qualifying this homeopathic formula.
     *
     * @param  notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Setter for the reference qualifying this homeopathic formula.
     *
     * @param  reference
     */
    public void setReference(Reference reference) {
        this.reference = reference;
    }

    /**
     * Setter for the source material associated with this formula.
     *
     * @param  sourceMaterial
     */
    public void setSourceMaterials(Set<HomeopathicGenericText> sourceMaterials) {
        this.sourceMaterials = sourceMaterials;
    }

    /**
     * Gets whether this formula / ingredient is considered toxic and must be highly
     * diluted to be used safely.
     * 
     * @param  toxic a boolean that indicates whether this is considered toxic and
     *         must be highly diluted to be used safely.
     *         
     * @see    getToxic()
     */
    public void setToxic(Boolean toxic) {
        this.toxic = toxic;
    }

    /**
     * Gets whether this formula / ingredient is considered toxic and must be highly
     * diluted to be used safely.  Used by Hibernate
     * 
     * @param  toxic a String that indicates whether this is considered toxic and
     *         must be highly diluted to be used safely.
     *         
     * @see    getToxicAsString()
     */
    public void setToxicAsString(String toxic) {
        this.toxic = StringUtils.stringToBoolean(toxic);
    }
    
    /**
     * This method navigates through all the homeopathic method of
     * preparations, accumulates the quantity as string and attempts
     * to return a list of quantity as string.
     *  
     * @return
     */
    public Set<String> getQuantityAsString() {
    	TreeSet<String> quantityAsString = new TreeSet<String>();
    	Iterator i = getHomeopathicMethodOfPreparations().iterator();
    	while(i.hasNext()) {
    		HomeopathicMethodOfPreparation methodOfPreparation = (HomeopathicMethodOfPreparation) i.next();
    		if (methodOfPreparation != null 
    				&& methodOfPreparation.getQuantityAsString() != null) {
    			quantityAsString.add(methodOfPreparation.getQuantityAsString());
    		}
    	}
    	return quantityAsString;
    }
    
}
