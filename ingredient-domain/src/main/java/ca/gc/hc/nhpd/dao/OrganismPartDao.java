package ca.gc.hc.nhpd.dao;

import ca.gc.hc.nhpd.dto.SourceOrganismPart;
import ca.gc.hc.nhpd.model.OrganismPart;
import ca.gc.hc.nhpd.util.MapUtil;
import ca.gc.hc.nhpd.util.SqlUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


/*******************************************************************************
 * An object used to retrieve instances of OrganismPart from persistent store.
 * 
 * @see ca.gc.hc.nhpd.model.OrganismPart
 */
@Component
public class OrganismPartDao extends AbstractDao {

    private static final Log log = LogFactory.getLog(OrganismPartDao.class);

    public static boolean LOAD_INTO_MEMORY = true;

    /***************************************************************************
     * Generic Constructor.
     */
    public OrganismPartDao() {
        super(OrganismPart.class);
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
     * Gets a collection of all the Units in the persistent store. Note that
     * these are sorted by their codes.
     * 
     * @return all instances of the Units that are in the persistent store.
     * @throws HibernateException
     *             if a problem is encountered.
     */
    public List findAll() throws HibernateException {
        List order = new ArrayList();
        return findByCriteria(null, order);
    }

    /***************************************************************************
     * Gets the OrganismPart with the passed ID from the persistent store.
     * Returns null if none is found.
     * 
     * @param id
     *            the unique key of the OrganismPart in the persistent store.
     * @param lock
     *            true if the corresponding record should automatically be
     *            locked from updates in the persistent store.
     * @return the OrganismPart, if any, with the passed ID in the persistent
     *         store.
     */
    public Object findById(Long id, boolean lock) {
        return (Object) super.findByIdBase(id, lock);
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
        queryText.append("op.orgpart_id, ");

        if (isFrench)
            queryText.append("oppt.orgparttype_name_fr || ' - (' || o.organism_name || ')', oppt.orgparttype_name_fr ");
        else
            queryText.append("oppt.orgparttype_name_eng || ' - (' || o.organism_name || ')', oppt.orgparttype_name_eng ");

        queryText.append("FROM ");
        queryText.append("organism_parts op, ");
        queryText.append("organism_part_types oppt, ");
        queryText.append("organisms o ");
        queryText.append("WHERE ");
        queryText.append("op.orgparttype_id=oppt.orgparttype_id and ");
        queryText.append("op.organism_id=o.organism_id ");

        // NB. Search terms are language specific
        if (searchString != null && !searchString.equals(EMPTY_STRING)) {
            queryText.append(" AND ");
            if (isFrench) {
                queryText.append(" LOWER(oppt.orgparttype_name_fr)");
            } else {
                queryText.append(" LOWER(oppt.orgparttype_name_eng)");
            }
            String likeClause = (SqlUtil.createQueryExpression(searchString));
            queryText.append(likeClause);
        }

        appendOrderByClause(queryText, isFrench);
        
        return queryText;
        
    }
    
    private void appendOrderByClause(StringBuffer queryText, boolean isFrench) {

        /*
         * NB. Oracle is optimized such that the whole result set does not get
         * sorted - see:
         * http://www.oracle.com/technology/oramag/oracle/06-sep/o56asktom.html
         * Tests with the order clause moved to the outer select had identical
         * response times.
         */
        if (isFrench)
            queryText.append("  ORDER BY LOWER(oppt.orgparttype_name_fr)");
        else
            queryText.append("  ORDER BY LOWER(oppt.orgparttype_name_eng)");

        queryText.append(" )");
    }

    
	public List<SourceOrganismPart> getSourceOrganismParts(
                Long ingredientId,
                Long organismPartId,
                boolean isFrench)
    {

        if (ingredientId == null && organismPartId == null) {
            return null;
        }
        
        StringBuffer queryText = new StringBuffer();

        queryText.append("SELECT DISTINCT * FROM SourceOrganismParts_mv ");
        
        if (ingredientId != null && organismPartId != null) {
            queryText.append("WHERE ingred_id = " + ingredientId + " and ");
            queryText.append("orgpart_id = " + organismPartId);
        } else if (ingredientId != null) {
            queryText.append("WHERE ingred_id = " + ingredientId);
        } else {
            queryText.append("WHERE orgpart_id = " + organismPartId);
        }
        log.debug("QueryText: " + queryText);
        
        List results = executeMaterializedViewQuery(queryText);
        
        if (results.size() == 0) {
            return null;
        }
        
        //convert the search results to IngredientSearchResults objects.
        return convertToOrganismPartSearchResult(results, isFrench);
                                                
    }

    private List<SourceOrganismPart> 
    convertToOrganismPartSearchResult(List results,
                                     boolean isFrench) {
        
        List<SourceOrganismPart> organismPartSearchResults = new ArrayList<SourceOrganismPart>();
        Object[] columns = null;
        SourceOrganismPart organismPartSearchResult = null;
    
        for (int x=0;x<results.size();x++) {
            columns = (Object[])results.get(x);
            organismPartSearchResult =
                new SourceOrganismPart(columns,isFrench);
            organismPartSearchResults.add(organismPartSearchResult);
        }
        
        //sort by appended name and part type
        Collections.sort(organismPartSearchResults,
                         new SourceOrganismPart.SourceOrganismPartComparator());
                         
        return organismPartSearchResults;
    }
	
	private List executeMaterializedViewQuery(StringBuffer queryText) {
        List results = null;

        log.debug("WS Materialized View query: " + queryText);
    
        long startTime = System.currentTimeMillis();
        results = getSession().createSQLQuery(queryText.toString()).list();

        long execTime = System.currentTimeMillis() - startTime;
        log.debug("SourceOrganismParts_mv returned " + results.size() + 
                  " records in " + execTime + "ms.");
        return results;
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
