
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for homeopathicSubstanceWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="homeopathicSubstanceWS">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ingredient.webservice.nhpd.hc.gc.ca/}ingredientWS">
 *       &lt;sequence>
 *         &lt;element name="atfRequired" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="atfRequiredInProcessing" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="homeopathicDilutionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="homeopathicMethodOfPreparations" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}homeopathicMethodOfPreparationWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="knownAsSynthetic" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="notes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenceCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sourceMaterials" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="toxic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "homeopathicSubstanceWS", propOrder = {
    "atfRequired",
    "atfRequiredInProcessing",
    "homeopathicDilutionCode",
    "homeopathicMethodOfPreparations",
    "knownAsSynthetic",
    "notes",
    "referenceCode",
    "referenceName",
    "sourceMaterials",
    "toxic"
})
public class HomeopathicSubstanceWS
    extends IngredientWS
{

    protected Boolean atfRequired;
    protected Boolean atfRequiredInProcessing;
    protected String homeopathicDilutionCode;
    @XmlElement(nillable = true)
    protected List<HomeopathicMethodOfPreparationWS> homeopathicMethodOfPreparations;
    protected Boolean knownAsSynthetic;
    protected String notes;
    protected String referenceCode;
    protected String referenceName;
    @XmlElement(nillable = true)
    protected List<String> sourceMaterials;
    protected String toxic;

    /**
     * Gets the value of the atfRequired property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAtfRequired() {
        return atfRequired;
    }

    /**
     * Sets the value of the atfRequired property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAtfRequired(Boolean value) {
        this.atfRequired = value;
    }

    /**
     * Gets the value of the atfRequiredInProcessing property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAtfRequiredInProcessing() {
        return atfRequiredInProcessing;
    }

    /**
     * Sets the value of the atfRequiredInProcessing property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAtfRequiredInProcessing(Boolean value) {
        this.atfRequiredInProcessing = value;
    }

    /**
     * Gets the value of the homeopathicDilutionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHomeopathicDilutionCode() {
        return homeopathicDilutionCode;
    }

    /**
     * Sets the value of the homeopathicDilutionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHomeopathicDilutionCode(String value) {
        this.homeopathicDilutionCode = value;
    }

    /**
     * Gets the value of the homeopathicMethodOfPreparations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the homeopathicMethodOfPreparations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHomeopathicMethodOfPreparations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HomeopathicMethodOfPreparationWS }
     * 
     * 
     */
    public List<HomeopathicMethodOfPreparationWS> getHomeopathicMethodOfPreparations() {
        if (homeopathicMethodOfPreparations == null) {
            homeopathicMethodOfPreparations = new ArrayList<HomeopathicMethodOfPreparationWS>();
        }
        return this.homeopathicMethodOfPreparations;
    }

    /**
     * Gets the value of the knownAsSynthetic property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isKnownAsSynthetic() {
        return knownAsSynthetic;
    }

    /**
     * Sets the value of the knownAsSynthetic property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setKnownAsSynthetic(Boolean value) {
        this.knownAsSynthetic = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotes(String value) {
        this.notes = value;
    }

    /**
     * Gets the value of the referenceCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceCode() {
        return referenceCode;
    }

    /**
     * Sets the value of the referenceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceCode(String value) {
        this.referenceCode = value;
    }

    /**
     * Gets the value of the referenceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceName() {
        return referenceName;
    }

    /**
     * Sets the value of the referenceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceName(String value) {
        this.referenceName = value;
    }

    /**
     * Gets the value of the sourceMaterials property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sourceMaterials property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSourceMaterials().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSourceMaterials() {
        if (sourceMaterials == null) {
            sourceMaterials = new ArrayList<String>();
        }
        return this.sourceMaterials;
    }

    /**
     * Gets the value of the toxic property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToxic() {
        return toxic;
    }

    /**
     * Sets the value of the toxic property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToxic(String value) {
        this.toxic = value;
    }

}
