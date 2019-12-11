package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.DosageForm;
import ca.gc.hc.nhpd.model.DosageFormGroup;
import ca.gc.hc.nhpd.util.StringComparator;

import java.util.Set;
import java.util.TreeSet;

public class DosageFormGroupWS implements Comparable<DosageFormGroupWS> {
    // The wrapped DosageFormGroup
    private DosageFormGroup dosageFormGroup;
    private Set<DosageFormWS> dosageForms;

    public DosageFormGroupWS(DosageFormGroup dosageFormGroup) {
        this.dosageFormGroup = dosageFormGroup;
    }

    public DosageFormGroupWS() {
        dosageFormGroup = new DosageFormGroup();
    }

    // TODO expose attrs
    public String getCode() {
        if (dosageFormGroup != null && dosageFormGroup.getCode() != null) {
            return dosageFormGroup.getCode();
        }
        return null;
    }
    
    public void setCode(String code) {
        // Do nothing.
    }

    public String getName() {
        if (dosageFormGroup != null && dosageFormGroup.getName() != null) {
            return dosageFormGroup.getName();
        }
        return null;
    }
    
    public void setName(String name) {
        // Do nothing.
    }
    
    // Comparator since the Dosage Form Group is returned as part of a set.
    public int compareTo(DosageFormGroupWS o) throws ClassCastException {

        if (dosageFormGroup == null) {
            return 1;
        }
        if (o == null || o.getDosageFormGroup() == null) {
            return -1;
        }

        return StringComparator.compare(dosageFormGroup.getCode(),
                ((DosageFormGroupWS) o).getCode());
    }
    
    public DosageFormGroup getDosageFormGroup() {
        return dosageFormGroup;
    }
    
    public Set<DosageFormWS> getDosageForms() {
        if (dosageFormGroup == null || dosageFormGroup.getDosageForms() == null) {
            return null;
        }
        dosageForms = new TreeSet<DosageFormWS>();
        for (DosageForm dosageForm : dosageFormGroup.getDosageForms()) {
            dosageForms.add(new DosageFormWS(dosageForm));
        }
        return dosageForms;
    }
    
    public void setDosageForms(Set<DosageFormWS> dosageForms) {
        // Do nothing.
    }
    
}
