//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.01.06 at 03:34:05 PM CET 
//


package sprawozdania.rok2018;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Pozycja sprawozdania w tysiącach złotych umożliwiająca rozszerzenie o dodatkowe pozycje (tzw. pozycje użytkownika)
 * 
 * <p>Java class for TPozycjaSprawozdaniaTys complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPozycjaSprawozdaniaTys">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/DefinicjeTypySprawozdaniaFinansowe/}TKwotyPozycjiTys">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="PozycjaUszczegolawiajaca" type="{http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/DefinicjeTypySprawozdaniaFinansowe/}TKwotyPozycjiSprawozdaniaTys" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPozycjaSprawozdaniaTys", propOrder = {
    "pozycjaUszczegolawiajaca"
})
public class TPozycjaSprawozdaniaTys
    extends TKwotyPozycjiTys
{

    @XmlElement(name = "PozycjaUszczegolawiajaca")
    protected List<TKwotyPozycjiSprawozdaniaTys> pozycjaUszczegolawiajaca;

    /**
     * Gets the value of the pozycjaUszczegolawiajaca property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pozycjaUszczegolawiajaca property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPozycjaUszczegolawiajaca().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TKwotyPozycjiSprawozdaniaTys }
     * 
     * 
     */
    public List<TKwotyPozycjiSprawozdaniaTys> getPozycjaUszczegolawiajaca() {
        if (pozycjaUszczegolawiajaca == null) {
            pozycjaUszczegolawiajaca = new ArrayList<TKwotyPozycjiSprawozdaniaTys>();
        }
        return this.pozycjaUszczegolawiajaca;
    }

}
