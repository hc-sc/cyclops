
package ca.gc.hc.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for monographSubIngredientDoseWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="monographSubIngredientDoseWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="doseMaximum" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="doseMinimum" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="doseUnits" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}unitsWS" minOccurs="0"/>
 *         &lt;element name="frequencyMaximum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="frequencyMinimum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="frequencyUnits" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}unitsWS" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="monographSubIngredientCombination" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographSubIngredientCombinationWS" minOccurs="0"/>
 *         &lt;element name="restrictionType" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}restrictionTypeWS" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "monographSubIngredientDoseWS", propOrder = {
    "code",
    "doseMaximum",
    "doseMinimum",
    "doseUnits",
    "frequencyMaximum",
    "frequencyMinimum",
    "frequencyUnits",
    "id",
    "monographSubIngredientCombination",
    "restrictionType"
})
public class MonographSubIngredientDoseWS {

    protected String code;
    protected Float doseMaximum;
    protected Float doseMinimum;
    protected UnitsWS doseUnits;
    protected int frequencyMaximum;
    protected int frequencyMinimum;
    protected UnitsWS frequencyUnits;
    protected Long id;
    protected MonographSubIngredientCombinationWS monographSubIngredientCombination;
    protected RestrictionTypeWS restrictionType;

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
     * Gets the value of the doseMaximum property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getDoseMaximum() {
        return doseMaximum;
    }

    /**
     * Sets the value of the doseMaximum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setDoseMaximum(Float value) {
        this.doseMaximum = value;
    }

    /**
     * Gets the value of the doseMinimum property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getDoseMinimum() {
        return doseMinimum;
    }

    /**
     * Sets the value of the doseMinimum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setDoseMinimum(Float value) {
        this.doseMinimum = value;
    }

    /**
     * Gets the value of the doseUnits property.
     * 
     * @return
     *     possible object is
     *     {@link UnitsWS }
     *     
     */
    public UnitsWS getDoseUnits() {
        return doseUnits;
    }

    /**
     * Sets the value of the doseUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitsWS }
     *     
     */
    public void setDoseUnits(UnitsWS value) {
        this.doseUnits = value;
    }

    /**
     * Gets the value of the frequencyMaximum property.
     * 
     */
    public int getFrequencyMaximum() {
        return frequencyMaximum;
    }

    /**
     * Sets the value of the frequencyMaximum property.
     * 
     */
    public void setFrequencyMaximum(int value) {
        this.frequencyMaximum = value;
    }

    /**
     * Gets the value of the frequencyMinimum property.
     * 
     */
    public int getFrequencyMinimum() {
        return frequencyMinimum;
    }

    /**
     * Sets the value of the frequencyMinimum property.
     * 
     */
    public void setFrequencyMinimum(int value) {
        this.frequencyMinimum = value;
    }

    /**
     * Gets the value of the frequencyUnits property.
     * 
     * @return
     *     possible object is
     *     {@link UnitsWS }
     *     
     */
    public UnitsWS getFrequencyUnits() {
        return frequencyUnits;
    }

    /**
     * Sets the value of the frequencyUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitsWS }
     *     
     */
    public void setFrequencyUnits(UnitsWS value) {
        this.frequencyUnits = value;
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
     * Gets the value of the monographSubIngredientCombination property.
     * 
     * @return
     *     possible object is
     *     {@link MonographSubIngredientCombinationWS }
     *     
     */
    public MonographSubIngredientCombinationWS getMonographSubIngredientCombination() {
        return monographSubIngredientCombination;
    }

    /**
     * Sets the value of the monographSubIngredientCombination property.
     * 
     * @param value
     *     allowed object is
     *     {@link MonographSubIngredientCombinationWS }
     *     
     */
    public void setMonographSubIngredientCombination(MonographSubIngredientCombinationWS value) {
        this.monographSubIngredientCombination = value;
    }

    /**
     * Gets the value of the restrictionType property.
     * 
     * @return
     *     possible object is
     *     {@link RestrictionTypeWS }
     *     
     */
    public RestrictionTypeWS getRestrictionType() {
        return restrictionType;
    }

    /**
     * Sets the value of the restrictionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link RestrictionTypeWS }
     *     
     */
    public void setRestrictionType(RestrictionTypeWS value) {
        this.restrictionType = value;
    }

}
