//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.12.26 at 09:13:00 PM CET 
//


package sprawozdania.v12;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Identyfikator podmiotu składającego sprawozdanie finansowe. W przypadku sprawozdań finansowych składanych do Szefa KAS wypełnia się identyfikator podatkowy. W sprawozdanych finansowych składanych do KRS wypełnia się numer KRS.
 * 
 * <p>Java class for TIdentyfikatorPodmiotu complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TIdentyfikatorPodmiotu">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="NIP" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TNrNIP"/>
 *         &lt;element name="KRS" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TNrKRS"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TIdentyfikatorPodmiotu", propOrder = {
    "nip",
    "krs"
})
public class TIdentyfikatorPodmiotu {

    @XmlElement(name = "NIP")
    protected String nip;
    @XmlElement(name = "KRS")
    protected String krs;

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
     * Gets the value of the krs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKRS() {
        return krs;
    }

    /**
     * Sets the value of the krs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKRS(String value) {
        this.krs = value;
    }

}