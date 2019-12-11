package ca.gc.hc.nhpd.webservice.model;


import java.math.BigDecimal;

import ca.gc.hc.nhpd.dto.PreClearedInfo;
import ca.gc.hc.nhpd.util.StringComparator;

public class PreClearedInfoWS implements Comparable<PreClearedInfoWS>{

	    // The wrapped PreClearedInfo
	    private PreClearedInfo preClearedInfo;

	    public PreClearedInfoWS(PreClearedInfo preClearedInfo) {
	        this.preClearedInfo = preClearedInfo;
	    }

	    public PreClearedInfoWS() {
	    	preClearedInfo = new PreClearedInfo();
	    }

	    public long getMonoId() {
	        return preClearedInfo.getMonoId();
	    }

	    public void setMonoId(long monoId) {
	        //
	    }

	    public String getName() {
	        return preClearedInfo.getName();
	    }

	    public void setName(String name) {
	        //
	    }

	    /*
	     * Implement Comparable compareTo method. Method required because this
	     * object is added to a TreeSet Does an ignore case compare on the
	     * Preparation Name
	     */
	    public int compareTo(PreClearedInfoWS o) throws ClassCastException {
	        return StringComparator.compare(preClearedInfo.getName(),
	                ((PreClearedInfoWS) o).getName());

	    }
	    
	}


