
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for monographEntryWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="monographEntryWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ingredientWS" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}ingredientWS" minOccurs="0"/>
 *         &lt;element name="monographDoseCombinations" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographDoseCombinationWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="monographSourceOrganismParts" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographSourceOrganismPartWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="otherMonographSources" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}textMonographSourceMaterialWS" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "monographEntryWS", propOrder = {
    "ingredientWS",
    "monographDoseCombinations",
    "monographSourceOrganismParts",
    "otherMonographSources"
})
public class MonographEntryWS {

    protected IngredientWS ingredientWS;
    @XmlElement(nillable = true)
    protected List<MonographDoseCombinationWS> monographDoseCombinations;
    @XmlElement(nillable = true)
    protected List<MonographSourceOrganismPartWS> monographSourceOrganismParts;
    @XmlElement(nillable = true)
    protected List<TextMonographSourceMaterialWS> otherMonographSources;

    /**
     * Gets the value of the ingredientWS property.
     * 
     * @return
     *     possible object is
     *     {@link IngredientWS }
     *     
     */
    public IngredientWS getIngredientWS() {
        return ingredientWS;
    }

    /**
     * Sets the value of the ingredientWS property.
     * 
     * @param value
     *     allowed object is
     *     {@link IngredientWS }
     *     
     */
    public void setIngredientWS(IngredientWS value) {
        this.ingredientWS = value;
    }

    /**
     * Gets the value of the monographDoseCombinations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monographDoseCombinations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonographDoseCombinations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MonographDoseCombinationWS }
     * 
     * 
     */
    public List<MonographDoseCombinationWS> getMonographDoseCombinations() {
        if (monographDoseCombinations == null) {
            monographDoseCombinations = new ArrayList<MonographDoseCombinationWS>();
        }
        return this.monographDoseCombinations;
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

}
