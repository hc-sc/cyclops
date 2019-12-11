package ca.gc.hc.nhpd.dao;

import ca.gc.hc.nhpd.model.ContextSensitiveCitations;
import ca.gc.hc.nhpd.model.PersistentObject;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;


/*******************************************************************************
 * An object used to retrieve instances of ContextSensitiveCitations from persistent store.
 *
 * @see  ca.gc.hc.nhpd.model.ContextSensitiveCitations
 */
public class ContextSensitiveCitationsDao extends AbstractDao {

    //~ Static fields/initializers ---------------------------------------------

    static private final Log log = LogFactory.getLog(
            ContextSensitiveCitationsDao.class);

    static public boolean LOAD_INTO_MEMORY = false;

    //~ public -----------------------------------------------------------------

    /**
     * Generic Constructor.
     */
    public ContextSensitiveCitationsDao() {
        super(ContextSensitiveCitations.class);
    }

    //~ public -----------------------------------------------------------------

    /**
     * Gets a collection of all the ContextSensitiveCitations in the persistent store.
     *
     * @return  all instances of the ContextSensitiveCitations that are in the persistent
     *          store.
     *
     * @throws  HibernateException  if a problem is encountered.
     */
    public List findAll() throws HibernateException {
        return findByCriteria(null, null);
    }

    /**
     * Gets the ContextSensitiveCitations object using the context object as the
     * key to get a handle on the resulting set. Returns an empty List if none
     * are found that match.
     *
     * @param   object  the context object used as the key to get the
     *                  contextSensitiveCitation.
     *
     * @return  the ContextSensitiveCitations associated to the context object.
     */
    public List findByContextObject(PersistentObject object) {

        // Should never happen...  but just in case.
        if (object == null) {
            return null;
        }

        StringBuffer queryText = new StringBuffer();
        List results;

        queryText.append("select ");
        queryText.append(" c.* ");
        queryText.append("from ");
        queryText.append("  context_sensitive_citations c ");
        queryText.append("where ");
        queryText.append("  c.context_object_id=" + object.getId() + " and ");
        queryText.append("  c.context_object_name='"
            + object.getSimpleClassName() + "'");

        log.debug("Query: " + queryText);

        results = getSession().createSQLQuery(queryText.toString()).addEntity(
                "i", getModelClass()).list();

        return results;
    }

    /**
     * Gets the ContextSensitiveCitations with the passed ID from the persistent store. Returns
     * null if none is found.
     *
     * @param   id    the unique key of the ContextSensitiveCitations in the persistent store.
     * @param   lock  true if the corresponding record should automatically be
     *                locked from updates in the persistent store.
     *
     * @return  the ContextSensitiveCitations, if any, with the passed ID in the persistent
     *          store.
     */
    public Object findById(Long id, boolean lock) {

        Object o = (Object) super.findByIdBase(id, lock);

        return o;
    }

    /**
     * Flag determining if the list of items for this DAO object should be
     * loaded into the ApplicationGlobal's resident memory. This is used for
     * system pick lists, and other code list objects. This method returns the
     * static value stored in the LOAD_INTO_MEMORY that should be located at the
     * top of the individual DAO classes. By default, the AbstractDAO ensures
     * that none of the individual DAO classes are loaded unless specifically
     * set.
     *
     * @return  LOAD_INTO_MEMORY static variable.
     */
    public boolean loadIntoMemory() {
        return LOAD_INTO_MEMORY;
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
