
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ingredientWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ingredientWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authorizedName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="className" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="commonNames" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="nhpClassifications" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}nhpClassificationWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="nonMedicinalRole" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}nonMedicinalRoleWS" minOccurs="0"/>
 *         &lt;element name="nonMedicinalRoles" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}nonMedicinalRoleWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="properNames" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="roles" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="scientificNames" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "ingredientWS", propOrder = {
    "authorizedName",
    "className",
    "commonNames",
    "id",
    "nhpClassifications",
    "nonMedicinalRole",
    "nonMedicinalRoles",
    "properNames",
    "roles",
    "scientificNames",
    "type"
})
@XmlSeeAlso({
    ChemicalSubstanceWS.class,
    CustomOrganismSubstanceWS.class,
    DefinedOrganismSubstanceWS.class,
    HomeopathicSubstanceWS.class
})
public class IngredientWS {

    protected String authorizedName;
    protected String className;
    @XmlElement(nillable = true)
    protected List<String> commonNames;
    protected Long id;
    @XmlElement(nillable = true)
    protected List<NhpClassificationWS> nhpClassifications;
    protected NonMedicinalRoleWS nonMedicinalRole;
    @XmlElement(nillable = true)
    protected List<NonMedicinalRoleWS> nonMedicinalRoles;
    @XmlElement(nillable = true)
    protected List<String> properNames;
    @XmlElement(nillable = true)
    protected List<String> roles;
    @XmlElement(nillable = true)
    protected List<String> scientificNames;
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
     * Gets the value of the className property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets the value of the className property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassName(String value) {
        this.className = value;
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
     * Gets the value of the nonMedicinalRole property.
     * 
     * @return
     *     possible object is
     *     {@link NonMedicinalRoleWS }
     *     
     */
    public NonMedicinalRoleWS getNonMedicinalRole() {
        return nonMedicinalRole;
    }

    /**
     * Sets the value of the nonMedicinalRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link NonMedicinalRoleWS }
     *     
     */
    public void setNonMedicinalRole(NonMedicinalRoleWS value) {
        this.nonMedicinalRole = value;
    }

    /**
     * Gets the value of the nonMedicinalRoles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nonMedicinalRoles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNonMedicinalRoles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NonMedicinalRoleWS }
     * 
     * 
     */
    public List<NonMedicinalRoleWS> getNonMedicinalRoles() {
        if (nonMedicinalRoles == null) {
            nonMedicinalRoles = new ArrayList<NonMedicinalRoleWS>();
        }
        return this.nonMedicinalRoles;
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
     * Gets the value of the roles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the roles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRoles() {
        if (roles == null) {
            roles = new ArrayList<String>();
        }
        return this.roles;
    }

    /**
     * Gets the value of the scientificNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the scientificNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScientificNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getScientificNames() {
        if (scientificNames == null) {
            scientificNames = new ArrayList<String>();
        }
        return this.scientificNames;
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
