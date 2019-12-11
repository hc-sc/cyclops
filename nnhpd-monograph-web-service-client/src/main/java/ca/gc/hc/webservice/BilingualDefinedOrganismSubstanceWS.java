
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bilingualDefinedOrganismSubstanceWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bilingualDefinedOrganismSubstanceWS">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ingredient.webservice.nhpd.hc.gc.ca/}bilingualIngredientWS">
 *       &lt;sequence>
 *         &lt;element name="organismTypes" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}organismTypeWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sourceOrganismParts" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}organismPartWS" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bilingualDefinedOrganismSubstanceWS", propOrder = {
    "organismTypes",
    "sourceOrganismParts"
})
public class BilingualDefinedOrganismSubstanceWS
    extends BilingualIngredientWS
{

    @XmlElement(nillable = true)
    protected List<OrganismTypeWS> organismTypes;
    @XmlElement(nillable = true)
    protected List<OrganismPartWS> sourceOrganismParts;

    /**
     * Gets the value of the organismTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the organismTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrganismTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrganismTypeWS }
     * 
     * 
     */
    public List<OrganismTypeWS> getOrganismTypes() {
        if (organismTypes == null) {
            organismTypes = new ArrayList<OrganismTypeWS>();
        }
        return this.organismTypes;
    }

    /**
     * Gets the value of the sourceOrganismParts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sourceOrganismParts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSourceOrganismParts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrganismPartWS }
     * 
     * 
     */
    public List<OrganismPartWS> getSourceOrganismParts() {
        if (sourceOrganismParts == null) {
            sourceOrganismParts = new ArrayList<OrganismPartWS>();
        }
        return this.sourceOrganismParts;
    }

}
