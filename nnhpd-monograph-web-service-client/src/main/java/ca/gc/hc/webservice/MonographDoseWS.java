
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for monographDoseWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="monographDoseWS">
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
 *         &lt;element name="monographDependenciesWS" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographDependenciesWS" minOccurs="0"/>
 *         &lt;element name="monographDirectionsForUse" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographDirectionForUseWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="monographSubIngredientDoseCombinations" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographSubIngredientDoseCombinationWS" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "monographDoseWS", propOrder = {
    "code",
    "doseMaximum",
    "doseMinimum",
    "doseUnits",
    "frequencyMaximum",
    "frequencyMinimum",
    "frequencyUnits",
    "monographDependenciesWS",
    "monographDirectionsForUse",
    "monographSubIngredientDoseCombinations",
    "restrictionType"
})
public class MonographDoseWS {

    protected String code;
    protected Float doseMaximum;
    protected Float doseMinimum;
    protected UnitsWS doseUnits;
    protected int frequencyMaximum;
    protected int frequencyMinimum;
    protected UnitsWS frequencyUnits;
    protected MonographDependenciesWS monographDependenciesWS;
    @XmlElement(nillable = true)
    protected List<MonographDirectionForUseWS> monographDirectionsForUse;
    @XmlElement(nillable = true)
    protected List<MonographSubIngredientDoseCombinationWS> monographSubIngredientDoseCombinations;
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
     * Gets the value of the monographDirectionsForUse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monographDirectionsForUse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonographDirectionsForUse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MonographDirectionForUseWS }
     * 
     * 
     */
    public List<MonographDirectionForUseWS> getMonographDirectionsForUse() {
        if (monographDirectionsForUse == null) {
            monographDirectionsForUse = new ArrayList<MonographDirectionForUseWS>();
        }
        return this.monographDirectionsForUse;
    }

    /**
     * Gets the value of the monographSubIngredientDoseCombinations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monographSubIngredientDoseCombinations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonographSubIngredientDoseCombinations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MonographSubIngredientDoseCombinationWS }
     * 
     * 
     */
    public List<MonographSubIngredientDoseCombinationWS> getMonographSubIngredientDoseCombinations() {
        if (monographSubIngredientDoseCombinations == null) {
            monographSubIngredientDoseCombinations = new ArrayList<MonographSubIngredientDoseCombinationWS>();
        }
        return this.monographSubIngredientDoseCombinations;
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
