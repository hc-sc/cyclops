/*
 * Created on 8-Jun-07
 */
package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.DosageForm;
import ca.gc.hc.nhpd.model.DosageUnit;
import ca.gc.hc.nhpd.model.Synonym;
import ca.gc.hc.nhpd.util.StringComparator;
import ca.gc.hc.nhpd.util.ThreadContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MRABE
 * 
 * @updated by Germain Brunet - Exposed the synonym as part of CR-0033
 */

public class DosageFormWS implements Comparable<DosageFormWS> {

    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private static final String COMMA = ",";

	private String version = ThreadContext.getInstance().getVersion();

    // The wrapped DosageForm
    private DosageForm dosageForm;

    public DosageFormWS(DosageForm dosageForm) {
        
        this.dosageForm = dosageForm;
    }

    public DosageFormWS() {
        dosageForm = new DosageForm();
    }

    public String isAllowIngredientUnits() {
    	
    	if (version.equals(ThreadContext.VERSION_1_4)) {
    		return null;
    	}
    	
        if (dosageForm != null
                && dosageForm.isAllowIngredientUnits() != null
                && dosageForm.isAllowIngredientUnits() == true) {
            return TRUE;
        }
        return FALSE;
    }
    
    public void setAllowIngredientUnits(String isAllowIngredientUnits) {
        //
    }
    
    public String getDosageFormCode() {
        return dosageForm.getCode();
    }

    public void setDosageFormCode(String code) {
        //
    }
    
    /**
     * Returns the synonyms associated with this Dosage Form. Note that the
     * synonyms stored with the Dosage Form are comma delimited in a single
     * string. To create separate synonym entries, we will split the string
     * using the comma character.
     * 
     * Returning the DosageUnits is a new 2.0 ePLA requirement.
     * 
     * @return the list of synonyms associated with this dosage form.
     */
    public List<DosageUnitsWS> getDosageUnits() {
    	
    	if (version.equals(ThreadContext.VERSION_1_4)) {
    		return null;
    	}
        
        if (dosageForm == null
                || dosageForm.getDosageUnits() == null) {
            return null;
        }
        List<DosageUnitsWS> dosageUnitsWS = new ArrayList<DosageUnitsWS>();
        for (DosageUnit dosageUnit : dosageForm.getDosageUnits()) {
            if (dosageUnit != null && dosageUnit.getCode() != null) {
                dosageUnitsWS.add(new DosageUnitsWS(dosageUnit));
            }
        }
        return dosageUnitsWS;
    }

    public void setDosageUnits(List<DosageUnitsWS> dosageUnits) {
        // Not Implemented. }
    }
    
    /**
     * Returns the name for this Dosage Form object.
     * 
     * @return the name of the Dosage Form object.
     */
    public String getName() {
        return dosageForm.getName();
    }

    public void setName(String name) {
        // Not Implemented.
    }

    /**
     * Returns a boolean flag indicating if the assessment is required.
     * 
     * @return boolean flag indicating if the assessment is required.
     */
    public String isAssessmentRequired() {
        if (dosageForm != null
                && dosageForm.isAssessmentRequired() == true) {
            return TRUE;
        }
        return FALSE;
    }

    public void setAssessmentRequired(String assessmentRequired) {
        // Not Implemented.
    }

    /**
     * Returns the synonyms associated with this Dosage Form. Note that the
     * synonyms stored with the Dosage Form are comma delimited in a single
     * string. To create separate synonym entries, we will split the string
     * using the comma character.
     * 
     * @return the list of synonyms associated with this dosage form.
     */
    public List<String> getSynonyms() {
        List<String> synonyms = new ArrayList<String>();
        for (Synonym synonym : dosageForm.getSynonyms()) {
            if (synonym != null && synonym.getName() != null) {
                String names[] = synonym.getName().split(COMMA);
                for (String name : names) {
                    synonyms.add(name.trim());
                }
            }
        }
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        // Not Implemented. }
    }

    // Comparator since the Dosage Form is returned as part of a set.
    public int compareTo(DosageFormWS o) throws ClassCastException {

        if (dosageForm == null) {
            return 1;
        }
        if (o == null || o.getDosageForm() == null) {
            return -1;
        }

        // DosageFormWS dosageFormWS = (DosageFormWS) o;
        // return dosageForm.compareTo(dosageFormWS.dosageForm);
        return StringComparator.compare(dosageForm.getName(),
                ((DosageFormWS) o).getName());
    }

    // This private method is used for sorting purposes in the comparator.
    private DosageForm getDosageForm() {
        return dosageForm;
    }

}
