//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.01.06 at 03:34:05 PM CET 
//


package sprawozdania.rok2018;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Typ wykorzystywany do przekazywania załączników do sprawozdania, np. spraozdania z audyty lub not dodatkowych, w postaci plików PDF. 
 * 
 * <p>Java class for TInformacjaDodatkowa complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TInformacjaDodatkowa">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Opis" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/}TTekstowy"/>
 *         &lt;element name="Plik" type="{http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/DefinicjeTypySprawozdaniaFinansowe/}TZalacznik" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TInformacjaDodatkowa", propOrder = {
    "opis",
    "plik"
})
public class TInformacjaDodatkowa {

    @XmlElement(name = "Opis", required = true)
    protected String opis;
    @XmlElement(name = "Plik")
    protected TZalacznik plik;

    /**
     * Gets the value of the opis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOpis() {
        return opis;
    }

    /**
     * Sets the value of the opis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOpis(String value) {
        this.opis = value;
    }

    /**
     * Gets the value of the plik property.
     * 
     * @return
     *     possible object is
     *     {@link TZalacznik }
     *     
     */
    public TZalacznik getPlik() {
        return plik;
    }

    /**
     * Sets the value of the plik property.
     * 
     * @param value
     *     allowed object is
     *     {@link TZalacznik }
     *     
     */
    public void setPlik(TZalacznik value) {
        this.plik = value;
    }

}
