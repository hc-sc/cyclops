
package ca.gc.hc.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for subPopulationWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="subPopulationWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="maximumAge" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="maximumAgeUnits" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}unitsWS" minOccurs="0"/>
 *         &lt;element name="minimumAge" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="minimumAgeUnits" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}unitsWS" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "subPopulationWS", propOrder = {
    "code",
    "id",
    "maximumAge",
    "maximumAgeUnits",
    "minimumAge",
    "minimumAgeUnits",
    "name"
})
public class SubPopulationWS {

    protected String code;
    protected Long id;
    protected int maximumAge;
    protected UnitsWS maximumAgeUnits;
    protected int minimumAge;
    protected UnitsWS minimumAgeUnits;
    protected String name;

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
     * Gets the value of the maximumAge property.
     * 
     */
    public int getMaximumAge() {
        return maximumAge;
    }

    /**
     * Sets the value of the maximumAge property.
     * 
     */
    public void setMaximumAge(int value) {
        this.maximumAge = value;
    }

    /**
     * Gets the value of the maximumAgeUnits property.
     * 
     * @return
     *     possible object is
     *     {@link UnitsWS }
     *     
     */
    public UnitsWS getMaximumAgeUnits() {
        return maximumAgeUnits;
    }

    /**
     * Sets the value of the maximumAgeUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitsWS }
     *     
     */
    public void setMaximumAgeUnits(UnitsWS value) {
        this.maximumAgeUnits = value;
    }

    /**
     * Gets the value of the minimumAge property.
     * 
     */
    public int getMinimumAge() {
        return minimumAge;
    }

    /**
     * Sets the value of the minimumAge property.
     * 
     */
    public void setMinimumAge(int value) {
        this.minimumAge = value;
    }

    /**
     * Gets the value of the minimumAgeUnits property.
     * 
     * @return
     *     possible object is
     *     {@link UnitsWS }
     *     
     */
    public UnitsWS getMinimumAgeUnits() {
        return minimumAgeUnits;
    }

    /**
     * Sets the value of the minimumAgeUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitsWS }
     *     
     */
    public void setMinimumAgeUnits(UnitsWS value) {
        this.minimumAgeUnits = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
