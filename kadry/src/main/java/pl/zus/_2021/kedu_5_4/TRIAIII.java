//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.06.05 at 02:35:54 AM CEST 
//


package pl.zus._2021.kedu_5_4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_RIAIII complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_RIAIII">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="p1" type="{http://www.zus.pl/2021/KEDU_5_4}t_Nazwisko_31"/>
 *         &lt;element name="p2" type="{http://www.zus.pl/2021/KEDU_5_4}t_Imie_22"/>
 *         &lt;element name="p3" type="{http://www.zus.pl/2021/KEDU_5_4}t_TypIdentyfikatora_1"/>
 *         &lt;element name="p4" type="{http://www.zus.pl/2021/KEDU_5_4}t_IdentyfikatorUbezpRaporty_11"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_RIAIII", propOrder = {
    "p1",
    "p2",
    "p3",
    "p4"
})
public class TRIAIII {

    @XmlElement(required = true)
    protected String p1;
    @XmlElement(required = true)
    protected String p2;
    @XmlElement(required = true)
    protected String p3;
    @XmlElement(required = true)
    protected String p4;

    /**
     * Gets the value of the p1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getP1() {
        return p1;
    }

    /**
     * Sets the value of the p1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setP1(String value) {
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

}