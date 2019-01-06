//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.01.06 at 03:34:05 PM CET 
//


package sprawozdania.rok2018;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * Nagłówek sprawozdania finansowego dla Jednostki Innej w tysiącach
 * 
 * <p>Java class for TNaglowekSprawozdaniaFinansowegoJednostkaInnaWTysiacach complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TNaglowekSprawozdaniaFinansowegoJednostkaInnaWTysiacach">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/DefinicjeTypySprawozdaniaFinansowe/}TNaglowekSprawozdaniaFinansowego">
 *       &lt;sequence>
 *         &lt;element name="KodSprawozdania">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="kodSystemowy" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="SFJINT (1)" />
 *                 &lt;attribute name="wersjaSchemy" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="1-0E" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="WariantSprawozdania">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TNaglowekSprawozdaniaFinansowegoJednostkaInnaWTysiacach", namespace = "http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/JednostkaInnaStruktury", propOrder = {
    "kodSprawozdania",
    "wariantSprawozdania"
})
public class TNaglowekSprawozdaniaFinansowegoJednostkaInnaWTysiacach
    extends TNaglowekSprawozdaniaFinansowego
{

    @XmlElement(name = "KodSprawozdania", required = true)
    protected TNaglowekSprawozdaniaFinansowegoJednostkaInnaWTysiacach.KodSprawozdania kodSprawozdania;
    @XmlElement(name = "WariantSprawozdania", required = true)
    protected String wariantSprawozdania;

    /**
     * Gets the value of the kodSprawozdania property.
     * 
     * @return
     *     possible object is
     *     {@link TNaglowekSprawozdaniaFinansowegoJednostkaInnaWTysiacach.KodSprawozdania }
     *     
     */
    public TNaglowekSprawozdaniaFinansowegoJednostkaInnaWTysiacach.KodSprawozdania getKodSprawozdania() {
        return kodSprawozdania;
    }

    /**
     * Sets the value of the kodSprawozdania property.
     * 
     * @param value
     *     allowed object is
     *     {@link TNaglowekSprawozdaniaFinansowegoJednostkaInnaWTysiacach.KodSprawozdania }
     *     
     */
    public void setKodSprawozdania(TNaglowekSprawozdaniaFinansowegoJednostkaInnaWTysiacach.KodSprawozdania value) {
        this.kodSprawozdania = value;
    }

    /**
     * Gets the value of the wariantSprawozdania property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWariantSprawozdania() {
        return wariantSprawozdania;
    }

    /**
     * Sets the value of the wariantSprawozdania property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWariantSprawozdania(String value) {
        this.wariantSprawozdania = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="kodSystemowy" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="SFJINT (1)" />
     *       &lt;attribute name="wersjaSchemy" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="1-0E" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class KodSprawozdania {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "kodSystemowy", required = true)
        protected String kodSystemowy;
        @XmlAttribute(name = "wersjaSchemy", required = true)
        protected String wersjaSchemy;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the kodSystemowy property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getKodSystemowy() {
            if (kodSystemowy == null) {
                return "SFJINT (1)";
            } else {
                return kodSystemowy;
            }
        }

        /**
         * Sets the value of the kodSystemowy property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setKodSystemowy(String value) {
            this.kodSystemowy = value;
        }

        /**
         * Gets the value of the wersjaSchemy property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWersjaSchemy() {
            if (wersjaSchemy == null) {
                return "1-0E";
            } else {
                return wersjaSchemy;
            }
        }

        /**
         * Sets the value of the wersjaSchemy property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWersjaSchemy(String value) {
            this.wersjaSchemy = value;
        }

    }

}
