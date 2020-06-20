/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import beansRegon.SzukajDaneBean;
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import daoFK.KontoDAOfk;
import daoFK.PozycjaBilansDAO;
import daoFK.PozycjaRZiSDAO;
import daoFK.TabelanbpDAO;
import data.Data;
import embeddable.PanstwaMap;
import embeddablefk.InterpaperXLS;
import entity.Klienci;
import entity.Podatnik;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import error.E;
import gus.GUSView;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import view.WpisView;
import waluty.Z;
/**
 *
 * @author Osito
 */

public class ReadXLSMurawskiFile {
    
    private static String filename = "c://temp//faktury2.xlsx";
    
    public static List<InterpaperXLS> getListafaktur(byte[] plikinterpaper, String mc) {
        List<InterpaperXLS> listafaktur = Collections.synchronizedList(new ArrayList<>());
         try {
            InputStream file = new ByteArrayInputStream(plikinterpaper);
             //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
             //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
             //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            int i =1;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                try {
                    String mcdok = Data.getMc(Data.data_yyyyMMdd(row.getCell(4).getDateCellValue()));
                    if (mc.equals(mcdok)) {
                        InterpaperXLS interpaperXLS = new InterpaperXLS();
                        interpaperXLS.setNr(i++);
                        interpaperXLS.setNrfaktury(row.getCell(5).getStringCellValue());
                        interpaperXLS.setDatawystawienia(row.getCell(4).getDateCellValue());
                        interpaperXLS.setDatasprzedaży(row.getCell(4).getDateCellValue());
                        interpaperXLS.setDataobvat(row.getCell(4).getDateCellValue());
                        interpaperXLS.setKontrahent(row.getCell(6).getStringCellValue());
                        interpaperXLS.setNip(row.getCell(12)!=null && row.getCell(12).getStringCellValue().length()==10?row.getCell(12).getStringCellValue():row.getCell(13).getStringCellValue());
                        interpaperXLS.setWalutaplatnosci(row.getCell(11).getStringCellValue());
                        interpaperXLS.setBruttowaluta(row.getCell(8).getNumericCellValue());
                        interpaperXLS.setSaldofaktury(row.getCell(8).getNumericCellValue());
                        interpaperXLS.setNettowaluta(row.getCell(8).getNumericCellValue());
                        interpaperXLS.setVatwaluta(row.getCell(8).getNumericCellValue()-row.getCell(7).getNumericCellValue());
                        listafaktur.add(interpaperXLS);
                    }
                } catch (Exception e){
                    error.E.s("");
                }
            }
            file.close();
        }
        catch (Exception e) {
            E.e(e);
        }
        return listafaktur;
    }
    
     public static List<InterpaperXLS> getListafakturXLS(byte[] plikinterpaper, List<Klienci> k, KlienciDAO klienciDAO, String rodzajdok, GUSView gUSView, TabelanbpDAO tabelanbpDAO, String mc) {
        List<InterpaperXLS> listafaktur = Collections.synchronizedList(new ArrayList<>());
         try {
            InputStream file = new ByteArrayInputStream(plikinterpaper);
             //Create Workbook instance holding reference to .xlsx file  TYLKO NOWE XLSX
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            //XSSFWorkbook workbook = new XSSFWorkbook(file);
             //Get first/desired sheet from the workbook
            HSSFSheet sheet = workbook.getSheetAt(1);
            Iterator<Row> rowIterator = sheet.iterator();
            int i =1;
            Map<String, Klienci> znalezieni = new HashMap<>();
            List<Row> wiersze = new ArrayList<>();
            while (rowIterator.hasNext()) {
                 Row row = rowIterator.next();
                 wiersze.add(row);
            }
            try {
                InterpaperXLS interpaperXLS = new InterpaperXLS();
                String mcdok = Data.getMc(Data.data_yyyyMMdd(wiersze.get(1).getCell(11).getDateCellValue()));
                if (mc.equals(mcdok)) {
                    //String nip = row.getCell(2).getStringCellValue().replace("-", "").trim();
                    uzupelnijsprzedaz(interpaperXLS, wiersze, k, klienciDAO, znalezieni, gUSView, tabelanbpDAO);
                    if (interpaperXLS.getKontrahent()!=null && (interpaperXLS.getNettowaluta()!=0.0 || interpaperXLS.getVatwaluta()!=0.0)) {
                        interpaperXLS.setNr(i++);
                        listafaktur.add(interpaperXLS);
                    }
                }
                    
            } catch (Exception e){
                E.e(e);
            }
            file.close();
        }
        catch (Exception e) {
            E.e(e);
        }
        return listafaktur;
    }
    
   private static void przewalutuj(InterpaperXLS interpaperXLS) {
        Tabelanbp t = interpaperXLS.getTabelanbp();
        if (t!=null && !t.getWaluta().getSymbolwaluty().equals("PLN")) {
            interpaperXLS.setNettoPLN(Z.z(interpaperXLS.getNettowaluta()*t.getKurssredniPrzelicznik()));
            interpaperXLS.setVatPLN(Z.z(interpaperXLS.getVatwaluta()*t.getKurssredniPrzelicznik()));
        } else {
            interpaperXLS.setNettoPLN(interpaperXLS.getNettowaluta());
            interpaperXLS.setVatPLN(interpaperXLS.getVatPLN());
        }
        interpaperXLS.setBruttowaluta(Z.z(interpaperXLS.getNettowaluta()+interpaperXLS.getVatwaluta()));
        interpaperXLS.setBruttoPLN(Z.z(interpaperXLS.getNettoPLN()+interpaperXLS.getVatPLN()));
    }
      
    private static void uzupelnijsprzedaz(InterpaperXLS interpaperXLS, List<Row> rows ,List<Klienci> k, KlienciDAO klienciDAO, Map<String, Klienci> znalezieni, GUSView gUSView, TabelanbpDAO tabelanbpDAO) {
            if (rows.size()>0) {
            String numerrach = rows.get(10).getCell(8).getStringCellValue();
            boolean korekta = false;
            if (numerrach.contains("Korrekturrechnungsnummer")) {
                String tmp = rows.get(10).getCell(8).getStringCellValue();
                tmp = tmp.replace("Korrekturrechnungsnummer","").trim();
                tmp = tmp.substring(0, tmp.indexOf("für")).trim();
                interpaperXLS.setNrfaktury(tmp);
                korekta = true;
            } else {
                String tmp = rows.get(10).getCell(8).getStringCellValue();
                tmp = tmp.replace("Rechnungsnummer ","").trim();
                interpaperXLS.setNrfaktury(tmp);
            }
            interpaperXLS.setDatawystawienia(rows.get(1).getCell(11).getDateCellValue());
            interpaperXLS.setDatasprzedaży(rows.get(1).getCell(11).getDateCellValue());
            interpaperXLS.setDataobvat(rows.get(1).getCell(11).getDateCellValue());
            String kontr = rows.get(11).getCell(8).getStringCellValue().split("\n")[0];
            interpaperXLS.setKontrahent(kontr);
            String[] kl = rows.get(11).getCell(8).getStringCellValue().split("\n");
            if (kl!=null && kl.length==3) {
                interpaperXLS.setKlientnazwa(kl[0]);
                interpaperXLS.setKlientulica(kl[1]);
                interpaperXLS.setKlientkod(kl[2]);
                interpaperXLS.setKlientpaństwo("DE");
            } else if (kl!=null && kl.length==4) {
                interpaperXLS.setKlientnazwa(kl[0]);
                interpaperXLS.setKlientulica(kl[1]+" "+kl[2]);
                interpaperXLS.setKlientkod(kl[3]);
                interpaperXLS.setKlientpaństwo("DE");
            }
            interpaperXLS.setNip("");
            interpaperXLS.setKlient(ustawkontrahenta(interpaperXLS, k, klienciDAO, znalezieni, gUSView));
            interpaperXLS.setWalutaplatnosci("EUR");
            if (korekta){
                interpaperXLS.setNettowaluta(-Z.z(rows.get(21).getCell(8).getNumericCellValue()));
                interpaperXLS.setVatwaluta(-Z.z(rows.get(21).getCell(10).getNumericCellValue()));
                interpaperXLS.setBruttowaluta(-Z.z(rows.get(21).getCell(12).getNumericCellValue()));
            } else {
                interpaperXLS.setNettowaluta(Z.z(rows.get(21).getCell(8).getNumericCellValue()));
                interpaperXLS.setVatwaluta(Z.z(rows.get(21).getCell(10).getNumericCellValue()));
                interpaperXLS.setBruttowaluta(Z.z(rows.get(21).getCell(12).getNumericCellValue()));
            }
            ustawtabelenbp(interpaperXLS, tabelanbpDAO);
            przewalutuj(interpaperXLS);
            }
        }

   private static Klienci ustawkontrahenta(InterpaperXLS interpaperXLS, List<Klienci> k, KlienciDAO klienciDAO, Map<String, Klienci> znalezieni, GUSView gUSView) {
//       if (interpaperXLS.getKontrahent().equals("HST")) {
//           error.E.s("");
//       }
       Klienci klient = null;
        try {
            if (!znalezieni.isEmpty()) {
                for (String p : znalezieni.keySet()) {
                    if (p.equals(interpaperXLS.getKontrahent().trim())) {
                        klient = znalezieni.get(p);
                        break;
                    }
                }
            }
            if (klient==null && interpaperXLS.getNip()!=null && interpaperXLS.getNip().length()>6) {
                for (Klienci p : k) {
                    if (p.getNip().contains(interpaperXLS.getNip().trim())) {
                        klient = p;
                        znalezieni.put(interpaperXLS.getKontrahent(), p);
                        break;
                    }
                }
            }
            if (klient==null && interpaperXLS.getKontrahent().toLowerCase().trim().length()>3 && interpaperXLS.getNip()!=null && interpaperXLS.getNip().length()>6) {
                for (Klienci p : k) {
                    if (p.getNpelna().toLowerCase().contains(interpaperXLS.getKontrahent().toLowerCase().trim()) || (p.getNskrocona()!=null && p.getNskrocona().toLowerCase().contains(interpaperXLS.getKontrahent().toLowerCase().trim()))) {
                        klient = p;
                        znalezieni.put(interpaperXLS.getKontrahent(), p);
                        break;
                    }
                }
            }
            if (klient==null && interpaperXLS.getNip()!=null && interpaperXLS.getNip().length()>6) {
                String nip = interpaperXLS.getNip().trim();
                if (nip.length()==10 && Character.isDigit(nip.charAt(0))) {
                    klient = SzukajDaneBean.znajdzdaneregonAutomat(nip, gUSView);
                    if (klient.getNpelna()==null) {
                        klient = null;
                    } else {
                        //klienciDAO.dodaj(klient);
                    }
                    znalezieni.put(interpaperXLS.getKontrahent(), klient);
                }
            }
             
            if (klient==null && interpaperXLS.getNip()!=null && interpaperXLS.getNip().length()>6) {
                String nip = interpaperXLS.getNip().trim();
                if (!Character.isDigit(nip.charAt(0))) {
                    klient = new Klienci(1, interpaperXLS.getKlientnazwa(), interpaperXLS.getKlientnazwa(), interpaperXLS.getNip(), interpaperXLS.getKlientkod(), interpaperXLS.getKlientmiasto(), interpaperXLS.getKlientulica(), interpaperXLS.getKlientdom(), interpaperXLS.getKlientlokal());
                    klient.setKrajnazwa(interpaperXLS.getKlientpaństwo());
                    klient.setKrajkod(PanstwaMap.getWykazPanstwSX().get(klient.getKrajnazwa()));
                    if (klient.getNip()!=null && klient.getNip().length()>5) {
                        //klienciDAO.dodaj(klient);
                        znalezieni.put(interpaperXLS.getKontrahent(), klient);
                    }
                }
            }
            //sa dwie opcje moze nie znalesc po nipoie polskiego i te bez nipu
            if (klient==null) {
                klient = new Klienci(1, interpaperXLS.getKlientnazwa(), interpaperXLS.getKlientnazwa(), null, interpaperXLS.getKlientkod(), interpaperXLS.getKlientmiasto(), interpaperXLS.getKlientulica(), interpaperXLS.getKlientdom(), interpaperXLS.getKlientlokal());
                klient.setKrajnazwa(interpaperXLS.getKlientpaństwo());
                klient.setKrajkod(PanstwaMap.getWykazPanstwSX().get(klient.getKrajnazwa()));
                znalezieni.put(interpaperXLS.getKontrahent(), klient);
            }
            if (klient.getKrajnazwa()==null) {
                String nip = klient.getNip();
                String kod = klient.getKodpocztowy();
                if (nip.length()==10 && kod.contains("-")) {
                    klient.setKrajnazwa("Polska");
                    klient.setKrajkod("PL");
                }
                //klienciDAO.edit(klient);
            }
        } catch (Exception e) {
            E.e(e);
        }
        return klient;
    }
   
    
    
    private static void ustawtabelenbp(InterpaperXLS interpaperXLS, TabelanbpDAO tabelanbpDAO) {
         if (!interpaperXLS.getWalutaplatnosci().equals("PLN")) {
            Format formatterX = new SimpleDateFormat("yyyy-MM-dd");
            String datadokumentu = formatterX.format(interpaperXLS.getDatasprzedaży());
            DateTime dzienposzukiwany = new DateTime(datadokumentu);
            boolean znaleziono = false;
            int zabezpieczenie = 0;
            while (!znaleziono && (zabezpieczenie < 365)) {
                dzienposzukiwany = dzienposzukiwany.minusDays(1);
                String doprzekazania = dzienposzukiwany.toString("yyyy-MM-dd");
                Tabelanbp tabelanbppobrana = tabelanbpDAO.findByDateWaluta(doprzekazania, interpaperXLS.getWalutaplatnosci());
                if (tabelanbppobrana instanceof Tabelanbp) {
                    znaleziono = true;
                    interpaperXLS.setTabelanbp(tabelanbppobrana);
                    break;
                }
                zabezpieczenie++;
            }
         }
    }
    
     public static void updateKonta(KontoDAOfk kontoDAOfk, Podatnik podatnik, Integer rok, String filename) {
         try {
            FileInputStream file = new FileInputStream(new File(filename));
             //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
             //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
             //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                try {
                    String pelnynumer = row.getCell(1).getStringCellValue();
                    String nazwapelna = row.getCell(2).getStringCellValue();
                    String tlumaczenie = row.getCell(3).getStringCellValue();
                    if (!tlumaczenie.equals("")) {
                        Konto k = kontoDAOfk.findKonto(pelnynumer, podatnik, rok);
                        if (k != null && k.getNazwapelna().equals(nazwapelna)) {
                            k.setDe(tlumaczenie);
                            kontoDAOfk.edit(k);
                        }
                    }
                } catch (Exception e) {
                    E.e(e);
                }
            }
            file.close();
        } catch (Exception e) {
            E.e(e);
        }
    }
    
      
    public static void main(String[] args) {
        try {
            String tmp = "Korrekturrechnungsnummer 194/20 für Rechnungsnummer 148/20";
            tmp = tmp.replace("Korrekturrechnungsnummer","").trim();
            tmp = tmp.substring(0, tmp.indexOf("für")).trim();
            error.E.s(tmp);
//            FileInputStream file = new FileInputStream(new File(filename));
//             //Create Workbook instance holding reference to .xlsx file
//            XSSFWorkbook workbook = new XSSFWorkbook(file);
//             //Get first/desired sheet from the workbook
//            XSSFSheet sheet = workbook.getSheetAt(0);
//             //Iterate through each rows one by one
//            Iterator<Row> rowIterator = sheet.iterator();
//            while (rowIterator.hasNext()) {
//                Row row = rowIterator.next();
//                InterpaperXLS interpaperXLS = new InterpaperXLS();
//                interpaperXLS.setNr((int) row.getCell(0).getNumericCellValue());
//                interpaperXLS.setNrfaktury(row.getCell(1).getStringCellValue());
//                interpaperXLS.setDatawystawienia(row.getCell(2).getDateCellValue());
//                interpaperXLS.setDatasprzedaży(row.getCell(3).getDateCellValue());
//                interpaperXLS.setKontrahent(row.getCell(4).getStringCellValue());
//                interpaperXLS.setWalutaplatnosci(row.getCell(5).getStringCellValue());
//                interpaperXLS.setBruttowaluta(row.getCell(6).getNumericCellValue());
//                interpaperXLS.setSaldofaktury(row.getCell(7).getNumericCellValue());
//                interpaperXLS.setTerminplatnosci(row.getCell(8).getDateCellValue());
//                interpaperXLS.setPrzekroczenieterminu((int) row.getCell(9).getNumericCellValue());
//                if (row.getCell(10) != null) {
//                    interpaperXLS.setOstatniawplata(row.getCell(10).getDateCellValue());
//                } else {
//                    interpaperXLS.setOstatniawplata(null);
//                }
//                interpaperXLS.setSposobzaplaty(row.getCell(11).getStringCellValue());
//                interpaperXLS.setNettowaluta(row.getCell(12).getNumericCellValue());
//                interpaperXLS.setVatwaluta(row.getCell(13).getNumericCellValue());
//                interpaperXLS.setNettoPLN(row.getCell(14).getNumericCellValue());
//                interpaperXLS.setVatPLN(row.getCell(15).getNumericCellValue());
//                interpaperXLS.setBruttoPLN(row.getCell(16).getNumericCellValue());
//            }
//            file.close();
        } catch (Exception e) {
            E.e(e);
        }
    }
    
//    switch (cell.getCellType())
//                    {
//                        case Cell.CELL_TYPE_NUMERIC:
//                            if (i == 2 || i == 3) {
//                                
//                                System.out.print(cell.getDateCellValue() + "; ");
//                            } else {
//                                System.out.print(cell.getNumericCellValue() + "; ");
//                            }
//                            break;
//                        case Cell.CELL_TYPE_STRING:
//                            System.out.print(cell.getStringCellValue() + "; ");
//                            break;
//                    }

    
}
