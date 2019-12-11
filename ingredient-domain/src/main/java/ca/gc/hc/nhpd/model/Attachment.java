package ca.gc.hc.nhpd.model;

import ca.gc.hc.nhpd.conf.SpringContext;
import ca.gc.hc.nhpd.util.HibernateUtil;
import ca.gc.hc.nhpd.util.ImageUtil;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;

/*******************************************************************************
 * An object that includes the information about a file (document or image),
 * including the content of the file itself. Note that when these items are
 * large, they are dealt with in a transient way - as streams to minimize the
 * memory footprint. Smaller ones may be cached in memory to minimize the trips
 * to persistent store.
 */
public class Attachment extends PersistentObject {
    private static final Log LOG = LogFactory.getLog(Attachment.class);

    private final static Integer MAX_WIDTH = 500;
    
    private boolean cacheable;
    private byte[] contentCache;
    private Blob contentBlob;
    private String descriptionE;
    private String descriptionF;
    private Integer height;
    private String mimeType;
    private String nameE;
    private String nameF;
    private Integer size;
    private Integer width;
    
    /***************************************************************************
     * Don't invoke this. Used by Hibernate only.
     */
    public Blob getContentBlob() {
        return contentBlob;
    }

    /***************************************************************************
     * Gets the content of the attachment as an InputStream.
     * @return the content of the attachment as an InputStream.
     * @see #setContentFromInputStream()
     */
    public InputStream getContentInputStream(){
        if (contentCache == null && contentBlob != null && isCacheable()) {
            try {
                contentCache = ImageUtil.toByteArray(contentBlob);
            } catch (Exception e) {
                LOG.error("Problem getting content for '" + getId() + "': " + e);
            }
        }
        if (contentCache != null) {
            return new ByteArrayInputStream(contentCache);
        }
        if (contentBlob != null) {
            try {
                return contentBlob.getBinaryStream();
            } catch (SQLException e) {
                LOG.error("Problem getting content for '" + getId() + "': " + e);
            }
        }
        
        return null;
    }
    
	/***************************************************************************
	 * Gets the content of the attachment as an byte array.
	 * @return the content of the attachment as an byte array.
	 * @see #setContentFromInputStream()
	 */
	public byte[] getContentBytes(){
		if (contentCache == null && contentBlob != null && isCacheable()) {
			try {
				contentCache = ImageUtil.toByteArray(contentBlob);
			} catch (Exception e) {
				LOG.error("Problem getting content for '" + getId() + "': " + e);
			}
		}
		return contentCache;
	}    

    /***************************************************************************
     * Gets the description in English.
     * @return the description in English.
     * @see #setDescriptionE()
     */
    public String getDescriptionE(){
        return descriptionE;
    }

    /***************************************************************************
     * Gets the description in French.
     * @return the description in French.
     * @see #setDescriptionF()
     */
    public String getDescriptionF(){
        return descriptionF;
    }

    public String getDescription(){
        if (isLanguageFrench()) {
            return getDescriptionF();
        }
        return getDescriptionE();
    }
    
    /***************************************************************************
     * Gets the height of the attachment in pixels. This is only applicable to
     * images.
     * @return the height of the image.
     * @see #setHeight()
     */
    public Integer getHeight(){
        return height;
    }
    
    /***************************************************************************
     * Gets a formulated height for the image based on any adjustments that
     * had to be made to the width.  For example, if we had to shring the 
     * image in order to fit the MAX_WIDTH then the display height will be 
     * proportionally altered.
     * 
     * @return the proportional height of the image based on the width.
     * 
     * @see #getDisplayWidth()
     */
    public Integer getDisplayHeight() {
        if (getWidth() > MAX_WIDTH && getHeight() != null && getWidth() != null && getWidth() != 0) {
            Double newHeight = getHeight().doubleValue() * (MAX_WIDTH / getWidth().doubleValue()); 
            return newHeight.intValue();
        }
        return height;
    }

    /***************************************************************************
     * Gets the mime type of the attachment. This indicates to the browser what
     * type of format it is receiving (e.g. jpeg, gif, doc, pdf, etc.).
     * @return the mime type of the attachment.
     * @see #setMimeType()
     */
    public String getMimeType(){
        return mimeType;
    }

    /***************************************************************************
     * Gets the name in the language appropriate for the Locale.
     * @return the locale-specific name.
     */
    public String getName(){
        if (isLanguageFrench()) {
            return getNameF();
        }
        return getNameE();
    }

    /***************************************************************************
     * Gets the name in English.
     * @return the name in English.
     * @see #setNameE()
     */
    public String getNameE(){
        return nameE;
    }

    /***************************************************************************
     * Gets the name in French.
     * @return the name in French.
     * @see #setNameF()
     */
    public String getNameF(){
        return nameF;
    }

    /***************************************************************************
     * Gets the size of the attachment (number of bytes). This indicates to the
     * browser how much data to expect.
     * @return the size of the attachment.
     * @see #setSize()
     */
    public Integer getSize(){
        return size;
    }
    
    /***************************************************************************
     * Gets the size of the attachment (number of kilobytes). This indicates to the
     * browser how much data to expect.
     * @return the size of the attachment in kilobytes.
     * @see #setSize()
     */
    public Integer getSizeInKilobyte() {
        return size / 1024;
    }

    /***************************************************************************
     * Gets the width of the attachment in pixels. This is only applicable to
     * images.
     * @return the width of the image.
     * @see #setWidth()
     */
    public Integer getWidth(){
        return width;
    }

    /***************************************************************************
     * Gets a formulated width for the image based on a maximum size.  If the 
     * width exceeds the maximum size, then the image width is set to the 
     * maximum size.
     * 
     * @return the display width for the image.
     * 
     * @see #getDisplayWidth()
     */
    public Integer getDisplayWidth() {
        // limit the width of the image.
        if (width > MAX_WIDTH) {
            return MAX_WIDTH;
        }
        
        return width;
    }
    
    /***************************************************************************
     * Gets whether this attachment should be cached.
     * @return true if this attachment should be cached.
     * @see #setCacheable()
     */
    public boolean isCacheable(){
        return cacheable;
    }

    /***************************************************************************
     * Sets whether this attachment should be cached.
     * @param aBoolean true if this attachment should be cached.
     * @see #isCacheable()
     */
    public void setCacheable(boolean aBoolean){
        cacheable = aBoolean;
    }

    /***************************************************************************
     * Don't invoke this. Used by Hibernate only.
     * @param newVal
     */
    public void setContentBlob(Blob newVal) {
        contentBlob = newVal;
    }

    /***************************************************************************
     * Sets the content of the attachment from the passed InputStream.
     * @param inStream the InputStream to load the content of the attachment
     *        from.
     * @see #getContentInputStream()
     */
    public void setContentFromInputStream(InputStream inStream, long length){
    	// Session session = HibernateUtil.getCurrentSession();
    	Session session = SpringContext.getSession();
    	
        contentBlob = Hibernate.getLobCreator(session).createBlob(inStream, length);
        contentCache = null; //Clear out the previous value, if any
    }
    
	/***************************************************************************
	 * Sets the content of the attachment from the passed byte array.
	 * @param bytes the byte[] to load the content of the attachment
	 *        from.
	 * @see #getContentFromBytes()
	 */
	public void setContentFromBytes(byte[] bytes){
        // Session session = HibernateUtil.getCurrentSession();
        Session session = SpringContext.getSession();
        
        contentBlob = Hibernate.getLobCreator(session).createBlob(bytes);
		contentCache = null; //Clear out the previous value, if any
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
     * Sets the height of the attachment in pixels. This is only applicable to
     * images.
     * @param anInteger the height of the image.
     * @see #getHeight()
     */
    public void setHeight(Integer anInteger){
        height = anInteger;
    }

    /***************************************************************************
     * Sets the mime type of the attachment. This indicates to the browser what
     * type of format it is receiving (e.g. jpeg, gif, doc, pdf, etc.).
     * @param aString the mime type of the attachment.
     * @see #getMimeType()
     */
    public void setMimeType(String aString){
        mimeType = aString;
    }

    /***************************************************************************
     * Gets the name in English.
     * @return the name in English.
     * @see #setNameE()
     */
    public void setNameE(String newVal){
        nameE = newVal;
    }

    /***************************************************************************
     * Sets the name in French.
     * @param newVal the name in French
     * @see #getNameF()
     */
    public void setNameF(String newVal){
        nameF = newVal;
    }

    /***************************************************************************
     * Sets the size of the attachment (number of bytes). This indicates to the
     * browser how much data to expect.
     * @param anInteger the size of the attachment.
     * @see #getSize()
     */
    public void setSize(Integer anInteger){
        size = anInteger;
    }

    /***************************************************************************
     * Sets the width of the attachment in pixels. This is only applicable to
     * images.
     * @param anInteger the width of the image.
     * @see #getWidth()
     */
    public void setWidth(Integer anInteger){
        width = anInteger;
    }

    /**
     * Clear contentCashe byte[]
     *
     */
    public void clearContentCache(){
    	contentCache = null;
    }
}
