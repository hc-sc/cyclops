
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for organismPartWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="organismPartWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="constituents" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nameE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nameF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="organism" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="organismSynonyms" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="partTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="partTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="partTypeNameE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="partTypeNameF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="partTypeSynonyms" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "organismPartWS", propOrder = {
    "constituents",
    "name",
    "nameE",
    "nameF",
    "organism",
    "organismSynonyms",
    "partTypeCode",
    "partTypeName",
    "partTypeNameE",
    "partTypeNameF",
    "partTypeSynonyms"
})
public class OrganismPartWS {

    @XmlElement(nillable = true)
    protected List<String> constituents;
    protected String name;
    protected String nameE;
    protected String nameF;
    protected String organism;
    @XmlElement(nillable = true)
    protected List<String> organismSynonyms;
    protected String partTypeCode;
    protected String partTypeName;
    protected String partTypeNameE;
    protected String partTypeNameF;
    @XmlElement(nillable = true)
    protected List<String> partTypeSynonyms;

    /**
     * Gets the value of the constituents property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the constituents property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConstituents().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getConstituents() {
        if (constituents == null) {
            constituents = new ArrayList<String>();
        }
        return this.constituents;
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
     * Gets the value of the nameE property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameE() {
        return nameE;
    }

    /**
     * Sets the value of the nameE property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameE(String value) {
        this.nameE = value;
    }

    /**
     * Gets the value of the nameF property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameF() {
        return nameF;
    }

    /**
     * Sets the value of the nameF property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameF(String value) {
        this.nameF = value;
    }

    /**
     * Gets the value of the organism property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganism() {
        return organism;
    }

    /**
     * Sets the value of the organism property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganism(String value) {
        this.organism = value;
    }

    /**
     * Gets the value of the organismSynonyms property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the organismSynonyms property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrganismSynonyms().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOrganismSynonyms() {
        if (organismSynonyms == null) {
            organismSynonyms = new ArrayList<String>();
        }
        return this.organismSynonyms;
    }

    /**
     * Gets the value of the partTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartTypeCode() {
        return partTypeCode;
    }

    /**
     * Sets the value of the partTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartTypeCode(String value) {
        this.partTypeCode = value;
    }

    /**
     * Gets the value of the partTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartTypeName() {
        return partTypeName;
    }

    /**
     * Sets the value of the partTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartTypeName(String value) {
        this.partTypeName = value;
    }

    /**
     * Gets the value of the partTypeNameE property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartTypeNameE() {
        return partTypeNameE;
    }

    /**
     * Sets the value of the partTypeNameE property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartTypeNameE(String value) {
        this.partTypeNameE = value;
    }

    /**
     * Gets the value of the partTypeNameF property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartTypeNameF() {
        return partTypeNameF;
    }

    /**
     * Sets the value of the partTypeNameF property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartTypeNameF(String value) {
        this.partTypeNameF = value;
    }

    /**
     * Gets the value of the partTypeSynonyms property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the partTypeSynonyms property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPartTypeSynonyms().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPartTypeSynonyms() {
        if (partTypeSynonyms == null) {
            partTypeSynonyms = new ArrayList<String>();
        }
        return this.partTypeSynonyms;
    }

}
