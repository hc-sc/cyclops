
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for chemicalSubstanceWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="chemicalSubstanceWS">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ingredient.webservice.nhpd.hc.gc.ca/}ingredientWS">
 *       &lt;sequence>
 *         &lt;element name="relatedSourceIngredientNames" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="relatedSourceIngredients" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}relatedSourceIngredientWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sourceOrganismParts" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}sourceOrganismPartWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="subIngredients" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}subIngredientWS" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "chemicalSubstanceWS", propOrder = {
    "relatedSourceIngredientNames",
    "relatedSourceIngredients",
    "sourceOrganismParts",
    "subIngredients"
})
public class ChemicalSubstanceWS
    extends IngredientWS
{

    protected String relatedSourceIngredientNames;
    @XmlElement(nillable = true)
    protected List<RelatedSourceIngredientWS> relatedSourceIngredients;
    @XmlElement(nillable = true)
    protected List<SourceOrganismPartWS> sourceOrganismParts;
    @XmlElement(nillable = true)
    protected List<SubIngredientWS> subIngredients;

    /**
     * Gets the value of the relatedSourceIngredientNames property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelatedSourceIngredientNames() {
        return relatedSourceIngredientNames;
    }

    /**
     * Sets the value of the relatedSourceIngredientNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelatedSourceIngredientNames(String value) {
        this.relatedSourceIngredientNames = value;
    }

    /**
     * Gets the value of the relatedSourceIngredients property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relatedSourceIngredients property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelatedSourceIngredients().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelatedSourceIngredientWS }
     * 
     * 
     */
    public List<RelatedSourceIngredientWS> getRelatedSourceIngredients() {
        if (relatedSourceIngredients == null) {
            relatedSourceIngredients = new ArrayList<RelatedSourceIngredientWS>();
        }
        return this.relatedSourceIngredients;
    }

    /**
     * Gets the value of the sourceOrganismParts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sourceOrganismParts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSourceOrganismParts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SourceOrganismPartWS }
     * 
     * 
     */
    public List<SourceOrganismPartWS> getSourceOrganismParts() {
        if (sourceOrganismParts == null) {
            sourceOrganismParts = new ArrayList<SourceOrganismPartWS>();
        }
        return this.sourceOrganismParts;
    }

    /**
     * Gets the value of the subIngredients property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subIngredients property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubIngredients().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubIngredientWS }
     * 
     * 
     */
    public List<SubIngredientWS> getSubIngredients() {
        if (subIngredients == null) {
            subIngredients = new ArrayList<SubIngredientWS>();
        }
        return this.subIngredients;
    }

}
