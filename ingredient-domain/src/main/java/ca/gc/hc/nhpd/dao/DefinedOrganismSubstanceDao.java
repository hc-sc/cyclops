package ca.gc.hc.nhpd.dao;

import ca.gc.hc.nhpd.model.DefinedOrganismSubstance;
import ca.gc.hc.nhpd.util.PagedList;
import ca.gc.hc.nhpd.util.SqlUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Component;

/*******************************************************************************
 * An object used to retrieve instances of Monographs from persistent store.
 * @seeMonograph ca.gc.hc.nhpd.model.Monograph
 */
@Component
public class DefinedOrganismSubstanceDao extends AbstractDao {
    
    private static final Logger log = Logger.getLogger(DefinedOrganismSubstanceDao.class);
    
    public static boolean LOAD_INTO_MEMORY = true;
    
    /***************************************************************************
     * Columns used in building the Persistent Object Search Result by the 
     * getSearchResultSQLStatement method in the AbstractDao.
     */
    private final static String TABLE_NAME="ingredients";
    private final static String ID_COLUMN_NAME="ingred_id";
    private final static String FRENCH_COLUMN_NAME="ingred_authorized_name_fr";
    private final static String ENGLISH_COLUMN_NAME="ingred_authorized_name_eng";
    private final static String DISCRIMINATOR="ingredspec_class_name='DefinedOrganismSubstance'";
    
    /***************************************************************************
     * Generic Constructor.
     */
    public DefinedOrganismSubstanceDao() {
        super(DefinedOrganismSubstance.class);
    }
    
    /***************************************************************************
     * Gets a collection of all the Monographs in the persistent store.
     * @return all instances of the Monographs that are in the persistent store.
     * @throws HibernateException if a problem is encountered.
     */
    public List findAll() throws HibernateException {
        return findByCriteria(null, null);
    }

    /***************************************************************************
     * Gets the Monograph with the passed ID from the persistent store.
     * Returns null if none is found.
     * @param id the unique key of the Monograph in the persistent store.
     * @param lock true if the corresponding record should automatically be
     *        locked from updates in the persistent store.
     * @return the Monograph, if any, with the passed ID in the persistent
     *         store.
     */
    public Object findById(Long id, boolean lock) {
        return super.findByIdBase(id, lock);
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
    
    /**
     * Returns true when the Ingredient is a probiotic.  A probiotic is 
     * defined as an ingredient with a NHPClassification of 11.
     *  
     * @param ingredientId
     * @return
     */
    public Boolean isProbiotic(Long ingredientId) {
        
        if (ingredientId == null) {
            return null;
        }
        
        StringBuffer queryText = new StringBuffer();

        queryText.append("SELECT ");
        queryText.append("  count(*) ");
        queryText.append("FROM ");
        queryText.append("  INGREDIENT_NHPCLASSIFICATIONS ");
        queryText.append("WHERE ");
        queryText.append("  NHPCLASS_ID = 11 and ");
        queryText.append("  INGRED_ID=");
        queryText.append(ingredientId);
        
        List results = getSession().createSQLQuery(queryText.toString()).list();
        Object o = results.get(0);
        BigDecimal result = (BigDecimal) o;
        
        
        if (result.intValue() == 0) {
            return false;
        }
        
        return true;
        
    }
    
     
}
