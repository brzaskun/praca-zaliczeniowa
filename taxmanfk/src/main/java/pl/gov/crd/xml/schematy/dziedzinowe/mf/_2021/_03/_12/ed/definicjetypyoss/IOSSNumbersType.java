//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.10.27 at 09:55:56 AM CEST 
//


package pl.gov.crd.xml.schematy.dziedzinowe.mf._2021._03._12.ed.definicjetypyoss;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IOSSNumbers_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IOSSNumbers_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IOSSNumber" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/03/12/eD/DefinicjeTypyOss/}IOSSNumber_Type"/>
 *         &lt;element name="IntNumber" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/03/12/eD/DefinicjeTypyOss/}IntNumber_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IOSSNumbers_Type", propOrder = {
    "iossNumber",
    "intNumber"
})
public class IOSSNumbersType {

    @XmlElement(name = "IOSSNumber", required = true)
    protected IOSSNumberType iossNumber;
    @XmlElement(name = "IntNumber")
    protected IntNumberType intNumber;

    /**
     * Gets the value of the iossNumber property.
     * 
     * @return
     *     possible object is
     *     {@link IOSSNumberType }
     *     
     */
    public IOSSNumberType getIOSSNumber() {
        return iossNumber;
    }

    /**
     * Sets the value of the iossNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link IOSSNumberType }
     *     
     */
    public void setIOSSNumber(IOSSNumberType value) {
        this.iossNumber = value;
    }

    /**
     * Gets the value of the intNumber property.
     * 
     * @return
     *     possible object is
     *     {@link IntNumberType }
     *     
     */
    public IntNumberType getIntNumber() {
        return intNumber;
    }

    /**
     * Sets the value of the intNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntNumberType }
     *     
     */
    public void setIntNumber(IntNumberType value) {
        this.intNumber = value;
    }

}