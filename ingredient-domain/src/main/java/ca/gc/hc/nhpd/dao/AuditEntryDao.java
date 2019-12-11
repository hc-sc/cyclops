package ca.gc.hc.nhpd.dao;

import ca.gc.hc.nhpd.model.AuditEntry;
import ca.gc.hc.nhpd.util.PagedList;
import ca.gc.hc.nhpd.model.UserAccount;
import ca.gc.hc.nhpd.util.SqlUtil;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.HibernateException;
import java.util.Locale;

/*******************************************************************************
 * An object used to retrieve instances of AuditEntry from persistent store.
 * @see ca.gc.hc.nhpd.model.AuditEntry
 */
public class AuditEntryDao extends AbstractDao {
    
	private static final Logger log = Logger.getLogger(AuditEntryDao.class);
	
	public static boolean LOAD_INTO_MEMORY = true;
	
    /***************************************************************************
     * Generic Constructor.
     */
    public AuditEntryDao() {
        super(AuditEntry.class);
    }
    
	/***************************************************************************
	 * Flag determining if the list of audit entries for this DAO object should be
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
     * Gets a collection of all the Audit Entries in the persistent store.
     * Note that these are sorted by their codes.
     * @return all instances of the AuditEntry that are in the persistent store.
     * @throws HibernateException if a problem is encountered.
     */
    public List findAll() throws HibernateException {
        List order = new ArrayList();
        
        order.add(Order.asc("code"));
        return findByCriteria(null, order);
    }

    /***************************************************************************
     * Gets the AuditEntry with the passed ID from the persistent store.
     * Returns null if none is found.
     * @param id the unique key of the AuditEntry in the persistent store.
     * @param lock true if the corresponding record should automatically be
     *        locked from updates in the persistent store.
     * @return the AuditEntry, if any, with the passed ID in the persistent store.
     */
    public Object findById(Long id, boolean lock) {
    	try {
    	    return (AuditEntry)super.findByIdBase(id, lock);
    	} catch ( Exception e ) {
    		  e.printStackTrace();
    	}
    	return (Object)super.findByIdBase(id, lock);
    }
	
	/***************************************************************************
	 * Gets the Audit Entries with the passed constraints from the persistent
	 * store. Returns an empty List if none are found that match.
	 * @param accountName a partial name that these accounts must have. This may contain a
	 *        literal value to searched on, or a partial string that uses wild
	 *        card searching (see SqlUtil.createQueryExpression() for details).
	 *        This looks for all matches in the userAccount's name.
	 * @return the Audit Entries in the persistent store that match the passed
	 *         constraints.
	 */
	public PagedList findForFilter(String accountName, String sortColumn, int maxRows) {
		//String accName = SqlUtil.createQueryExpression(accountName).toLowerCase();
		StringBuffer queryText = new StringBuffer();
		List results;
		
		getSession().beginTransaction();
		if (accountName == null) {
			results = null;
			return new PagedList(results);
		}
		//need a nested select to support rownum check at end.
		queryText.append("SELECT * FROM (");
		queryText.append("SELECT {ae.*}");
		queryText.append(" FROM audit_entries ae JOIN user_accounts ua");
		queryText.append(" ON ae.useracc_id = ua.useracc_id" );

		if (accountName != null && !EMPTY_STRING.equals(accountName))
		{
			queryText.append(" WHERE LOWER(ua.useracc_account_name) like '%" + accountName.toLowerCase() + "%'");
		}
		
		if (sortColumn != null) {
			queryText.append(" ORDER BY " + sortColumn);
		}
		
		queryText.append(" ) WHERE ROWNUM <= " + maxRows);
		
		log.debug("Query: " + queryText);
		results = getSession().createSQLQuery(queryText.toString()).addEntity("ae", AuditEntry.class).list();
		getSession().flush();
		return new PagedList(results);
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
