/*
 * Created on 30-Aug-07
 *
 */
package ca.gc.hc.nhpd.model;

import java.io.Serializable;


/*******************************************************************************
 * The MonographCitationJoin provides a place to store the various relatinships
 * betwen the Monograph object and the Citation object. Since we have 4
 * possibilities, this Object / Join Table allows us to store these in a single
 * table, rather than creating 4 additional Oracle join tables.
 */

public class MonographCitationJoin implements Comparable {

    //~ Static fields/initializers ---------------------------------------------

    static public final Long RELATION_BIBLIOGRAPHY_ENGLISH = new Long(1);
    static public final Long RELATION_BIBLIOGRAPHY_FRENCH = new Long(2);
    static public final Long RELATION_REFERENCE_REVIEWED_ENGLISH = new Long(3);
    static public final Long RELATION_REFERENCE_REVIEWED_FRENCH = new Long(4);

    //~ Instance fields --------------------------------------------------------

    private Citation citation;

    private Id id = new Id();

    private Monograph monograph;
    private Long relationship;

    //~ public -----------------------------------------------------------------

    /**
     * Generic Constructor for this object. 
     */
    public MonographCitationJoin() {
    }

    /**
     * Constructor used by Hibernate to create and manipulate this object.  
     * 
     * @param monograph object used in this join.
     * @param citation object used in this join.
     * @param relationship relationship used in this join.
     */
    public MonographCitationJoin(GeneratedMonograph monograph, Citation citation,
        Long relationship) {
        this.monograph = monograph;
        this.citation = citation;
        this.relationship = relationship;

        this.id.mono_id = monograph.getId();
        this.id.citation_id = citation.getId();
        this.id.relationship = relationship;

        monograph.getMonographCitationJoins().add(this);
    }

    //~ public -----------------------------------------------------------------

    /**
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param   o  the object to compare this to.
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ClassCastException  DOCUMENT ME!
     */
    public int compareTo(Object o) throws ClassCastException {

        if (getId() == null) {
            return 1;
        }

        if (o == null) {
            return -1;
        }

        return getId().compareTo(((MonographCitationJoin) o).getId());
    }

    public boolean equals(Object o) {

        if (getId() == null) {
            return false;
        }

        if (o == null) {
            return false;
        }

        return getId().equals(((MonographCitationJoin) o).getId());
    }

    /**
     * Gets the citation associated to this join relationship.
     *
     * @return  the Citation side of the relationship.
     *
     * @see     #setCitation()
     */
    public Citation getCitation() {
        return citation;
    }

    public Id getId() {
        return id;
    }

    /**
     * Gets the monograph associated to this join relationship.
     *
     * @return  the Monograph side of the relationship.
     *
     * @see     #setMonograph()
     */
    public Monograph getMonograph() {
        return monograph;
    }

    /**
     * Gets the relationship associated to this join relationship.
     *
     * @return  the Relationship associated with this join.
     *
     * @see     #setRelationship()
     */
    public Long getRelationship() {
        return relationship;
    }

    public int hashCode() {
        return getId().hashCode();
    }

    /**
     * Sets the citation associated to this join relationship.
     *
     * @param  value  Citation
     *
     * @see    #getCitation()
     */
    public void setCitation(Citation value) {
        citation = value;
    }

    public void setId(Id value) {
        id = value;
    }

    /**
     * Sets the monograph associated to this join relationship.
     *
     * @param  value  Monograph
     *
     * @see    #getMonograph()
     */
    public void setMonograph(Monograph value) {
        monograph = value;
    }

    /**
     * Sets the relationship associated to this join relationship.
     *
     * @param  value  Relationship
     *
     * @see    #getRelationship()
     */
    public void setRelationship(Long value) {
        relationship = value;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getId().toString());

        return sb.toString();
    }

    //~ Inner Classes ----------------------------------------------------------

    /*******************************************************************************
     * The Id inner class is tightly joined to Hibernate and allows the
     * monograph citation join table to represent multiple monograph to citation
     * relationship through the use of a collections (relationship) column.
     *
     * <p>Following the examples provided, we are formulating a complex id for
     * Hibernate to uniquely identify this object.
     */

    static public class Id implements Serializable, Comparable {

        private Long citation_id;
        private Long mono_id;
        private Long relationship;

        // Default constructor.
        public Id() {
        }

        // Constructor used by hibernate.
        public Id(Long mono_id_value, Long citation_id_value,
            Long relationship_value) {
            this.mono_id = mono_id_value;
            this.citation_id = citation_id_value;
            this.relationship = relationship_value;
        }

        /**
         * Implementation of the compareTo to allow this object to be compared
         * to other objects.
         *
         * @param   o  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         *
         * @throws  ClassCastException  DOCUMENT ME!
         */
        public int compareTo(Object o) throws ClassCastException {

            if ((this.mono_id == null) || (this.citation_id == null)
                    || (this.relationship == null)) {
                return 1;
            }

            if ((o != null) && (o instanceof Id)) {
                Id that = (Id) o;

                if (!this.mono_id.equals(that.mono_id)) {
                    return this.mono_id.compareTo(that.mono_id);
                }

                if (!this.citation_id.equals(that.citation_id)) {
                    return this.citation_id.compareTo(that.citation_id);
                }

                if (!this.relationship.equals(that.relationship)) {
                    return this.relationship.compareTo(that.relationship);
                }

                // everything equal.
                return 0;
            }

            return -1;
        }

        /**
         * Implementation of an equals to determin if this object is equivalent
         * to another.
         *
         * @param   o  incomming object being compared to this one.
         *
         * @return  true / false boolean depending on object comparison.
         */
        public boolean equals(Object o) {

            if ((o != null) && (o instanceof Id)) {
                Id that = (Id) o;

                return this.mono_id.equals(that.mono_id)
                    && this.citation_id.equals(that.citation_id)
                    && this.relationship.equals(that.relationship);
            } else {
                return false;
            }
        }


        public int hashCode() {

            if (mono_id == null) {
                return 0;
            }

            if (citation_id == null) {
                return 0;
            }

            if (relationship == null) {
                return 0;
            }

            return mono_id.hashCode() * 100000 + citation_id.hashCode()
                + relationship.hashCode() * 1000000000;
        }

        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb.append("mono_id=" + mono_id);
            sb.append(", citation_id=" + citation_id);
            sb.append(", relationship=" + relationship);

            return sb.toString();
        }
    }

    /***************************************************************************
     * Returns the simple name of the class.  
     * Java 5 has a feature to return the simple name without a String formula.
     */
    public String getSimpleClassName() {
        String className = this.getClass().getName();
        return className.substring( className.lastIndexOf( "." ) + 1, className.length() ); 
    }
    
    
    public String getShortDescription() {
        return (String) getId().toString();
    }
    
}
