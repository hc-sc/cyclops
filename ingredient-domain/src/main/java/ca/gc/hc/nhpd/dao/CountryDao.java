package ca.gc.hc.nhpd.dao;

import ca.gc.hc.nhpd.dao.order.CountriesEnglishOrder;
import ca.gc.hc.nhpd.dao.order.CountriesFrenchOrder;
import ca.gc.hc.nhpd.model.Country;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.hibernate.HibernateException;
import java.util.Locale;

import ca.gc.hc.nhpd.util.PagedList;
import ca.gc.hc.nhpd.util.SqlUtil;

/*******************************************************************************
 * An object used to retrieve instances of Country from persistent store.
 * @see ca.gc.hc.nhpd.model.Country
 */
@Component
public class CountryDao extends AbstractDao {
    
	private static final Logger log = Logger.getLogger(CountryDao.class);
	
	public static boolean LOAD_INTO_MEMORY = true;
	
    /***************************************************************************
     * Columns used in building the Persistent Object Search Result by the 
     * getSearchResultSQLStatement method in the AbstractDao.
     */
    private final static String TABLE_NAME="countries";
    private final static String ID_COLUMN_NAME="country_id";
    private final static String FRENCH_COLUMN_NAME="country_name_fr";
    private final static String ENGLISH_COLUMN_NAME="country_name_eng";
    
    /***************************************************************************
     * Generic Constructor.
     */
    public CountryDao() {
        super(Country.class);
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
    @Override
	public boolean loadIntoMemory() {
		return LOAD_INTO_MEMORY;
	}
    
    /***************************************************************************
     * Gets a collection of all the Countries in the persistent store. Note that
     * these are sorted by their English names.
     * @return all instances of the Countries that are in the persistent store.
     * @throws HibernateException if a problem is encountered.
     */
    @Cacheable("countryFindAll")
    public List findAll() throws HibernateException {
        List order = new ArrayList();

        order.add(Order.asc("nameE"));
        return findByCriteria(null, order);
    }

    /**
     * Returns a list of countries sorted by Canada, US and other countries
     * using the Country's English name.
     * @param language the language to sort on (defaults to English).
     * @return list of countries sorted with Canada, US, then language specific country names.
     * @throws HibernateException
     */
    @Cacheable(value="countryLanguageOrdered", key="#language")
    public List<Country> findAllLanguageOrdered(String language) throws HibernateException {
    	List order = new ArrayList();
    	if (Locale.FRENCH.getLanguage().equals(language)) {
    		order.add(new CountriesFrenchOrder());
		} else {
			order.add(new CountriesEnglishOrder());
		}
    	
        return findByCriteria(null, order);
    }

    /***************************************************************************
     * Gets the Country with the passed ID from the persistent store. Returns
     * null if none is found.
     * @param id the unique key of the Country in the persistent store.
     * @param lock true if the corresponding record should automatically be
     *        locked from updates in the persistent store.
     * @return the Country, if any, with the passed ID in the persistent store.
     */
    public Object findById(Long id, boolean lock) {
        return (Object)super.findByIdBase(id, lock);
    }

    /**
     * Returns a country object given a code.
     * 
     * @param code
     * 
     * @return the Country, if any, with the code.
     */
    @Cacheable(value="countryFindByCode", key= "{#code, #language}")
    public Country findByCode(String code, String language) {
    	StringBuffer queryText = new StringBuffer();
		queryText.append("FROM Country co ");
		queryText.append("WHERE LOWER(co.code) = '" + code.trim().toLowerCase() + "' ");
		if (Locale.FRENCH.getLanguage().equals(language)) {
			queryText.append(" ORDER BY UPPER(co.nameF)");
		} else {
			queryText.append(" ORDER BY UPPER(co.nameE)");
		}
		List results = getSession().createQuery(queryText.toString()).list();
		if (results == null || results.size() < 1) {
			return null;
		}
		return (Country)results.get(0);
    }
    
	/***************************************************************************
	* Gets the Countries with the passed constraints from the persistent
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
	* @return the Countries in the persistent store that match the passed
	*         constraints.
	*/

	public PagedList findForFilter(String name, String language) {
		String nameExpression = SqlUtil.createQueryExpression(name);
		StringBuffer queryText = new StringBuffer();
		List results;
    
		queryText.append("SELECT DISTINCT c.* ");
		queryText.append("FROM countries c ");

		// Search units accross the Code, French and English names.
		nameExpression = nameExpression.toUpperCase();
		queryText.append("WHERE (UPPER(c.country_code)" + nameExpression);
		queryText.append("  OR UPPER(c.country_name_eng)" + nameExpression);
		queryText.append("  OR UPPER(c.country_name_fr)" + nameExpression);
        
		queryText.append("  ) ");
		if (Locale.FRENCH.getLanguage().equals(language)) {
			queryText.append("  ORDER BY UPPER(c.country_name_fr)");
		} else {
			queryText.append("  ORDER BY UPPER(c.country_name_eng)");
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
