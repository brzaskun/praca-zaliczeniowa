//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.01.20 at 11:22:28 PM CET 
//


package pl.zus._2020.kedu_5_2;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_LSKD complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_LSKD">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="p1" type="{http://www.zus.pl/2020/KEDU_5_2}t_Liczba_6" minOccurs="0"/>
 *         &lt;element name="p2" type="{http://www.zus.pl/2020/KEDU_5_2}t_Liczba_6" minOccurs="0"/>
 *         &lt;element name="p3" type="{http://www.zus.pl/2020/KEDU_5_2}t_Kwota_12" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_LSKD", propOrder = {
    "p1",
    "p2",
    "p3"
})
public class TLSKD {

    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger p1;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger p2;
    protected BigDecimal p3;

    /**
     * Gets the value of the p1 property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getP1() {
        return p1;
    }

    /**
     * Sets the value of the p1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setP1(BigInteger value) {
        this.p1 = value;
    }

    /**
     * Gets the value of the p2 property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getP2() {
        return p2;
    }

    /**
     * Sets the value of the p2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setP2(BigInteger value) {
        this.p2 = value;
    }

    /**
     * Gets the value of the p3 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getP3() {
        return p3;
    }

    /**
     * Sets the value of the p3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setP3(BigDecimal value) {
        this.p3 = value;
    }

}