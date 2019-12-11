
package ca.gc.hc.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for exposeBilingualDefinedOrganismSubstanceWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="exposeBilingualDefinedOrganismSubstanceWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}bilingualDefinedOrganismSubstanceWS" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "exposeBilingualDefinedOrganismSubstanceWS", propOrder = {
    "arg0"
})
public class ExposeBilingualDefinedOrganismSubstanceWS {

    protected BilingualDefinedOrganismSubstanceWS arg0;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link BilingualDefinedOrganismSubstanceWS }
     *     
     */
    public BilingualDefinedOrganismSubstanceWS getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BilingualDefinedOrganismSubstanceWS }
     *     
     */
    public void setArg0(BilingualDefinedOrganismSubstanceWS value) {
        this.arg0 = value;
    }

}
