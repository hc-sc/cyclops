
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dosageFormWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dosageFormWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="allowIngredientUnits" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="assessmentRequired" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dosageFormCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dosageUnits" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}dosageUnitsWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="synonyms" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dosageFormWS", propOrder = {
    "allowIngredientUnits",
    "assessmentRequired",
    "dosageFormCode",
    "dosageUnits",
    "name",
    "synonyms"
})
public class DosageFormWS {

    protected String allowIngredientUnits;
    protected String assessmentRequired;
    protected String dosageFormCode;
    @XmlElement(nillable = true)
    protected List<DosageUnitsWS> dosageUnits;
    protected String name;
    @XmlElement(nillable = true)
    protected List<String> synonyms;

    /**
     * Gets the value of the allowIngredientUnits property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllowIngredientUnits() {
        return allowIngredientUnits;
    }

    /**
     * Sets the value of the allowIngredientUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllowIngredientUnits(String value) {
        this.allowIngredientUnits = value;
    }

    /**
     * Gets the value of the assessmentRequired property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssessmentRequired() {
        return assessmentRequired;
    }

    /**
     * Sets the value of the assessmentRequired property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssessmentRequired(String value) {
        this.assessmentRequired = value;
    }

    /**
     * Gets the value of the dosageFormCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDosageFormCode() {
        return dosageFormCode;
    }

    /**
     * Sets the value of the dosageFormCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDosageFormCode(String value) {
        this.dosageFormCode = value;
    }

    /**
     * Gets the value of the dosageUnits property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dosageUnits property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDosageUnits().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DosageUnitsWS }
     * 
     * 
     */
    public List<DosageUnitsWS> getDosageUnits() {
        if (dosageUnits == null) {
            dosageUnits = new ArrayList<DosageUnitsWS>();
        }
        return this.dosageUnits;
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
     * Gets the value of the synonyms property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the synonyms property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSynonyms().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSynonyms() {
        if (synonyms == null) {
            synonyms = new ArrayList<String>();
        }
        return this.synonyms;
    }

}
