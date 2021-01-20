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
 * <p>Java class for t_ZWUA complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_ZWUA">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="naglowek.DP" type="{http://www.zus.pl/2020/KEDU_5_2}t_naglowek_DP" minOccurs="0"/>
 *         &lt;element name="identyfikacja.PL" type="{http://www.zus.pl/2020/KEDU_5_2}t_dane_PL_id" minOccurs="0"/>
 *         &lt;element name="identyfikacja.UB" type="{http://www.zus.pl/2020/KEDU_5_2}t_dane_UB_id" minOccurs="0"/>
 *         &lt;element name="cechy.DP" type="{http://www.zus.pl/2020/KEDU_5_2}t_cechy" minOccurs="0"/>
 *         &lt;element name="I" type="{http://www.zus.pl/2020/KEDU_5_2}t_DOZWUA" minOccurs="0"/>
 *         &lt;element name="II" type="{http://www.zus.pl/2020/KEDU_5_2}t_DIPL" minOccurs="0"/>
 *         &lt;element name="III" type="{http://www.zus.pl/2020/KEDU_5_2}t_DAU" minOccurs="0"/>
 *         &lt;element name="IV" type="{http://www.zus.pl/2020/KEDU_5_2}t_WUBD"/>
 *         &lt;element name="V" type="{http://www.zus.pl/2020/KEDU_5_2}t_WRSP" minOccurs="0"/>
 *         &lt;element name="VI" type="{http://www.zus.pl/2020/KEDU_5_2}t_OPL1" minOccurs="0"/>
 *         &lt;element name="stopka.DP" type="{http://www.zus.pl/2020/KEDU_5_2}t_stopka_DP" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.zus.pl/2020/KEDU_5_2}AtrybutyDokumentu"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_ZWUA", propOrder = {
    "naglowekDP",
    "identyfikacjaPL",
    "identyfikacjaUB",
    "cechyDP",
    "i",
    "ii",
    "iii",
    "iv",
    "v",
    "vi",
    "stopkaDP"
})
public class TZWUA {

    @XmlElement(name = "naglowek.DP")
    protected TNaglowekDP naglowekDP;
    @XmlElement(name = "identyfikacja.PL")
    protected TDanePLId identyfikacjaPL;
    @XmlElement(name = "identyfikacja.UB")
    protected TDaneUBId identyfikacjaUB;
    @XmlElement(name = "cechy.DP")
    protected TCechy cechyDP;
    @XmlElement(name = "I")
    protected TDOZWUA i;
    @XmlElement(name = "II")
    protected TDIPL ii;
    @XmlElement(name = "III")
    protected TDAU iii;
    @XmlElement(name = "IV", required = true)
    protected TWUBD iv;
    @XmlElement(name = "V")
    protected TWRSP v;
    @XmlElement(name = "VI")
    protected TOPL1 vi;
    @XmlElement(name = "stopka.DP")
    protected TStopkaDP stopkaDP;
    @XmlAttribute(name = "id_dokumentu", required = true)
    protected BigInteger idDokumentu;
    @XmlAttribute(name = "kolejnosc")
    protected BigInteger kolejnosc;
    @XmlAttribute(name = "status_weryfikacji")
    protected TStatusWeryfikacji statusWeryfikacji;
    @XmlAttribute(name = "status_kontroli")
    protected String statusKontroli;

    /**
     * Gets the value of the naglowekDP property.
     * 
     * @return
     *     possible object is
     *     {@link TNaglowekDP }
     *     
     */
    public TNaglowekDP getNaglowekDP() {
        return naglowekDP;
    }

    /**
     * Sets the value of the naglowekDP property.
     * 
     * @param value
     *     allowed object is
     *     {@link TNaglowekDP }
     *     
     */
    public void setNaglowekDP(TNaglowekDP value) {
        this.naglowekDP = value;
    }

    /**
     * Gets the value of the identyfikacjaPL property.
     * 
     * @return
     *     possible object is
     *     {@link TDanePLId }
     *     
     */
    public TDanePLId getIdentyfikacjaPL() {
        return identyfikacjaPL;
    }

    /**
     * Sets the value of the identyfikacjaPL property.
     * 
     * @param value
     *     allowed object is
     *     {@link TDanePLId }
     *     
     */
    public void setIdentyfikacjaPL(TDanePLId value) {
        this.identyfikacjaPL = value;
    }

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
     * Gets the value of the cechyDP property.
     * 
     * @return
     *     possible object is
     *     {@link TCechy }
     *     
     */
    public TCechy getCechyDP() {
        return cechyDP;
    }

    /**
     * Sets the value of the cechyDP property.
     * 
     * @param value
     *     allowed object is
     *     {@link TCechy }
     *     
     */
    public void setCechyDP(TCechy value) {
        this.cechyDP = value;
    }

    /**
     * Gets the value of the i property.
     * 
     * @return
     *     possible object is
     *     {@link TDOZWUA }
     *     
     */
    public TDOZWUA getI() {
        return i;
    }

    /**
     * Sets the value of the i property.
     * 
     * @param value
     *     allowed object is
     *     {@link TDOZWUA }
     *     
     */
    public void setI(TDOZWUA value) {
        this.i = value;
    }

    /**
     * Gets the value of the ii property.
     * 
     * @return
     *     possible object is
     *     {@link TDIPL }
     *     
     */
    public TDIPL getII() {
        return ii;
    }

    /**
     * Sets the value of the ii property.
     * 
     * @param value
     *     allowed object is
     *     {@link TDIPL }
     *     
     */
    public void setII(TDIPL value) {
        this.ii = value;
    }

    /**
     * Gets the value of the iii property.
     * 
     * @return
     *     possible object is
     *     {@link TDAU }
     *     
     */
    public TDAU getIII() {
        return iii;
    }

    /**
     * Sets the value of the iii property.
     * 
     * @param value
     *     allowed object is
     *     {@link TDAU }
     *     
     */
    public void setIII(TDAU value) {
        this.iii = value;
    }

    /**
     * Gets the value of the iv property.
     * 
     * @return
     *     possible object is
     *     {@link TWUBD }
     *     
     */
    public TWUBD getIV() {
        return iv;
    }

    /**
     * Sets the value of the iv property.
     * 
     * @param value
     *     allowed object is
     *     {@link TWUBD }
     *     
     */
    public void setIV(TWUBD value) {
        this.iv = value;
    }

    /**
     * Gets the value of the v property.
     * 
     * @return
     *     possible object is
     *     {@link TWRSP }
     *     
     */
    public TWRSP getV() {
        return v;
    }

    /**
     * Sets the value of the v property.
     * 
     * @param value
     *     allowed object is
     *     {@link TWRSP }
     *     
     */
    public void setV(TWRSP value) {
        this.v = value;
    }

    /**
     * Gets the value of the vi property.
     * 
     * @return
     *     possible object is
     *     {@link TOPL1 }
     *     
     */
    public TOPL1 getVI() {
        return vi;
    }

    /**
     * Sets the value of the vi property.
     * 
     * @param value
     *     allowed object is
     *     {@link TOPL1 }
     *     
     */
    public void setVI(TOPL1 value) {
        this.vi = value;
    }

    /**
     * Gets the value of the stopkaDP property.
     * 
     * @return
     *     possible object is
     *     {@link TStopkaDP }
     *     
     */
    public TStopkaDP getStopkaDP() {
        return stopkaDP;
    }

    /**
     * Sets the value of the stopkaDP property.
     * 
     * @param value
     *     allowed object is
     *     {@link TStopkaDP }
     *     
     */
    public void setStopkaDP(TStopkaDP value) {
        this.stopkaDP = value;
    }

    /**
     * Gets the value of the idDokumentu property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdDokumentu() {
        return idDokumentu;
    }

    /**
     * Sets the value of the idDokumentu property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdDokumentu(BigInteger value) {
        this.idDokumentu = value;
    }

    /**
     * Gets the value of the kolejnosc property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKolejnosc() {
        return kolejnosc;
    }

    /**
     * Sets the value of the kolejnosc property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKolejnosc(BigInteger value) {
        this.kolejnosc = value;
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
