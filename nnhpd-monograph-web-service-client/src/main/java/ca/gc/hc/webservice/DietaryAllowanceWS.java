
package ca.gc.hc.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dietaryAllowanceWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dietaryAllowanceWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="minimumThreshold" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="subPopulation" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}subPopulationWS" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dietaryAllowanceWS", propOrder = {
    "minimumThreshold",
    "subPopulation"
})
public class DietaryAllowanceWS {

    protected Float minimumThreshold;
    protected SubPopulationWS subPopulation;

    /**
     * Gets the value of the minimumThreshold property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getMinimumThreshold() {
        return minimumThreshold;
    }

    /**
     * Sets the value of the minimumThreshold property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setMinimumThreshold(Float value) {
        this.minimumThreshold = value;
    }

    /**
     * Gets the value of the subPopulation property.
     * 
     * @return
     *     possible object is
     *     {@link SubPopulationWS }
     *     
     */
    public SubPopulationWS getSubPopulation() {
        return subPopulation;
    }

    /**
     * Sets the value of the subPopulation property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubPopulationWS }
     *     
     */
    public void setSubPopulation(SubPopulationWS value) {
        this.subPopulation = value;
    }

}
