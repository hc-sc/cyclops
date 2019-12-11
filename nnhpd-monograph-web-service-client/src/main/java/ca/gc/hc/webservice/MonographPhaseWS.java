
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for monographPhaseWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="monographPhaseWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="monographDoseCombinations" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographDoseCombinationWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="monographDurations" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographDurationWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="order" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "monographPhaseWS", propOrder = {
    "code",
    "monographDoseCombinations",
    "monographDurations",
    "name",
    "order"
})
public class MonographPhaseWS {

    protected String code;
    @XmlElement(nillable = true)
    protected List<MonographDoseCombinationWS> monographDoseCombinations;
    @XmlElement(nillable = true)
    protected List<MonographDurationWS> monographDurations;
    protected String name;
    protected int order;

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
     * Gets the value of the monographDoseCombinations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monographDoseCombinations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonographDoseCombinations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MonographDoseCombinationWS }
     * 
     * 
     */
    public List<MonographDoseCombinationWS> getMonographDoseCombinations() {
        if (monographDoseCombinations == null) {
            monographDoseCombinations = new ArrayList<MonographDoseCombinationWS>();
        }
        return this.monographDoseCombinations;
    }

    /**
     * Gets the value of the monographDurations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monographDurations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonographDurations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MonographDurationWS }
     * 
     * 
     */
    public List<MonographDurationWS> getMonographDurations() {
        if (monographDurations == null) {
            monographDurations = new ArrayList<MonographDurationWS>();
        }
        return this.monographDurations;
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
     * Gets the value of the order property.
     * 
     */
    public int getOrder() {
        return order;
    }

    /**
     * Sets the value of the order property.
     * 
     */
    public void setOrder(int value) {
        this.order = value;
    }

}
