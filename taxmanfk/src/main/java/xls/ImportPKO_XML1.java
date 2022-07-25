/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import data.Data;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import msg.Msg;
import org.xml.sax.SAXException;
import pkosaxml.AccountHistory;
import waluty.Z;


/**
 *
 * @author Osito
 */

public class ImportPKO_XML1 implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
    public static List importujdok(byte[] pobrane, String mcwpisu, int nrwyciagu, int lpwiersza, String mc, String wybranawaluta) {
        Integer mcInt = Integer.parseInt(mcwpisu);
        List zwrot = new ArrayList<Object>();
        List<ImportBankWiersz> pobranefaktury = new ArrayList<>();
        ImportowanyPlikNaglowek pn = new ImportowanyPlikNaglowek();
        try {
            JAXBContext context = JAXBContext.newInstance(pkosaxml.AccountHistory.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            InputStream file = new ByteArrayInputStream(pobrane);
            Object person2 = unmarshaller.unmarshal(file);
            pkosaxml.AccountHistory doc = (pkosaxml.AccountHistory) person2;
            AccountHistory.Search naglowek = doc.getSearch();
            pn.setWyciagdataod(data.Data.calendarToString(naglowek.getDate().getSince()));
            pn.setWyciagdatado(data.Data.calendarToString(naglowek.getDate().getTo()));
            String miesiacimp = Data.getMc(pn.getWyciagdataod());
            String miesiacimp2 = Data.getMc(pn.getWyciagdatado());
            pn.setWyciagnrod(miesiacimp);
            pn.setWyciagnrdo(miesiacimp2);
            pn.setWyciagnr(mc);
            AccountHistory.Operations operacje = doc.getOperations();
                if (operacje!=null && operacje.getOperation().size()>0) {
                    for (AccountHistory.Operations.Operation a : operacje.getOperation()) {
                        String mcwiersz = Data.getMc(Data.calendarToString(a.getExecDate()));
                        pn.setWyciagwaluta(a.getAmount().getCurr());
                        if (Integer.parseInt(mcwiersz) < mcInt) {

                        } else if (Integer.parseInt(mcwiersz) > mcInt) {

                        } else {
                            ImportBankWiersz x = new ImportBankWiersz();
                            x.setNr(lpwiersza++);
                            x.setDatatransakcji(Data.calendarToString(a.getExecDate()));
                            x.setDatawaluty(Data.calendarToString(a.getExecDate()));
                            String[] split = a.getDescription().split("\\R");
                            x.setNrwyciagu(pn.getWyciagnr());
                            String rodzajoperacji = a.getType();
                            x.setNrtransakji(rodzajoperacji);
                            double kwota = Z.z(a.getAmount().getValue().doubleValue());
                            if (kwota>0.0) {
                                String opis = pobierz(split,"Tytuł:");
                                if (opis.equals("brak")) {
                                    opis = pobierz(split,"Referencje własne zleceniodawcy:");
                                }
                                x.setOpistransakcji(rodzajoperacji+" "+opis);
                                x.setIBAN(pobierz(split,"Rachunek nadawcy:").replaceAll("\\s", ""));
                                x.setKontrahent(pobierz(split,"Nazwa nadawcy:"));//??
                                x.setKwota(kwota);
                                x.setWnma("Wn");
                            } else {
                                String opis = pobierz(split,"Tytuł:");
                                if (opis.equals("brak")) {
                                    opis = pobierz(split,"Dodatkowy opis:");
                                }
                                x.setOpistransakcji(rodzajoperacji+" "+opis);
                                x.setIBAN(pobierz(split,"Rachunek odbiorcy:").replaceAll("\\s", ""));
                                x.setKontrahent(pobierz(split,"Nazwa odbiorcy:"));//??
                                x.setKwota(Math.abs(kwota));
                                x.setWnma("Ma");
                            }
                            
                            x.setWaluta(a.getAmount().getCurr());
                            x.setTyptransakcji(oblicztyptransakcji(x));
                            x.setNaglowek(pn);
                            pobranefaktury.add(x);
                        }
                    }
                }
        }catch (javax.xml.bind.UnmarshalException ex1) {
            Msg.msg("e", "Wystąpił błąd przy wczytywaniu pliku xml");
            Msg.msg("e", "Sprawdź czy struktura jest ok");
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd przy pobieraniu danych po wczytaniu.");
        }
        zwrot.add(pn);
        Collections.reverse(pobranefaktury);
        for (int i = 1; i <pobranefaktury.size();i++) {
            pobranefaktury.get(i-1).setNr(i);
        }
        zwrot.add(pobranefaktury);
        zwrot.add(nrwyciagu);
        zwrot.add(lpwiersza);
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
        if (p.getOpistransakcji().contains("Prowizja")) {
            zwrot = 3;
        } else if (p.getOpistransakcji().contains("Opłata")) {
            zwrot = 3;
        } else if (p.getOpistransakcji().equals("PRZELEW ELIXIR - ONLINE")) {
            zwrot = 1;
        } else if (p.getOpistransakcji().equals("UZNANIE")) {
            zwrot = 2;
        } else if (p.getKontrahent().toLowerCase().contains("INTERPAPER SP Z O O SK")) {
            zwrot = 8;
        } else if (p.getKontrahent().toLowerCase().contains("Gmina")) {
            zwrot = 8;
        } else if (p.getKontrahent().toLowerCase().contains("PRZELEW ELIXIR NA RACHUNEK ZUS - ONLINE")) {
            zwrot = 7;
        } else if (p.getKontrahent().toLowerCase().contains("Podatkowy")) {
            zwrot = 6;
        } else if (p.getOpistransakcji().contains("karty")) {
            zwrot = 4;
        } else if (p.getOpistransakcji().startsWith("/VAT/")) {
            p.setOpistransakcji(p.getOpistransakcji().substring(p.getOpistransakcji().indexOf("INV/")+4));
            zwrot = 9;
        } else if (p.getOpistransakcji().contains("REZERWACJA")) {
            zwrot = 10;
        } else if (p.getOpistransakcji().contains("TRANSAKCJA KARTĄ ")) {
            zwrot = 5;
        } else if (p.getOpistransakcji().contains("Wynagrodzenie z tytułu rachunku")) {
            zwrot = 12;
        } else if (p.getOpistransakcji().contains("Wynagrodzenie za")) {
            zwrot = 12;
        } else if (p.getWnma().equals("Wn")) {
            zwrot = 1;
        } else if (p.getWnma().equals("Ma")) {
            zwrot = 2;
        }
        return zwrot;
    }

    
    public static void main(String[] args) throws SAXException, IOException {
        Integer mcInt = Integer.parseInt("01");
        List zwrot = new ArrayList<Object>();
        List<ImportBankWiersz> pobranefaktury = new ArrayList<>();
        int nrwyciagu = 1;
        int lpwiersza = 1;
        File file = new File("e:\\bank.xml");
        try {
            ImportowanyPlikNaglowek pn = new ImportowanyPlikNaglowek();
            JAXBContext context = JAXBContext.newInstance(pkosaxml.AccountHistory.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Object person2 = unmarshaller.unmarshal(file);
            pkosaxml.AccountHistory doc = (pkosaxml.AccountHistory) person2;
            AccountHistory.Search naglowek = doc.getSearch();
            pn.setWyciagdataod(data.Data.calendarToString(naglowek.getDate().getSince()));
            pn.setWyciagdatado(data.Data.calendarToString(naglowek.getDate().getTo()));
            String miesiacimp = Data.getMc(pn.getWyciagdataod());
            String miesiacimp2 = Data.getMc(pn.getWyciagdatado());
            pn.setWyciagnrod(miesiacimp);
            pn.setWyciagnrdo(miesiacimp2);
            pn.setWyciagnr("01");
            AccountHistory.Operations operacje = doc.getOperations();
                if (operacje!=null && operacje.getOperation().size()>0) {
                    for (AccountHistory.Operations.Operation a : operacje.getOperation()) {
                        String mcwiersz = Data.getMc(Data.calendarToString(a.getExecDate()));
                        pn.setWyciagwaluta(a.getAmount().getCurr());
                        if (Integer.parseInt(mcwiersz) < mcInt) {

                        } else if (Integer.parseInt(mcwiersz) > mcInt) {

                        } else {
                            ImportBankWiersz x = new ImportBankWiersz();
                            x.setNr(lpwiersza++);
                            x.setDatatransakcji(Data.calendarToString(a.getExecDate()));
                            x.setDatawaluty(Data.calendarToString(a.getExecDate()));
                            String[] split = a.getDescription().split("\\R");
                            x.setNrwyciagu(pn.getWyciagnr());
                            String rodzajoperacji = a.getType();
                            x.setNrtransakji(rodzajoperacji);
                            double kwota = Z.z(a.getAmount().getValue().doubleValue());
                            if (kwota<0.0) {
                                String opis = pobierz(split,"Tytuł:");
                                if (opis.equals("brak")) {
                                    opis = pobierz(split,"Referencje własne zleceniodawcy:");
                                }
                                x.setOpistransakcji(rodzajoperacji+" "+opis);
                                x.setIBAN(pobierz(split,"Rachunek odbiorcy:"));
                                x.setKontrahent(pobierz(split,"Nazwa odbiorcy:"));//??
                                x.setKwota(kwota);
                                x.setWnma("Wn");
                            } else {
                                String opis = pobierz(split,"Tytuł:");
                                if (opis.equals("brak")) {
                                    opis = pobierz(split,"Dodatkowy opis:");
                                }
                                x.setOpistransakcji(rodzajoperacji+" "+opis);
                                x.setIBAN(pobierz(split,"Rachunek nadawcy:"));
                                x.setKontrahent(pobierz(split,"Nazwa nadawcy:"));//??
                                x.setKwota(kwota);
                                x.setWnma("Ma");
                            }
                            
                            x.setWaluta(a.getAmount().getCurr());
                            x.setTyptransakcji(oblicztyptransakcji(x));
                            x.setNaglowek(pn);
                            pobranefaktury.add(x);
                        }
                    }
                }
            error.E.s("");
        } catch (Exception ex) {
            error.E.s("");
            // Logger.getLogger(Dedraparser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private static String pobierz(String[] split, String tyt) {
        String zwrot = "brak";
        for (String s : split) {
            if (s.contains(tyt)) {
                try {
                    zwrot = s.split(":")[1];
                } catch (Exception e){}
            }
        }
        return zwrot;
    }

   

    

    

   
    

    
   
    
   
    
}
