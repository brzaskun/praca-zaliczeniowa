/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dedra;

import embeddable.EVatViewPola;
import entity.Evewidencja;
import entity.Ewidencjevat;
import entity.Podatnik;
import entityfk.EVatwpisDedra;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
        List<EVatwpisDedra> wierszeewidencji = new ArrayList<>();
         try {
            File fXmlFile = new File(sciezka);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            pobierzfaktury(doc, wierszeewidencji, wpisView, podatnik, ewidencja);
            pobierzkorekty(doc, wierszeewidencji, wpisView, podatnik, ewidencja);
            System.out.println("");
        } catch (Exception ex) {
            Logger.getLogger(Dedraparser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wierszeewidencji;
    }
    
    private static void pobierzfaktury(Document doc, List<EVatwpisDedra> wierszeewidencji, WpisView wpisView, Podatnik podatnik, Evewidencja ewidencja) {
        NodeList nList = doc.getElementsByTagName("A1");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                EVatwpisDedra p = new EVatwpisDedra();
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    p.setFaktura(eElement.getAttribute("F"));
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
    }
    
    private static void pobierzkorekty(Document doc, List<EVatwpisDedra> wierszeewidencji, WpisView wpisView, Podatnik podatnik, Evewidencja ewidencja) {
        NodeList nList = doc.getElementsByTagName("C1");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                EVatwpisDedra p = new EVatwpisDedra();
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    p.setFaktura(eElement.getAttribute("FO"));
                    p.setDatadokumentu(eElement.getAttribute(""));
                    p.setDataoperacji(eElement.getAttribute(""));
                    double netto = Double.parseDouble(eElement.getAttribute("ZR"));
                    p.setNetto(netto);
                    p.setNettowwalucie(netto);
                    double vat = Double.parseDouble(eElement.getAttribute("DR"));
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
    }
    
    public static void main(String[] args) throws SAXException, IOException {
        try {
            File fXmlFile = new File("E:\\Dropbox\\Alesz\\VAT052016\\DPH52016.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("A1");
            System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                EVatViewPola p = new EVatViewPola();
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("id: " + eElement.getAttribute("F"));
                    System.out.println("data: " + eElement.getAttribute("Den"));
                    System.out.println("netto: " + eElement.getAttribute("Z"));
                    System.out.println("vat: " + eElement.getAttribute("D"));
                    System.out.println("imie_i_nazwisko: " + eElement.getAttribute("N"));
                    System.out.println("ulica: " + eElement.getAttribute("A"));
                    System.out.println("miasto: " + eElement.getAttribute("M"));
                    System.out.println("stawka: " + eElement.getAttribute("S"));
                }
            }
            System.out.println("");
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Dedraparser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
