package ca.gc.hc.nhpd.dao;

import ca.gc.hc.nhpd.model.Reference;
import ca.gc.hc.nhpd.util.PagedList;
import ca.gc.hc.nhpd.util.SqlUtil;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

/*******************************************************************************
 * An object used to retrieve instances of Reference from persistent store.
 * @see ca.gc.hc.nhpd.model.Reference
 */
public class ReferenceDao extends AbstractDao {
	
	private static final Logger log = Logger.getLogger(ReferenceDao.class);
	
    public static boolean LOAD_INTO_MEMORY = true;
    
    /***************************************************************************
     * Columns used in building the Persistent Object Search Result by the 
     * getSearchResultSQLStatement method in the AbstractDao.
     */
    private final static String TABLE_NAME="references_mv";
    private final static String ID_COLUMN_NAME="id";
    private final static String FRENCH_COLUMN_NAME="shortDescriptionFr";
    private final static String ENGLISH_COLUMN_NAME="shortDescriptionEng";
    
	
    /***************************************************************************
     * Generic Constructor.
     */
    public ReferenceDao() {
        super(Reference.class);
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
     * Gets a collection of all the References in the persistent store.
     * @return all instances of the References that are in the persistent store.
     * @throws HibernateException if a problem is encountered.
     */
    public List findAll() throws HibernateException {
        return findByCriteria(null, null);
    }

    /***************************************************************************
     * Gets the Reference with the passed ID from the persistent store.
     * Returns null if none is found.
     * @param id the unique key of the Reference in the persistent store.
     * @param lock true if the corresponding record should automatically be
     *        locked from updates in the persistent store.
     * @return the Reference, if any, with the passed ID in the persistent store.
     */
    public Object findById(Long id, boolean lock) {
        return (Object)super.findByIdBase(id, lock);
    }
    
    /***************************************************************************
     * Gets the Reference with the passed constraints from the persistent
     * store. Returns an empty List if none are found that match.
     * @param name a partial name that these users must have. This may contain a
     *        literal value to searched on, or a partial string that uses wild
     *        card searching (see SqlUtil.createQueryExpression() for details).
     * @param code when applicable, the results are filtered to only include
     *        References with the passed code. If null or -1, this constraint 
     *        is not applied.
     * @param language the ISO 639.2 language to sort on (defaults to English).
     * @return the Reference in the persistent store that match the passed
     *         constraints.
     */
    public PagedList findForFilter(String name, String code, String language) {
    	
    	boolean includeName = (name==null||name.length()<1)?false:true;
    	boolean includeCode = (code==null||code.length()<1)?false:true;
    	
    	StringBuffer queryText = new StringBuffer();
    	String nameExpression = SqlUtil.createQueryExpression(name).toLowerCase();
    	String codeExpression = SqlUtil.createQueryExpression(code).toLowerCase();
    	
    	queryText.append("SELECT r.* ");
    	queryText.append("FROM REFS r ");
    	
    	if(includeName||includeCode) {
    		queryText.append("WHERE ");
    	}
    	
    	boolean hasName = false;
    	if(includeCode) {
    		queryText.append("LOWER(r.REF_CODE) ").append(codeExpression);
    		hasName = true;
    	}
    	if(includeName) {
    		if(hasName) {
    			queryText.append("and ");
    		}
    		
            if (isFrench(language)) {
    			queryText.append("LOWER(r.REF_NAME_FR)" + nameExpression);
    		}else {
                queryText.append("LOWER(r.REF_NAME_ENG)" + nameExpression);
            }
    	}
    	
		log.debug("Query: " + queryText);
		
        List results = getSession().createSQLQuery(queryText.toString()).
                  addEntity("r", getModelClass()).list();
        
        //return new PagedList(results);
    	return new PagedList();
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
