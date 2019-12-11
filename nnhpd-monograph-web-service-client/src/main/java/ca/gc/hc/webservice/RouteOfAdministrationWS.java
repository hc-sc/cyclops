
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for routeOfAdministrationWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="routeOfAdministrationWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="noFixedLimits" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="possibleDosageForms" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}dosageFormWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="roaCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="roaName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sterileRequired" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "routeOfAdministrationWS", propOrder = {
    "noFixedLimits",
    "possibleDosageForms",
    "roaCode",
    "roaName",
    "sterileRequired"
})
public class RouteOfAdministrationWS {

    protected String noFixedLimits;
    @XmlElement(nillable = true)
    protected List<DosageFormWS> possibleDosageForms;
    protected String roaCode;
    protected String roaName;
    protected String sterileRequired;

    /**
     * Gets the value of the noFixedLimits property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoFixedLimits() {
        return noFixedLimits;
    }

    /**
     * Sets the value of the noFixedLimits property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoFixedLimits(String value) {
        this.noFixedLimits = value;
    }

    /**
     * Gets the value of the possibleDosageForms property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the possibleDosageForms property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPossibleDosageForms().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DosageFormWS }
     * 
     * 
     */
    public List<DosageFormWS> getPossibleDosageForms() {
        if (possibleDosageForms == null) {
            possibleDosageForms = new ArrayList<DosageFormWS>();
        }
        return this.possibleDosageForms;
    }

    /**
     * Gets the value of the roaCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoaCode() {
        return roaCode;
    }

    /**
     * Sets the value of the roaCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoaCode(String value) {
        this.roaCode = value;
    }

    /**
     * Gets the value of the roaName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoaName() {
        return roaName;
    }

    /**
     * Sets the value of the roaName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoaName(String value) {
        this.roaName = value;
    }

    /**
     * Gets the value of the sterileRequired property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSterileRequired() {
        return sterileRequired;
    }

    /**
     * Sets the value of the sterileRequired property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSterileRequired(String value) {
        this.sterileRequired = value;
    }

}
