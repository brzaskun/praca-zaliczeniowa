//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.12.31 at 07:59:47 PM CET 
//


package deklaracje.vat717;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TKodFormularza_ZU.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TKodFormularza_ZU">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ORD-ZU"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TKodFormularza_ZU", namespace = "http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/02/05/eD/ORDZU/")
@XmlEnum
public enum TKodFormularzaZU {

    @XmlEnumValue("ORD-ZU")
    ORD_ZU("ORD-ZU");
    private final String value;

    TKodFormularzaZU(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TKodFormularzaZU fromValue(String v) {
        for (TKodFormularzaZU c: TKodFormularzaZU.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
