
package ca.gc.hc.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getMonographById complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getMonographById">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="monoID" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographIdWS" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getMonographById", propOrder = {
    "monoID"
})
public class GetMonographById {

    protected MonographIdWS monoID;

    /**
     * Gets the value of the monoID property.
     * 
     * @return
     *     possible object is
     *     {@link MonographIdWS }
     *     
     */
    public MonographIdWS getMonoID() {
        return monoID;
    }

    /**
     * Sets the value of the monoID property.
     * 
     * @param value
     *     allowed object is
     *     {@link MonographIdWS }
     *     
     */
    public void setMonoID(MonographIdWS value) {
        this.monoID = value;
    }

}
