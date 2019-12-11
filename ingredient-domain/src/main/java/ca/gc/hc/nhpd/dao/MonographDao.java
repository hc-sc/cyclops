package ca.gc.hc.nhpd.dao;

import ca.gc.hc.nhpd.dto.MonographSearchCriteria;
import ca.gc.hc.nhpd.dto.MonographSearchResult;
import ca.gc.hc.nhpd.dto.PreClearedInfo;
import ca.gc.hc.nhpd.dto.SearchCriteria;
import ca.gc.hc.nhpd.dto.SearchCriterion;
import ca.gc.hc.nhpd.exception.IngredientsException;
import ca.gc.hc.nhpd.model.Monograph;
import ca.gc.hc.nhpd.model.MonographType;
import ca.gc.hc.nhpd.util.SqlUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/*******************************************************************************
 * An object used to retrieve instances of Monographs from persistent store.
 * @seeMonograph ca.gc.hc.nhpd.model.Monograph
 */
@Component
public class MonographDao extends AbstractDao {

	public static boolean LOAD_INTO_MEMORY = true;

	private static final Log log = LogFactory.getLog(MonographDao.class);
	
	/***************************************************************************
	 * Generic Constructor.
	 */
	public MonographDao() {
		super(Monograph.class);
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
	 * Determines if there are External AbLS in the database
	 * @return boolean - true if there are external abls in the db, false if not.
	 * @throws HibernateException if a problem is encountered.
	 */
	public boolean hasExternalAbls() throws HibernateException {
		Long count = (Long) getSession().createQuery("select count(*) from " +
				"Monograph m, in (m.monographTypes) as t where " +
				"t.code='" + MonographType.MONOGRAPH_TYPE_CODE_ABLS + "'").uniqueResult();

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/***************************************************************************
     * Refactored code for retrieving the list of Monographs.  This is used by
     * the web application.
	 *  
	 * @return instances of MonographSearchResult.
     * 
	 * @throws HibernateException if a problem is encountered.
	 */
	public List<MonographSearchResult> listMonographs(String language,
													  boolean includeAblsMonographs,
                                                      boolean includeAblsInternalMonographs)		
	throws HibernateException {
		
		boolean isFrench = isFrench(language);

        StringBuffer queryText = new StringBuffer();
		
		queryText.append("SELECT DISTINCT ");
        queryText.append("  m.mono_id as mono_id, ");
		if (isFrench ) {
			queryText.append("  m.mono_name_fr as mono_name, ");
            queryText.append("  m.mono_url_fr as mono_url, ");
        } else {
			queryText.append("  m.mono_name_eng as mono_name, ");
            queryText.append("  m.mono_url_eng as mono_url, ");
        }
        queryText.append("  null as mono_synonyms, ");
		queryText.append("  m.monospec_class_name as mono_class_name, ");
        if (isFrench) {
            queryText.append("  m.attachment_fr_id as attachment_id, ");
            queryText.append("  a.attachment_name_fr as attachment_name, ");
            queryText.append("  a.attachment_desc_fr as attachment_desc, ");
        } else {
            queryText.append("  m.attachment_eng_id as attachment_id, ");
            queryText.append("  a.attachment_name_eng as attachment_name, ");
            queryText.append("  a.attachment_desc_eng as attachment_desc, ");
        }
        queryText.append("  a.attachment_size as attachment_size, ");
        
        queryText.append("  sum(decode(mt.monotype_code, 'A', 1, 0)) as type_a, ");
        queryText.append("  sum(decode(mt.monotype_code, 'C', 1, 0)) as type_c, ");
        queryText.append("  sum(decode(mt.monotype_code, 'P', 1, 0)) as type_p, ");
        queryText.append("  sum(decode(mt.monotype_code, 'T', 1, 0)) as type_t, ");
        queryText.append("  sum(decode(mt.monotype_code, 'I', 1, 0)) as type_i ");
		queryText.append("FROM "); 
        queryText.append("  monographs m, ");
        queryText.append("  monograph_types mt, ");
        queryText.append("  monograph_monograph_types mmt, ");
        queryText.append("  attachments a ");
        queryText.append("WHERE ");
        queryText.append("  m.mono_id = mmt.mono_id (+) AND ");
        queryText.append("  mt.monotype_id (+) = mmt.monotype_id AND ");
        if (isFrench) {
            queryText.append(" m.attachment_fr_id = a.attachment_id (+) ");
        } else {
            queryText.append(" m.attachment_eng_id = a.attachment_id (+) ");
        }
		
        if (!includeAblsMonographs) {
            queryText.append("AND ");
            queryText.append("  m.mono_id not in ( ");
            queryText.append("    select m.mono_id from "); 
            queryText.append("      monographs m, monograph_monograph_types mmt, monograph_types mt "); 
            queryText.append("    where ");
            queryText.append("      m.mono_id = mmt.mono_id and mt.monotype_id = mmt.monotype_id and mt.monotype_code='A' ");
            queryText.append(") ");
        }

        if (!includeAblsInternalMonographs) {
            queryText.append("AND ");
            queryText.append("  m.mono_id not in ( ");
            queryText.append("    select m.mono_id from "); 
            queryText.append("      monographs m, monograph_monograph_types mmt, monograph_types mt "); 
            queryText.append("    where ");
            queryText.append("      m.mono_id = mmt.mono_id and mt.monotype_id = mmt.monotype_id and mt.monotype_code='I' ");
            queryText.append(") ");
        }

        queryText.append("GROUP BY ");
        queryText.append("  m.mono_id, ");
        if (isFrench) {
            queryText.append("  m.mono_name_fr, ");
            queryText.append("  m.mono_url_fr, ");
        } else {
            queryText.append("  m.mono_name_eng, ");
            queryText.append("  m.mono_url_eng, ");
        }
        queryText.append("  m.monospec_class_name, m.attachment_eng_id, ");
        if (isFrench) {
            queryText.append("  m.attachment_fr_id, ");
            queryText.append("  a.attachment_name_fr, ");
            queryText.append("  a.attachment_desc_fr, ");
        } else {
            queryText.append("  m.attachment_eng_id, ");
            queryText.append("  a.attachment_name_eng, ");
            queryText.append("  a.attachment_desc_eng, ");
        }
        queryText.append("  a.attachment_size");
        
		log("MONO QUERY: " + queryText.toString());
		List resultsList = getSession().createSQLQuery(queryText.toString()).list();
	
        return convertToSearchResults(resultsList);

    }
	
	/***************************************************************************
     * Returns a list of monographs associated with an ingredient.  The list
     * of monographs is filtered to remove monograph with names that include the
     * following character combinations: "- NMI Safety", "(AC)", "(ACCOMBO)".
     * In addition, unique combinations are ensured through the use of the SQL
     * DISTINCT query command.
     * 
     * The only information returned is the monograph id and the language 
     * specific monograph name.  The result is sorted by the monograph name.
     * 
     * An empty list will be returned when no monographs are found for a given
     * ingredient or when the ingredient id provided is null.  The list will
     * default to English when the language string provided does not equate to
     * the <code>CANADA_FRENCH.getISO3Language()</code> value of "fra".
     *
     * This function will throw a Hibernate Exception if it encounters an error.
     *
     * @param ingredientId the id of the ingredient.
     * 
     * @param language in which to return the monograph names.
     *  
     * @return instances of MonographSearchResult.
     * 
     * @throws HibernateException if a problem is encountered.
     */
    public List<MonographSearchResult> getMonographsByIngredientId(Long ingredientId, String language)        
    throws HibernateException {
        
        if (ingredientId == null) {
            return new ArrayList<MonographSearchResult>();
        }
        
        boolean isFrench = isFrench(language);

        StringBuffer queryText = new StringBuffer();
        
        queryText.append("SELECT DISTINCT ");
        queryText.append("  m.mono_id as mono_id, ");
        if (isFrench ) {
            queryText.append("  m.mono_name_fr as mono_name, ");
        } else {
            queryText.append("  m.mono_name_eng as mono_name, ");
        }
        queryText.append("  null as mono_url, ");
        queryText.append("  null as mono_synonyms, ");
        queryText.append("  null as mono_class_name, ");
        queryText.append("  null as attachment_id, ");
        queryText.append("  null as attachment_name, ");
        queryText.append("  null as attachment_desc, ");
        queryText.append("  null as attachment_size, ");
        queryText.append("  null as type_a, ");
        queryText.append("  null as type_c, ");
        queryText.append("  null as type_p, ");
        queryText.append("  null as type_t, ");
        queryText.append("  null as type_i ");
        queryText.append("FROM "); 
        queryText.append("  monographs m, ");
        queryText.append("  monograph_entries me ");
        queryText.append("WHERE ");
        queryText.append("  m.mono_id = me.mono_id AND ");
        queryText.append("  me.ingred_id = ? AND " );
        queryText.append("  m.mono_name_eng not like '%- NMI Safety%' AND ");
        queryText.append("  m.mono_name_eng not like '%(AC)%' AND ");
        queryText.append("  m.mono_name_eng not like '%(ACCOMBO)%' ");
        queryText.append("ORDER BY ");
        if (isFrench ) {
            queryText.append("  m.mono_name_fr");
        } else {
            queryText.append("  m.mono_name_eng");
        }
        
        @SuppressWarnings("unchecked")
        List<MonographSearchResult> resultsList = getSession().createSQLQuery(queryText.toString()).setLong(0, ingredientId).list();
    
        return convertToSearchResults(resultsList);

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
        
        Object object = super.findByIdBase(id, lock);
        
        if (object == null) {
            throw new IngredientsException
            ("Error: Not a valid monograph ID");
        }
        return object;
	}
	
	/*
	 * Method which satisfies Product License Application (PLA) requirements
	 * for the PLA Web Service.  The SearchCriteria contains attribute 
	 * name/value pairs.  For Monograph search, the only attribute that 
	 * is provided is the text string to search on. 
	 * Uses the Web Service specific Materialized View: "MONOGRAPH_SYNONYMS_MV"
	 */ 
	public List findBySearchCriteria(SearchCriteria criteria,
									 String language,
									 int maxRows) {
        
        
        // Used by the web services to determine if we should display ABLS monographs. 
        SearchCriterion displayAblsCriterion =
            criteria.getCriterionByName(MonographSearchCriteria.DISPLAY_ABLS);
        boolean displayAbLSMonographs = false;
        if (displayAblsCriterion != null)
        {
            String attribute = displayAblsCriterion.getAttributeValue();
            if (attribute.toLowerCase().equals(MonographSearchCriteria.TRUE)) {
                displayAbLSMonographs = true;
            } else {
                displayAbLSMonographs = false;
            }
        }

        // Used by the web services to determine if we should display ABLS Internal monographs.
        SearchCriterion displayAblsInternalCriterion =
            criteria.getCriterionByName(MonographSearchCriteria.DISPLAY_ABLS_INTERNAL);
        boolean displayAbLSInternalMonographs = false;
        if (displayAblsInternalCriterion != null)
        {
            String attribute = displayAblsInternalCriterion.getAttributeValue();
            if (attribute.toLowerCase().equals(MonographSearchCriteria.TRUE)) {
                displayAbLSInternalMonographs = true;
            } else {
                displayAbLSInternalMonographs = false;
            }
        }
        
        // Used by the web services to determine if we should display product monographs.
        SearchCriterion displayProductCriterion =
            criteria.getCriterionByName(MonographSearchCriteria.DISPLAY_PRODUCTS);
        boolean displayProductMonographs = false;
        if (displayProductCriterion != null)
        {
            String attribute = displayProductCriterion.getAttributeValue();
            
            if (attribute.toLowerCase().equals(MonographSearchCriteria.TRUE)) {
                displayProductMonographs = true;
            } else {
                displayProductMonographs = false;
            }
        }

		return invokeMonographSynonymsMv(criteria, language, displayAbLSMonographs, 
                displayAbLSInternalMonographs, displayProductMonographs, maxRows);
	}   

	/*
	 * Invokes the Oracle Materialized View (MV) which retrieves all 
	 * Synonyms of Monographs that match the specified search criteria.
	 * The MV was introduced to maximize Monograph Search performance 
	 * for the PLA Web Service.
	 * 
	 * NB. only one text search string parameter is currently supported.
	 * 
	 * @param searchCriteria - see findBySearchCriteria parameters
	 * @param maxRows - maximum number of rows to return; -1 means no limit  
	 * 
	 */
	private List invokeMonographSynonymsMv(SearchCriteria searchCriteria,
										   String language,
                                           boolean includeAblsMonographs,
                                           boolean includeAblsInternalMonographs,
                                           boolean includeProductMonographs,
										   int maxRows) {

		List results = null; 
		StringBuffer queryText = new StringBuffer();
		boolean isFrench = isFrench(language);

		//need a nested select to support rownum check at end.
		queryText.append("SELECT * from (");
		queryText.append("SELECT DISTINCT mono_id as mono_id, ");
		if (isFrench) {
			queryText.append("mono_name_fr as mono_name, ");
            queryText.append("null as mono_url, ");
            queryText.append("french_synonyms as mono_synonyms, ");
            queryText.append("monospec_class_name as mono_class_name, ");
        } else {
			queryText.append("mono_name_eng as mono_name, ");
            queryText.append("null as mono_url, ");
            queryText.append("english_synonyms as mono_synonyms, ");
            queryText.append("monospec_class_name as mono_class_name, ");
        }
		
	    /* 
	     * null out unused columns to allow MonographSearchResult DTO to be 
	     * used by both the IDB web app and MWS clients.  MR Jan 6,2011
	     */
	    queryText.append("null as pdf_attachment_id, ");
	    queryText.append("null as pdf_attachment_filename, ");
	    queryText.append("null as pdf_attachment_desc, ");
	    queryText.append("null as pdf_attachment_size, ");
	    queryText.append("null as type_a, null as type_c,");
	    queryText.append("null as type_p, null as type_t, null as type_i ");
		queryText.append(" FROM MONOGRAPH_SYNONYMS_MV  ");	

		//Add the text String.  For now, only one text string is allowed
		String[] textValues = 
			getCriteriaValuesForName(MonographSearchCriteria.NAMESTRING,
					                 searchCriteria);
         
		//search for the text in both the search terms and the monograph name,
        //depending on the user language
        if (textValues != null)
        {
            String textClause = SqlUtil.createDelimitedQueryExpression(
                                        textValues[0].toLowerCase());
			if (isFrench)
            {
				queryText.append(" WHERE (LOWER(mono_name_fr) " + textClause);
                queryText.append(" OR LOWER(french_searchterm) " + textClause + ") and ");
            }
			else {
				queryText.append(" WHERE (LOWER(mono_name_eng) " + textClause);
                queryText.append(" OR LOWER(english_searchterm) " + textClause + ") and ");   
            }
        }else {
        	queryText.append(" where ");
        	
        }

        // SQL Query that includes the ABLS monographs.
       // queryText.append("AND ");
        if (!includeAblsMonographs) {
            queryText.append("  mono_id not in ( ");
        } else {
            queryText.append("  mono_id in ( ");
        }
        queryText.append("    select m.mono_id from "); 
        queryText.append("      monographs m, monograph_monograph_types mmt, monograph_types mt "); 
        queryText.append("    where ");
        queryText.append("      m.mono_id = mmt.mono_id and mt.monotype_id = mmt.monotype_id and mt.monotype_code='A' ");
        queryText.append(") ");

        // SQL Query that includes the ABLS Internal monographs.
        queryText.append("AND ");
        if (!includeAblsInternalMonographs) {
            queryText.append("  mono_id not in ( ");
        } else {
            queryText.append("  mono_id in ( ");
        }
        queryText.append("    select m.mono_id from "); 
        queryText.append("      monographs m, monograph_monograph_types mmt, monograph_types mt "); 
        queryText.append("    where ");
        queryText.append("      m.mono_id = mmt.mono_id and mt.monotype_id = mmt.monotype_id and mt.monotype_code='I' ");
        queryText.append(") ");

        // SQL Query that specifies Generated Monographs.
        if (!includeProductMonographs) {
            queryText.append("AND ");
            queryText.append("    monospec_class_name = 'GeneratedMonograph'");
        }
        
		/*
		 * NB. Oracle is optimized so that the whole result set is not sorted; see:
		 *   http://www.oracle.com/technology/oramag/oracle/06-sep/o56asktom.html
		 * Tests with the order clause moved to the outer select had identical
		 * response times.
		 */
		if (isFrench)
			queryText.append("  ORDER BY LOWER(mono_name_fr)");
		else
			queryText.append("  ORDER BY LOWER(mono_name_eng)");

		//add row limiter (outer part of nested select)
		if (maxRows <= 0) maxRows = 500;
		queryText.append(" ) WHERE ROWNUM <= " + maxRows);

        log.debug("Monograph Search Materialized View query: " + queryText);
		
        long startTime = System.currentTimeMillis();
		results = getSession().createSQLQuery(queryText.toString()).list();

		long endTime = System.currentTimeMillis();
		long execTime = endTime - startTime; 
		log.debug("Monograph Search Materialized View execution time(ms):" + execTime);

		//convert the search results to IngredientSearchResults objects.
		return convertToSearchResults(results);

	}     


	/*
	 * Converts the result set returned by the MONOGRAPH_SYNONYMS_MV
	 * Materialized View into a List of MonographSearchResult objects.
	 */
	private List<MonographSearchResult> convertToSearchResults(List results)
	{
		List<MonographSearchResult> monographSearchResults = new ArrayList<MonographSearchResult>();

		for (int x=0;x<results.size();x++)
		{
			monographSearchResults.add(new MonographSearchResult((Object[])results.get(x)));
			//each row is represented as an Object[], which contains a BigDecimal
			//and 3 Strings which correspond to the returned columns 
		}
		return monographSearchResults;	
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
    
    private void log(String msg) {
    	log.debug(msg);
    }
    /***************************************************************************
	 * Gets the list of external Monograph names 
	 * Returns null if none is found.
	 * @param
	 */
	public List<PreClearedInfo> getPciList(String language ) throws HibernateException {
				
		boolean isFrench = isFrench(language);

        StringBuffer queryText = new StringBuffer();
		
		queryText.append("SELECT mono_id, mono_name FROM (SELECT DISTINCT ");
		
		queryText.append("m.mono_id as mono_id, m.monospec_class_name,"); 
		if (isFrench ) {
			queryText.append("  m.mono_name_fr as mono_name ");		
        } else {
			queryText.append("  m.mono_name_eng as mono_name ");		
        }
		
		queryText.append("FROM "); 
        queryText.append("  monographs m, ");
        queryText.append("  monograph_monograph_types mmt ");
        queryText.append("WHERE ");
        queryText.append("  m.mono_id = mmt.mono_id(+) AND ");
        queryText.append("  mmt.MONOTYPE_ID in (2,3) ");      
        
    	if (isFrench ) {
    		queryText.append("union select  m.mono_id, m.monospec_class_name, m.mono_name_fr from monographs m ");	
    		//queryText.append("union select  m.mono_name_fr from monographs m ");		
        } else {
        	queryText.append("union select  m.mono_id, m.monospec_class_name, m.mono_name_eng from monographs m ");	
        	//queryText.append("union select   m.mono_name_eng from monographs m ");
        }        
    	queryText.append (" where not exists (select 'x' from monograph_monograph_types where m.mono_id = mono_id))");
    	queryText.append (" where monospec_class_name = 'GeneratedMonograph'");
        queryText.append(" order by 2");
        
                 
		List resultsList = new ArrayList<PreClearedInfo>();
		
		resultsList = (List<PreClearedInfo>)getSession().createSQLQuery(queryText.toString()).list();
			
		//int size = resultsList.size();
		//String item = (String) resultsList.get(1);
		//PreClearedInfo item = (PreClearedInfo) resultsList.get(1);
		log.debug("Returned results - size " + resultsList.size());
		return (convertToPciInfo(resultsList));
	}

	private List<PreClearedInfo> convertToPciInfo(List resultsList) {
		// TODO Auto-generated method stub
		List<PreClearedInfo> preClearedInfoList = new ArrayList<PreClearedInfo>();
		
		for (int x=0;x<resultsList.size();x++){
			preClearedInfoList.add(new PreClearedInfo((Object[])resultsList.get(x)));
		}
		return preClearedInfoList;
	}
}
