package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.dto.RelatedSourceIngredient;
import ca.gc.hc.nhpd.dto.SourceOrganismPart;
import ca.gc.hc.nhpd.model.ChemicalSubstance;
import ca.gc.hc.nhpd.model.SubIngredient;
import ca.gc.hc.nhpd.util.ThreadContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.TreeMap;

/*******************************************************************************
 * An object that represents a generally accepted chemical substance definition.
 */

public class ChemicalSubstanceWS extends IngredientWS {

	private String SEPERATOR = "~;~";
	
	private String version = ThreadContext.getInstance().getVersion();
	
    // the wrapped chemicalSubstance
    private ChemicalSubstance chemicalSubstance;
    
    private TreeMap<String, Set<String>> taxonomyNodes;

    public ChemicalSubstanceWS(ChemicalSubstance chemicalSubstance,
            boolean bilingual) {
        super(chemicalSubstance);
        if (chemicalSubstance == null) {
            this.chemicalSubstance = new ChemicalSubstance();
        } else {
            this.chemicalSubstance = chemicalSubstance;
        }
        this.bilingual = bilingual;
        setTaxonomyNodes();
    }

    public ChemicalSubstanceWS() {
        super(new ChemicalSubstance());
        this.chemicalSubstance = new ChemicalSubstance();
        setTaxonomyNodes();
    }

    public List<String> getProperNames() {
        return initializeNames(chemicalSubstance.getProperNames(), "Proper");
    }

    public void setProperNames(List<String> properNames) {
        //
    }

    public Set<String> getScientificNames() {
        return chemicalSubstance.getScientificNames();
    }

    public void setScientificNames(Set<String> scientificNames) {
        //
    }

    public List<String> getCommonNames() {
        return initializeNames(chemicalSubstance.getCommonNames(), "Common");
    }

    public void setCommonNames(List<String> commonNames) {
        //
    }
    
    /*
     * Wrap up the SourceOrganismPart DTOs.  
     */
    public Set<SourceOrganismPartWS> getSourceOrganismParts() {
        Set<SourceOrganismPartWS> sourceOrgParts = 
        	new TreeSet<SourceOrganismPartWS>();
        
        List<SourceOrganismPart> sops = chemicalSubstance.getSourceOrganismPartDtos();
        if (sops == null || sops.size() < 1) {
        	return null;
        }

        for (SourceOrganismPart sop: sops) {
        	if (chemicalSubstance.isLanguageFrench()) {
        		sop.setFrench(true);
        	}
        	sourceOrgParts.add(new SourceOrganismPartWS(sop));

        	if (getTaxonomyNodes().get(sop.getName()) != null) {
	        	//cartesian product of parts list with taxonomy nodes
	        	for (String taxonomyNode:getTaxonomyNodes().get(sop.getName())) {
	        		SourceOrganismPart xsop = new SourceOrganismPart(taxonomyNode, sop);
	        		sourceOrgParts.add(new SourceOrganismPartWS(xsop));
	        	}
        	}      	
        }
       		    
    	return sourceOrgParts;
    	
    }
	
    public void setSourceOrganismParts(Set<SourceOrganismPartWS> sourceOrganismParts) {
    	  // no impl: for JAXB
    }
    
   /*
    * Wrap up the RelatedSourceIngredient DTOs.  
    */
    public List<RelatedSourceIngredientWS> getRelatedSourceIngredients() {
        
    	// Returns null for 2.0
    	if (version.equals(ThreadContext.VERSION_2_0)) {
    		return null;
    	}
    	
        List<RelatedSourceIngredient> relatedSourceIngredients = 
    		chemicalSubstance.getRelatedSourceIngredientDtos();
        if (relatedSourceIngredients == null) {
        	return null;
        }
        
        List<RelatedSourceIngredientWS> retRsis = 
        	new ArrayList<RelatedSourceIngredientWS>();
        
    		
        for (RelatedSourceIngredient rsi: relatedSourceIngredients) {
        	retRsis.add(new RelatedSourceIngredientWS(rsi));	
        }
        
        return retRsis;
    }
	
    public void setRelatedSourceIngredients
    	(List<RelatedSourceIngredientWS> relatedSourceIngredients) {
        // no impl: for JAXB
    }
    
    public String getRelatedSourceIngredientNames() {
    	
    	// Returns null for 1.4
    	if (version.equals(ThreadContext.VERSION_1_4)) {
    		return null;
    	}

        List<RelatedSourceIngredient> relatedSourceIngredients = 
    		chemicalSubstance.getRelatedSourceIngredientDtos();
        if (relatedSourceIngredients == null) {
        	return null;
        }
        
        StringBuffer sb = new StringBuffer();
        for (RelatedSourceIngredient relatedSourceIngredient : relatedSourceIngredients) {
        	
        	if (sb.length() != 0) {
        		sb.append(SEPERATOR);
        	}
        	
        	if (relatedSourceIngredient.isFrench()
        			&& relatedSourceIngredient.getIngredientNameFrench() != null) {
        		sb.append(relatedSourceIngredient.getIngredientNameFrench());
        	} else if (
        			relatedSourceIngredient.getIngredientNameEnglish() != null) {
        		sb.append(relatedSourceIngredient.getIngredientNameEnglish());
        	}
        }
        
        return sb.toString();
    }

    public void setRelatedSourceIngredientNames(String relatedSourceIngredientNames) {
    	// no impl.
    }
    
    public Set<SubIngredientWS> getSubIngredients() {
        Set<SubIngredientWS> subIngredients = new TreeSet<SubIngredientWS>();
        Set<SubIngredient> modelSubIngredients = chemicalSubstance
                .getSubIngredients();
        SubIngredient subIngredient = null;

        if (modelSubIngredients != null) {
            Iterator<SubIngredient> iter = modelSubIngredients.iterator();
            while (iter.hasNext()) {
                subIngredient = (SubIngredient) iter.next();
                subIngredients.add(new SubIngredientWS(subIngredient));
            }
        }
        return subIngredients;
    }

    public void setSubIngredients(Set<SubIngredientWS> subIngredients) {
        // no impl
    }

    private void setTaxonomyNodes() {
    	taxonomyNodes = chemicalSubstance.getTaxonomyPartsList();
    }
    
    private TreeMap<String, Set<String>> getTaxonomyNodes() {
    	return taxonomyNodes;
    }

}