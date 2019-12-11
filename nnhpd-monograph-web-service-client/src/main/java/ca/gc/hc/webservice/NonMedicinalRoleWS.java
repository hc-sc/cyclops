
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for nonMedicinalRoleWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="nonMedicinalRoleWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nonMedicinalPurposes" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}nonMedicinalPurposeWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="restrictions" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}nonMedicinalRestrictionWS" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nonMedicinalRoleWS", propOrder = {
    "nonMedicinalPurposes",
    "restrictions"
})
public class NonMedicinalRoleWS {

    @XmlElement(nillable = true)
    protected List<NonMedicinalPurposeWS> nonMedicinalPurposes;
    @XmlElement(nillable = true)
    protected List<NonMedicinalRestrictionWS> restrictions;

    /**
     * Gets the value of the nonMedicinalPurposes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nonMedicinalPurposes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNonMedicinalPurposes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NonMedicinalPurposeWS }
     * 
     * 
     */
    public List<NonMedicinalPurposeWS> getNonMedicinalPurposes() {
        if (nonMedicinalPurposes == null) {
            nonMedicinalPurposes = new ArrayList<NonMedicinalPurposeWS>();
        }
        return this.nonMedicinalPurposes;
    }

    /**
     * Gets the value of the restrictions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the restrictions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRestrictions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NonMedicinalRestrictionWS }
     * 
     * 
     */
    public List<NonMedicinalRestrictionWS> getRestrictions() {
        if (restrictions == null) {
            restrictions = new ArrayList<NonMedicinalRestrictionWS>();
        }
        return this.restrictions;
    }

}
