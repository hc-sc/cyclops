
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for monographWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="monographWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="commonNames" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="ingredientWS" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}ingredientWS" minOccurs="0"/>
 *         &lt;element name="monographDurations" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographDurationWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="monographEntries" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographEntryWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="monographPhases" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographPhaseWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="monographRiskStatements" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographRiskStatementWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="monographRouteOfAdmin" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographRouteOfAdministrationWS" minOccurs="0"/>
 *         &lt;element name="monographSourceMaterials" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="monographUses" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographUseWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="properNames" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="publishedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="subPopulations" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}subPopulationWS" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "monographWS", propOrder = {
    "commonNames",
    "id",
    "ingredientWS",
    "monographDurations",
    "monographEntries",
    "monographPhases",
    "monographRiskStatements",
    "monographRouteOfAdmin",
    "monographSourceMaterials",
    "monographUses",
    "name",
    "properNames",
    "publishedDate",
    "subPopulations"
})
public class MonographWS {

    @XmlElement(nillable = true)
    protected List<String> commonNames;
    protected Long id;
    protected IngredientWS ingredientWS;
    @XmlElement(nillable = true)
    protected List<MonographDurationWS> monographDurations;
    @XmlElement(nillable = true)
    protected List<MonographEntryWS> monographEntries;
    @XmlElement(nillable = true)
    protected List<MonographPhaseWS> monographPhases;
    @XmlElement(nillable = true)
    protected List<MonographRiskStatementWS> monographRiskStatements;
    protected MonographRouteOfAdministrationWS monographRouteOfAdmin;
    @XmlElement(nillable = true)
    protected List<String> monographSourceMaterials;
    @XmlElement(nillable = true)
    protected List<MonographUseWS> monographUses;
    protected String name;
    @XmlElement(nillable = true)
    protected List<String> properNames;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar publishedDate;
    @XmlElement(nillable = true)
    protected List<SubPopulationWS> subPopulations;

    /**
     * Gets the value of the commonNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the commonNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommonNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCommonNames() {
        if (commonNames == null) {
            commonNames = new ArrayList<String>();
        }
        return this.commonNames;
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
     * Gets the value of the monographDurations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monographDurations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonographDurations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MonographDurationWS }
     * 
     * 
     */
    public List<MonographDurationWS> getMonographDurations() {
        if (monographDurations == null) {
            monographDurations = new ArrayList<MonographDurationWS>();
        }
        return this.monographDurations;
    }

    /**
     * Gets the value of the monographEntries property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monographEntries property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonographEntries().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MonographEntryWS }
     * 
     * 
     */
    public List<MonographEntryWS> getMonographEntries() {
        if (monographEntries == null) {
            monographEntries = new ArrayList<MonographEntryWS>();
        }
        return this.monographEntries;
    }

    /**
     * Gets the value of the monographPhases property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monographPhases property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonographPhases().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MonographPhaseWS }
     * 
     * 
     */
    public List<MonographPhaseWS> getMonographPhases() {
        if (monographPhases == null) {
            monographPhases = new ArrayList<MonographPhaseWS>();
        }
        return this.monographPhases;
    }

    /**
     * Gets the value of the monographRiskStatements property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monographRiskStatements property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonographRiskStatements().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MonographRiskStatementWS }
     * 
     * 
     */
    public List<MonographRiskStatementWS> getMonographRiskStatements() {
        if (monographRiskStatements == null) {
            monographRiskStatements = new ArrayList<MonographRiskStatementWS>();
        }
        return this.monographRiskStatements;
    }

    /**
     * Gets the value of the monographRouteOfAdmin property.
     * 
     * @return
     *     possible object is
     *     {@link MonographRouteOfAdministrationWS }
     *     
     */
    public MonographRouteOfAdministrationWS getMonographRouteOfAdmin() {
        return monographRouteOfAdmin;
    }

    /**
     * Sets the value of the monographRouteOfAdmin property.
     * 
     * @param value
     *     allowed object is
     *     {@link MonographRouteOfAdministrationWS }
     *     
     */
    public void setMonographRouteOfAdmin(MonographRouteOfAdministrationWS value) {
        this.monographRouteOfAdmin = value;
    }

    /**
     * Gets the value of the monographSourceMaterials property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monographSourceMaterials property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonographSourceMaterials().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMonographSourceMaterials() {
        if (monographSourceMaterials == null) {
            monographSourceMaterials = new ArrayList<String>();
        }
        return this.monographSourceMaterials;
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
     * Gets the value of the properNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the properNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProperNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getProperNames() {
        if (properNames == null) {
            properNames = new ArrayList<String>();
        }
        return this.properNames;
    }

    /**
     * Gets the value of the publishedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPublishedDate() {
        return publishedDate;
    }

    /**
     * Sets the value of the publishedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPublishedDate(XMLGregorianCalendar value) {
        this.publishedDate = value;
    }

    /**
     * Gets the value of the subPopulations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subPopulations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubPopulations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubPopulationWS }
     * 
     * 
     */
    public List<SubPopulationWS> getSubPopulations() {
        if (subPopulations == null) {
            subPopulations = new ArrayList<SubPopulationWS>();
        }
        return this.subPopulations;
    }

}
