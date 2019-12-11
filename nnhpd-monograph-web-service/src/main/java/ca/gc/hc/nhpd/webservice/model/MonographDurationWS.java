/*
 * Created on 8-Jun-07
 *
 */
package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.MonographDuration;
import ca.gc.hc.nhpd.util.ThreadContext;

/**
 * @author MRABE
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MonographDurationWS implements Comparable<MonographDurationWS> {

    private String version = ThreadContext.getInstance().getVersion();

    // The wrapped MonographDuration
    private MonographDuration monographDuration;

    // The associated dependencies
    private MonographDependenciesWS monographDependenciesWS;

    public MonographDurationWS(MonographDuration monographDuration) {
        this.monographDuration = monographDuration;
        this.monographDependenciesWS = new MonographDependenciesWS(
                monographDuration);
    }

    public MonographDurationWS() {
        monographDuration = new MonographDuration();
        this.monographDependenciesWS = new MonographDependenciesWS();
    }

    public MonographDependenciesWS getMonographDependenciesWS() {
        return monographDependenciesWS;
    }

    public void setMonographDependenciesWS(
            MonographDependenciesWS monographDependenciesWS) {
        this.monographDependenciesWS = monographDependenciesWS;
    }

    public String getDurationStatement() {
        return monographDuration.getDurationStatement();
    }

    public void setDurationStatement(String durationStatement) {
        //
    }

    public int getMaximumDuration() {
        return monographDuration.getMaximumDuration();
    }

    public void setMaximumDuration(int maximumDuration) {
        //
    }

    public int getMinimumDuration() {
        return monographDuration.getMinimumDuration();
    }

    public void setMinimumDuration(int minimumDuration) {
        //
    }

    public UnitsWS getUnits() {
        return new UnitsWS(monographDuration.getUnits());
    }

    public void setUnits(UnitsWS units) {
        //
    }

    /**
     * Returns this object's ID.  This functionality was removed with the web 
     * service updates and only returns the id for backwards compatibility with
     * the ePLA 1.4 form.
     * 
     * @return this object's ID.
     */
    public Long getId() {
    
    	if (!version.equals(ThreadContext.VERSION_1_4)) {
    		return null;
    	}
    
        return monographDuration.getId();
    }

    public void setId(Long id) {
        //
    }

    public String getCode() {
        return monographDuration.getCode();
    }
    
    public void setCode(String code) {
        
    }
    
    public int compareTo(MonographDurationWS o) throws ClassCastException {
        return monographDuration
                .compareTo(o.monographDuration);
    }
}
