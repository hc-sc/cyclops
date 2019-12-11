
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bilingualIngredientWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bilingualIngredientWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authorizedName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="authorizedNameE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="authorizedNameF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="commonNames" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}qualifiedSynonymWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="nhpClassifications" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}nhpClassificationWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="properNames" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}qualifiedSynonymWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bilingualIngredientWS", propOrder = {
    "authorizedName",
    "authorizedNameE",
    "authorizedNameF",
    "commonNames",
    "id",
    "nhpClassifications",
    "properNames",
    "type"
})
@XmlSeeAlso({
    BilingualDefinedOrganismSubstanceWS.class,
    BilingualChemicalSubstanceWS.class,
    BilingualHomeopathicSubstanceWS.class
})
public class BilingualIngredientWS {

    protected String authorizedName;
    protected String authorizedNameE;
    protected String authorizedNameF;
    @XmlElement(nillable = true)
    protected List<QualifiedSynonymWS> commonNames;
    protected Long id;
    @XmlElement(nillable = true)
    protected List<NhpClassificationWS> nhpClassifications;
    @XmlElement(nillable = true)
    protected List<QualifiedSynonymWS> properNames;
    protected String type;

    /**
     * Gets the value of the authorizedName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizedName() {
        return authorizedName;
    }

    /**
     * Sets the value of the authorizedName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizedName(String value) {
        this.authorizedName = value;
    }

    /**
     * Gets the value of the authorizedNameE property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizedNameE() {
        return authorizedNameE;
    }

    /**
     * Sets the value of the authorizedNameE property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizedNameE(String value) {
        this.authorizedNameE = value;
    }

    /**
     * Gets the value of the authorizedNameF property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizedNameF() {
        return authorizedNameF;
    }

    /**
     * Sets the value of the authorizedNameF property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizedNameF(String value) {
        this.authorizedNameF = value;
    }

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
     * {@link QualifiedSynonymWS }
     * 
     * 
     */
    public List<QualifiedSynonymWS> getCommonNames() {
        if (commonNames == null) {
            commonNames = new ArrayList<QualifiedSynonymWS>();
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
     * Gets the value of the nhpClassifications property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nhpClassifications property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNhpClassifications().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NhpClassificationWS }
     * 
     * 
     */
    public List<NhpClassificationWS> getNhpClassifications() {
        if (nhpClassifications == null) {
            nhpClassifications = new ArrayList<NhpClassificationWS>();
        }
        return this.nhpClassifications;
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
     * {@link QualifiedSynonymWS }
     * 
     * 
     */
    public List<QualifiedSynonymWS> getProperNames() {
        if (properNames == null) {
            properNames = new ArrayList<QualifiedSynonymWS>();
        }
        return this.properNames;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
