/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import dedra.Dedraparser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author Osito
 */

public class GCOBankImportView implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
//    public static void importujdok(byte[] pobrane, String mcwpisu) {
//        try {
//            InputStream file = new ByteArrayInputStream(pobrane);
//            List<Klienci> k = klienciDAO.findAll();
//            if (pobrane!=null && !pobrane.isEmpty()) {
//                int nrwyciagu = 0;
//                int j = 1;
//                for (byte[] plik : pobrane) {
//                    InputStream file = new ByteArrayInputStream(plik);
//                    List<List<String>> records = new ArrayList<>();
//                    try (BufferedReader br =  new BufferedReader(new InputStreamReader(file, Charset.forName("UTF-8")))) {
//                        String line;
//                        while ((line = br.readLine()) != null) {
//                            String[] values = line.split(";");
//                            records.add(Arrays.asList(values));
//                        }
//                    } catch (Exception e) {
//                    }
//                    int i = 0;
//                    for (Iterator<List<String>> it = records.iterator(); it.hasNext();) {
//                        List<String> baza = it.next();
//                        List<String> row = new ArrayList<>();
//                        if (i==0) {
//                            if (nrwyciagu==0) {
//                                wyciagnrod = baza.get(0);
//                                wyciagdataod = Data.zmienkolejnosc(baza.get(2));
//                            }
//                            wyciagdatado = Data.zmienkolejnosc(baza.get(1));
//                            wyciagkonto = baza.get(5);;
//                            wyciagwaluta = baza.get(6);
//                            wyciagbz = Double.parseDouble(baza.get(12).replace(",","."));
//                            wyciagobrotywn += !baza.get(9).equals("") ? Double.parseDouble(baza.get(9).replace(",",".")) : 0.0;
//                            wyciagobrotyma += !baza.get(10).equals("") ? Double.parseDouble(baza.get(10).replace(",",".")) : 0.0;
//                            wyciagnrdo = baza.get(0);
//                        } else if (i==1) {
//                            if (nrwyciagu==0) {
//                                wyciagbo = Double.parseDouble(baza.get(12).replace(",","."));
//                            }
//                        } else {
//                            ImportBankWiersz x = new ImportBankWiersz();
//                            x.setNr(j++);
//                            x.setDatatransakcji(Data.zmienkolejnosc(baza.get(1)));
//                            x.setDatawaluty(Data.zmienkolejnosc(baza.get(2)));
//                            x.setNrwyciagu(baza.get(0));
//                            x.setIBAN(baza.get(5));//??
//                            x.setKontrahent(baza.get(4));//??
//                            if (!baza.get(10).equals("")) {
//                                x.setKwota(Double.parseDouble(baza.get(10).replace(",",".")));
//                                x.setWnma("Ma");
//                            } else if (!baza.get(11).equals("")) {
//                                x.setKwota(Double.parseDouble(baza.get(11).replace(",",".")));
//                                x.setWnma("Wn");
//                            }
//                            x.setWaluta(wyciagwaluta);
//                            x.setNrtransakji(baza.get(8));
//                            x.setOpistransakcji(baza.get(3));
//                            x.setTyptransakcji(oblicztyptransakcji(x));
//                            pobranefaktury.add(x);
//                        }
//                        i++;
//                    }
//                    nrwyciagu++;
//                }
//            }
//        } catch (Exception e) {
//            Msg.msg("e", "Wystąpił błąd przy pobieraniu danych");
//        }
////        for (InterpaperXLS p : pobranefaktury) {
////           generowanieDokumentu(p);
////        }
//    }
    
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
        if (p.getNrtransakji().equals("OPŁATA/PROWIZJA")) {
            zwrot = 3;
        } else if (p.getNrtransakji().equals("PRZELEW ELIXIR - ONLINE") || p.getNrtransakji().equals("PRZELEW NA RACHUNEK W SAN PL - ONLINE")) {
            zwrot = 1;
        } else if (p.getNrtransakji().equals("UZNANIE") || p.getNrtransakji().equals("UZNANIE - PŁATNOŚĆ PODZIELONA")) {
            zwrot = 2;
        } else if (p.getKontrahent().toLowerCase().contains("INTERPAPER SP Z O O SK")) {
            zwrot = 8;
        } else if (p.getKontrahent().toLowerCase().contains("Gmina")) {
            zwrot = 8;
        } else if (p.getKontrahent().toLowerCase().contains("PRZELEW ELIXIR NA RACHUNEK ZUS - ONLINE")) {
            zwrot = 7;
        } else if (p.getKontrahent().toLowerCase().contains("PRZELEW ELIXIR NA RACH. ORGANU PODATK. - ONLINE")) {
            zwrot = 6;
        } else if (p.getNrtransakji().equals("WYPŁATA KARTĄ")) {
            zwrot = 4;
        } else if (p.getNrtransakji().contains("TRANSAKCJA KARTĄ ")) {
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
            Path pathToFile = Paths.get("D:\\mbank.csv");
            List<List<String>> records = new ArrayList<>();
            try (BufferedReader br =  Files.newBufferedReader(pathToFile)) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(";");
                    records.add(Arrays.asList(values));
                }
            } catch (Exception e) {
            }
            int i = 0;
            List<ImportBankWiersz> listaswierszy = new ArrayList<>();
            for (Iterator<List<String>> it = records.iterator(); it.hasNext();) {
                List<String> baza = it.next();
                List<String> row = new ArrayList<>();
//                for (String r : baza) {
//                    row.add(r.replace("\"", ""));
//                }
//                if (i==0) {
//                    String wyciagnr = baza.get(0);
//                    String wyciagdataod = baza.get(2);
//                    String wyciagdatado = baza.get(1);
//                    String wyciagkonto = baza.get(5);;
//                    String wyciagwaluta = baza.get(6);
//                    String wyciagbz = baza.get(12);
//                    String wyciagobrotywn = baza.get(10);
//                    String wyciagobrotyma = baza.get(11);
//                    System.out.println("");
//                } else if (i==1) {
//                    String wyciagbo = baza.get(12);
//                } else {
//                    ImportBankWiersz x = new ImportBankWiersz();
//                    x.setDatatransakcji(baza.get(1));
//                    x.setDatawaluty(baza.get(2));
//                    x.setIBAN(baza.get(5));//??
//                    x.setKontrahent(baza.get(4));//??
//                    x.setKwota(Double.parseDouble(baza.get(10).replace(",",".")));
//                    x.setWnma("Wn");
//                    if (!baza.get(11).equals("")) {
//                        x.setKwota(-Double.parseDouble(baza.get(11).replace(",",".")));
//                        x.setWnma("Ma");
//                    }
//                    x.setNrtransakji(baza.get(8));
//                    x.setOpistransakcji(baza.get(3));
//                    x.setTyptransakcji(oblicztyptransakcji(x));
//                    listaswierszy.add(x);
//                }
                i++;
            }
            System.out.println("");
        } catch (Exception ex) {
            Logger.getLogger(Dedraparser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   

    

    

   
    

    
   
    
   
    
}
