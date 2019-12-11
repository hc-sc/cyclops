
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for monographDoseCombinationWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="monographDoseCombinationWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="monographDependenciesWS" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographDependenciesWS" minOccurs="0"/>
 *         &lt;element name="monographDoses" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographDoseWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="visible" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "monographDoseCombinationWS", propOrder = {
    "code",
    "monographDependenciesWS",
    "monographDoses",
    "visible"
})
public class MonographDoseCombinationWS {

    protected String code;
    protected MonographDependenciesWS monographDependenciesWS;
    @XmlElement(nillable = true)
    protected List<MonographDoseWS> monographDoses;
    protected boolean visible;

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
     * Gets the value of the monographDependenciesWS property.
     * 
     * @return
     *     possible object is
     *     {@link MonographDependenciesWS }
     *     
     */
    public MonographDependenciesWS getMonographDependenciesWS() {
        return monographDependenciesWS;
    }

    /**
     * Sets the value of the monographDependenciesWS property.
     * 
     * @param value
     *     allowed object is
     *     {@link MonographDependenciesWS }
     *     
     */
    public void setMonographDependenciesWS(MonographDependenciesWS value) {
        this.monographDependenciesWS = value;
    }

    /**
     * Gets the value of the monographDoses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monographDoses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonographDoses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MonographDoseWS }
     * 
     * 
     */
    public List<MonographDoseWS> getMonographDoses() {
        if (monographDoses == null) {
            monographDoses = new ArrayList<MonographDoseWS>();
        }
        return this.monographDoses;
    }

    /**
     * Gets the value of the visible property.
     * 
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the value of the visible property.
     * 
     */
    public void setVisible(boolean value) {
        this.visible = value;
    }

}
