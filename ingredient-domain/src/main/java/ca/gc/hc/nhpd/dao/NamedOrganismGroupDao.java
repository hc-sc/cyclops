package ca.gc.hc.nhpd.dao;

import ca.gc.hc.nhpd.model.NamedOrganismGroup;
import ca.gc.hc.nhpd.util.SqlUtil;

import java.util.List;
import org.hibernate.HibernateException;

/*******************************************************************************
 * An object used to retrieve instances of Organism from persistent store.
 * @see ca.gc.hc.nhpd.model.Organism
 */
public class NamedOrganismGroupDao extends AbstractDao {
    /***************************************************************************
     * Generic Constructor.
     */
    public NamedOrganismGroupDao() {
        super(NamedOrganismGroup.class);
    }
    
    /***************************************************************************
     * Gets a collection of all the OrganismGroups in the persistent store.
     * @return all instances of the OrganismGroups that are in the persistent store.
     * @throws HibernateException if a problem is encountered.
     */
    public List findAll() throws HibernateException {
        return findByCriteria(null, null);
    }

    /***************************************************************************
     * Gets the OrganismGroup with the passed ID from the persistent store.
     * Returns null if none is found.
     * @param id the unique key of the OrganismGroup in the persistent store.
     * @param lock true if the corresponding record should automatically be
     *        locked from updates in the persistent store.
     * @return the OrganismGroup, if any, with the passed ID in the persistent store.
     */
    public Object findById(Long id, boolean lock) {
        return super.findByIdBase(id, lock);
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
        queryText.append("og.org_grp_id, ");
        
        if (isFrench) {
            queryText.append("og.org_grp_name_fr ");
        } else {
            queryText.append("og.org_grp_name_eng ");
        }
        
        queryText.append("FROM ");
        queryText.append("organism_groups og ");
        queryText.append("WHERE ");
        queryText.append("org_grp_class_name='NamedOrganismGroup' ");
        
        // NB. Search terms are language specific
        if (searchString != null && !searchString.equals(EMPTY_STRING)) {
            queryText.append("AND ");
            if (isFrench) {
                queryText.append("LOWER(og.org_grp_name_fr) ");
            } else {
                queryText.append("LOWER(og.org_grp_name_eng) ");
            }
            String likeClause = (SqlUtil.createQueryExpression(searchString));
            queryText.append(likeClause);
        }

        if (isFrench) {
            queryText.append("ORDER BY LOWER(og.org_grp_name_fr) ");
        } else {
            queryText.append("ORDER BY LOWER(og.org_grp_name_eng) ");
        }
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
