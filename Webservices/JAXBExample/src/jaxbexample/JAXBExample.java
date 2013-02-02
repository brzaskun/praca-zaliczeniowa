/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxbexample;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Osito
 */
public class JAXBExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Podpis deklaracja = new Podpis();
        XMLGregorianCalendar cal = XMLGregorianCalendarImpl.createDate(2013, 01, 01,0);
	deklaracja.setDataUrodzenia(cal);
        deklaracja.setImiePierwsze("Jan");
        deklaracja.setNazwisko("Manowiec");
        deklaracja.setNIP("8511005008");
        deklaracja.setPESEL("70052809810");
        deklaracja.setKwota(BigDecimal.ZERO);
        
	  try {
 
		File file = new File("C:\\uslugi\\file.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(Podpis.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
 
		jaxbMarshaller.marshal(deklaracja, file);
		jaxbMarshaller.marshal(deklaracja, System.out);
 
	      } catch (JAXBException e) {
		e.printStackTrace();
	      }
 
	}
}

