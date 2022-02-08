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
import java.io.IOException;
import java.io.InputStream;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import org.apache.commons.io.IOUtils;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
/**
 *
 * @author Osito
 */
public class XMLValid {
    private static String schemaVATUE4 = "resources\\schema\\vatue4schemat.xsd";
    private static String schemaVATUE5 = "resources\\schema\\vatue5schemat.xsd";
    private static String schemaVATUEK4 = "resources\\schema\\vatuek4schemat.xsd";
    private static String schemaVATUEK5 = "resources\\schema\\vatuek5schemat.xsd";
    private static String schemaJPKKR = "resources\\schema\\jpkkrschemat.xsd";
    private static String schemaIntrastat = "resources\\schema\\intratstat.xsd";
    private static String schemaVATUE4l = "d:\\vatue4schemat.xsd";
    private static String schemasprfin = "d:\\schemat.xsd";
    private static String deklaracja = "d:\\dekl.xml";
    private static String deklaracjaschema = "d:\\schemat.xsd";
    
    
    public static Object[] walidujCMLVATUE(String deklaracja, Object deklaracja_object, int podst0korekta1) {
        Object[] zwrot = new Object[2];
        zwrot[0] = Boolean.FALSE;
        InputStream stream = null;
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = ctx.getRealPath("/")+schemaVATUE4;
        if (deklaracja_object.getClass().equals(deklaracje.vatue.m4.Deklaracja.class)) {
            realPath = ctx.getRealPath("/")+schemaVATUE4;
        } else if (deklaracja_object.getClass().equals(deklaracje.vatuek.m4.Deklaracja.class)) {
                realPath = ctx.getRealPath("/")+schemaVATUEK4;
        } else if (deklaracja_object.getClass().equals(pl.gov.crd.wzor._2020._07._03._9690.Deklaracja.class)) {
            realPath = ctx.getRealPath("/")+schemaVATUE5;
         } else if (deklaracja_object.getClass().equals(pl.gov.crd.wzor._2020._07._03._9689.Deklaracja.class)) {
                realPath = ctx.getRealPath("/")+schemaVATUEK5;
        }
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
                    //error.E.s(data);
                }
            } catch (Exception ex) {
                // Logger.getLogger(XMLValid.class.getName()).log(Level.SEVERE, null, ex);
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
                error.E.s("Plik jest prawidłowy");
            } catch (SAXException e) {
                zwrot[0] = Boolean.FALSE;
                zwrot[1] = obsluzblad(e);
                //error.E.s(obsluzblad(e));
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
                // Logger.getLogger(XMLValid.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return zwrot;
    }
    
    public static Object[] walidujJPK2020View(String mainfilename, int coweryfikowac, String wersjaschemy) {
            Object[] zwrot = new Object[2];
            zwrot[0] = Boolean.FALSE;
            error.E.s("start walidacji");
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/")+"resources\\xml\\JPK2020M.xsd";
            if (wersjaschemy.equals("1-2E")) {
                realPath = ctx.getRealPath("/")+"resources\\xml\\JPK2020M.xsd";
                if (coweryfikowac==1) {
                    realPath = ctx.getRealPath("/")+"resources\\xml\\JPK2020K.xsd";
                }
            }
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
                zwrot[1] = obsluzblad2(e);
                error.E.s(obsluzblad2(e));
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
    
    public static Object[] walidujJPK2022View(String mainfilename, int coweryfikowac, String wersjaschemy) {
            Object[] zwrot = new Object[2];
            zwrot[0] = Boolean.FALSE;
            error.E.s("start walidacji");
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/")+"resources\\xml\\JPK2022M.xsd";
            if (wersjaschemy.equals("1-2E")) {
                realPath = ctx.getRealPath("/")+"resources\\xml\\JPK2022M.xsd";
                if (coweryfikowac==1) {
                    realPath = ctx.getRealPath("/")+"resources\\xml\\JPK2022K.xsd";
                }
            }
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
                zwrot[1] = obsluzblad2(e);
                error.E.s(obsluzblad2(e));
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
    
    
     public static Object[] walidujJPKKR(String mainfilename) {
            Object[] zwrot = new Object[2];
            zwrot[0] = Boolean.FALSE;
            error.E.s("start walidacji");
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/")+schemaJPKKR;
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
                zwrot[1] = obsluzblad2(e);
                error.E.s(obsluzblad2(e));
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
     
     public static Object[] walidujIntrastat(String mainfilename) {
            Object[] zwrot = new Object[2];
            zwrot[0] = Boolean.FALSE;
            error.E.s("start walidacji");
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/")+schemaIntrastat;
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
                zwrot[1] = obsluzblad2(e);
                error.E.s(obsluzblad2(e));
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
    
               
    

    public static Object[] walidujsprawozdanieView(InputStream inputStream, int coweryfikowac, String wersjaschemy) {
            Object[] zwrot = new Object[2];
            zwrot[0] = Boolean.FALSE;
            error.E.s("start walidacji");
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/")+"resources\\xml\\schematsf.xsd";
            if (wersjaschemy.equals("1-0")) {
                realPath = ctx.getRealPath("/")+"resources\\xml\\schematsf.xsd";
                if (coweryfikowac==1) {
                    realPath = ctx.getRealPath("/")+"resources\\xml\\schematoop.xsd";
                }
            } else if (wersjaschemy.equals("1-2")) {
                realPath = ctx.getRealPath("/")+"resources\\xml\\schematsf_12.xsd";
                if (coweryfikowac==1) {
                    realPath = ctx.getRealPath("/")+"resources\\xml\\schematoop_12.xsd";
                }
            }
            try {
            File schemaFile = null;
            try {
                schemaFile = new File(realPath);
            } catch (Exception ex) {
                // Logger.getLogger(XMLValid.class.getName()).log(Level.SEVERE, null, ex);
            }
            Source xmlFile = new StreamSource(inputStream);
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
            
            
    public static Object[] walidujsprawozdanie() {
        Object[] zwrot = new Object[2];
        zwrot[0] = Boolean.FALSE;
        InputStream stream = null;
        try {
            File schemaFile = null;
        try {
            schemaFile = new File(schemasprfin);
        } catch (Exception ex) {
            // Logger.getLogger(XMLValid.class.getName()).log(Level.SEVERE, null, ex);
        }
        //webapp example xsd:
        //URL schemaFile = new URL("http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd");
        //local file example:
            //File schemaFile = new File(realPath); // etc.
            String data = null;
            error.E.s("start walidacji");
            try {
                FileInputStream fis = new FileInputStream("d:\\spr.xml");
                data = IOUtils.toString(fis, "UTF-8");
                int czyjestpodpis = data.indexOf("<Signature");
                if (czyjestpodpis > 0) {
                    data = data.substring(0, data.indexOf("<Signature")) + data.substring(data.indexOf("</Signature>") + 12);
                    //error.E.s(data);
                }
            } catch (Exception ex) {
                // Logger.getLogger(XMLValid.class.getName()).log(Level.SEVERE, null, ex);
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
                error.E.s("Plik jest prawidłowy");
                error.E.s("Koniec walidacji bezbledna");
            } catch (SAXException e) {
                zwrot[0] = Boolean.FALSE;
                zwrot[1] = obsluzblad2(e);
                error.E.s(obsluzblad2(e));
            } catch (Exception e) {
                zwrot[0] = Boolean.FALSE;
                zwrot[1] = "Błąd walidacji pliku. Sprawdzanie przerwane";
            }
        } catch (Exception ex) {
            E.e(ex);
            error.E.s("Błąd ładowania plików do walidacji. Sprawdzanie przerwane");
        } finally {
            try {
                stream.close();
                error.E.s("Zamykam stream");
            } catch (IOException ex) {
                // Logger.getLogger(XMLValid.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return zwrot;
    }
    
    public static Object[] walidujVAT7() {
        Object[] zwrot = new Object[2];
        zwrot[0] = Boolean.FALSE;
        InputStream stream = null;
        try {
            File schemaFile = null;
        try {
            schemaFile = new File(deklaracjaschema);
        } catch (Exception ex) {
            // Logger.getLogger(XMLValid.class.getName()).log(Level.SEVERE, null, ex);
        }
        //webapp example xsd:
        //URL schemaFile = new URL("http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd");
        //local file example:
            //File schemaFile = new File(realPath); // etc.
            String data = null;
            error.E.s("start walidacji");
            try {
                FileInputStream fis = new FileInputStream(deklaracja);
                data = IOUtils.toString(fis, "UTF-8");
                int czyjestpodpis = data.indexOf("<Signature");
                if (czyjestpodpis > 0) {
                    data = data.substring(0, data.indexOf("<Signature")) + data.substring(data.indexOf("</Signature>") + 12);
                    //error.E.s(data);
                }
            } catch (Exception ex) {
                // Logger.getLogger(XMLValid.class.getName()).log(Level.SEVERE, null, ex);
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
        } finally {
            try {
                stream.close();
                error.E.s("Zamykam stream");
            } catch (IOException ex) {
                // Logger.getLogger(XMLValid.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return zwrot;
    }
    
    private static String obsluzblad(SAXException e) {
        String kolumna = "";
        String wiersz = "";
        String ostrzezenie ="Bląd walidacji: ";
        String wiadomosc = rodzajbledu(e.getMessage());
        String zwrot = ostrzezenie+wiadomosc;
        if (e.getClass().getName().equals("org.xml.sax.SAXParseException")) {
            kolumna = "kol. "+((SAXParseException)e).getColumnNumber()+";";
            wiersz = "wiersz "+((SAXParseException)e).getLineNumber()+";";
            zwrot = kolumna+wiersz+wiadomosc;
            
        }
        return zwrot;
    }
    private static String obsluzblad2(SAXException e) {
        String kolumna = "";
        String wiersz = "";
        String ostrzezenie ="Bląd walidacji: ";
        String wiadomosc = rodzajbledu2(e.getMessage());
        String zwrot = ostrzezenie+wiadomosc;
        if (e.getClass().getName().equals("org.xml.sax.SAXParseException")) {
            kolumna = "kol. "+((SAXParseException)e).getColumnNumber()+";";
            wiersz = "wiersz "+((SAXParseException)e).getLineNumber()+";";
            zwrot = kolumna+wiersz+wiadomosc;
            
        }
        return zwrot;
    }
     private static String rodzajbledu2(String message) {
        String zwrot = message;
        String polenip = "";
        if (message.contains("Email}' is expected")) {
            zwrot = "Brak adresu email"; 
        } else if (message.contains("not facet-valid with respect to pattern '(.)+@(.)+' for type 'TAdresEmail'")) {
            zwrot = "Zły adres email";
        } else if (message.contains("facet-valid with respect to pattern '((\\d{4})-(\\d{2})-(\\d{2}))' for type 'TData'")) {
            zwrot = "Błęda data w wierszu";
        } else if (message.contains("Invalid content was found starting with element 'P_")) {
            message = message.replace("cvc-complex-type.2.4.a: Invalid content was found starting with element ", "");
            message = message.replace("http://crd.gov.pl/wzor/2020/05/08/9393/", "");
            message = message.replace("cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to minLength '1' for type","Puste pole, trzeba wpisać wartość");
            message = message.replace("One of", "Brakuje pól");
            message = message.replace("is expected", "");
            zwrot = "Deklaracja. Błąd od pola "+message;
        } else {
            zwrot = message;
        }
        return zwrot;
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
 public static Object[] walidujJPK2020() {
     Object[] zwrot = new Object[2];
        zwrot[0] = Boolean.FALSE;
        InputStream stream = null;
        File schemaFile = null;
        try {
            schemaFile = new File("d:\\JPK2020K.xsd");
        } catch (Exception ex) {
            // Logger.getLogger(XMLValid.class.getName()).log(Level.SEVERE, null, ex);
        }
        //webapp example xsd:
        //URL schemaFile = new URL("http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd");
        //local file example:
            //File schemaFile = new File(realPath); // etc.
            String data = null;
            error.E.s("start walidacji");
            try {
                FileInputStream fis = new FileInputStream("d:\\jpk.xml");
                data = IOUtils.toString(fis, "UTF-8");
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
    
    public static void main(String[] args) {
        Object[] zwrot = new Object[2];
        zwrot[0] = Boolean.FALSE;
        InputStream stream = null;
        File schemaFile = null;
        try {
            schemaFile = new File("d:\\intratstat.xsd");
        } catch (Exception ex) {
            // Logger.getLogger(XMLValid.class.getName()).log(Level.SEVERE, null, ex);
        }
        //webapp example xsd:
        //URL schemaFile = new URL("http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd");
        //local file example:
            //File schemaFile = new File(realPath); // etc.
            String data = null;
            error.E.s("start walidacji");
            try {
                FileInputStream fis = new FileInputStream("d:\\int11.xml");
                data = IOUtils.toString(fis, "UTF-8");
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

    }

    
}
