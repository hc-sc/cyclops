package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.QualifiedSynonym;
import ca.gc.hc.nhpd.model.Synonym;

/*******************************************************************************
 * Wraps the model OrganismType to expose it to the web service.
 ******************************************************************************/
public class QualifiedSynonymWS implements Comparable<QualifiedSynonymWS> {

    // ~ Static fields/initializers
    // ---------------------------------------------

    private QualifiedSynonym qualifiedSynonym;

    // ~ Constructors
    // -----------------------------------------------------------

    public QualifiedSynonymWS(QualifiedSynonym qualifiedSynonym) {
        if (qualifiedSynonym == null) {
            throw new IllegalArgumentException("qualifiedSynonym is null!");
        }
        this.qualifiedSynonym = qualifiedSynonym;
    }

    // Support added for Homeopathic Substances that don't have
    // references for common / proper names.
    public QualifiedSynonymWS(Synonym synonym) {
        if (synonym == null) {
            throw new IllegalArgumentException("Synonym is null!");
        }
        this.qualifiedSynonym = new QualifiedSynonym();
        this.qualifiedSynonym.setSynonym(synonym);
    }

    public QualifiedSynonymWS() {
        this.qualifiedSynonym = new QualifiedSynonym();
    }

    /*----------------------------------------------------------------
     * Public Accessors/Mutators - methods exposed to the Web Service
     *----------------------------------------------------------------*/

    /**
     * Getter that returns the name of the Qualified Synonym English Name. This
     * function is used by the web services.
     */
    public String getNameE() {
        if (qualifiedSynonym.getSynonym() != null
                && qualifiedSynonym.getSynonym().getNameE() != null) {
            return qualifiedSynonym.getSynonym().getNameE();
        }
        return null;
    }

    /**
     * Setter for the Qualified Synonym English Name. This function is required
     * but NOT used by the web services.
     * 
     * @param name
     */
    public void setNameE(String name) {
        // Not Implemented.
    }

    /**
     * Getter that returns the name of the Qualified Synonym French Name. This
     * function is used by the web services.
     */
    public String getNameF() {
        if (qualifiedSynonym.getSynonym() != null
                && qualifiedSynonym.getSynonym().getNameF() != null) {
            return qualifiedSynonym.getSynonym().getNameF();
        }
        return null;
    }

    /**
     * Setter for the Qualified Synonym French Name. This function is required
     * but NOT used by the web services.
     * 
     * @param name
     */
    public void setNameF(String name) {
        // Not Implemented.
    }

    /**
     * Getter that returns the local specific Qualified Synonym Name. This
     * function is used by the web services.
     */
    public String getName() {
        if (qualifiedSynonym.getSynonym() != null
                && qualifiedSynonym.getSynonym().getName() != null) {
            return qualifiedSynonym.getSynonym().getName();
        }
        return null;
    }

    /**
     * Setter for the local specific Qualified Synonym Name. This function is
     * required but NOT used by the web services.
     * 
     * @param name
     */
    public void setName(String name) {
        // Not Implemented.
    }

    /**
     * Method required because this object is added to a TreeSet. Uses the
     * Qualified Synonym ID to check for duplicates and provide a sort order.
     */
    public int compareTo(QualifiedSynonymWS o) throws ClassCastException {

        if (getQualifiedSynonym() == null
                || getQualifiedSynonym().getSynonym() == null) {
            return 1;
        }
        if (o == null || o.getQualifiedSynonym() == null
                || o.getQualifiedSynonym().getSynonym().getId() == null) {
            return -1;
        }

        return qualifiedSynonym.getId().compareTo(
                o.getQualifiedSynonym().getSynonym().getId());
    }

    /**
     * Returns an instance of the object being wrapped. Used for sorting
     * purposes.
     */
    private QualifiedSynonym getQualifiedSynonym() {
        return qualifiedSynonym;
    }

}