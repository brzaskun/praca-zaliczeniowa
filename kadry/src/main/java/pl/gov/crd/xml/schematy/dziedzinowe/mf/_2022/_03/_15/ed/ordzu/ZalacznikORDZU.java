//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.12.30 at 12:51:44 PM CET 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._03._15.ed.ordzu;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Naglowek" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/03/15/eD/ORDZU/}TNaglowek_ORD-ZU"/>
 *         &lt;element name="PozycjeSzczegolowe">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="P_13">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/03/15/eD/DefinicjeTypy/}TTekstowy">
 *                         &lt;maxLength value="2000"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "naglowek",
    "pozycjeSzczegolowe"
})
@XmlRootElement(name = "Zalacznik_ORD-ZU")
public class ZalacznikORDZU {

    @XmlElement(name = "Naglowek", required = true)
    protected TNaglowekORDZU naglowek;
    @XmlElement(name = "PozycjeSzczegolowe", required = true)
    protected ZalacznikORDZU.PozycjeSzczegolowe pozycjeSzczegolowe;

    /**
     * Gets the value of the naglowek property.
     * 
     * @return
     *     possible object is
     *     {@link TNaglowekORDZU }
     *     
     */
    public TNaglowekORDZU getNaglowek() {
        return naglowek;
    }

    /**
     * Sets the value of the naglowek property.
     * 
     * @param value
     *     allowed object is
     *     {@link TNaglowekORDZU }
     *     
     */
    public void setNaglowek(TNaglowekORDZU value) {
        this.naglowek = value;
    }

    /**
     * Gets the value of the pozycjeSzczegolowe property.
     * 
     * @return
     *     possible object is
     *     {@link ZalacznikORDZU.PozycjeSzczegolowe }
     *     
     */
    public ZalacznikORDZU.PozycjeSzczegolowe getPozycjeSzczegolowe() {
        return pozycjeSzczegolowe;
    }

    /**
     * Sets the value of the pozycjeSzczegolowe property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZalacznikORDZU.PozycjeSzczegolowe }
     *     
     */
    public void setPozycjeSzczegolowe(ZalacznikORDZU.PozycjeSzczegolowe value) {
        this.pozycjeSzczegolowe = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="P_13">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2022/03/15/eD/DefinicjeTypy/}TTekstowy">
     *               &lt;maxLength value="2000"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "p13"
    })
    public static class PozycjeSzczegolowe {

        @XmlElement(name = "P_13", required = true)
        protected String p13;

        /**
         * Gets the value of the p13 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getP13() {
            return p13;
        }

        /**
         * Sets the value of the p13 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setP13(String value) {
            this.p13 = value;
        }

    }

}