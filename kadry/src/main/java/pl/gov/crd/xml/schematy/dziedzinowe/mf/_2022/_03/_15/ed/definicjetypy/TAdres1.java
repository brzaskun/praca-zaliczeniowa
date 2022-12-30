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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Dane określające adres - bez elementu Poczta w adresie polskim
 * 
 * <p>Java class for TAdres1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TAdres1">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;sequence>
 *           &lt;element name="AdresPol" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/03/15/eD/DefinicjeTypy/}TAdresPolski1"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element name="AdresZagr" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/03/15/eD/DefinicjeTypy/}TAdresZagraniczny"/>
 *         &lt;/sequence>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TAdres1", propOrder = {
    "adresPol",
    "adresZagr"
})
@XmlSeeAlso({
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._03._15.ed.definicjetypy.TOsobaNiefizyczna1 .AdresSiedziby.class,
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._03._15.ed.definicjetypy.TOsobaNiefizyczna2 .AdresSiedziby.class,
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._03._15.ed.definicjetypy.TOsobaFizycznaPelna1 .AdresZamieszkania.class,
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._03._15.ed.definicjetypy.TOsobaFizyczna5 .AdresZamieszkania.class,
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._03._15.ed.definicjetypy.TOsobaFizyczna4 .AdresZamieszkania.class,
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._03._15.ed.definicjetypy.TOsobaFizyczna3 .AdresZamieszkania.class,
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._03._15.ed.definicjetypy.TPodmiotDowolny2 .AdresZamieszkaniaSiedziby.class,
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._03._15.ed.definicjetypy.TPodmiotDowolnyPelny1 .AdresZamieszkaniaSiedziby.class,
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._03._15.ed.definicjetypy.TPodmiotDowolny1 .AdresZamieszkaniaSiedziby.class,
    pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._03._15.ed.definicjetypy.TOsobaNiefizycznaPelna1 .AdresSiedziby.class
})
public class TAdres1 {

    @XmlElement(name = "AdresPol")
    protected TAdresPolski1 adresPol;
    @XmlElement(name = "AdresZagr")
    protected TAdresZagraniczny adresZagr;

    /**
     * Gets the value of the adresPol property.
     * 
     * @return
     *     possible object is
     *     {@link TAdresPolski1 }
     *     
     */
    public TAdresPolski1 getAdresPol() {
        return adresPol;
    }

    /**
     * Sets the value of the adresPol property.
     * 
     * @param value
     *     allowed object is
     *     {@link TAdresPolski1 }
     *     
     */
    public void setAdresPol(TAdresPolski1 value) {
        this.adresPol = value;
    }

    /**
     * Gets the value of the adresZagr property.
     * 
     * @return
     *     possible object is
     *     {@link TAdresZagraniczny }
     *     
     */
    public TAdresZagraniczny getAdresZagr() {
        return adresZagr;
    }

    /**
     * Sets the value of the adresZagr property.
     * 
     * @param value
     *     allowed object is
     *     {@link TAdresZagraniczny }
     *     
     */
    public void setAdresZagr(TAdresZagraniczny value) {
        this.adresZagr = value;
    }

}
