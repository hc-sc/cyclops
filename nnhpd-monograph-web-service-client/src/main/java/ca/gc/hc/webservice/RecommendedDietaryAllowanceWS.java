
package ca.gc.hc.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for recommendedDietaryAllowanceWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="recommendedDietaryAllowanceWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dietaryAllowances" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}dietaryAllowanceWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ingredientAuthorizedName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="units" type="{http://ingredient.webservice.nhpd.hc.gc.ca/}unitsWS" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recommendedDietaryAllowanceWS", propOrder = {
    "code",
    "dietaryAllowances",
    "ingredientAuthorizedName",
    "units"
})
public class RecommendedDietaryAllowanceWS {

    protected String code;
    @XmlElement(nillable = true)
    protected List<DietaryAllowanceWS> dietaryAllowances;
    protected String ingredientAuthorizedName;
    protected UnitsWS units;

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the dietaryAllowances property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dietaryAllowances property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDietaryAllowances().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DietaryAllowanceWS }
     * 
     * 
     */
    public List<DietaryAllowanceWS> getDietaryAllowances() {
        if (dietaryAllowances == null) {
            dietaryAllowances = new ArrayList<DietaryAllowanceWS>();
        }
        return this.dietaryAllowances;
    }

    /**
     * Gets the value of the ingredientAuthorizedName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIngredientAuthorizedName() {
        return ingredientAuthorizedName;
    }

    /**
     * Sets the value of the ingredientAuthorizedName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIngredientAuthorizedName(String value) {
        this.ingredientAuthorizedName = value;
    }

    /**
     * Gets the value of the units property.
     * 
     * @return
     *     possible object is
     *     {@link UnitsWS }
     *     
     */
    public UnitsWS getUnits() {
        return units;
    }

    /**
     * Sets the value of the units property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitsWS }
     *     
     */
    public void setUnits(UnitsWS value) {
        this.units = value;
    }

}
