package ca.gc.hc.nhpd.dao;

import ca.gc.hc.nhpd.model.CommonTerm;
import ca.gc.hc.nhpd.util.PagedList;
import ca.gc.hc.nhpd.util.SqlUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.hibernate.criterion.Order;
import org.hibernate.HibernateException;

/*******************************************************************************
 * An object used to retrieve instances of Common Terms from persistent 
 * store.  
 * @see ca.gc.hc.nhpd.model.CommonTerm
 */
public class CommonTermDao extends AbstractDao {
    
	public static boolean LOAD_INTO_MEMORY = true;
	
    /***************************************************************************
     * Columns used in building the Persistent Object Search Result by the 
     * getSearchResultSQLStatement method in the AbstractDao.
     */
    private final static String TABLE_NAME="common_terms";
    private final static String ID_COLUMN_NAME="commonterm_id";
    private final static String FRENCH_COLUMN_NAME="commonterm_name_fr";
    private final static String ENGLISH_COLUMN_NAME="commonterm_name_eng";
    
    /***************************************************************************
     * Generic Constructor.
     */
    public CommonTermDao() {
        super(CommonTerm.class);
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
    public List findAll() throws HibernateException {
        List order = new ArrayList();

        order.add(Order.asc("nameE"));
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
    
    /*
     * Retrieve Common Terms by a single type.  Introduced for Web Services.
     * NB. this method orders the result set by name, not code!
     */
    public PagedList findByType(String commonTermType, String language) {

        StringBuffer queryText = new StringBuffer();
        List results;
        
        queryText.append("SELECT DISTINCT ");
        queryText.append("  ct.* ");
        queryText.append("FROM ");
        queryText.append("  COMMON_TERMS ct, ");
        queryText.append("  COMMON_TERM_TYPES ctt ");
        queryText.append("WHERE ");
        queryText.append("ct.commontermtype_id = ctt.commontermtype_id and ");
        queryText.append("(ctt.commontermtype_name_eng = '" + commonTermType + "' or ");
        queryText.append("ctt.commontermtype_name_fr = '" + commonTermType + "')");

        // Search units across the Code, French and English names.
        if (isFrench(language)) {
            queryText.append("  ORDER BY UPPER(ct.commonterm_name_fr)");
        } else {
            queryText.append("  ORDER BY UPPER(ct.commonterm_name_eng)");
        }
        
        results = getSession().createSQLQuery(queryText.toString()).
                 addEntity("i", getModelClass()).list();
       
        return new PagedList(results);
    }
    
    /*
     * retrieve Common Terms by multiple types
     */
    public PagedList findByTypes(String[] commonTermTypes, String language) {
        
        StringBuffer queryText = new StringBuffer();
        List results;
        
        //unitsTypes is assumed validated by client
        if (commonTermTypes.length == 1)  
            return findByType(commonTermTypes[0],language);
        
        queryText.append
            ("SELECT DISTINCT ct.* FROM COMMON_TERMS ct"
           + " inner join COMMON_TERM_TYPES ctt ON ct.commontermtype_id = ctt.commontermtype_id"
           + " WHERE ctt.COMMONTERMTYPE_NAME_ENG = ");
           
        queryText.append(SqlUtil.buildAnyParms(commonTermTypes));
    
        if (isFrench(language)) {
            queryText.append("  ORDER BY UPPER(ct.commonterm_name_fr)");
        } else {
            queryText.append("  ORDER BY UPPER(ct.commonterm_name_eng)");
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
