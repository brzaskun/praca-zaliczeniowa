/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;
import com.sun.xml.stream.buffer.XMLStreamBuffer;
import com.sun.xml.stream.buffer.XMLStreamBufferSource;
import error.E;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.commons.io.IOUtils;
import org.xml.sax.SAXException;
/**
 *
 * @author Osito
 */
public class XMLValid {
    private static String schemaVATUE4 = "resources\\schema\\vatue4schemat.xsd";
    private static String schemaVATUE4l = "d:\\vatue4schemat.xsd";
    private static String schemasprfin = "d:\\JednostkaInnaWZlotych(1)_v1-0.xsd";
    
    
    public static Object[] walidujCMLVATUE(String deklaracja) {
        Object[] zwrot = new Object[2];
        zwrot[0] = Boolean.FALSE;
        InputStream stream = null;
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = ctx.getRealPath("/")+schemaVATUE4;
        try {
            //URL schemaFile = null;
//        try {
//            schemaFile = new URL(schemaVATUE4);
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(XMLValid.class.getName()).log(Level.SEVERE, null, ex);
//        }
// webapp example xsd:
// URL schemaFile = new URL("http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd");
// local file example:
            File schemaFile = new File(realPath); // etc.
            String data = deklaracja;
            try {
                //FileInputStream fis = new FileInputStream("d:\\vatue4.xml");
                //data = IOUtils.toString(fis, "UTF-8");
                int czyjestpodpis = data.indexOf("<Signature");
                if (czyjestpodpis > 0) {
                    data = data.substring(0, data.indexOf("<Signature")) + data.substring(data.indexOf("</Signature>") + 12);
                    //System.out.println(data);
                }
            } catch (Exception ex) {
                Logger.getLogger(XMLValid.class.getName()).log(Level.SEVERE, null, ex);
            }
            stream = new ByteArrayInputStream(data.getBytes("UTF-8"));
            //Source xmlFile = new StreamSource(new File("d:\\vatue4.xml"));
            Source xmlFile = new StreamSource(stream);
            SchemaFactory schemaFactory = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            try {
                Schema schema = schemaFactory.newSchema(schemaFile);
                Validator validator = schema.newValidator();
                validator.validate(xmlFile);
                zwrot[0] = Boolean.TRUE;
                zwrot[1] = "Plik prawidłowy";
                System.out.println("Plik jest prawidłowy");
            } catch (SAXException e) {
                zwrot[0] = Boolean.FALSE;
                zwrot[1] = obsluzblad(e);
                //System.out.println(obsluzblad(e));
            } catch (Exception e) {
                zwrot[0] = Boolean.FALSE;
                zwrot[1] = "Błąd walidacji pliku. Sprawdzanie przerwane";
            }
        } catch (Exception ex) {
            E.e(ex);
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                Logger.getLogger(XMLValid.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return zwrot;
    }
    
    public static Object[] walidujsprawozdanie() {
        Object[] zwrot = new Object[2];
        zwrot[0] = Boolean.FALSE;
        InputStream stream = null;
        try {
            File schemaFile = null;
        try {
            schemaFile = new File(schemasprfin);
        } catch (Exception ex) {
            Logger.getLogger(XMLValid.class.getName()).log(Level.SEVERE, null, ex);
        }
        //webapp example xsd:
        //URL schemaFile = new URL("http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd");
        //local file example:
            //File schemaFile = new File(realPath); // etc.
            String data = null;
            try {
                FileInputStream fis = new FileInputStream("d:\\sprawozdaniefinansowe8511005008mcrok012019.xml");
                data = IOUtils.toString(fis, "UTF-8");
                int czyjestpodpis = data.indexOf("<Signature");
                if (czyjestpodpis > 0) {
                    data = data.substring(0, data.indexOf("<Signature")) + data.substring(data.indexOf("</Signature>") + 12);
                    //System.out.println(data);
                }
            } catch (Exception ex) {
                Logger.getLogger(XMLValid.class.getName()).log(Level.SEVERE, null, ex);
            }
            stream = new ByteArrayInputStream(data.getBytes("UTF-8"));
            //Source xmlFile = new StreamSource(new File("d:\\vatue4.xml"));
            Source xmlFile = new StreamSource(stream);
            SchemaFactory schemaFactory = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            try {
                Schema schema = schemaFactory.newSchema(schemaFile);
                Validator validator = schema.newValidator();
                validator.validate(xmlFile);
                zwrot[0] = Boolean.TRUE;
                zwrot[1] = "Plik prawidłowy";
                System.out.println("Plik jest prawidłowy");
            } catch (SAXException e) {
                zwrot[0] = Boolean.FALSE;
                zwrot[1] = obsluzblad(e);
                //System.out.println(obsluzblad(e));
                zwrot[1] = "Błąd walidacji pliku. Brak info o szczegolach";
            } catch (Exception e) {
                zwrot[0] = Boolean.FALSE;
                zwrot[1] = "Błąd walidacji pliku. Sprawdzanie przerwane";
            }
        } catch (Exception ex) {
            E.e(ex);
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                Logger.getLogger(XMLValid.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Koniec walidacji bezbledna");
        return zwrot;
    }
    
    private static String obsluzblad(SAXException e) {
        String zwrot ="Bląd walidacji: ";
        String wiadomosc = rodzajbledu(e.getMessage());
        return zwrot+wiadomosc;
    }
    
    private static String rodzajbledu(String message) {
        String zwrot = "nie znany rodzaj błędu";
        String polenip = "";
        try {
            polenip = message.substring(message.indexOf("Value")+6, message.indexOf(" is not facet-valid"));
        } catch (Exception e){}
        if (message.contains("TNrVatUE")) {
            zwrot = "Błędny numer VAT kontrahenta "+polenip; 
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

    
    public static void main(String[] args) {
        walidujsprawozdanie();
    }

    
}
