package ca.gc.hc.nhpd.dao;

import ca.gc.hc.nhpd.model.OrganismMonographEntry;
import java.util.List;
import org.hibernate.HibernateException;


/*******************************************************************************
 * An object used to retrieve instances of OrganismMonographEntry from
 * persistent store.
 *
 * @see  ca.gc.hc.nhpd.model.OrganismMonographEntry
 */
public class OrganismMonographEntryDao extends AbstractDao {

    //~ public -----------------------------------------------------------------

    /**
     * Generic Constructor.
     */
    public OrganismMonographEntryDao() {
        super(OrganismMonographEntry.class);
    }

    //~ public -----------------------------------------------------------------

    /**
     * Gets a collection of all the OrganismMonographEntry in the persistent
     * store.
     *
     * @return  all instances of the OrganismMonographEntry that are in the
     *          persistent store.
     *
     * @throws  HibernateException  if a problem is encountered.
     */
    public List findAll() throws HibernateException {
        return findByCriteria(null, null);
    }

    /**
     * Gets the OrganismMonographEntry with the passed ID from the persistent
     * store. Returns null if none is found.
     *
     * @param   id    the unique key of the OrganismMonographEntry in the
     *                persistent store.
     * @param   lock  true if the corresponding record should automatically be
     *                locked from updates in the persistent store.
     *
     * @return  the Monograph, if any, with the passed ID in the persistent
     *          store.
     */
    public Object findById(Long id, boolean lock) {
        return super.findByIdBase(id, lock);
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
