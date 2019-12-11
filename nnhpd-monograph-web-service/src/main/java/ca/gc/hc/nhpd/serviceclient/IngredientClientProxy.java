package ca.gc.hc.nhpd.serviceclient;

import ca.gc.hc.nhpd.dto.IngredientSearchCriteria;
import ca.gc.hc.nhpd.dto.IngredientSearchResult;
import ca.gc.hc.nhpd.dto.PreClearedInfo;
import ca.gc.hc.nhpd.exception.IngredientsException;
import ca.gc.hc.nhpd.model.ApplicationType;
import ca.gc.hc.nhpd.model.CommonTerm;
import ca.gc.hc.nhpd.model.Country;
import ca.gc.hc.nhpd.model.HomeopathicDilution;
import ca.gc.hc.nhpd.model.Ingredient;
import ca.gc.hc.nhpd.model.IngredientId;
import ca.gc.hc.nhpd.model.DosageForm;
import ca.gc.hc.nhpd.model.NonMedicinalPurpose;
import ca.gc.hc.nhpd.model.OrganismPartType;
import ca.gc.hc.nhpd.model.OrganismType;
import ca.gc.hc.nhpd.model.OrganismTypeGroup;
import ca.gc.hc.nhpd.model.PreparationRule;
import ca.gc.hc.nhpd.model.PreparationType;
import ca.gc.hc.nhpd.model.RouteOfAdministration;
import ca.gc.hc.nhpd.model.Solvent;
import ca.gc.hc.nhpd.model.StandardOrGradeReference;
import ca.gc.hc.nhpd.model.SubPopulation;
import ca.gc.hc.nhpd.model.Units;
import ca.gc.hc.nhpd.servicepilot.ingredient.IngredientSessionBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
/**
 * 
 * This class is a client proxy for the Ingredient busines logic
 * layer.   It hides the complexities of calling EJBs (or whatever
 * future technology the business logic layer will be implemented in)
 * such as JNDI lookup and exception handling. 
 * For now, we'll just call the implementation directly; we'll 
 * implement the remotable session bean tier later.
 * 
 * @author MRABE
 *
 */
public class IngredientClientProxy 
{
	//
	private static Log log = LogFactory.getLog(IngredientClientProxy.class);
   
   /*
	* Search Ingredients by Role and an optional searchString.
	* Use one of the Role strings specified in the IngredientService 
	* interface. 
	*/   
	public List<IngredientSearchResult> searchIngredients(IngredientSearchCriteria criteria)
	{
		//this will eventually be replaced with JNDI session bean lookup
		//and create.
		IngredientSessionBean ingredientSession = new IngredientSessionBean();
		try
		{
			return ingredientSession.searchIngredients(criteria);
		}
        catch (IngredientsException ie) {
            // We want to raise any IngredientException.
            // This exception is normally an ingredient that is not found.
            throw ie;
        }
		catch (Throwable ex)
		{
			throw new IngredientsException(ex);
		}
		
	}

    /*
     * Retrieve a list of common term types.  Common term types contain individual
     * common terms. 
     */
    public List<CommonTerm> getCommonTermsByTypes(String[] commonTermTypes) {
        //this may eventually be replaced with JNDI session bean lookup
        //and create.
        IngredientSessionBean ingredientSession = new IngredientSessionBean();
        try
        {
            return ingredientSession.getCommonTermsByTypes(commonTermTypes);
        }
        catch (IngredientsException ie) {
            // We want to raise any IngredientException.
            // This exception is normally an ingredient that is not found.
            throw ie;
        }
        catch (Throwable ex)
        {
            throw new IngredientsException(ex);
        }
    }

    /*
     * Retrieve a list of countries. 
     */
    public List<Country> getCountries() {
        //this may eventually be replaced with JNDI session bean lookup
        //and create.
        IngredientSessionBean ingredientSession = new IngredientSessionBean();
        try
        {
            return ingredientSession.getCountries();
        }
        catch (IngredientsException ie) {
            // We want to raise any IngredientException.
            // This exception is normally an ingredient that is not found.
            throw ie;
        }
        catch (Throwable ex)
        {
            throw new IngredientsException(ex);
        }
    }
    
    /*
     * Retrieve a list of Standard or Grade References. 
     */
    public List<StandardOrGradeReference> getStandardOrGradeReferences() {
        //this may eventually be replaced with JNDI session bean lookup
        //and create.
        IngredientSessionBean ingredientSession = new IngredientSessionBean();
        try
        {
            return ingredientSession.getStandardOrGradeReferences();
        }
        catch (IngredientsException ie) {
            // We want to raise any IngredientException.
            // This exception is normally an ingredient that is not found.
            throw ie;
        }
        catch (Throwable ex)
        {
            throw new IngredientsException(ex);
        }
    }
    
    /*
     * Retrieve a list of dosage forms. 
     */
    public List<DosageForm> getDosageForm() {
        //this may eventually be replaced with JNDI session bean lookup
        //and create.
        IngredientSessionBean ingredientSession = new IngredientSessionBean();
        try
        {
            return ingredientSession.getDosageForms();
        }
        catch (IngredientsException ie) {
            // We want to raise any IngredientException.
            // This exception is normally an ingredient that is not found.
            throw ie;
        }
        catch (Throwable ex)
        {
            throw new IngredientsException(ex);
        }
    }
    
    /*
    * Get all Homeopathic Dilutions 
    */   
    public List<HomeopathicDilution> getHomeopathicDilutions() 
    {
        //this will eventually be replaced with JNDI session bean lookup
        //and create.
        IngredientSessionBean ingredientSession = new IngredientSessionBean();
        try
        {
            return ingredientSession.getHomeopathicDilutions();
        }
        catch (Throwable ex)
        {
            throw new IngredientsException(ex);
        }
        
    }
    
   /*
	* Retrieve a specific Ingredient by its Id. 
	* 
	*/   
	public Ingredient getIngredientById(IngredientId ingredientId) 
	{
		//this will eventually be replaced with JNDI session bean lookup
		//and create.
		IngredientSessionBean ingredientSession = new IngredientSessionBean();
		try
		{
			return ingredientSession.getIngredientById(ingredientId,true);
		}
		catch (IngredientsException ie) {
		    // We want to raise any IngredientException.
		    // This exception is normally an ingredient that is not found.
		    throw ie;
		}
		catch (Throwable ex)
		{
			throw new IngredientsException(ex);
		}
		
	}
	
    /*
     * Retrieve a list of preparation rules. 
     */
    public List<PreparationRule> getPreparationRule() {
        //this may eventually be replaced with JNDI session bean lookup
        //and create.
        IngredientSessionBean ingredientSession = new IngredientSessionBean();
        try
        {
            return ingredientSession.getPreparationRules();
        }
        catch (IngredientsException ie) {
            // We want to raise any IngredientException.
            // This exception is normally an ingredient that is not found.
            throw ie;
        }
        catch (Throwable ex)
        {
            throw new IngredientsException(ex);
        }
    }
    
	/*
	* Get all Solvents 
	*/   
	public List<Solvent> getSolvents() 
	{
		//this will eventually be replaced with JNDI session bean lookup
		//and create.
		IngredientSessionBean ingredientSession = new IngredientSessionBean();
		try
		{
			return ingredientSession.getSolvents();
		}
		catch (Throwable ex)
		{
			throw new IngredientsException(ex);
		}
		
	}

    /*
    * Get all Application Types 
    */   
    public List<ApplicationType> getApplicationTypes() 
    {
        //this will eventually be replaced with JNDI session bean lookup
        //and create.
        IngredientSessionBean ingredientSession = new IngredientSessionBean();
        try
        {
            return ingredientSession.getApplicationTypes();
        }
        catch (Throwable ex)
        {
            throw new IngredientsException(ex);
        }
        
    }
    
	/*
	* Get all Non-MedicinalPurposes 
	*/   
	public List<NonMedicinalPurpose> getNonMedicinalPurposes() 
	{
		//this will eventually be replaced with JNDI session bean lookup
		//and create.
		IngredientSessionBean ingredientSession = new IngredientSessionBean();
		try
		{
			return ingredientSession.getNonMedicinalPurposes();
		}
		catch (Throwable ex)
		{
			throw new IngredientsException(ex);
		}
		
	}	
	
	/*
	* Get all Non-MedicinalPurposes 
	*/   
	public List<PreparationType> getPreparationTypes() 
	{
		//this will eventually be replaced with JNDI session bean lookup
		//and create.
		IngredientSessionBean ingredientSession = new IngredientSessionBean();
		try
		{
			return ingredientSession.getPreparationTypes();
		}
		catch (Throwable ex)
		{
			throw new IngredientsException(ex);
		}
	}	
	
	/*
	* Get all Non-MedicinalPurposes 
	*/   
	public List<OrganismPartType> getOrganismPartTypes(String organismTypeGroup) 
	{
		//this will eventually be replaced with JNDI session bean lookup
		//and create.
		IngredientSessionBean ingredientSession = new IngredientSessionBean();
		try
		{
			return ingredientSession.getOrganismPartTypes(organismTypeGroup);
		}
		catch (Throwable ex)
		{
			throw new IngredientsException(ex);
		}
		
	}	
	
	/*
	* Get Units by Type 
	*/   
	public List<Units> getUnitsByTypes(String[] types) 
	{
		//this will eventually be replaced with JNDI session bean lookup
		//and create.
		IngredientSessionBean ingredientSession = new IngredientSessionBean();
		try
		{
			return ingredientSession.getUnitsByTypes(types);
		}
		catch (Throwable ex)
		{
			ex.printStackTrace();
			throw new IngredientsException(ex);
		}
		
	}
	
	/*
	* Get Units by Type Code
	*/   
	public List<Units> getUnitsByTypeCodes(String[] typeCodes) 
	{
		//this will eventually be replaced with JNDI session bean lookup
		//and create.
		IngredientSessionBean ingredientSession = new IngredientSessionBean();
		try
		{
			return ingredientSession.getUnitsByTypeCodes(typeCodes);
		}
		catch (Throwable ex)
		{
			ex.printStackTrace();
			throw new IngredientsException(ex);
		}
		
	}
		
   /*
	* Retrieves all Administration Routes												  
	*/  
	public List<RouteOfAdministration> getRoutesOfAdministration()
	{
		
		log.info ("IngredientClientProxy: getRoutesOfAdministration");
		//this will eventually be replaced with JNDI session bean lookup
		//and create.
		IngredientSessionBean ingredientSession = new IngredientSessionBean();
		
		try
		{
			return ingredientSession.getRoutesOfAdministration();
		}
		catch (Throwable ex)
		{
			ex.printStackTrace();
			throw new IngredientsException(ex);
		}
		
	}

   /*
    * Retrieves all Organism Types												  
	*/  
	public List<OrganismType> getOrganismTypes()
	{
		
		log.info ("IngredientClientProxy: getOrganismTypes");
		//this will eventually be replaced with JNDI session bean lookup
		//and create.
		IngredientSessionBean ingredientSession = new IngredientSessionBean();
		
		try
		{
			return ingredientSession.getOrganismTypes();
		}
		catch (Throwable ex)
		{
			ex.printStackTrace();
			throw new IngredientsException(ex);
		}
		
	}

	   /*
	    * Retrieves all Organism Type Groups												  
		*/  
		public List<OrganismTypeGroup> getOrganismTypeGroups()
		{
			
			log.info ("IngredientClientProxy: getOrganismTypeGroups");
			//this will eventually be replaced with JNDI session bean lookup
			//and create.
			IngredientSessionBean ingredientSession = new IngredientSessionBean();
			
			try
			{
				return ingredientSession.getOrganismTypeGroups();
			}
			catch (Throwable ex)
			{
				ex.printStackTrace();
				throw new IngredientsException(ex);
			}
			
		}

		/*
		* Get the preferred subpopulations
		*/   
		public List<SubPopulation> getPreferredSubPopulations() 
		{
			//this will eventually be replaced with JNDI session bean lookup
			//and create.
			IngredientSessionBean ingredientSession = new IngredientSessionBean();
			try
			{
				return ingredientSession.getPreferredSubPopulations();
			}
			catch (Throwable ex)
			{
				ex.printStackTrace();
				throw new IngredientsException(ex);
			}
			
		}
		
	/*
	public List getDosageFormsForRouteOfAdmin(String routeOfAdmin, 
			    							  int assessmentRequired)
	{
	
		log.debug("getDosageFormsForRouteOfAdmin" );
		IngredientSessionBean ingredientSession = new IngredientSessionBean();
		
		try
		{
			return ingredientSession.getDosageFormsForRouteOfAdmin(routeOfAdmin,
				   										   assessmentRequired);
		}
		catch (Throwable ex)
		{
			ex.printStackTrace();
			throw new IngredientsException(ex);
		}
	}
	*/
		public List<PreClearedInfo> getPcis()
		{
			IngredientSessionBean ingredientSession = new IngredientSessionBean();
			try
			{
				return ingredientSession.getPcis();
			}
			catch (Throwable ex)
			{
				ex.printStackTrace();
				throw new IngredientsException(ex);
			}
			
		}	
		
}