/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import beansRegon.SzukajDaneBean;
import dao.KlienciDAO;
import dao.KontoDAOfk;
import dao.PozycjaBilansDAO;
import dao.PozycjaRZiSDAO;
import dao.RodzajedokDAO;
import data.Data;
import embeddable.PanstwaMap;
import embeddablefk.InterpaperXLS;
import entity.Klienci;
import entity.Podatnik;
import entity.Rodzajedok;
import entityfk.Konto;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import error.E;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.NumberToTextConverter;
import view.WpisView;
import waluty.Z;
/**
 *
 * @author Osito
 */

public class ReadXLSExolightFile {
    
    private static String filename = "d://exp.xlsx";
    
    public static List<InterpaperXLS> getListafaktur(byte[] plikinterpaper, String mc) {
        List<InterpaperXLS> listafaktur = Collections.synchronizedList(new ArrayList<>());
         try {
            InputStream file = new ByteArrayInputStream(plikinterpaper);
             //Create Workbook instance holding reference to .xlsx file
            Workbook workbook = WorkbookFactory.create(file);
             //Get first/desired sheet from the workbook
            Sheet sheet = workbook.getSheetAt(0);
             //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            int i =1;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                try {
//                    String mcdok = Data.getMc(Data.data_yyyyMMdd(row.getCell(4).getDateCellValue()));
//                    if (mc.equals(mcdok)) {
                        InterpaperXLS interpaperXLS = new InterpaperXLS();
                        interpaperXLS.setNr(i++);
                        interpaperXLS.setNrfaktury(row.getCell(5).getStringCellValue());
                        interpaperXLS.setDatawystawienia(pobierzdate(row.getCell(4)));
                        interpaperXLS.setDatasprzedaży(pobierzdate(row.getCell(4)));
                        interpaperXLS.setDataobvat(pobierzdate(row.getCell(4)));
                        interpaperXLS.setKontrahent(row.getCell(6).getStringCellValue());
                        interpaperXLS.setNip(row.getCell(12)!=null && row.getCell(12).getStringCellValue().length()==10?row.getCell(12).getStringCellValue():row.getCell(13).getStringCellValue());
                        interpaperXLS.setWalutaplatnosci(row.getCell(11).getStringCellValue());
                        interpaperXLS.setBruttowaluta(row.getCell(8).getNumericCellValue());
                        interpaperXLS.setSaldofaktury(row.getCell(8).getNumericCellValue());
                        interpaperXLS.setNettowaluta(row.getCell(8).getNumericCellValue());
                        interpaperXLS.setVatwaluta(row.getCell(8).getNumericCellValue()-row.getCell(7).getNumericCellValue());
                        listafaktur.add(interpaperXLS);
//                    }
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
    
    private static Date pobierzdate(Cell cell) {
        Date zwrot = null;
        try {
            zwrot = cell.getDateCellValue();
        } catch (Exception e){}
        if (zwrot==null) {
            try {
            zwrot = Data.stringToDate(cell.getStringCellValue());
        } catch (Exception e){}
        }
        return zwrot;
    }
    
     public static List<InterpaperXLS> getListafakturXLS(byte[] plikinterpaper, List<Klienci> k, KlienciDAO klienciDAO, String rodzajdok, String mc) {
        List<InterpaperXLS> listafaktur = Collections.synchronizedList(new ArrayList<>());
         try {
            InputStream file = new ByteArrayInputStream(plikinterpaper);
             //Create Workbook instance holding reference to .xlsx file  TYLKO NOWE XLSX
            Workbook workbook = WorkbookFactory.create(file);
            //Workbook workbook = WorkbookFactory.create(file);
             //Get first/desired sheet from the workbook
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            int i =1;
            Map<String, Klienci> znalezieni = new HashMap<>();
            while (rowIterator.hasNext()) {
                 Row row = rowIterator.next();
                    try {
                        InterpaperXLS interpaperXLS = new InterpaperXLS();
                        //String nip = row.getCell(2).getStringCellValue().replace("-", "").trim();
                        if (rodzajdok.contains("zakup")||rodzajdok.contains("WNT")||rodzajdok.contains("IU")) {
//                            String mcdok = Data.getMc(Data.data_yyyyMMdd(row.getCell(1).getDateCellValue()));
//                            if (mc.equals(mcdok)) {
                                uzupelnijzakup(interpaperXLS, row, k, klienciDAO, znalezieni);
                                if (interpaperXLS.getKontrahent()!=null && (interpaperXLS.getNettowaluta()!=0.0 || interpaperXLS.getVatwaluta()!=0.0)) {
                                    interpaperXLS.setNr(i++);
                                    listafaktur.add(interpaperXLS);
                                }
//                            }
                        } else {
//                            String mcdok = Data.getMc(Data.data_yyyyMMdd(row.getCell(1).getDateCellValue()));
//                            if (mc.equals(mcdok)) {
                                if (rodzajdok.equals("sprzedaż")) {
                                    uzupelnijsprzedaz(interpaperXLS, row, k, klienciDAO, znalezieni);
                                }
                                if (interpaperXLS.getKontrahent()!=null && (interpaperXLS.getNettowaluta()!=0.0 || interpaperXLS.getVatwaluta()!=0.0)) {
                                    interpaperXLS.setNr(i++);
                                    listafaktur.add(interpaperXLS);
                                }
//                            }
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
     
   private static void uzupelnijzakup(InterpaperXLS interpaperXLS, Row row, List<Klienci> k, KlienciDAO klienciDAO, Map<String, Klienci> znalezieni) {
        if (row.getCell(0).getRowIndex()>0) {
            interpaperXLS.setNrfaktury(row.getCell(2).getStringCellValue());
            if (row.getCell(0).getCellType() == CellType.STRING) {
                interpaperXLS.setDatawystawienia(Data.stringToDate(row.getCell(0).getStringCellValue()));
                interpaperXLS.setDatasprzedaży(Data.stringToDate(row.getCell(1).getStringCellValue()));
            } else {
                interpaperXLS.setDatawystawienia(row.getCell(0).getDateCellValue());
                interpaperXLS.setDatasprzedaży(row.getCell(1).getDateCellValue());
            }
            interpaperXLS.setKontrahent(pobierzkontrahenta(row.getCell(3)));
            interpaperXLS.setKlientnazwa(pobierzkontrahenta(row.getCell(3)));
            interpaperXLS.setKlientmiasto(pobierzkontrahenta(row.getCell(4)));
            String n5 = row.getCell(5)!=null&&row.getCell(5).getNumericCellValue()!=0?NumberToTextConverter.toText(row.getCell(5).getNumericCellValue()):null;
            String n6 = row.getCell(6)!=null&&!row.getCell(6).getStringCellValue().equals("")?row.getCell(6).getStringCellValue():null;
            String nip = n5!=null?n5:n6;
            interpaperXLS.setNip(nip);
            String nettowaluta = row.getCell(10).getStringCellValue();
            int nettowalutasize = nettowaluta.length();
            String waluta = nettowaluta.substring(nettowalutasize-3, nettowalutasize);
            interpaperXLS.setKlient(ustawkontrahenta(interpaperXLS, k, klienciDAO, znalezieni));
            interpaperXLS.setWalutaplatnosci(waluta);
            interpaperXLS.setNettowaluta(zamiennakwote(row.getCell(10).getStringCellValue()));
            interpaperXLS.setBruttowaluta(zamiennakwote(row.getCell(11).getStringCellValue()));
            interpaperXLS.setVatwaluta(Z.z(interpaperXLS.getNettowaluta()*0.23));
            interpaperXLS.setNettoPLN(zamiennakwote(row.getCell(7).getStringCellValue()));
            interpaperXLS.setBruttoPLN(zamiennakwote(row.getCell(9).getStringCellValue()));
            interpaperXLS.setVatPLN(Z.z(interpaperXLS.getNettoPLN()*0.23));
            interpaperXLS.setOpis("zakup towaru");
        }
   }
   
    private static void uzupelnijsprzedaz(InterpaperXLS interpaperXLS, Row row, List<Klienci> k, KlienciDAO klienciDAO, Map<String, Klienci> znalezieni) {
        if (row.getCell(0).getRowIndex()>0) {
                interpaperXLS.setNrfaktury(row.getCell(2).getStringCellValue());
                interpaperXLS.setDatawystawienia(pobierzdate(row.getCell(1)));
                interpaperXLS.setDataotrzymania(pobierzdate(row.getCell(0)));
                interpaperXLS.setDatasprzedaży(pobierzdate(row.getCell(1)));
                interpaperXLS.setKontrahent(pobierzkontrahenta(row.getCell(3)));
                interpaperXLS.setKlientnazwa(pobierzkontrahenta(row.getCell(3)));
                interpaperXLS.setKlientmiasto(pobierzkontrahenta(row.getCell(4)));
                String n5 = row.getCell(5)!=null&&row.getCell(5).getNumericCellValue()!=0?NumberToTextConverter.toText(row.getCell(5).getNumericCellValue()):null;
                String n6 = row.getCell(6)!=null&&!row.getCell(6).getStringCellValue().equals("")?row.getCell(6).getStringCellValue():null;
                String nip = n5!=null?n5:n6;
                interpaperXLS.setNip(nip);
                String nettowaluta = row.getCell(7).getStringCellValue();
                int nettowalutasize = nettowaluta.length();
                String waluta = nettowaluta.substring(nettowalutasize-3, nettowalutasize);
                interpaperXLS.setKlient(ustawkontrahenta(interpaperXLS, k, klienciDAO, znalezieni));
                interpaperXLS.setWalutaplatnosci(waluta);
                interpaperXLS.setNettowaluta(zamiennakwote(row.getCell(7).getStringCellValue()));
                interpaperXLS.setBruttowaluta(zamiennakwote(row.getCell(8).getStringCellValue()));
                interpaperXLS.setVatwaluta(Z.z(interpaperXLS.getBruttowaluta()-interpaperXLS.getNettowaluta()));
                interpaperXLS.setNettoPLN(zamiennakwote(row.getCell(9).getStringCellValue()));
                interpaperXLS.setBruttoPLN(zamiennakwote(row.getCell(10).getStringCellValue()));
                interpaperXLS.setVatPLN(zamiennakwote(row.getCell(11).getStringCellValue()));
        }
    }

   private static Klienci ustawkontrahenta(InterpaperXLS interpaperXLS, List<Klienci> k, KlienciDAO klienciDAO, Map<String, Klienci> znalezieni) {
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
                    klient = SzukajDaneBean.znajdzdaneregonAutomat(nip);
                    if (klient.getNpelna()==null) {
                        klient = null;
                    } else {
                        if (!klient.getNpelna().equals("nie znaleziono firmy w bazie Regon")) {
                            klienciDAO.create(klient);
                        }
                    }
                    znalezieni.put(interpaperXLS.getKontrahent(), klient);
                }
            }
             
            if (klient==null && interpaperXLS.getNip()!=null && interpaperXLS.getNip().length()>6) {
                String nip = interpaperXLS.getNip().trim();
                if (!Character.isDigit(nip.charAt(0))) {
                    klient = new Klienci(null, interpaperXLS.getKlientnazwa(), interpaperXLS.getKlientnazwa(), interpaperXLS.getNip(), interpaperXLS.getKlientkod(), interpaperXLS.getKlientmiasto(), interpaperXLS.getKlientulica(), interpaperXLS.getKlientdom(), interpaperXLS.getKlientlokal());
                    klient.setKrajnazwa(interpaperXLS.getKlientpaństwo());
                    klient.setKrajkod(PanstwaMap.getWykazPanstwSX().get(klient.getKrajnazwa()));
                    if (klient.getNip()!=null && klient.getNip().length()>5) {
                        if (!klient.getNpelna().equals("nie znaleziono firmy w bazie Regon")) {
                            klienciDAO.create(klient);
                        }
                        znalezieni.put(interpaperXLS.getKontrahent(), klient);
                    }
                }
            }
            //sa dwie opcje moze nie znalesc po nipoie polskiego i te bez nipu
            if (klient==null) {
                klient = new Klienci(null, interpaperXLS.getKlientnazwa(), interpaperXLS.getKlientnazwa(), null, interpaperXLS.getKlientkod(), interpaperXLS.getKlientmiasto(), interpaperXLS.getKlientulica(), interpaperXLS.getKlientdom(), interpaperXLS.getKlientlokal());
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
            Workbook workbook = WorkbookFactory.create(targetStream);
             //Get first/desired sheet from the workbook
            Sheet sheet = workbook.getSheetAt(0);
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
            Workbook workbook = WorkbookFactory.create(targetStream);
             //Get first/desired sheet from the workbook
            Sheet sheet = workbook.getSheetAt(0);
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
            Workbook workbook = WorkbookFactory.create(file);
             //Get first/desired sheet from the workbook
            Sheet sheet = workbook.getSheetAt(0);
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
            Workbook workbook = WorkbookFactory.create(file);
             //Get first/desired sheet from the workbook
            Sheet sheet = workbook.getSheetAt(0);
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
            Workbook workbook = WorkbookFactory.create(file);
             //Get first/desired sheet from the workbook
            Sheet sheet = workbook.getSheetAt(0);
             //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            int i = 1;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getCell(0).getRowIndex()>2) {
                    InterpaperXLS interpaperXLS = new InterpaperXLS();
                    interpaperXLS.setNrfaktury(row.getCell(2).getStringCellValue());
                    interpaperXLS.setDatawystawienia(row.getCell(0).getDateCellValue());
                    interpaperXLS.setDatasprzedaży(row.getCell(1).getDateCellValue());
                    interpaperXLS.setKontrahent(pobierzkontrahenta(row.getCell(3)));
                    String n5 = row.getCell(5)!=null&&row.getCell(5).getNumericCellValue()!=0?NumberToTextConverter.toText(row.getCell(5).getNumericCellValue()):null;
                    String n6 = row.getCell(6)!=null&&!row.getCell(6).getStringCellValue().equals("")?row.getCell(6).getStringCellValue():null;
                    String nip = n5!=null?n5:n6;
                    interpaperXLS.setNip(nip);
                    String nettowaluta = row.getCell(7).getStringCellValue();
                    int nettowalutasize = nettowaluta.length();
                    String waluta = nettowaluta.substring(nettowalutasize-3, nettowalutasize);
                    //interpaperXLS.setKlient(ustawkontrahenta(interpaperXLS, k, klienciDAO, znalezieni));
                    interpaperXLS.setWalutaplatnosci(waluta);
                    interpaperXLS.setNettowaluta(zamiennakwote(row.getCell(7).getStringCellValue()));
                    interpaperXLS.setBruttowaluta(zamiennakwote(row.getCell(8).getStringCellValue()));
                    interpaperXLS.setVatwaluta(Z.z(interpaperXLS.getBruttowaluta()-interpaperXLS.getNettowaluta()));
                    interpaperXLS.setNettoPLN(zamiennakwote(row.getCell(9).getStringCellValue()));
                    interpaperXLS.setBruttoPLN(zamiennakwote(row.getCell(10).getStringCellValue()));
                    interpaperXLS.setVatPLN(zamiennakwote(row.getCell(11).getStringCellValue()));
                    i++;
                    if (i==384) {
                    }
                }
            }
            file.close();
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    private static double zamiennakwote(String stringCellValue) {
        int nettowalutasize = stringCellValue.length();
        String waluta = stringCellValue.substring(nettowalutasize-3, nettowalutasize);
        waluta = " "+waluta;
        String kwota = stringCellValue.replace(waluta,"");
        kwota = kwota.replace(",", ".");
        kwota = kwota.replace(" ", "");
        double zwrot = Z.z(Double.parseDouble(kwota));
        return zwrot;
    }
    
    private static String pobierzkontrahenta(Cell cell) {
        String zwrot = "Błąd w nazwie kontrahenta";
        try {
            zwrot = cell.getStringCellValue();
        } catch (Exception e) {}
        return zwrot;
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
