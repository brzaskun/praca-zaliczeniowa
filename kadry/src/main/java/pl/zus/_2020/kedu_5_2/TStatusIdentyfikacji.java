//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.01.20 at 11:22:28 PM CET 
//


package pl.zus._2020.kedu_5_2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_status_identyfikacji.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="t_status_identyfikacji">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;length value="1"/>
 *     &lt;enumeration value="I"/>
 *     &lt;enumeration value="P"/>
 *     &lt;enumeration value="B"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "t_status_identyfikacji")
@XmlEnum
public enum TStatusIdentyfikacji {

    I,
    P,
    B;

    public String value() {
        return name();
    }

    public static TStatusIdentyfikacji fromValue(String v) {
        return valueOf(v);
    }

}
