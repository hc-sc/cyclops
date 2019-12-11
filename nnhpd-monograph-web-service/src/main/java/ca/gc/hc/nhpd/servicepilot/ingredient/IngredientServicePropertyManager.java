package ca.gc.hc.nhpd.servicepilot.ingredient;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/*
 * Used (in conjunction wth the commmon PropertyManager) to retrieve 
 * Ingredient Web Service specific configuration properties  
 */
public class IngredientServicePropertyManager {

	private static final Log log = LogFactory.getLog(IngredientServicePropertyManager.class);
	
	//property names
	public static final String PLA_MAX_SEARCH_RESULTS_SIZE  	= "PLA_MAX_SEARCH_RESULTS_SIZE";
	public static final String INGREDIENT_DB_URL 				= "INGREDIENT_DB_URL";
	public static final String INGREDIENT_REQUEST_URL 			= "INGREDIENT_REQUEST_URL";
	public static final String MONOGRAPH_REQUEST_URL 			= "MONOGRAPH_REQUEST_URL";
	
	//default property values
	public static final Integer DEFAULT_PLA_MAX_SEARCH_RESULTS_SIZE = 500;
	public static final String DEFAULT_INGREDIENT_DB_URL = 
		"http://webprod.hc-sc.gc.ca/nhpid-bdipsn/";
	//ingredient details request.  Client must append the numeric ingredient Id.
	public static final String DEFAULT_INGREDIENT_REQUEST_URL = 
		DEFAULT_INGREDIENT_DB_URL + "ingredReq.do?id=";
	
	//Monograph URL. the oeuf host is currently 10.243.2.21
	public static final String DEFAULT_MONOGRAPH_REQUEST_URL = 
		DEFAULT_INGREDIENT_DB_URL + "monoReq.do?id=";

	/** Map of properties with values. Can be overridden. */
    private static Map<String,Object> configurationProperties;

    static {
    	generateDefaults();
    }
    
	/**
	 * Generates the initial list of configuration items, with default properties. 
	 */
	private static void generateDefaults() {
		Map<String,Object>configurationProperties = new HashMap<String,Object>();
		
		configurationProperties.put(PLA_MAX_SEARCH_RESULTS_SIZE, DEFAULT_PLA_MAX_SEARCH_RESULTS_SIZE);
		configurationProperties.put(INGREDIENT_DB_URL, DEFAULT_INGREDIENT_DB_URL);
		configurationProperties.put(INGREDIENT_REQUEST_URL, DEFAULT_INGREDIENT_REQUEST_URL);
		configurationProperties.put(MONOGRAPH_REQUEST_URL, DEFAULT_MONOGRAPH_REQUEST_URL);
		
		IngredientServicePropertyManager.configurationProperties = configurationProperties;
	}
    
	public static int getPlaMaxSearchResultSize()
	{
		return (Integer)configurationProperties.get(PLA_MAX_SEARCH_RESULTS_SIZE);
	}
	
	/* 
	 * Returns URL for ingredients web site Home page 
	 */
	public static String getIngredientsWebSiteUrl()
	{
		return (String)configurationProperties.get(INGREDIENT_DB_URL);
	}
	
	/* 
	 * Returns URL for single ingredient details web page 
	 */
	public static String getIngredientRequestUrl()
	{
		return (String)configurationProperties.get(INGREDIENT_REQUEST_URL);
	}
	
	/* 
	 * Returns URL for monograph details web page 
	 */
	public static String getMonographRequestUrl()
	{
		return (String)configurationProperties.get(MONOGRAPH_REQUEST_URL);
	}	

	/**
	 * Sets the properties from a java naming context (JNDI). 
	 * This method uses the list of properties already loaded in memory and overrides
	 * the default values.
	 * @param context
	 */
	public static void overrideProperties(Context context) {
		// Read through all existing configuration properties to see if they get overridden.
		for (String propertyName : configurationProperties.keySet()) {
			try {
				Object configurationValue = context.lookup(propertyName);
				configurationProperties.put(propertyName, configurationValue);
				log.debug("Environment property loaded: " + propertyName + " = " + configurationValue);
			} catch (NameNotFoundException e) {
				// This error will happen often, lookups throw it when the binding name
				// doesn't exist (in other words, when the property is not overridden).
				log.debug("The property " + propertyName + " does not get overridden");
			} catch (NamingException e) {
				log.warn("An error occurred when reading property " + propertyName + " from context.xml or web.xml. Using default value.", e);
			}
		}
	}
}
