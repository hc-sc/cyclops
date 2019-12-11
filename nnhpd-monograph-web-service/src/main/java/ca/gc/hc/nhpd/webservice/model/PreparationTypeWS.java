package ca.gc.hc.nhpd.webservice.model;

import java.util.Set;
import java.util.TreeSet;

import ca.gc.hc.nhpd.model.PreparationType;
import ca.gc.hc.nhpd.model.Solvent;
import ca.gc.hc.nhpd.util.StringComparator;

public class PreparationTypeWS implements Comparable<PreparationTypeWS> {

    private static final String REQUIRED = "true";
    private static final String NOT_REQUIRED = "false";
    
    // the wrapped PartPreparation
    private PreparationType preparationType;

    private String preparationTypeGroupCode;

    public PreparationTypeWS(PreparationType prepType) {
        if (prepType == null)
            preparationType = new PreparationType();
        else
            preparationType = prepType;
    }

    public PreparationTypeWS() {
        this.preparationType = new PreparationType();
    }

    public String getCode() {
        return preparationType.getCode();
    }

    public void setCode(String code) {
        preparationType.setCode(code);
    }

    public String getExtract() {
        if (preparationType != null
                && preparationType.getExtract() != null
                && preparationType.getExtract() == true) {
            return REQUIRED;
        }
        return NOT_REQUIRED;
    }

    public void setExtract(String extract) {
        //
    }    
    
    public String getName() {
        return preparationType.getName();
    }

    public void setName(String name) {
        //
    }

    public String getPreparationTypeGroupCode() {
        return preparationTypeGroupCode;
    }

    public void setPreparationTypeGroupCode(String preparationTypeGroupCode) {
        this.preparationTypeGroupCode = preparationTypeGroupCode;
    }

    public String getRatioType() {
        return preparationType.getRatioType();
    }

    public void setRatioType(String ratioType) {
        //
    }
    
    public Set<SolventWS> getRestrictedSolvents() {
        if (preparationType.getRestrictedSolvents() == null) {
            return null;
        }
        Set<SolventWS> solventsWS = new TreeSet<SolventWS>();
        for (Solvent solvent : preparationType.getRestrictedSolvents()) {
            solventsWS.add(new SolventWS(solvent));
        }
        return solventsWS;
    }
    
    public void setRestrictedSolvents(Set<SolventWS> solvents) {
        // Not implemented.
    }

    public String getStandardized() {
        if (preparationType != null 
                && preparationType.getStandardized() != null
                && preparationType.getStandardized() == true) {
            return REQUIRED;
        }
        return NOT_REQUIRED;
    }

    public void setStandardized(String standardized) {
        //
    }    
    
    /*
     * Implement Comparable compareTo method. Method required because this
     * object is added to a TreeSet Does an ignore case compare on the
     * Preparation Name
     */
    public int compareTo(PreparationTypeWS o) throws ClassCastException {
        // String thisName = preparationType.getName().toLowerCase();
        // String compName = ((PreparationTypeWS)o).getName().toLowerCase();
        return StringComparator.compare(preparationType.getName(),
                ((PreparationTypeWS) o).getName());

    }
}
