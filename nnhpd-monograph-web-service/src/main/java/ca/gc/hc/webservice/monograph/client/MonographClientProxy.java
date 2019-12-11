package ca.gc.hc.webservice.monograph.client;

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ca.gc.hc.nhpd.exception.IngredientsException;
import ca.gc.hc.nhpd.model.GeneratedMonograph;
import ca.gc.hc.nhpd.model.MonographId;
import ca.gc.hc.nhpd.model.MonographSourceMaterial;
import ca.gc.hc.nhpd.model.QualifiedSynonym;
import ca.gc.hc.nhpd.dto.MonographSearchCriteria;
import ca.gc.hc.nhpd.servicepilot.monograph.MonographSessionBean;

public class MonographClientProxy {
	
	private static Log log = LogFactory.getLog(MonographClientProxy.class);

	
	/*
	 * Search Monographs by searchString.  
	 */   
	
	public List<MonographSearchCriteria> searchMonographs(MonographSearchCriteria criteria)
	{
		//this may eventually be replaced with JNDI session bean lookup
		//and create.
		MonographSessionBean monographSession = new MonographSessionBean();
		try
		{
			return monographSession.searchMonographs(criteria);
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
	 * Retrieve a specific Monograph by its Id. 
	 * 
	 */   
	public GeneratedMonograph getMonographById(MonographId monographId) 
	{
		//this should eventually be replaced with JNDI session bean lookup and create.
		log ("MCP: getMonographById");
		
		GeneratedMonograph retMono = null;
		MonographSessionBean monographSession = new MonographSessionBean();
		try
		{
			retMono = monographSession.getMonographById(monographId);
			if(retMono != null) {
				retMono.getId();
				retMono.getName();
			
//				Set<QualifiedSynonym> commonNames = retMono.getCommonNames();
//				if(commonNames != null && commonNames.size()>0) {
//					System.out.println("Common name are following ");
//					for(QualifiedSynonym sn:commonNames) {
//						System.out.println(sn.getDisplayName());
//					}
//				}
//				
//				Set<QualifiedSynonym> propernames = retMono.getProperNames();
//				if(propernames != null && propernames.size()>0) {
//					System.out.println("Propers name are following ");
//					for(QualifiedSynonym sn:propernames) {
//						System.out.println(sn.getDisplayName());
//					}
//				}
//				Set<MonographSourceMaterial>  materis = retMono.getMonographSourceMaterials();
//				if(materis != null && materis.size()>0) {
//					System.out.println("Source materials name are following ");
//					for(MonographSourceMaterial sn:materis) {
//						System.out.println(sn.getSourceMaterialName());
//					}
//				}
				
				
			}
		}
		catch (Throwable ex)
		{
			log.error(ex);
			ex.printStackTrace();
			throw new IngredientsException(ex);
		}
		log ("MCP: Returning Mono");
		return retMono;
		
	}
	
	private void log (String msg)
	{
		log.debug(msg);
	}
	
}
