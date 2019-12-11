
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for preparationTypeWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="preparationTypeWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="extract" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="preparationTypeGroupCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ratioType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="restrictedSolvents" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}solventWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="standardized" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "preparationTypeWS", propOrder = {
    "code",
    "extract",
    "name",
    "preparationTypeGroupCode",
    "ratioType",
    "restrictedSolvents",
    "standardized"
})
public class PreparationTypeWS {

    protected String code;
    protected String extract;
    protected String name;
    protected String preparationTypeGroupCode;
    protected String ratioType;
    @XmlElement(nillable = true)
    protected List<SolventWS> restrictedSolvents;
    protected String standardized;

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
     * Gets the value of the extract property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtract() {
        return extract;
    }

    /**
     * Sets the value of the extract property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtract(String value) {
        this.extract = value;
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
     * Gets the value of the preparationTypeGroupCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreparationTypeGroupCode() {
        return preparationTypeGroupCode;
    }

    /**
     * Sets the value of the preparationTypeGroupCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreparationTypeGroupCode(String value) {
        this.preparationTypeGroupCode = value;
    }

    /**
     * Gets the value of the ratioType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatioType() {
        return ratioType;
    }

    /**
     * Sets the value of the ratioType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatioType(String value) {
        this.ratioType = value;
    }

    /**
     * Gets the value of the restrictedSolvents property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the restrictedSolvents property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRestrictedSolvents().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SolventWS }
     * 
     * 
     */
    public List<SolventWS> getRestrictedSolvents() {
        if (restrictedSolvents == null) {
            restrictedSolvents = new ArrayList<SolventWS>();
        }
        return this.restrictedSolvents;
    }

    /**
     * Gets the value of the standardized property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStandardized() {
        return standardized;
    }

    /**
     * Sets the value of the standardized property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStandardized(String value) {
        this.standardized = value;
    }

}
