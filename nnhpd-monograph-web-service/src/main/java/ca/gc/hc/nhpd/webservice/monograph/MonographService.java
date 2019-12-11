package ca.gc.hc.nhpd.webservice.monograph;

import ca.gc.hc.nhpd.dto.MonographSearchResult;
import ca.gc.hc.nhpd.dto.MonographSearchCriteria;
import ca.gc.hc.nhpd.dto.SearchCriterion;
import ca.gc.hc.nhpd.exception.IngredientsException;
import ca.gc.hc.nhpd.model.GeneratedMonograph;
import ca.gc.hc.nhpd.model.MonographId;
import ca.gc.hc.nhpd.model.PersistentObject;
import ca.gc.hc.nhpd.servicepilot.monograph.MonographServicePropertyManager;
import ca.gc.hc.nhpd.webservice.model.ConfigPropertyWS;
import ca.gc.hc.nhpd.webservice.model.IngredientWS;
import ca.gc.hc.nhpd.webservice.model.ChemicalMonographEntryWS;
import ca.gc.hc.nhpd.webservice.model.ChemicalSubstanceWS;
import ca.gc.hc.nhpd.webservice.model.CustomOrganismSubstanceWS;
import ca.gc.hc.nhpd.webservice.model.DefinedOrganismSubstanceWS;
import ca.gc.hc.nhpd.webservice.model.MonographIdWS;
import ca.gc.hc.nhpd.webservice.model.MonographSearchCriteriaWS;
import ca.gc.hc.nhpd.webservice.model.MonographSearchResultWS;
import ca.gc.hc.nhpd.webservice.model.MonographWS;
import ca.gc.hc.nhpd.webservice.model.OrganismMonographEntryWS;
import ca.gc.hc.nhpd.webservice.model.SubIngredientWS;
import ca.gc.hc.nhpd.webservice.servlet.WebServiceInitServlet;
import ca.gc.hc.webservice.monograph.client.MonographClientProxy;
import ca.gc.hc.nhpd.util.ThreadContext;
import ca.gc.hc.nhpd.util.VersionInfo;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

//@WebService(serviceName = "MonographService")
//@HandlerChain(file = "handlers.xml")
public class MonographService  {

	private static String buildVersion;

    // private String webServiceVersion = ThreadContext.getInstance().getVersion();

	private static final Log log = LogFactory.getLog(MonographService.class);
   
	
	public MonographService() {
		
	}
   /*
	* The base class PersistentObject is used by all the model specific subclasses
	* to determine the current user language.  We set the LocaleSource of the  
	* PersistentObject to the static ThreadContext instance, which contains a 
	* ThreadLocal user locale.  The ThreadContext locale is set on every request 
	* via the SoapHeaderHandler.   
	*/  
//	static {
//		PersistentObject.setLocaleSource(ThreadContext.getInstance());
//	}
	
   /*
	* Retrieves Monograph Web Service version number.  Added as a convenience
	* to enable a client to quickly determine what version is being used. 
	*/
//	@WebMethod
//	public Set<String> getMwsVersion() throws RemoteException {
//		
//    	Boolean displayVersion = ThreadContext.getInstance().getDisplayVersion();
//    	Set<String> versions = new TreeSet<String>();
//
//    	// default functionality
//    	if (displayVersion == false) {
//    		versions.add(getBuildVersion());
//    		return versions;
//    	}
//
//    	// version functionality.  (Additional information is being returned)
//    	versions.add("version" + VersionInfo.VERSION_LIST_SEPERATOR + getBuildVersion());
//    	versions.addAll(new VersionInfo().getVersionList());
//    	return versions;
//	}

	/*
	* Retrieves server side configuration items that the client needs	
	*/
	
//	@WebMethod
//	public List<ConfigPropertyWS> getMwsConfigProperties()
//	throws RemoteException
//	{
//		
//		List<ConfigPropertyWS> properties = new ArrayList<ConfigPropertyWS>();
//		
//		//TODO - make monograph specific
//		
//		properties.add
//			(new ConfigPropertyWS(MonographServicePropertyManager.MONOGRAPH_REQUEST_URL,
//							      MonographServicePropertyManager.getMonographRequestUrl()));
//		properties.add
//		    (new ConfigPropertyWS(MonographServicePropertyManager.INGREDIENT_REQUEST_URL,
//							      MonographServicePropertyManager.getIngredientRequestUrl()));
//		properties.add
//			(new ConfigPropertyWS(MonographServicePropertyManager.PLA_MAX_SEARCH_RESULTS_SIZE,
//			 new Integer(MonographServicePropertyManager.getPlaMaxSearchResultSize()).toString()));   
//		
//		return properties;
//	}	
	
	
	/*
	* Search Ingredients by SearchCriteria, which provides name value pairs to 
	* search by name string, role, category and ingredient class name
	*/

    @WebMethod
	public List<MonographSearchResultWS> searchMonographs(@WebParam(name="criteria")  MonographSearchCriteriaWS  criteria) 
	{
		
		//Use the Ingredient client proxy; it calls the implementation
		//IngredientWS[] ingredients = null;
		List<MonographSearchResultWS> retList = null;
		
		MonographClientProxy proxy = new MonographClientProxy();
		try
		{
			MonographSearchCriteria monographSearchCriteria = extractCriteria(criteria);
            List<MonographSearchCriteria> list = proxy.searchMonographs(monographSearchCriteria);
            retList = wrapMonographSearchResults(list);
        } catch (IngredientsException ie) {
            // We want to throw any Ingredient Exception.
            // The exception is normally an ingredient that sql is not available.
            throw ie;
		}
		catch (Throwable ex)
		{
			log.error("Exception occured in searchMonographs ", ex);
		}
	
		return retList;
	}
	
   /*
	* Retrieve Monograph By its Id.  Returns the Monograph object, and its related
	* Ingredient, and Monograph Dependant Objects (MDOs).  This method is 
	* typically used by the PLA after selecting one of the Ingredients from
	* from the search results.
	*/	
	
	@WebMethod
	public MonographWS getMonographById(@WebParam(name="monoID") MonographIdWS monographId)
	 
	{
		log.debug("MSI: getMonographById");
		MonographId monoId = new MonographId();
		monoId.setId(monographId.getId());
		
		log.debug("MSI:getMonographById creating proxy...");
		MonographClientProxy proxy = new MonographClientProxy();
		
		log.debug("MSI:getMonographById invoking proxy...");
		GeneratedMonograph generatedMonograph = proxy.getMonographById(monoId);
		
    	return new MonographWS(generatedMonograph);
	}

	
	
	
/*
	private MonographSearchCriteria extractCriteria(MonographSearchCriteriaWS criteria)
	{
		SearchCriterion[] criterion = criteria.getCriteria();

		MonographSearchCriteria monographSearchCriteria =
			new MonographSearchCriteria();
		for (int x=0;x<criterion.length;x++)
			monographSearchCriteria.addCriterion(criterion[x]);

		return monographSearchCriteria;
	}
*/
	private MonographSearchCriteria extractCriteria(MonographSearchCriteriaWS criteria)
	{
		List<SearchCriterion> criteriaList = criteria.getCriteria();

		MonographSearchCriteria monographSearchCriteria =
			new MonographSearchCriteria();
		Iterator<SearchCriterion> iter = criteriaList.iterator();
		while (iter.hasNext())
		{
			monographSearchCriteria.addCriterion((SearchCriterion)iter.next());
		}
		return monographSearchCriteria;
	}	
	
	
	private List<MonographSearchResultWS> wrapMonographSearchResults(List monographSearchResults)
	{
		
		ArrayList<MonographSearchResultWS> arrayList = 
			new ArrayList<MonographSearchResultWS>();
		try
		{
			log.debug("wrapMonographSearchResults:  list size: " + monographSearchResults.size());

			for (int x=0;x<monographSearchResults.size();x++)
			{
				//log.debug("Impl: Adding MonographSearchResult...");
				arrayList.add(new MonographSearchResultWS((MonographSearchResult)monographSearchResults.get(x)));
			}
			log.debug("wrapIngredientSearchResults:  arrayList size: " + arrayList.size());	

		}
		catch (Throwable ex)
		{
			log.debug("Unexpected exception occured...");
			ex.printStackTrace();
		}
		return arrayList;
		
	}
//
//
//	//exposes the wrapper classes that are not currently exposed via the parameters
//	//of the @WebMethod annotated API methods
//	@WebMethod 
//	public void exposeIngredientWS(IngredientWS ingredientWS)  {}
//	@WebMethod 
//	public void exposeChemicalSubstanceWS(ChemicalSubstanceWS chemicalSubstanceWS) {}	
//	@WebMethod 
//	public void exposeCustomOrganismSubstanceWS(CustomOrganismSubstanceWS chemicalSubstanceWS) {}
//	@WebMethod 
//	public void exposeDefinedOrganismSubstanceWS(DefinedOrganismSubstanceWS definedOrganismSubstanceWS) {}
//	@WebMethod 
//	public void exposeSubIngredientWS(SubIngredientWS subIngredientWS) {}
//	@WebMethod 
//	public void exposeChemicalMonographEntry(ChemicalMonographEntryWS chemicalMonographEntryWS) {}
//	@WebMethod 
//	public void exposeOrganismMonographEntry(OrganismMonographEntryWS organismMonographEntryWS) {}
//
//    /**
//     * Returns the buid version read when loading the Web Service from MANIFEST.MF.
//     * @return
//     */
//    public static String getBuildVersion() {
//    	if(buildVersion == null) {
//    		buildVersion = "NHP Monograph Web Service - " + WebServiceInitServlet.getVersion();
//    	}
//    	
//    	return buildVersion;
//    }
}
