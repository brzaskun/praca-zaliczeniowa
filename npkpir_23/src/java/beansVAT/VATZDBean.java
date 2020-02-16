/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansVAT;

import data.Data;
import static deklaracje.vat272.VAT27Bean.StringToDocument;
import deklaracje.vatzd.ObjectFactory;
import deklaracje.vatzd.TNaglowekVATZD;
import deklaracje.vatzd.WniosekVATZD;
import entity.Dok;
import entity.DokSuper;
import entity.VATZDpozycja;
import entityfk.Dokfk;
import error.E;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class VATZDBean {
    
    public static WniosekVATZD createVATZD(List dok) {
        ObjectFactory of = new ObjectFactory();
        WniosekVATZD vatzd = of.createWniosekVATZD();
        TNaglowekVATZD tn = of.createTNaglowekVATZD();
        vatzd.setNaglowek(tn);
        WniosekVATZD.PozycjeSzczegolowe ps = of.createWniosekVATZDPozycjeSzczegolowe();
        double sumanetto = 0.0;
        double sumavat = 0.0;
        for (Object apsik : dok) {
            VATZDpozycja poz = (VATZDpozycja) apsik;
            DokSuper d = poz.getDokfk() !=null ? (DokSuper)poz.getDokfk() : (DokSuper)poz.getDok();
            String nrfaktury = d.getClass().equals(Dok.class) ? ((Dok) d).getNrWlDk() : ((Dokfk) d).getNumerwlasnydokfk();
            String[] datawyst = d.getClass().equals(Dok.class) ? Data.getSplitted(((Dok) d).getDataWyst()) : Data.getSplitted(((Dokfk) d).getDatadokumentu());
            String[] terminpl = Data.getSplitted(d.getTerminPlatnosci());
            double netto = d.getClass().equals(Dok.class) ? ((Dok) d).getNetto() : ((Dokfk) d).getNettoVAT();
            double vat = d.getClass().equals(Dok.class) ? ((Dok) d).getVat() : ((Dokfk) d).getVATVAT();
            WniosekVATZD.PozycjeSzczegolowe.PB pb = of.createWniosekVATZDPozycjeSzczegolowePB(d.getKontr().getNpelna(),d.getKontr().getNip(), nrfaktury, data(datawyst), data(terminpl), new BigDecimal(Z.z(netto)).setScale(2, RoundingMode.HALF_EVEN), new BigDecimal(Z.z(vat)).setScale(2, RoundingMode.HALF_EVEN));
            ps.getPB().add(pb);
            sumanetto += Z.z(netto);
            sumavat += Z.z(vat);
        }
        ps.setP10(new BigDecimal(Z.z(sumanetto)).setScale(0, RoundingMode.HALF_EVEN).toBigInteger());
        ps.setP11(new BigDecimal(Z.z(sumavat)).setScale(0, RoundingMode.HALF_EVEN).toBigInteger());
        vatzd.setPozycjeSzczegolowe(ps);
        //marszajuldoplikuxml(vatzd);
        return vatzd;
    }
    
    public static XMLGregorianCalendar data(String[] data) {
         XMLGregorianCalendar newXMLGregorianCalendar = null;
        try {
            newXMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
            newXMLGregorianCalendar.setYear(Integer.parseInt(data[0]));
            newXMLGregorianCalendar.setMonth(Integer.parseInt(data[1]));
            newXMLGregorianCalendar.setDay(Integer.parseInt(data[2]));
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
        String zwrot = sw.toString().replaceAll("\n|\r", "").substring(sw.toString().replaceAll("\n|\r", "").indexOf(">")+1);
        return zwrot;
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
        //WniosekVATZD wn = createVATZD();
        //marszajuldoplikuxml(wn);
        //System.out.println(marszajuldoStringu(wn));
    }
}
