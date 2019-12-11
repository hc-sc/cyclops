
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bilingualHomeopathicSubstanceWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bilingualHomeopathicSubstanceWS">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ingredient.webservice.nhpd.hc.gc.ca/}bilingualIngredientWS">
 *       &lt;sequence>
 *         &lt;element name="sourceMaterials" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bilingualHomeopathicSubstanceWS", propOrder = {
    "sourceMaterials"
})
public class BilingualHomeopathicSubstanceWS
    extends BilingualIngredientWS
{

    @XmlElement(nillable = true)
    protected List<String> sourceMaterials;

    /**
     * Gets the value of the sourceMaterials property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sourceMaterials property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSourceMaterials().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSourceMaterials() {
        if (sourceMaterials == null) {
            sourceMaterials = new ArrayList<String>();
        }
        return this.sourceMaterials;
    }

}
