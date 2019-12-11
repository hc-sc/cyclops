package ca.gc.hc.nhpd.model;

import java.util.Set;

/*******************************************************************************
 * This object represents risks / directions that may appear on a medicinal
 * product label, e.g. "Not suitable for infants under the age of twelve months".
 */
public class CodedRisk extends PersistentObject { //TODO New Class
	private MeddraPreferredTerm preferredTerm;
	// private Set riskStatementsE;
	// private Set riskStatementsF;
	private Set<RiskStatement> riskStatements;
	private RiskType type;

    /***************************************************************************
	 * Gets the commonly used unique code for this object. In this case, it is a
	 * concatenation of the RiskType's code and the PreferredTerm's code in the form
	 * "type:term".
	 */
	public String getCode(){
        StringBuffer buffer = new StringBuffer();
        
        if (getType() != null) {
            buffer.append(getType().getCode());
        }
        buffer.append(":");
        if (getPreferredTerm() != null) {
            buffer.append(getPreferredTerm().getCode());
        }
        
		return buffer.toString();
	}

    /***************************************************************************
     */
	public MeddraPreferredTerm getPreferredTerm(){
		return preferredTerm;
	}

    /***************************************************************************
	 * Gets the risk statements. 
	 * @return the risk statements.
	 * @see #setRiskStatements()
	 */
	public Set<RiskStatement> getRiskStatements(){
		return riskStatements;
	}

    /***************************************************************************
     */
	public RiskType getType(){
		return type;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setPreferredTerm(MeddraPreferredTerm newVal){
		preferredTerm = newVal;
	}

    /***************************************************************************
	 * Sets the risk statements.
	 * @param newVal the risk statements
	 * @see #getRiskStatements()
	 */
	public void setRiskStatements(Set<RiskStatement> newVal){
		riskStatements = newVal;
	}

    /***************************************************************************
	 * @param newVal
	 */
	public void setType(RiskType newVal){
		type = newVal;
	}
}