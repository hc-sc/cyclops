
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getSubPopulationsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getSubPopulationsResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="subPopulation" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}subPopulationWS" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSubPopulationsResponse", propOrder = {
    "subPopulation"
})
public class GetSubPopulationsResponse {

    protected List<SubPopulationWS> subPopulation;

    /**
     * Gets the value of the subPopulation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subPopulation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubPopulation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubPopulationWS }
     * 
     * 
     */
    public List<SubPopulationWS> getSubPopulation() {
        if (subPopulation == null) {
            subPopulation = new ArrayList<SubPopulationWS>();
        }
        return this.subPopulation;
    }

}
