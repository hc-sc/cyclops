/*
 * Created on 8-Jun-07
 *
 */
package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.DosageForm;
import ca.gc.hc.nhpd.model.MonographRouteOfAdministration;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author MRABE
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MonographRouteOfAdministrationWS implements
        Comparable<MonographRouteOfAdministrationWS> {

    // The wrapped MonographRiskStatement
    private MonographRouteOfAdministration monographRouteOfAdministration;

    public MonographRouteOfAdministrationWS(
            MonographRouteOfAdministration monographRouteOfAdministration) {
        this.monographRouteOfAdministration = monographRouteOfAdministration;
    }

    public MonographRouteOfAdministrationWS() {
        monographRouteOfAdministration = new MonographRouteOfAdministration();
    }

    public Set<DosageFormWS> getMonographDosageForms() {

        Set<DosageFormWS> retDosageForms = new TreeSet<DosageFormWS>();
        Set<DosageForm> dosageForms = monographRouteOfAdministration
                .getApplicableDosageForms();
        // log("MonoRoaWS: getMonographDosageForms 1: Size:" +
        // dosageForms.size());

        DosageForm dosageForm = null;
        Iterator<DosageForm> iter = dosageForms.iterator();
        while (iter.hasNext()) {

            dosageForm = (DosageForm) (iter.next());
            // log("MonoRoaWS: getMonographDosageForms 2 Roa Id: " +
            retDosageForms.add(new DosageFormWS(dosageForm));
            // log("MonoRoaWS: getMonographDosageForms 3 ");
        }
        return retDosageForms;
    }

    public void setMonographDosageForms(Set<DosageFormWS> dosageForms) {
        //
    }

    public String getCode() {
        return monographRouteOfAdministration.getRouteOfAdministration()
                .getCode();
    }

    public void setCode(String code) {
        //
    }

    public String getName() {
        return monographRouteOfAdministration.getRouteOfAdministration()
                .getName();
    }

    public void setName(String name) {
        //
    }

    public String getComment() {
        return monographRouteOfAdministration.getRouteOfAdministration()
                .getComment();
    }

    public void setComment(String comment) {
        //
    }

    public int compareTo(MonographRouteOfAdministrationWS o) {
        return this.monographRouteOfAdministration
                .compareTo(o.monographRouteOfAdministration);
    }

}
