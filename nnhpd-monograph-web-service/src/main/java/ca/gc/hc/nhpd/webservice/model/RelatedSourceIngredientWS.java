package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.dto.RelatedSourceIngredient;
import ca.gc.hc.nhpd.util.ThreadContext;

import java.util.List;

/*******************************************************************************
 * Wraps a RelatedSourceIngredient DTO 
 * 
 * @author MRABE
 */


public class RelatedSourceIngredientWS {

	private String version = ThreadContext.getInstance().getVersion();
	
	//the wrapped SourceOrganismPart DTO
    private RelatedSourceIngredient relatedSourceIngredient;

    //ctors
    public RelatedSourceIngredientWS() {
    	relatedSourceIngredient = new RelatedSourceIngredient();
    }

    public RelatedSourceIngredientWS(RelatedSourceIngredient relatedSourceIngredient) {
        if (relatedSourceIngredient == null) {
            throw new IllegalArgumentException("relatedSourceIngredient is null!");
        }
        this.relatedSourceIngredient = relatedSourceIngredient;
    }

    /*----------------------------------------------------------------
     * Public Accessors/Mutators - methods exposed to the Web Service
     *----------------------------------------------------------------*/
    /**
     * Returns the unique identifying code associated with this Organism Part
     * Type.
     */
    public List<String> getSynonyms() {
    	
    	// Return null for 2.0.
    	if (version.equals(ThreadContext.VERSION_2_0)) {
    		return null;
    	}
    	
    	if (relatedSourceIngredient.isFrench())
    		return relatedSourceIngredient.getSynonymsFrench();
    	else
    		return relatedSourceIngredient.getSynonymsEnglish();
    }

    public void setSynonyms(List<String> synonyms) {
        //Not Implemented; for JAXB only
    }
    
    /*
     * This is the Latin organism name - no need to translate.
     */
    public String getIngredientName() {
    	if (relatedSourceIngredient.isFrench())
    		return relatedSourceIngredient.getIngredientNameFrench();
    	return relatedSourceIngredient.getIngredientNameEnglish();
    	
    }
    
    public void setIngredientName(String name) {
    	//Not Implemented; for JAXB only
    }
        
}
