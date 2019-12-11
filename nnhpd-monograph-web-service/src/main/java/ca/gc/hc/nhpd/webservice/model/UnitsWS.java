/*
 * Created on 8-Jun-07
 *
 */
package ca.gc.hc.nhpd.webservice.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import ca.gc.hc.nhpd.model.Units;
import ca.gc.hc.nhpd.model.UnitsType;
import ca.gc.hc.nhpd.util.ThreadContext;

/**
 * @author MRABE
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UnitsWS {

	private static final String TRUE = "true";
    private static final String FALSE = "false";
    
	private String version = ThreadContext.getInstance().getVersion();
	
	// Support up to 15 decimal places.
	NumberFormat formatter = new DecimalFormat("#.###############");

    // The wrapped Units
    private Units units;

    public UnitsWS(Units units) {
        // create interim objects to avoid JAX-WS NullPointerExceptions...
        if (units == null) {
            this.units = new Units();
            this.units.setType(new UnitsType());
        } else
            this.units = units;
    }

    public UnitsWS() {
        units = new Units();
        units.setType(new UnitsType());
    }

    public String getCode() {
        return units.getCode();
    }

    public void setCode(String code) {
        //
    }
    
    public String getEquivalent() {
        if (units != null 
                && units.isEquivalent() != null
                && units.isEquivalent() == true) {
            return TRUE;
        }
        return FALSE;
    }
    
    public void setEquivalent(String equivalent) {
        //
    }

    public String getName() {
        return units.getName();
    }

    public void setName(String name) {
        //
    }

    /**
     * Returns a flag indicating if this is a preferred unit.
     * 
     * @return boolean flag indicating if this is a preferred unit.
     */
    public String getPreferred() {
    	
        if (units != null 
                && units.isPreferred() != null
                && units.isPreferred() == true) {
            return TRUE;
        }
        return FALSE;
    }
    
    public void setPreferred(String preferred) {
        //
    }

    /**
     * Returns a number that represents the ratio of this unit
     * to the base unit.
     * 
     * @return a number that represents this units ratio to base.
     */
    public String getRatioToBase() {
    	
    	if (version.equals(ThreadContext.VERSION_1_4)) {
    		return null;
    	}

    	Double ratioToBase = units.getRatioToBase();
    	
        return formatter.format(ratioToBase);
        
    }
    
    public void setRatioToBase(String ratioToBase) {
        //
    }
    
    public String getUnitType() {
        return units.getType().getName();
    }

    public void setUnitType(String unitType) {
        //
    }

    public String getUnitTypeCode() {
    	
    	if (version.equals(ThreadContext.VERSION_1_4)) {
    		return null;
    	}

        return units.getType().getCode();
    }

    public void setUnitTypeCode(String unitTypeCode) {
        //
    }

}
