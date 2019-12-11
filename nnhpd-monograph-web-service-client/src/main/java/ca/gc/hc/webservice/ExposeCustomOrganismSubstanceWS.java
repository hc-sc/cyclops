
package ca.gc.hc.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for exposeCustomOrganismSubstanceWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="exposeCustomOrganismSubstanceWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}customOrganismSubstanceWS" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "exposeCustomOrganismSubstanceWS", propOrder = {
    "arg0"
})
public class ExposeCustomOrganismSubstanceWS {

    protected CustomOrganismSubstanceWS arg0;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link CustomOrganismSubstanceWS }
     *     
     */
    public CustomOrganismSubstanceWS getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomOrganismSubstanceWS }
     *     
     */
    public void setArg0(CustomOrganismSubstanceWS value) {
        this.arg0 = value;
    }

}
