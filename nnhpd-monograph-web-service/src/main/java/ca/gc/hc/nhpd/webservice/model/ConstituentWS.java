package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.dto.Constituent;
import ca.gc.hc.nhpd.util.StringComparator;
import java.util.List;

/*******************************************************************************
 * Wraps the model OrganismType to expose it to the web service.
 ******************************************************************************/
public class ConstituentWS implements Comparable<ConstituentWS> {

    // ~ Static fields/initializers
    // ---------------------------------------------
    private Constituent constituent;

    // ~ Constructors
    // -----------------------------------------------------------

    public ConstituentWS(Constituent constituent) {
        if (constituent == null) {
            throw new IllegalArgumentException("constituent is null!");
        }
        this.constituent = constituent;
    }

    public ConstituentWS() {
        this.constituent = new Constituent();
    }

    /*----------------------------------------------------------------
     * Public Accessors/Mutators - methods exposed to the Web Service
     *----------------------------------------------------------------*/
        
    public Constituent getConstituent() {
        return constituent;
    }
    
    /**
     * Getter that returns the name of the Organism Type Name. This function is
     * used by the web services.
     */
    public String getApprovedName() {
        return constituent.getAuthorizedName();
    }

    /**
     * Setter for the Organism Type Name. This function is required but NOT used
     * by the web services.
     * 
     * @param name
     */
    public void setApprovedName(String name) {
        // unused!
    }

    /**
     * Getter that returns the comment associated to the Organism Type. This
     * function is used by the web services.
     */
    public List<String> getCommonNames() {
        return constituent.getCommonNames();
    }

    /**
     * Setter for the Organism Type Comment. This function is required but NOT
     * used by the web services.
     * 
     * @param comment
     */
    public void setCommonNames(List<String> commonNames) {
        // unused!
    }

    /**
     * Getter for the Id.
     * 
     * @return the id associated with this constituent.
     */
    public Long getId() {
        return constituent.getId();
    }
    
    /**
     * Setter for the Id.
     * 
     * @param id
     */
    public void setId(Long id) {
        // unused!
    }
    
    /**
     * Getter that returns the comment associated to the Organism Type. This
     * function is used by the web services.
     */
    public List<String> getProperNames() {
        return constituent.getProperNames();
    }

    /**
     * Setter for the Organism Type Comment. This function is required but NOT
     * used by the web services.
     * 
     * @param comment
     */
    public void setProperNames(List<String> properNames) {
        // unused!
    }

    /*
     * Method required because this object is added to a TreeSet Does an ignore
     * case compare on the Organism Type Code
     */
    public int compareTo(ConstituentWS o) throws ClassCastException {
        return StringComparator.compare(constituent.getAuthorizedName(),
                ((ConstituentWS) o).getConstituent().getAuthorizedName());
    }

}