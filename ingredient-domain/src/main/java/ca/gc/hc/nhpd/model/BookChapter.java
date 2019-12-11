package ca.gc.hc.nhpd.model;

/*******************************************************************************
 * An object that represents the chapter in a book that may be cited as a source
 * of information.
 * @version 1.0
 */
public class BookChapter extends PublishedWork {
    private static final String TYPE_E = "Book Chapter";
    private static final String TYPE_F = "Chapitre du livre";
    private String bookTitleE;
	private String bookTitleF;
    private String edition;
    private String editor;
    private String pageNumbers;

    /***************************************************************************
     */
    public String getBookTitleE(){
        return bookTitleE;
    }

	/***************************************************************************
	 */
	public String getBookTitleF(){
		return bookTitleF;
	}

	/***************************************************************************
	 * Gets the BookTitle in the language appropriate for the Locale.
	 * @return the locale-specific book title.
	 */
	public String getBookTitle(){
		if (isLanguageFrench()) {
			return getBookTitleF();
		}
		return getBookTitleE();
	}
	
    /***************************************************************************
     */
    public String getEdition(){
        return edition;
    }

    /***************************************************************************
     */
    public String getEditor(){
        return editor;
    }

    /***************************************************************************
     * Gets the type in the language appropriate for the Locale.
     * @return the locale-specific type.
     */
    public String getType(){
        if (isLanguageFrench()) {
            return TYPE_F;
        }
        return TYPE_E;
    }

    /***************************************************************************
     */
    public String getPageNumbers(){
        return pageNumbers;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setBookTitleE(String newVal){
        bookTitleE = newVal;
    }

	/***************************************************************************
	 * @param newVal
	 */
	public void setBookTitleF(String newVal){
		bookTitleF = newVal;
	}

    /***************************************************************************
     * @param newVal
     */
    public void setEdition(String newVal){
        edition = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setEditor(String newVal){
        editor = newVal;
    }

    /***************************************************************************
     * @param newVal
     */
    public void setPageNumbers(String newVal){
        pageNumbers = newVal;
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
        buffer.append(", bookTitleE: ");
        buffer.append(getBookTitleE());
		buffer.append(", bookTitleF: ");
		buffer.append(getBookTitleF());
        buffer.append(", edition: ");
        buffer.append(getEdition());
        buffer.append(", editor: ");
        buffer.append(getEditor());
        buffer.append(", pageNumbers: ");
        buffer.append(getPageNumbers());
        
        return buffer.toString();
    }
}