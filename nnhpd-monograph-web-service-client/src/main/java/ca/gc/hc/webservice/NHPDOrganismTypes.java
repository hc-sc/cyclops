
package ca.gc.hc.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NHPDOrganismTypes.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="NHPDOrganismTypes">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ALGAE"/>
 *     &lt;enumeration value="ANIMAL"/>
 *     &lt;enumeration value="BACTERIUM"/>
 *     &lt;enumeration value="FUNGI"/>
 *     &lt;enumeration value="NONHUMANANIMAL"/>
 *     &lt;enumeration value="PLANT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "NHPDOrganismTypes")
@XmlEnum
public enum NHPDOrganismTypes {

    ALGAE,
    ANIMAL,
    BACTERIUM,
    FUNGI,
    NONHUMANANIMAL,
    PLANT;

    public String value() {
        return name();
    }

    public static NHPDOrganismTypes fromValue(String v) {
        return valueOf(v);
    }

}
