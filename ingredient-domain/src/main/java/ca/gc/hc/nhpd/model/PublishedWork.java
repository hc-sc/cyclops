package ca.gc.hc.nhpd.model;

/*******************************************************************************
 * An abstract object extended by classes that represent publications that may be
 * cited as a source of information.
 * @version 1.0
 */
public abstract class PublishedWork extends Citation implements BibliographicalEntry {
    private String place;
    private String publisherE;
    private String publisherF;

    /***************************************************************************
     */
    public String getPlace(){
        return place;
    }

    /***************************************************************************
     */
    public String getPublisherE(){
        return publisherE;
    }

	/***************************************************************************
	 */
	public String getPublisherF(){
		return publisherF;
	}

	/***************************************************************************
	 * Gets the publisher in the language appropriate for the Locale.
	 * @return the locale-specific publisher.
	 */
	public String getPublisher(){
		if (isLanguageFrench()) {
			return getPublisherF();
		}
		return getPublisherE();
	}

    /***************************************************************************
     * @param newVal
     */
    public void setPlace(String newVal){
        place = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setPublisherE(String newVal){
        publisherE = newVal;
    }

	/***************************************************************************
	 * @param newVal
	 */
	public void setPublisherF(String newVal){
		publisherF = newVal;
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
        buffer.append(", place: ");
        buffer.append(getPlace());
        buffer.append(", publisherE: ");
        buffer.append(getPublisherE());
		buffer.append(", publisherF: ");
		buffer.append(getPublisherF());
        
        return buffer.toString();
    }
    
    /**
      * This returns this object as a bibliographical entry for display purposes.
     * Note that this will only return the "full text" if it is populated, since
     * it does not know how to create a string from its components.
      * @return this object as a bibliographical entry for display.
      */
    @Override
    public String getValueInBibliographicalFormat(){
        return getFullText();
    }

}