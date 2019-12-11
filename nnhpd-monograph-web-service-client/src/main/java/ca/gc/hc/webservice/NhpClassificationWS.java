
package ca.gc.hc.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for nhpClassificationWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="nhpClassificationWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descriptionE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descriptionF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nameE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nameF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nhpClassificationWS", propOrder = {
    "code",
    "description",
    "descriptionE",
    "descriptionF",
    "name",
    "nameE",
    "nameF"
})
public class NhpClassificationWS {

    protected String code;
    protected String description;
    protected String descriptionE;
    protected String descriptionF;
    protected String name;
    protected String nameE;
    protected String nameF;

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
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the descriptionE property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescriptionE() {
        return descriptionE;
    }

    /**
     * Sets the value of the descriptionE property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescriptionE(String value) {
        this.descriptionE = value;
    }

    /**
     * Gets the value of the descriptionF property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescriptionF() {
        return descriptionF;
    }

    /**
     * Sets the value of the descriptionF property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescriptionF(String value) {
        this.descriptionF = value;
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

}
