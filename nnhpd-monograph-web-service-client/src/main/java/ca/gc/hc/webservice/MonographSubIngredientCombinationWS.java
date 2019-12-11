
package ca.gc.hc.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for monographSubIngredientCombinationWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="monographSubIngredientCombinationWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="maximumRatio" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="minimumRatio" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="primarySubIngredient" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}subIngredientWS" minOccurs="0"/>
 *         &lt;element name="secondarySubIngredient" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}subIngredientWS" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "monographSubIngredientCombinationWS", propOrder = {
    "code",
    "id",
    "maximumRatio",
    "minimumRatio",
    "primarySubIngredient",
    "secondarySubIngredient"
})
public class MonographSubIngredientCombinationWS {

    protected String code;
    protected Long id;
    protected Float maximumRatio;
    protected Float minimumRatio;
    protected SubIngredientWS primarySubIngredient;
    protected SubIngredientWS secondarySubIngredient;

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
     * Gets the value of the maximumRatio property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getMaximumRatio() {
        return maximumRatio;
    }

    /**
     * Sets the value of the maximumRatio property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setMaximumRatio(Float value) {
        this.maximumRatio = value;
    }

    /**
     * Gets the value of the minimumRatio property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getMinimumRatio() {
        return minimumRatio;
    }

    /**
     * Sets the value of the minimumRatio property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setMinimumRatio(Float value) {
        this.minimumRatio = value;
    }

    /**
     * Gets the value of the primarySubIngredient property.
     * 
     * @return
     *     possible object is
     *     {@link SubIngredientWS }
     *     
     */
    public SubIngredientWS getPrimarySubIngredient() {
        return primarySubIngredient;
    }

    /**
     * Sets the value of the primarySubIngredient property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubIngredientWS }
     *     
     */
    public void setPrimarySubIngredient(SubIngredientWS value) {
        this.primarySubIngredient = value;
    }

    /**
     * Gets the value of the secondarySubIngredient property.
     * 
     * @return
     *     possible object is
     *     {@link SubIngredientWS }
     *     
     */
    public SubIngredientWS getSecondarySubIngredient() {
        return secondarySubIngredient;
    }

    /**
     * Sets the value of the secondarySubIngredient property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubIngredientWS }
     *     
     */
    public void setSecondarySubIngredient(SubIngredientWS value) {
        this.secondarySubIngredient = value;
    }

}
