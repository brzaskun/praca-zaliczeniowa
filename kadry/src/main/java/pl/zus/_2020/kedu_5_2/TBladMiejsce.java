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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_blad_miejsce complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_blad_miejsce">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id_obiektu" type="{http://www.zus.pl/2020/KEDU_5_2}t_blad_id_obiektu"/>
 *         &lt;element name="typ" type="{http://www.zus.pl/2020/KEDU_5_2}t_blad_typ_obiektu"/>
 *         &lt;element name="blok" type="{http://www.zus.pl/2020/KEDU_5_2}t_blad_blok" minOccurs="0"/>
 *         &lt;element name="pole" type="{http://www.zus.pl/2020/KEDU_5_2}t_blad_pole" minOccurs="0"/>
 *         &lt;element name="id_bloku" type="{http://www.zus.pl/2020/KEDU_5_2}t_blad_id_bloku" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_blad_miejsce", propOrder = {
    "idObiektu",
    "typ",
    "blok",
    "pole",
    "idBloku"
})
public class TBladMiejsce {

    @XmlElement(name = "id_obiektu", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger idObiektu;
    @XmlElement(required = true)
    protected String typ;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger blok;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger pole;
    @XmlElement(name = "id_bloku")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger idBloku;

    /**
     * Gets the value of the idObiektu property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdObiektu() {
        return idObiektu;
    }

    /**
     * Sets the value of the idObiektu property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdObiektu(BigInteger value) {
        this.idObiektu = value;
    }

    /**
     * Gets the value of the typ property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTyp() {
        return typ;
    }

    /**
     * Sets the value of the typ property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTyp(String value) {
        this.typ = value;
    }

    /**
     * Gets the value of the blok property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBlok() {
        return blok;
    }

    /**
     * Sets the value of the blok property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBlok(BigInteger value) {
        this.blok = value;
    }

    /**
     * Gets the value of the pole property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPole() {
        return pole;
    }

    /**
     * Sets the value of the pole property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPole(BigInteger value) {
        this.pole = value;
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

}