/*
 * Created on 8-Jun-07
 *
 */
package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.MonographDirectionForUse;

/**
 * @author MRABE
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MonographDirectionForUseWS implements Comparable<MonographDirectionForUseWS> {

    // The wrapped MonographDirectionsForUse
    private MonographDirectionForUse monographDirectionForUse;

    // The associated dependencies
    private MonographDependenciesWS monographDependenciesWS;

    public MonographDirectionForUseWS(
            MonographDirectionForUse monographDirectionForUse) {
        
        this.monographDirectionForUse = monographDirectionForUse;
        this.monographDependenciesWS = new MonographDependenciesWS(
                monographDirectionForUse);
    }

    public MonographDirectionForUseWS() {
        monographDirectionForUse = new MonographDirectionForUse();
        this.monographDependenciesWS = new MonographDependenciesWS();
    }

    public MonographDependenciesWS getMonographDependenciesWS() {
        MonographDependenciesWS monographDependenciesWS = new MonographDependenciesWS(monographDirectionForUse);
        if (monographDependenciesWS.hasDependency()) {
            return monographDependenciesWS;
        }
        return null;
    }

    public void setMonographDependenciesWS(
            MonographDependenciesWS monographDependenciesWS) {
        //
    }

    public String getCode() {
        return monographDirectionForUse.getCode();
    }
    
    public void setCode(String code) {
        //
    }

    public String getDirectionForUse() {
        return monographDirectionForUse.getDirectionForUse();
    }

    public void setDirectionForUse(String direction) {
        //
    }
    
    // delegate the compareTo call to the wrapped object
    public int compareTo(MonographDirectionForUseWS directionWS) throws ClassCastException {
        return monographDirectionForUse
                .compareTo(directionWS.monographDirectionForUse);
    }
}
