/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

import error.E;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.apache.commons.io.IOUtils;
import org.xml.sax.SAXException;

/**
 *
 * @author Osito
 */
public class XMLValid_PIT11_27 {
    
    private static String schemaPIT1127 = "resources\\xsd\\pit1127.xsd";
    
    public static Object[] walidujPIT1127(String mainfilename) {
            Object[] zwrot = new Object[2];
            zwrot[0] = Boolean.FALSE;
            error.E.s("start walidacji");
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/")+schemaPIT1127;
            try {
            File schemaFile = null;
            try {
                schemaFile = new File(realPath);
            } catch (Exception ex) {
                // Logger.getLogger(XMLValid.class.getName()).log(Level.SEVERE, null, ex);
            }
            String realPath2 = ctx.getRealPath("/")+"resources\\xml\\";
            FileInputStream fis = new FileInputStream(realPath2+mainfilename);
            String data = IOUtils.toString(fis, "UTF-8");
            Source xmlFile = new StreamSource(new ByteArrayInputStream(data.getBytes(org.apache.commons.codec.CharEncoding.UTF_8)));
            SchemaFactory schemaFactory = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            try {
                    Schema schema = schemaFactory.newSchema(schemaFile);
                Validator validator = schema.newValidator();
                validator.validate(xmlFile);
                zwrot[0] = Boolean.TRUE;
                zwrot[1] = "Plik prawidłowy";
                error.E.s("Plik jest prawidłowy");
                error.E.s("Koniec walidacji bezbledna");
            } catch (SAXException e) {
                zwrot[0] = Boolean.FALSE;
                zwrot[1] = obsluzblad(e);
                error.E.s(obsluzblad(e));
            } catch (Exception e) {
                zwrot[0] = Boolean.FALSE;
                zwrot[1] = "Błąd walidacji pliku. Sprawdzanie przerwane";
            }
        } catch (Exception ex) {
            E.e(ex);
            error.E.s("Błąd ładowania plików do walidacji. Sprawdzanie przerwane");
        }
        return zwrot;
    }
    
    
     private static String obsluzblad(SAXException e) {
        String zwrot ="Bląd walidacji: ";
        String wiadomosc = rodzajbledu(e.getMessage());
        return zwrot+wiadomosc;
    }
     
    private static String rodzajbledu(String message) {
        String zwrot = message;
        String polenip = "";
        try {
            polenip = message.substring(message.indexOf("Value")+6, message.indexOf(" is not facet-valid"));
        } catch (Exception e){}
        if (message.contains("TNrVatUE")) {
            zwrot = "Błędny numer VAT kontrahenta "+polenip+" Sprawdź czy nie ma spacji!"; 
        } else if (message.contains("TKodKrajuUE")) {
            zwrot = "Błąd w symbolu kraju kontrahenta";
        } else if (message.contains("TNazwisko")) {
            zwrot = "Błąd w nazwisku podatnika";
        } else if (message.contains("TImie")) {
            zwrot = "Błąd w imieniu podatnika";
        } else if (message.contains("TNrNIP")) {
            zwrot = "Błąd w numerze NIP podatnika";
        } else if (message.contains("P_Ua}' is expected.")) {
            zwrot = "Brak symbolu kraju kontrahenta/Błędny symbol";
        }
        return zwrot;
    }
}
