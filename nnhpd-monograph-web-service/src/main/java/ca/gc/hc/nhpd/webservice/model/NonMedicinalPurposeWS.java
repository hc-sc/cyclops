package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.NonMedicinalPurpose;
import ca.gc.hc.nhpd.model.Synonym;
import ca.gc.hc.nhpd.util.StringComparator;
import ca.gc.hc.nhpd.util.ThreadContext;

import java.util.ArrayList;
import java.util.List;

/*******************************************************************************
 * Web Service wrapper for NonMedicinalPurpose.
 * 
 * @version 1.0
 */
public class NonMedicinalPurposeWS implements Comparable<NonMedicinalPurposeWS> {

	private static final String TRUE = "true";
    private static final String FALSE = "false";
	
	private String version = ThreadContext.getInstance().getVersion();
	
    // the wrapped NonMedicinalPurpose
    private NonMedicinalPurpose nonMedicinalPurpose;
   
	public NonMedicinalPurposeWS() {
        nonMedicinalPurpose = new NonMedicinalPurpose();
    }

    public NonMedicinalPurposeWS(NonMedicinalPurpose nonMedicinalPurpose) {
        this.nonMedicinalPurpose = nonMedicinalPurpose;
    }

    /**
     * Returns the name for this Dosage Form object.
     * 
     * @return the name of the Dosage Form object.
     */
    public String getName() {
        return nonMedicinalPurpose.getName();
    }

    public void setName(String name) {
        //null impl: for JAXB only
    }
    
    public String getCode() {
		return nonMedicinalPurpose.getCode();
	}

	public void setCode(String code) {
		//null impl: for JAXB only
	}
	
    /**
     * Returns the synonyms associated with this Non Medicinal Purpose.
     * 
     * @return the list of synonyms associated with this Non Medicinal Purpose.
     */
    public List<String> getSynonyms() {
        List<String> synonyms = new ArrayList<String>();
        for (Synonym synonym : nonMedicinalPurpose.getSynonyms()) {
            synonyms.add(synonym.getName());
        }
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        // Not Implemented.
    }
    
    //Comparable "compareTo" implementation.
    public int compareTo(NonMedicinalPurposeWS o) throws ClassCastException {

        if (nonMedicinalPurpose == null) {
            return 1;
        }
        if (o == null || o.getNonMedicinalPurpose() == null) {
            return -1;
        }

        return StringComparator.compare(nonMedicinalPurpose.getName(),
                ((NonMedicinalPurposeWS) o).getName());
    }

    // This private method is used for sorting purposes in the comparator.
    private NonMedicinalPurpose getNonMedicinalPurpose() {
        return nonMedicinalPurpose;
    }

    /**
     * Gets whether this purpose makes a distinction as to whether the ingredient
     * is artificial or natural. Most don't care.
     * 
     * @return true if the fact that the ingredient is artificial or natural
     *         matters.
     */
    public String getArtificialOrNatural() {
    	
    	// New 2.8 functionality.  Null returned for 2.7.
    	if (version.equals(ThreadContext.VERSION_1_4)) {
    		return null;
    	}

    	if (nonMedicinalPurpose.getArtificalOrNatural() != null
    			&& nonMedicinalPurpose.getArtificalOrNatural() == true) {
    		return TRUE;
    	}
    	return FALSE;
    }
    
    public void setArtificialOrNatural(String string) {
    	// Not Implemented.
    }
    
}