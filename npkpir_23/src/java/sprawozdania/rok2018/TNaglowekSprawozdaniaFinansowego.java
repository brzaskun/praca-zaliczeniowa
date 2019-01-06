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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Nagłówek sprawozdania finansowego
 * 
 * <p>Java class for TNaglowekSprawozdaniaFinansowego complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TNaglowekSprawozdaniaFinansowego">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OkresOd" type="{http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/DefinicjeTypySprawozdaniaFinansowe/}TDataSF"/>
 *         &lt;element name="OkresDo" type="{http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/DefinicjeTypySprawozdaniaFinansowe/}TDataSF"/>
 *         &lt;element name="DataSporzadzenia" type="{http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/DefinicjeTypySprawozdaniaFinansowe/}TDataSF"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TNaglowekSprawozdaniaFinansowego", propOrder = {
    "okresOd",
    "okresDo",
    "dataSporzadzenia"
})
@XmlSeeAlso({
    TNaglowekSprawozdaniaFinansowegoJednostkaInnaWZlotych.class,
    TNaglowekSprawozdaniaFinansowegoJednostkaInnaWTysiacach.class
})
public class TNaglowekSprawozdaniaFinansowego {

    @XmlElement(name = "OkresOd", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar okresOd;
    @XmlElement(name = "OkresDo", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar okresDo;
    @XmlElement(name = "DataSporzadzenia", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataSporzadzenia;

    /**
     * Gets the value of the okresOd property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOkresOd() {
        return okresOd;
    }

    /**
     * Sets the value of the okresOd property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOkresOd(XMLGregorianCalendar value) {
        this.okresOd = value;
    }

    /**
     * Gets the value of the okresDo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOkresDo() {
        return okresDo;
    }

    /**
     * Sets the value of the okresDo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOkresDo(XMLGregorianCalendar value) {
        this.okresDo = value;
    }

    /**
     * Gets the value of the dataSporzadzenia property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataSporzadzenia() {
        return dataSporzadzenia;
    }

    /**
     * Sets the value of the dataSporzadzenia property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataSporzadzenia(XMLGregorianCalendar value) {
        this.dataSporzadzenia = value;
    }

}
