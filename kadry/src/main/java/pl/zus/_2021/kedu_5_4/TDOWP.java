//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.06.05 at 02:35:54 AM CEST 
//


package pl.zus._2021.kedu_5_4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for t_DOWP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_DOWP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="p1" type="{http://www.zus.pl/2021/KEDU_5_4}t_Data_DDMMRRRR_8" minOccurs="0"/>
 *         &lt;element name="p2" type="{http://www.zus.pl/2021/KEDU_5_4}t_TAK_NIE_1" minOccurs="0"/>
 *         &lt;element name="p3" type="{http://www.zus.pl/2021/KEDU_5_4}t_TAK_NIE_1" minOccurs="0"/>
 *         &lt;element name="p4" type="{http://www.zus.pl/2021/KEDU_5_4}t_TAK_NIE_1" minOccurs="0"/>
 *         &lt;element name="p5" type="{http://www.zus.pl/2021/KEDU_5_4}t_TAK_NIE_1" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_DOWP", propOrder = {
    "p1",
    "p2",
    "p3",
    "p4",
    "p5"
})
public class TDOWP {

    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar p1;
    protected Boolean p2;
    protected Boolean p3;
    protected Boolean p4;
    protected Boolean p5;

    /**
     * Gets the value of the p1 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getP1() {
        return p1;
    }

    /**
     * Sets the value of the p1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setP1(XMLGregorianCalendar value) {
        this.p1 = value;
    }

    /**
     * Gets the value of the p2 property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isP2() {
        return p2;
    }

    /**
     * Sets the value of the p2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setP2(Boolean value) {
        this.p2 = value;
    }

    /**
     * Gets the value of the p3 property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isP3() {
        return p3;
    }

    /**
     * Sets the value of the p3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setP3(Boolean value) {
        this.p3 = value;
    }

    /**
     * Gets the value of the p4 property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isP4() {
        return p4;
    }

    /**
     * Sets the value of the p4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setP4(Boolean value) {
        this.p4 = value;
    }

    /**
     * Gets the value of the p5 property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isP5() {
        return p5;
    }

    /**
     * Sets the value of the p5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setP5(Boolean value) {
        this.p5 = value;
    }

}