
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bilingualCountryWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bilingualCountryWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nameE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nameF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provinceStateWS" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}bilingualProvinceStateWS" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bilingualCountryWS", propOrder = {
    "code",
    "name",
    "nameE",
    "nameF",
    "provinceStateWS"
})
public class BilingualCountryWS {

    protected String code;
    protected String name;
    protected String nameE;
    protected String nameF;
    @XmlElement(nillable = true)
    protected List<BilingualProvinceStateWS> provinceStateWS;

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
     * Gets the value of the nameE property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameE() {
        return nameE;
    }

    /**
     * Sets the value of the nameE property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameE(String value) {
        this.nameE = value;
    }

    /**
     * Gets the value of the nameF property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameF() {
        return nameF;
    }

    /**
     * Sets the value of the nameF property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameF(String value) {
        this.nameF = value;
    }

    /**
     * Gets the value of the provinceStateWS property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the provinceStateWS property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProvinceStateWS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BilingualProvinceStateWS }
     * 
     * 
     */
    public List<BilingualProvinceStateWS> getProvinceStateWS() {
        if (provinceStateWS == null) {
            provinceStateWS = new ArrayList<BilingualProvinceStateWS>();
        }
        return this.provinceStateWS;
    }

}
