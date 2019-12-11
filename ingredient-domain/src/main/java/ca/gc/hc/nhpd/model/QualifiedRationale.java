/*
 * Created on 30-Aug-07
 */
package ca.gc.hc.nhpd.model;


/*******************************************************************************
 * The QualifiedRationales object is used to add additional qualifications to
 * the PersistentObject. It was created to support the many Monograph related
 * object by allowing the client to add citation information to any of the
 * objects added to support a Monograph, and to add the ability to add one or
 * more citations to qualify the information.
 */
public class QualifiedRationale extends Rationale {

    //~ Instance fields --------------------------------------------------------

    private Citation citation;
    private String commentE;
    private String commentF;

    //~ public -----------------------------------------------------------------

    /**
     * Gets the citation associated with this Qualified Rationale.
     *
     * @return  the specific citation for this Qualified Rationale.
     */
    public Citation getCitation() {
        return citation;
    }

    /**
     * Gets the comment in the language appropriate for the Locale.
     *
     * @return  the locale-specific comment.
     */
    public String getComment() {

        if (isLanguageFrench()) {
            return getCommentF();
        }

        return getCommentE();
    }

    /**
     * Gets the English comment associated with this Qualified Rationale.
     *
     * @return  the English specific comment.
     */
    public String getCommentE() {
        return commentE;
    }

    /**
     * Gets the French comment associated with this Qualified Rationale.
     *
     * @return  the French specific comment.
     */
    public String getCommentF() {
        return commentF;
    }

    /**
     * Sets the citation associated with this Qualified Rationale.
     *
     * @param  newVal  the citation for this Qualified Rationale
     */
    public void setCitation(Citation newVal) {
        citation = newVal;
    }

    /**
     * Sets the English comment for this Qualified Rationale.
     *
     * @param  newVal  the English comment for this Qualified Rationale
     */
    public void setCommentE(String newVal) {
        commentE = newVal;
    }

    /**
     * Sets the French comment for this Qualified Rationale.
     *
     * @param  newVal  the French comment for this Qualified Rationale
     */
    public void setCommentF(String newVal) {
        commentF = newVal;
    }
}
