//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.06.19 at 02:15:30 PM CEST 
//


package pl.gov.mf.ksef.schema.gtw.svc.online.types._2021._10._01._0001;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import pl.gov.mf.ksef.schema.gtw.svc.types._2021._10._01._0001.AuthorisationTypeType;


/**
 * <p>Java class for AuthorisationContextSignedType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuthorisationContextSignedType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ksef.mf.gov.pl/schema/gtw/svc/online/types/2021/10/01/0001}AuthorisationContextType">
 *       &lt;sequence>
 *         &lt;element name="Type" type="{http://ksef.mf.gov.pl/schema/gtw/svc/types/2021/10/01/0001}AuthorisationTypeType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthorisationContextSignedType", propOrder = {
    "type"
})
public class AuthorisationContextSignedType
    extends AuthorisationContextType
implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement(name = "Type", required = true)
    @XmlSchemaType(name = "string")
    protected AuthorisationTypeType type;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link AuthorisationTypeType }
     *     
     */
    public AuthorisationTypeType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthorisationTypeType }
     *     
     */
    public void setType(AuthorisationTypeType value) {
        this.type = value;
    }

}