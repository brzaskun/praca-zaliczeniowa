/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxb;

import error.E;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import xls.ing.Document;
import xls.ing.Id;
import xls.ing.Other;

/**
 *
 * @author Osito
 */
public class Makexml {

    public static String marszal(Object deklaracja, Class c) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
//            NamespacePrefixMapper  npm = new NamespacePrefixMapper() {
//                @Override
//                public String getPreferredPrefix(String string, String string1, boolean bln) {
//                    return "em";
//                }
//            };
//            marshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", npm);
            marshaller.marshal(deklaracja, new FileWriter("james.xml"));
            marshaller.marshal(deklaracja,sw);
        } catch (Exception ex) {
            E.e(ex);
        }
        return sw.toString();
    }

    public static void unmarszal(String filename, Class c) {
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Marshaller marshaller = context.createMarshaller();
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Object person2 = unmarshaller.unmarshal(new File(filename));
            xls.ing.Document doc = (Document) person2;
            XMLGregorianCalendar frDtTm = doc.getBkToCstmrAcctRpt().getRpt().getFrToDt().getFrDtTm();
            System.out.println(data.Data.calendarToString(frDtTm));
            Id id = doc.getBkToCstmrAcctRpt().getRpt().getNtry().get(1).getNtryDtls().getTxDtls().getRltdPties().getCdtrAcct().getId();
            Other o = (Other) id.getContent().get(1);
            String iban = (String) o.getId().getContent().get(0);
            System.out.println("");
            //System.out.println(person2);
//            System.out.println(person2.getNazwisko());
//            System.out.println(person2.getAdres());

//          marshaller.marshal(person, new FileWriter("edyta.xml"));
//          marshaller.marshal(person, System.out);
        } catch (Exception ex) {
            E.e(ex);
        }
    }
    
    public static void main(String[] args) {
        unmarszal("d:\\ing.xml", xls.ing.Document.class);
    }
}
