
package ca.gc.hc.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for homeopathicMethodOfPreparationWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="homeopathicMethodOfPreparationWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="homeopathicMethodOfPreparationTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="homeopathicMethodOfPreparationsAsString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maximum" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="minimum" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="quantityAsString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "homeopathicMethodOfPreparationWS", propOrder = {
    "homeopathicMethodOfPreparationTypeCode",
    "homeopathicMethodOfPreparationsAsString",
    "maximum",
    "minimum",
    "quantityAsString"
})
public class HomeopathicMethodOfPreparationWS {

    protected String homeopathicMethodOfPreparationTypeCode;
    protected String homeopathicMethodOfPreparationsAsString;
    protected Integer maximum;
    protected Integer minimum;
    protected String quantityAsString;

    /**
     * Gets the value of the homeopathicMethodOfPreparationTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHomeopathicMethodOfPreparationTypeCode() {
        return homeopathicMethodOfPreparationTypeCode;
    }

    /**
     * Sets the value of the homeopathicMethodOfPreparationTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHomeopathicMethodOfPreparationTypeCode(String value) {
        this.homeopathicMethodOfPreparationTypeCode = value;
    }

    /**
     * Gets the value of the homeopathicMethodOfPreparationsAsString property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHomeopathicMethodOfPreparationsAsString() {
        return homeopathicMethodOfPreparationsAsString;
    }

    /**
     * Sets the value of the homeopathicMethodOfPreparationsAsString property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHomeopathicMethodOfPreparationsAsString(String value) {
        this.homeopathicMethodOfPreparationsAsString = value;
    }

    /**
     * Gets the value of the maximum property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaximum() {
        return maximum;
    }

    /**
     * Sets the value of the maximum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaximum(Integer value) {
        this.maximum = value;
    }

    /**
     * Gets the value of the minimum property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinimum() {
        return minimum;
    }

    /**
     * Sets the value of the minimum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinimum(Integer value) {
        this.minimum = value;
    }

    /**
     * Gets the value of the quantityAsString property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuantityAsString() {
        return quantityAsString;
    }

    /**
     * Sets the value of the quantityAsString property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuantityAsString(String value) {
        this.quantityAsString = value;
    }

}
