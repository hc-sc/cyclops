package ca.gc.hc.nhpd.model;

/*******************************************************************************
 * This Interface must be implemented by all citation notes that want to be returned in a Bibliographical Format.
 */
public interface BibliographicalEntry {

    /***************************************************************************
     * Returns this object in a Bibliographical Format.
     */
    public String getValueInBibliographicalFormat();

}