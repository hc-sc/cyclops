package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.dto.Constituent;
import ca.gc.hc.nhpd.dto.SourceOrganismPart;
import ca.gc.hc.nhpd.util.StringComparator;
import ca.gc.hc.nhpd.util.ThreadContext;

import java.util.ArrayList;
import java.util.List;

/*******************************************************************************
 * An object that represents the type of a specific part of an organism (e.g. a
 * stem).
 * 
 * @version 1.0
 */


public class SourceOrganismPartWS implements Comparable<SourceOrganismPartWS>{
	
	private String version = ThreadContext.getInstance().getVersion();
	
	private String SEPERATOR = "~;~";
	
	//the wrapped SourceOrganismPart DTO
    private SourceOrganismPart sourceOrganismPart;

    //ctors
    public SourceOrganismPartWS() {
    	sourceOrganismPart = new SourceOrganismPart();
    }

    public SourceOrganismPartWS(SourceOrganismPart sourceOrganismPart) {
        if (sourceOrganismPart == null) {
            throw new IllegalArgumentException("sourceOrganismPart is null!");
        }
        this.sourceOrganismPart = sourceOrganismPart;
    }

    /*----------------------------------------------------------------
     * Public Accessors/Mutators - methods exposed to the Web Service
     *----------------------------------------------------------------*/
    /**
     * Returns the unique identifying code associated with this Organism Part
     * Type.
     */
    public List<ConstituentWS> getConstituents() {
        
    	// Original 1.4 functionality.  Null returned for 2.0.
    	if (version.equals(ThreadContext.VERSION_2_0)) {
    		return null;
    	}
    	
        if (sourceOrganismPart == null || sourceOrganismPart.getConstituents() == null) {
            return null;
        }
        
        List<ConstituentWS> constituents = new ArrayList<ConstituentWS>();
        for (Constituent constituent : sourceOrganismPart.getConstituents()) {
            constituents.add(new ConstituentWS(constituent));
        }
        return constituents;
    }

    public void setConstituents(List<ConstituentWS> constituents) {
        // Not Implemented.
    }

    /*
     * This is the Latin organism name - no need to translate.
     */
    public String getName() {
    	
    	return sourceOrganismPart.getName();
    }
    
    public void setName(String name) {
        // Not Implemented.
    }
    
    /*
     * This is the Latin organism name - no need to translate.
     */
    public String getOrganism() {
    	return sourceOrganismPart.getOrganism();
    }
    
    public void setOrganism(String name) {
        // Not Implemented.
    }
	
	public String getPartTypeCode() {
		return sourceOrganismPart.getPartTypeCode();
	}

	public void setPartTypeCode(String partTypeCode) {
		
	}

	public String getPartTypeName() {
		if (sourceOrganismPart.isFrench())
			return sourceOrganismPart.getPartTypeNameFrench();
		return sourceOrganismPart.getPartTypeNameEnglish();
	}

	public void setPartTypeName(String partTypeName) {
		
	}

	public List<String> getOrganismSynonyms() {
		
    	if (version.equals(ThreadContext.VERSION_2_0)) {
    		return null;
    	}
		
		if (sourceOrganismPart.isFrench())
			return sourceOrganismPart.getOrganismSynonymsFrench();
		return sourceOrganismPart.getOrganismSynonymsEnglish();
	}

	public void setOrganismSynonyms(List<String> organismSynonyms) {
		
	}

	public List<String> getPartTypeSynonyms() {
		
    	if (version.equals(ThreadContext.VERSION_2_0)) {
    		return null;
    	}
		
		if (sourceOrganismPart.isFrench())
			return sourceOrganismPart.getPartTypeSynonymsFrench();
		return sourceOrganismPart.getPartTypeSynonymsEnglish();
	}

	public void setPartTypeSynonyms(List<String> partTypeSynonyms) {
		
	}
	
    /*
     * Implement Comparable compareTo method. Method required because this
     * object is added to a TreeSet Does an ignore case compare on the
     * Preparation Name
     */
    public int compareTo(SourceOrganismPartWS o) throws ClassCastException {
        return StringComparator.compare(sourceOrganismPart.getName(),
                o.getName());

    }
}
