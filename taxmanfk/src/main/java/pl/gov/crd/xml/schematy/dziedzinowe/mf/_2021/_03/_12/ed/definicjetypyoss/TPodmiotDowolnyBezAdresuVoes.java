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
 * Skrócony zestaw danych o podmiocie
 * 
 * <p>Java class for TPodmiotDowolnyBezAdresuVoes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPodmiotDowolnyBezAdresuVoes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PodmiotDowolny" type="{http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2021/03/12/eD/DefinicjeTypyOss/}TIdentyfikatorOsobyVoesMandatory"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPodmiotDowolnyBezAdresuVoes", propOrder = {
    "podmiotDowolny"
})
public class TPodmiotDowolnyBezAdresuVoes {

    @XmlElement(name = "PodmiotDowolny", required = true)
    protected TIdentyfikatorOsobyVoesMandatory podmiotDowolny;

    /**
     * Gets the value of the podmiotDowolny property.
     * 
     * @return
     *     possible object is
     *     {@link TIdentyfikatorOsobyVoesMandatory }
     *     
     */
    public TIdentyfikatorOsobyVoesMandatory getPodmiotDowolny() {
        return podmiotDowolny;
    }

    /**
     * Sets the value of the podmiotDowolny property.
     * 
     * @param value
     *     allowed object is
     *     {@link TIdentyfikatorOsobyVoesMandatory }
     *     
     */
    public void setPodmiotDowolny(TIdentyfikatorOsobyVoesMandatory value) {
        this.podmiotDowolny = value;
    }

}