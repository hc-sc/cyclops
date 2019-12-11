package ca.gc.hc.nhpd.dao;

import ca.gc.hc.nhpd.model.Country;
import ca.gc.hc.nhpd.model.ProvinceState;
import ca.gc.hc.nhpd.model.Units;
import ca.gc.hc.nhpd.util.PagedList;
import ca.gc.hc.nhpd.util.SqlUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import java.util.Locale;
import java.util.Set;

/*******************************************************************************
 * An object used to retrieve instances of Units from persistent store.
 * @see ca.gc.hc.nhpd.model.Units
 */
@Component
public class ProvinceStateDao extends AbstractDao {
    
	private static final Logger log = Logger.getLogger(ProvinceStateDao.class);
	
	public static boolean LOAD_INTO_MEMORY = true;
	
    /***************************************************************************
     * Columns used in building the Persistent Object Search Result by the 
     * getSearchResultSQLStatement method in the AbstractDao.
     */
    private final static String TABLE_NAME="provinces_states";
    private final static String ID_COLUMN_NAME="provstate_id";
    private final static String FRENCH_COLUMN_NAME="provstate_name_fr";
    private final static String ENGLISH_COLUMN_NAME="provstate_name_eng";
    private final static String DISCRIMINATOR=null;
    
    /***************************************************************************
     * Generic Constructor.
     */
    public ProvinceStateDao() {
        super(ProvinceState.class);
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
     * Gets a collection of all the Units in the persistent store.
     * Note that these are sorted by their codes.
     * @return all instances of the Units that are in the persistent store.
     * @throws HibernateException if a problem is encountered.
     */
    public List findAll() throws HibernateException {
        List order = new ArrayList();

        order.add(Order.asc("code"));
        return findByCriteria(null, order);
    }

    /***************************************************************************
     * Gets the Units with the passed ID from the persistent store.
     * Returns null if none is found.
     * @param id the unique key of the Units in the persistent store.
     * @param lock true if the corresponding record should automatically be
     *        locked from updates in the persistent store.
     * @return the Units, if any, with the passed ID in the persistent store.
     */
    public Object findById(Long id, boolean lock) {
    	try {
    	    return (Units)super.findByIdBase(id, lock);
    	} catch ( Exception e ) {
    		  e.printStackTrace();
    	}
    	return (Object)super.findByIdBase(id, lock);
    }

    /**
     * Returns a list of Provinces or States using the Country Code and sorted by the 
     * language specified.
     * 
     * @param countryCode specifying the country for which we want the provinces.
     * 
     * @param language specifying the language for which we want the list sorted.
     * 
     * @return a sorted list of States or Provinces for the given country code.
     */
    @Cacheable(value="provinceStatefindByCountryCode", key="{#countryCode, #language}")
    public List<ProvinceState> findByCountryCode(String countryCode, String language) {
    	
		Criteria crit = getSession().createCriteria(getModelClass());

        if (isFrench(language)) {
    		crit.addOrder(Order.asc("nameF"));
    	} else {
    		crit.addOrder(Order.asc("nameE"));
    	}
    	
    	crit.createAlias("country", "c").add(Restrictions.eq("c.code", countryCode));

		return crit.list();
    				
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
