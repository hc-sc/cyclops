/*
 * Created on 8-Jun-07
 */
package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.DosageUnit;
import ca.gc.hc.nhpd.util.StringComparator;

/**
 * Exposes the Dosage Unit object.
 */

public class DosageUnitsWS implements Comparable<DosageUnitsWS> {

    // The wrapped DosageForm
    private DosageUnit dosageUnit;

    public DosageUnitsWS(DosageUnit dosageUnit) {
        
        this.dosageUnit = dosageUnit;
    }

    public DosageUnitsWS() {
        dosageUnit = new DosageUnit();
    }
    
    /**
     * Returns the code for this Dosage Unit object.
     * 
     * @return the code of the Dosage Unit object.
     */

    public String getDosageUnitCode() {
        return dosageUnit.getCode();
    }

    public void setDosageUnitCode(String code) {
        //
    }

    /**
     * Returns the name for this Dosage Unit object.
     * 
     * @return the name of the Dosage Unit object.
     */
    public String getName() {
        return dosageUnit.getName();
    }

    public void setName(String name) {
        // Not Implemented.
    }

    // Comparator since the Dosage Unit is returned as part of a set.
    public int compareTo(DosageUnitsWS o) throws ClassCastException {

        if (dosageUnit == null) {
            return 1;
        }
        if (o == null || o.getDosageUnitCode() == null) {
            return -1;
        }

        // DosageFormWS dosageFormWS = (DosageFormWS) o;
        // return dosageForm.compareTo(dosageFormWS.dosageForm);
        return StringComparator.compare(dosageUnit.getCode(),
                ((DosageUnitsWS) o).getDosageUnitCode());
    }

}
