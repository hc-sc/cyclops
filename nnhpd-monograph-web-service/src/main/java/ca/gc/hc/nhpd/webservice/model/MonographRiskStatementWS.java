/*
 * Created on 8-Jun-07
 *
 */
package ca.gc.hc.nhpd.webservice.model;

import ca.gc.hc.nhpd.model.MonographRiskStatement;
import ca.gc.hc.nhpd.model.RiskStatement;
import ca.gc.hc.nhpd.util.ThreadContext;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author MRABE
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MonographRiskStatementWS implements Comparable<MonographRiskStatementWS> {

    // I don't like the codes in the DB, let's map them.
    private static final String CONTRAINDICATION = "CONTRAINDICATION";

    private static final String CAUTIONWARNING = "CAUTIONWARNING";

    private static final String ADVERSEREACTION = "ADVERSEREACTION";

    private static final String DB_CONTRAINDICATION = "CONTERINDICATION";

    private static final String DB_CAUTIONWARNING = "CA_WA";

    private static final String DB_ADVERSEREACTION = "ADR";

    private String version = ThreadContext.getInstance().getVersion();

    // The wrapped MonographRiskStatement
    private MonographRiskStatement monographRiskStatement;

    // The associated dependencies
    private MonographDependenciesWS monographDependenciesWS;

    public MonographRiskStatementWS(
            MonographRiskStatement monographRiskStatement) {
        this.monographRiskStatement = monographRiskStatement;
        this.monographDependenciesWS = new MonographDependenciesWS(
                monographRiskStatement);
    }

    public MonographRiskStatementWS() {
        monographRiskStatement = new MonographRiskStatement();
        this.monographDependenciesWS = new MonographDependenciesWS();
    }

    public String getRiskType() {
        String dbRiskType = monographRiskStatement.getRiskStatement()
                .getCodedRisk().getType().getCode();
        return mapRiskType(dbRiskType);
    }

    public void setRiskType(String riskType) {
        //
    }

    private String mapRiskType(String dbRiskType) {
        if (dbRiskType.equals(DB_CONTRAINDICATION))
            return CONTRAINDICATION;
        if (dbRiskType.equals(DB_CAUTIONWARNING))
            return CAUTIONWARNING;
        if (dbRiskType.equals(DB_ADVERSEREACTION))
            return ADVERSEREACTION;
        return "UNDEFINED";
    }

    public MonographDependenciesWS getMonographDependenciesWS() {
        MonographDependenciesWS monographDependenciesWS = new MonographDependenciesWS(monographRiskStatement);
        if (monographDependenciesWS.hasDependency()) {
            return monographDependenciesWS;
        }
        return null;
    }

    public void setMonographDependenciesWS(
            MonographDependenciesWS monographDependenciesWS) {
        this.monographDependenciesWS = monographDependenciesWS;
    }

    public String getRiskStatement() {
        return monographRiskStatement.getRiskStatement().getName();
    }

    public void setRiskStatement(String riskStatement) {
        //
    }

    // These are the RiskStatements that are retrieved via the CodedRisk
    public Set<String> getRiskStatements() {

        Set<String> risks = new TreeSet<String>();
        RiskStatement riskStatement = monographRiskStatement.getRiskStatement();
        if ((riskStatement != null) && (riskStatement.getCodedRisk() != null)) {
            Set<RiskStatement> statements = riskStatement.getCodedRisk()
                    .getRiskStatements();
            for (RiskStatement statement : statements) {
                risks.add(statement.getName());
            }

        }
        return risks;

    }

    public void setRiskStatements(Set<String> statements) {
        //
    }

    public int compareTo(MonographRiskStatementWS o) throws ClassCastException {
        // delegate to wrapped type
        return monographRiskStatement
                .compareTo(o.monographRiskStatement);

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
    
        return monographRiskStatement.getId();
    }

    public void setId(Long id) {
        //
    }
    
    public String getCode() {
        return monographRiskStatement.getCode();
    }
    
    public void setCode(String code) {
        //
    }

}
