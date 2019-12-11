package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.OrganismType;
import ca.gc.hc.nhpd.model.OrganismTypeGroup;
import ca.gc.hc.nhpd.util.StringComparator;

/*******************************************************************************
 * Wraps the model OrganismType to expose it to the web service.
 ******************************************************************************/
public class OrganismTypeWS implements Comparable<OrganismTypeWS> {

    // ~ Static fields/initializers
    // ---------------------------------------------
    private OrganismType organismType;

    // ~ Constructors
    // -----------------------------------------------------------

    public OrganismTypeWS(OrganismType organismType) {
        if (organismType == null) {
            throw new IllegalArgumentException("organismType is null!");
        }
        this.organismType = organismType;
    }

    public OrganismTypeWS() {
        this.organismType = new OrganismType();
    }

    /*----------------------------------------------------------------
     * Public Accessors/Mutators - methods exposed to the Web Service
     *----------------------------------------------------------------*/

    /**
     * Getter that returns the name of the Organism Type Name. This function is
     * used by the web services.
     */
    public String getName() {
        return organismType.getName();
    }

    /**
     * Setter for the Organism Type Name. This function is required but NOT used
     * by the web services.
     * 
     * @param name
     */
    public void setName(String name) {
        // unused!
    }

    /**
     * Getter that returns the comment associated to the Organism Type. This
     * function is used by the web services.
     */
    public String getComment() {
        return organismType.getComment();
    }

    /**
     * Setter for the Organism Type Comment. This function is required but NOT
     * used by the web services.
     * 
     * @param comment
     */
    public void setComment(String comment) {
        // unused!
    }

    // the single org part name
    /**
     * Getter that returns the code associated to the Organism Type. This
     * function is used by the web services.
     */
    public String getCode() {
        return organismType.getCode();
    }

    /**
     * Setter for the Organism Type Code. This function is required but NOT used
     * by the web services.
     * 
     * @param code
     */
    public void setCode(String code) {
        // unused!
    }

    public OrganismTypeGroupWS getOrganismTypeGroup() {
        OrganismTypeGroup organismTypeGroup = organismType.getGroup();
        if (organismTypeGroup != null && organismTypeGroup.getNameE() != null) {
            return new OrganismTypeGroupWS(organismTypeGroup);
        }
        return null;
    }

    public void setOrganismTypeGroup(OrganismTypeGroupWS organismTypeGroup) {
        // unused!
    }

    /*
     * Method required because this object is added to a TreeSet Does an ignore
     * case compare on the Organism Type Code
     */
    public int compareTo(OrganismTypeWS o) throws ClassCastException {
        return StringComparator.compare(organismType.getCode(),
                ((OrganismTypeWS) o).getCode());
    }

}