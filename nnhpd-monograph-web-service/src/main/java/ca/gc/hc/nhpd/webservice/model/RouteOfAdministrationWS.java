package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.RouteOfAdministration;
import ca.gc.hc.nhpd.model.DosageForm;
import ca.gc.hc.nhpd.util.ThreadContext;

import java.util.Iterator;
import java.util.Set;

/*******************************************************************************
 * An object that represents the way a substance is to be administered (e.g.
 * orally, topically, etc.).
 */
public class RouteOfAdministrationWS {

    private static final String TRUE = "true";
    private static final String FALSE = "false";
    
    private String version = ThreadContext.getInstance().getVersion();
    
    // the wrapped RouteOfAdministration

    private RouteOfAdministration routeOfAdministration;

    private DosageFormWS[] possibleDosageForms;

    public RouteOfAdministrationWS(RouteOfAdministration routeOfAdmin) {
        if (routeOfAdmin == null) {
            this.routeOfAdministration = new RouteOfAdministration();
            possibleDosageForms = new DosageFormWS[0];
        } else {
            this.routeOfAdministration = routeOfAdmin;
            initializeDosageForms(routeOfAdmin);
        }
    }

    private void initializeDosageForms(RouteOfAdministration routeOfAdmin) {
        Set<DosageForm> dosageForms = routeOfAdmin.getPossibleDosageForms();
        possibleDosageForms = new DosageFormWS[dosageForms.size()];
        Iterator<DosageForm> iter = dosageForms.iterator();
        int x = -1;
        while (iter.hasNext()) {
            possibleDosageForms[++x] = new DosageFormWS((DosageForm) iter
                    .next());
        }
    }

    public RouteOfAdministrationWS() {
        routeOfAdministration = new RouteOfAdministration();
    }

    public String getNoFixedLimits() {
    	
    	// New 2.8 functionality.  Null returned for 2.7.
    	if (version.equals(ThreadContext.VERSION_1_4)) {
    		return null;
    	}
    	
        if (routeOfAdministration.getNoFixedLimits() != null
                && routeOfAdministration.getNoFixedLimits() == true) {
            return TRUE;
        }
        return FALSE;
        
    }
    
    public void setNoFixedLimits(String value) {
        // not implemented.
    }
    
    public String getRoaCode() {
        return routeOfAdministration.getCode();
    }

    public void setRoaCode(String code) {
        // routeOfAdministration.setCode(code);
    }

    public String getRoaName() {
        return routeOfAdministration.getName();
    }

    public void setRoaName(String name) {
        //
    }

    /**
     * @return
     */
    public DosageFormWS[] getPossibleDosageForms() {
        return possibleDosageForms;
    }

    /**
     * @param formWSs
     */
    public void setPossibleDosageForms(DosageFormWS[] formWSs) {
        // possibleDosageForms = formWSs;
    }

    /**
     * Gets whether this route of administration requires a sterile ingredient.
     * Most don't care.
     * 
     * @return true if this route of administration requires a sterile
     *         ingredient.
     */
    public String getSterileRequired() {
    	
    	// New 2.8 functionality.  Null returned for 2.7.
    	if (version.equals(ThreadContext.VERSION_1_4)) {
    		return null;
    	}
    	
        if (routeOfAdministration.getSterileRequired() != null
                && routeOfAdministration.getSterileRequired() == true) {
            return TRUE;
        }
        return FALSE;
        
    }
    
    public void setSterileRequired(String sterileRequired) {
    	// Not Implemented.
    }
    
}
