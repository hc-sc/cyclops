
package ca.gc.hc.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for monographDurationWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="monographDurationWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="durationStatement" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="maximumDuration" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="minimumDuration" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="monographDependenciesWS" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographDependenciesWS" minOccurs="0"/>
 *         &lt;element name="units" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}unitsWS" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "monographDurationWS", propOrder = {
    "code",
    "durationStatement",
    "id",
    "maximumDuration",
    "minimumDuration",
    "monographDependenciesWS",
    "units"
})
public class MonographDurationWS {

    protected String code;
    protected String durationStatement;
    protected Long id;
    protected int maximumDuration;
    protected int minimumDuration;
    protected MonographDependenciesWS monographDependenciesWS;
    protected UnitsWS units;

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
     * Gets the value of the durationStatement property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDurationStatement() {
        return durationStatement;
    }

    /**
     * Sets the value of the durationStatement property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDurationStatement(String value) {
        this.durationStatement = value;
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
     * Gets the value of the maximumDuration property.
     * 
     */
    public int getMaximumDuration() {
        return maximumDuration;
    }

    /**
     * Sets the value of the maximumDuration property.
     * 
     */
    public void setMaximumDuration(int value) {
        this.maximumDuration = value;
    }

    /**
     * Gets the value of the minimumDuration property.
     * 
     */
    public int getMinimumDuration() {
        return minimumDuration;
    }

    /**
     * Sets the value of the minimumDuration property.
     * 
     */
    public void setMinimumDuration(int value) {
        this.minimumDuration = value;
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
     * Gets the value of the units property.
     * 
     * @return
     *     possible object is
     *     {@link UnitsWS }
     *     
     */
    public UnitsWS getUnits() {
        return units;
    }

    /**
     * Sets the value of the units property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitsWS }
     *     
     */
    public void setUnits(UnitsWS value) {
        this.units = value;
    }

}
