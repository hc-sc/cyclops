package ca.gc.hc.nhpd.dao;

import ca.gc.hc.nhpd.model.TraditionalParadigm;
import ca.gc.hc.nhpd.util.PagedList;
import ca.gc.hc.nhpd.util.SqlUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;

/*******************************************************************************
 * An object used to retrieve instances of TraditionalParadigm from persistent store.
 * @see ca.gc.hc.nhpd.model.TraditionalParadigm
 */
public class TraditionalParadigmDao extends AbstractDao {	

	private static final Logger log = Logger.getLogger(TraditionalParadigmDao.class);
	
	public static boolean LOAD_INTO_MEMORY = true;
	
    /***************************************************************************
     * Generic Constructor.
     */
    public TraditionalParadigmDao() {
        super(TraditionalParadigm.class);
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
     * Gets a collection of all the TraditionalParadigm in the persistent store.
     * Note that these are sorted by their codes.
     * @return all instances of the TraditionalParadigm that are in the persistent store.
     * @throws HibernateException if a problem is encountered.
     */
    public List findAll() throws HibernateException {
        List order = new ArrayList();

        order.add(Order.asc("nameE"));
		return findByCriteria(null, order);
    }

    /***************************************************************************
     * Gets the TraditionalParadigm with the passed ID from the persistent store.
     * Returns null if none is found.
     * @param id the unique key of the TraditionalParadigm in the persistent store.
     * @param lock true if the corresponding record should automatically be
     *        locked from updates in the persistent store.
     * @return the TraditionalParadigm, if any, with the passed ID in the persistent store.
     */
    public Object findById(Long id, boolean lock) {
        return (Object)super.findByIdBase(id, lock);
    }
    
	/***************************************************************************
		* Gets the TraditionalParadigm with the passed constraints from the persistent
		* store. Returns an empty List if none are found that match.
		* @param name a partial name that these users must have. This may contain a
      	*        literal value to searched on, or a partial string that uses wild
		*        card searching (see SqlUtil.createQueryExpression() for details).
		*        This looks for all matches in the TraditionalParadigm's name, as well as in 
		*        all related common names and CAS entry codes (logical OR).
		* @param usage when applicable, the results are filtered to only include
		*        Units with the passed role subclass name. If null or -1,
		*        this constraint is not applied.
		* @param language the language to sort on (defaults to English).
		* @return the TraditionalParadigm in the persistent store that match the passed
		*         constraints.
		*/
	
	public PagedList findForFilter(String name, String language) {
		String nameExpression = SqlUtil.createQueryExpression(name);
		StringBuffer queryText = new StringBuffer();
		List results;
    
		queryText.append("SELECT DISTINCT tp.* ");
		queryText.append("FROM trad_paradigms tp ");

		// Search indication types accross the Code, French and English names.
		nameExpression = nameExpression.toUpperCase();
		queryText.append("WHERE (UPPER(tp.tradparadigm_name_eng)" + nameExpression);
		queryText.append("  OR UPPER(tp.tradparadigm_name_fr)" + nameExpression);        
		queryText.append("  ) ");
		if (Locale.FRENCH.getLanguage().equals(language)) {
			queryText.append("  ORDER BY UPPER(tp.tradparadigm_name_fr)");
		} else {
			queryText.append("  ORDER BY UPPER(tp.tradparadigm_name_eng)");
		}

		log.debug("Query: " + queryText);
		
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
