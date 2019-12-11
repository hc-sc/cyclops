
package ca.gc.hc.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for monographSourceOrganismPartWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="monographSourceOrganismPartWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sourceMaterialName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sourceOrganismPart" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}sourceOrganismPartWS" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "monographSourceOrganismPartWS", propOrder = {
    "sourceMaterialName",
    "sourceOrganismPart"
})
public class MonographSourceOrganismPartWS {

    protected String sourceMaterialName;
    protected SourceOrganismPartWS sourceOrganismPart;

    /**
     * Gets the value of the sourceMaterialName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceMaterialName() {
        return sourceMaterialName;
    }

    /**
     * Sets the value of the sourceMaterialName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceMaterialName(String value) {
        this.sourceMaterialName = value;
    }

    /**
     * Gets the value of the sourceOrganismPart property.
     * 
     * @return
     *     possible object is
     *     {@link SourceOrganismPartWS }
     *     
     */
    public SourceOrganismPartWS getSourceOrganismPart() {
        return sourceOrganismPart;
    }

    /**
     * Sets the value of the sourceOrganismPart property.
     * 
     * @param value
     *     allowed object is
     *     {@link SourceOrganismPartWS }
     *     
     */
    public void setSourceOrganismPart(SourceOrganismPartWS value) {
        this.sourceOrganismPart = value;
    }

}
