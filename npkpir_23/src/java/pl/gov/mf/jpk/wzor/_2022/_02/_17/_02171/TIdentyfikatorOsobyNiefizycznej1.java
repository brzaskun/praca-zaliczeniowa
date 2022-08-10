//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.07.18 at 09:11:32 PM CEST 
//


package pl.gov.mf.jpk.wzor._2022._02._17._02171;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Podstawowy zestaw danych identyfikacyjnych o osobie niefizycznej - bez elementu Numer REGON, rozszerzony o numery identyfikacyjne dla celów OSS i IOSS
 * 
 * <p>Java class for TIdentyfikatorOsobyNiefizycznej1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TIdentyfikatorOsobyNiefizycznej1">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="NIP" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2018/08/24/eD/DefinicjeTypy/}TNrNIP"/>
 *           &lt;element name="EUPLVATID" type="{http://jpk.mf.gov.pl/wzor/2022/02/17/02171/}EUPLVATID_Type"/>
 *           &lt;element name="IMPLVATID" type="{http://jpk.mf.gov.pl/wzor/2022/02/17/02171/}IMPLVATID_Type"/>
 *         &lt;/choice>
 *         &lt;element name="PelnaNazwa">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="240"/>
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
@XmlType(name = "TIdentyfikatorOsobyNiefizycznej1", propOrder = {
    "nip",
    "euplvatid",
    "implvatid",
    "pelnaNazwa"
})
public class TIdentyfikatorOsobyNiefizycznej1 {

    @XmlElement(name = "NIP")
    protected String nip;
    @XmlElement(name = "EUPLVATID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String euplvatid;
    @XmlElement(name = "IMPLVATID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String implvatid;
    @XmlElement(name = "PelnaNazwa", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String pelnaNazwa;

    /**
     * Gets the value of the nip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNIP() {
        return nip;
    }

    /**
     * Sets the value of the nip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNIP(String value) {
        this.nip = value;
    }

    /**
     * Gets the value of the euplvatid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEUPLVATID() {
        return euplvatid;
    }

    /**
     * Sets the value of the euplvatid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEUPLVATID(String value) {
        this.euplvatid = value;
    }

    /**
     * Gets the value of the implvatid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIMPLVATID() {
        return implvatid;
    }

    /**
     * Sets the value of the implvatid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIMPLVATID(String value) {
        this.implvatid = value;
    }

    /**
     * Gets the value of the pelnaNazwa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPelnaNazwa() {
        return pelnaNazwa;
    }

    /**
     * Sets the value of the pelnaNazwa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPelnaNazwa(String value) {
        this.pelnaNazwa = value;
    }

}