package ca.gc.hc.nhpd.dao;

import ca.gc.hc.nhpd.exception.IngredientsException;
import ca.gc.hc.nhpd.model.ApplicationText;
import ca.gc.hc.nhpd.model.HomeopathicSubstance;
import ca.gc.hc.nhpd.model.Ingredient;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.HibernateException;
import java.util.Locale;
import ca.gc.hc.nhpd.util.PagedList;
import ca.gc.hc.nhpd.util.SqlUtil;

/*******************************************************************************
 * An object used to retrieve instances of ApplicationText from persistent store.
 * @see ca.gc.hc.nhpd.model.ApplicationText
 */
public class ApplicationTextDao extends AbstractDao {

	private static final Logger log = Logger.getLogger(ApplicationTextDao.class);

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
	public ApplicationTextDao() {
		super(ApplicationText.class);
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
	public Object findById(Long id, boolean lock) {
		try {
			return (ApplicationText)super.findByIdBase(id, lock);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return (Object)super.findByIdBase(id, lock);
	}
	
	
	/***************************************************************************
	 * Returns a single ApplicationText object via its code.
	 * @param code The ApplicationText language neutral code used to uniquely
	 * 		  identify the ApplicationText object/row in the table 
	 */

	public ApplicationText findByCode(String code) {
		StringBuffer queryText = new StringBuffer();
		queryText.append("FROM ApplicationText at ");
		queryText.append("WHERE LOWER(at.code) = '" + code.trim() + "'");
		List results = getSession().createQuery(queryText.toString()).list();
		if (results == null || results.size() < 1) {
			return null;
		}
		return (ApplicationText)results.get(0);
	}

	/***************************************************************************
	 * Gets the Application Text with the passed constraints from the persistent
	 * store. Returns an empty List if none are found that match.
	 * @param name a partial name that these users must have. This may contain a
	 *        literal value to searched on, or a partial string that uses wild
	 *        card searching (see SqlUtil.createQueryExpression() for details).
	 *        This looks for all matches in the unit's name, as well as in 
	 *        all related common names and CAS entry codes (logical OR).
	 * @param usage when applicable, the results are filtered to only include
	 *        Units with the passed role subclass name. If null or -1,
	 *        this constraint is not applied.
	 * @param language the language to sort on (defaults to English).
	 * @return the Units in the persistent store that match the passed
	 *         constraints.
	 */

	public PagedList findForFilter(String name, String language) {

		String nameExpression = SqlUtil.createQueryExpression(name);
		StringBuffer queryText = new StringBuffer();
		List results;

		queryText.append("SELECT DISTINCT at.* ");
		queryText.append("FROM application_text at ");

		// Search units accross the Code, French and English names.
		nameExpression = nameExpression.toUpperCase();
		queryText.append("WHERE (UPPER(at.apptext_code)" + nameExpression);
		queryText.append("  OR UPPER(at.apptext_text_eng)" + nameExpression);
		queryText.append("  OR UPPER(at.apptext_text_fr)" + nameExpression);

		queryText.append("  ) ");
		if (Locale.FRENCH.getLanguage().equals(language)) {
			queryText.append("  ORDER BY UPPER(at.apptext_code)");
		} else {
			queryText.append("  ORDER BY UPPER(at.apptext_code)");
		}

		results = getSession().createSQLQuery(queryText.toString()).
		addEntity("i", getModelClass()).list();

		return new PagedList(results);
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
