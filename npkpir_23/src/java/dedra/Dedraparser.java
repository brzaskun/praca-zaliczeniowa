/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dedra;


import daoFK.EVatwpisDedraDAO;
import entity.EVatwpisSuper;
import entity.Evewidencja;
import entity.Podatnik;
import entityfk.EVatwpisDedra;
import error.E;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import msg.Msg;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class Dedraparser {
    
    
    public static List<EVatwpisDedra> parsujewidencje(String sciezka, Podatnik podatnik, Evewidencja ewidencja, WpisView wpisView) {
        List<EVatwpisDedra> wierszeewidencji = Collections.synchronizedList(new ArrayList<>());
        String bladfakt = null;
        String bladkorekta = null;
         try {
            File fXmlFile = new File(sciezka);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            error.E.s("Root element :" + doc.getDocumentElement().getNodeName());
            bladfakt = pobierzfaktury(doc, wierszeewidencji, wpisView, podatnik, ewidencja);
            if (bladfakt!=null) {
                Msg.msg("e", "Wystąpił błąd przy importowaniu faktur. Sprawdź nazwy pól. Błąd wystąpił w  "+bladfakt);
            } else {
                Msg.msg("Zaimportowano faktury bez błędów");
            }
            bladkorekta = pobierzkorekty(doc, wierszeewidencji, wpisView, podatnik, ewidencja);
            if (bladkorekta!=null) {
                Msg.msg("e", "Wystąpił błąd przy importowaniu korekt. Sprawdz nazwy pól. Błąd wystąpił w "+bladkorekta);
            } else {
                Msg.msg("Zaimportowano korekty bez błędów");
            }
        } catch (Exception ex) {
             System.out.println(E.e(ex));
              Msg.msg("e", "Wystąpił błąd przy importowaniu pliku xml. Nie wiem gdzie");
            // Logger.getLogger(Dedraparser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wierszeewidencji;
    }
    
    private static String pobierzfaktury(Document doc, List<EVatwpisDedra> wierszeewidencji, WpisView wpisView, Podatnik podatnik, Evewidencja ewidencja) {
        NodeList nList = doc.getElementsByTagName("A1");
        String zwrot = null;
        String ostatnia = null;
        try {
            for (int temp = 0; temp < nList.getLength(); temp++) {
                EVatwpisDedra p = new EVatwpisDedra();
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    p.setFaktura(eElement.getAttribute("F"));
                    ostatnia = eElement.getAttribute("F");
                    if (ostatnia.equals("5010000134")){
                        System.out.println("");
                    }
                    p.setDatadokumentu(eElement.getAttribute("Den"));
                    p.setDataoperacji(eElement.getAttribute("Den"));
                    double netto = Double.parseDouble(eElement.getAttribute("Z"));
                    p.setNetto(netto);
                    p.setNettowwalucie(netto);
                    double vat = Double.parseDouble(eElement.getAttribute("D"));
                    p.setVat(vat);
                    p.setVatwwalucie(vat);
                    p.setBrutto(Z.z(netto+vat));
                    p.setImienazwisko(eElement.getAttribute("N"));
                    p.setUlica(eElement.getAttribute("A"));
                    p.setMiasto(eElement.getAttribute("M"));
                    p.setEstawka(eElement.getAttribute("S"));
                    p.setMcEw(wpisView.getMiesiacWpisu());
                    p.setRokEw(wpisView.getRokWpisuSt());
                    p.setEwidencja(ewidencja);
                    p.setPodatnikObj(podatnik);
                    wierszeewidencji.add(p);
                }
            }
        } catch (Exception ex) {
            zwrot = ostatnia;
        }
        return zwrot;
    }
    
    private static String pobierzkorekty(Document doc, List<EVatwpisDedra> wierszeewidencji, WpisView wpisView, Podatnik podatnik, Evewidencja ewidencja) {
        NodeList nList = doc.getElementsByTagName("C1");
        String zwrot = null;
        String ostatnia = null;
        try {
            for (int temp = 0; temp < nList.getLength(); temp++) {
                EVatwpisDedra p = new EVatwpisDedra();
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    p.setFaktura(eElement.getAttribute("FO"));
                    ostatnia = eElement.getAttribute("FO");
                    p.setDatadokumentu(eElement.getAttribute("Den"));
                    p.setDataoperacji(eElement.getAttribute("Den"));
                    double netto = Double.parseDouble(eElement.getAttribute("ZR"));
                    p.setNetto(netto);
                    p.setNettowwalucie(netto);
                    double vat = Double.parseDouble(eElement.getAttribute("DR"));
                    p.setVat(vat);
                    p.setVatwwalucie(vat);
                    p.setBrutto(Z.z(netto + vat));
                    p.setImienazwisko(eElement.getAttribute("N"));
                    p.setUlica(eElement.getAttribute("A"));
                    p.setMiasto(eElement.getAttribute("M"));
                    p.setEstawka(eElement.getAttribute("S"));
                    p.setMcEw(wpisView.getMiesiacWpisu());
                    p.setRokEw(wpisView.getRokWpisuSt());
                    p.setEwidencja(ewidencja);
                    p.setPodatnikObj(podatnik);
                    wierszeewidencji.add(p);
                }
            }
        } catch (Exception ex) {
            zwrot = ostatnia;
        }

        return zwrot;
    }
    
    public static EVatwpisDedra dodajwierszzakupu(EVatwpisDedra p, WpisView wpisView, Podatnik podatnik, Evewidencja ewidencja, EVatwpisDedraDAO eVatwpisDedraDAO) {
            p.setNettowwalucie(p.getNetto());
            p.setVatwwalucie(p.getVat());
            p.setBrutto(Z.z(p.getNetto()+p.getVat()));
            p.setImienazwisko(p.getKlient().getNpelna());
            p.setUlica(p.getKlient().getUlica());
            p.setMiasto(p.getKlient().getMiejscowosc());
            p.setMcEw(wpisView.getMiesiacWpisu());
            p.setRokEw(wpisView.getRokWpisuSt());
            p.setEwidencja(ewidencja);
            p.setPodatnikObj(podatnik);
            eVatwpisDedraDAO.dodaj(p);
            return p;
    }
    
    public static void main(String[] args) throws SAXException, IOException {
        try {
            File fXmlFile = new File("E:\\Dropbox\\Alesz\\VAT052016\\DPH52016.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            error.E.s("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("A1");
            error.E.s("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                EVatwpisSuper p = new EVatwpisSuper();
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    error.E.s("id: " + eElement.getAttribute("F"));
                    error.E.s("data: " + eElement.getAttribute("Den"));
                    error.E.s("netto: " + eElement.getAttribute("Z"));
                    error.E.s("vat: " + eElement.getAttribute("D"));
                    error.E.s("imie_i_nazwisko: " + eElement.getAttribute("N"));
                    error.E.s("ulica: " + eElement.getAttribute("A"));
                    error.E.s("miasto: " + eElement.getAttribute("M"));
                }
            }
        } catch (ParserConfigurationException ex) {
            // Logger.getLogger(Dedraparser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
