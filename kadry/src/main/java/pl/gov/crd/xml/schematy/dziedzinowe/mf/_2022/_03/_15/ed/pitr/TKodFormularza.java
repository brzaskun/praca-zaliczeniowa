//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.12.30 at 12:51:44 PM CET 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._03._15.ed.pitr;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TKodFormularza.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TKodFormularza">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PIT-R"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TKodFormularza")
@XmlEnum
public enum TKodFormularza {

    @XmlEnumValue("PIT-R")
    PIT_R("PIT-R");
    private final String value;

    TKodFormularza(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TKodFormularza fromValue(String v) {
        for (TKodFormularza c: TKodFormularza.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
