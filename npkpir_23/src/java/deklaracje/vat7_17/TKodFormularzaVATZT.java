//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.03.20 at 09:12:36 PM CET 
//


package deklaracje.vat7_17;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TKodFormularza_VAT-ZT.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TKodFormularza_VAT-ZT">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="VAT-ZT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TKodFormularza_VAT-ZT", namespace = "http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/07/29/eD/VATZT/")
@XmlEnum
public enum TKodFormularzaVATZT {

    @XmlEnumValue("VAT-ZT")
    VAT_ZT("VAT-ZT");
    private final String value;

    TKodFormularzaVATZT(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TKodFormularzaVATZT fromValue(String v) {
        for (TKodFormularzaVATZT c: TKodFormularzaVATZT.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
