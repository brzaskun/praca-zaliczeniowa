//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.01.20 at 11:22:28 PM CET 
//


package pl.zus._2020.kedu_5_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_TYUB complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_TYUB">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="p1" type="{http://www.zus.pl/2020/KEDU_5_2}t_KodTytuluUbezpieczenia_6"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_TYUB", propOrder = {
    "p1"
})
public class TTYUB {

    @XmlElement(required = true)
    protected TKodTytuluUbezpieczenia6 p1;

    /**
     * Gets the value of the p1 property.
     * 
     * @return
     *     possible object is
     *     {@link TKodTytuluUbezpieczenia6 }
     *     
     */
    public TKodTytuluUbezpieczenia6 getP1() {
        return p1;
    }

    /**
     * Sets the value of the p1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link TKodTytuluUbezpieczenia6 }
     *     
     */
    public void setP1(TKodTytuluUbezpieczenia6 value) {
        this.p1 = value;
    }

}
