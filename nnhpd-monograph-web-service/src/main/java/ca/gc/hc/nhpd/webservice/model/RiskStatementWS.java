/*
 * Created on 8-Jun-07
 *
 */
package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.MonographRiskStatement;

/**
 * @author MRABE
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RiskStatementWS {

    // The wrapped MonographDose
    private MonographRiskStatement monographRiskStatement;

    public RiskStatementWS(MonographRiskStatement monographRiskStatement) {
        this.monographRiskStatement = monographRiskStatement;
    }

    public RiskStatementWS() {
        monographRiskStatement = new MonographRiskStatement();
    }
    
    public String getCode() {
    	return monographRiskStatement.getCode();
    }
    
    public void setCode(String code) {
    	// do nothing!
    }

}
