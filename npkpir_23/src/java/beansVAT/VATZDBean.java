/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansVAT;

import data.Data;
import deklaracje.vat272.Deklaracja;
import static deklaracje.vat272.VAT27Bean.StringToDocument;
import deklaracje.vatzd.ObjectFactory;
import deklaracje.vatzd.TKodFormularzaVATZD;
import deklaracje.vatzd.TNaglowekVATZD;
import deklaracje.vatzd.WniosekVATZD;
import error.E;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import static org.jsoup.nodes.Document.OutputSettings.Syntax.xml;
import org.w3c.dom.Document;
import view.WpisView;

/**
 *
 * @author Osito
 */
public class VATZDBean {
    public static WniosekVATZD createVATZD() {
        ObjectFactory of = new ObjectFactory();
        WniosekVATZD vatzd = of.createWniosekVATZD();
        TNaglowekVATZD tn = of.createTNaglowekVATZD();
        TNaglowekVATZD.KodFormularza kf = of.createTNaglowekVATZDKodFormularza();
        kf.setWersjaSchemy(kf.getWersjaSchemy());
        kf.setKodSystemowy(kf.getKodSystemowy());
        kf.setValue(TKodFormularzaVATZD.VAT_ZD);
        tn.setKodFormularza(kf);
        tn.setWariantFormularza((byte)1);
        vatzd.setNaglowek(tn);
        WniosekVATZD.PozycjeSzczegolowe ps = of.createWniosekVATZDPozycjeSzczegolowe();
        WniosekVATZD.PozycjeSzczegolowe.PB pb = of.createWniosekVATZDPozycjeSzczegolowePB();
        pb.setPBB("nazwa podatnika");
        pb.setPBC("8511005008");
        pb.setPBD1("numer faktury");
        pb.setPBD2(data());
        pb.setPBE(data());
        pb.setPBF(BigDecimal.ONE);
        pb.setPBG(BigDecimal.ONE);
        pb.setTyp("G");
        ps.getPB().add(pb);
        ps.setP10(BigInteger.TEN);
        ps.setP11(BigInteger.ONE);
        vatzd.setPozycjeSzczegolowe(ps);
        return vatzd;
    }
    
    public static XMLGregorianCalendar data() {
         XMLGregorianCalendar newXMLGregorianCalendar = null;
        try {
            newXMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
            newXMLGregorianCalendar.setYear(Integer.parseInt("2018"));
            newXMLGregorianCalendar.setMonth(Integer.parseInt("05"));
            newXMLGregorianCalendar.setDay(Integer.parseInt("25"));
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newXMLGregorianCalendar;
    }
    
    public static String marszajuldoStringu(WniosekVATZD dekl) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(WniosekVATZD.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(dekl, new StreamResult(sw));
        } catch (Exception ex) {
            E.e(ex);
        }
        return sw.toString().replaceAll("\n|\r", "").substring(sw.toString().replaceAll("\n|\r", "").indexOf(">")+1);
    }
    
    public static void marszajuldoplikuxml(WniosekVATZD dekl) {
        try {
            JAXBContext context = JAXBContext.newInstance(WniosekVATZD.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            StringWriter sw = new StringWriter();
            marshaller.marshal(dekl, new StreamResult(sw));
            Document dokmt = StringToDocument(sw.toString());
            String plik = "d:\\testowa.xml";
            FileOutputStream fileStream = new FileOutputStream(new File(plik));
            OutputStreamWriter writer = new OutputStreamWriter(fileStream, "UTF-8");
            marshaller.marshal(dekl, writer);
        } catch (Exception ex) {
            E.e(ex);
        }
    }
     
    
    public static void main(String[] args) {
        WniosekVATZD wn = createVATZD();
        marszajuldoplikuxml(wn);
        //System.out.println(marszajuldoStringu(wn));
    }
}
