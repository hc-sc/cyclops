package ca.gc.hc.nhpd.dao;

import java.util.List;

import ca.gc.hc.nhpd.model.Attachment;
import org.hibernate.SQLQuery;

/*******************************************************************************
 * An object used to retrieve instances of Attachment from persistent store.
 * @see ca.gc.hc.nhpd.model.Attachment
 */
public class AttachmentDao extends AbstractDao {

	public static boolean LOAD_INTO_MEMORY = true;
	
    // Used in Hibernate Prepared Call for getting attachments by name. 
    private static final String QS_ATTACHMENT_BY_NAME = "select * from attachments where attachment_name_eng = :name";
    private static final String QS_ATTACHMENT_BY_NAME_KEY = "name";
    
    /***************************************************************************
     * Generic Constructor.
     */
    public AttachmentDao() {
        super(Attachment.class);
    }

	/***************************************************************************
	 * Flag determining if the list of items for this DAO object should be
	 * loaded into the ApplicationGlobal's resident memory.  This is used for
	 * system pick lists, and other code list objects.  This method returns the
	 * static value stored in the LOAD_INTO_MEMORY that should be located at
	 * the top of the individual DAO classes.  By default, the AbstractDAO
	 * ensures that none of the individual DAO classes are loaded unless 
	 * specifically set.
	 * @return LOAD_INTO_MEMORY static variable.
	 */
	public boolean loadIntoMemory() {
		return LOAD_INTO_MEMORY;
	}

    /***************************************************************************
     * Gets the Attachment with the passed ID from the persistent store. Returns
     * null if none is found.
     * @param id the unique key of the Attachment in the persistent store.
     * @param lock true if the corresponding record should automatically be
     *        locked from updates in the persistent store.
     * @return the Attachment, if any, with the passed ID in the persistent store.
     */
    public Object findById(Long id, boolean lock) {
        return (Object)super.findByIdBase(id, lock);
    }
    
    /***************************************************************************
     * Gets the Attachment using the English name from the persistent store. 
     * Returns a null if none is found.
     * @param name in English of the Attachment in the persistent store.
     * @return the Attachment, if any.
     */    
    public Attachment findByName(String name) {
        SQLQuery query = getSession().createSQLQuery(QS_ATTACHMENT_BY_NAME);
        query.setString(QS_ATTACHMENT_BY_NAME_KEY, name);
        List<Attachment> list = query.addEntity(Attachment.class).list();
        if (list != null && list.get(0) != null) {
            return list.get(0);
        }
        return null;
    }
    
    /**
     * Getter that returns the SQL discriminator used by this object.  This method
     * exposes the discriminator for use in building the searchResultsSqlStatement
     * in the AbstractDao and allows for the caching of PersistentObjectSearchResults.
     */
    @Override
    protected String getDiscriminator() {
        return null;
    }
    
    /**
     * Getter that returns the SQL English column used by this object.  This method
     * exposes the table name for use in building the searchResultsSqlStatement
     * in the AbstractDao and allows for the caching of PersistentObjectSearchResults.
     */
    @Override
    protected String getEnglishColumnName() {
        return null;
    }

    /**
     * Getter that returns the SQL French column used by this object.  This method
     * exposes the table name for use in building the searchResultsSqlStatement
     * in the AbstractDao and allows for the caching of PersistentObjectSearchResults.
     */
    @Override
    protected String getFrenchColumnName() {
        return null;
    }

    /**
     * Getter that returns the SQL id column used by this object.  This method
     * exposes the table name for use in building the searchResultsSqlStatement
     * in the AbstractDao and allows for the caching of PersistentObjectSearchResults.
     */
    @Override
    protected String getIdColumnName() {
        return null;
    }
    
    /**
     * Getter that returns the SQL table name used by this object.  This method
     * exposes the table name for use in building the searchResultsSqlStatement
     * in the AbstractDao and allows for the caching of PersistentObjectSearchResults.
     */
    @Override
    protected String getTableName() {
        return null;
    }

}
