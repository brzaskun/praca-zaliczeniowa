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
import embeddable.PanstwaMap;
import embeddablefk.InterpaperXLS;
import entity.Klienci;
import entity.Podatnik;
import entity.Rodzajedok;
import entityfk.Konto;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import error.E;
import gus.GUSView;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
import view.WpisView;
/**
 *
 * @author Osito
 */

public class ReadXLSFirmaoFile {
    
    private static String filename = "c://temp//faktury2.xlsx";
    
    public static List<InterpaperXLS> getListafaktur(byte[] plikinterpaper) {
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
                    InterpaperXLS interpaperXLS = new InterpaperXLS();
                    interpaperXLS.setNr(i++);
                    interpaperXLS.setNrfaktury(row.getCell(0).getStringCellValue());
                    interpaperXLS.setDatawystawienia(row.getCell(2).getDateCellValue());
                    interpaperXLS.setDatasprzedaży(row.getCell(3).getDateCellValue());
                    interpaperXLS.setDataobvat(row.getCell(4).getDateCellValue());
                    interpaperXLS.setKontrahent(row.getCell(5).getStringCellValue());
                    interpaperXLS.setNip(row.getCell(6).getStringCellValue());
                    interpaperXLS.setWalutaplatnosci(row.getCell(7).getStringCellValue());
                    interpaperXLS.setBruttowaluta(row.getCell(8).getNumericCellValue());
                    interpaperXLS.setSaldofaktury(row.getCell(9).getNumericCellValue());
                    interpaperXLS.setTerminplatnosci(row.getCell(10).getDateCellValue());
                    interpaperXLS.setPrzekroczenieterminu((int) row.getCell(11).getNumericCellValue());
                    if (row.getCell(11) != null) {
                        interpaperXLS.setOstatniawplata(row.getCell(12).getDateCellValue());
                    } else {
                        interpaperXLS.setOstatniawplata(null);
                    }
                    interpaperXLS.setSposobzaplaty(row.getCell(13).getStringCellValue());
                    interpaperXLS.setNettowaluta(row.getCell(14).getNumericCellValue());
                    interpaperXLS.setVatwaluta(row.getCell(15).getNumericCellValue());
                    interpaperXLS.setNettoPLN(row.getCell(16).getNumericCellValue());
                    interpaperXLS.setNettoPLNvat(row.getCell(17).getNumericCellValue());
                    interpaperXLS.setVatPLN(row.getCell(18).getNumericCellValue());
                    interpaperXLS.setBruttoPLN(row.getCell(19).getNumericCellValue());
                    listafaktur.add(interpaperXLS);
                } catch (Exception e){
                    System.out.println("");
                }
            }
            file.close();
        }
        catch (Exception e) {
            E.e(e);
        }
        return listafaktur;
    }
    
     public static List<InterpaperXLS> getListafakturXLS(byte[] plikinterpaper, List<Klienci> k, KlienciDAO klienciDAO, String rodzajdok, GUSView gUSView) {
        List<InterpaperXLS> listafaktur = Collections.synchronizedList(new ArrayList<>());
         try {
            InputStream file = new ByteArrayInputStream(plikinterpaper);
             //Create Workbook instance holding reference to .xlsx file  TYLKO NOWE XLSX
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            //XSSFWorkbook workbook = new XSSFWorkbook(file);
             //Get first/desired sheet from the workbook
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            int i =1;
            Map<String, Klienci> znalezieni = new HashMap<>();
            while (rowIterator.hasNext()) {
                 Row row = rowIterator.next();
                    try {
                        InterpaperXLS interpaperXLS = new InterpaperXLS();
                        String nip = row.getCell(2).getStringCellValue().replace("-", "").trim();
                        if (rodzajdok.contains("zakup")) {
                            uzupelnijzakup(interpaperXLS, row, k, klienciDAO, znalezieni, gUSView);
                            if (interpaperXLS.getKontrahent()!=null) {
                                interpaperXLS.setNr(i++);
                                listafaktur.add(interpaperXLS);
                            }
                        } else {
                            if (rodzajdok.equals("sprzedaż NIP") && nip!=null && !nip.equals("")) {
                                uzupelnijsprzedaz(interpaperXLS, row, k, klienciDAO, znalezieni, gUSView);
                            } else if (rodzajdok.equals("sprzedaż os.fiz") && (nip==null || nip.equals(""))) {
                                uzupelnijsprzedaz(interpaperXLS, row, k, klienciDAO, znalezieni, gUSView);
                            }
                            if (interpaperXLS.getKontrahent()!=null) {
                                interpaperXLS.setNr(i++);
                                listafaktur.add(interpaperXLS);
                            }
                        }
                    } catch (Exception e){
                        E.e(e);
                    }
            }
            file.close();
        }
        catch (Exception e) {
            E.e(e);
        }
        return listafaktur;
    }
     
   private static void uzupelnijzakup(InterpaperXLS interpaperXLS, Row row, List<Klienci> k, KlienciDAO klienciDAO, Map<String, Klienci> znalezieni, GUSView gUSView) {
        if (row.getCell(16).getStringCellValue().equals("Zakup")) {
            interpaperXLS.setNrfaktury(row.getCell(0).getStringCellValue());
            interpaperXLS.setDatawystawienia(row.getCell(10).getDateCellValue());
            interpaperXLS.setDatasprzedaży(row.getCell(8).getDateCellValue());
            interpaperXLS.setDataobvat(row.getCell(8).getDateCellValue());
            interpaperXLS.setKlientnazwa(row.getCell(3).getStringCellValue());
            interpaperXLS.setKlientpaństwo(row.getCell(4).getStringCellValue());
            interpaperXLS.setKlientkod(row.getCell(5).getStringCellValue());
            interpaperXLS.setKlientmiasto(row.getCell(6).getStringCellValue());
            interpaperXLS.setKlientulica(row.getCell(7).getStringCellValue());
            interpaperXLS.setKlientdom("");
            interpaperXLS.setKlientlokal("");
            String kontr = row.getCell(3).getStringCellValue()+" "+row.getCell(3).getStringCellValue()+" "+row.getCell(5).getStringCellValue()+" "+row.getCell(6).getStringCellValue();
            interpaperXLS.setKontrahent(kontr);
            interpaperXLS.setNip(row.getCell(2).getStringCellValue().replace("-", "").trim());
            interpaperXLS.setKlient(ustawkontrahenta(interpaperXLS, k, klienciDAO, znalezieni, gUSView));
            interpaperXLS.setWalutaplatnosci(row.getCell(12).getStringCellValue());
            interpaperXLS.setSaldofaktury(row.getCell(17).getNumericCellValue()+row.getCell(18).getNumericCellValue());
            interpaperXLS.setTerminplatnosci(row.getCell(9).getDateCellValue());
            interpaperXLS.setNettowaluta(row.getCell(17).getNumericCellValue());
            interpaperXLS.setVatwaluta(row.getCell(18).getNumericCellValue());
            interpaperXLS.setBruttowaluta(row.getCell(17).getNumericCellValue()+row.getCell(18).getNumericCellValue());
            interpaperXLS.setNettoPLN(row.getCell(13).getNumericCellValue());
            interpaperXLS.setNettoPLNvat(row.getCell(13).getNumericCellValue());
            interpaperXLS.setVatPLN(row.getCell(14).getNumericCellValue());
        }
   }
   
    private static void uzupelnijsprzedaz(InterpaperXLS interpaperXLS, Row row, List<Klienci> k, KlienciDAO klienciDAO, Map<String, Klienci> znalezieni, GUSView gUSView) {
        if (row.getCell(16).getStringCellValue().equals("Sprzedaż")&& !row.getCell(1).getStringCellValue().equals("Paragon")) {
            interpaperXLS.setNrfaktury(row.getCell(0).getStringCellValue());
            interpaperXLS.setDatawystawienia(row.getCell(10).getDateCellValue());
            interpaperXLS.setDatasprzedaży(row.getCell(8).getDateCellValue());
            interpaperXLS.setDataobvat(row.getCell(8).getDateCellValue());
            interpaperXLS.setKlientnazwa(row.getCell(3).getStringCellValue());
            interpaperXLS.setKlientpaństwo(row.getCell(4).getStringCellValue());
            interpaperXLS.setKlientkod(row.getCell(5).getStringCellValue());
            interpaperXLS.setKlientmiasto(row.getCell(6).getStringCellValue());
            interpaperXLS.setKlientulica(row.getCell(7).getStringCellValue());
            interpaperXLS.setKlientdom("");
            interpaperXLS.setKlientlokal("");
            String kontr = row.getCell(3).getStringCellValue()+" "+row.getCell(3).getStringCellValue()+" "+row.getCell(5).getStringCellValue()+" "+row.getCell(6).getStringCellValue();
            interpaperXLS.setKontrahent(kontr);
            interpaperXLS.setNip(row.getCell(2).getStringCellValue().replace("-", "").trim());
            interpaperXLS.setKlient(ustawkontrahenta(interpaperXLS, k, klienciDAO, znalezieni, gUSView));
            interpaperXLS.setWalutaplatnosci(row.getCell(12).getStringCellValue());
            interpaperXLS.setSaldofaktury(row.getCell(17).getNumericCellValue()+row.getCell(18).getNumericCellValue());
            interpaperXLS.setTerminplatnosci(row.getCell(9).getDateCellValue());
            interpaperXLS.setNettowaluta(row.getCell(17).getNumericCellValue());
            interpaperXLS.setVatwaluta(row.getCell(18).getNumericCellValue());
            interpaperXLS.setBruttowaluta(row.getCell(17).getNumericCellValue()+row.getCell(18).getNumericCellValue());
            interpaperXLS.setNettoPLN(row.getCell(13).getNumericCellValue());
            interpaperXLS.setNettoPLNvat(row.getCell(13).getNumericCellValue());
            interpaperXLS.setVatPLN(row.getCell(14).getNumericCellValue());
        }
    }

   private static Klienci ustawkontrahenta(InterpaperXLS interpaperXLS, List<Klienci> k, KlienciDAO klienciDAO, Map<String, Klienci> znalezieni, GUSView gUSView) {
//       if (interpaperXLS.getKontrahent().equals("HST")) {
//           System.out.println("");
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
                        klienciDAO.dodaj(klient);
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
                        klienciDAO.dodaj(klient);
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
                klienciDAO.edit(klient);
            }
        } catch (Exception e) {
            E.e(e);
        }
        return klient;
    }
   
    
    
    public static void updateRZiSInter(PozycjaRZiSDAO pozycjaRZiSDAO, WpisView wpisView, byte[] contents) {
         try {
            InputStream targetStream = new ByteArrayInputStream(contents);
             //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(targetStream);
             //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
             //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                try {
                    String pelnynumer = row.getCell(1).getStringCellValue();
                    String nazwapelna = row.getCell(2).getStringCellValue();
                    String tlumaczenie = row.getCell(4).getStringCellValue();
                    if (!tlumaczenie.equals("")) {
                        PozycjaRZiS k = pozycjaRZiSDAO.findRzisLP(Integer.parseInt(pelnynumer));
                        if (k != null) {
                            k.setDe(tlumaczenie);
                            pozycjaRZiSDAO.edit(k);
                        }
                    }
                } catch (Exception e) {
                    E.e(e);
                }
            }
            targetStream.close();
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    public static void updateBilansInter(PozycjaBilansDAO pozycjaBilansDAO, WpisView wpisView, byte[] contents) {
         try {
            InputStream targetStream = new ByteArrayInputStream(contents);
             //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(targetStream);
             //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
             //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                try {
                    String pelnynumer = row.getCell(1).getStringCellValue();
                    String nazwapelna = row.getCell(2).getStringCellValue();
                    String tlumaczenie = row.getCell(4).getStringCellValue();
                    if (!tlumaczenie.equals("")) {
                        PozycjaBilans k = pozycjaBilansDAO.findBilansLP(Integer.parseInt(pelnynumer));
                        if (k != null) {
                            k.setDe(tlumaczenie);
                            pozycjaBilansDAO.edit(k);
                        }
                    }
                } catch (Exception e) {
                    E.e(e);
                }
            }
            targetStream.close();
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    
    public static void updateRodzajedok(RodzajedokDAO rodzajedokDAO, WpisView wpisView, String filename) {
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
                    String skrot = row.getCell(1).getStringCellValue();
                    String nazwapelna = row.getCell(2).getStringCellValue();
                    String tlumaczenie = row.getCell(3).getStringCellValue();
                    if (!tlumaczenie.equals("")) {
                        Rodzajedok k = rodzajedokDAO.find(skrot, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                        if (k != null && k.getNazwa().equals(nazwapelna)) {
                            k.setDe(tlumaczenie);
                            rodzajedokDAO.edit(k);
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
            FileInputStream file = new FileInputStream(new File(filename));
             //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
             //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
             //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                InterpaperXLS interpaperXLS = new InterpaperXLS();
                interpaperXLS.setNr((int) row.getCell(0).getNumericCellValue());
                interpaperXLS.setNrfaktury(row.getCell(1).getStringCellValue());
                interpaperXLS.setDatawystawienia(row.getCell(2).getDateCellValue());
                interpaperXLS.setDatasprzedaży(row.getCell(3).getDateCellValue());
                interpaperXLS.setKontrahent(row.getCell(4).getStringCellValue());
                interpaperXLS.setWalutaplatnosci(row.getCell(5).getStringCellValue());
                interpaperXLS.setBruttowaluta(row.getCell(6).getNumericCellValue());
                interpaperXLS.setSaldofaktury(row.getCell(7).getNumericCellValue());
                interpaperXLS.setTerminplatnosci(row.getCell(8).getDateCellValue());
                interpaperXLS.setPrzekroczenieterminu((int) row.getCell(9).getNumericCellValue());
                if (row.getCell(10) != null) {
                    interpaperXLS.setOstatniawplata(row.getCell(10).getDateCellValue());
                } else {
                    interpaperXLS.setOstatniawplata(null);
                }
                interpaperXLS.setSposobzaplaty(row.getCell(11).getStringCellValue());
                interpaperXLS.setNettowaluta(row.getCell(12).getNumericCellValue());
                interpaperXLS.setVatwaluta(row.getCell(13).getNumericCellValue());
                interpaperXLS.setNettoPLN(row.getCell(14).getNumericCellValue());
                interpaperXLS.setVatPLN(row.getCell(15).getNumericCellValue());
                interpaperXLS.setBruttoPLN(row.getCell(16).getNumericCellValue());
            }
            file.close();
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
