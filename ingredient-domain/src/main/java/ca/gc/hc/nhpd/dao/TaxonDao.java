package ca.gc.hc.nhpd.dao;

import ca.gc.hc.nhpd.model.Taxon;
import ca.gc.hc.nhpd.util.SqlUtil;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.HibernateException;

/*******************************************************************************
 * An object used to retrieve instances of Taxon from persistent store.
 * @see ca.gc.hc.nhpd.model.Taxon
 */
public class TaxonDao extends AbstractDao {
	
	public static boolean LOAD_INTO_MEMORY = true;
	
    /***************************************************************************
     * Generic Constructor.
     */
    public TaxonDao() {
        super(Taxon.class);
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
     * Gets a collection of all the Taxa in the persistent store.
     * Note that these are sorted by their English names.
     * @return all instances of the Taxa that are in the persistent store.
     * @throws HibernateException if a problem is encountered.
     */
    public List findAll() throws HibernateException {
        List order = new ArrayList();

        order.add(Order.asc("name"));
        return findByCriteria(null, order);
    }

    /***************************************************************************
     * Gets the Taxon with the passed ID from the persistent store.
     * Returns null if none is found.
     * @param id the unique key of the Taxon in the persistent store.
     * @param lock true if the corresponding record should automatically be
     *        locked from updates in the persistent store.
     * @return the Taxon, if any, with the passed ID in the persistent store.
     */
    public Object findById(Long id, boolean lock) {
        return (Object)super.findByIdBase(id, lock);
    }
    
    /***************************************************************************
     * Defines the custom SQL query required to return a record set from the
     * database. This is used to populate a list of simplified
     * PersistentObjectSearchResults objects used in place of the model objects
     * for list display purposes, search purposes, etc.
     * 
     * See AbstractDao for a complete explanation.
     */
    public StringBuffer getSearchResultSQLStatement(boolean isFrench,
            String searchString) {
        
        StringBuffer queryText = new StringBuffer();

        // need a nested select to support rownum check at end.
        queryText.append("SELECT * from (");
        queryText.append("SELECT DISTINCT ");
        queryText.append("  t.taxon_id, ");
        queryText.append("  t.taxon_name ");
        queryText.append("FROM ");
        queryText.append("  taxa t ");

        // NB. Search terms are language specific
        if (searchString != null && !searchString.equals(EMPTY_STRING)) {
            queryText.append("WHERE ");
            queryText.append("  LOWER(t.taxon_name) ");
            String likeClause = (SqlUtil.createQueryExpression(searchString));
            queryText.append(likeClause);
        }

        queryText.append("ORDER BY LOWER(t.taxon_name) ");
        
        queryText.append(")");

        return queryText;
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
