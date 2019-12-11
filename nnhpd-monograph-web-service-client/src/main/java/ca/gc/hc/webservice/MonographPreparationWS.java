
package ca.gc.hc.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for monographPreparationWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="monographPreparationWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maximumExtractionRatio" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="minimumExtractionRatio" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="preparationType" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}preparationTypeWS" minOccurs="0"/>
 *         &lt;element name="preparationTypeGroup" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}preparationTypeGroupWS" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "monographPreparationWS", propOrder = {
    "code",
    "maximumExtractionRatio",
    "minimumExtractionRatio",
    "name",
    "preparationType",
    "preparationTypeGroup"
})
public class MonographPreparationWS {

    protected String code;
    protected Float maximumExtractionRatio;
    protected Float minimumExtractionRatio;
    protected String name;
    protected PreparationTypeWS preparationType;
    protected PreparationTypeGroupWS preparationTypeGroup;

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
     * Gets the value of the maximumExtractionRatio property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getMaximumExtractionRatio() {
        return maximumExtractionRatio;
    }

    /**
     * Sets the value of the maximumExtractionRatio property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setMaximumExtractionRatio(Float value) {
        this.maximumExtractionRatio = value;
    }

    /**
     * Gets the value of the minimumExtractionRatio property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getMinimumExtractionRatio() {
        return minimumExtractionRatio;
    }

    /**
     * Sets the value of the minimumExtractionRatio property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setMinimumExtractionRatio(Float value) {
        this.minimumExtractionRatio = value;
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

    /**
     * Gets the value of the preparationType property.
     * 
     * @return
     *     possible object is
     *     {@link PreparationTypeWS }
     *     
     */
    public PreparationTypeWS getPreparationType() {
        return preparationType;
    }

    /**
     * Sets the value of the preparationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PreparationTypeWS }
     *     
     */
    public void setPreparationType(PreparationTypeWS value) {
        this.preparationType = value;
    }

    /**
     * Gets the value of the preparationTypeGroup property.
     * 
     * @return
     *     possible object is
     *     {@link PreparationTypeGroupWS }
     *     
     */
    public PreparationTypeGroupWS getPreparationTypeGroup() {
        return preparationTypeGroup;
    }

    /**
     * Sets the value of the preparationTypeGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link PreparationTypeGroupWS }
     *     
     */
    public void setPreparationTypeGroup(PreparationTypeGroupWS value) {
        this.preparationTypeGroup = value;
    }

}
