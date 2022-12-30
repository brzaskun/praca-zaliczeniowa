//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.12.30 at 12:51:44 PM CET 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._03._15.ed.definicjetypy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Skrócony zestaw danych o osobie fizycznej lub niefizycznej z identyfikatorem NIP albo PESEL
 * 
 * <p>Java class for TPodmiotDowolnyBezAdresu1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPodmiotDowolnyBezAdresu1">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="OsobaFizyczna" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/03/15/eD/DefinicjeTypy/}TIdentyfikatorOsobyFizycznej1"/>
 *         &lt;element name="OsobaNiefizyczna" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/03/15/eD/DefinicjeTypy/}TIdentyfikatorOsobyNiefizycznej"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPodmiotDowolnyBezAdresu1", propOrder = {
    "osobaFizyczna",
    "osobaNiefizyczna"
})
public class TPodmiotDowolnyBezAdresu1 {

    @XmlElement(name = "OsobaFizyczna")
    protected TIdentyfikatorOsobyFizycznej1 osobaFizyczna;
    @XmlElement(name = "OsobaNiefizyczna")
    protected TIdentyfikatorOsobyNiefizycznej osobaNiefizyczna;

    /**
     * Gets the value of the osobaFizyczna property.
     * 
     * @return
     *     possible object is
     *     {@link TIdentyfikatorOsobyFizycznej1 }
     *     
     */
    public TIdentyfikatorOsobyFizycznej1 getOsobaFizyczna() {
        return osobaFizyczna;
    }

    /**
     * Sets the value of the osobaFizyczna property.
     * 
     * @param value
     *     allowed object is
     *     {@link TIdentyfikatorOsobyFizycznej1 }
     *     
     */
    public void setOsobaFizyczna(TIdentyfikatorOsobyFizycznej1 value) {
        this.osobaFizyczna = value;
    }

    /**
     * Gets the value of the osobaNiefizyczna property.
     * 
     * @return
     *     possible object is
     *     {@link TIdentyfikatorOsobyNiefizycznej }
     *     
     */
    public TIdentyfikatorOsobyNiefizycznej getOsobaNiefizyczna() {
        return osobaNiefizyczna;
    }

    /**
     * Sets the value of the osobaNiefizyczna property.
     * 
     * @param value
     *     allowed object is
     *     {@link TIdentyfikatorOsobyNiefizycznej }
     *     
     */
    public void setOsobaNiefizyczna(TIdentyfikatorOsobyNiefizycznej value) {
        this.osobaNiefizyczna = value;
    }

}
