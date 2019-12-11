package ca.gc.hc.nhpd.dao;

import ca.gc.hc.nhpd.model.OrganismType;
import ca.gc.hc.nhpd.util.PagedList;
import ca.gc.hc.nhpd.util.SqlUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Component;
import org.hibernate.HibernateException;

/*******************************************************************************
 * An object used to retrieve instances of OrganismType from persistent store.
 * 
 * @see ca.gc.hc.nhpd.model.OrganismType
 */
@Component
public class OrganismTypeDao extends AbstractDao {

    public static boolean LOAD_INTO_MEMORY = true;

    /***************************************************************************
     * Columns used in building the Persistent Object Search Result by the 
     * getSearchResultSQLStatement method in the AbstractDao.
     */
    private final static String TABLE_NAME="organism_types";
    private final static String ID_COLUMN_NAME="orgtype_id";
    private final static String FRENCH_COLUMN_NAME="orgtype_name_fr";
    private final static String ENGLISH_COLUMN_NAME="orgtype_name_eng";
    
    /***************************************************************************
     * Generic Constructor.
     */
    public OrganismTypeDao() {
        super(OrganismType.class);
    }

    /***************************************************************************
     * Flag determining if the list of items for this DAO object should be
     * loaded into the ApplicationGlobal's resident memory. This is used for
     * system pick lists, and other code list objects. This method returns the
     * static value stored in the LOAD_INTO_MEMORY that should be located at the
     * top of the individual DAO classes. By default, the AbstractDAO ensures
     * that none of the individual DAO classes are loaded unless specifically
     * set.
     * 
     * @return LOAD_INTO_MEMORY static variable.
     */
    public boolean loadIntoMemory() {
        return LOAD_INTO_MEMORY;
    }

    /***************************************************************************
     * Gets a collection of all the OrganismTypes in the persistent store. Note
     * that these are sorted by their English names.
     * 
     * @return all instances of the OrganismTypes that are in the persistent
     *         store.
     * @throws HibernateException
     *             if a problem is encountered.
     */
    public List findAll() throws HibernateException {
        List order = new ArrayList();

        order.add(Order.asc("nameE"));
        return findByCriteria(null, order);
    }

    /***************************************************************************
     * Gets a collection of all the Organism Types in the persistent
     * store in the language specified.  Note that the sorting is based on the
     * language that is passed.
     * 
     * @return all instances of the Organism Types that are in the
     *         persistent store.
     * @throws HibernateException if a problem is encountered.
     */
    public List findAll(String language) throws HibernateException {
        List order = new ArrayList();
        
        if (isFrench(language))
        	order.add(Order.asc("nameF"));
        else
        	order.add(Order.asc("nameE"));
        
        return findByCriteria(null, order);
    }
    
    /***************************************************************************
     * Gets the OrganismType with the passed ID from the persistent store.
     * Returns null if none is found.
     * 
     * @param id
     *            the unique key of the OrganismType in the persistent store.
     * @param lock
     *            true if the corresponding record should automatically be
     *            locked from updates in the persistent store.
     * @return the OrganismType, if any, with the passed ID in the persistent
     *         store.
     */
    public Object findById(Long id, boolean lock) {
        return (Object) super.findByIdBase(id, lock);
    }

    /***************************************************************************
     * Gets the OrganismType with the passed constraints from the persistent
     * store. Returns an empty List if none are found that match.
     * 
     * @param name
     *            a partial name that these users must have. This may contain a
     *            literal value to searched on, or a partial string that uses
     *            wild card searching (see SqlUtil.createQueryExpression() for
     *            details). This looks for all matches in the unit's name, as
     *            well as in all related common names and CAS entry codes
     *            (logical OR).
     * @param usage
     *            when applicable, the results are filtered to only include
     *            Units with the passed role subclass name. If null or -1, this
     *            constraint is not applied.
     * @param language
     *            the language to sort on (defaults to English).
     * @return the User Role in the persistent store that match the passed
     *         constraints.
     */

    public PagedList findForFilter(String name, String language) {
        String nameExpression = SqlUtil.createQueryExpression(name);
        StringBuffer queryText = new StringBuffer();
        List results;

        queryText.append("SELECT DISTINCT ot.* ");
        queryText.append("FROM organism_types ot ");

        // Search units accross the Code, French and English names.
        nameExpression = nameExpression.toUpperCase();
        queryText
                .append("WHERE (UPPER(ot.orgtype_name_eng)" + nameExpression);
        queryText.append("  OR UPPER(ot.orgtype_name_fr)" + nameExpression);

        queryText.append("  ) ");
        queryText.append("  ORDER BY UPPER(ot.orgtype_name_eng)");

        results = getSession().createSQLQuery(queryText.toString()).addEntity(
                "i", getModelClass()).list();

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
