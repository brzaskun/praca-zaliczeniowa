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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

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
            marshaller.marshal(deklaracja, new FileWriter("d:\\james.xml"));
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

            error.E.s("koniec");
            //error.E.s(person2);
//            error.E.s(person2.getNazwisko());
//            error.E.s(person2.getAdres());

//          marshaller.marshal(person, new FileWriter("edyta.xml"));
//          marshaller.marshal(person, System.out);
        } catch (javax.xml.bind.UnmarshalException ex1) {
            error.E.s(E.e(ex1));
        } catch (Exception ex) {
            E.e(ex);
            error.E.s("error");
        }
    }
    
    public static void main(String[] args) {
        //unmarszal("d:\\ing2.xml", xls.ing.Document.class);
    }
}
