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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for t_DDORSA_B complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_DDORSA_B">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="p1" type="{http://www.zus.pl/2020/KEDU_5_2}t_KodTytuluUbezpieczenia_6"/>
 *         &lt;element name="p2" type="{http://www.zus.pl/2020/KEDU_5_2}t_KodSwiadczeniaPrzerwy_3"/>
 *         &lt;element name="p3" type="{http://www.zus.pl/2020/KEDU_5_2}t_Data_DDMMRRRR_8" minOccurs="0"/>
 *         &lt;element name="p4" type="{http://www.zus.pl/2020/KEDU_5_2}t_Data_DDMMRRRR_8" minOccurs="0"/>
 *         &lt;element name="p5" type="{http://www.zus.pl/2020/KEDU_5_2}t_LiczbaDni_3" minOccurs="0"/>
 *         &lt;element name="p6" type="{http://www.zus.pl/2020/KEDU_5_2}t_Kwota_7" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_DDORSA_B", propOrder = {
    "p1",
    "p2",
    "p3",
    "p4",
    "p5",
    "p6"
})
public class TDDORSAB {

    @XmlElement(required = true)
    protected TKodTytuluUbezpieczenia6 p1;
    @XmlElement(required = true)
    protected String p2;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar p3;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar p4;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger p5;
    protected BigDecimal p6;

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

    /**
     * Gets the value of the p2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getP2() {
        return p2;
    }

    /**
     * Sets the value of the p2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setP2(String value) {
        this.p2 = value;
    }

    /**
     * Gets the value of the p3 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getP3() {
        return p3;
    }

    /**
     * Sets the value of the p3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setP3(XMLGregorianCalendar value) {
        this.p3 = value;
    }

    /**
     * Gets the value of the p4 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getP4() {
        return p4;
    }

    /**
     * Sets the value of the p4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setP4(XMLGregorianCalendar value) {
        this.p4 = value;
    }

    /**
     * Gets the value of the p5 property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getP5() {
        return p5;
    }

    /**
     * Sets the value of the p5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setP5(BigInteger value) {
        this.p5 = value;
    }

    /**
     * Gets the value of the p6 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getP6() {
        return p6;
    }

    /**
     * Sets the value of the p6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setP6(BigDecimal value) {
        this.p6 = value;
    }

}