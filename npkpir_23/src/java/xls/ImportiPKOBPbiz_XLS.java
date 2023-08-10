/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import data.Data;
import extclass.ReverseIterator;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import msg.Msg;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import waluty.Z;

/**
 *
 * @author Osito
 */

public class ImportiPKOBPbiz_XLS implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
    public static List importujdok(byte[] pobrane, String mcwpisu, int nrwyciagu, int lpwiersza, String mc) {
        List zwrot = new ArrayList<Object>();
        List<ImportBankWiersz> pobranefaktury = new ArrayList<>();
        List<Row> records = new ArrayList<>();
        ImportowanyPlikNaglowek pn = new ImportowanyPlikNaglowek();
        String mcod = null;
        int i = 0;
        try {
            InputStream file = new ByteArrayInputStream(pobrane);
            if (pobrane!=null) {
                    try {
                        //Create Workbook instance holding reference to .xlsx file
                        Workbook workbook = WorkbookFactory.create(file);
                        //Get first/desired sheet from the workbook
                        Sheet sheet = workbook.getSheetAt(0);
                        //Iterate through each rows one by one
                        Iterator<Row> rowIterator = sheet.iterator();
                        while (rowIterator.hasNext()) {
                            Row row = rowIterator.next();
                            records.add(row);
                        }
                        file.close();
                    } catch (Exception ex) {
                        Logger.getLogger(BankImportView.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            file.close();
                        } catch (IOException ex) {
                            Logger.getLogger(BankImportView.class.getName()).log(Level.SEVERE, null, ex);
                        }
                }
            int rozmiar = records.size();
            if (rozmiar > 2) {
                Row lrow = records.get(1);
                Row prow = records.get(rozmiar-1);
                pn.setWyciagdataod(Data.zmienkolejnosc(prow.getCell(0).getStringCellValue()));
                pn.setWyciagnrod(Data.getMc(pn.getWyciagdataod()));
                pn.setWyciagnrdo(mc);
                pn.setWyciagnr("1");
                pn.setWyciagwaluta((String) X.x(prow.getCell(5)));
                pn.setWyciagobrotywn(sumujobroty(records,0));
                pn.setWyciagobrotyma(sumujobroty(records,1));
                pn.setWyciagbo(xls.X.xKwota(prow.getCell(6))-xls.X.xKwota(prow.getCell(4)));
                pn.setWyciagbz(xls.X.xKwota(lrow.getCell(6))-xls.X.xKwota(lrow.getCell(4)));
                pn.setWyciagdatado(Data.zmienkolejnosc(lrow.getCell(0).getStringCellValue()));
                for (Iterator<Row> it = new ReverseIterator<>(records).iterator(); it.hasNext();) {
                    Row baza = it.next();
                    if (i==rozmiar) {

                    }  else {
                        ImportBankWiersz x = new ImportBankWiersz();
                        x.setNr(lpwiersza++);
                        x.setDatatransakcji(Data.zmienkolejnosc(baza.getCell(0).getStringCellValue()));
                        x.setDatawaluty(Data.zmienkolejnosc(baza.getCell(1).getStringCellValue()));
                        String mcwiersz = Data.getMc(x.getDatatransakcji());
                        if (!mcwiersz.equals(mc)) {
                              
                        } else {
                            String pobranyciag = baza.getCell(2)!=null? baza.getCell(2).getStringCellValue():"";
                            String opis = pobierzciag(pobranyciag,"Tytuł: ");
                            x.setOpistransakcji(opis);
                            x.setNrwyciagu(pn.getWyciagnr());
                            String rachunek = pobierzciag(pobranyciag,"Rachunek kontrahenta: ");
                            x.setIBAN(rachunek.replace(" ", ""));
                            String nazw = pobierzciag(pobranyciag,"Nazwa i adres Kontrahenta: ");
                            x.setKontrahent(nazw);
                            x.setKontrahent(x.getKontrahent().replace("Spółka Z Ograniczoną Odpowiedzialnością", "sp. z o.o."));
                            x.setKontrahent(x.getKontrahent().replace("SPÓŁKA Z OGRANICZONĄ ODPOWIEDZIALNOŚCIĄ", "sp. z o.o."));
                            x.setKontrahent(x.getKontrahent().replace("SPÓŁKA Z OGRANICZONĄ ODPOWI EDZIALNOŚCIĄ", "sp. z o.o."));
                            double kwota = (double) X.x(baza.getCell(4));
                            x.setWnma(kwota > 0.0 ? "Wn" : "Ma");
                            kwota = Math.abs(kwota);
                            x.setKwota(kwota);
                            x.setWaluta(pn.getWyciagwaluta());
                            x.setNrtransakji(baza.getCell(3).getStringCellValue());
                            x.setTyptransakcji(oblicztyptransakcji(x));
                            x.setNaglowek(pn);
                            pobranefaktury.add(x);
                        }
                    }
                    i++;
                }
            }
            }
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd. Wiersz "+i);
            Msg.msg("e", "Sprawdź czy w pliku nie występuję znak ; w niedozwolonych miejscach");
        }
        zwrot.add(pn);
        zwrot.add(pobranefaktury);
        zwrot.add(nrwyciagu);
        zwrot.add(lpwiersza);
        return zwrot;
    }
    
    
    private static String pobierzciag(String pobranyciag, String tytul) {
        String zwrot = "";
        int rozm = pobranyciag.length();
        int gdzie = pobranyciag.indexOf(tytul);
        int startpoz = tytul.length();
        pobranyciag = pobranyciag.substring(gdzie+startpoz, rozm);
        int gdzie2 = pobranyciag.indexOf("|");
        pobranyciag = pobranyciag.substring(0, gdzie2);
        zwrot = pobranyciag.trim();
        return zwrot;
    }
    
    
    private static double sumujobroty(List<Row> records, int i) {
        double zwrot = 0.0;
        int j = 0;
        int rozmiar = records.size()-1;
         for (Iterator<Row> it = new ReverseIterator<>(records).iterator(); it.hasNext();) {
            Row p = it.next();
            if (j==rozmiar) {

            } else {
                double kwota = xls.X.xKwota(p.getCell(4));
                if (i==0 && kwota > 0.0) {
                    zwrot = zwrot + xls.X.xKwota(p.getCell(4));
                } else if (i==1 && kwota < 0.0){
                    zwrot = zwrot - xls.X.xKwota(p.getCell(4));
                }
            }
            j++;
        }
        return Z.z(zwrot);
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
        if (p.getNrtransakji().equals("Prowizja")) {
            zwrot = 3;
        } else if (p.getNrtransakji().equals("Opłata")) {
            zwrot = 3;
        } else if (p.getNrtransakji().equals("PRZELEW ELIXIR - ONLINE") || p.getNrtransakji().equals("PRZELEW NA RACHUNEK W SAN PL - ONLINE")) {
            zwrot = 1;
        } else if (p.getNrtransakji().equals("Uznanie") || p.getNrtransakji().equals("Uznanie - płatność podzielona")) {
            zwrot = 1;
        } else if (p.getKontrahent().toLowerCase().contains("INTERPAPER SP Z O O SK")) {
            zwrot = 8;
        } else if (p.getKontrahent().toLowerCase().contains("Gmina")) {
            zwrot = 8;
        } else if (p.getNrtransakji().contains("Przelew z rachunku")) {
            zwrot = 2;
        } else if (p.getNrtransakji().contains("Przelew do ZUS")) {
            zwrot = 7;
        } else if (p.getNrtransakji().contains("Przelew podatkowy")) {
            zwrot = 6;
        } else if (p.getNrtransakji().contains("Wypłata z bankomatu")) {
            zwrot = 4;
        } else if (p.getNrtransakji().equals("Płatnośc kartą")) {
            zwrot = 5;
        } else if (p.getNrtransakji().equals("Uznanie")) {
            zwrot = 9;
        } else if (p.getNrtransakji().contains("REZERWACJA")) {
            zwrot = 10;
        } else if (p.getNrtransakji().contains("Przelew z rachunku")) {
            zwrot = 2;
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
    
//    public static void main(String[] args) throws SAXException, IOException {
//        List zwrot = new ArrayList<Object>();
//        List<ImportBankWiersz> pobranefaktury = new ArrayList<>();
//        int nrwyciagu = 1;
//        int lpwiersza = 1;
//        String mc = "03";
//        ImportowanyPlikNaglowek pn = new ImportowanyPlikNaglowek();
//        String mcod = null;
//        try {
//            Path pathToFile = Paths.get("D:\\tmp.csv");
//            List<List<String>> records = new ArrayList<>();
//            try (BufferedReader br =  Files.newBufferedReader(pathToFile,Charset.forName("windows-1250"))) {
//                String line;
//                while ((line = br.readLine()) != null) {
//                    String[] values = line.split(";");
//                    records.add(Arrays.asList(values));
//                }
//            } catch (Exception e) {
//                error.E.s(error.E.e(e));
//            }
//            int i = 0;
//            int rozmiar = records.size();
//                for (Iterator<List<String>> it = records.iterator(); it.hasNext();) {
//                    List<String> baza = it.next();
//                    if (i==14) {
//                        pn.setWyciagdataod(Data.zmienkolejnosc(baza.get(0)));
//                        pn.setWyciagdatado(Data.zmienkolejnosc(baza.get(1)));
//                        pn.setWyciagnrod(mc);
//                        pn.setWyciagnrdo(mc);
//                        pn.setWyciagnr(mc);
//                        if (pn.getWyciagnrod()!=null) {
//                            mcod = pn.getWyciagdataod().split("-")[1];
//                            if (!mcod.equals(mc)) {
//                                break;
//                            }
//                        }
//                    } else if (i==18) {
//                        pn.setWyciagwaluta(baza.get(0));
//                    } else if (i==20) {
//                        pn.setWyciagkonto(baza.get(0));
//                    } else if (i==31) {
//                        String replaceco = pn.getWyciagwaluta();
//                        pn.setWyciagobrotywn(!baza.get(2).equals("") ? Double.parseDouble(baza.get(2).replaceAll("\\s+","").replace(replaceco,"").replace(",",".")) : 0.0);
//                    } else if (i==32) {
//                        String replaceco = pn.getWyciagwaluta();
//                        pn.setWyciagobrotyma(!baza.get(2).equals("") ? Double.parseDouble(baza.get(2).replaceAll("\\s+","").replace(replaceco,"").replace(",",".")) : 0.0);
//                    } else if (i==35) {
//                        String replaceco = pn.getWyciagwaluta();
//                        pn.setWyciagbo(Double.parseDouble(baza.get(1).replace(replaceco,"").replaceAll("\\s+","").replace(",",".")));
//                    }  else if (i>37&& i<rozmiar-6){
//                        ImportBankWiersz x = new ImportBankWiersz();
//                        x.setNr(lpwiersza++);
//                        x.setDatatransakcji(Data.zmienkolejnosc(baza.get(0)));
//                        x.setDatawaluty(Data.zmienkolejnosc(baza.get(1)));
//                        String opis = baza.get(3)!=null && !baza.get(3).equals("\"\"")? baza.get(3).replace("\"", "").toLowerCase(new Locale("pl", "PL")):baza.get(2).toLowerCase(new Locale("pl", "PL"));
//                        x.setOpistransakcji(opis);
//                        x.setNrwyciagu(pn.getWyciagnr());
//                        x.setIBAN(baza.get(5).replace("\"", "").replace("'", ""));//??
//                        String kontr = baza.get(4).length()<5?"":baza.get(4).trim().replaceAll("\"","");
//                        kontr = WordUtils.capitalizeFully(kontr);
//                        x.setKontrahent(kontr);//??
//                        x.setKwota(Double.parseDouble(baza.get(6).replaceAll("\\s+","").replace(",",".")));
//                        x.setWnma(x.getKwota()>0.0?"Wn":"Ma");
//                        x.setWaluta(pn.getWyciagwaluta());
//                        x.setNrtransakji(baza.get(2));
//                        x.setTyptransakcji(oblicztyptransakcji(x));
//                        x.setNaglowek(pn);
//                        pobranefaktury.add(x);
//                    }
//                    if (i==rozmiar-3) {
//                        String replaceco = pn.getWyciagwaluta();
//                        pn.setWyciagbz(Double.parseDouble(baza.get(7).replace(replaceco,"").replaceAll("\\s+","").replace(",",".")));
//                    }
//                    i++;
//            }
//            error.E.s("");
//        } catch (Exception ex) {
//            // Logger.getLogger(Dedraparser.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        zwrot.add(pn);
//        zwrot.add(pobranefaktury);
//        zwrot.add(nrwyciagu);
//        zwrot.add(lpwiersza);
//        if (!mcod.equals(mc)) {
//           zwrot.add("dataerror");
//        }
//    }

    
   

    
public static void main(String[] args) {
    String rachunek = "Rachunek kontrahenta: 73 1020 2821 0000 1502 0164 1745|Nazwa i adres Kontrahenta: UNIVERSAL EXPORTS MACIEJ DERĘGOWSKI|Tytuł: 4/06/2021   |Identyfikator transakcji: 16810501100297507|";
    if (rachunek.contains("Tytuł:")) {
        String tytul = "Identyfikator transakcji: ";
        int rozm = rachunek.length();
        int gdzie = rachunek.indexOf(tytul);
        int startpoz = tytul.length();
        rachunek = rachunek.substring(gdzie+startpoz, rozm);
        int gdzie2 = rachunek.indexOf("|");
        rachunek = rachunek.substring(0, gdzie2);
        rachunek = rachunek.trim();
        System.out.println(rachunek);
    }
}

    

   
    

    
   
    
   
    
}
