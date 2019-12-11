package ca.gc.hc.nhpd.dao;

import ca.gc.hc.nhpd.exception.IngredientsException;
import ca.gc.hc.nhpd.model.ApplicationProperty;
import ca.gc.hc.nhpd.model.HomeopathicSubstance;
import ca.gc.hc.nhpd.model.Ingredient;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.HibernateException;
import java.util.Locale;
import ca.gc.hc.nhpd.util.PagedList;
import ca.gc.hc.nhpd.util.SqlUtil;

/*******************************************************************************
 * An object used to retrieve instances of ApplicationText from persistent store.
 * @see ca.gc.hc.nhpd.model.ApplicationText
 */
public class ApplicationPropertyDao extends AbstractDao {

	private static final Logger log = Logger.getLogger(ApplicationPropertyDao.class);

	public static boolean LOAD_INTO_MEMORY = true;

	/***************************************************************************
	 * Columns used in building the Persistent Object Search Result by the 
	 * getSearchResultSQLStatement method in the AbstractDao.
	 */
	private final static String TABLE_NAME="application_text";
	private final static String ID_COLUMN_NAME="apptext_id";
	private final static String FRENCH_COLUMN_NAME="apptext_code";
	private final static String ENGLISH_COLUMN_NAME="apptext_code";
	private final static String DISCRIMINATOR=null;

	/***************************************************************************
	 * Generic Constructor.
	 */
	public ApplicationPropertyDao() {
		super(ApplicationProperty.class);
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
	 * Gets a collection of all the ApplicationText entries in the persistent
	 * store.
	 * @return all instances of ApplicationText that are in the persistent store.
	 * @throws HibernateException if a problem is encountered.
	 */
	public List findAll() throws HibernateException {
		return findByCriteria(null, null);
	}

	/***************************************************************************
	 * Gets the ApplicationText with the passed ID from the persistent store.
	 * Returns null if none is found.
	 * @param id the unique key of the Units in the persistent store.
	 * @param lock true if the corresponding record should automatically be
	 *        locked from updates in the persistent store.
	 * @return the Units, if any, with the passed ID in the persistent store.
	 */
	public ApplicationProperty findByKey(String key) throws IngredientsException {
        
        StringBuffer sb = new StringBuffer();
        sb.append("select * from application_properties where key='" + key + "'");
        
        List<ApplicationProperty> applicationProperties = getSession().createSQLQuery(sb.toString()).addEntity(ApplicationProperty.class).list();
        
        if (applicationProperties == null || applicationProperties.size() == 0) {
            return null;
        }
        
        if (applicationProperties.size() == 1) {
            return applicationProperties.get(0);
        }
        
        throw new IngredientsException("Found more than one Application Property with the same key!");
        
	}

	/**
	 * Getter that returns the SQL discriminator used by this object.  This method
	 * exposes the discriminator for use in building the searchResultsSqlStatement
	 * in the AbstractDao and allows for the caching of PersistentObjectSearchResults.
	 */
	@Override
	protected String getDiscriminator() {
		return DISCRIMINATOR;
	}

	/**
	 * Getter that returns the SQL English column used by this object.  This method
	 * exposes the table name for use in building the searchResultsSqlStatement
	 * in the AbstractDao and allows for the caching of PersistentObjectSearchResults.
	 */
	@Override
	protected String getEnglishColumnName() {
		return ENGLISH_COLUMN_NAME;
	}

	/**
	 * Getter that returns the SQL French column used by this object.  This method
	 * exposes the table name for use in building the searchResultsSqlStatement
	 * in the AbstractDao and allows for the caching of PersistentObjectSearchResults.
	 */
	@Override
	protected String getFrenchColumnName() {
		return FRENCH_COLUMN_NAME;
	}

	/**
	 * Getter that returns the SQL id column used by this object.  This method
	 * exposes the table name for use in building the searchResultsSqlStatement
	 * in the AbstractDao and allows for the caching of PersistentObjectSearchResults.
	 */
	@Override
	protected String getIdColumnName() {
		return ID_COLUMN_NAME;
	}

	/**
	 * Getter that returns the SQL table name used by this object.  This method
	 * exposes the table name for use in building the searchResultsSqlStatement
	 * in the AbstractDao and allows for the caching of PersistentObjectSearchResults.
	 */
	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

}
