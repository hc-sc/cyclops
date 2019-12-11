package ca.gc.hc.nhpd.dao;

import ca.gc.hc.nhpd.model.QualifiedSynonym;
import ca.gc.hc.nhpd.util.SqlUtil;

/*******************************************************************************
 * An object used to retrieve instances of CommonName from persistent store.
 * @see ca.gc.hc.nhpd.model.Synonym
 */
public class QualifiedSynonymDao extends AbstractDao {
	
	public static boolean LOAD_INTO_MEMORY = true;
	
    /***************************************************************************
     * Generic Constructor.
     */
    public QualifiedSynonymDao() {
        super(QualifiedSynonym.class);
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
     * Gets the CommonName with the passed ID from the persistent store.
     * Returns null if none is found.
     * @param id the unique key of the CommonName in the persistent store.
     * @param lock true if the corresponding record should automatically be
     *        locked from updates in the persistent store.
     * @return the CommonName, if any, with the passed ID in the persistent store.
     */
    public Object findById(Long id, boolean lock) {
        return (Object)super.findByIdBase(id, lock);
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
        queryText.append("qsr_mv.id, ");
        
        if (isFrench) {
            queryText.append("qsr_mv.shortDescriptionFr ");
        } else {
            queryText.append("qsr_mv.shortDescriptionEng ");
        }
        
        queryText.append("FROM ");
        queryText.append("  qualifiedsynonym_references_mv qsr_mv ");

        // NB. Search terms are language specific
        if (searchString != null && !searchString.equals(EMPTY_STRING)) {
            queryText.append("WHERE ");
            if (isFrench) {
                queryText.append("LOWER(qsr_mv.shortDescriptionFr) ");
            } else {
                queryText.append("LOWER(qsr_mv.shortDescriptionEng) ");
            }
            String likeClause = (SqlUtil.createQueryExpression(searchString));
            queryText.append(likeClause);
        }

        if (isFrench) {
            queryText.append("ORDER BY LOWER(qsr_mv.shortDescriptionFr) ");
        } else {
            queryText.append("ORDER BY LOWER(qsr_mv.shortDescriptionEng) ");
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
