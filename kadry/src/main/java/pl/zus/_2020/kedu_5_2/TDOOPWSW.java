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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_DOOPWSW complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_DOOPWSW">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identyfikacja.UB" type="{http://www.zus.pl/2020/KEDU_5_2}t_dane_UB_id" minOccurs="0"/>
 *         &lt;element name="cechy.BL" type="{http://www.zus.pl/2020/KEDU_5_2}t_cechy" minOccurs="0"/>
 *         &lt;element name="A" type="{http://www.zus.pl/2020/KEDU_5_2}t_DOOPWSW_A" minOccurs="0"/>
 *         &lt;element name="B" type="{http://www.zus.pl/2020/KEDU_5_2}t_DOOPWSW_B" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.zus.pl/2020/KEDU_5_2}AtrybutyBlokuWielokrotnego"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_DOOPWSW", propOrder = {
    "identyfikacjaUB",
    "cechyBL",
    "a",
    "b"
})
public class TDOOPWSW {

    @XmlElement(name = "identyfikacja.UB")
    protected TDaneUBId identyfikacjaUB;
    @XmlElement(name = "cechy.BL")
    protected TCechy cechyBL;
    @XmlElement(name = "A")
    protected TDOOPWSWA a;
    @XmlElement(name = "B")
    protected TDOOPWSWB b;
    @XmlAttribute(name = "id_bloku", required = true)
    protected BigInteger idBloku;
    @XmlAttribute(name = "status_weryfikacji")
    protected TStatusWeryfikacji statusWeryfikacji;
    @XmlAttribute(name = "status_kontroli")
    protected String statusKontroli;

    /**
     * Gets the value of the identyfikacjaUB property.
     * 
     * @return
     *     possible object is
     *     {@link TDaneUBId }
     *     
     */
    public TDaneUBId getIdentyfikacjaUB() {
        return identyfikacjaUB;
    }

    /**
     * Sets the value of the identyfikacjaUB property.
     * 
     * @param value
     *     allowed object is
     *     {@link TDaneUBId }
     *     
     */
    public void setIdentyfikacjaUB(TDaneUBId value) {
        this.identyfikacjaUB = value;
    }

    /**
     * Gets the value of the cechyBL property.
     * 
     * @return
     *     possible object is
     *     {@link TCechy }
     *     
     */
    public TCechy getCechyBL() {
        return cechyBL;
    }

    /**
     * Sets the value of the cechyBL property.
     * 
     * @param value
     *     allowed object is
     *     {@link TCechy }
     *     
     */
    public void setCechyBL(TCechy value) {
        this.cechyBL = value;
    }

    /**
     * Gets the value of the a property.
     * 
     * @return
     *     possible object is
     *     {@link TDOOPWSWA }
     *     
     */
    public TDOOPWSWA getA() {
        return a;
    }

    /**
     * Sets the value of the a property.
     * 
     * @param value
     *     allowed object is
     *     {@link TDOOPWSWA }
     *     
     */
    public void setA(TDOOPWSWA value) {
        this.a = value;
    }

    /**
     * Gets the value of the b property.
     * 
     * @return
     *     possible object is
     *     {@link TDOOPWSWB }
     *     
     */
    public TDOOPWSWB getB() {
        return b;
    }

    /**
     * Sets the value of the b property.
     * 
     * @param value
     *     allowed object is
     *     {@link TDOOPWSWB }
     *     
     */
    public void setB(TDOOPWSWB value) {
        this.b = value;
    }

    /**
     * Gets the value of the idBloku property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdBloku() {
        return idBloku;
    }

    /**
     * Sets the value of the idBloku property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdBloku(BigInteger value) {
        this.idBloku = value;
    }

    /**
     * Gets the value of the statusWeryfikacji property.
     * 
     * @return
     *     possible object is
     *     {@link TStatusWeryfikacji }
     *     
     */
    public TStatusWeryfikacji getStatusWeryfikacji() {
        return statusWeryfikacji;
    }

    /**
     * Sets the value of the statusWeryfikacji property.
     * 
     * @param value
     *     allowed object is
     *     {@link TStatusWeryfikacji }
     *     
     */
    public void setStatusWeryfikacji(TStatusWeryfikacji value) {
        this.statusWeryfikacji = value;
    }

    /**
     * Gets the value of the statusKontroli property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusKontroli() {
        return statusKontroli;
    }

    /**
     * Sets the value of the statusKontroli property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusKontroli(String value) {
        this.statusKontroli = value;
    }

}
