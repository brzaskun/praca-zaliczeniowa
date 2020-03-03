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
import embeddablefk.InterpaperXLS;
import entity.Klienci;
import entity.Podatnik;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import error.E;
import gus.GUSView;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import msg.Msg;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import view.WpisView;
/**
 *
 * @author Osito
 */

public class ReadFirmaoXLSFile {
    
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
    
     public static List<InterpaperXLS> getListafakturCSV(byte[] plikinterpaper, List<Klienci> k, KlienciDAO klienciDAO, String rodzajdok, GUSView gUSView) {
        List<InterpaperXLS> listafaktur = Collections.synchronizedList(new ArrayList<>());
         try {
            InputStream file = new ByteArrayInputStream(plikinterpaper);
            List<List<String>> records = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(";");
                    records.add(Arrays.asList(values));
                }
            }
            int i =0;
            Map<String, Klienci> znalezieni = new HashMap<>();
            for (Iterator<List<String>> it = records.iterator(); it.hasNext();) {
                    List<String> baza = it.next();
                    List<String> row = new ArrayList<>();
                    for (String r : baza) {
                        row.add(r.replace("\"", ""));
                    }
                    try {
                        InterpaperXLS interpaperXLS = new InterpaperXLS();
                        interpaperXLS.setNr(i++);
                        if (rodzajdok.equals("zakup")) {
                            uzupelnijzakup(interpaperXLS, row, k, klienciDAO, znalezieni, gUSView);
                        } else {
                            uzupelnijsprzedaz(interpaperXLS, row, k, klienciDAO, znalezieni, gUSView);
                        }
                        
                        listafaktur.add(interpaperXLS);
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
     
   private static void uzupelnijzakup(InterpaperXLS interpaperXLS, List<String> row, List<Klienci> k, KlienciDAO klienciDAO, Map<String, Klienci> znalezieni, GUSView gUSView) {
       interpaperXLS.setNrfaktury(row.get(0));
       interpaperXLS.setDataotrzymania(Date.valueOf(row.get(1)));
        interpaperXLS.setDatawystawienia(Date.valueOf(row.get(2)));
        interpaperXLS.setDatasprzedaży(Date.valueOf(row.get(3)));
        interpaperXLS.setDataobvat(Date.valueOf(row.get(4)));
        interpaperXLS.setKontrahent(row.get(5));
        interpaperXLS.setNip(row.get(6));
        interpaperXLS.setKlient(ustawkontrahenta(interpaperXLS, k, klienciDAO, znalezieni, gUSView));
        interpaperXLS.setWalutaplatnosci(row.get(7));
        interpaperXLS.setBruttowaluta(format.F.kwota(row.get(8)));
        interpaperXLS.setSaldofaktury(format.F.kwota(row.get(9)));
        interpaperXLS.setTerminplatnosci(Date.valueOf(row.get(10)));
        interpaperXLS.setPrzekroczenieterminu(Integer.valueOf(row.get(11)));
        if (row.get(12) != null && !row.get(12).equals("")) {
            interpaperXLS.setOstatniawplata(Date.valueOf(row.get(12)));
        } else {
            interpaperXLS.setOstatniawplata(null);
        }
        interpaperXLS.setSposobzaplaty(row.get(13));
        interpaperXLS.setNettowaluta(format.F.kwota(row.get(14)));
        interpaperXLS.setVatwaluta(format.F.kwota(row.get(15)));
        interpaperXLS.setNettoPLN(format.F.kwota(row.get(16)));
        interpaperXLS.setNettoPLNvat(format.F.kwota(row.get(17)));
        interpaperXLS.setVatPLN(format.F.kwota(row.get(18)));
        interpaperXLS.setBruttoPLN(format.F.kwota(row.get(19)));
   }
   
   private static void uzupelnijsprzedaz(InterpaperXLS interpaperXLS, List<String> row,List<Klienci> k, KlienciDAO klienciDAO, Map<String, Klienci> znalezieni, GUSView gUSView) {
       interpaperXLS.setNrfaktury(row.get(1));
        interpaperXLS.setDatawystawienia(Date.valueOf(row.get(2)));
        interpaperXLS.setDatasprzedaży(Date.valueOf(row.get(3)));
        interpaperXLS.setDataobvat(null);
        interpaperXLS.setKontrahent(row.get(4));
        interpaperXLS.setNip(row.get(5));
        interpaperXLS.setKlient(ustawkontrahenta(interpaperXLS, k, klienciDAO, znalezieni, gUSView));
        interpaperXLS.setWalutaplatnosci(row.get(6));
        interpaperXLS.setBruttowaluta(format.F.kwota(row.get(7)));
        interpaperXLS.setSaldofaktury(format.F.kwota(row.get(8)));
        interpaperXLS.setTerminplatnosci(Date.valueOf(row.get(9)));
        interpaperXLS.setPrzekroczenieterminu(Integer.valueOf(row.get(10)));
        if (row.get(11) != null && !row.get(11).equals("")) {
            interpaperXLS.setOstatniawplata(Date.valueOf(row.get(11)));
        } else {
            interpaperXLS.setOstatniawplata(null);
        }
        interpaperXLS.setSposobzaplaty(row.get(12));
        interpaperXLS.setNettowaluta(format.F.kwota(row.get(13)));
        interpaperXLS.setVatwaluta(format.F.kwota(row.get(14)));
        interpaperXLS.setNettoPLN(format.F.kwota(row.get(15)));
        interpaperXLS.setNettoPLNvat(format.F.kwota(row.get(16)));
        interpaperXLS.setVatPLN(format.F.kwota(row.get(17)));
        interpaperXLS.setBruttoPLN(format.F.kwota(row.get(18)));
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
            if (klient==null) {
                for (Klienci p : k) {
                    if (p.getNip().contains(interpaperXLS.getNip().trim())) {
                        klient = p;
                        znalezieni.put(interpaperXLS.getKontrahent(), p);
                        break;
                    }
                }
            }
            if (klient==null && interpaperXLS.getKontrahent().toLowerCase().trim().length()>3) {
                for (Klienci p : k) {
                    if (p.getNpelna().toLowerCase().contains(interpaperXLS.getKontrahent().toLowerCase().trim()) || (p.getNskrocona()!=null && p.getNskrocona().toLowerCase().contains(interpaperXLS.getKontrahent().toLowerCase().trim()))) {
                        klient = p;
                        znalezieni.put(interpaperXLS.getKontrahent(), p);
                        break;
                    }
                }
            }
            if (klient==null) {
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
            String filename = "c://temp//faktury2.xlsx";
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
