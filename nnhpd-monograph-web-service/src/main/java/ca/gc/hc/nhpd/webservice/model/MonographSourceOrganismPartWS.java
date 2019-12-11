package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.MonographSourceOrganismPart;

public class MonographSourceOrganismPartWS implements Comparable<MonographSourceOrganismPartWS> {

    // The wrapped MonographSourceOrganismPart
    private MonographSourceOrganismPart monographSourceOrganismPart;

    public MonographSourceOrganismPartWS(
            MonographSourceOrganismPart monographSourceOrganismPart) {
        this.monographSourceOrganismPart = monographSourceOrganismPart;
    }

    public MonographSourceOrganismPartWS() {
        monographSourceOrganismPart = new MonographSourceOrganismPart();
    }

    public String getSourceMaterialName() {
        return monographSourceOrganismPart.getSourceMaterialName();
    }

    public void setSourceMaterialName(String name) {
        //
    }

    public int compareTo(MonographSourceOrganismPartWS o) throws ClassCastException {
        // delegate to wrapped type
        return monographSourceOrganismPart
                .compareTo(o.monographSourceOrganismPart);
    }

    /* No longer used in favor of the getSourceOrganismPart - GB - 2355 
    public OrganismPartWS getOrganismPart() {
        return new OrganismPartWS(
                monographSourceOrganismPart.getOrganismPart(), false);
    }

    public void setOrganismPart(OrganismPartWS organismPart) {
        //
    }
    */

    public SourceOrganismPartWS getSourceOrganismPart() {
        if (monographSourceOrganismPart != null 
                && monographSourceOrganismPart.getSourceOrganismPart() != null) {
            return new SourceOrganismPartWS(
                monographSourceOrganismPart.getSourceOrganismPart());
        }
        return null;
    }

    public void setSourceOrganismPart(SourceOrganismPartWS organismPart) {
        //
    }

}
