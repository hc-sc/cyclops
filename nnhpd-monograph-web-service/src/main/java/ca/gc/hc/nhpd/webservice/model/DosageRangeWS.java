package ca.gc.hc.nhpd.webservice.model;

public class DosageRangeWS {
    // had problems wrapping the DosageRange here...
    // null id in PersistentObject was being used to retrieve ShortDesc
    private Double maximumDosage;

    private Double minimumDosage;

    private String dosageUnits;

    public String getDosageUnits() {
        return dosageUnits;
    }

    public void setDosageUnits(String dosageUnits) {
        this.dosageUnits = dosageUnits;
    }

    public Double getMaximumDosage() {
        return maximumDosage;
    }

    public void setMaximumDosage(Double maximumDosage) {
        this.maximumDosage = maximumDosage;
    }

    public Double getMinimumDosage() {
        return minimumDosage;
    }

    public void setMinimumDosage(Double minimumDosage) {
        this.minimumDosage = minimumDosage;
    }

}