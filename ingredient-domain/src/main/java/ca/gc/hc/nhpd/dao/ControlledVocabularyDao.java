package ca.gc.hc.nhpd.dao;

import ca.gc.hc.nhpd.conf.SpringContext;
import ca.gc.hc.nhpd.dto.VocabularySearchCriteria;
import ca.gc.hc.nhpd.exception.IngredientsException;
import ca.gc.hc.nhpd.model.Country;
import ca.gc.hc.nhpd.model.MultiNamedObject;
import ca.gc.hc.nhpd.util.HibernateUtil;
import ca.gc.hc.nhpd.util.PagedList;
import ca.gc.hc.nhpd.util.SqlUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;

import java.util.List;
import java.util.Locale;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.sql.Statement;

/***************************************************************************
 * Data Access Object used to query for Controlled Vocabulary objects.
 * Controlled Vocabulary objects correspond to database "code tables"; they
 * include model objects such as DoageForm, Chemical Class, NonMedicinalPurpose,
 * etc.  Hibernate query Language queries are used to search for Controlled
 * Vocabulary object, filtering on the name and synonyms (if present) 
 * 
 */
public class ControlledVocabularyDao {

	
	static public final String LANG_FR = Locale.CANADA_FRENCH.getISO3Language(); // ISO 639.2
	
	private static final Log log = LogFactory.getLog(ControlledVocabularyDao.class);

	/**************************************************************************
	 * Does search on selected code tables, filtering on a search string.
	 * If the model class contains synonyms, the synonyms are also searched.
	 * @param modelClassName Class name of model object which implements the
	 * 		  ControlledVocabularyObject interface
	 * @param searchText text string used for filtering queries.  May contain 
	 * 		  "*" as wildcard
	 * @param language ISO 639 language code 
	 * @return collection of controlled vocabulary objects mathcing the sear
	 * criteria
	 */ 
	public PagedList searchControlledVocabulary(String modelClassName,
											    String searchText, 
											    String language,
											    HashMap searchFields,
											    HashMap sortOrder)
	{
		//if there's no search text, return them all.
		if ( (searchText == null) || 
				(searchText.trim().equals("")) || 
				(searchText.trim().equals("*")) ) 
			return executeSimpleQuery(modelClassName, 
					searchFields, sortOrder);

		//get the model class
		Object object = null;
		try {
			Class clazz = Class.forName("ca.gc.hc.nhpd.model." + modelClassName); 
			object = clazz.newInstance();
		}
		catch (Exception ex) {
			throw new IngredientsException(ex);
		}

		//build an SQL compatible search string
		String sqlSearchString = SqlUtil.createQueryExpression(searchText);
		
		//Object does not have synonyms: filter on name only 
		log.debug("MCN:  " + modelClassName 
			+ "  SS: " + sqlSearchString
			+ "  SearchFields: " + searchFields
			+ "  SortOrder: " + sortOrder);
			 
		return executeVocabularyQuery(modelClassName,
				sqlSearchString, searchFields, sortOrder);	

	}

	/**************************************************************************
	 * Executes simple HQL query which retrieves all Controlled Vocabulary 
	 * objects.  Objects may or may not have synonyms.
	 * @param modelClassName Class name of model object which implements the
	 * 		  ControlledVocabularyObject interface
	 * @param nameColumn language specific name attribute used in the query 
	 * 		  "order by" clause 
	 * @return collection of all controlled vocabulary objects of the specified 
	 * 		   model class
	 */ 
	private PagedList executeSimpleQuery(String modelClassName, HashMap searchFields,
			HashMap sortOrder)
	{
		String queryString = "";
		
		List sortList = (ArrayList)sortOrder.get(modelClassName);
		Iterator sortItr = sortList.iterator();
		
		if (modelClassName.equals("TestMethod")){
		//Test methods are a special case because we want to sort on
		//test method type and subtype as well as name.
			
			List fieldList = (ArrayList)searchFields.get(modelClassName);
			List joinList = (ArrayList)searchFields.get(modelClassName + "Joins");
			Iterator fieldItr = fieldList.iterator();
			
			queryString  = "select cvo from " + modelClassName + " cvo";
			
			if (joinList != null){
				Iterator joinItr = joinList.iterator();
				int x = 1;
				while (joinItr.hasNext()){
					String join = (String)joinItr.next();
					queryString += " left join cvo." + join + " " + join;	
				}
			}
			
			//process the first sort item
			if(sortItr.hasNext()){
				VocabularySearchCriteria vs = (VocabularySearchCriteria)sortItr.next();
				queryString += " order by " + vs.getSearchSynonym() + "." +  vs.getSrchField();
			}
			
			//process and additional sort fields
			while(sortItr.hasNext()){
				VocabularySearchCriteria vs = (VocabularySearchCriteria)sortItr.next();
				queryString += ", " + vs.getSearchSynonym() + "." +  vs.getSrchField();
			}
			
		}else{
			//generic HQL query for Controlled Vocabulary objects 	
			queryString  = "from " + modelClassName;

            // Only show the visible preparation types.
            if (modelClassName.equals("PreparationType")){
                queryString += " where lower(visible)='y' ";
            }

			//process the first sort item
			if(sortItr.hasNext()){
				VocabularySearchCriteria vs = (VocabularySearchCriteria)sortItr.next();
				queryString += " order by " + vs.getSrchField();
			}
			
			//process any additional sort fields
			while(sortItr.hasNext()){
				VocabularySearchCriteria vs = (VocabularySearchCriteria)sortItr.next();
				queryString += ", " + vs.getSrchField();
			}			
		}
        
		//log(queryString);
		List results = executeHqlQuery(queryString);
		
		return new PagedList(results);

	}
	
	/**************************************************************************
	 * Executes HQL query which retrieves Test Method Vocabulary objects,
	 * filtering on the object name or Test Method properties containing
	 * the search string.
	 * @param modelClassName Class name of model object which implements the
	 * 		  ControlledVocabularyObject interface
	 * @param sqlSearchString search text formatted as SQL "like" clause 
	 * @param nameColumn language specific name attribute used in the query 
	 * 		  "order by" clause 
	 * @return collection of all Test Method Vocabulary objects of the 
	 * specified model class
	 */ 
	private PagedList executeVocabularyQuery(String modelClassName,
											String sqlSearchString,
											HashMap searchFields,
											HashMap sortOrder) {
		
		//log ("executeVocabularyQuery: Class=" + modelClassName);
		/* 
		 * Hibernate Query Language query.  NB. the "distinct" keyword works at 
		 * the Hibernate level to filter out duplicates, not at the SQL level. 
		 */
		List fieldList = (ArrayList)searchFields.get(modelClassName);
		List joinList = (ArrayList)searchFields.get(modelClassName + "Joins");
		Iterator fieldItr = fieldList.iterator();
		String nameColumn = (String)fieldItr.next();  //name column to search is always 
		                                             //the first elelment of the list			
		String queryString  = "select ";
			
			//The test method join will  be disticnt. Including the distinct identifier
			//for Test mehtods was resulting in an SQL error on the new sorting feature.
			if (joinList != null && !(modelClassName.equals("TestMethod"))) {
				queryString += "distinct ";
			}
				
			queryString += "cvo from " + modelClassName + " cvo";
			
			if (joinList != null){
				Iterator joinItr = joinList.iterator();
				int x = 1;
				while (joinItr.hasNext()){
					String join = (String)joinItr.next();
					queryString += " left join cvo." + join + " " + join;	
				}
			
				queryString += " where (lower(cvo." + nameColumn + ") " + sqlSearchString.toLowerCase();
				
				joinItr = joinList.iterator();
				while (joinItr.hasNext()){
					String join = (String)joinItr.next();
					queryString += " or lower(" + join + "." + nameColumn + ") " + sqlSearchString.toLowerCase() + ") "; 	
				}
			}else{
				queryString += " where (lower(cvo." + nameColumn + ") " + sqlSearchString.toLowerCase() + ") ";
			}
			
			while(fieldItr.hasNext()){
				queryString += "or lower(cvo." + fieldItr.next() + ") "  + sqlSearchString.toLowerCase() + ") ";
			}
			
            // Only show the visible preparation types.
            if (modelClassName.equals("PreparationType")){
                queryString += "and lower(visible)='y' ";
            }
            
			List sortList = (ArrayList)sortOrder.get(modelClassName);
			Iterator sortItr = sortList.iterator();
			
			//process the first sort item
			if(sortItr.hasNext()){
				VocabularySearchCriteria vs = (VocabularySearchCriteria)sortItr.next();
				queryString += " order by " + vs.getSearchSynonym() + "." +  vs.getSrchField();
			}
			
			//process and additional sort fields
			while(sortItr.hasNext()){
				VocabularySearchCriteria vs = (VocabularySearchCriteria)sortItr.next();
				queryString += ", " + vs.getSearchSynonym() + "." +  vs.getSrchField();
			}
			
		//log(queryString);
			
		//List results = 
		return new PagedList(executeHqlQuery(queryString));

	}
	
	/**************************************************************************
	 * Executes an HQL query.  
	 * @param queryString Hibernate Query Language query string
	 * @return collection of objects which are the result set of the query 
	 */ 	
	private List executeHqlQuery(String queryString) {
		
		long startTime = System.currentTimeMillis();
		Query hqlQuery = getSession().createQuery(queryString); 
		List resultList = null;
		
		//log("ControlledVocabularyDao executing HQL query: " + queryString);
		try {
			resultList = hqlQuery.list(); 
		} 
		catch (Throwable ex) {
			log.error ("ControlledVocabulary query failed: ", ex );
			ex.printStackTrace();	
		}
		
		//long execTime = System.currentTimeMillis() - startTime;
		//log ("CV Results size: " + resultList.size() + 
		//	 "  HQL query exec time(ms): "  + execTime);
		return resultList;

	}


	/**
	 * Stolen from AbstractDao  
	 */
	public void prepForUse(List entities) 
	throws HibernateException {

		Session hibernateSession = getSession();
		for (int i = 0; i < entities.size(); i++) {
			if (!hibernateSession.contains(entities.get(i))) {    	
				entities.set(i, hibernateSession.merge(entities.get(i)));
			}
		}

	}

    /***************************************************************************
     * Gets the current Hibernate Session.  Typically used by clients in cases 
     * where they know that a transaction has already been started and the 
     * associated session can be retrieved.  
     * 
     * @return the current Hibernate Session.
     */
    protected Session getSession() {    	
    
    	 // Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    	 Session session = getCurrentSession();

         /*
          * Sometimes the transaction is closed by a save or delete event. This
          * ensures that the transaction is restarted.  TODO: this should only 
          * be required if doing updates, which the current IDB web app 
          * (our primary client) does not need.  Do we need this? 
          */
          if (!session.getTransaction().isActive()) {
              session.beginTransaction();
          }

          return session;
    
    }	
    
    private Session getCurrentSession() {
		SessionFactory sessionFact = (SessionFactory) SpringContext.getApplicationContext().getBean("hibernateSessionFactory");
		Session session = sessionFact.getCurrentSession();
		return session;
    }
}



