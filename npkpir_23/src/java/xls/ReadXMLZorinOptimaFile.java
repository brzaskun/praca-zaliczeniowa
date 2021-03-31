/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import beansRegon.SzukajDaneBean;
import dao.DokDAOfk;
import dao.KlienciDAO;
import data.Data;
import embeddable.PanstwaMap;
import embeddablefk.InterpaperXLS;
import entity.Klienci;
import entityfk.Dokfk;
import error.E;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import pl.com.cdn.optima.offline.ROOT;
import view.WpisView;
import waluty.Z;
/**
 *
 * @author Osito
 */

public class ReadXMLZorinOptimaFile {
    
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
    
     public static Object[] getListafakturXLS(byte[] plikinterpaper, List<Klienci> k, KlienciDAO klienciDAO, String rodzajdok, String jakipobor, String mc, DokDAOfk dokDAOfk, WpisView wpisView) {
        Object[] zwrot = new Object[4];
        List<InterpaperXLS> listafaktur = Collections.synchronizedList(new ArrayList<>());
        List<ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT> innyokres = new ArrayList<>();
        List<ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT> przerwanyimport = new ArrayList<>();
        List<InterpaperXLS> importyzbrakami = Collections.synchronizedList(new ArrayList<>());
         try {
            InputStream file = new ByteArrayInputStream(plikinterpaper);
            InputStreamReader reader = new InputStreamReader(file, "Windows-1250");
            JAXBContext jaxbContext = JAXBContext.newInstance(ROOT.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ROOT tabela =  (ROOT) jaxbUnmarshaller.unmarshal(reader);
             //Create Workbook instance holding reference to .xlsx file  TYLKO NOWE XLSX
            if (tabela!=null && tabela.getREJESTRYSPRZEDAZYVAT()!=null && !tabela.getREJESTRYSPRZEDAZYVAT().getREJESTRSPRZEDAZYVAT().isEmpty()) {
                int i =1;
                Map<String, Klienci> znalezieni = new HashMap<>();
                for (ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT row :tabela.getREJESTRYSPRZEDAZYVAT().getREJESTRSPRZEDAZYVAT()) {
                    try {
                        InterpaperXLS interpaperXLS = new InterpaperXLS();
                        String nip = pobierznip(row);
                        String mcdok = Data.getMc(Data.calendarToString(row.getDATASPRZEDAZY()));
                        if (mc.equals(mcdok)) {
                            if (rodzajdok.contains("zakup")) {
                                uzupelnijzakup(interpaperXLS, row, k, klienciDAO, znalezieni);
                                if (interpaperXLS.getKontrahent()!=null && (interpaperXLS.getNettowaluta()!=0.0 || interpaperXLS.getVatwaluta()!=0.0)) {
                                    interpaperXLS.setNr(i++);
                                    listafaktur.add(interpaperXLS);
                                }
                            } else {
                                ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT zlyrow = null;
                                if (jakipobor.equals("wszystko")) {
                                    zlyrow = uzupelnijsprzedaz(interpaperXLS, row, k, klienciDAO, znalezieni);
                                    if (zlyrow != null) {
                                        przerwanyimport.add(zlyrow);
                                    }
                                    if (interpaperXLS.getKontrahent() != null && (interpaperXLS.getNettowaluta() != 0.0 || interpaperXLS.getVatwaluta() != 0.0)) {
                                        interpaperXLS.setNr(i++);
                                        listafaktur.add(interpaperXLS);
                                    } else {
                                        importyzbrakami.add(interpaperXLS);
                                    }
                                } else if (jakipobor.equals("firmy") && nip!=null && !nip.equals("") &&  nip.length()>7) {
                                    zlyrow = uzupelnijsprzedaz(interpaperXLS, row, k, klienciDAO, znalezieni);
                                    if (zlyrow!=null) {
                                        przerwanyimport.add(zlyrow);
                                    }
                                    if (interpaperXLS.getKontrahent()!=null && (interpaperXLS.getNettowaluta()!=0.0 || interpaperXLS.getVatwaluta()!=0.0)) {
                                        interpaperXLS.setNr(i++);
                                        listafaktur.add(interpaperXLS);
                                    } else {
                                        importyzbrakami.add(interpaperXLS);
                                    }
                                } else if (jakipobor.equals("fiz") && (nip==null || nip.equals("") || nip.length()<8)) {
                                    zlyrow = uzupelnijsprzedaz(interpaperXLS, row, k, klienciDAO, znalezieni);
                                    if (zlyrow != null) {
                                        przerwanyimport.add(zlyrow);
                                    }
                                    if (interpaperXLS.getKontrahent() != null && (interpaperXLS.getNettowaluta() != 0.0 || interpaperXLS.getVatwaluta() != 0.0)) {
                                        interpaperXLS.setNr(i++);
                                        listafaktur.add(interpaperXLS);
                                    } else {
                                        importyzbrakami.add(interpaperXLS);
                                    }
                                } else if (jakipobor.equals("paragony") && (nip==null || nip.equals("") || nip.length()<8)) {
                                    zlyrow = uzupelnijsprzedazPar(interpaperXLS, row, k, klienciDAO, znalezieni);
                                    if (zlyrow != null) {
                                        przerwanyimport.add(zlyrow);
                                    }
                                    if (interpaperXLS.getKontrahent() != null && (interpaperXLS.getNettowaluta() != 0.0 || interpaperXLS.getVatwaluta() != 0.0)) {
                                        interpaperXLS.setNr(i++);
                                        listafaktur.add(interpaperXLS);
                                    } else {
                                        importyzbrakami.add(interpaperXLS);
                                    }
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
        }
        List<Dokfk> faktury = dokDAOfk.findDokfkPodatnikRokMc(wpisView);
        boolean znaleziono = false;
        for (Iterator<Dokfk> it = faktury.iterator();it.hasNext();) {
            Dokfk d = it.next();
            for (Iterator<InterpaperXLS> itn = listafaktur.iterator();itn.hasNext();) {
                InterpaperXLS e = itn.next();
                if (d.getNumerwlasnydokfk().equals(e.getNrfaktury())) {
                    e.setJuzzaksiegowany(true);
                    znaleziono = true;
                    break;
                }
            }
            if (znaleziono) {
                it.remove();
                znaleziono = false;
            }
        }
        zwrot[0] = listafaktur;
        zwrot[2] = importyzbrakami;
        List<InterpaperXLS> zlefakturylista = new ArrayList<>();
        Map<String, Klienci> znalezieni = new HashMap<>();
        int i =1;
        for (ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT row :przerwanyimport) {
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
        for (ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT row :innyokres) {
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
     
   private static void uzupelnijzakup(InterpaperXLS interpaperXLS, ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT row, List<Klienci> k, KlienciDAO klienciDAO, Map<String, Klienci> znalezieni) {
//        if (row.getCell(16).getStringCellValue().equals("Zakup")) {
//            interpaperXLS.setNrfaktury(row.getCell(0).getStringCellValue());
//            interpaperXLS.setDatawystawienia(row.getCell(10).getDateCellValue());
//            interpaperXLS.setDatasprzedaży(row.getCell(8).getDateCellValue());
//            interpaperXLS.setDataobvat(row.getCell(8).getDateCellValue());
//            interpaperXLS.setKlientnazwa(row.getCell(3).getStringCellValue());
//            interpaperXLS.setKlientpaństwo(row.getCell(4).getStringCellValue());
//            interpaperXLS.setKlientkod(row.getCell(5).getStringCellValue());
//            interpaperXLS.setKlientmiasto(row.getCell(6).getStringCellValue());
//            interpaperXLS.setKlientulica(row.getCell(7).getStringCellValue());
//            interpaperXLS.setKlientdom("");
//            interpaperXLS.setKlientlokal("");
//            String kontr = row.getCell(3).getStringCellValue()+" "+row.getCell(3).getStringCellValue()+" "+row.getCell(5).getStringCellValue()+" "+row.getCell(6).getStringCellValue();
//            interpaperXLS.setKontrahent(kontr);
//            interpaperXLS.setNip(row.getCell(2).getStringCellValue().replace("-", "").trim());
//            interpaperXLS.setKlient(ustawkontrahenta(interpaperXLS, k, klienciDAO, znalezieni));
//            interpaperXLS.setWalutaplatnosci(row.getCell(12).getStringCellValue());
//            interpaperXLS.setSaldofaktury(row.getCell(17).getNumericCellValue()+row.getCell(18).getNumericCellValue());
//            interpaperXLS.setTerminplatnosci(row.getCell(9).getDateCellValue());
//            interpaperXLS.setNettowaluta(row.getCell(17).getNumericCellValue());
//            interpaperXLS.setVatwaluta(row.getCell(18).getNumericCellValue());
//            interpaperXLS.setBruttowaluta(row.getCell(17).getNumericCellValue()+row.getCell(18).getNumericCellValue());
//            interpaperXLS.setNettoPLN(row.getCell(13).getNumericCellValue());
//            interpaperXLS.setNettoPLNvat(row.getCell(13).getNumericCellValue());
//            interpaperXLS.setVatPLN(row.getCell(14).getNumericCellValue());
//        }
   }
   
//FV i FV 2  Sprzedaz krajowa i traktowana zagranica jako krajowa z VAT
//PAR i PAR 2  Sprzedaz fiskalna paragony
//FD  i FD 2  Sprzedaz unijna 
//FE i FE 2  Sprzedaz eksportowa
//FVPR i FVPR 2  Sprzedaz z przekroczeniem limitu gdzie stosowana ma być stawka danego kraju (DE  16% i FR 20%). 

    private static ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT uzupelnijsprzedaz(InterpaperXLS interpaperXLS, ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT row, List<Klienci> k, KlienciDAO klienciDAO, Map<String, Klienci> znalezieni) {
        ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT zlafaktura = null;
        if (!row.getOPIS().equals("PAR")&&!row.getOPIS().equals("PAR 2")&&!row.getOPIS().equals("KPAR")&&!row.getOPIS().equals("KPAR 2")) {
            try {
                if (row.getIDZRODLA().equals("A5BB0C3C-43E3-4251-97DB-DE9CF8FF59E8")) {
                    System.out.println("");
                }
                interpaperXLS.setNrfaktury(row.getNUMER());
                interpaperXLS.setDatawystawienia(row.getDATAWYSTAWIENIA().toGregorianCalendar().getTime());
                interpaperXLS.setDatasprzedaży(row.getDATASPRZEDAZY().toGregorianCalendar().getTime());
                interpaperXLS.setDataobvat(row.getDATASPRZEDAZY().toGregorianCalendar().getTime());
                interpaperXLS.setKlientnazwa(row.getNAZWA1());
                interpaperXLS.setKlientpaństwo(row.getKRAJ());
                interpaperXLS.setKlientkod(row.getKODPOCZTOWY());
                interpaperXLS.setKlientmiasto(row.getMIASTO());
                interpaperXLS.setKlientulica(row.getULICA());
                interpaperXLS.setKlientdom(row.getNRDOMU());
                interpaperXLS.setKlientlokal(row.getNRLOKALU());
                String kontr = row.getNAZWA1()+" "+row.getKRAJ()+" "+row.getKODPOCZTOWY()+" "+row.getMIASTO();
                interpaperXLS.setKontrahent(kontr);
                interpaperXLS.setNip(pobierznip(row));
                interpaperXLS.setNipkrajzorin(row.getNIPKRAJ());
                interpaperXLS.setKlient(ustawkontrahenta(interpaperXLS, k, klienciDAO, znalezieni));
                interpaperXLS.setWalutaplatnosci(pobierzwalute(row));
                List<ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT.POZYCJE.POZYCJA> poz = row.getPOZYCJE().getPOZYCJA();
                ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT.PLATNOSCI.PLATNOSC plt = row.getPLATNOSCI().getPLATNOSC();
                interpaperXLS.setSaldofaktury(plt!=null?plt.getKWOTAPLAT():0.0);
                interpaperXLS.setTerminplatnosci(plt!=null&&plt.getTERMINPLAT()!=null? plt.getTERMINPLAT().toGregorianCalendar().getTime():null);
                double kwoty[] = obliczkwoty(poz,plt, pobierzwalute(row));
                interpaperXLS.setNettoPLN(kwoty[0]);
                interpaperXLS.setNettoPLNvat(kwoty[0]);
                interpaperXLS.setVatPLN(kwoty[1]);
                interpaperXLS.setBruttoPLN(kwoty[2]);
                interpaperXLS.setNettowaluta(kwoty[3]);
                interpaperXLS.setVatwaluta(kwoty[4]);
                interpaperXLS.setBruttowaluta(kwoty[5]);
            } catch (Exception e) {
                zlafaktura = row;
            }
        }
        return zlafaktura;
    }
    
    private static ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT uzupelnijsprzedazPar(InterpaperXLS interpaperXLS, ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT row, List<Klienci> k, KlienciDAO klienciDAO, Map<String, Klienci> znalezieni) {
        ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT zlafaktura = null;
        if (row.getOPIS().equals("PAR")&&row.getOPIS().equals("PAR 2")&&row.getOPIS().equals("KPAR")&&row.getOPIS().equals("KPAR 2")) {
            try {
                interpaperXLS.setNrfaktury(row.getNUMER());
                interpaperXLS.setDatawystawienia(row.getDATAWYSTAWIENIA().toGregorianCalendar().getTime());
                interpaperXLS.setDatasprzedaży(row.getDATASPRZEDAZY().toGregorianCalendar().getTime());
                interpaperXLS.setDataobvat(row.getDATASPRZEDAZY().toGregorianCalendar().getTime());
                interpaperXLS.setKlientnazwa(row.getNAZWA1());
                interpaperXLS.setKlientpaństwo(row.getKRAJ());
                interpaperXLS.setKlientkod(row.getKODPOCZTOWY());
                interpaperXLS.setKlientmiasto(row.getMIASTO());
                interpaperXLS.setKlientulica(row.getULICA());
                interpaperXLS.setKlientdom(row.getNRDOMU());
                interpaperXLS.setKlientlokal(row.getNRLOKALU());
                String kontr = row.getNAZWA1()+" "+row.getKRAJ()+" "+row.getKODPOCZTOWY()+" "+row.getMIASTO();
                interpaperXLS.setKontrahent(kontr);
                interpaperXLS.setNip(pobierznip(row));
                interpaperXLS.setKlient(ustawkontrahenta(interpaperXLS, k, klienciDAO, znalezieni));
                interpaperXLS.setWalutaplatnosci(pobierzwalute(row));
                List<ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT.POZYCJE.POZYCJA> poz = row.getPOZYCJE().getPOZYCJA();
                ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT.PLATNOSCI.PLATNOSC plt = row.getPLATNOSCI().getPLATNOSC();
                interpaperXLS.setSaldofaktury(plt.getKWOTAPLAT());
                interpaperXLS.setTerminplatnosci(plt.getTERMINPLAT()!=null? plt.getTERMINPLAT().toGregorianCalendar().getTime():null);
                double kwoty[] = obliczkwoty(poz,plt, pobierzwalute(row));
                interpaperXLS.setNettoPLN(kwoty[0]);
                interpaperXLS.setNettoPLNvat(kwoty[0]);
                interpaperXLS.setVatPLN(kwoty[1]);
                interpaperXLS.setBruttoPLN(kwoty[2]);
                interpaperXLS.setNettowaluta(kwoty[3]);
                interpaperXLS.setVatwaluta(kwoty[4]);
                interpaperXLS.setBruttowaluta(kwoty[5]);
            } catch (Exception e) {
                zlafaktura = row;
            }
        }
        return zlafaktura;
    }
    
    private static ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT uzupelnijsprzedaz2(InterpaperXLS interpaperXLS, ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT row, List<Klienci> k, KlienciDAO klienciDAO, Map<String, Klienci> znalezieni) {
        ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT zlafaktura = null;
        try {
            interpaperXLS.setNrfaktury(row.getNUMER());
            interpaperXLS.setDatawystawienia(row.getDATAWYSTAWIENIA().toGregorianCalendar().getTime());
            interpaperXLS.setDatasprzedaży(row.getDATASPRZEDAZY().toGregorianCalendar().getTime());
            interpaperXLS.setDataobvat(row.getDATASPRZEDAZY().toGregorianCalendar().getTime());
            interpaperXLS.setKlientnazwa(row.getNAZWA1());
            interpaperXLS.setKlientpaństwo(row.getKRAJ()!=null?row.getKRAJ().trim():null);
            interpaperXLS.setKlientkod(row.getKODPOCZTOWY());
            interpaperXLS.setKlientmiasto(row.getMIASTO());
            interpaperXLS.setKlientulica(row.getULICA());
            interpaperXLS.setKlientdom(row.getNRDOMU());
            interpaperXLS.setKlientlokal(row.getNRLOKALU());
            String kontr = row.getNAZWA1()+" "+row.getKRAJ()+" "+row.getKODPOCZTOWY()+" "+row.getMIASTO();
            interpaperXLS.setKontrahent(kontr);
            interpaperXLS.setNip(pobierznip(row));
            interpaperXLS.setNipkrajzorin(row.getNIPKRAJ()!=null?row.getNIPKRAJ().trim():null);
            interpaperXLS.setKlient(ustawkontrahenta(interpaperXLS, k, klienciDAO, znalezieni));
            interpaperXLS.setWalutaplatnosci(pobierzwalute(row));
            List<ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT.POZYCJE.POZYCJA> poz = row.getPOZYCJE().getPOZYCJA();
            ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT.PLATNOSCI.PLATNOSC plt = row.getPLATNOSCI().getPLATNOSC();
            interpaperXLS.setSaldofaktury(plt.getKWOTAPLAT());
            interpaperXLS.setTerminplatnosci(plt.getTERMINPLAT()!=null? plt.getTERMINPLAT().toGregorianCalendar().getTime():null);
            double kwoty[] = obliczkwoty(poz,plt, pobierzwalute(row));
            interpaperXLS.setNettoPLN(kwoty[0]);
            interpaperXLS.setNettoPLNvat(kwoty[0]);
            interpaperXLS.setVatPLN(kwoty[1]);
            interpaperXLS.setBruttoPLN(kwoty[2]);
            interpaperXLS.setNettowaluta(kwoty[3]);
            interpaperXLS.setVatwaluta(kwoty[4]);
            interpaperXLS.setBruttowaluta(kwoty[5]);
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
       if (interpaperXLS.getNrfaktury().equals("G/FD 2/000739/02/21")) {
           System.out.println("");
       }
       if (interpaperXLS.getNrfaktury().equals("G/FD 2/000796/02/21")) {
           System.out.println("");
       }
        try {
            if (!znalezieni.isEmpty()) {
                Set<String> keySet = znalezieni.keySet();
                for (String p : keySet) {
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
            if (klient==null && interpaperXLS.getNip()!=null && interpaperXLS.getNip().length()>6 && interpaperXLS.getNipkrajzorin().isEmpty()) {
                String nip = interpaperXLS.getNip().trim();
                if (nip.length()==10 && Character.isDigit(nip.charAt(0))) {
                    klient = SzukajDaneBean.znajdzdaneregonAutomat(nip);
                    if (klient.getNpelna()==null && (klient.getNpelna().equals("wystąpił błąd logowania do serwera GUS")||klient.getNpelna().equals("nie znaleziono firmy w bazie Regon"))) {
                        klient = null;
                    } else {
                        if (!klient.getNpelna().equals("nie znaleziono firmy w bazie Regon")) {
                            klient.setKrajkod("PL");
                            klient.setKrajnazwa("Polska");
                            klienciDAO.create(klient);
                        }
                    }
                    znalezieni.put(interpaperXLS.getKontrahent(), klient);
                }
            }
             
            if (klient==null && interpaperXLS.getNip()!=null && interpaperXLS.getNip().length()>6) {
                String nip = interpaperXLS.getNip().trim();
                if (interpaperXLS.getKlientpaństwo()!=null) {
                    klient = new Klienci(1, interpaperXLS.getKlientnazwa(), interpaperXLS.getKlientnazwa(), interpaperXLS.getNip(), interpaperXLS.getKlientkod(), interpaperXLS.getKlientmiasto(), interpaperXLS.getKlientulica(), interpaperXLS.getKlientdom(), interpaperXLS.getKlientlokal());
                    klient.setKrajnazwa(interpaperXLS.getKlientpaństwo()!=null?interpaperXLS.getKlientpaństwo().trim():null);
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
   
    
      
    public static void main(String[] args) {
        try {
            FileInputStream file = new FileInputStream(new File("d://S1.xml"));
            InputStreamReader reader = new InputStreamReader(file, "Windows-1250");
            JAXBContext jaxbContext = JAXBContext.newInstance(ROOT.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ROOT tabela =  (ROOT) jaxbUnmarshaller.unmarshal(reader);
             //Create Workbook instance holding reference to .xlsx file  TYLKO NOWE XLSX
            if (tabela!=null && tabela.getREJESTRYSPRZEDAZYVAT()!=null && !tabela.getREJESTRYSPRZEDAZYVAT().getREJESTRSPRZEDAZYVAT().isEmpty()) {
                int i =1;
                int paragon = 0;
                int faktura = 0;
                for (ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT row :tabela.getREJESTRYSPRZEDAZYVAT().getREJESTRSPRZEDAZYVAT()) {
                    try {
                        InterpaperXLS interpaperXLS = new InterpaperXLS();
                        String nip = pobierznip(row);
                        if (!row.getOPIS().equals("PAR")&&!row.getOPIS().equals("PAR 2")&&!row.getOPIS().equals("KPAR")&&!row.getOPIS().equals("KPAR 2")) {
                            interpaperXLS.setNrfaktury(row.getNUMER());
                            interpaperXLS.setDatawystawienia(row.getDATAWYSTAWIENIA().toGregorianCalendar().getTime());
                            interpaperXLS.setDatasprzedaży(row.getDATASPRZEDAZY().toGregorianCalendar().getTime());
                            interpaperXLS.setDataobvat(row.getDATASPRZEDAZY().toGregorianCalendar().getTime());
                            interpaperXLS.setKlientnazwa(row.getNAZWA1());
                            interpaperXLS.setKlientpaństwo(row.getKRAJ());
                            interpaperXLS.setKlientkod(row.getKODPOCZTOWY());
                            interpaperXLS.setKlientmiasto(row.getMIASTO());
                            interpaperXLS.setKlientulica(row.getULICA());
                            interpaperXLS.setKlientdom(row.getNRDOMU());
                            interpaperXLS.setKlientlokal(row.getNRLOKALU());
                            String kontr = row.getNAZWA1()+" "+row.getKRAJ()+" "+row.getKODPOCZTOWY()+" "+row.getMIASTO();
                            interpaperXLS.setKontrahent(kontr);
                            interpaperXLS.setNip(pobierznip(row));
                            interpaperXLS.setWalutaplatnosci(pobierzwalute(row));
                            List<ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT.POZYCJE.POZYCJA> poz = row.getPOZYCJE().getPOZYCJA();
                            ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT.PLATNOSCI.PLATNOSC plt = row.getPLATNOSCI().getPLATNOSC()!=null?row.getPLATNOSCI().getPLATNOSC():null;
                            interpaperXLS.setSaldofaktury(plt.getKWOTAPLAT());
                            interpaperXLS.setTerminplatnosci(plt.getTERMINPLAT().toGregorianCalendar().getTime());
                            double kwoty[] = obliczkwoty(poz,plt, pobierzwalute(row));
                            interpaperXLS.setNettoPLN(kwoty[0]);
                            interpaperXLS.setNettoPLNvat(kwoty[0]);
                            interpaperXLS.setVatPLN(kwoty[1]);
                            interpaperXLS.setBruttoPLN(kwoty[2]);
                            interpaperXLS.setNettowaluta(kwoty[3]);
                            interpaperXLS.setVatwaluta(kwoty[4]);
                            interpaperXLS.setBruttowaluta(kwoty[5]);
                            faktura++;
                        } else {
                            paragon++;
                        }
                    } catch (Exception e){
                        E.e(e);
                    }
                }
                System.out.println("ilosc paragonow "+paragon);
            }
            file.close();
        }
        catch (Exception e) {
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

    private static String pobierznip(ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT row) {
        String zwrot = null;
        String podmiotit = row.getPODMIOTID();
        if (!podmiotit.equals("00000000-0009-0002-0001-000000000000")) {
            zwrot = row.getNIP();
            zwrot = zwrot.replace("-","");
        }
        return zwrot;
    }

    private static String pobierzwalute(ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT row) {
        String zwrot = null;
        zwrot = row.getPLATNOSCI().getPLATNOSC()!=null?row.getPLATNOSCI().getPLATNOSC().getWALUTADOK():null;
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
        if (plt==null) {
            nettopln = pozycjenetto;
            vatpln = pozycjevat;
            zwrot[0] = nettopln;
            zwrot[1] = vatpln;
            zwrot[2] = Z.z(nettopln+vatpln);
            zwrot[3] = nettopln;
            zwrot[4] = vatpln;
            zwrot[5] = Z.z(nettopln+vatpln);
        } else {
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
                if (Z.z(Math.abs(sumapozycje))==Z.z(Math.abs(plt.getKWOTAPLAT()))) {
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
