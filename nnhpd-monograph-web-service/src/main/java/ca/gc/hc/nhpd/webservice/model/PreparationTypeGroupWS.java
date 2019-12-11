package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.PreparationType;
import ca.gc.hc.nhpd.model.PreparationTypeGroup;
import ca.gc.hc.nhpd.util.StringComparator;
import ca.gc.hc.nhpd.util.ThreadContext;

import java.util.Set;
import java.util.TreeSet;

public class PreparationTypeGroupWS implements
        Comparable<PreparationTypeGroupWS> {

    private String version = ThreadContext.getInstance().getVersion();
	
    // ~ Static fields/initializers
    // ---------------------------------------------
    private PreparationTypeGroup preparationTypeGroup;

    // ~ Constructors
    // -----------------------------------------------------------

    public PreparationTypeGroupWS(PreparationTypeGroup prepTypeGroup) {
        if (prepTypeGroup == null) {
            // throw new IllegalArgumentException();
            preparationTypeGroup = new PreparationTypeGroup();
        } else {
            preparationTypeGroup = prepTypeGroup;
        }
    }

    public PreparationTypeGroupWS() {
        this.preparationTypeGroup = new PreparationTypeGroup();
    }

    /*----------------------------------------------------------------
     * Public Accessors/Mutators - methods exposed to the Web Service
     *----------------------------------------------------------------*/

    /**
     * Returns the code associated with the preparation type group.
     * 
     * @return the preparation type group code.
     */
    public String getCode() {
        return preparationTypeGroup.getCode();
    }

    public void setCode(String code) {
        // Not Implemented.
    }

    /**
     * Returns the description associated with the preparation type group.  This 
     * functionality was removed with the web service updates and only returns 
     * the description for backwards compatibility with the ePLA 1.4 form.
     * 
     * @return the preparation type group description.
     */
    public String getDescription() {
    
    	if (!version.equals(ThreadContext.VERSION_1_4)) {
    		return null;
    	}
    
        return preparationTypeGroup.getDescription();
    }

    public void setDescription(String descriptionE) {
        // Not Implemented.
    }

    /**
     * Returns the locale specific name associated with the preparation type
     * group.
     * 
     * @return the preparation type group locale specific name.
     */
    public String getName() {
        return preparationTypeGroup.getName();
    }

    public void setName(String name) {
        // Not Implemented.
    }

    /**
     * Returnes a set of preparation type associated with the preparation type
     * group.
     * 
     * @return the set of preparation types associated with the preparation type
     *         group.
     */
    public Set<PreparationTypeWS> getPreparationTypes() {
        // wrap the contained PreparationTypes
        Set<PreparationTypeWS> prepTypesWS = new TreeSet<PreparationTypeWS>();
        Set<PreparationType> prepTypes = preparationTypeGroup
                .getPreparationTypes();
        if (prepTypes != null) {
            for (PreparationType preparationType : prepTypes) {
                prepTypesWS.add(new PreparationTypeWS(preparationType));
            }
        }
        return prepTypesWS;
    }

    public void setPreparationTypes(Set<PreparationTypeWS> preparationTypes) {
        // Not Implemented.
    }

    /*
     * Implement Comparable compareTo method. Method required because this
     * object is added to a TreeSet Does an ignore case compare on the
     * Preparation Name
     */
    public int compareTo(PreparationTypeGroupWS o) throws ClassCastException {
        // String thisName = preparationTypeGroup.getName().toLowerCase();
        // String compName =
        // ((PreparationTypeGroupWS)o).getName().toLowerCase();
        return StringComparator.compare(preparationTypeGroup.getName(),
                ((PreparationTypeGroupWS) o).getName());
    }

}
