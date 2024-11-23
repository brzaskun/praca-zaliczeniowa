/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import embeddablefk.PodsumowanieDanychAVTR;
import entity.KlientJPK;
import entity.Podatnik;
import entity.PodsumowanieAmazonOSS;
import error.E;
import formatpdf.F;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.primefaces.PrimeFaces;
import static pdf.PdfDok.dodajsumyfk;
import pdffk.PdfMain;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.dodajTabele2;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import plik.Plik;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class PdfAmazon extends Pdf implements Serializable {
    
    public static void drukujDokAmazonfk(List<KlientJPK> lista, WpisView wpisView, int modyfikator) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"AmazonRaport"+wpisView.getRokWpisuSt()+wpisView.getMiesiacWpisu();
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        Document document = PdfMain.inicjacjaA4Landscape();
        try {
            double netto = 0.0;
            double vat = 0.0;
            int ilosc = 0;
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Zestawienie zaksięgowanych dokumentów firma -  ", wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            Set<String> waluty = pobierzwalutyfk(lista);
            Set<String> kraje = pobierzkrajefk(lista);
            List<PodsumowanieDanychAVTR> tabelazbiorcza = new ArrayList<>();
            List<PodsumowanieDanychAVTR> tabelazbiorczafctransfer = new ArrayList<>();
            List<PodsumowanieDanychAVTR> tabelazbiorczawdt = new ArrayList<>();
            List<PodsumowanieDanychAVTR> tabelazbiorczaexport = new ArrayList<>();
            Map<String, List<KlientJPK>> kategorie = new HashMap<>();
            kategorie.put("sprzedaz", new ArrayList<>());
            kategorie.put("fctransfer", new ArrayList<>());
            kategorie.put("wdt", new ArrayList<>());
            kategorie.put("export", new ArrayList<>());
            List<KlientJPK> listatabele = new ArrayList<>();
            for (KlientJPK klient : lista) {
                if (!klient.isWdt() && !klient.isEksport() && !"FC_TRANSFER".equals(klient.getRodzajtransakcji())) {
                    kategorie.get("sprzedaz").add(klient);
                } else if ("FC_TRANSFER".equals(klient.getRodzajtransakcji())) {
                    kategorie.get("fctransfer").add(klient);
                } else if (klient.isWdt()) {
                    kategorie.get("wdt").add(klient);
                } else if (klient.isEksport()) {
                    kategorie.get("export").add(klient);
                } else {
                    System.out.println("");
                }
            }
            System.out.println("sprzedaz oss "+kategorie.get("sprzedaz").size());
            for (String kraj : kraje) {
                List<KlientJPK> sprzedazkraj = kategorie.get("sprzedaz").stream().filter((p) -> p.getJurysdykcja()!=null&&p.getJurysdykcja().equals(kraj)).collect(Collectors.toList());
                for (String waluta : waluty) {
                    List<KlientJPK> sprzedazwaluty = sprzedazkraj.stream().filter((p) -> p.getWaluta().equals(waluta)).collect(Collectors.toList());
                    if (!sprzedazwaluty.isEmpty()) {
                        dodajtabelekrajfk(kraj, waluta, document, sprzedazwaluty, modyfikator, tabelazbiorcza, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
                        for (KlientJPK klient : sprzedazwaluty) {
                            netto = Z.z(netto+klient.getNettowaluta());
                            vat = Z.z(vat+klient.getVatwaluta());
                        }
                        ilosc = ilosc+sprzedazwaluty.size();
                        listatabele.addAll(sprzedazwaluty);
                    }
                    
                }
            }
            PdfMain.dodajLinieOpisu(document, "");
            if (kategorie.get("fctransfer").isEmpty()) {
                PdfMain.dodajLinieOpisuBezOdstepu(document, "BRAK PRZESUNIĘĆ FC TRANSFER");
            } else {
                System.out.println("sprzedaz fc_transfer "+kategorie.get("fctransfer").size());
                for (String kraj : kraje) {
                    List<KlientJPK> sprzedazkraj = kategorie.get("fctransfer").stream().filter((p) -> p.getJurysdykcja()!=null&&p.getJurysdykcja().equals(kraj)).collect(Collectors.toList());
                    for (String waluta : waluty) {
                        List<KlientJPK> sprzedazwaluty = sprzedazkraj.stream().filter((p) -> p.getWaluta().equals(waluta)).collect(Collectors.toList());
                        if (!sprzedazwaluty.isEmpty()) {
                            dodajtabelefctransfer(kraj, waluta, document, sprzedazwaluty, modyfikator, tabelazbiorczafctransfer, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
                             for (KlientJPK klient : sprzedazwaluty) {
                                netto = Z.z(netto+klient.getNettowaluta());
                                vat = Z.z(vat+klient.getVatwaluta());
                            }
                             ilosc = ilosc+sprzedazwaluty.size();
                             listatabele.addAll(sprzedazwaluty);
                        }
                    }
                }
            }
            PdfMain.dodajLinieOpisu(document, "");
            if (kategorie.get("wdt").isEmpty()) {
                PdfMain.dodajLinieOpisuBezOdstepu(document, "BRAK SPRZEDAŻY WDT");
            } else {
                System.out.println("sprzedaz wdt "+kategorie.get("wdt").size());
                for (String kraj : kraje) {
                    List<KlientJPK> sprzedazkraj = kategorie.get("wdt").stream().filter((p) -> p.getJurysdykcja()!=null&&p.getJurysdykcja().equals(kraj)).collect(Collectors.toList());
                    for (String waluta : waluty) {
                        List<KlientJPK> sprzedazwaluty = sprzedazkraj.stream().filter((p) -> p.getWaluta().equals(waluta)).collect(Collectors.toList());
                        if (!sprzedazwaluty.isEmpty()) {
                            dodajtabeleWDTfk(kraj, waluta, document, sprzedazwaluty, modyfikator, tabelazbiorczawdt);
                             for (KlientJPK klient : sprzedazwaluty) {
                               netto = Z.z(netto+klient.getNettowaluta());
                                vat = Z.z(vat+klient.getVatwaluta());
                            }
                             ilosc = ilosc+sprzedazwaluty.size();
                             listatabele.addAll(sprzedazwaluty);
                        }
                    }
                }
            }
            PdfMain.dodajLinieOpisu(document, "");
            if (kategorie.get("export").isEmpty()) {
                PdfMain.dodajLinieOpisuBezOdstepu(document, "BRAK SPRZEDAŻY EXPORT");
            } else {
                System.out.println("export "+kategorie.get("export").size());
                for (String kraj : kraje) {
                    List<KlientJPK> sprzedazkraj = kategorie.get("export").stream().filter((p) -> p.getJurysdykcja()!=null&&p.getJurysdykcja().equals(kraj)).collect(Collectors.toList());
                    for (String waluta : waluty) {
                        List<KlientJPK> sprzedazwaluty = sprzedazkraj.stream().filter((p) -> p.getWaluta().equals(waluta)).collect(Collectors.toList());
                        if (!sprzedazwaluty.isEmpty()) {
                            dodajtabeleExportfk(kraj, waluta, document, sprzedazwaluty, modyfikator, tabelazbiorczaexport);
                             for (KlientJPK klient : sprzedazwaluty) {
                                netto = Z.z(netto+klient.getNettowaluta());
                                vat = Z.z(vat+klient.getVatwaluta());
                            }
                             ilosc = ilosc+sprzedazwaluty.size();
                             listatabele.addAll(sprzedazwaluty);
                        }
                    }
                }
                sumujtabelazbiorcza(tabelazbiorczaexport);
            }
            List<KlientJPK> roznicowa = lista.stream()
            .filter(klient -> !listatabele.contains(klient))
            .collect(Collectors.toList());
            double netto1 =0.0;
            double vat1 =0.0;
            for (KlientJPK klient : listatabele) {
                    netto1 = Z.z(netto+klient.getNettowaluta());
                    vat1 = Z.z(vat+klient.getVatwaluta());
            }
            
            document.newPage();
            PdfMain.dodajLinieOpisu(document, "SUMY SPRZEDAŻY DETALICZNEJ");
            String[] nag1 = new String[]{"lp","kraj","waluta","netto","vat","netto pln","vat pln", "stawka vat", "kurs"};
            int[] nag2 = new int[]{2,3,3,3,3,3,3,3,3};
            dodajsume(tabelazbiorcza);
            dodajTabele2(document, nag1, nag2, tabelazbiorcza, 70, modyfikator,"tabelaklientjpkfk");
            document.newPage();
            PdfMain.dodajLinieOpisu(document, "SUMY FC TRANSFER");
            dodajsume(tabelazbiorczafctransfer);
            dodajTabele2(document, nag1, nag2, tabelazbiorczafctransfer, 70, modyfikator,"tabelaklientjpkfk");
            document.newPage();
            PdfMain.dodajLinieOpisu(document, "SUMY SPRZEDAŻY WDT");
            dodajsume(tabelazbiorczawdt);
            dodajTabele2(document, nag1, nag2, tabelazbiorczawdt, 70, modyfikator,"tabelaklientjpkfk");
            document.newPage();
            PdfMain.dodajLinieOpisu(document, "SUMY SPRZEDAŻY EXPORT");
            dodajsume(tabelazbiorczaexport);
            dodajTabele2(document, nag1, nag2, tabelazbiorczaexport, 70, modyfikator,"tabelaklientjpkfk");
            PdfMain.dodajLinieOpisu(document, "PODSUMOWANIE IMPORTU");
            PdfMain.dodajLinieOpisuBezOdstepu(document, "netto waluty "+F.currplain(netto));
            PdfMain.dodajLinieOpisuBezOdstepu(document, "vat waluty "+F.currplain(vat));
            PdfMain.dodajLinieOpisuBezOdstepu(document, "razem brutto "+F.currplain(Z.z(netto+vat)));
            PdfMain.dodajLinieOpisuBezOdstepu(document, "ilosc "+F.number(ilosc));
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } catch (Exception e) {
            E.e(e);
        } finally {
            finalizacjaDokumentuQR(document,nazwa);
        }
    }
    
    
     private static void dodajsume(List<PodsumowanieDanychAVTR> tabelazbiorcza) {
        double netto = 0.0;
        double vat = 0.0;
        double nettopln = 0.0;
        double vatpln = 0.0;
        for (PodsumowanieDanychAVTR p : tabelazbiorcza) {
            netto = netto + p.getNettoWaluta();
            vat = vat + p.getVatWaluta();
            nettopln = nettopln + p.getNetto();
            vatpln = vatpln + p.getVat();
        }
        PodsumowanieDanychAVTR a = new PodsumowanieDanychAVTR(0,"", "razem", netto, vat, nettopln, vatpln, 0.0, 0.0);
        tabelazbiorcza.add(a);
               
    }

      private static Set<String> pobierzwalutyfk(List<KlientJPK> lista) {
        Set<String> zwrot = new HashSet<>();
        for (KlientJPK p : lista) {
            String poz = p.getWaluta();
            if (poz!=null) {
                zwrot.add(poz);
            }
        }
        return zwrot;
    }

    private static Set<String> pobierzkrajefk(List<KlientJPK> lista) {
        Set<String> zwrot = new HashSet<>();
        for (KlientJPK p : lista) {
            String poz = p.getJurysdykcja();
            if (poz!=null) {
                zwrot.add(poz);
            }
        }
        return zwrot;
    }
    private static PodsumowanieAmazonOSS dodajtabelekrajfk(String kraj, String waluta, Document document, List<KlientJPK> lista, int modyfikator, List<PodsumowanieDanychAVTR> tabelazbiorcza, Podatnik podatnik, String rok, String mc) {
        PdfMain.dodajLinieOpisuBezOdstepuKolor(document, "SPRZEDAŻ OPODATKOWANA "+kraj.toUpperCase()+" WALUTA "+waluta, BaseColor.BLACK);
        dodajTabele(document, testobjects.testobjects.getListaDokJPK(lista),100,modyfikator);
        PodsumowanieAmazonOSS zwrot = dodajsumyfk(lista, document, waluta, tabelazbiorcza, podatnik, rok, mc);
        return zwrot;
    }
    
    private static PodsumowanieAmazonOSS dodajtabelefctransfer(String kraj, String waluta, Document document, List<KlientJPK> lista, int modyfikator, List<PodsumowanieDanychAVTR> tabelazbiorcza, Podatnik podatnik, String rok, String mc) {
        PdfMain.dodajLinieOpisuBezOdstepuKolor(document, "FC TRANSFER PRZESUNIĘCIA "+kraj.toUpperCase()+" WALUTA "+waluta, BaseColor.BLACK);
        dodajTabele(document, testobjects.testobjects.getListaDokJPK(lista),100,modyfikator);
        PodsumowanieAmazonOSS zwrot = dodajsumyfk(lista, document, waluta, tabelazbiorcza, podatnik, rok, mc);
        return zwrot;
    }
    
    private static void dodajtabeleWDTfk(String kraj, String waluta, Document document, List<KlientJPK> lista, int modyfikator,List<PodsumowanieDanychAVTR> tabelazbiorcza) {
        PdfMain.dodajLinieOpisuBezOdstepuKolor(document, "SPRZEDAŻ WDT "+kraj.toUpperCase()+" WALUTA "+waluta, BaseColor.BLACK);
        dodajTabele(document, testobjects.testobjects.getListaDokJPK(lista),100,modyfikator);
        dodajsumyfk(lista, document, waluta, tabelazbiorcza, null, null, null);
    }
    
    private static void dodajtabeleExportfk(String kraj, String waluta, Document document, List<KlientJPK> lista, int modyfikator,List<PodsumowanieDanychAVTR> tabelazbiorcza) {
        PdfMain.dodajLinieOpisuBezOdstepuKolor(document, "SPRZEDAŻ EXPORT "+kraj.toUpperCase()+" WALUTA "+waluta, BaseColor.BLACK);
        dodajTabele(document, testobjects.testobjects.getListaDokJPK(lista),100,modyfikator);
        dodajsumyfk(lista, document, waluta, tabelazbiorcza, null, null, null);
    } 
    private static void sumujtabelazbiorcza(List tabelazbiorczaexport) {
//         
//         if (tabelazbiorczaexport !=null) {
//            for (List a : tabelazbiorczaexport) {
//               Object[] p = a.toArray();
//               //new Object[]{jurys, waluta, nettowaluta, vatwaluta, netto, vat, vatstawka, kurs};
//            }
//         }
    }
     
}
