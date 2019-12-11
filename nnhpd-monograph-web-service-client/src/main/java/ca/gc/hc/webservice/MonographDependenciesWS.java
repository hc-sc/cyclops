
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for monographDependenciesWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="monographDependenciesWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dosageFormGroups" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}dosageFormGroupWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="monographDoseCombination" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographDoseCombinationWS" minOccurs="0"/>
 *         &lt;element name="monographPreparation" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographPreparationWS" minOccurs="0"/>
 *         &lt;element name="monographSourceIngredient" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographSourceIngredientWS" minOccurs="0"/>
 *         &lt;element name="monographSourceOrganismParts" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographSourceOrganismPartWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="monographUses" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographUseWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="otherDependancy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="otherMonographSources" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}textMonographSourceMaterialWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="subPopulation" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}subPopulationWS" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "monographDependenciesWS", propOrder = {
    "dosageFormGroups",
    "monographDoseCombination",
    "monographPreparation",
    "monographSourceIngredient",
    "monographSourceOrganismParts",
    "monographUses",
    "otherDependancy",
    "otherMonographSources",
    "subPopulation"
})
public class MonographDependenciesWS {

    @XmlElement(nillable = true)
    protected List<DosageFormGroupWS> dosageFormGroups;
    protected MonographDoseCombinationWS monographDoseCombination;
    protected MonographPreparationWS monographPreparation;
    protected MonographSourceIngredientWS monographSourceIngredient;
    @XmlElement(nillable = true)
    protected List<MonographSourceOrganismPartWS> monographSourceOrganismParts;
    @XmlElement(nillable = true)
    protected List<MonographUseWS> monographUses;
    protected String otherDependancy;
    @XmlElement(nillable = true)
    protected List<TextMonographSourceMaterialWS> otherMonographSources;
    protected SubPopulationWS subPopulation;

    /**
     * Gets the value of the dosageFormGroups property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dosageFormGroups property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDosageFormGroups().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DosageFormGroupWS }
     * 
     * 
     */
    public List<DosageFormGroupWS> getDosageFormGroups() {
        if (dosageFormGroups == null) {
            dosageFormGroups = new ArrayList<DosageFormGroupWS>();
        }
        return this.dosageFormGroups;
    }

    /**
     * Gets the value of the monographDoseCombination property.
     * 
     * @return
     *     possible object is
     *     {@link MonographDoseCombinationWS }
     *     
     */
    public MonographDoseCombinationWS getMonographDoseCombination() {
        return monographDoseCombination;
    }

    /**
     * Sets the value of the monographDoseCombination property.
     * 
     * @param value
     *     allowed object is
     *     {@link MonographDoseCombinationWS }
     *     
     */
    public void setMonographDoseCombination(MonographDoseCombinationWS value) {
        this.monographDoseCombination = value;
    }

    /**
     * Gets the value of the monographPreparation property.
     * 
     * @return
     *     possible object is
     *     {@link MonographPreparationWS }
     *     
     */
    public MonographPreparationWS getMonographPreparation() {
        return monographPreparation;
    }

    /**
     * Sets the value of the monographPreparation property.
     * 
     * @param value
     *     allowed object is
     *     {@link MonographPreparationWS }
     *     
     */
    public void setMonographPreparation(MonographPreparationWS value) {
        this.monographPreparation = value;
    }

    /**
     * Gets the value of the monographSourceIngredient property.
     * 
     * @return
     *     possible object is
     *     {@link MonographSourceIngredientWS }
     *     
     */
    public MonographSourceIngredientWS getMonographSourceIngredient() {
        return monographSourceIngredient;
    }

    /**
     * Sets the value of the monographSourceIngredient property.
     * 
     * @param value
     *     allowed object is
     *     {@link MonographSourceIngredientWS }
     *     
     */
    public void setMonographSourceIngredient(MonographSourceIngredientWS value) {
        this.monographSourceIngredient = value;
    }

    /**
     * Gets the value of the monographSourceOrganismParts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monographSourceOrganismParts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonographSourceOrganismParts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MonographSourceOrganismPartWS }
     * 
     * 
     */
    public List<MonographSourceOrganismPartWS> getMonographSourceOrganismParts() {
        if (monographSourceOrganismParts == null) {
            monographSourceOrganismParts = new ArrayList<MonographSourceOrganismPartWS>();
        }
        return this.monographSourceOrganismParts;
    }

    /**
     * Gets the value of the monographUses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monographUses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonographUses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MonographUseWS }
     * 
     * 
     */
    public List<MonographUseWS> getMonographUses() {
        if (monographUses == null) {
            monographUses = new ArrayList<MonographUseWS>();
        }
        return this.monographUses;
    }

    /**
     * Gets the value of the otherDependancy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherDependancy() {
        return otherDependancy;
    }

    /**
     * Sets the value of the otherDependancy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherDependancy(String value) {
        this.otherDependancy = value;
    }

    /**
     * Gets the value of the otherMonographSources property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the otherMonographSources property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOtherMonographSources().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TextMonographSourceMaterialWS }
     * 
     * 
     */
    public List<TextMonographSourceMaterialWS> getOtherMonographSources() {
        if (otherMonographSources == null) {
            otherMonographSources = new ArrayList<TextMonographSourceMaterialWS>();
        }
        return this.otherMonographSources;
    }

    /**
     * Gets the value of the subPopulation property.
     * 
     * @return
     *     possible object is
     *     {@link SubPopulationWS }
     *     
     */
    public SubPopulationWS getSubPopulation() {
        return subPopulation;
    }

    /**
     * Sets the value of the subPopulation property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubPopulationWS }
     *     
     */
    public void setSubPopulation(SubPopulationWS value) {
        this.subPopulation = value;
    }

}
