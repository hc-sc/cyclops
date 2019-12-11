
package ca.gc.hc.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NHPDUnitTypeCodes.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="NHPDUnitTypeCodes">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ADI"/>
 *     &lt;enumeration value="AREA"/>
 *     &lt;enumeration value="BIOU"/>
 *     &lt;enumeration value="DEN"/>
 *     &lt;enumeration value="DOSF"/>
 *     &lt;enumeration value="EQUV"/>
 *     &lt;enumeration value="EQUW"/>
 *     &lt;enumeration value="HOMU"/>
 *     &lt;enumeration value="IMPD"/>
 *     &lt;enumeration value="INTU"/>
 *     &lt;enumeration value="LEN"/>
 *     &lt;enumeration value="MASS"/>
 *     &lt;enumeration value="MICC"/>
 *     &lt;enumeration value="MOLE"/>
 *     &lt;enumeration value="PERC"/>
 *     &lt;enumeration value="QUAS"/>
 *     &lt;enumeration value="RAD"/>
 *     &lt;enumeration value="TEMP"/>
 *     &lt;enumeration value="TIME"/>
 *     &lt;enumeration value="VOLUME"/>
 *     &lt;enumeration value="WGHT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "NHPDUnitTypeCodes")
@XmlEnum
public enum NHPDUnitTypeCodes {

    ADI,
    AREA,
    BIOU,
    DEN,
    DOSF,
    EQUV,
    EQUW,
    HOMU,
    IMPD,
    INTU,
    LEN,
    MASS,
    MICC,
    MOLE,
    PERC,
    QUAS,
    RAD,
    TEMP,
    TIME,
    VOLUME,
    WGHT;

    public String value() {
        return name();
    }

    public static NHPDUnitTypeCodes fromValue(String v) {
        return valueOf(v);
    }

}
