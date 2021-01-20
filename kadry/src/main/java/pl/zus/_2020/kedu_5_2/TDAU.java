//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.01.20 at 11:22:28 PM CET 
//


package pl.zus._2020.kedu_5_2;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for t_DAU complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_DAU">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="p1" type="{http://www.zus.pl/2020/KEDU_5_2}t_PESEL_11" minOccurs="0"/>
 *         &lt;element name="p2" type="{http://www.zus.pl/2020/KEDU_5_2}t_NIP_10" minOccurs="0"/>
 *         &lt;element name="p3" type="{http://www.zus.pl/2020/KEDU_5_2}t_RodzajDokumentu_1" minOccurs="0"/>
 *         &lt;element name="p4" type="{http://www.zus.pl/2020/KEDU_5_2}t_IdentyfikatorPlatnUbezp_9" minOccurs="0"/>
 *         &lt;element name="p5" type="{http://www.zus.pl/2020/KEDU_5_2}t_Nazwisko_31" minOccurs="0"/>
 *         &lt;element name="p6" type="{http://www.zus.pl/2020/KEDU_5_2}t_Imie_22" minOccurs="0"/>
 *         &lt;element name="p7" type="{http://www.zus.pl/2020/KEDU_5_2}t_Data_DDMMRRRR_8" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_DAU", propOrder = {
    "p1",
    "p2",
    "p3",
    "p4",
    "p5",
    "p6",
    "p7"
})
public class TDAU {

    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger p1;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger p2;
    protected String p3;
    protected String p4;
    protected String p5;
    protected String p6;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar p7;

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
     *     {@link String }
     *     
     */
    public String getP3() {
        return p3;
    }

    /**
     * Sets the value of the p3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setP3(String value) {
        this.p3 = value;
    }

    /**
     * Gets the value of the p4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getP4() {
        return p4;
    }

    /**
     * Sets the value of the p4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setP4(String value) {
        this.p4 = value;
    }

    /**
     * Gets the value of the p5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getP5() {
        return p5;
    }

    /**
     * Sets the value of the p5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setP5(String value) {
        this.p5 = value;
    }

    /**
     * Gets the value of the p6 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getP6() {
        return p6;
    }

    /**
     * Sets the value of the p6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setP6(String value) {
        this.p6 = value;
    }

    /**
     * Gets the value of the p7 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getP7() {
        return p7;
    }

    /**
     * Sets the value of the p7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setP7(XMLGregorianCalendar value) {
        this.p7 = value;
    }

}
