package ca.gc.hc.nhpd.dao;

import ca.gc.hc.nhpd.exception.IngredientsException;
import ca.gc.hc.nhpd.dto.IngredientSearchResult;
import ca.gc.hc.nhpd.dto.IngredientSearchCriteria;
import ca.gc.hc.nhpd.dto.SourceOrganismPart;
import ca.gc.hc.nhpd.dto.RelatedSourceIngredient;
import ca.gc.hc.nhpd.dto.SearchCriteria;
import ca.gc.hc.nhpd.dto.SearchCriterion;
import ca.gc.hc.nhpd.model.Ingredient;
import ca.gc.hc.nhpd.model.HomeopathicSubstance;
import ca.gc.hc.nhpd.util.HibernateUtil;
import ca.gc.hc.nhpd.util.MapUtil;
import ca.gc.hc.nhpd.util.PagedList;
import ca.gc.hc.nhpd.util.SqlUtil;
import ca.gc.hc.nhpd.util.StringUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/*******************************************************************************
 * An object used to retrieve instances of Ingredient from persistent store.
 * @see ca.gc.hc.nhpd.model.Ingredient
 */
@Component
public class IngredientDao extends AbstractDao {

	private static final Log log = LogFactory.getLog(IngredientDao.class);

	@Autowired
	OrganismPartTypeDao organismPartTypeDao;
	
	@Autowired
	HomeopathicSubstanceDao homeopathicSubstanceDao;
	
	public static boolean LOAD_INTO_MEMORY = false;

    private static final String TILDA = "~";
    
    private static final String ID_START = "<id>";
    private static final String ID_END = "</id>";
    private static final String TP_START = "<tp>";
    private static final String TP_END = "</tp>";
    private static final String AP_START = "<ap>";
    private static final String AP_END = "</ap>";
	
	/* column names for "ingredient_synonyms_mv" Oracle Materialized View */
	private static final String roleColumn = "role_name";
	private static final String categoryCodeColumn = "category_code";
	private static final String classNameColumn = "class_name";

	private static final String englishMvColumns =
		"mv.english_name, mv.english_synonyms, mv.english_proper_names, " +
		"mv.english_common_names, mv.english_precleared_urls";
	private static final String frenchMvColumns =
		"mv.french_name, mv.french_synonyms, mv.french_proper_names, " +
		"mv.french_common_names, mv.french_precleared_urls";

	private static final int DEFAULT_MAX_ROWS = 100000;


	/***************************************************************************
	 * Generic Constructor.
	 */
	public IngredientDao() {
		super(Ingredient.class);
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
	 * Gets a collection of all the Ingredients in the persistent store.
	 * @return all instances of the Ingredients that are in the persistent store.
	 * @throws HibernateException if a problem is encountered.
	 */
	public List findAll() throws HibernateException {
		return findByCriteria(null, null);
	}

	/***************************************************************************
	 * Gets the Ingredient with the passed ID from the persistent store.
	 * Returns null if none is found.
	 * @param id the unique key of the Ingredient in the persistent store.
	 * @param lock true if the corresponding record should automatically be
	 *        locked from updates in the persistent store.
	 * @return the Ingredient, if any, with the passed ID in the persistent
	 *         store.
	 */
	public Object findById(Long id, boolean lock) {
		
		Object o = (Object)super.findByIdBase(id, lock);
		// testPrintNames(o);
		return o;
	}

    /***************************************************************************
     * Gets the Ingredient with the passed ID from the persistent store.
     * Returns null if none is found.
     * @param id the unique key of the Ingredient in the persistent store.
     * @param lock true if the corresponding record should automatically be
     *        locked from updates in the persistent store.
     * @param displayHomeopathicIngredient flag indicates if the homeopathic
     *        substance should be displayed.
     * @return the Ingredient, if any, with the passed ID in the persistent
     *         store.
     */
    public Ingredient findById(Long id, 
    						   boolean lock, 
    						   boolean displayHomeopathicIngredient
                               ) {
                         
        Ingredient i = (Ingredient)findById(id, lock);
        
        if (!displayHomeopathicIngredient &&
                i instanceof HomeopathicSubstance) {
            throw new IngredientsException
            	("Error: Web application not configured to display Homeopathic Ingredients");
        }
        
        return i;
    }

	/***************************************************************************
	 * Gets the Ingredients with the passed constraints from the persistent
	 * store. Returns an empty List if none are found that match.
	 * @param name a partial name that these users must have. This may contain a
	 *        literal value to searched on, or a partial string that uses wild
	 *        card searching (see SqlUtil.createQueryExpression() for details).
	 *        This looks for all matches in the ingredient's name, as well as in
	 *        all related common names organism groups, organisms, and CAS entry
	 *        codes (logical OR).
	 * @param role when applicable, the results are filtered to only include
	 *        Ingredients with the passed role subclass name. If null or -1,
	 *        this constraint is not applied.
	 * @param language the ISO 639.2 language to sort on (defaults to English).
	 * @return the Ingredients in the persistent store that match the passed
	 *         constraints.
	 */
	public PagedList findForFilter(String name, 
                                   String role, 
								   String language, 
                                   int maxRows,
                                   boolean displayHomeopathicIngredient) {
        
		boolean includeRole = (role != null && !("-1".equals(role)));
		String nameExpression = SqlUtil.createQueryExpression(name).toLowerCase();
		StringBuffer queryText = new StringBuffer();
		List results;
		
		//need a nested select to support rownum check at end.
		queryText.append("SELECT * from (");
		queryText.append("SELECT {i.*} ");
		queryText.append("FROM ingredients i ");
		queryText.append("WHERE i.ingred_id IN ( ");
		queryText.append("  SELECT DISTINCT ins.ingred_id ");
		queryText.append("  FROM ingredient_name_search_mv ins ");
		queryText.append("  WHERE ");
        
		if (isFrench(language)) {
			queryText.append(" LOWER(ins.french_name)" + nameExpression);
		} else {
			queryText.append(" LOWER(ins.english_name)" + nameExpression);
		}

		if (includeRole) {
			queryText.append(" AND ins.role_name = '" + role + "'");
		}
        
        if (!displayHomeopathicIngredient) {
            queryText.append(" AND ins.role_name != ");
            queryText.append("'" + IngredientSearchCriteria.HOMEOPATHICROLE + "'");
        }
        
		queryText.append("  ) ");

        if (isFrench(language)) {
			queryText.append("  ORDER BY LOWER(i.ingred_authorized_name_fr)");
		} else {
			queryText.append("  ORDER BY LOWER(i.ingred_authorized_name_eng)");
		}
		queryText.append(" ) WHERE ROWNUM <= " + maxRows); 	
		 
		log.debug("Query (findForFilter) : " + queryText.toString());
		
		results = getSession().createSQLQuery(queryText.toString()).
			addEntity("i", getModelClass()).list();

		return new PagedList(results);
	}

	/***************************************************************************
	 * Gets the Ingredients with the passed purpose from the persistent store.
	 * Returns an empty List if none are found that match.
	 * @param purposeId the id of the purpose that these Ingredients must have.
	 * @param language the ISO 639.2 language to sort on (defaults to English).
	 * @return the Ingredients in the persistent store that are used for the
	 *         passed purpose.
	 */
	public PagedList findForPurpose(String purposeId, String language) {
		StringBuffer queryText = new StringBuffer();
		List results;

		queryText.append("SELECT DISTINCT {i.*} ");
		queryText.append("FROM ingredients i ");
		queryText.append("  INNER JOIN ingredient_roles role ON i.ingred_id = role.ingred_id ");
		queryText.append("  INNER JOIN ingredient_role_purposes role_purp ON role.ingredrole_id = role_purp.ingredrole_id ");
		queryText.append("WHERE role_purp.purpose_id = '" + purposeId + "'");
        if (isFrench(language)) {
			queryText.append("  ORDER BY LOWER(i.ingred_authorized_name_fr)");
		} else {
			queryText.append("  ORDER BY LOWER(i.ingred_authorized_name_eng)");
		}

		log.debug("Query (findForPurpose) : " + queryText);

		results = getSession().createSQLQuery(queryText.toString()).
			addEntity("i", getModelClass()).list();
		
		return new PagedList(results);
	}

	/***************************************************************************
	 * Gets the Ingredients with the passed role name from the persistent store.
	 * Returns an empty List if none are found that match.
	 * @param role the role subclass name that these Ingredients must have.
	 * @param language the ISO 639.2 language to sort on (defaults to English).
	 * @return the Ingredients in the persistent store that have the passed
	 *         role.
	 */
	public PagedList findForRole(String roleName, String language) {
		StringBuffer queryText = new StringBuffer();
		List results;

		queryText.append("SELECT DISTINCT {i.*} ");
		queryText.append("FROM ingredients i ");
		queryText.append("  INNER JOIN ingredient_roles role ON i.ingred_id = role.ingred_id ");
		queryText.append("WHERE role.rolespec_class_name = '" + roleName + "'");


        if (isFrench(language)) {
			queryText.append("  ORDER BY LOWER(i.ingred_authorized_name_fr)");
		} else {
			queryText.append("  ORDER BY LOWER(i.ingred_authorized_name_eng)");
		}

		log.debug("Query (findForRole) : " + queryText);

		results = getSession().createSQLQuery(queryText.toString()).
			addEntity("i", getModelClass()).list();
		
		return new PagedList(results);
	}
	
	/* 
	 * Convenience query method which wraps the results in a PagedList
	 * Calls thru to the existing implementation.
	 */
	public PagedList searchIngredients(SearchCriteria criteria,
			 						   String language,
			 						   boolean includeProperNames,
                                       boolean includeCommonNames,
			 						   boolean includeHomeoPathicIngredients,
                                       boolean includeAblsInternalMonographs,
			 					   	   int maxRows) {
		return new PagedList(findBySearchCriteria(criteria,
							 language,
							 includeProperNames,
                             includeCommonNames,
							 includeHomeoPathicIngredients,
                             includeAblsInternalMonographs,
							 maxRows));
	}

	
	/***************************************************************************
	 * Method which satisfies Product License Application (PLA) requirements
	 * for the PLA Web Service.  The SearchCriteria contains name/value pairs
	 * for Role, Category, Name string, and Ingredient Class name (e.g. the
	 * Ingredient subclass).  Uses the Web Service specific Materialized View:
	 * "INGREDIENT_SYNONYMS_MV"
	 */
	public List<IngredientSearchResult> findBySearchCriteria(SearchCriteria criteria,
									 String language,
									 boolean includeProperNames,
                                     boolean includeCommonNames,
									 boolean includeHomeopathicIngredients,
                                     boolean includeAblsInternalMonographs,
									 int maxRows)
	{
        
		boolean french = isFrench(language);

		//check for compendial PLA search
		SearchCriterion searchCriterion =
			criteria.getCriterionByName(IngredientSearchCriteria.APPLICATIONTYPE);
		if (searchCriterion != null)
		{
			//Compendial/Compendial from Generated Monographs requires filtering
			String applicationType = searchCriterion.getAttributeValue();
            
            boolean generatedMonograph;
            boolean ablsMonograph;
            /*The version 1.4.4 ePLA has been retired, so the COMPENDIAL search
             * from ePLA 2.x should get both generated and non-generated monograph
             * ingredients.  The following boolean will capture this use case.
             */
            boolean allMonoIngredients;
            if (applicationType.equals(IngredientSearchCriteria.COMPENDIAL)) {
                allMonoIngredients = true;
                generatedMonograph = false;
                ablsMonograph = false;
            } else if (applicationType.equals(IngredientSearchCriteria.COMPENDIALGENERATED)) {
            	allMonoIngredients = false;
                generatedMonograph = true;
                ablsMonograph = false;
            } else if (applicationType.equals(IngredientSearchCriteria.ABLS)) {
            	allMonoIngredients = false;
                generatedMonograph = false;
                ablsMonograph = true;
            } else if (applicationType.equals(IngredientSearchCriteria.ABLSGENERATED)) {
            	allMonoIngredients = false;
                generatedMonograph = true;
                ablsMonograph = true;
            } else {
                return null;
            }
            return invokeCompendialSearch(criteria, 
									      french, 
										  maxRows,
										  allMonoIngredients,
                                          generatedMonograph,
                                          ablsMonograph,
                                          includeAblsInternalMonographs);
			
		}

		// Override the includeHomeopathicIngredients flag.
		// This is used by the web services. 
		SearchCriterion displayHomeopathicCriterion =
			criteria.getCriterionByName(IngredientSearchCriteria.DISPLAY_HOMEOPATHIC);
		if (displayHomeopathicCriterion != null)
		{
			String attribute = displayHomeopathicCriterion.getAttributeValue();
			
			if (attribute.toLowerCase().equals(IngredientSearchCriteria.TRUE)) {
				includeHomeopathicIngredients = true;
			} else {
				includeHomeopathicIngredients = false;
			}
		}
		
		// Determin if the NHPSASChangedDate is being used.
		SearchCriterion displayNHPSASChangedDateCriterion =
			criteria.getCriterionByName(IngredientSearchCriteria.NHPSASCHANGEDATE);
		String nhpsasChangedDate = null;
		if (displayNHPSASChangedDateCriterion != null)
		{
			String attribute = displayNHPSASChangedDateCriterion.getAttributeValue();
			maxRows = -1; // override max rows to return a complete list.
			nhpsasChangedDate = formatDateForSQL(attribute);
			includeHomeopathicIngredients = true;
		}
		
		
		// When the HMN category is requested, override the 
		// includeHomeopathicIngredients flag and display Homeopathic
		// ingredients in the search results.
		// This is used by the web services.
		String[] categoryArray = getCriteriaValuesForName(IngredientSearchCriteria.CATEGORY,criteria);
		if (categoryArray != null && categoryArray.length > 0) {
			List<String> categoryList = Arrays.asList(categoryArray);
			if (categoryList.contains(IngredientSearchCriteria.HMN)) {
				includeHomeopathicIngredients = true;
			}
		}
		
		//regular search (application type not provided)
		return invokeIngredientSynonymsMv(criteria, 
										  french,
										  includeProperNames,
                                          includeCommonNames,
										  includeHomeopathicIngredients,
                                          includeAblsInternalMonographs,
										  nhpsasChangedDate,
										  maxRows);

	}

	/***************************************************************************
	 * JH: 2013-04-05: During the WCAG exercises, the 1.4.4 form was retired,
	 * and single ingredient generated monograph ingredients were made available
	 * on the Compendial application type of form 2.x.
	 * For Ingredient searches on "Compendial" application type, then, the 
	 * result set needs to include ingredients from both the generated and non-
	 * generated monographs.  This method builds a query which joins the
	 * Ingredient_Synonoyms_MV materialized view to the Monograph_Entries table.
	 *
	 * Query Text for all monograph ingredients looks like:
	 * ----------------------------------------------------
	 * SELECT * from (
     * 	SELECT DISTINCT	mv.ingred_id,
                    	mv.english_name,
                    	mv.english_synonyms,
                    	mv.english_proper_names,
                    	mv.english_common_names,
                    	mv.english_precleared_urls,
                    	mv.category_code
     * 	FROM	ingredient_synonyms_mv mv,
     * 			monograph_entries me,
     *			monographs m
     * 	WHERE	m.mono_id not in    
     * 			(	select 	m.mono_id    
     * 				  from	monographs m,
     * 						monograph_monograph_types mmt,
     * 						monograph_types mt
     * 				 where	m.mono_id = mmt.mono_id
     * 			 	  and	mt.monotype_id = mmt.monotype_id
     *			 	  and	(mt.monotype_code='A'
     *						 or
     *						 mt.monotype_code='I')
     *			)
     * 	AND	me.ingred_id=mv.ingred_id
     * 	AND	me.mono_id=m.mono_id
     * 	AND	(m.monospec_class_name='GeneratedMonograph'
     * 		 OR
     * 		 m.monospec_class_name='ExternalMonograph'));
     *  );
	 *
	 * @param searchCriteria - see findBySearchCriteria parameters
	 * @param isFrench - language indicator default = false = English
	 * @param maxRows - maximum number of rows to return; -1 means no limit
	 */
	private List<IngredientSearchResult> invokeCompendialSearch(SearchCriteria searchCriteria,
										boolean isFrench,
										int maxRows,
										boolean allMonoIngredients,
                                        boolean generatedMonograph,
                                        boolean ablsMonograph,
                                        boolean includeAblsInternalMonographs)
	{
		
		StringBuffer queryText = new StringBuffer();

		//need a nested select to support rownum check at end.
		queryText.append("SELECT * from (");
		queryText.append("SELECT DISTINCT ");
		queryText.append("mv.ingred_id, ");
		if (isFrench)
			queryText.append(frenchMvColumns);
		else
			queryText.append(englishMvColumns);
		queryText.append(", mv.category_code ");
		queryText.append("FROM ");
        queryText.append("  ingredient_synonyms_mv mv, ");
        queryText.append("  monograph_entries me, ");
        queryText.append("  monographs m ");
        queryText.append("WHERE ");
        if (ablsMonograph) {
            queryText.append("  m.mono_id in ( ");
        } else {
            queryText.append("  m.mono_id not in ( ");
        }
        queryText.append("    select m.mono_id from "); 
        queryText.append("      monographs m, monograph_monograph_types mmt, monograph_types mt "); 
        queryText.append("    where ");
        queryText.append("      m.mono_id = mmt.mono_id and mt.monotype_id = mmt.monotype_id and (mt.monotype_code='A' ");
        // Added July 22nd to prevent monographs with type code of 'I' from showing up.
        queryText.append("      or mt.monotype_code='I') ");
        queryText.append(") AND ");
        queryText.append("  me.ingred_id=mv.ingred_id AND ");
        queryText.append("  me.mono_id=m.mono_id AND ");

        if (generatedMonograph) {
            queryText.append("  m.monospec_class_name='GeneratedMonograph' AND ");
        // JH2013-06-18 - see comments for 2013-04-05: this will build the
        // new compendial response for ePLA 2.3.0 (WCAG release).  The response
        // contains compendial and CfGM ingredients.
        } else if (allMonoIngredients){
            queryText.append("  (m.monospec_class_name='GeneratedMonograph' OR ");
            queryText.append("  m.monospec_class_name='ExternalMonograph') AND ");
        } else {
            queryText.append("  m.monospec_class_name='ExternalMonograph' AND ");
        }
        
	    //build the where clause from the contents of the search criteria
		//TODO revisit - do we still need all the filter clauses?
		boolean clauseAdded = addFilterClauses(queryText,searchCriteria);

		//Add the text String.  For now, only one text string is supported
		String[] textValues =
			getCriteriaValuesForName(IngredientSearchCriteria.NAMESTRING,
									 searchCriteria);

		//Build the SQL "LIKE" clause.
		if (textValues != null) {
			appendLikeClause(queryText,
							 textValues[0].toLowerCase(),
							 false,
							 null,
							 clauseAdded,
							 isFrench);
		}

		appendOrderByClause(queryText,isFrench,maxRows);

		log.debug("CS Query Text (invokeCompendialSearch) : " + queryText);
		
		List results = executeMaterializedViewQuery(queryText,
													"Compendial Search query ");

		//convert the search results to IngredientSearchResults objects.
		return convertToIngredientSearchResults(results, isFrench, false, false, includeAblsInternalMonographs);

	}
	
	
	/*
	 * RelatedSourceIngredients_MV introduced to speed up retrieval of related
	 * source ingredients, which are parent ingredients of the subingredients 
	 * associated to an ingredient.  The RelatedSourceIngredient DTO contains 
	 * the related source ingredient E/F names and synonyms.  The query may 
	 * return several RelatedSourceIngredient objects. 
	 */
	public List<RelatedSourceIngredient> 
		getRelatedSourceIngredients(Long ingredientId,boolean french) {

        String materializedViewName = "RelatedSourceIngredients_mv";
		StringBuffer queryText = new StringBuffer();

        queryText.append("SELECT * FROM " + materializedViewName);
        queryText.append(" WHERE ingred_id = " + ingredientId);
        log.debug("QueryText: " + queryText);
        
        List results = executeMaterializedViewQuery(queryText,
        											materializedViewName);
        
        //convert the search results to RelatedSourceIngredient DTOs.
        return convertToRelatedSourceIngredients(results, french);
                                                
    }

	private List<RelatedSourceIngredient> 
		convertToRelatedSourceIngredients(List results, boolean french) {
	
		if (results == null || results.size() < 1) {
			return null;
		}
		
		ArrayList<RelatedSourceIngredient> retRsis = 
		   	new ArrayList<RelatedSourceIngredient>(); 
		for (Object result: results) { 
			 Object[] columns = (Object[])result;
			 retRsis.add(new RelatedSourceIngredient(columns, french));
		}
		
	    return retRsis;
	 	
	}
	
	
	/***************************************************************************
	 * Invokes the Oracle Materialized View (MV) which retrieves all
	 * Synonyms of Ingredients that match the specified search criteria.
	 * MV was introduced to maximize performance for the PLA Web Service.
	 *
	 * Refactored Aug 22/2007 to allow for multiple search criteria
	 * attribute values for a single criteria type:
	 *     e.g. CATEGORY=AHS; CATEGORY=ABS; CATEGORY=AFN.
	 * This type of search is translated into an SQL 'ANY' clause, e.g.
	 *    ...where category_code = ANY ('AHS','ABS','AFN')
	 *
	 * NB. only one text search string parameter is currently supported.
	 *
	 * @param searchCriteria - see findBySearchCriteria parameters
	 * @param maxRows - maximum number of rows to return; -1 means no limit
	 *
	 */
	private List<IngredientSearchResult> invokeIngredientSynonymsMv(SearchCriteria searchCriteria,
			boolean isFrench,
			boolean includeProperNames,
            boolean includeCommonNames,
			boolean includeHomeopathicIngredients,
            boolean includeAblsInternalMonographs,
			String nhpsasChangedDate,
			int maxRows)
	{

        String mvName = "ingredient_synonyms_mv";
		StringBuffer queryText = new StringBuffer();

		//need a nested select to support rownum check at end.
		queryText.append("SELECT * from (");
		queryText.append("SELECT DISTINCT ");
		queryText.append(  "mv.ingred_id, ");

		if (isFrench) {
			queryText.append(frenchMvColumns);
        } else {
			queryText.append(englishMvColumns);
        }

		queryText.append(", mv.category_code");
		queryText.append(" FROM ingredient_synonyms_mv mv WHERE ");
		
		//build the where clause from the contents of the search criteria
		boolean clauseAdded = addFilterClauses(queryText,searchCriteria);
		
		if (!includeHomeopathicIngredients) {
			if (clauseAdded) queryText.append(" and ");
			queryText.append(" mv.category_code != 'HMN' ");
		}

		// Add the nhpsasChangedDate clause.
		if (nhpsasChangedDate != null) {
			if (clauseAdded || !includeHomeopathicIngredients) {
				queryText.append(" and ");
			}
			queryText.append(" ingred_id in ( ");
			queryText.append("   select ingred_id ");
			queryText.append("   from NHPSAS_INGREDIENT_SYNC ");
			queryText.append(" where nhpsas_ingred_lastupdate_date>=TO_DATE('" + nhpsasChangedDate + "','YYYY/MM/DD'))");
		}
		
		//Add the text String.  For now, only one text string is supported
		String[] textValues =
			getCriteriaValuesForName(IngredientSearchCriteria.NAMESTRING,
					searchCriteria);

		//NB.  Search terms are language specific
		if (textValues != null)
		{
			appendLikeClause(queryText,
							 textValues[0].toLowerCase(),
							 !includeHomeopathicIngredients,
							 nhpsasChangedDate,
							 clauseAdded,
							 isFrench);
		}
		appendOrderByClause(queryText,isFrench,maxRows);
		
		// Used only with the nhpsasChangeDate, 
		// include any record who's ID has been deleted.
		if (nhpsasChangedDate != null) {
			queryText.append(" union ");
			queryText.append(" select ingred_id, null, null, null, null, null, null ");
			queryText.append(" from NHPSAS_INGREDIENT_SYNC ");
			queryText.append(" where NHPSAS_INGRED_HASH is null and ");
			queryText.append(" nhpsas_ingred_lastupdate_date>=TO_DATE('" + nhpsasChangedDate + "','YYYY/MM/DD')");
		}

        log.debug("QueryText (invokeIngredientSynonymsMv) : " + queryText);
        
		List results = executeMaterializedViewQuery(queryText,mvName);

		//convert the search results to IngredientSearchResults objects.
		return convertToIngredientSearchResults(results,
												isFrench,
												includeProperNames,
                                                includeCommonNames,
                                                includeAblsInternalMonographs);
												
	}
	
    /*
     * Constructs the LIKE clause.  Invokes special handling to build wildcard
     * LIKE clause in case of "begins with" | "ends with" searches
     * on delimited data.
     */
	private void appendLikeClause(StringBuffer queryText,
								  String queryString,
								  boolean includeHomeopathicIngredients,
								  String nhpsasChangedDate,
								  boolean clauseAdded,
								  boolean isFrench)
	{
		//NB.  Search terms are language specific
		if (clauseAdded || includeHomeopathicIngredients || nhpsasChangedDate != null) {
			queryText.append(" AND ");
		}
		if (isFrench)
			queryText.append(" LOWER(mv.french_searchterm)");
		else
			queryText.append(" LOWER(mv.english_searchterm)");

        queryText.append(SqlUtil.createDelimitedQueryExpression(queryString));
	}

	private void appendOrderByClause(StringBuffer queryText,
			  						 boolean isFrench,
			  						 int maxRows)
	{

		/*
		 * NB. Oracle is optimized such that the whole result set
		 * does not get sorted - see:
		 *   http://www.oracle.com/technology/oramag/oracle/06-sep/o56asktom.html
		 * Tests with the order clause moved to the outer select had
		 * identical response times.
		 */
		if (isFrench)
			queryText.append("  ORDER BY LOWER(mv.french_name)");
		else
			queryText.append("  ORDER BY LOWER(mv.english_name)");

		//no limit on a query with -1 maxRows - Required for NHPSAS.
		if (maxRows == -1) {
			queryText.append(" )");
		} else {
			// If 0 or smaller, or larger than DefaultMaxRows then set to DefaultMaxRows
			if (maxRows <= 0 || maxRows > DEFAULT_MAX_ROWS) { 
				maxRows = DEFAULT_MAX_ROWS;
			}
			queryText.append(" ) WHERE ROWNUM <= " + maxRows);
		}
	}

	private boolean addFilterClauses(StringBuffer queryText,
			SearchCriteria searchCriteria)
	{

		boolean clauseAdded = false;
		clauseAdded = addFilterClause(queryText,
				searchCriteria,
				IngredientSearchCriteria.ROLE,
				roleColumn,
				clauseAdded);

		clauseAdded = addFilterClause(queryText,
				searchCriteria,
				IngredientSearchCriteria.CATEGORY,
				categoryCodeColumn,
				clauseAdded);

		clauseAdded = addFilterClause(queryText,
				searchCriteria,
				IngredientSearchCriteria.CLASSNAME,
				classNameColumn,
				clauseAdded);

		return clauseAdded;

	}

	/***************************************************************************
	 * Constructs and adds the search filter criteria to the SQL query.
	 * For cases where multiple values are provided for a single search
	 * criteria, an SQL 'ANY' clause is built.
	 *
	 * @return boolean flag indicating if a clause has beed added; this allows
	 * the caller to determine if an 'AND' keyword is required.
	 */
	private boolean addFilterClause(StringBuffer queryText,
			SearchCriteria criteria,
			String attributeName,
			String columnName,
			boolean clauseAdded)
	{

		StringBuffer filterClause = new StringBuffer();

		String[] criteriaValues = getCriteriaValuesForName(attributeName,criteria);
		if (criteriaValues == null)
			return clauseAdded;   //nothing added - flag stays the same

		if (clauseAdded) filterClause.append(" AND " );
		if (criteriaValues.length > 1) {
			//builds an SQL 'ANY' clause
			filterClause.append(columnName + " = " + SqlUtil.buildAnyParms(criteriaValues));
		}
		else {
			//single criterion for this attribute name
			filterClause.append(columnName + " = '" + criteriaValues[0] + "'");
		}

		queryText.append(filterClause);
		return true;

	}

	private List executeMaterializedViewQuery(StringBuffer queryText,
											  String materializedViewName) {
		List results = null;

		log.debug("WS Materialized View query: " + queryText);
		
		long startTime = System.currentTimeMillis();

		results = getSession().createSQLQuery(queryText.toString()).list();

		long execTime = System.currentTimeMillis() - startTime;
		log.debug(materializedViewName + " returned " + results.size() + 
			      " records in " + execTime + "ms.");
		
		return results;
	}

	/***************************************************************************
	 * Converts the result set returned by the INGREDIENT_SYNONYMS_MV
	 * Materialized View into a List of IngredientSearchResult objects.
	 * Each row is represented as an Object[], which contains the 7 columns
	 * we are interested in.  The last 3 columns are used to determine if
	 * the Ingredient is associated to a Generated Monograph.
	 * 
	 */
	
	private List<IngredientSearchResult> 
		convertToIngredientSearchResults(List results,
									     boolean isFrench,
									     boolean includeProperNames,
                                         boolean includeCommonNames,
                                         boolean includeAblsInternalMonographs)
	{
		List<IngredientSearchResult> ingredientSearchResults = new ArrayList<IngredientSearchResult>();
		Object[] columns = null;
		IngredientSearchResult ingredientSearchResult = null;
		int dups = 0;

		for (int x=0;x<results.size();x++)
		{
			columns = (Object[])results.get(x);
			ingredientSearchResult =
				new IngredientSearchResult(columns,isFrench,includeProperNames, includeAblsInternalMonographs);
			
			//Temp hack to test for duplicates - need to refine query
			//This will be refactored as part of Monograph object model
			//refactoring.
			if (!ingredientSearchResults.contains(ingredientSearchResult))
			{
				ingredientSearchResults.add(ingredientSearchResult);
			}
			else dups++;

		}
		//log ("found " + dups + " dups");
		return ingredientSearchResults;
	}

	/**
	 * FormatDateForSQL tries to take a date from this format:
	 * yyyy-MM-dd, and reformat it to the yyyy/MM/dd format
	 * prefered by SQL.
	 * 
     * Returns a null value when the date can't be converted.
     * 
	 * @param dateValue
	 * 
	 * @return a date formatted for SQL
	 */
	private String formatDateForSQL(String dateValue) {
		try {
			// Convert the string to a date.
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = (Date) formatter.parse(dateValue);
			// Convert the date back to a string.
			Format newFormatter = new SimpleDateFormat("yyyy/MM/dd");
			return newFormatter.format(date);
		} catch (Exception e) {
            // deliberate exception handling on my part.  The null will
            // be returned when the date can not be handled.
            return null;
		}
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
        queryText.append("i.ingred_id, ");
        
        if (isFrench) {
            queryText.append("i.ingred_authorized_name_fr ");
        } else {
            queryText.append("i.ingred_authorized_name_eng ");
        }
        
        queryText.append("FROM ");
        queryText.append("ingredients i ");

        // NB. Search terms are language specific
        if (searchString != null && !searchString.equals(EMPTY_STRING)) {
            queryText.append("WHERE ");
            if (isFrench) {
                queryText.append("LOWER(i.ingred_authorized_name_fr) ");
            } else {
                queryText.append("LOWER(i.ingred_authorized_name_eng) ");
            }
            String likeClause = (SqlUtil.createQueryExpression(searchString));
            queryText.append(likeClause);
        }

        if (isFrench) {
            queryText.append("ORDER BY LOWER(i.ingred_authorized_name_fr) ");
        } else {
            queryText.append("ORDER BY LOWER(i.ingred_authorized_name_eng) ");
        }
        queryText.append(")");
        
        return queryText;
    }
    
    /**
     * This function returns a list of source ingredients for a given ingredient.
     * Support the isFrench boolean to return the organism part information in the 
     * requested language.  Will return null if the ingredientId is null or the 
     * return list is empty.  List is alpha sorted.
     *
     * @param ingredientId
     * @param isFrench
     * 
     * @return
     */
    public List<String> findSourceIngredients(
               Long ingredientId, boolean isFrench)
   {

        List<String> sourceIngredients = new ArrayList<String>();
        
       if (ingredientId == null) {
           return sourceIngredients;
       }
       
       StringBuffer queryText = new StringBuffer();

       queryText.append("SELECT DISTINCT ");
       queryText.append("  op.orgparttype_id, ");
       queryText.append("  o.organism_name, ");
       if (isFrench) {
           queryText.append("  opt.orgparttype_name_fr ");
       } else {
           queryText.append("  opt.orgparttype_name_eng ");
       };
       queryText.append("FROM ");
       queryText.append("  subingredients su, ");
       queryText.append("  organism_part_subingredients ops, ");
       queryText.append("  organism_parts op, ");
       queryText.append("  organism_part_types opt, ");
       queryText.append("  organisms o ");
       queryText.append("WHERE ");
       queryText.append("  ops.subingred_id = su.subingred_id and ");
       queryText.append("  op.orgpart_id = ops.orgpart_id and ");
       queryText.append("  o.organism_id = op.organism_id and ");
       queryText.append("  opt.orgparttype_id = op.orgparttype_id and ");
       queryText.append("  su.ingred_id = ");
       queryText.append(ingredientId);
       
       List<Object[]> results = getSession().createSQLQuery(queryText.toString()).list();
       
       for (Object[] result : results) {
           BigDecimal organismPartId = (BigDecimal) result[0];
           String organismName = (String) result[1];
           String organismPart = (String) result[2];
           StringBuilder sb = new StringBuilder();
           sb.append(organismName).append(SPACE).append(DASH).append(SPACE).append(organismPart);
           sourceIngredients.add(sb.toString());
       }
       
       // Sort the value.
       Collections.sort(sourceIngredients);
       
       return sourceIngredients;
                                               
    }

    /**
     * Finds the source material using the organism parts relationship.  Used 
     * to build the source material list.
     * 
     * @param ingredientId
     * @param isFrench
     * @return
     */
    private List<String> findSourceMaterialUsingOrganismPartsRelationship(Long ingredientId, boolean isFrench) {

        List<String> sourceMaterialUsingOrganismPartsRelationship = new ArrayList<String>();
        
        if (ingredientId == null) {
            return sourceMaterialUsingOrganismPartsRelationship;
        }
        
        StringBuffer queryText = new StringBuffer();

        queryText.append("SELECT DISTINCT ");
        queryText.append("  o.organism_name, ");
        if (isFrench) {
            queryText.append("  opt.orgparttype_name_fr ");
        } else {
            queryText.append("  opt.orgparttype_name_eng ");
        };
        queryText.append("FROM ");
        queryText.append("  ingredient_orgparts io, ");
        queryText.append("  organism_parts op, ");
        queryText.append("  organisms o, ");
        queryText.append("  organism_part_types opt ");
        queryText.append("WHERE ");
        queryText.append("  op.orgparttype_id = opt.orgparttype_id and ");
        queryText.append("  op.organism_id = o.organism_id and ");
        queryText.append("  op.orgpart_id = io.orgpart_id and ");
        queryText.append("  io.ingred_id = ");
        queryText.append(ingredientId);
        
        List<Object[]> results = getSession().createSQLQuery(queryText.toString()).list();
        
        for (Object[] result : results) {
            String organismName = (String) result[0];
            String organismPart = (String) result[1];
            if (organismPart.equals("Whole plant") || organismPart.equals("Plante enti�re")) {
                // Add all the other organism parts when dealing with a whole plant.
                List<String> otherOrganismParts = organismPartTypeDao.findByTypeGroup(1L, isFrench);
                for (String otherOrganismPart : otherOrganismParts) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(organismName).append(SPACE).append(DASH).append(SPACE).append(otherOrganismPart);
                    sourceMaterialUsingOrganismPartsRelationship.add(sb.toString());
                }
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(organismName).append(SPACE).append(DASH).append(SPACE).append(organismPart);
                sourceMaterialUsingOrganismPartsRelationship.add(sb.toString());
            }
        }
        
        return sourceMaterialUsingOrganismPartsRelationship;

    }
    
    
    /**
     * This function returns a list of sub ingredients for a given ingredient.
     * Will return null if the ingredientId is null.  Will return an empty list
     * if no sub-ingredients are found.
     *
     * @param ingredientId
     * 
     * @return
     */
    private List<Long> findSubingredientIds(
               Long ingredientId)
   {

       if (ingredientId == null) {
           return null;
       }
       
       StringBuffer queryText = new StringBuffer();

       queryText.append("SELECT DISTINCT ");
       queryText.append("  su.ingred_id ");
       queryText.append("FROM ");
       queryText.append("  ingredient_subingredients si, ");
       queryText.append("  subingredients su ");
       queryText.append("WHERE ");
       queryText.append("  su.subingred_id = si.subingred_id and ");
       queryText.append("  si.ingred_id = ");
       queryText.append(ingredientId);
       
       List results = getSession().createSQLQuery(queryText.toString()).list();
       
       List<Long> ingredientIds = new ArrayList<Long>();
       for (Object result : results) {
           BigDecimal soureceIngredientId = (BigDecimal) result;
           ingredientIds.add(soureceIngredientId.longValue());
       }
       
       return ingredientIds;
                                               
    }

    /**
     * For a given ingredient, finds all the source materials then returns 
     * them in a sorted list.  Will return a null if no source material is
     * found or if the ingredientId is null. 
     */
    public List<String> findSourceMaterials(
            Long ingredientId, boolean isFrench) {

        List<String> sourceMaterials = new ArrayList<String>();
        
        // Load from ingredient.
        sourceMaterials.addAll(findSourceIngredients(ingredientId, isFrench));
        
        // Load from Subingredients.
        List<Long> subingredientIds = findSubingredientIds(ingredientId);
        for (Long subingredientId : subingredientIds) {
            sourceMaterials.addAll(findSourceIngredients(subingredientId, isFrench));
        }

        // Load from Organism Parts.
        sourceMaterials.addAll(findSourceMaterialUsingOrganismPartsRelationship(ingredientId, isFrench));

        // Load from Homeopathic Substance
        sourceMaterials.addAll(homeopathicSubstanceDao.findHomeopathicSourceMaterials(ingredientId, isFrench));
        
        if (sourceMaterials.size() == 0) {
            return null;
        }
        
        // Sort the value.
        Collections.sort(sourceMaterials);
        
        return sourceMaterials;
        
    }
    
    /**
     * This function is used to find the constituents for a 
     * Defined Organism Substance.
     *
     * @param ingredientId
     * @param isFrench
     * @return
     */
    private List<String> findDefinedOrganismConstituents(
            Long ingredientId, boolean isFrench)
    {

        if (ingredientId == null) {
            return new ArrayList<String>();
        }
    
        StringBuffer queryText = new StringBuffer();

        queryText.append("SELECT DISTINCT ");
        if (isFrench) {
            queryText.append("  i.ingred_authorized_name_fr ");
        } else {
            queryText.append("  i.ingred_authorized_name_eng ");
        };
        queryText.append("FROM ");
        queryText.append("  ingredient_orgparts io, ");
        queryText.append("  organism_parts op, ");
        queryText.append("  organism_parts op2, ");
        queryText.append("  organisms o, ");
        queryText.append("  organism_part_types opt, ");
        queryText.append("  organism_part_types opt2, ");
        queryText.append("  organism_part_subingredients ops, ");
        queryText.append("  subingredients s, ");
        queryText.append("  ingredients i ");
        queryText.append("WHERE ");
        queryText.append("  op.orgparttype_id = opt.orgparttype_id and ");
        queryText.append("  op.organism_id = o.organism_id and ");
        queryText.append("  o.organism_id = op2.organism_id and ");
        queryText.append("  op2.orgpart_id = ops.orgpart_id and ");
        queryText.append("  op2.orgparttype_id = opt2.orgparttype_id and ");
        queryText.append("  s.subingred_id = ops.subingred_id and ");
        queryText.append("  i.ingred_id = s.ingred_id and ");
        queryText.append("  op.orgpart_id = io.orgpart_id and ");
        queryText.append("  io.ingred_id = ");
        queryText.append(ingredientId);
    
        List<String> results = getSession().createSQLQuery(queryText.toString()).list();
    
        return results;
                                            
    }

    /**
     * This function is used to find the constituents for a 
     * Chemical Substance.
     *
     * @param ingredientId
     * @param isFrench
     * @return
     */
    private List<String> findChemicalConstituents(
            Long ingredientId, boolean isFrench)
    {

        if (ingredientId == null) {
            return new ArrayList<String>();
        }
    
        StringBuffer queryText = new StringBuffer();

        queryText.append("SELECT DISTINCT ");
        if (isFrench) {
            queryText.append("  i.ingred_authorized_name_fr ");
        } else {
            queryText.append("  i.ingred_authorized_name_eng ");
        };
        queryText.append("FROM ");
        queryText.append("  ingredient_subingredients isu, ");
        queryText.append("  subingredients su, ");
        queryText.append("  ingredients i ");
        queryText.append("WHERE ");
        queryText.append("  isu.subingred_id = su.subingred_id and ");
        queryText.append("  i.ingred_id=su.ingred_id and ");
        queryText.append("  isu.ingred_id = ");
        queryText.append(ingredientId);
    
        List<String> results = getSession().createSQLQuery(queryText.toString()).list();
        
        return results;
                                            
    }

    /**
     * For any given ingredient, finds all the constituents and then returns 
     * them as a sorted list.  Used by the ANTool to populate the source 
     * ingredient list.  Will return a null if no constituents are present
     * or if the ingredientId supplied is null. 
     */
    public List<String> findConstituents(
            Long ingredientId, boolean isFrench) {

        List<String> constituents = new ArrayList<String>();
        
        // Load from ingredient.
        constituents.addAll(findChemicalConstituents(ingredientId, isFrench));
        constituents.addAll(findDefinedOrganismConstituents(ingredientId, isFrench));
        
        if (constituents.size() == 0) {
            return null;
        }
        
        // Sort the value.
        Collections.sort(constituents);
        
        return constituents;
        
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
