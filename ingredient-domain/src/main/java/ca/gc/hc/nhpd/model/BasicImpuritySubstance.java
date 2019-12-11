package ca.gc.hc.nhpd.model;

import java.util.Set;

/*******************************************************************************
 * An object that represents a substance that only exists in the system as an
 * impurity. This contains basic information about the substance. It is used for
 * subclassing and doubles as a microbiological impurity substance, since that
 * only requires basic information.
 */
public class BasicImpuritySubstance extends PersistentObject
                                    implements ImpuritySubstance {
	private String commentE;
	private String commentF;
	private String descriptionE;
	private String descriptionF;
	private String authorizedNameE;
	private String authorizedNameF;
	private Set<TestMethod> purityTestMethods;

    /***************************************************************************
	 * Gets the comment in the language appropriate for the Locale.
	 * @return the locale-specific comment.
	 */
	public String getComment(){
        if (isLanguageFrench()) {
            return getCommentF();
        }
        return getCommentE();
	}

    /***************************************************************************
	 * Gets the comment in English.
	 * @return the comment in English.
	 * @see #setCommentE()
	 */
	public String getCommentE(){
		return commentE;
	}

    /***************************************************************************
	 * Gets the comment in French.
	 * @return the comment in French.
	 * @see #setCommentF()
	 */
	public String getCommentF(){
		return commentF;
	}

    /***************************************************************************
     * Gets the description in the language appropriate for the Locale.
     * @return the locale-specific description.
     */
    public String getDescription(){
        if (isLanguageFrench()) {
            return getDescriptionF();
        }
        return getDescriptionE();
    }

    /***************************************************************************
     */
	public String getDescriptionE(){
		return descriptionE;
	}

    /***************************************************************************
     */
	public String getDescriptionF(){
		return descriptionF;
	}

    /***************************************************************************
	 * Gets the authorized name in the language appropriate for the Locale.
	 * @return the locale-specific authorized name.
	 */
	public String getAuthorizedName(){
        if (isLanguageFrench()) {
            return getAuthorizedNameF();
        }
        return getAuthorizedNameE();
	}

    /***************************************************************************
	 * Gets the authorized name in English.
	 * @return the authorized name in English.
	 * @see #setAuthorizedNameE()
	 */
	public String getAuthorizedNameE(){
		return authorizedNameE;
	}

    /***************************************************************************
	 * Gets the authorized name in French.
	 * @return the authorized name in French.
	 * @see #setAuthorizedNameF()
	 */
	public String getAuthorizedNameF(){
		return authorizedNameF;
	}

	/***************************************************************************
	 * Gets the English short description used to represent this object in 
	 * drop down lists and other various interfaces. 
	 * @return the English specific short description.
	 */
	public String getShortDescriptionE() {
		return getAuthorizedNameE();
	}

	/***************************************************************************
	 * Gets the English short description used to represent this object in 
	 * drop down lists and other various interfaces. 
	 * @return the French specific short description.
	 */
	public String getShortDescriptionF() {
		return getAuthorizedNameF();
	}

    /***************************************************************************
     */
	public Set<TestMethod> getPurityTestMethods(){
		return purityTestMethods;
	}

    /***************************************************************************
	 * Sets the comment in English.
	 * @param newVal the comment in English
	 * @see #getCommentE()
	 * @param newVal
	 */
	public void setCommentE(String newVal){
		commentE = newVal;
	}

    /***************************************************************************
	 * Gets the comment in French.
	 * @param newVal the comment in French
	 * @see #getCommentF()
	 * @param newVal
	 */
	public void setCommentF(String newVal){
		commentF = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setDescriptionE(String newVal){
		descriptionE = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setDescriptionF(String newVal){
		descriptionF = newVal;
	}

    /***************************************************************************
	 * Gets the name in English.
	 * @return the authorized name in English.
	 * @see #setAuthorizedNameE()
	 * @param newVal
	 */
	public void setAuthorizedNameE(String newVal){
		authorizedNameE = newVal;
	}

    /***************************************************************************
	 * Sets the authorized name in French.
	 * @param newVal the authorized name in French
	 * @see #getAuthorizedNameF()
	 * @param newVal
	 */
	public void setAuthorizedNameF(String newVal){
		authorizedNameF = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setPurityTestMethods(Set<TestMethod> newVal){
		purityTestMethods = newVal;
	}

    /***************************************************************************
     * This provides a list of the instance variable values for this object, and
     * is called by the PersistentObject's toString(). Intended to provide
     * useful debugging information. Over-ridden to add additional values to the
     * end.
     */
    public String getValuesAsString(){
        StringBuffer buffer = new StringBuffer();
        
        buffer.append(super.getValuesAsString());
        buffer.append(", authorizedNameE: ");
        buffer.append(getAuthorizedNameE());
        buffer.append(", authorizedNameF: ");
        buffer.append(getAuthorizedNameF());
        buffer.append(", descriptionE: ");
        buffer.append(getDescriptionE());
        buffer.append(", descriptionF: ");
        buffer.append(getDescriptionF());
        if (getDescriptionE() != null) {
            buffer.append(", descriptionE: ");
            buffer.append(getDescriptionE());
        }
        if (getDescriptionE() != null) {
            buffer.append(", descriptionF: ");
            buffer.append(getDescriptionF());
        }
        buffer.append(", purityTestMethods: ");
        buffer.append(getPurityTestMethods());
        
        return buffer.toString();
    }
}