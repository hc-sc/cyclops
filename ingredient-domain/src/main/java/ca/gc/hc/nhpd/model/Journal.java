package ca.gc.hc.nhpd.model;

import java.util.Date;

/*******************************************************************************
 * An object that represents a journal that may be cited as a source of
 * information. These are populated by a process that extracts them from pubmed.
 * @version 1.0
 */
public class Journal extends PersistentObject {
    
	private Date accessDate;
	private String additionalInformationSource;
	private String author;
	private String category;
	private String description;
	private String essn;
	private String frequency;
	private String indexDocument;
	private String indexHistory;
	private String issn;
	private String isoAbbreviation;
	private String language;
	private String notes;
	private String publicationDates;
	private String publicationTypes;
	private String publisher;
	private String pubmedId;
	private String titleAbbreviation;
	private String titles;

    /***************************************************************************
     */
	public Date getAccessDate(){
		return accessDate;
	}

    /***************************************************************************
     */
	public String getAdditionalInformationSource(){
		return additionalInformationSource;
	}

    /***************************************************************************
     */
	public String getAuthor(){
		return author;
	}

    /***************************************************************************
     */
	public String getCategory(){
		return category;
	}

    /***************************************************************************
     */
	public String getDescription(){
		return description;
	}

    /***************************************************************************
     */
	public String getEssn(){
		return essn;
	}

    /***************************************************************************
     */
	public String getFrequency(){
		return frequency;
	}

    /***************************************************************************
     */
	public String getIndexDocument(){
		return indexDocument;
	}

    /***************************************************************************
     */
	public String getIndexHistory(){
		return indexHistory;
	}

    /***************************************************************************
     */
	public String getIssn(){
		return issn;
	}

    /***************************************************************************
     */
	public String getIsoAbbreviation(){
		return isoAbbreviation;
	}

    /***************************************************************************
     */
	public String getLanguage(){
		return language;
	}

    /***************************************************************************
     */
	public String getNotes(){
		return notes;
	}

    /***************************************************************************
     */
	public String getPublicationDates(){
		return publicationDates;
	}

    /***************************************************************************
     */
	public String getPublicationTypes(){
		return publicationTypes;
	}

    /***************************************************************************
     */
	public String getPublisher(){
		return publisher;
	}

    /***************************************************************************
     */
	public String getPubmedId(){
		return pubmedId;
	}

    /***************************************************************************
     */
	public String getTitleAbbreviation(){
		return titleAbbreviation;
	}

    /***************************************************************************
     */
	public String getTitles(){
		return titles;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setAccessDate(Date newVal){
		accessDate = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setAdditionalInformationSource(String newVal){
		additionalInformationSource = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setAuthor(String newVal){
		author = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setCategory(String newVal){
		category = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setDescription(String newVal){
		description = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setEssn(String newVal){
		essn = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setFrequency(String newVal){
		frequency = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setIndexDocument(String newVal){
		indexDocument = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setIndexHistory(String newVal){
		indexHistory = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setIssn(String newVal){
		issn = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setIsoAbbreviation(String newVal){
		isoAbbreviation = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setLanguage(String newVal){
		language = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setNotes(String newVal){
		notes = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setPublicationDates(String newVal){
		publicationDates = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setPublicationTypes(String newVal){
		publicationTypes = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setPublisher(String newVal){
		publisher = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setPubmedId(String newVal){
		pubmedId = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setTitleAbbreviation(String newVal){
		titleAbbreviation = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setTitles(String newVal){
		titles = newVal;
	}

    /***************************************************************************
     * Gets the short description in the language appropriate for the Locale.
     * 
     * @return the locale-specific name.
     */
    public String getShortDescription(){
        if (isLanguageFrench()) {
            return getShortDescriptionF();
        }
        return getShortDescriptionE();
    }
    
    /***************************************************************************
     * Gets the English short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the English specific short description.
     */
    public String getShortDescriptionE() {
        return getTitles();
    }

    /***************************************************************************
     * Gets the French short description used to represent this object in 
     * drop down lists and other various interfaces. 
     * @return the French specific short description.
     */
    public String getShortDescriptionF() {
        return getTitles();
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
        buffer.append(", titleAbbreviation: ");
        buffer.append(getTitleAbbreviation());
        buffer.append(", titles: ");
        buffer.append(getTitles());
        buffer.append(", author: ");
        buffer.append(getAuthor());
        buffer.append(", publisher: ");
        buffer.append(getPublisher());
        buffer.append(", description: ");
        buffer.append(getDescription());
        buffer.append(", notes: ");
        buffer.append(getNotes());
        buffer.append(", additionalInformationSource: ");
        buffer.append(getAdditionalInformationSource());
        buffer.append(", language: ");
        buffer.append(getLanguage());
        buffer.append(", frequency: ");
        buffer.append(getFrequency());
        buffer.append(", publicationTypes: ");
        buffer.append(getPublicationTypes());
        buffer.append(", publicationDates: ");
        buffer.append(getPublicationDates());
        buffer.append(", category: ");
        buffer.append(getCategory());
        buffer.append(", indexDocument: ");
        buffer.append(getIndexDocument());
        buffer.append(", indexHistory: ");
        buffer.append(getIndexHistory());
        buffer.append(", essn: ");
        buffer.append(getEssn());
        buffer.append(", issn: ");
        buffer.append(getIssn());
        buffer.append(", isoAbbreviation: ");
        buffer.append(getIsoAbbreviation());
        buffer.append(", pubmedId: ");
        buffer.append(getPubmedId());
        buffer.append(", accessDate: ");
        buffer.append(getAccessDate());
        buffer.append(", creationDate: ");
        buffer.append(getCreationDate());
        buffer.append(", lastUpdateDate: ");
        buffer.append(getLastUpdateDate());
        if (getLastUpdateAccount() != null) {
            buffer.append(getLastUpdateAccount().getAccountName());
        }
        
        return buffer.toString();
    }
    
    
}