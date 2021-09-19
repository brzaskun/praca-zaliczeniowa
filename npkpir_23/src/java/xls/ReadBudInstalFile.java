/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import beansRegon.SzukajDaneBean;
import dao.KlienciDAO;
import data.Data;
import embeddable.PanstwaMap;
import embeddablefk.InterpaperXLS;
import entity.Klienci;
import error.E;
import importfaktury.k3f.Invoice;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import msg.Msg;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import pl.com.cdn.optima.offline.ROOT;
import waluty.Z;
/**
 *
 * @author Osito
 */

public class ReadBudInstalFile {
    
    private static String filename = "c://temp//faktury2.xlsx";
    
//    public static List<InterpaperXLS> getListafaktur(byte[] plikinterpaper, String mc) {
//        List<InterpaperXLS> listafaktur = Collections.synchronizedList(new ArrayList<>());
//         try {
//            InputStream file = new ByteArrayInputStream(plikinterpaper);
//             //Create Workbook instance holding reference to .xlsx file
//            Workbook workbook = WorkbookFactory.create(file);
//             //Get first/desired sheet from the workbook
//            Sheet sheet = workbook.getSheetAt(0);
//             //Iterate through each rows one by one
//            Iterator<Row> rowIterator = sheet.iterator();
//            int i =1;
//            while (rowIterator.hasNext()) {
//                Row row = rowIterator.next();
//                try {
//                    String mcdok = Data.getMc(Data.data_yyyyMMdd(row.getCell(2).getDateCellValue()));
//                    if (mc.equals(mcdok)) {
//                        InterpaperXLS interpaperXLS = new InterpaperXLS();
//                        interpaperXLS.setNr(i++);
//                        interpaperXLS.setNrfaktury(row.getCell(0).getStringCellValue());
//                        interpaperXLS.setDatawystawienia(row.getCell(2).getDateCellValue());
//                        interpaperXLS.setDatasprzedaży(row.getCell(3).getDateCellValue());
//                        interpaperXLS.setDataobvat(row.getCell(4).getDateCellValue());
//                        interpaperXLS.setKontrahent(row.getCell(5).getStringCellValue());
//                        interpaperXLS.setNip(row.getCell(6).getStringCellValue());
//                        interpaperXLS.setWalutaplatnosci(row.getCell(7).getStringCellValue());
//                        interpaperXLS.setBruttowaluta(row.getCell(8).getNumericCellValue());
//                        interpaperXLS.setSaldofaktury(row.getCell(9).getNumericCellValue());
//                        interpaperXLS.setTerminplatnosci(row.getCell(10).getDateCellValue());
//                        interpaperXLS.setPrzekroczenieterminu((int) row.getCell(11).getNumericCellValue());
//                        if (row.getCell(11) != null) {
//                            interpaperXLS.setOstatniawplata(row.getCell(12).getDateCellValue());
//                        } else {
//                            interpaperXLS.setOstatniawplata(null);
//                        }
//                        interpaperXLS.setSposobzaplaty(row.getCell(13).getStringCellValue());
//                        interpaperXLS.setNettowaluta(row.getCell(14).getNumericCellValue());
//                        interpaperXLS.setVatwaluta(row.getCell(15).getNumericCellValue());
//                        interpaperXLS.setNettoPLN(row.getCell(16).getNumericCellValue());
//                        interpaperXLS.setNettoPLNvat(row.getCell(17).getNumericCellValue());
//                        interpaperXLS.setVatPLN(row.getCell(18).getNumericCellValue());
//                        interpaperXLS.setBruttoPLN(row.getCell(19).getNumericCellValue());
//                        listafaktur.add(interpaperXLS);
//                    }
//                } catch (Exception e){
//                    error.E.s("");
//                }
//            }
//            file.close();
//        }
//        catch (Exception e) {
//            E.e(e);
//        }
//        return listafaktur;
//    }
    
     public static Object[] getListafaktur(byte[] plikinterpaper, List<Klienci> k, KlienciDAO klienciDAO, String rodzajdok, String jakipobor, String mc) {
        Object[] zwrot = new Object[4];
        List<InterpaperXLS> listafaktur = Collections.synchronizedList(new ArrayList<>());
        List<CSVRecord> innyokres = new ArrayList<>();
        List<CSVRecord> przerwanyimport = new ArrayList<>();
        List<InterpaperXLS> importyzbrakami = Collections.synchronizedList(new ArrayList<>());
         try {
            InputStream file = new ByteArrayInputStream(plikinterpaper);
            List<String> list = new BufferedReader(new InputStreamReader(file, Charset.forName("cp1250"))).lines().collect(Collectors.toList());
            List<String> listbezinfo = list.subList(3, list.size()-1);
            List<String> rekordy = new ArrayList<>();
            StringBuilder sb = null;
            for (String p : listbezinfo) {
                if (p.equals("[NAGLOWEK]")) {
                    sb = new StringBuilder();
                } else if (p.equals("")) {
                    
                } else if (p.equals("[ZAWARTOSC]")) {
                    rekordy.add(sb.toString());
                    sb = null;
                } else if (p.equals("KONTRAHENCI")) {
                    sb = null;
                    break;
                } else {
                    if (sb!=null) {
                        sb.append(p);
                    }
                }
            }
           List<String> listfaktury = rekordy.stream().filter(p->p.startsWith("\"FS\",")).collect(Collectors.toList());
           List<String> listkorekty = rekordy.stream().filter(p->p.startsWith("\"KFS\",")).collect(Collectors.toList());
           listfaktury.addAll(listkorekty);
            //list2.forEach(System.out::println);
            List<CSVRecord> recordss = new ArrayList<>();
            for (String p : listfaktury) {
                Reader targetReader = new StringReader(p);
                Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(targetReader);
                records.forEach(recordss::add);
                System.out.println("");
            }
             //Create Workbook instance holding reference to .xlsx file  TYLKO NOWE XLSX
            if (recordss!=null) {
                int i =1;
                Map<String, Klienci> znalezieni = new HashMap<>();
                for (CSVRecord row :recordss) {
                    try {
                        InterpaperXLS interpaperXLS = new InterpaperXLS();
                        String nip = pobierznip(row);
                        String mcdok = Data.getMc(Data.zmienkolejnoscEPP(row.get(21)));
                        if (mc.equals(mcdok)) {
                                CSVRecord zlyrow = null;
                                if (nip!=null && !nip.equals("") &&  nip.length()>7) {
                                    zlyrow = uzupelnijsprzedaz2(interpaperXLS, row, k, klienciDAO, znalezieni);
                                    if (zlyrow!=null) {
                                        przerwanyimport.add(zlyrow);
                                    }
                                    if (interpaperXLS.getKontrahent()!=null && (interpaperXLS.getNettowaluta()!=0.0 || interpaperXLS.getVatwaluta()!=0.0)) {
                                        interpaperXLS.setNr(i++);
                                        listafaktur.add(interpaperXLS);
                                    } else {
                                        importyzbrakami.add(interpaperXLS);
                                    }
                                }
                        } else {
                            innyokres.add(row);
                        }
                    } catch (Exception e){
                        E.e(e);
                    }
                }
            } else {
                
            }
            file.close();
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Błąd w strukturze pliku");
        }
        zwrot[0] = listafaktur;
        zwrot[2] = importyzbrakami;
        List<InterpaperXLS> zlefakturylista = new ArrayList<>();
        Map<String, Klienci> znalezieni = new HashMap<>();
        int i =1;
        for (CSVRecord row :przerwanyimport) {
            try {
                InterpaperXLS interpaperXLS = new InterpaperXLS();
                uzupelnijsprzedaz2(interpaperXLS, row, k, klienciDAO, znalezieni);
                interpaperXLS.setNr(i++);
                zlefakturylista.add(interpaperXLS);
            } catch (Exception e){}
        }
        zwrot[1] = zlefakturylista;
        List<InterpaperXLS> zlefakturylista2 = new ArrayList<>();
        Map<String, Klienci> znalezieni2 = new HashMap<>();
        i =1;
        for (CSVRecord row :innyokres) {
            try {
                InterpaperXLS interpaperXLS = new InterpaperXLS();
                uzupelnijsprzedaz2(interpaperXLS, row, k, klienciDAO, znalezieni2);
                interpaperXLS.setNr(i++);
                zlefakturylista2.add(interpaperXLS);
            } catch (Exception e){}
        }
        zwrot[3] = zlefakturylista2;
        return zwrot;
    }
     
   
   
//FV i FV 2  Sprzedaz krajowa i traktowana zagranica jako krajowa z VAT
//PAR i PAR 2  Sprzedaz fiskalna paragony
//FD  i FD 2  Sprzedaz unijna 
//FE i FE 2  Sprzedaz eksportowa
//FVPR i FVPR 2  Sprzedaz z przekroczeniem limitu gdzie stosowana ma być stawka danego kraju (DE  16% i FR 20%). 

        
    private static CSVRecord uzupelnijsprzedaz2(InterpaperXLS interpaperXLS, CSVRecord row, List<Klienci> k, KlienciDAO klienciDAO, Map<String, Klienci> znalezieni) {
        CSVRecord zlafaktura = null;
        try {
                String nip = pobierznip(row);
                interpaperXLS.setNrfaktury(row.get(6));
                interpaperXLS.setDatawystawienia(Data.stringToDate(Data.zmienkolejnoscEPP(row.get(21))));
                interpaperXLS.setDatasprzedaży(Data.stringToDate(Data.zmienkolejnoscEPP(row.get(21))));
                interpaperXLS.setDataobvat(Data.stringToDate(Data.zmienkolejnoscEPP(row.get(21))));
                interpaperXLS.setKlientnazwa(row.get(12));
                interpaperXLS.setKlientpaństwo(row.get(60));
                interpaperXLS.setKlientkod(row.get(15));
                interpaperXLS.setKlientmiasto(row.get(14));
                interpaperXLS.setKlientulica(row.get(16));
                String kontr = interpaperXLS.getKlientnazwa()+" "+interpaperXLS.getKlientkod()+" "+interpaperXLS.getKlientmiasto()+" "+interpaperXLS.getKlientulica();
                interpaperXLS.setKontrahent(kontr);
                interpaperXLS.setNip(pobierznip(row));
                interpaperXLS.setKlient(ustawkontrahenta(interpaperXLS, k, klienciDAO, znalezieni));
                interpaperXLS.setWalutaplatnosci(row.get(46));
                interpaperXLS.setSaldofaktury(Z.z(Double.parseDouble(row.get(29))));
                interpaperXLS.setNettoPLN(Z.z(Double.parseDouble(row.get(27))));
                interpaperXLS.setNettoPLNvat(Z.z(Double.parseDouble(row.get(27))));
                interpaperXLS.setVatPLN(Z.z(Double.parseDouble(row.get(28))));
                interpaperXLS.setBruttoPLN(Z.z(Double.parseDouble(row.get(29))));
                interpaperXLS.setNettowaluta(Z.z(Double.parseDouble(row.get(27))));
                interpaperXLS.setVatwaluta(Z.z(Double.parseDouble(row.get(28))));
                interpaperXLS.setBruttowaluta(Z.z(Double.parseDouble(row.get(29))));
            } catch (Exception e) {
            zlafaktura = row;
        }
        return zlafaktura;
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
                if (interpaperXLS.getKlientpaństwo()!=null) {
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
            if (klient.getKrajkod()==null && klient.getKrajnazwa()!=null) {
                klient.setKrajkod(PanstwaMap.getWykazPanstwSX().get(klient.getKrajnazwa()));
                klienciDAO.edit(klient);
            }
        } catch (Exception e) {
            E.e(e);
        }
        return klient;
    }
   
    
      
//    public static void main(String[] args) {
//        try {
//            FileInputStream file = new FileInputStream(new File("d://raport_invoices_20210128a.xml"));
//            InputStreamReader reader = new InputStreamReader(file,"UTF-8");
//            JAXBContext jaxbContext = JAXBContext.newInstance(Invoices.class);
//            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//            Invoices tabela =  (Invoices) jaxbUnmarshaller.unmarshal(reader);
//            System.out.println("");
//             //Create Workbook instance holding reference to .xlsx file  TYLKO NOWE XLSX
//            if (tabela!=null && tabela.getInvoice()!=null && !tabela.getInvoice().isEmpty()) {
//                int i =1;
//                int paragon = 0;
//                int faktura = 0;
//                for (Invoice.Item row :tabela.getInvoice()) {
//                    try {
//                        InterpaperXLS interpaperXLS = new InterpaperXLS();
//                        String nip = pobierznip(row);
//                        interpaperXLS.setNrfaktury(row.getInvoiceNumber());
//                        interpaperXLS.setDatawystawienia(Data.stringToDate(row.getDateInvoice()));
//                        interpaperXLS.setDatasprzedaży(Data.stringToDate(row.getDateSell()));
//                        interpaperXLS.setDataobvat(Data.stringToDate(row.getDateSell()));
//                        interpaperXLS.setKlientnazwa(row.getClientInformation().getInvoiceFullname());
//                        interpaperXLS.setKlientpaństwo(row.getClientInformation().getInvoiceCountry());
//                        interpaperXLS.setKlientkod(row.getClientInformation().getInvoicePostcode());
//                        interpaperXLS.setKlientmiasto(row.getClientInformation().getInvoiceCity());
//                        interpaperXLS.setKlientulica(row.getClientInformation().getInvoiceAddress());
//                        String kontr = row.getClientInformation().getInvoiceFullname()+" "+row.getClientInformation().getInvoiceCountry()+" "+row.getClientInformation().getInvoicePostcode()+" "+row.getClientInformation().getInvoiceCity();
//                        interpaperXLS.setKontrahent(kontr);
//                        interpaperXLS.setNip(pobierznip(row));
//                        interpaperXLS.setWalutaplatnosci(pobierzwalute(row));
//                        interpaperXLS.setSaldofaktury(row.getTotalPriceBrutto());
//                        interpaperXLS.setNettoPLN(Z.z(row.getTotalPriceNetto()));
//                        interpaperXLS.setNettoPLNvat(Z.z(row.getTotalTax()));
//                        interpaperXLS.setVatPLN(Z.z(row.getTotalTax()));
//                        interpaperXLS.setBruttoPLN(row.getTotalPriceBrutto());
//                        interpaperXLS.setNettowaluta(Z.z(row.getTotalPriceNetto()));
//                        interpaperXLS.setVatwaluta(Z.z(row.getTotalTax()));
//                        interpaperXLS.setBruttowaluta(row.getTotalPriceBrutto());
//                        faktura++;
//                    } catch (Exception e){
//                        E.e(e);
//                    }
//                }
//                System.out.println("ilosc paragonow "+paragon);
//                System.out.println("ilosc faktur "+faktura);
//            }
//            file.close();
//            System.out.println("");
//        }
//        catch (Exception e) {
//            E.e(e);
//        }
//    }
    
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

    private static String pobierznip(CSVRecord row) {
        String zwrot = null;
        zwrot = row.get(17);
        zwrot = zwrot.replace("-","");
        if (zwrot.startsWith("PL")){
            zwrot = zwrot.substring(2);
        }
        return zwrot;
    }

    private static String pobierzwalute(Invoice.Item row) {
        String zwrot = null;
        zwrot = "PLN";
        if (zwrot==null || zwrot.equals("")) {
            zwrot = "PLN";
        }
        return zwrot;
    }

    private static double[] obliczkwoty(List<ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT.POZYCJE.POZYCJA> poz, ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT.PLATNOSCI.PLATNOSC plt, String waluta) {
        double zwrot[] = new double[6];
        double pozycjenetto = 0.0;
        double pozycjevat = 0.0;
        double sumapozycje = 0.0;
        double nettopln = 0.0;
        double vatpln = 0.0;
        double bruttopln = 0.0;
        for (ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT.POZYCJE.POZYCJA p :poz) {
            pozycjenetto = Z.z(pozycjenetto+Double.parseDouble(obetnijwalute(p.getNETTO(), waluta)));
            pozycjevat = Z.z(pozycjevat+Double.parseDouble(obetnijwalute(p.getVAT(), waluta)));
        }
        sumapozycje = Z.z(pozycjenetto+pozycjevat);
        boolean pozycjesawzlotowkach = Z.z(sumapozycje) == Z.z(plt.getKWOTAPLNPLAT());
        boolean kwotyrowne = Z.z(plt.getKWOTAPLAT())==Z.z(plt.getKWOTAPLNPLAT());
        if (pozycjesawzlotowkach && kwotyrowne) {
            nettopln = pozycjenetto;
            vatpln = pozycjevat;
            zwrot[0] = nettopln;
            zwrot[1] = vatpln;
            zwrot[2] = Z.z(nettopln+vatpln);
            zwrot[3] = nettopln;
            zwrot[4] = vatpln;
            zwrot[5] = Z.z(nettopln+vatpln);
        } else {
            if (Z.z(sumapozycje)==Z.z(plt.getKWOTAPLAT())) {
                double nettowaluta = pozycjenetto;
                double vatwaluta = pozycjevat;
                double stawkavat = vatwaluta/nettowaluta;
                double procentvat = stawkavat/(1.0+stawkavat);
                zwrot[3] = nettowaluta;
                zwrot[4] = vatwaluta;
                zwrot[5] = Z.z(nettowaluta+vatwaluta);
                vatpln = Z.z(plt.getKWOTAPLNPLAT()*procentvat);
                nettopln = Z.z(plt.getKWOTAPLNPLAT()-vatpln);
                if (nettowaluta<0.0) {
                    nettopln = -nettopln;
                    vatpln = -vatpln;
                }
                zwrot[0] = nettopln;
                zwrot[1] = vatpln;
                zwrot[2] = Z.z(nettopln+vatpln);
            } else {
                nettopln = pozycjenetto;
                vatpln = pozycjevat;
                double stawkavat = vatpln/nettopln;
                double procentvat = stawkavat/(1+stawkavat);
                zwrot[0] = nettopln;
                zwrot[1] = vatpln;
                zwrot[2] = Z.z(nettopln+vatpln);
                double vatwaluta = Z.z(plt.getKWOTAPLAT()*procentvat);
                double nettowaluta = Z.z(plt.getKWOTAPLAT()-vatwaluta);
                if (nettopln<0.0) {
                    nettowaluta = -nettowaluta;
                    vatwaluta = -vatwaluta;
                }
                zwrot[3] = nettowaluta;
                zwrot[4] = vatwaluta;
                zwrot[5] = Z.z(nettowaluta+vatwaluta);
            }
        }
        return zwrot;
    }

    private static String obetnijwalute(String netto, String waluta) {
        String zwrot = netto;
        if (netto.contains(waluta)) {
            zwrot = netto.replace(waluta,"");
            zwrot = zwrot.trim();
        }
        return zwrot;
    }

    
}
