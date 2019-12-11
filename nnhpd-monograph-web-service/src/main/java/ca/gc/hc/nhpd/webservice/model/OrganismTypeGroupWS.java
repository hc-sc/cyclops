package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.OrganismTypeGroup;
import ca.gc.hc.nhpd.util.StringComparator;

/*******************************************************************************
 * Wraps the model OrganismType to expose it to the web service.
 ******************************************************************************/
public class OrganismTypeGroupWS implements Comparable<OrganismTypeGroupWS> {

    // ~ Static fields/initializers
    // ---------------------------------------------

    private OrganismTypeGroup organismTypeGroup;

    // ~ Constructors
    // -----------------------------------------------------------

    public OrganismTypeGroupWS(OrganismTypeGroup organismTypeGroup) {
        if (organismTypeGroup == null) {
            throw new IllegalArgumentException("organismTypeGroup is null!");
        }
        this.organismTypeGroup = organismTypeGroup;
    }

    public OrganismTypeGroupWS() {
        this.organismTypeGroup = new OrganismTypeGroup();
    }

    /*----------------------------------------------------------------
     * Public Accessors/Mutators - methods exposed to the Web Service
     *----------------------------------------------------------------*/

    /**
     * Getter that returns the name of the Organism Type Name. This function is
     * used by the web services.
     */
    public String getName() {
        return organismTypeGroup.getName();
    }

    /**
     * Setter for the Organism Type Name. This function is required but NOT used
     * by the web services.
     * 
     * @param name
     */
    public void setName(String name) {
        // Not Implemented.
    }

    /**
     * Getter that returns the comment associated to the Organism Type. This
     * function is used by the web services.
     */
    public String getComment() {
        return organismTypeGroup.getComment();
    }

    /**
     * Setter for the Organism Type Comment. This function is required but NOT
     * used by the web services.
     * 
     * @param comment
     */
    public void setComment(String comment) {
        // Not Implemented.
    }

    /**
     * Method required because this object is added to a TreeSet Does an ignore
     * case compare on the Organism Type Code
     */
    public int compareTo(OrganismTypeGroupWS o) throws ClassCastException {

        if (getName() == null) {
            return 1;
        }
        if (o == null || o.getName() == null) {
            return -1;
        }

        return StringComparator.compare(organismTypeGroup.getName(),
                ((OrganismTypeGroupWS) o).getName());
    }

}