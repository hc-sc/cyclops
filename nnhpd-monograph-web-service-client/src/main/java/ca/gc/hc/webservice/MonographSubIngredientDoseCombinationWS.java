
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for monographSubIngredientDoseCombinationWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="monographSubIngredientDoseCombinationWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="monographSubIngredientDoses" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographSubIngredientDoseWS" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "monographSubIngredientDoseCombinationWS", propOrder = {
    "code",
    "id",
    "monographSubIngredientDoses"
})
public class MonographSubIngredientDoseCombinationWS {

    protected String code;
    protected Long id;
    @XmlElement(nillable = true)
    protected List<MonographSubIngredientDoseWS> monographSubIngredientDoses;

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
     * Gets the value of the monographSubIngredientDoses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monographSubIngredientDoses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonographSubIngredientDoses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MonographSubIngredientDoseWS }
     * 
     * 
     */
    public List<MonographSubIngredientDoseWS> getMonographSubIngredientDoses() {
        if (monographSubIngredientDoses == null) {
            monographSubIngredientDoses = new ArrayList<MonographSubIngredientDoseWS>();
        }
        return this.monographSubIngredientDoses;
    }

}
