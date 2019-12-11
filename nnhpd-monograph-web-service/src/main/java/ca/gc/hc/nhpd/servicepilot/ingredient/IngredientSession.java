package ca.gc.hc.nhpd.servicepilot.ingredient;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import ca.gc.hc.nhpd.model.Ingredient;
import ca.gc.hc.nhpd.model.IngredientId;
import ca.gc.hc.nhpd.webservice.model.IngredientSearchCriteriaWS;


/*
* This class exposes a service interface (e.g. the Ingredient Management Service)
* which facilitates the management and querying of Ingredients and related objects.
* It is targetted for the Non-Compendial Web Services pilot which feeds the 
* Adobe Form Product License Application. 
*
* The current implementation plan is to automate the generation of
* the Monograph Management (Web) Service via the JSR 109 implementation
* provided by leading J2EE Application server vendors.  It is highly 
* desirable to transition this implementation to a JAX-WS compliant 
* implementation ASAP.  (see JAX-RPC/JAX-WS options analysis document)
* 
* The current primary function of this interface is to expose values for 
* populating picklists.
* 
* Author: M. Rabe
* Date: May 2007
*
*/

public interface IngredientSession extends Remote
{

	public static final String NON_MEDICINAL_ROLE = "NonMedicinalRole"; 
	public static final String MEDICINAL_ROLE = "MedicinalRole"; 
	
   /*
    * Search Ingredients by Role and an optional searchString.
    * Use one of the Role strings specified in the IngredientService 
    * interface. 
    *
	* JAX-WS equivalent method: 
	* @WebMethod
	* public List<IngredientWS> searchIngredients(...);
	*/   
	public List searchIngredients(IngredientSearchCriteriaWS criteria)
	throws RemoteException;
	
   /*
	* Search Ingredients by Role and an optional searchString.
	* Use one of the Role strings specified in the IngredientService 
	* interface. 
	*
	* JAX-WS equivalent method: 
	* @WebMethod
	* public IngredientWS getIngredientById(IngredientIdWS IngredientId);												  
	*/   
	public Ingredient getIngredientById(IngredientId ingredientId) 
	throws RemoteException;
	
	
   /*
    * Retrieves all possible preparations for medicinal ingredients
    *
    */
    /*
	public PartPreparation[] getAllPreparationMethods()
    throws RemoteException;
    
   
   /*
    * Retrieves all Administration Routes
    *
    * JAX-WS equivalent method: 
	* @WebMethod
	* public List<RouteOfAdministration> getRoutesOfAdministration();												  
	*/  
   
    public List getRoutesOfAdministration()
    throws RemoteException;

    /*
	*  TBD: Future operations.
	*
	*  CRUD ops:
	*	   create
	*	   update
	*	   get
	*	   delete
	*  State management operations:
	*	   publish
	*	   unpublish
	*	   withdraw
	*	   register
	*	   etc...
	*/	
	
}

	
   

