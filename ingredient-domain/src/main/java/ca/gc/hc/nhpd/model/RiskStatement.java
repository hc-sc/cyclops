package ca.gc.hc.nhpd.model;

import java.util.Iterator;

/*******************************************************************************
 * This object represents risks / directions that may appear on a medicinal
 * product label, e.g. "Not suitable for infants under the age of twelve months".
 */
public class RiskStatement extends Synonym { //TODO New Class
	private CodedRisk codedRisk;

    /***************************************************************************
     */
	public CodedRisk getCodedRisk(){
		return codedRisk;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setCodedRisk(CodedRisk newVal){
		codedRisk = newVal;
	}
    
    /**
     * Helper method to navigate the RiskStatement - CodedRisk - RiskType relationship 
     * and return the riskType as a string.  Also used in the sorting of the 
     * riskStatements so that they can be groupped by RiskType.
     */
    public String getRiskTypeName() {
        if ( getCodedRisk() == null 
            || getCodedRisk().getType() == null ) {
            return null;
        }
        return getCodedRisk().getType().getName();
    }
}