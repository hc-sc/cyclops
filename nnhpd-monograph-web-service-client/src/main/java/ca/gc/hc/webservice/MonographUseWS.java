
package ca.gc.hc.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for monographUseWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="monographUseWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="longStatement" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="monographDependenciesWS" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographDependenciesWS" minOccurs="0"/>
 *         &lt;element name="recommendedDietaryAllowance" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}recommendedDietaryAllowanceWS" minOccurs="0"/>
 *         &lt;element name="shortStatement" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "monographUseWS", propOrder = {
    "code",
    "id",
    "longStatement",
    "monographDependenciesWS",
    "recommendedDietaryAllowance",
    "shortStatement"
})
public class MonographUseWS {

    protected String code;
    protected Long id;
    protected String longStatement;
    protected MonographDependenciesWS monographDependenciesWS;
    protected RecommendedDietaryAllowanceWS recommendedDietaryAllowance;
    protected String shortStatement;

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the longStatement property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLongStatement() {
        return longStatement;
    }

    /**
     * Sets the value of the longStatement property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLongStatement(String value) {
        this.longStatement = value;
    }

    /**
     * Gets the value of the monographDependenciesWS property.
     * 
     * @return
     *     possible object is
     *     {@link MonographDependenciesWS }
     *     
     */
    public MonographDependenciesWS getMonographDependenciesWS() {
        return monographDependenciesWS;
    }

    /**
     * Sets the value of the monographDependenciesWS property.
     * 
     * @param value
     *     allowed object is
     *     {@link MonographDependenciesWS }
     *     
     */
    public void setMonographDependenciesWS(MonographDependenciesWS value) {
        this.monographDependenciesWS = value;
    }

    /**
     * Gets the value of the recommendedDietaryAllowance property.
     * 
     * @return
     *     possible object is
     *     {@link RecommendedDietaryAllowanceWS }
     *     
     */
    public RecommendedDietaryAllowanceWS getRecommendedDietaryAllowance() {
        return recommendedDietaryAllowance;
    }

    /**
     * Sets the value of the recommendedDietaryAllowance property.
     * 
     * @param value
     *     allowed object is
     *     {@link RecommendedDietaryAllowanceWS }
     *     
     */
    public void setRecommendedDietaryAllowance(RecommendedDietaryAllowanceWS value) {
        this.recommendedDietaryAllowance = value;
    }

    /**
     * Gets the value of the shortStatement property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortStatement() {
        return shortStatement;
    }

    /**
     * Sets the value of the shortStatement property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortStatement(String value) {
        this.shortStatement = value;
    }

}
