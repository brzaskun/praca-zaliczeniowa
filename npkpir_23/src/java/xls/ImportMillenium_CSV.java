/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import data.Data;
import error.E;
import formatpdf.F;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author Osito
 */

public class ImportMillenium_CSV implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
    public static List importujdok(byte[] pobrane, String mcwpisu, int nrwyciagu, int lpwiersza, String mc, String wybranawaluta) {
        List zwrot = new ArrayList<Object>();
        ImportowanyPlikNaglowek pn = new ImportowanyPlikNaglowek();
        pn.setWyciagwaluta(wybranawaluta);
        String numer = String.valueOf(nrwyciagu) + "mc";
        List<ImportBankWiersz> listaswierszy = new ArrayList<>();
        boolean blad = false;
        try {
            ByteArrayInputStream file = new ByteArrayInputStream(pobrane);
            if (pobrane != null) {
                //Iterable<CSVRecord> recordss = CSVFormat.DEFAULT.withHeader().withSkipHeaderRecord(true).parse(Files.newBufferedReader(pathToFile,Charset.forName("UTF-8")));
               CSVFormat csvFormat = CSVFormat.newFormat(',')
                    .builder()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .build();

                Iterable<CSVRecord> recordss = csvFormat.parse(new InputStreamReader(file, Charset.forName("UTF-8")));

                int i = 0;
                ImportBankWiersz y = new ImportBankWiersz();
                for (CSVRecord record : recordss) {
                    if (record.get("Waluta").equals(wybranawaluta)) {
                        ImportBankWiersz x = new ImportBankWiersz();
                        x.setNr(lpwiersza++);
                        x.setDatatransakcji(record.get("Data transakcji"));
                        x.setDatawaluty(record.get("Data rozliczenia"));
                        String mcwiersz = record.get("Data rozliczenia").split("-")[1];
                        if (!mcwiersz.equals(mc)) {
                            //blad = true;
                            //break;
                        } else {
                            x.setIBAN(record.get("Na rachunek/ Z rachunku").replaceAll("\\s",""));//??
                            x.setWaluta(wybranawaluta);
                            x.setKontrahent(record.get("Odbiorca / Nadawca"));//??
                            double obciazenie = Math.abs(F.kwota(record.get("Obciążenie")));
                            double uznanie = Math.abs(F.kwota(record.get("Uznanie")));
                            if (uznanie != 0.0) {
                                x.setKwota(uznanie);
                                x.setWnma("Wn");
                                pn.setWyciagobrotywn(pn.getWyciagobrotywn() + obciazenie);
                            } else {
                                x.setKwota(obciazenie);
                                x.setWnma("Ma");
                                pn.setWyciagobrotyma(pn.getWyciagobrotyma() + obciazenie);
                            }
                            x.setNrtransakji(record.get("Rodzaj transakcji"));
                            x.setOpistransakcji(record.get("Opis"));
                            x.setTyptransakcji(oblicztyptransakcji(x));
                            pn.setWyciagdatado(record.get("Data rozliczenia"));
                            pn.setWyciagbz(F.kwota(record.get("Saldo")));
                            pn.setWyciagnr(mc);
                            x.setNaglowek(pn);
                            listaswierszy.add(x);
                            i++;
                        }
                    }
                }
                Collections.reverse(listaswierszy);
                int j = 1;
                for (ImportBankWiersz p : listaswierszy) {
                    p.setNr(j++);
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
        zwrot.add(pn);
        zwrot.add(listaswierszy);
        zwrot.add(nrwyciagu);
        zwrot.add(lpwiersza);
        if (blad) {
            zwrot.add("dataerror");
        }
        return zwrot;
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
        if (p.getNrtransakji().equals("OPŁATA")) {
            zwrot = 3;
        } else if (p.getNrtransakji().equals("PROWIZJA")) {
            zwrot = 3;
        } else if (p.getNrtransakji().contains("UŻYCIE KARTY")) {
            zwrot = 4;
        } else if (p.getNrtransakji().toLowerCase().contains("rezerwa")) {
            zwrot = 1;
        } else if (p.getNrtransakji().contains("Przeliczenie")) {
            zwrot = 9;
        } else if (p.getNrtransakji().contains("Wypłata")) {
            zwrot = 9;
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
            Path pathToFile = Paths.get("D:\\mil.csv");
           CSVFormat csvFormat = CSVFormat.newFormat(',')
    .builder()
    .setHeader()
    .setSkipHeaderRecord(true)
    .build();

Iterable<CSVRecord> recordss = csvFormat
    .parse(Files.newBufferedReader(pathToFile, Charset.forName("UTF-8")));

           ImportowanyPlikNaglowek pn = new ImportowanyPlikNaglowek();
           String mc = "01";
           String nrwyciagu = "1"+mc;
           int i = 0;
            List<ImportBankWiersz> listaswierszy = new ArrayList<>();
            ImportBankWiersz y = new ImportBankWiersz();
            boolean blad = false;
            for (CSVRecord record : recordss) {
                    if (record.get("Waluta").equals("PLN")) {
                        ImportBankWiersz x = new ImportBankWiersz();
                        x.setNr(i++);
                        x.setDatatransakcji(Data.zmien8na10(record.get("Data księgowania")));
                        x.setDatawaluty(Data.zmien8na10(record.get("Data efektywna")));
                        String mcwiersz = Data.zmien8na10(record.get("Data efektywna")).split("-")[1];
                        if (!mcwiersz.equals(mc)) {
                            blad = true;
                            break;
                        }
                        x.setIBAN("");//??
                        x.setWaluta("PLN");
                        x.setKontrahent(record.get("Nazwa kontrahenta"));//??
                        double kwota = F.kwota(record.get("Kwota"));
                        x.setKwota(kwota);
                        if (kwota > 0.0) {
                            x.setWnma("Wn");
                            pn.setWyciagobrotywn(pn.getWyciagobrotywn() + kwota);
                        } else {
                            x.setWnma("Ma");
                            pn.setWyciagobrotyma(pn.getWyciagobrotyma() + Math.abs(kwota));
                        }
                        x.setNrtransakji(record.get("Tytuł płatności (linia 1)"));
                        x.setOpistransakcji(record.get("Typ operacji"));
                        x.setTyptransakcji(oblicztyptransakcji(x));
                        pn.setWyciagdatado(Data.zmien8na10(record.get("Data efektywna")));
                        pn.setWyciagbz(F.kwota(record.get("Saldo po operacji")));
                        x.setNaglowek(pn);
                        listaswierszy.add(x);
                        i++;
                    }
                }
        listaswierszy.add(y);
        } catch (Exception ex) {
            E.e(ex);
        }
    }

 

    

    

   
    

    
   
    
   
    
}
