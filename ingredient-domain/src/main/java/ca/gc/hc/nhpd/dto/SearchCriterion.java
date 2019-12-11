/*
 * Models a simple search criterion.  
 * Created on 4-Jun-07
 *
 */
package ca.gc.hc.nhpd.dto;

/**
 * @author MRABE
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SearchCriterion {

    public final static int EQUALS = 1;

    public final static int LT = 2;

    public final static int GT = 3;

    public final static int LTOREQUALS = 4;

    public final static int GTOREQUALS = 5;

    public final static int CONTAINS = 6;

    public final static int BEGINSWITH = 7;

    public final static int ENDSWITH = 8;

    private String attributeName;

    private int operator;

    private String attributeValue;

    public SearchCriterion() {
    }

    public SearchCriterion(String attributeName, String attributeValue,
            int operator) {
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
        this.operator = operator;
    }

    /**
     * @return
     */
    public String getAttributeName() {
        return attributeName;
    }

    /**
     * @return
     */
    public String getAttributeValue() {
        return attributeValue;
    }

    /**
     * @return
     */
    public int getOperator() {
        return operator;
    }

    /**
     * @param string
     */
    public void setAttributeName(String string) {
        attributeName = string;
    }

    /**
     * @param string
     */
    public void setAttributeValue(String string) {
        attributeValue = string;
    }

    /**
     * @param i
     */
    public void setOperator(int i) {
        operator = i;
    }

}
