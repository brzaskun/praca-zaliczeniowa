//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.06.19 at 01:34:22 PM CEST 
//


package pl.gov.crd.wzor._2021._11._29._11089;

import java.io.Serializable;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Informacje o rachunku bankowym
 * 
 * <p>Java class for TRachunekBankowy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TRachunekBankowy">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="NrRBPL" type="{http://crd.gov.pl/wzor/2021/11/29/11089/}TNrNRB"/>
 *           &lt;sequence>
 *             &lt;element name="NrRBZagr" type="{http://crd.gov.pl/wzor/2021/11/29/11089/}TNrRBZagr"/>
 *             &lt;element name="SWIFT" type="{http://crd.gov.pl/wzor/2021/11/29/11089/}SWIFT_Type"/>
 *           &lt;/sequence>
 *         &lt;/choice>
 *         &lt;element name="RachunekWlasnyBanku" type="{http://crd.gov.pl/wzor/2021/11/29/11089/}TRachunekWlasnyBanku" minOccurs="0"/>
 *         &lt;element name="NazwaBanku" type="{http://crd.gov.pl/wzor/2021/11/29/11089/}TZnakowy" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TRachunekBankowy", propOrder = {
    "nrRBPL",
    "nrRBZagr",
    "swift",
    "rachunekWlasnyBanku",
    "nazwaBanku"
})
public class TRachunekBankowy implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement(name = "NrRBPL")
    protected String nrRBPL;
    @XmlElement(name = "NrRBZagr")
    protected String nrRBZagr;
    @XmlElement(name = "SWIFT")
    protected String swift;
    @XmlElement(name = "RachunekWlasnyBanku")
    protected BigInteger rachunekWlasnyBanku;
    @XmlElement(name = "NazwaBanku")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String nazwaBanku;

    /**
     * Gets the value of the nrRBPL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNrRBPL() {
        return nrRBPL;
    }

    /**
     * Sets the value of the nrRBPL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNrRBPL(String value) {
        this.nrRBPL = value;
    }

    /**
     * Gets the value of the nrRBZagr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNrRBZagr() {
        return nrRBZagr;
    }

    /**
     * Sets the value of the nrRBZagr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNrRBZagr(String value) {
        this.nrRBZagr = value;
    }

    /**
     * Gets the value of the swift property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSWIFT() {
        return swift;
    }

    /**
     * Sets the value of the swift property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSWIFT(String value) {
        this.swift = value;
    }

    /**
     * Gets the value of the rachunekWlasnyBanku property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRachunekWlasnyBanku() {
        return rachunekWlasnyBanku;
    }

    /**
     * Sets the value of the rachunekWlasnyBanku property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRachunekWlasnyBanku(BigInteger value) {
        this.rachunekWlasnyBanku = value;
    }

    /**
     * Gets the value of the nazwaBanku property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNazwaBanku() {
        return nazwaBanku;
    }

    /**
     * Sets the value of the nazwaBanku property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNazwaBanku(String value) {
        this.nazwaBanku = value;
    }

}