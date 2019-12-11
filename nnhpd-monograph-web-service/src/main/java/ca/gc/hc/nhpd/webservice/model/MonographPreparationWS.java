package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.MonographPreparation;

public class MonographPreparationWS {
    // The wrapped MonographPreparation
    private MonographPreparation monographPreparation;

    public MonographPreparationWS(MonographPreparation monographPreparation) {
        this.monographPreparation = monographPreparation;
    }

    public MonographPreparationWS() {
        monographPreparation = new MonographPreparation();
    }

    public String getCode() {
        return monographPreparation.getCode();
    }

    public void setCode(String code) {
        //
    }

    public String getName() {
        return monographPreparation.getName();
    }
    
    public void setName(String name) {
        //
    }
    
    public Float getMaximumExtractionRatio() {
        return monographPreparation.getMaximumExtractionRatio();
    }

    public void setMaximumExtractionRatio(Float maximumExtractionRatio) {
        //
    }

    public Float getMinimumExtractionRatio() {
        return monographPreparation.getMinimumExtractionRatio();
    }

    public void setMinimumExtractionRatio(Float minimumExtractionRatio) {
        //
    }

    public PreparationTypeGroupWS getPreparationTypeGroup() {
        return new PreparationTypeGroupWS(monographPreparation
                .getPreparationTypeGroup());
    }

    public void setPreparationTypeGroup(
            PreparationTypeGroupWS preparationTypeGroup) {
        //
    }

    public PreparationTypeWS getPreparationType() {
        return new PreparationTypeWS(monographPreparation.getPreparationType());
    }

    public void setPreparationType(PreparationTypeWS preparationType) {
        //
    }

}
