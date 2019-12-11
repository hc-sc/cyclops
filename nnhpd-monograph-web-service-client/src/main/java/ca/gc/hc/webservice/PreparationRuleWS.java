
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for preparationRuleWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="preparationRuleWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="freshDryPresent" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ratio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="restrictedSolvents" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}solventWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="solventPresent" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="standardizedPresent" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "preparationRuleWS", propOrder = {
    "code",
    "freshDryPresent",
    "ratio",
    "restrictedSolvents",
    "solventPresent",
    "standardizedPresent"
})
public class PreparationRuleWS {

    protected String code;
    protected Boolean freshDryPresent;
    protected String ratio;
    @XmlElement(nillable = true)
    protected List<SolventWS> restrictedSolvents;
    protected Boolean solventPresent;
    protected Boolean standardizedPresent;

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
     * Gets the value of the freshDryPresent property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFreshDryPresent() {
        return freshDryPresent;
    }

    /**
     * Sets the value of the freshDryPresent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFreshDryPresent(Boolean value) {
        this.freshDryPresent = value;
    }

    /**
     * Gets the value of the ratio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatio() {
        return ratio;
    }

    /**
     * Sets the value of the ratio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatio(String value) {
        this.ratio = value;
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
     * Gets the value of the solventPresent property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSolventPresent() {
        return solventPresent;
    }

    /**
     * Sets the value of the solventPresent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSolventPresent(Boolean value) {
        this.solventPresent = value;
    }

    /**
     * Gets the value of the standardizedPresent property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isStandardizedPresent() {
        return standardizedPresent;
    }

    /**
     * Sets the value of the standardizedPresent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setStandardizedPresent(Boolean value) {
        this.standardizedPresent = value;
    }

}
