/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import dedra.Dedraparser;
import error.E;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
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

/**
 *
 * @author Osito
 */

public class InterpaperBankSantander implements Serializable {
    private static final long serialVersionUID = 1L;
        
    
    public static void importujdok(byte[] pobrane, String mcwpisu) {
        try {
            InputStream file = new ByteArrayInputStream(pobrane);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            ImportowanyPlikNaglowek pn = new ImportowanyPlikNaglowek();
            try {
                NodeList nList1 = doc.getElementsByTagName("Stmt");
                pn.setWyciagnr(pT(nList1.item(0), "Id"));
                pn.setWyciagdataod(pT(nList1.item(0), "FrDtTm"));
                String wyciagdatado = pT(nList1.item(0), "ToDtTm");
                if (wyciagdatado!=null) {
                    pn.setWyciagnrdo(wyciagdatado.substring(0, 10));
                }
                pn.setWyciagkonto(pT(nList1.item(0), "IBAN"));
                pn.setWyciagwaluta(pT(nList1.item(0), "Ccy"));
                pn.setWyciagbo(Double.valueOf(pT(nList1.item(0), "Amt", 0)));
                pn.setWyciagbz(Double.valueOf(pT(nList1.item(0), "Amt", 1)));
            } catch (Exception e){
                E.e(e);
            }
            NodeList nList = doc.getElementsByTagName("Ntry");
            List<ImportBankWiersz> pobranefaktury = new ArrayList<>();
            error.E.s("----------------------------");
            int len = nList.getLength();
                for (int temp = 0; temp < len; temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        try {
                            ImportBankWiersz p = new ImportBankWiersz();
                            p.setNr(temp+1);
                            String iban = pE(nNode, "Id") == null ? "brak" : pTFC(nNode, "Id");
                            p.setIBAN(iban);
                            String elt1 = pT(nNode, "Amt");
                            p.setKwota(Double.valueOf(elt1));
                            elt1 = pE(nNode, "Amt").getAttribute("Ccy");
                            p.setWaluta(elt1);
                            elt1 = pT(nNode, "CdtDbtInd");
                            p.setWnma(elt1.equals("DBIT")?"Ma":"Wn");
                            elt1 = pTFC(nNode, "BookgDt");
                            p.setDatatransakcji(elt1.substring(0,10));
                            elt1 = pTFC(nNode, "ValDt");
                            p.setDatawaluty(elt1.substring(0,10));
                            elt1 = pT(nNode, "Ustrd");
                            p.setOpistransakcji(elt1);
                            elt1 = pE(nNode, "Nm") == null ? "brak" : pT(nNode, "Nm");
                            p.setKontrahent(elt1);
                            elt1 = pE(nNode, "Ctry") == null ? "brak" : pT(nNode, "Ctry");
                            p.setKontrahentakraj(elt1);
                            elt1 = pE(nNode, "AdrLine") == null ? "brak" : pT(nNode, "AdrLine");
                            p.setKontrahentaadres(elt1);
                            elt1 = pT(nNode, "TxId");
                            p.setNrtransakji(elt1);
                            p.setTyptransakcji(oblicztyptransakcji(p));
                            pobranefaktury.add(p);
                        } catch (Exception e) {
                            E.e(e);
                        }
                    }
                }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    
    
     //typ transakcji
        //1 wpływ faktura 201,203
        //2 zapłata faktura 202,204
        //3 prowizja - 404-3
        //4 wypłata karta - 149-1
        //5 płatnośc karta - 149-3
        //6 US - 222
        //7 ZUS - 231
        //8 Gmina - 226
        //9 bank-bank - 149-2
    private static int oblicztyptransakcji(ImportBankWiersz p) {
        int zwrot = 0;
        if (p.getKontrahent().equals("NOTPROVIDED")) {
            zwrot = 3;
        } else if (p.getKontrahent().toLowerCase().contains("INTERPAPER SP Z O O SK")) {
            zwrot = 8;
        } else if (p.getKontrahent().toLowerCase().contains("Gmina")) {
            zwrot = 8;
        } else if (p.getKontrahent().toLowerCase().contains("Zakład Ubezpieczeń Społecznych")) {
            zwrot = 7;
        } else if (p.getKontrahent().toLowerCase().contains("urząd")) {
            zwrot = 6;
        } else if (p.getOpistransakcji().equals("WYPŁATA KARTĄ")) {
            zwrot = 4;
        } else if (p.getOpistransakcji().equals("TRANSAKCJA KARTĄ PŁATNICZĄ")) {
            zwrot = 5;
        } else if (p.getWnma().equals("Wn")) {
            zwrot = 1;
        } else if (p.getWnma().equals("Ma")) {
            zwrot = 2;
        }
        return zwrot;
    }
    
    
 
 public static Element pE(Node nNode1, String nazwa) {
     Element eElement = (Element) nNode1;
     return (Element) eElement.getElementsByTagName(nazwa).item(0);
 }
 
 public static String pT(Node nNode1, String nazwa) {
     Element eElement = (Element) nNode1;
     return ((Element) eElement.getElementsByTagName(nazwa).item(0)).getTextContent();
 }
 public static String pT(Node nNode1, String nazwa, int pozycja) {
     Element eElement = (Element) nNode1;
     return ((Element) eElement.getElementsByTagName(nazwa).item(pozycja)).getTextContent();
 }
 
 public static String pTFC(Node nNode1, String nazwa) {
     Element eElement = (Element) nNode1;
     return ((Element) eElement.getElementsByTagName(nazwa).item(0)).getFirstChild().getNextSibling().getTextContent();
 }
    
public static void main(String[] args) throws SAXException, IOException {
        try {
            File fXmlFile = new File("e:\\wgo.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            error.E.s("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList1 = doc.getElementsByTagName("account");
            String iban = pT(nList1.item(0), "iban");
            String waluta = pT(nList1.item(0), "currency");
            nList1 = doc.getElementsByTagName("stmt");
            String nrwyciagu = pT(nList1.item(0), "stmt-no");;
            String dataod = pT(nList1.item(0), "begin");
            String datado = pT(nList1.item(0), "end");
            String bo = pT(nList1.item(0), "begin-value");
            String bz = pT(nList1.item(0), "end-value");
            error.E.s("----------------------------");
            nList1 = doc.getElementsByTagName("trn");
            error.E.s("----------------------------");
            int len = nList1.getLength();
                for (int temp = 0; temp < len; temp++) {
                    Node nNode1 = nList1.item(temp);
                    if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
                        try {
                            Element eElement = (Element) nNode1;
                            String elt = pT(nNode1, "trn-code") == null ? "brak" : pT(nNode1, "trn-code");
                            error.E.s("trn-code : " + elt);
                            String elt1 = pT(nNode1, "value");
                            error.E.s("value : " + elt1);
                            error.E.s("data : " + pT(nNode1, "creat-date"));
                            error.E.s("data waluty : " + pT(nNode1, "exe-date"));
                            error.E.s("opis : " + pT(nNode1, "desc-base"));
                            String elt3 = pE(nNode1, "desc-opt") == null ? "brak" : pT(nNode1, "desc-opt");
                            error.E.s("odbiorca : " + elt3);
                        } catch (Exception e) {
                            E.e(e);
                        }
                    }
                }
        } catch (ParserConfigurationException ex) {
            // Logger.getLogger(Dedraparser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   

    

    

   
    

    
   
    
   
    
}
