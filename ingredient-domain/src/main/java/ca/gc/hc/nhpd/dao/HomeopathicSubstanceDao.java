package ca.gc.hc.nhpd.dao;

import ca.gc.hc.nhpd.model.HomeopathicSubstance;
import ca.gc.hc.nhpd.util.PagedList;
import ca.gc.hc.nhpd.util.SqlUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
public class HomeopathicSubstanceDao extends AbstractDao {
    
    private static final Logger log = Logger.getLogger(HomeopathicSubstanceDao.class);
    
    public static boolean LOAD_INTO_MEMORY = true;
    
    /***************************************************************************
     * Columns used in building the Persistent Object Search Result by the 
     * getSearchResultSQLStatement method in the AbstractDao.
     */
    private final static String TABLE_NAME="ingredients";
    private final static String ID_COLUMN_NAME="ingred_id";
    private final static String FRENCH_COLUMN_NAME="ingred_authorized_name_fr";
    private final static String ENGLISH_COLUMN_NAME="ingred_authorized_name_eng";
    private final static String DISCRIMINATOR="ingredspec_class_name='HomeopathicSubstance'";
    
  
    /***************************************************************************
     * Generic Constructor.
     */
    public HomeopathicSubstanceDao() {
        super(HomeopathicSubstance.class);
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
     * This function returns a list of homeopathic source materials in sorted order.
     * Will return an empty list if the ingredient is null or when no results are found.
     *
     * @param ingredientId
     * 
     * @return
     */
    public List<String> findHomeopathicSourceMaterials(
               Long ingredientId, boolean isFrench)
    {

        if (ingredientId == null) {
            return new ArrayList<String>();
        }
       
        StringBuffer queryText = new StringBuffer();

        queryText.append("SELECT DISTINCT ");
        if (isFrench) {
	    queryText.append("  hg.homeogentext_name_fr ");
        } else {
	    queryText.append("  hg.homeogentext_name_eng ");
        }
        queryText.append("FROM ");
        queryText.append("  ingredient_roles ir, ");
        queryText.append("  homeopathic_formulas hf, ");
        queryText.append("  homeopathic_source_mat_hgts hsm, ");
        queryText.append("  homeopathic_generic_texts hg ");
        queryText.append("WHERE ");
        queryText.append("  ir.ingredrole_id = hf.ingredrole_id and ");
        queryText.append("  hf.homeoform_id = hsm.homeoform_id and ");
        queryText.append("  hsm.homeogentext_id = hg.homeogentext_id and ");
        queryText.append("  ir.ingred_id = ");
        queryText.append(ingredientId);
       
        List<String> results = getSession().createSQLQuery(queryText.toString()).list();

        if (results == null || results.size() == 0) {
            return new ArrayList<String>();
        }
        
        Collections.sort(results);
       
        return results;
                                               
    }
    
}
