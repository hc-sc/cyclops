
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for nonMedicinalRestrictionWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="nonMedicinalRestrictionWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="allowedPurposes" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}nonMedicinalPurposeWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="details" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="routeOfAdmin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="safetyConcern" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "nonMedicinalRestrictionWS", propOrder = {
    "allowedPurposes",
    "details",
    "routeOfAdmin",
    "safetyConcern",
    "type"
})
public class NonMedicinalRestrictionWS {

    @XmlElement(nillable = true)
    protected List<NonMedicinalPurposeWS> allowedPurposes;
    protected String details;
    protected String routeOfAdmin;
    protected String safetyConcern;
    protected String type;

    /**
     * Gets the value of the allowedPurposes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allowedPurposes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllowedPurposes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NonMedicinalPurposeWS }
     * 
     * 
     */
    public List<NonMedicinalPurposeWS> getAllowedPurposes() {
        if (allowedPurposes == null) {
            allowedPurposes = new ArrayList<NonMedicinalPurposeWS>();
        }
        return this.allowedPurposes;
    }

    /**
     * Gets the value of the details property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets the value of the details property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetails(String value) {
        this.details = value;
    }

    /**
     * Gets the value of the routeOfAdmin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRouteOfAdmin() {
        return routeOfAdmin;
    }

    /**
     * Sets the value of the routeOfAdmin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRouteOfAdmin(String value) {
        this.routeOfAdmin = value;
    }

    /**
     * Gets the value of the safetyConcern property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSafetyConcern() {
        return safetyConcern;
    }

    /**
     * Sets the value of the safetyConcern property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSafetyConcern(String value) {
        this.safetyConcern = value;
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
