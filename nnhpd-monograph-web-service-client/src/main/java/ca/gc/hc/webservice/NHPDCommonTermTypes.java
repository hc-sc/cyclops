
package ca.gc.hc.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NHPDCommonTermTypes.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="NHPDCommonTermTypes">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Flame Projection"/>
 *     &lt;enumeration value="Exceptions"/>
 *     &lt;enumeration value="Irradiation"/>
 *     &lt;enumeration value="Storage"/>
 *     &lt;enumeration value="Lot"/>
 *     &lt;enumeration value="Original Material"/>
 *     &lt;enumeration value="Homeopathic Use"/>
 *     &lt;enumeration value="Homeopathic Dosage Unit"/>
 *     &lt;enumeration value="Dose Frequency Unit"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "NHPDCommonTermTypes")
@XmlEnum
public enum NHPDCommonTermTypes {

    @XmlEnumValue("Flame Projection")
    FLAME_PROJECTION("Flame Projection"),
    @XmlEnumValue("Exceptions")
    EXCEPTIONS("Exceptions"),
    @XmlEnumValue("Irradiation")
    IRRADIATION("Irradiation"),
    @XmlEnumValue("Storage")
    STORAGE("Storage"),
    @XmlEnumValue("Lot")
    LOT("Lot"),
    @XmlEnumValue("Original Material")
    ORIGINAL_MATERIAL("Original Material"),
    @XmlEnumValue("Homeopathic Use")
    HOMEOPATHIC_USE("Homeopathic Use"),
    @XmlEnumValue("Homeopathic Dosage Unit")
    HOMEOPATHIC_DOSAGE_UNIT("Homeopathic Dosage Unit"),
    @XmlEnumValue("Dose Frequency Unit")
    DOSE_FREQUENCY_UNIT("Dose Frequency Unit");
    private final String value;

    NHPDCommonTermTypes(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static NHPDCommonTermTypes fromValue(String v) {
        for (NHPDCommonTermTypes c: NHPDCommonTermTypes.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
