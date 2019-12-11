package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.util.StringComparator;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;


/*******************************************************************************
 * An object that represents a name that may be in common use for an item.
 */
public class Synonym extends PersistentObject implements Comparable {

    //~ Static fields/initializers ---------------------------------------------

    static public final String EMPTY_EQUIVALENCE = "NA";

    //~ Instance fields --------------------------------------------------------

    private Set<Citation> citationsE;
    private Set<Citation> citationsF;
    private String commentE;
    private String commentF;
    private String descriptionE;
    private String descriptionF;
    private String nameE;
    private String nameF;

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

        if (getName() == null) {
            return 1;
        }

        if ((o == null) || (((Synonym) o).getName() == null)) {
            return -1;
        }

        // if the English same, and French different, compare the French.
        if ((getNameF() != null) && (getNameE() != null)
                && (((Synonym) o).getNameF() != null)
                && (((Synonym) o).getNameE() != null)
                && getNameE().toLowerCase().equals(
                    ((Synonym) o).getNameE().toLowerCase())
                && !getNameF().toLowerCase().equals(
                    ((Synonym) o).getNameF().toLowerCase())) {
        	int result = StringComparator.compare(getNameF(),((Synonym)o).getNameF());
        	if (result != 0) 
        		return result;
        	
        }

        // if the French same, and English different, compare the English.
        if ((getNameF() != null) && (getNameE() != null)
                && (((Synonym) o).getNameF() != null)
                && (((Synonym) o).getNameE() != null)
                && getNameF().toLowerCase().equals(
                    ((Synonym) o).getNameF().toLowerCase())
                && !getNameE().toLowerCase().equals(
                    ((Synonym) o).getNameE().toLowerCase())) {
            int result = StringComparator.compare(getNameE(),((Synonym)o).getNameE());
            if (result != 0) 
        		return result;
        }

        // if both are same, or both are different return a compare based on
        // language.
    	int result = StringComparator.compare(getName(),((Synonym)o).getName());
    	if (result != 0) 
    		return result;
    	
    	//TODO testing code! add null - non-null tests
    	Set<Citation> thisCitations = getCitations();
    	Set<Citation> passedCitations = ((Synonym)o).getCitations();
    	if (thisCitations == null && passedCitations != null) {
        	return -1;
    	}
    	if (thisCitations != null && passedCitations == null) {
            return 1;
    	}
    	if (thisCitations != null && passedCitations != null) { 
            if (thisCitations.size() > passedCitations.size()) {
            	return 1;
            }
            if (thisCitations.size() < passedCitations.size()) {
            	return -1;
            }
    	}
    	return 0;
    	
    }

    
    /**
     * Gets the citations in the language appropriate for the Locale.
     *
     * @return  the locale-specific citations.
     */
    public Set<Citation> getCitations() {
        if (isLanguageFrench()) {
            return getCitationsF();
        }

        return getCitationsE();
    }

    /**
     * Gets the citations associated with this Object ordered by the citation
     * date.
     * @return the citations Set for this Object ordered by date.
     */
    public Set<Citation> getCitationsByDate() {
        Comparator<Citation> citationComparator = new Citation.CitationComparator();
        Set<Citation> citationsByDate = new TreeSet<Citation>(citationComparator);
        citationsByDate.addAll(getCitations());
        return citationsByDate;
    }

    /**
     * Gets the set of citations for the English text.
     *
     * @return  the set of citations for the English text.
     *
     * @see     #setCitationE()
     */
    public Set<Citation> getCitationsE() {
        return citationsE;
    }

    /**
     * Gets the set of citations for the French text.
     *
     * @return  the set of citations for the French text.
     *
     * @see     #setCitationsE()
     */
    public Set<Citation> getCitationsF() {
        return citationsF;
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
     * Gets the comment in English.
     *
     * @return  the comment in English.
     *
     * @see     #setCommentE()
     */
    public String getCommentE() {
        return commentE;
    }

    /**
     * Gets the comment in French.
     *
     * @return  the comment in French.
     *
     * @see     #setCommentF()
     */
    public String getCommentF() {
        return commentF;
    }

    /**
     * Gets the description in the language appropriate for the Locale.
     *
     * @return  the locale-specific description.
     */
    public String getDescription() {

        if (isLanguageFrench()) {
            return getDescriptionF();
        }

        return getDescriptionE();
    }

    /**
     * Gets the description in English.
     *
     * @return  the description in English.
     *
     * @see     #setDescriptionE()
     */
    public String getDescriptionE() {
        return descriptionE;
    }

    /**
     * Gets the description in French.
     *
     * @return  the description in French.
     *
     * @see     #setDescriptionF()
     */
    public String getDescriptionF() {
        return descriptionF;
    }

    /**
     * Gets the name in the language appropriate for the Locale.
     *
     * @return  the locale-specific name.
     */
    public String getName() {

        if (isLanguageFrench()) {
            return getNameF();
        }

        return getNameE();
    }

    private static String LABEL_MISSING_FRENCH = "Aucun équivalent pour le synonyme ";
    private static String LABEL_MISSING_ENGLISH = "No English equivalent to synonym ";
    private static String LABEL_QUOTE = "\"";
    
    /**
     * Gets the name in the language appropriate for the Locale.  When
     * the name is not available in French or English, returns the alternate
     * language name with an appropriate label.
     *
     * @return  the locale-specific name.
     */
    public String getDisplayName() {

        if (isLanguageFrench()) {
        	if((getNameF()==null || getNameF().length()==0 || getNameF().equals(EMPTY_EQUIVALENCE))
        			&& getNameE() != null) {
        		return LABEL_MISSING_FRENCH + LABEL_QUOTE + getNameE() + LABEL_QUOTE;
        	}
            return getNameF();
        }

    	if((getNameE()==null || getNameE().length()==0 || getNameE().equals(EMPTY_EQUIVALENCE))
    			&& getNameF() != null) {
    		return LABEL_MISSING_ENGLISH + LABEL_QUOTE + getNameF() + LABEL_QUOTE;
    	}
        return getNameE();
        
    }
    
    /**
     * Gets the name in English.
     *
     * @return  the name in English.
     *
     * @see     #setNameE()
     */
    public String getNameE() {
        return nameE;
    }

    /**
     * Gets the name in French.
     *
     * @return  the name in French.
     *
     * @see     #setNameF()
     */
    public String getNameF() {
        return nameF;
    }
    
    /**
     * Gets the English short description used to represent this object in drop
     * down lists and other various interfaces.
     *
     * @return  the English specific short description.
     */
    public String getShortDescriptionE() {
        return getNameE();
    }

    /**
     * Gets the English short description used to represent this object in drop
     * down lists and other various interfaces.
     *
     * @return  the French specific short description.
     */
    public String getShortDescriptionF() {
        return getNameF();
    }

    /**
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Over-ridden to add additional values to the
     * end.
     *
     * @return  DOCUMENT ME!
     */
    public String getValuesAsString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(super.getValuesAsString());
        buffer.append(", nameE: ");
        buffer.append(getNameE());
        buffer.append(", nameF: ");
        buffer.append(getNameF());

        if (getDescriptionE() != null) {
            buffer.append(", descriptionE: ");
            buffer.append(getDescriptionE());
        }

        if (getDescriptionF() != null) {
            buffer.append(", descriptionF: ");
            buffer.append(getDescriptionF());
        }

        if (getCommentE() != null) {
            buffer.append(", commentE: ");
            buffer.append(getCommentE());
        }

        if (getCommentF() != null) {
            buffer.append(", commentF: ");
            buffer.append(getCommentF());
        }

        return buffer.toString();
    }

    /**
     * Sets the set of citations qualifying the English text.
     *
     * @param  newVal  the set of citations for the English text.
     *
     * @see    #getCitationsE()
     */
    public void setCitationsE(Set<Citation> newVal) {
        citationsE = newVal;
    }

    /**
     * Gets the set of citations qualifying the French text.
     *
     * @param  newVal  the set of citations for the French text.
     *
     * @see    #getCitationsF()
     */
    public void setCitationsF(Set<Citation> newVal) {
        citationsF = newVal;
    }

    /**
     * Sets the comment in English.
     *
     * @param  newVal  the comment in English
     *
     * @see    #getCommentE()
     */
    public void setCommentE(String newVal) {
        commentE = newVal;
    }

    /**
     * Gets the comment in French.
     *
     * @param  newVal  the comment in French
     *
     * @see    #getCommentF()
     */
    public void setCommentF(String newVal) {
        commentF = newVal;
    }

    /**
     * Sets the description in English.
     *
     * @param  newVal  the description in English
     *
     * @see    #getDescriptionE()
     */
    public void setDescriptionE(String newVal) {
        descriptionE = newVal;
    }

    /**
     * Sets the description in French.
     *
     * @param  newVal  the description in French
     *
     * @see    #getDescriptionF()
     */
    public void setDescriptionF(String newVal) {
        descriptionF = newVal;
    }

    /**
     * Sets the name in English.
     *
     * @param  newVal  the name in English
     *
     * @see    #getNameE()
     */
    public void setNameE(String newVal) {
        nameE = newVal;
    }

    /**
     * Sets the name in French.
     *
     * @param  newVal  the name in French
     *
     * @see    #getNameF()
     */
    public void setNameF(String newVal) {
        nameF = newVal;
    }

}
