
package ca.gc.hc.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for monographSourceIngredientWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="monographSourceIngredientWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="monographDependenciesWS" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}monographDependenciesWS" minOccurs="0"/>
 *         &lt;element name="sourceMaterialName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "monographSourceIngredientWS", propOrder = {
    "monographDependenciesWS",
    "sourceMaterialName"
})
public class MonographSourceIngredientWS {

    protected MonographDependenciesWS monographDependenciesWS;
    protected String sourceMaterialName;

    /**
     * Gets the value of the monographDependenciesWS property.
     * 
     * @return
     *     possible object is
     *     {@link MonographDependenciesWS }
     *     
     */
    public MonographDependenciesWS getMonographDependenciesWS() {
        return monographDependenciesWS;
    }

    /**
     * Sets the value of the monographDependenciesWS property.
     * 
     * @param value
     *     allowed object is
     *     {@link MonographDependenciesWS }
     *     
     */
    public void setMonographDependenciesWS(MonographDependenciesWS value) {
        this.monographDependenciesWS = value;
    }

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

}
