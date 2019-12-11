
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for monographSearchResultWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="monographSearchResultWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="commonNames" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="monographName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="primaryIngredientName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "monographSearchResultWS", propOrder = {
    "commonNames",
    "id",
    "monographName",
    "primaryIngredientName"
})
public class MonographSearchResultWS {

    @XmlElement(nillable = true)
    protected List<String> commonNames;
    protected Long id;
    protected String monographName;
    protected String primaryIngredientName;

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
     * Gets the value of the monographName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMonographName() {
        return monographName;
    }

    /**
     * Sets the value of the monographName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonographName(String value) {
        this.monographName = value;
    }

    /**
     * Gets the value of the primaryIngredientName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryIngredientName() {
        return primaryIngredientName;
    }

    /**
     * Sets the value of the primaryIngredientName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryIngredientName(String value) {
        this.primaryIngredientName = value;
    }

}
