
package ca.gc.hc.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for monographDirectionForUseWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="monographDirectionForUseWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="directionForUse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="monographDependenciesWS" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographDependenciesWS" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "monographDirectionForUseWS", propOrder = {
    "code",
    "directionForUse",
    "monographDependenciesWS"
})
public class MonographDirectionForUseWS {

    protected String code;
    protected String directionForUse;
    protected MonographDependenciesWS monographDependenciesWS;

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
     * Gets the value of the directionForUse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirectionForUse() {
        return directionForUse;
    }

    /**
     * Sets the value of the directionForUse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirectionForUse(String value) {
        this.directionForUse = value;
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

}
