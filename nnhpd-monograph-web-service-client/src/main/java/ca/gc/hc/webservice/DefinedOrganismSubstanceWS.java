
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for definedOrganismSubstanceWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="definedOrganismSubstanceWS">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ingredient.webservice.nhpd.hc.gc.ca/}ingredientWS">
 *       &lt;sequence>
 *         &lt;element name="allOrganismParts" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}organismPartWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="isAnimalTissue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isWholeOrganism" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="organismGroupType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="organismParts" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}organismPartWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="organismTypeCodes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="organismTypeGroup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="organismTypes" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}organismTypeWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="partPreparations" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}preparationTypeWS" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "definedOrganismSubstanceWS", propOrder = {
    "allOrganismParts",
    "isAnimalTissue",
    "isWholeOrganism",
    "organismGroupType",
    "organismParts",
    "organismTypeCodes",
    "organismTypeGroup",
    "organismTypes",
    "partPreparations"
})
public class DefinedOrganismSubstanceWS
    extends IngredientWS
{

    @XmlElement(nillable = true)
    protected List<OrganismPartWS> allOrganismParts;
    protected String isAnimalTissue;
    protected Boolean isWholeOrganism;
    protected String organismGroupType;
    @XmlElement(nillable = true)
    protected List<OrganismPartWS> organismParts;
    @XmlElement(nillable = true)
    protected List<String> organismTypeCodes;
    protected String organismTypeGroup;
    @XmlElement(nillable = true)
    protected List<OrganismTypeWS> organismTypes;
    @XmlElement(nillable = true)
    protected List<PreparationTypeWS> partPreparations;

    /**
     * Gets the value of the allOrganismParts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allOrganismParts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllOrganismParts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrganismPartWS }
     * 
     * 
     */
    public List<OrganismPartWS> getAllOrganismParts() {
        if (allOrganismParts == null) {
            allOrganismParts = new ArrayList<OrganismPartWS>();
        }
        return this.allOrganismParts;
    }

    /**
     * Gets the value of the isAnimalTissue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsAnimalTissue() {
        return isAnimalTissue;
    }

    /**
     * Sets the value of the isAnimalTissue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsAnimalTissue(String value) {
        this.isAnimalTissue = value;
    }

    /**
     * Gets the value of the isWholeOrganism property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsWholeOrganism() {
        return isWholeOrganism;
    }

    /**
     * Sets the value of the isWholeOrganism property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsWholeOrganism(Boolean value) {
        this.isWholeOrganism = value;
    }

    /**
     * Gets the value of the organismGroupType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganismGroupType() {
        return organismGroupType;
    }

    /**
     * Sets the value of the organismGroupType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganismGroupType(String value) {
        this.organismGroupType = value;
    }

    /**
     * Gets the value of the organismParts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the organismParts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrganismParts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrganismPartWS }
     * 
     * 
     */
    public List<OrganismPartWS> getOrganismParts() {
        if (organismParts == null) {
            organismParts = new ArrayList<OrganismPartWS>();
        }
        return this.organismParts;
    }

    /**
     * Gets the value of the organismTypeCodes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the organismTypeCodes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrganismTypeCodes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOrganismTypeCodes() {
        if (organismTypeCodes == null) {
            organismTypeCodes = new ArrayList<String>();
        }
        return this.organismTypeCodes;
    }

    /**
     * Gets the value of the organismTypeGroup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganismTypeGroup() {
        return organismTypeGroup;
    }

    /**
     * Sets the value of the organismTypeGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganismTypeGroup(String value) {
        this.organismTypeGroup = value;
    }

    /**
     * Gets the value of the organismTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the organismTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrganismTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrganismTypeWS }
     * 
     * 
     */
    public List<OrganismTypeWS> getOrganismTypes() {
        if (organismTypes == null) {
            organismTypes = new ArrayList<OrganismTypeWS>();
        }
        return this.organismTypes;
    }

    /**
     * Gets the value of the partPreparations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the partPreparations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPartPreparations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PreparationTypeWS }
     * 
     * 
     */
    public List<PreparationTypeWS> getPartPreparations() {
        if (partPreparations == null) {
            partPreparations = new ArrayList<PreparationTypeWS>();
        }
        return this.partPreparations;
    }

}
