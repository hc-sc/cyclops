/*
 * Created on 8-Jun-07
 *
 */
package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.util.StringComparator;
import ca.gc.hc.nhpd.util.ThreadContext;
import ca.gc.hc.nhpd.model.MonographUse;

/**
 * @author MRABE
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MonographUseWS implements Comparable<MonographUseWS> {

    private String version = ThreadContext.getInstance().getVersion();

    // The wrapped MonographRiskStatement
    private MonographUse monographUse;

    public MonographUseWS(MonographUse monographUse, int dependencyLevel) {
        this.monographUse = monographUse;
    }

    public MonographUseWS() {
        monographUse = new MonographUse();
    }

    /*
     * Dependencies are comprised of a set of base class attributes of the mdo.
     * We'll build a distinct MonographDependencies object for the web service,
     * and initialize it on demand.
     */
    public MonographDependenciesWS getMonographDependenciesWS() {
    	MonographDependenciesWS monographDependenciesWS = new MonographDependenciesWS(monographUse);
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
        return monographUse.getCode();
    }
    
    public void setCode(String code) {
        //
    }
    
    public String getLongStatement() {
        return monographUse.getLongStatement();
    }

    public void setLongStatement(String longStatement) {
        //
    }

    public String getShortStatement() {
        return monographUse.getShortStatement();
    }

    public void setShortStatement(String shortStatement) {
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
    
        return monographUse.getId();
    }

    public void setId(Long id) {
        //
    }

    public RecommendedDietaryAllowanceWS getRecommendedDietaryAllowance() {
        if (monographUse != null
                && monographUse.getRecommendedDietaryAllowance() != null) {
            return new RecommendedDietaryAllowanceWS(monographUse.getRecommendedDietaryAllowance());
        }
        return null;
    }

    public void setRecommendedDietaryAllowance(RecommendedDietaryAllowanceWS recommendedDietaryAllowanceWS) {
        //
    }
    
    /*
     * Method required because this object is added to a TreeSet (in
     * MonographWS) Does an ignore case compare on the name
     */
    public int compareTo(MonographUseWS o) throws ClassCastException {
        return StringComparator.compare(monographUse.getLongStatement(),
                o.getLongStatement());
    }
}
